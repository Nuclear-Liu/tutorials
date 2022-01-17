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


_pipeline_ 是一系列聚合操作。
以下示例使用由聚合操作 `filter` 和 `forEach` 组成的管道打印集合 `roster` 中包含的男性成员：


```text
roster
    .stream()
    .filter(e -> e.getGender() == Person.Sex.MALE)
    .forEach(e -> System.out.println(e.getName()));
```


Compare this example to the following that prints the male members contained in the collection `roster` with a for-each loop:


将此示例与以下示例进行比较，该示例使用 for-each 循环打印集合 `roster` 中包含的男性成员：


```text
for (Person p : roster) {
    if (p.getGender() == Person.Sex.MALE) {
        System.out.println(p.getName());
    }
}
```


A pipeline contains the following components:


管道包含以下组件：


* A source: This could be a collection, an array, a generator function, or an I/O channel. 
  In this example, the source is the collection `roster`.

* 一个源： 这可以是一个集合、一个数组、一个生成器函数或一个 I/O 通道。
  在此示例中，源是集合 `roster` 。

* Zero or more _intermediate operations_. 
  An intermediate operation, such as `filter`, produces a new stream.

  A _stream_ is a sequence of elements. 
  Unlike a collection, it is not a data structure that stores elements. 
  Instead, a stream carries values from a source through a pipeline. 
  This example creates a stream from the collection `roster` by invoking the method `stream`.

  The `filter` operation returns a new stream that contains elements that match its predicate (this operation's parameter). 
  In this example, the predicate is the lambda expression `e -> e.getGender() == Person.Sex.MALE`. 
  It returns the boolean value `true` if the `gender` field of object `e` has the value `Person.Sex.MALE`. 
  Consequently, the `filter` operation in this example returns a stream that contains all male members in the collection `roster`.

* 零个或多个_中间操作_。
  中间操作，例如 `filter` ，会产生一个新的流。

  _stream_ 是一系列元素。
  与集合不同，它不是存储元素的数据结构。
  相反，流通过管道携带来自源的值。
  此示例通过调用方法 `stream` 从集合 `roster` 创建一个流。

  `filter` 操作返回一个新流，其中包含与其谓词（此操作的参数）匹配的元素。
  在此示例中，谓词是 lambda 表达式 `e -> e.getGender() == Person.Sex.MALE` 。
  如果对象 `e` 的 `gender` 字段的值为 `Person.Sex.MALE`，则返回布尔值 `true` 。
  因此，本例中的 `filter` 操作返回一个包含 `roster` 集合中所有男性成员的流。

* A _terminal operation_. 
  A terminal operation, such as `forEach`, produces a non-stream result, such as a primitive value (like a double value), a collection, or in the case of `forEach`, no value at all. 
  In this example, the parameter of the `forEach` operation is the lambda expression `e -> System.out.println(e.getName())`, which invokes the method `getName` on the object `e`. 
  (The Java runtime and compiler infer that the type of the object `e` is `Person`.)

* 一个*终端操作*。
  终端操作，例如 `forEach` ，会产生非流结果，例如原始值（如双精度值）、集合，或者在 `forEach` 的情况下，根本没有值。
  在这个例子中， `forEach` 操作的参数是 lambda 表达式 `e -> System.out.println(e.getName())` ，它调用对象 `e` 上的 `getName` 方法。
  （Java 运行时和编译器推断对象 `e` 的类型是 `Person` 。）


The following example calculates the average age of all male members contained in the collection `roster` with a pipeline that consists of the aggregate operations `filter`, `mapToInt`, and `average`:


以下示例使用由聚合操作 `filter`、 `mapToInt` 和 `average` 组成的管道计算集合 `roster` 中包含的所有男性成员的平均年龄：


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


`mapToInt` 操作返回一个类型为 `IntStream` 的新流（这是一个仅包含整数值的流）。
该操作将其参数中指定的函数应用于特定流中的每个元素。
在这个例子中，函数是 `Person::getAge` ，它是一个返回成员年龄的方法引用。
（或者，您可以使用 lambda 表达式 `e -> e.getAge()` 。）
因此，本例中的 `mapToInt` 操作返回一个流，其中包含集合 `roster` 中所有男性成员的年龄。


The `average` operation calculates the average value of the elements contained in a stream of type `IntStream`. 
It returns an object of type `OptionalDouble`. 
If the stream contains no elements, then the `average` operation returns an empty instance of `OptionalDouble`, and invoking the method `getAsDouble` throws a `NoSuchElementException`. 
The JDK contains many terminal operations such as `average` that return one value by combining the contents of a stream. 
These operations are called reduction operations; see the section [Reduction](https://docs.oracle.com/javase/tutorial/collections/streams/reduction.html) for more information.


`average` 操作计算包含在 `IntStream` 类型流中的元素的平均值。
它返回一个 `OptionalDouble` 类型的对象。
如果流不包含任何元素，则 `average` 操作会返回 `OptionalDouble` 的空实例，并且调用 `getAsDouble` 方法会抛出 `NoSuchElementException` 。
JDK 包含许多终端操作，例如通过组合流的内容返回一个值的 `average` 。
这些操作称为归约操作；有关详细信息，请参阅 [Reduction](./reduction.md) 部分。


## Differences Between Aggregate Operations and Iterators _聚合操作和迭代器之间的区别_


Aggregate operations, like `forEach`, appear to be like iterators. 
However, they have several fundamental differences:


聚合操作，如 `forEach` ，看起来就像迭代器。
但是，它们有几个根本区别：


* **They use internal iteration**: 
  Aggregate operations do not contain a method like `next` to instruct them to process the next element of the collection. 
  With _internal delegation_, your application determines _what_ collection it iterates, but the JDK determines _how_ to iterate the collection. 
  With _external iteration_, your application determines both what collection it iterates and how it iterates it. 
  However, external iteration can only iterate over the elements of a collection sequentially. 
  Internal iteration does not have this limitation. 
  It can more easily take advantage of parallel computing, which involves dividing a problem into subproblems, solving those problems simultaneously, and then combining the results of the solutions to the subproblems. 
  See the section [Parallelism](https://docs.oracle.com/javase/tutorial/collections/streams/parallelism.html) for more information.

* **他们使用内部迭代**：
  聚合操作不包含像 `next` 这样的方法来指示它们处理集合的下一个元素。
  使用*内部代表*，您的应用程序确定它迭代的*哪个*集合，但JDK 确定*如何*迭代集合。
  使用*外部迭代*，您的应用程序可以确定它迭代的集合以及它如何迭代它。
  但是，外部迭代只能按顺序迭代集合的元素。
  内部迭代没有这个限制。
  它可以更轻松地利用并行计算，其中包括将问题划分为子问题，同时解决这些问题，然后将解决方案的结果组合到子问题中。
  有关详细信息，请参阅 [Parallelism](./parallelism.md) 部分。

* **They process elements from a stream**: 
  Aggregate operations process elements from a stream, not directly from a collection. 
  Consequently, they are also called _stream operations_.

* **他们处理流中的元素**：
  聚合操作处理来自流的元素，而不是直接来自集合。
  因此，它们也被称为*流操作*。

* **They support behavior as parameters**: 
  You can specify [lambda expressions](https://docs.oracle.com/javase/tutorial/java/javaOO/lambdaexpressions.html) as parameters for most aggregate operations. 
  This enables you to customize the behavior of a particular aggregate operation.

* **它们支持作为参数的行为**：
  您可以将 [lambda expressions](./../../java/javaOO/lambdaexpressions.md) 指定为大多数聚合操作的参数。
  这使您能够自定义特定聚合操作的行为。
