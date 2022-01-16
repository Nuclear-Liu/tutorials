# Lesson: Aggregate Operations _聚合操作_


* [Reduction](https://docs.oracle.com/javase/tutorial/collections/streams/reduction.html)

* [Reduction](./reduction.md)

* [Parallelism](https://docs.oracle.com/javase/tutorial/collections/streams/parallelism.html)

* [Parallelism](./parallelism.md)

* [Questions and Exercises](https://docs.oracle.com/javase/tutorial/collections/streams/QandE/questions.html)

* [Questions and Exercises](./QandE/questions.md)


**Note**: To better understand the concepts in this section, review the sections [Lambda Expressions](https://docs.oracle.com/javase/tutorial/java/javaOO/lambdaexpressions.html) and [Method References](https://docs.oracle.com/javase/tutorial/java/javaOO/methodreferences.html).


**注意**：为了更好地理解本节中的概念，请查看 [Lambda Expressions](./../../java/javaOO/lambdaexpressions.md) 和 [Method References](./../../java/javaOO/methodreferences.md) 部分。


For what do you use collections? 
You don't simply store objects in a collection and leave them there. 
In most cases, you use collections to retrieve items stored in them.


你用什么集合？
您不只是将对象存储在集合中并将它们留在那里。
在大多数情况下，您使用集合来检索存储在其中的名目。


Consider again the scenario described in the section [Lambda Expressions](https://docs.oracle.com/javase/tutorial/java/javaOO/lambdaexpressions.html). 
Suppose that you are creating a social networking application. 
You want to create a feature that enables an administrator to perform any kind of action, such as sending a message, on members of the social networking application that satisfy certain criteria.


再次考虑 [Lambda Expressions](./../../java/javaOO/lambdaexpressions.md) 部分中描述的场景。
假设您正在创建一个社交网络应用程序
您希望创建一个功能，使管理员能够对满足特定条件的社交网络应用程序的成员执行任何类型的操作，例如发送消息。


As before, suppose that members of this social networking application are represented by the following [`Person`](https://docs.oracle.com/javase/tutorial/collections/streams/examples/Person.java) class:


如前所述，假设此社交网络应用程序的成员由以下 [`Person`](./examples/Person.java) 类表示：


```java
public class Person {

    public enum Sex {
        MALE, FEMALE
    }

    String name;
    LocalDate birthday;
    Sex gender;
    String emailAddress;
    
    // ...

    public int getAge() {
        // ...
    }

    public String getName() {
        // ...
    }
}
```


The following example prints the name of all members contained in the collection `roster` with a for-each loop:


以下示例使用 for-each 循环打印集合 `roster` 中包含的所有成员的名称：


```text
for (Person p : roster) {
    System.out.println(p.getName());
}
```


The following example prints all members contained in the collection `roster` but with the aggregate operation `forEach`:


以下示例打印集合 `roster` 中包含但使用聚合操作 `forEach` 的所有成员：


```text
roster
    .stream()
    .forEach(e -> System.out.println(e.getName());
```


Although, in this example, the version that uses aggregate operations is longer than the one that uses a for-each loop, you will see that versions that use bulk-data operations will be more concise for more complex tasks.


虽然在本例中，使用聚合操作的版本比使用 for-each 循环的版本长，但您会看到使用批量数据操作的版本对于更复杂的任务会更简洁。


The following topics are covered:


涵盖以下主题：


* [Pipelines and Streams](https://docs.oracle.com/javase/tutorial/collections/streams/index.html#pipelines)

* [Pipelines and Streams](./index.md#pipelines-and-streams-_pipeline--stream_)

* [Differences Between Aggregate Operations and Iterators](https://docs.oracle.com/javase/tutorial/collections/streams/index.html#differences)

* [Differences Between Aggregate Operations and Iterators](./index.md#differences-between-aggregate-operations-and-iterators-__)


Find the code excerpts described in this section in the example [`BulkDataOperationsExamples`](https://docs.oracle.com/javase/tutorial/collections/streams/examples/BulkDataOperationsExamples.java).


在示例 [`BulkDataOperationsExamples`](./examples/BulkDataOperationsExamples.java) 中查找本节中描述的代码摘录。


## Pipelines and Streams _Pipeline 与 Stream_


A _pipeline_ is a sequence of aggregate operations. 
The following example prints the male members contained in the collection `roster` with a pipeline that consists of the aggregate operations `filter` and `forEach`:


```text
roster
    .stream()
    .filter(e -> e.getGender() == Person.Sex.MALE)
    .forEach(e -> System.out.println(e.getName()));
```


Compare this example to the following that prints the male members contained in the collection `roster` with a for-each loop:


```text
for (Person p : roster) {
    if (p.getGender() == Person.Sex.MALE) {
        System.out.println(p.getName());
    }
}
```


A pipeline contains the following components:

* A source: This could be a collection, an array, a generator function, or an I/O channel. 
  In this example, the source is the collection `roster`.

* Zero or more _intermediate operations_. 
  An intermediate operation, such as `filter`, produces a new stream.

  A _stream_ is a sequence of elements. 
  Unlike a collection, it is not a data structure that stores elements. 
  Instead, a stream carries values from a source through a pipeline. 
  This example creates a stream from the collection `roster` by invoking the method `stream`.

  The `filter` operation returns a new stream that contains elements that match its predicate (this operation's parameter). 
  In this example, the predicate is the lambda expression `e -> e.getGender() == Person.Sex.MALE`. 
  It returns the boolean value `true` if the `gender` field of object e has the value `Person.Sex.MALE`. 
  Consequently, the `filter` operation in this example returns a stream that contains all male members in the collection `roster`.

* A _terminal operation_. 
  A terminal operation, such as `forEach`, produces a non-stream result, such as a primitive value (like a double value), a collection, or in the case of `forEach`, no value at all. 
  In this example, the parameter of the `forEach` operation is the lambda expression `e -> System.out.println(e.getName())`, which invokes the method `getName` on the object `e`. 
  (The Java runtime and compiler infer that the type of the object `e` is `Person`.)


The following example calculates the average age of all male members contained in the collection `roster` with a pipeline that consists of the aggregate operations `filter`, `mapToInt`, and `average`:


```text
double average = roster
    .stream()
    .filter(p -> p.getGender() == Person.Sex.MALE)
    .mapToInt(Person::getAge)
    .average()
    .getAsDouble();
```


The `mapToInt` operation returns a new stream of type `IntStream` (which is a stream that contains only integer values). 
The operation applies the function specified in its parameter to each element in a particular stream. 
In this example, the function is `Person::getAge`, which is a method reference that returns the age of the member. 
(Alternatively, you could use the lambda expression `e -> e.getAge()`.) 
Consequently, the `mapToInt` operation in this example returns a stream that contains the ages of all male members in the collection `roster`.


The `average` operation calculates the average value of the elements contained in a stream of type `IntStream`. 
It returns an object of type `OptionalDouble`. 
If the stream contains no elements, then the `average` operation returns an empty instance of `OptionalDouble`, and invoking the method `getAsDouble` throws a `NoSuchElementException`. 
The JDK contains many terminal operations such as `average` that return one value by combining the contents of a stream. 
These operations are called reduction operations; see the section [Reduction]() for more information.


## Differences Between Aggregate Operations and Iterators _聚合操作和迭代器之间的区别_