# Parallelism _并行性_


Parallel computing involves dividing a problem into subproblems, solving those problems simultaneously (in parallel, with each subproblem running in a separate thread), and then combining the results of the solutions to the subproblems. 
Java SE provides the [fork/join framework](https://docs.oracle.com/javase/tutorial/essential/concurrency/forkjoin.html), which enables you to more easily implement parallel computing in your applications. 
However, with this framework, you must specify how the problems are subdivided (partitioned). 
With aggregate operations, the Java runtime performs this partitioning and combining of solutions for you.


并行计算涉及将问题划分为子问题，同时解决这些问题（并行，每个子问题在单独的线程中运行），然后将解决方案的结果组合到子问题。
Java SE 提供了 [fork/join framework](../../essential/concurrency/forkjoin.md)，它使您能够更轻松地在应用程序中实现并行计算。
但是，使用此框架，您必须指定如何细分（分区）问题。
通过聚合操作，Java 运行时为您执行这种分区和组合解决方案。


One difficulty in implementing parallelism in applications that use collections is that collections are not thread-safe, which means that multiple threads cannot manipulate a collection without introducing [thread interference](https://docs.oracle.com/javase/tutorial/essential/concurrency/interfere.html) or [memory consistency errors](https://docs.oracle.com/javase/tutorial/essential/concurrency/memconsist.html). 
The Collections Framework provides [synchronization wrappers](https://docs.oracle.com/javase/tutorial/collections/implementations/wrapper.html), which add automatic synchronization to an arbitrary collection, making it thread-safe. 
However, synchronization introduces [thread contention](https://docs.oracle.com/javase/tutorial/essential/concurrency/sync.html#thread_contention). 
You want to avoid thread contention because it prevents threads from running in parallel. 
Aggregate operations and parallel streams enable you to implement parallelism with non-thread-safe collections provided that you do not modify the collection while you are operating on it.


在使用集合的应用程序中实现并行性的一个困难是集合不是线程安全的，这意味着多个线程无法在不引入 [thread interference](./../../essential/concurrency/interfere.md) 或 [memory consistency errors](./../../essential/concurrency/memconsist.md) 的情况下操作集合。
Collections Framework 提供了 [synchronization wrappers](./../../collections/implementations/wrapper.md) ，它将自动同步添加到任意集合，使其成为线程安全的。
但是，同步引入了 [thread contention](./../../essential/concurrency/sync.md#thread_contention) 。
您希望避免线程争用，因为它会阻止线程并行运行。
聚合操作和并行流使您能够使用非线程安全集合实现并行性，前提是您在操作集合时不修改集合。


Note that parallelism is not automatically faster than performing operations serially, although it can be if you have enough data and processor cores. 
While aggregate operations enable you to more easily implement parallelism, it is still your responsibility to determine if your application is suitable for parallelism.


请注意，并行性并不会自动比串行执行操作更快，尽管如果您有足够的数据和处理器内核，它可能会更快。
虽然聚合操作使您能够更轻松地实现并行性，但您仍然有责任确定您的应用程序是否适合并行性。


This section covers the following topics:


本节涵盖以下主题：


* [Executing Streams in Parallel](https://docs.oracle.com/javase/tutorial/collections/streams/parallelism.html#executing_streams_in_parallel)

* [Executing Streams in Parallel](./parallelism.md#executing-streams-in-parallel-__)

* [Concurrent Reduction](https://docs.oracle.com/javase/tutorial/collections/streams/parallelism.html#concurrent_reduction)

* [Concurrent Reduction](./parallelism.md#concurrent-reduction-_-reduction_)

* [Ordering](https://docs.oracle.com/javase/tutorial/collections/streams/parallelism.html#ordering)

* [Ordering](./parallelism.md#ordering-__)

* [Side Effects](https://docs.oracle.com/javase/tutorial/collections/streams/parallelism.html#side_effects)

* [Side Effects](./parallelism.md#side-effects-__)

  * [Laziness](https://docs.oracle.com/javase/tutorial/collections/streams/parallelism.html#laziness)
  
  * [Laziness](./parallelism.md#laziness)
  
  * [Interference](https://docs.oracle.com/javase/tutorial/collections/streams/parallelism.html#interference)
    
  * [Interference](./parallelism.md#interference)
  
  * [Stateful Lambda Expressions](https://docs.oracle.com/javase/tutorial/collections/streams/parallelism.html#stateful_lambda_expressions)
  
  * [Stateful Lambda Expressions](./parallelism.md#stateful-lambda-expressions)


You can find the code excerpts described in this section in the example ['ParallelismExamples'](https://docs.oracle.com/javase/tutorial/collections/streams/examples/ParallelismExamples.java).


您可以在示例中找到本节中描述的代码摘录 [`ParallelismExamples`](./examples/ParallelismExamples.java).


## Executing Streams in Parallel _并行执行流_


You can execute streams in serial or in parallel. 
When a stream executes in parallel, the Java runtime partitions the stream into multiple substreams. 
Aggregate operations iterate over and process these substreams in parallel and then combine the results.


您可以串行或并行执行流。
当流并行执行时，Java 运行时会将流划分为多个子流。
聚合操作迭代并并行处理这些子流，然后组合结果。


When you create a stream, it is always a serial stream unless otherwise specified. 
To create a parallel stream, invoke the operation [`Collection.parallelStream`](https://docs.oracle.com/javase/8/docs/api/java/util/Collection.html#parallelStream--). 
Alternatively, invoke the operation [`BaseStream.parallel`](https://docs.oracle.com/javase/8/docs/api/java/util/stream/BaseStream.html#parallel--). 
For example, the following statement calculates the average age of all male members in parallel:


创建流时，除非另有说明，否则它始终是串行流。
要创建并行流，请调用操作 [`Collection.parallelStream`](https://docs.oracle.com/javase/8/docs/api/java/util/Collection.html#parallelStream--) 。
或者，调用操作 [`BaseStream.parallel`](https://docs.oracle.com/javase/8/docs/api/java/util/Collection.html#parallelStream--) 。
例如，以下语句并行计算所有男性成员的平均年龄：


```text
double average = roster
    .parallelStream()
    .filter(p -> p.getGender() == Person.Sex.MALE)
    .mapToInt(Person::getAge)
    .average()
    .getAsDouble();
```


## Concurrent Reduction _并发 Reduction_


Consider again the following example (which is described in the section [Reduction](https://docs.oracle.com/javase/tutorial/collections/streams/reduction.html)) that groups members by gender. 
This example invokes the `collect` operation, which reduces the collection `roster` into a `Map`:


再次考虑以下按性别对成员进行分组的示例（在 [Reduction](./reduction.md) 部分中对此进行了描述）。
此示例调用 `collect` 操作，将集合 `roster` 缩减为 `Map` ：


```text
Map<Person.Sex, List<Person>> byGender =
    roster
        .stream()
        .collect(
            Collectors.groupingBy(Person::getGender));
```


The following is the parallel equivalent:


以下是并行等效项：


```text
ConcurrentMap<Person.Sex, List<Person>> byGender =
    roster
        .parallelStream()
        .collect(
            Collectors.groupingByConcurrent(Person::getGender));
```


This is called a _concurrent reduction_. 
The Java runtime performs a concurrent reduction if all of the following are true for a particular pipeline that contains the `collect` operation:


这称为*并发减少*。
如果对于包含 `collect` 操作的特定管道满足以下所有条件，Java 运行时会执行并发缩减：


* The stream is parallel.

* 流是并行的。

* The parameter of the `collect` operation, the collector, has the characteristic [`Collector.Characteristics.CONCURRENT`](https://docs.oracle.com/javase/8/docs/api/java/util/stream/Collector.Characteristics.html#CONCURRENT). 
  To determine the characteristics of a collector, invoke the [`Collector.characteristics`](https://docs.oracle.com/javase/8/docs/api/java/util/stream/Collector.Characteristics.html) method.

* `collect` 操作的参数，收集器，具有特征 [`Collector.Characteristics.CONCURRENT`](https://docs.oracle.com/javase/8/docs/api/java/util/stream/Collector.Characteristics.html#CONCURRENT) 。
  要确定收集器的特征，请调用 [`Collector.characteristics`](https://docs.oracle.com/javase/8/docs/api/java/util/stream/Collector.Characteristics.html) 方法。

* Either the stream is unordered, or the collector has the characteristic [`Collector.Characteristics.UNORDERED`](https://docs.oracle.com/javase/8/docs/api/java/util/stream/Collector.Characteristics.html#UNORDERED). 
  To ensure that the stream is unordered, invoke the [`BaseStream.unordered`](https://docs.oracle.com/javase/8/docs/api/java/util/stream/BaseStream.html#unordered--) operation.

* 流是无序的，或者收集器具有特征 [`Collector.Characteristics.UNORDERED`](https://docs.oracle.com/javase/8/docs/api/java/util/stream/Collector.Characteristics.html#UNORDERED) 。
  要确保流是无序的，请调用 [`BaseStream.unordered`](https://docs.oracle.com/javase/8/docs/api/java/util/stream/BaseStream.html#unordered--) 操作。


Note: This example returns an instance of [`ConcurrentMap`](https://docs.oracle.com/javase/8/docs/api/java/util/concurrent/ConcurrentMap.html) instead of `Map` and invokes the [`groupingByConcurrent`](https://docs.oracle.com/javase/8/docs/api/java/util/stream/Collectors.html#groupingByConcurrent-java.util.function.Function-) operation instead of `groupingBy`. 
(See the section [Concurrent Collections](https://docs.oracle.com/javase/tutorial/essential/concurrency/collections.html) for more information about `ConcurrentMap`.) Unlike the operation `groupingByConcurrent`, the operation `groupingBy` performs poorly with parallel streams. 
(This is because it operates by merging two maps by key, which is computationally expensive.) Similarly, the operation [`Collectors.toConcurrentMap`](https://docs.oracle.com/javase/8/docs/api/java/util/stream/Collectors.html#toConcurrentMap-java.util.function.Function-java.util.function.Function-) performs better with parallel streams than the operation [`Collectors.toMap`](https://docs.oracle.com/javase/8/docs/api/java/util/stream/Collectors.html#toMap-java.util.function.Function-java.util.function.Function-).


注意：此示例返回 [`ConcurrentMap`](https://docs.oracle.com/javase/8/docs/api/java/util/concurrent/ConcurrentMap.html) 的实例而不是 `Map` 并调用 [`groupingByConcurrent`](https://docs.oracle.com/javase/8/docs/api/java/util/stream/Collectors.html#groupingByConcurrent-java.util.function.Function-) 操作而不是 `groupingBy` 。
（有关 `ConcurrentMap` 的更多信息，请参阅 [Concurrent Collections](https://docs.oracle.com/javase/tutorial/essential/concurrency/collections.html) 部分。）与操作 `groupingByConcurrent` 不同，操作 `groupingBy` 在并行流中表现不佳。
（这是因为它通过键合并两个映射进行操作，这在计算上是昂贵的。）类似地，操作 [`Collectors.toConcurrentMap`](https://docs.oracle.com/javase/8/docs/api/java/util/stream/Collectors.html#toConcurrentMap-java.util.function.Function-java.util.function.Function-) 对并行流的执行比操作 [`Collectors.toMap`](https://docs.oracle.com/javase/8/docs/api/java/util/stream/Collectors.html#toMap-java.util.function.Function-java.util.function.Function-) 更好。


## Ordering _顺序_


The order in which a pipeline processes the elements of a stream depends on whether the stream is executed in serial or in parallel, the source of the stream, and intermediate operations. 
For example, consider the following example that prints the elements of an instance of `ArrayList` with the `forEach` operation several times:


管道处理流元素的顺序取决于流是串行执行还是并行执行、流的来源和中间操作。
例如，考虑以下示例，该示例使用 `forEach` 操作多次打印 `ArrayList` 实例的元素：


```text
Integer[] intArray = {1, 2, 3, 4, 5, 6, 7, 8 };
List<Integer> listOfIntegers =
    new ArrayList<>(Arrays.asList(intArray));

System.out.println("listOfIntegers:");
listOfIntegers
    .stream()
    .forEach(e -> System.out.print(e + " "));
System.out.println("");

System.out.println("listOfIntegers sorted in reverse order:");
Comparator<Integer> normal = Integer::compare;
Comparator<Integer> reversed = normal.reversed(); 
Collections.sort(listOfIntegers, reversed);  
listOfIntegers
    .stream()
    .forEach(e -> System.out.print(e + " "));
System.out.println("");
     
System.out.println("Parallel stream");
listOfIntegers
    .parallelStream()
    .forEach(e -> System.out.print(e + " "));
System.out.println("");
    
System.out.println("Another parallel stream:");
listOfIntegers
    .parallelStream()
    .forEach(e -> System.out.print(e + " "));
System.out.println("");
     
System.out.println("With forEachOrdered:");
listOfIntegers
    .parallelStream()
    .forEachOrdered(e -> System.out.print(e + " "));
System.out.println("");
```


This example consists of five pipelines. 
It prints output similar to the following:


此示例由五个管道组成。
它打印类似于以下内容的输出：


```log
listOfIntegers:
1 2 3 4 5 6 7 8
listOfIntegers sorted in reverse order:
8 7 6 5 4 3 2 1
Parallel stream:
3 4 1 6 2 5 7 8
Another parallel stream:
6 3 1 5 7 8 4 2
With forEachOrdered:
8 7 6 5 4 3 2 1
```


This example does the following:


此示例执行以下操作：


* The first pipeline prints the elements of the list `listOfIntegers` in the order that they were added to the list.

* 第一个管道按照添加到列表中的顺序打印列表 `listOfIntegers` 中的元素。

* The second pipeline prints the elements of `listOfIntegers` after it was sorted by the method [`Collections.sort`](https://docs.oracle.com/javase/8/docs/api/java/util/Collections.html#sort-java.util.List-).

* 第二个管道在通过方法 [`Collections.sort`](https://docs.oracle.com/javase/8/docs/api/java/util/Collections.html#sort-java.util.List-) 排序后打印 `listOfIntegers` 的元素。

* The third and fourth pipelines print the elements of the list in an apparently random order. 
  Remember that stream operations use internal iteration when processing elements of a stream. 
  Consequently, when you execute a stream in parallel, the Java compiler and runtime determine the order in which to process the stream's elements to maximize the benefits of parallel computing unless otherwise specified by the stream operation.

* 第三和第四个管道以明显随机的顺序打印列表的元素。
  请记住，流操作在处理流的元素时使用内部迭代。
  因此，当您并行执行流时，Java 编译器和运行时会确定处理流元素的顺序，以最大限度地发挥并行计算的优势，除非流操作另有规定。

* The fifth pipeline uses the method [`forEachOrdered`](https://docs.oracle.com/javase/8/docs/api/java/util/stream/Stream.html#forEachOrdered-java.util.function.Consumer-), which processes the elements of the stream in the order specified by its source, regardless of whether you executed the stream in serial or parallel. 
  Note that you may lose the benefits of parallelism if you use operations like `forEachOrdered` with parallel streams.

* 第五个管道使用方法 [`forEachOrdered`](https://docs.oracle.com/javase/8/docs/api/java/util/stream/Stream.html#forEachOrdered-java.util.function.Consumer-) ，它按照源指定的顺序处理流的元素，无论您是以串行还是并行方式执行流。
  请注意，如果您将 `forEachOrdered` 之类的操作与并行流一起使用，则可能会失去并行性的好处。


## Side Effects _副作用_


A method or an expression has a side effect if, in addition to returning or producing a value, it also modifies the state of the computer. 
Examples include mutable reductions (operations that use the `collect` operation; see the section [Reduction](https://docs.oracle.com/javase/tutorial/collections/streams/reduction.html) for more information) as well as invoking the `System.out.println` method for debugging. 
The JDK handles certain side effects in pipelines well. 
In particular, the `collect` method is designed to perform the most common stream operations that have side effects in a parallel-safe manner. 
Operations like `forEach` and `peek` are designed for side effects; a lambda expression that returns void, such as one that invokes `System.out.println`, can do nothing but have side effects. 
Even so, you should use the `forEach` and `peek` operations with care; if you use one of these operations with a parallel stream, then the Java runtime may invoke the lambda expression that you specified as its parameter concurrently from multiple threads. 
In addition, never pass as parameters lambda expressions that have side effects in operations such as `filter` and `map`. 
The following sections discuss [interference](https://docs.oracle.com/javase/tutorial/collections/streams/parallelism.html#interference) and [stateful lambda expressions](https://docs.oracle.com/javase/tutorial/collections/streams/parallelism.html#stateful_lambda_expressions), both of which can be sources of side effects and can return inconsistent or unpredictable results, especially in parallel streams. 
However, the concept of [laziness](https://docs.oracle.com/javase/tutorial/collections/streams/parallelism.html#laziness) is discussed first, because it has a direct effect on interference.


如果一个方法或一个表达式除了返回或产生一个值之外，它还修改了计算的状态，它就会产生副作用。
示例包括可变归约（使用 `collect` 操作的操作；有关更多信息，请参阅 [Reduction](./reduction.md) 部分）以及调用 `System.out.println` 方法进行调试。
JDK 很好地处理了管道中的某些副作用。
特别是， `collect` 方法旨在以并行安全的方式执行最常见的具有副作用的流操作。
像 `forEach` 和 `peek` 这样的操作是为副作用而设计的；一个返回 void 的 lambda 表达式，例如调用 `System.out.println` 的表达式，除了有副作用什么都做不了。
即便如此，你还是应该小心使用 `forEach` 和 `peek` 操作；如果您将这些操作之一用于并行流，则 Java 运行时可能会同时从多个线程调用您指定为其参数的 lambda 表达式。
此外，切勿将在 `filter` 和 `map` 等操作中具有副作用的 lambda 表达式作为参数传递。
以下部分讨论 [interference](./parallelism.md#interference) 和 [stateful lambda expressions](./parallelism.md#stateful-lambda-expressions)，它们都可能是副作用的来源，并且可能返回不一致或不可预测的结果，尤其是在并行流中。
但是，首先讨论 [laziness](./parallelism.md#laziness) 的概念，因为它对干扰有直接影响。


### Laziness


All intermediate operations are _lazy_. 
An expression, method, or algorithm is lazy if its value is evaluated only when it is required. 
(An algorithm is eager if it is evaluated or processed immediately.) Intermediate operations are lazy because they do not start processing the contents of the stream until the terminal operation commences. 
Processing streams lazily enables the Java compiler and runtime to optimize how they process streams. 
For example, in a pipeline such as the `filter-mapToInt-average` example described in the section [Aggregate Operations](https://docs.oracle.com/javase/tutorial/collections/streams/index.html), the `average` operation could obtain the first several integers from the stream created by the mapToInt operation, which obtains elements from the filter operation. 
The `average` operation would repeat this process until it had obtained all required elements from the stream, and then it would calculate the average.


所有中间操作都是*lazy*。
如果表达式、方法或算法仅在需要时才计算其值，则它是惰性的。
（如果立即评估或处理算法，则该算法是急切的。）中间操作是惰性的，因为它们在终端操作开始之前不会开始处理流的内容。
延迟处理流使 Java 编译器和运行时能够优化它们处理流的方式。
例如，在 [Aggregate Operations](./index.md) 部分中描述的 `filter-mapToInt-average` 示例这样的管道中，`average` 操作可以从 mapToInt 操作创建的流中获取前几个整数，从而获得过滤操作中的元素。
`average` 操作会重复这个过程，直到它从流中获得所有需要的元素，然后它会计算平均值。


## Interference


Lambda expressions in stream operations should not interfere. 
Interference occurs when the source of a stream is modified while a pipeline processes the stream. 
For example, the following code attempts to concatenate the strings contained in the List `listOfStrings`. 
However, it throws a `ConcurrentModificationException`:


流操作中的 Lambda 表达式不应干扰。
当管道处理流时修改流的源时会发生干扰。
例如，以下代码尝试连接 List `listOfStrings` 中包含的字符串。
但是，它会抛出一个 `ConcurrentModificationException` ：


```text
try {
    List<String> listOfStrings =
        new ArrayList<>(Arrays.asList("one", "two"));
         
    // This will fail as the peek operation will attempt to add the
    // string "three" to the source after the terminal operation has
    // commenced. 
             
    String concatenatedString = listOfStrings
        .stream()
        
        // Don't do this! Interference occurs here.
        .peek(s -> listOfStrings.add("three"))
        
        .reduce((a, b) -> a + " " + b)
        .get();
                 
    System.out.println("Concatenated string: " + concatenatedString);
         
} catch (Exception e) {
    System.out.println("Exception caught: " + e.toString());
}
```


This example concatenates the strings contained in `listOfStrings` into an `Optional<String>` value with the `reduce` operation, which is a terminal operation. 
However, the pipeline here invokes the intermediate operation `peek`, which attempts to add a new element to `listOfStrings`. 
Remember, all intermediate operations are lazy. 
This means that the pipeline in this example begins execution when the operation `get` is invoked, and ends execution when the `get` operation completes. 
The argument of the `peek` operation attempts to modify the stream source during the execution of the pipeline, which causes the Java runtime to throw a `ConcurrentModificationException`.


此示例使用 `reduce` 操作将 `listOfStrings` 中包含的字符串连接成 `Optional<String>` 值，这是一个终端操作。
然而，这里的管道调用了中间操作 `peek` ，它试图向 `listOfStrings` 添加一个新元素。
请记住，所有中间操作都是惰性的。
这意味着此示例中的管道在调用 `get` 操作时开始执行，并在 `get` 操作完成时结束执行。
`peek` 操作的参数试图在管道执行期间修改流源，这会导致 Java 运行时抛出 `ConcurrentModificationException` 。


### Stateful Lambda Expressions


Avoid using _stateful lambda expressions_ as parameters in stream operations. 
A stateful lambda expression is one whose result depends on any state that might change during the execution of a pipeline. 
The following example adds elements from the `List` `listOfIntegers` to a new `List` instance with the `map` intermediate operation. 
It does this twice, first with a serial stream and then with a parallel stream:


避免使用 _有状态的 lambda 表达式_ 作为流操作中的参数。
有状态 lambda 表达式的结果取决于在管道执行期间可能发生变化的任何状态。
以下示例使用 `map` 中间操作将 `List` `listOfIntegers` 中的元素添加到新的 `List` 实例。
它这样做了两次，首先是串行流，然后是并行流：


```text
List<Integer> serialStorage = new ArrayList<>();
     
System.out.println("Serial stream:");
listOfIntegers
    .stream()
    
    // Don't do this! It uses a stateful lambda expression.
    .map(e -> { serialStorage.add(e); return e; })
    
    .forEachOrdered(e -> System.out.print(e + " "));
System.out.println("");
     
serialStorage
    .stream()
    .forEachOrdered(e -> System.out.print(e + " "));
System.out.println("");

System.out.println("Parallel stream:");
List<Integer> parallelStorage = Collections.synchronizedList(
    new ArrayList<>());
listOfIntegers
    .parallelStream()
    
    // Don't do this! It uses a stateful lambda expression.
    .map(e -> { parallelStorage.add(e); return e; })
    
    .forEachOrdered(e -> System.out.print(e + " "));
System.out.println("");
     
parallelStorage
    .stream()
    .forEachOrdered(e -> System.out.print(e + " "));
System.out.println("");
```


The lambda expression `e -> { parallelStorage.add(e); return e; }` is a stateful lambda expression. 
Its result can vary every time the code is run. 
This example prints the following:


lambda 表达式 `e -> { parallelStorage.add(e); return e; }` 是一个有状态的 lambda 表达式。
每次运行代码时，其结果都会有所不同。
此示例打印以下内容：


```log
Serial stream:
8 7 6 5 4 3 2 1
8 7 6 5 4 3 2 1
Parallel stream:
8 7 6 5 4 3 2 1
1 3 6 2 4 5 8 7
```


The operation `forEachOrdered` processes elements in the order specified by the stream, regardless of whether the stream is executed in serial or parallel. 
However, when a stream is executed in parallel, the `map` operation processes elements of the stream specified by the Java runtime and compiler. 
Consequently, the order in which the lambda expression `e -> { parallelStorage.add(e); return e; }` adds elements to the `List` `parallelStorage` can vary every time the code is run. 
For deterministic and predictable results, ensure that lambda expression parameters in stream operations are not stateful.


`forEachOrdered` 操作按照流指定的顺序处理元素，无论流是串行执行还是并行执行。
但是，当并行执行流时， `map` 操作会处理 Java 运行时和编译器指定的流元素。
因此，lambda 表达式 `e -> { parallelStorage.add(e); return e; }` 将元素添加到 `List` `parallelStorage` 可以在每次代码运行时发生变化。
对于确定性和可预测的结果，请确保流操作中的 lambda 表达式参数不是有状态的。


**Note**: This example invokes the method `synchronizedList` so that the `List` `parallelStorage` is thread-safe. 
Remember that collections are not thread-safe. 
This means that multiple threads should not access a particular collection at the same time. 
Suppose that you do not invoke the method `synchronizedList` when creating `parallelStorage`:


**注意**：此示例调用方法 `synchronizedList` 以便 `List` `parallelStorage` 是线程安全的。
请记住，集合不是线程安全的。
这意味着多个线程不应同时访问特定的集合。
假设您在创建 `parallelStorage` 时没有调用 `synchronizedList` 方法：


`List<Integer> parallelStorage = new ArrayList<>();`


The example behaves erratically because multiple threads access and modify `parallelStorage` without a mechanism like synchronization to schedule when a particular thread may access the `List` instance. 
Consequently, the example could print output similar to the following:


该示例的行为不规律，因为多个线程访问和修改 `parallelStorage` 没有像同步这样的机制来安排特定线程何时可以访问 `List` 实例。
因此，该示例可以打印类似于以下内容的输出：


```log
Parallel stream:
8 7 6 5 4 3 2 1
null 3 5 4 7 8 1 2
```
