# Getting Started

In this simple example we are going to write a mock object test for a publish/subscribe message system. 
A Publisher sends messages to zero or more Subscribers. 
We want to test the Publisher, which involves testing its interactions with its Subscribers.

The Subscriber interface looks like this:

```java
interface Subscriber {
    void receive(String message);
}
```

We will test that a Publisher sends a message to a single registered Subscriber. 
To test interactions between the Publisher and the Subscriber we will use a mock Subscriber object.

## Set Up the Class Path

To use `jMock 2.6.1` you must add the following JAR files to your class path:

* jmock-2.6.1.jar
* hamcrest-core-1.3.jar
* hamcrest-library-1.3.jar
* jmock-junit4-2.6.1.jar

## Write the Test Case

First we must import the jMock classes, define our test fixture class and create a "Mockery" that represents the context in which the Publisher exists. 
The context mocks out the objects that the Publisher collaborates with (in this case a Subscriber) and checks that they are used correctly during the test.

```java
import org.jmock.Expectations;
import org.jmock.Mockery;
import org.jmock.integration.junit4.JMock;
import org.jmock.integration.junit4.JUnit4RuleMockery;

public class PublisherTest {
    @Rule public JUnitRuleMockery context = new JUnitRuleMockery();
    // ...
}
```

**Note**: this currently only works with the latest jMock release candidate (2.6.0RC1) and JUnit 4.7 and above.

In older versions of jMock and JUnit 4 you can use the JMock test runner, which is less flexible than the Rules mechanism shown above.

```java
import org.jmock.Expectations;
import org.jmock.Mockery;
import org.jmock.integration.junit4.JMock;
import org.jmock.integration.junit4.JUnit4RuleMockery;

@RunWith(JMock.class)
public class PublisherTest {
    Mockery context = new JUnit4Mockery();
    // ...    
}
```

Now we want to write the method that will perform our test:

```text
@Test 
public void oneSubscriberReceivesAMessage() {
    ...
}
```

We will now write the body of the test method.

We first set up the context in which our test will execute. 
We create a Publisher to test. 
We create a mock Subscriber that should receive the message. 
We then register the Subscriber with the Publisher. 
Finally we create a message object to publish.

```text
final Subscriber subscriber = context.mock(Subscriber.class);

Publisher publisher = new Publisher();
publisher.add(subscriber);

final String message = "message";
```

Next we define [expectations]() on the mock Subscriber that specify the methods that we expect to be called upon it during the test run. 
We expect the receive method to be called once with a single argument, the message that will be sent.

```text
context.checking(new Expectations() {{
    oneOf (subscriber).receive(message);
}});
```

We then execute the code that we want to test.

```text
publisher.publish(message);
```

After the code under test has finished our test must verify that the mock Subscriber was called as expected. 
If the expected calls were not made, the test will fail. 
The JMock test runner does this automatically. 
You don't have to explicitly verify the mock objects in your tests.

Here is the complete test.

```java
import org.jmock.integration.junit4.JMock;
import org.jmock.integration.junit4.JUnit4Mockery;
import org.jmock.Expectations;

@RunWith(JMock.class)
public class PublisherTest {
    Mockery context = new JUnit4Mockery();
    
    @Test 
    public void oneSubscriberReceivesAMessage() {
        // set up
        final Subscriber subscriber = context.mock(Subscriber.class);

        Publisher publisher = new Publisher();
        publisher.add(subscriber);
        
        final String message = "message";
        
        // expectations
        context.checking(new Expectations() {{
            oneOf (subscriber).receive(message);
        }});

        // execute
        publisher.publish(message);
    }
}
```

## Where Next?

The jMock library is explored in more depth in [other Cookbook recipes](). 
The [Cheat Sheet]() is an overview of the entire jMock API.
