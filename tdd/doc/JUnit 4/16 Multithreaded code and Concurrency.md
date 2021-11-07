# Multithreaded code and concurrency *多线程代码和并发*

> Mincong HUANG edited this page on Feb 16, 2017 · 11 revisions 

> Mincong HUANG 于2017年2月16日编辑本页·11次修改

Java code can be difficult to test for thread safety when multithreading.


多线程时，Java 代码可能很难测试线程安全性。

---

The article at [http://www.planetgeek.ch/2009/08/25/how-to-find-a-concurrency-bug-with-java/](http://www.planetgeek.ch/2009/08/25/how-to-find-a-concurrency-bug-with-java/) describes a method of exposing concurrency bugs that adds a new assertion method `assertConcurrent`.


[http://www.planetgeek.ch/2009/08/25/how-to-find-a-concurrency-bug-with-java/](http://www.planetgeek.ch/2009/08/25/how-to-find-a-concurrency-bug-with-java/) 上的文章描述了一种暴露并发错误的方法，该方法添加了一个新的断言方法 `assertConcurrent`。

---

To use this you pass in a Collection of Runnables that are your arrange\act\assert test on the SUT, they all run at the same time in the `assertConcurrent` method; the chances of triggering a multithreading code error, and thereby failing some assertion are greatly increased:


要使用它，您需要在 SUT 上传递一个 Runnables 集合，这些集合是您在 SUT 上的排列\行为\断言测试，它们都在 `assertConcurrent` 方法中同时运行；触发多线程代码错误的机会大大增加，从而使某些断言失败：

---

The `assertConcurrent` method from the article is:


文章中的 `assertConcurrent` 方法是：

---

```
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
        assertTrue("Timeout initializing threads! Perform long lasting initializations before passing runnables to assertConcurrent", allExecutorThreadsReady.await(runnables.size() * 10, TimeUnit.MILLISECONDS));
        // start all test runners
        afterInitBlocker.countDown();
        assertTrue(message +" timeout! More than" + maxTimeoutSeconds + "seconds", allDone.await(maxTimeoutSeconds, TimeUnit.SECONDS));
    } finally {
        threadPool.shutdownNow();
    }
    assertTrue(message + "failed with exception(s)" + exceptions, exceptions.isEmpty());
}

```

A JUnit extension that generalizes this pattern into a library is [ConcurrentUnit](https://github.com/jhalterman/concurrentunit).


将这种模式推广到库中的 JUnit 扩展是 [ConcurrentUnit](https://github.com/jhalterman/concurrentunit) 。

---

Another article giving an overview of alternative stragies at [http://tempusfugitlibrary.org/recipes/2012/05/20/testing-concurrent-code/]() might also be useful.


另一篇文章 [http:tempusfugitlibrary.orgrecipes20120520testing-concurrent-code]() 概述了替代策略也可能有用。

---

### Java Concurrency Bookshelf *Java 并发书架*

* The Java Memory Model and Thread Specification [JSR-133](http://jcp.org/en/jsr/detail?id=133)
* Threads and Locks, [Java Virtual Machine Specification](http://docs.oracle.com/javase/specs/jvms/se5.0/html/Threads.doc.html)
* The book [Java Concurrency in Practice](http://www.javaconcurrencyinpractice.com/) by Brian Goetz ([Amazon UK](http://tinyurl.com/a98yue3)).
* [Double Checked Locking article](http://www.cs.umd.edu/~pugh/java/memoryModel/DoubleCheckedLocking.html) by Doug Lea
* [Doug Lea's homepage](http://g.oswego.edu/)


* Java 内存模型和线程规范 [JSR-133](http://jcp.org/en/jsr/detail?id=133)
* 线程和锁， [Java Virtual Machine Specification](http://docs.oracle.com/javase/specs/jvms/se5.0/html/Threads.doc.html)
* The book [Java Concurrency in Practice](http://www.javaconcurrencyinpractice.com/) by Brian Goetz ([Amazon UK](http://tinyurl.com/a98yue3)).
* [Double Checked Locking article](http://www.cs.umd.edu/~pugh/java/memoryModel/DoubleCheckedLocking.html) by Doug Lea
* [Doug Lea's homepage](http://g.oswego.edu/)

---
