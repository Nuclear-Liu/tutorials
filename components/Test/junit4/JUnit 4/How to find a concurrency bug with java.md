# How to find a concurrency bug with java?

> By Daniel Schröter    In Java, Methodology, Testing   August 25, 2009     7 Min read      8 comments

How to find a concurrency bug – this was the question I asked myself some time ago.

It is always very hard to find a concurrency bug. Mostly you have no idea when it happens or if it is really a concurrency issue or some nasty bit of code. 
If it is a concurrency issue the question is if the bug is in your code or in a supplied library? 
Will the problem happen only on multicore processors or on any machine? 
Besides the technical problem the customer is eager to get a solution and management… we’ll i guess you know the story.

I won’t be able to tell you everything there is to know about concurrency testing – but I’ll show you a way that worked for me in most cases.

## The problem

One day I opened our bug tracking tool to get my next piece of work. 
It was an easy bug it seemed. Some validation failed on a number with wohoo 2 points in it. 
Well my thirst thought was “stupid user unable to type a number?”. 
5 minutes later some sweat was building up – the number came right from the database and there it was held in a proper decimal field…

## Reasons

How can that be? Obviously it can’t since we have a lot of unit tests and the application runs since many months and never a bug like this. 
But since I do not believe in fairies modifying values on the fly there must be a reason. 
But how do I find the filthy bug? 
It can be in every place from the database over the jdbc driver up to the business logic and up to the gui creation where processing stopped with an exception being thrown.

Some digging proofed that there were indeed tests that looked ok and soon I was browsing the logfiles for similar bugs. 
I did not find much – some numbers out of range the closest match.

The reasons that I felt most possible where: concurrency problem or calculation issue. 
I opted for the first one.

## Test

As a convinced TDD supporter I normally write a test that proofs the bug and then fix it. 
For a presumed concurrency problem just the same.

A test for a concurrency bug is mostly not a plain unit test since you do not know which unit contains the bug. 
When you have some unit tests (I hope you do) it is mostly easy to modify some tests to extract a test that uses the units you expect contain the bug.

The test should use many threads that start processing almost simultaneously to have a higher possibility of finding or in this case “causing” the bug.

Testing framework is an important criterion since it must support unsynchronized testing. 
A synchronized block at the wrong place could “fix” the test and hide the bug.

Optimally the application adheres to the fail fast pattern for fast failure perception.

## Test Code

The code of the unit test for above problem would not explain a lot since it contains a lot of application specific stuff. 
The framework used in this application is also proprietary. 
What I can show you is the code I use to perform the test:

```text
public static void assertConcurrent(final String message, final List<? extends Runnable> runnables, final int maxTimeoutSeconds) throws InterruptedException {
  final int numThreads = runnables.size();
  final List<Throwable> exceptions = Collections.synchronizedList(new ArrayList<Throwable>());
  final ExecutorService threadPool = Executors.newFixedThreadPool(numThreads);
  try {
    final CountDownLatch allExecutorThreadsReady = new CountDownLatch(numThreads);
    final CountDownLatch afterInitBlocker = new CountDownLatch(1);
    final CountDownLatch allDone = new CountDownLatch(numThreads);
    for (final Runnable submittedTestRunnable : runnables) {
      threadPool.submit(new Runnable() {
        public void run() {
          allExecutorThreadsReady.countDown();
          try {
            afterInitBlocker.await();
            submittedTestRunnable.run();
          } catch (final Throwable e) {
            exceptions.add(e);
          } finally {
            allDone.countDown();
          }
        }
      });
    }
    // wait until all threads are ready
    assertTrue(&quot;Timeout initializing threads! Perform long lasting initializations before passing runnables to assertConcurrent&quot;, allExecutorThreadsReady.await(runnables.size() * 10, TimeUnit.MILLISECONDS));
    // start all test runners
    afterInitBlocker.countDown();
    assertTrue(message +&quot; timeout! More than&quot; + maxTimeoutSeconds + &quot;seconds&quot;, allDone.await(maxTimeoutSeconds, TimeUnit.SECONDS));
  } finally {
    threadPool.shutdownNow();
  }
  assertTrue(message + &quot;failed with exception(s)&quot; + exceptions, exceptions.isEmpty());
}
```

I explain shortly the most important parts of this test.

For every runnable we create an adaptor runnable that takes care of waiting for all threads to be created, submitting failures to our exceptions list and notification when processing finished. 
It is very important to catch Throwable since we expect anything to happen from AssertionException, ConcurrentModificationException to Business Exceptions.

The adaptor runnables are being put into a jdk provided thread pool of the same size as the number of runnables. 
Just after being put into the thread pool an added thread executes and waits on

`afterInitBlocker.await();`

while the starting thread (may) waits on
allExecutorThreadsReady.await(runnables.size() * 10, TimeUnit.MILLISECONDS);

. This way it is guaranteed that when we call

`afterInitBlocker.countDown();`

from the starting thread that all testing threads are fully initialized.

`afterInitBlocker.countDown();`

notifies all waiting threads and they start testing

`submittedTestRunnable.run();`

. By starting all testing threads this way we achieve the maximum concurrent test load possible. 
The inner finally block assures that our testing thread is notified whether we catch a failure or the test runs smoothly. 
While the test threads execute whatever they are being put up to the starting thread waits at

`allDone.await(maxTimeoutSeconds, TimeUnit.SECONDS);`

. It is possible the await fails with an Exception when the timeout is reached. 
This could be caused by a deadlock, thread starvation, other processing on the testing machine or even by the timeout being too short. 
The thread pool is stopped in any case – even when we got a timeout. 
At the end we check if any thread has aborted with an exception.

## Finding the bug

After explaining the used testing code I think I should get back to the actual problem – writing a unit test that proofs a concurrency issue. 
With above method doing the hard work I had to write a test that fails. 
To begin with I wrote a test that uses all layers – reading a value from the database, process it and create the xml that would be transformed into a html page. 
Bang – 20 out of 100 tests failed with an exception – jupie. 
Next I tried to narrow the possible causes. 
After replacing the db layer with a mock I was still able to reproduce the bug. 
Then some exception traces – but not all – pointed to some formatting code – gotcha.

## The issue and the solution

In this case a developer had subclassed SimpleDateFormat. 
I still do not understand why the jdk developers did not make SimpleDateFormat thread safe but it is as it is. 
After modifying the unit test to only test the formatter and still failing I was able to replace the static held formatter by a ThreadLocal holding a formatter per thread. 
Thread safety is in this case achieved through thread confinement – each thread has his own unsafe formatter in the ThreadLocal – this way no synchronization is needed which would hinder performance.

## Review

With the steps I described it was quiet easy to find this issue. 
But I still have to note that finding a concurrency issue needs luck, intuition and knowledge. 
Even if you write a test as I did it may be it will not deadlock or not cause your issue as in production. 
Perhaps you must include random waits to produce the issue. 
It can even be that your code will only fail on some special multicore system running some special os. 
At least for me the described approach worked many times and I hope it will help you too.

