# Reduction


The section [Aggregate Operations](https://docs.oracle.com/javase/tutorial/collections/streams/index.html) describes the following pipeline of operations, which calculates the average age of all male members in the collection `roster`:


[Aggregate Operations](./index.md) 部分描述了以下操作管道，它计算集合 `roster` 中所有男性成员的平均年龄：


```text
double average = roster
    .stream()
    .filter(p -> p.getGender() == Person.Sex.MALE)
    .mapToInt(Person::getAge)
    .average()
    .getAsDouble();
```


The JDK contains many terminal operations (such as [`average`](https://docs.oracle.com/javase/8/docs/api/java/util/stream/IntStream.html#average--java/lang/reflect/Executable.html), [`sum`](https://docs.oracle.com/javase/8/docs/api/java/util/stream/IntStream.html#sum--), [`min`](https://docs.oracle.com/javase/8/docs/api/java/util/stream/Stream.html#min-java.util.Comparator-), [`max`](https://docs.oracle.com/javase/8/docs/api/java/util/stream/Stream.html#max-java.util.Comparator-), and [`count`](https://docs.oracle.com/javase/8/docs/api/java/util/stream/Stream.html#count--)) that return one value by combining the contents of a stream. 
These operations are called reduction operations. 
The JDK also contains reduction operations that return a collection instead of a single value. 
Many reduction operations perform a specific task, such as finding the average of values or grouping elements into categories. 
However, the JDK provides you with the general-purpose reduction operations [`reduce`](https://docs.oracle.com/javase/8/docs/api/java/util/stream/Stream.html#reduce-T-java.util.function.BinaryOperator-) and [`collect`](https://docs.oracle.com/javase/8/docs/api/java/util/stream/Stream.html#collect-java.util.function.Supplier-java.util.function.BiConsumer-java.util.function.BiConsumer-), which this section describes in detail.


JDK包含许多终端操作（如 [`average`](https://docs.oracle.com/javase/8/docs/api/java/util/stream/IntStream.html#average--java/lang/reflect/Executable.html)、 [`sum`](https://docs.oracle.com/javase/8/docs/api/java/util/stream/IntStream.html#sum--)、 [`min`](https://docs.oracle.com/javase/8/docs/api/java/util/stream/Stream.html#min-java.util.Comparator-)、 [`max`](https://docs.oracle.com/javase/8/docs/api/java/util/stream/Stream.html#max-java.util.Comparator-) 和 [`count`](https://docs.oracle.com/javase/8/docs/api/java/util/stream/Stream.html#count--) ）通过组合流的内容返回一个值。
这些操作称为归约操作。
JDK 还包含返回集合而不是单个值的归约操作。
许多归约操作执行特定任务，例如查找值的平均值或将元素分组。
但是，JDK 为您提供了通用的归约操作 [`reduce`](https://docs.oracle.com/javase/8/docs/api/java/util/stream/Stream.html#reduce-T-java.util.function.BinaryOperator-) 和 [`collect`](https://docs.oracle.com/javase/8/docs/api/java/util/stream/Stream.html#collect-java.util.function.Supplier-java.util.function.BiConsumer-java.util.function.BiConsumer-)，本节将详细介绍。


This section covers the following topics:


* [The `Stream.reduce` Method](https://docs.oracle.com/javase/tutorial/collections/streams/reduction.html#reduce)

* [The `Stream.reduce` Method](./reduction.md#the-streamreduce-method)

* [The `Stream.collect` Method](https://docs.oracle.com/javase/tutorial/collections/streams/reduction.html#collect)

* [The `Stream.collect` Method](./reduction.md#the-streamcollect-method)


You can find the code excerpts described in this section in the example [`ReductionExamples`](https://docs.oracle.com/javase/tutorial/collections/streams/examples/ReductionExamples.java).


您可以在示例 [`ReductionExamples`](./examples/ReductionExamples.java) 中找到本节中描述的代码摘录。


## The `Stream.reduce `Method


The [`Stream.reduce`](https://docs.oracle.com/javase/8/docs/api/java/util/stream/Stream.html#reduce-T-java.util.function.BinaryOperator-) method is a general-purpose reduction operation. 
Consider the following pipeline, which calculates the sum of the male members' ages in the collection `roster`. 
It uses the [`Stream.sum`](https://docs.oracle.com/javase/8/docs/api/java/util/stream/IntStream.html#sum--) reduction operation:


[`Stream.reduce`](https://docs.oracle.com/javase/8/docs/api/java/util/stream/Stream.html#reduce-T-java.util.function.BinaryOperator-) 方法是一种通用的归约操作。
考虑以下管道，它计算集合 `roster` 中男性成员的年龄总和。
它使用 [`Stream.sum`](https://docs.oracle.com/javase/8/docs/api/java/util/stream/IntStream.html#sum--) 归约操作：


```text
Integer totalAge = roster
    .stream()
    .mapToInt(Person::getAge)
    .sum();
```


Compare this with the following pipeline, which uses the `Stream.reduce` operation to calculate the same value:


将此与以下管道进行比较，后者使用 `Stream.reduce` 操作来计算相同的值：


```text
Integer totalAgeReduce = roster
   .stream()
   .map(Person::getAge)
   .reduce(
       0,
       (a, b) -> a + b);
```


The `reduce` operation in this example takes two arguments:


本例中的 `reduce` 操作有两个参数：


* `identity`: 
  The identity element is both the initial value of the reduction and the default result if there are no elements in the stream. 
  In this example, the identity element is `0`; this is the initial value of the sum of ages and the default value if no members exist in the collection `roster`.

* `identity`: 
  标识元素既是归约的初始值，也是流中没有元素时的默认结果。
  在此示例中，标识元素为 `0` ；这是年龄总和的初始值，如果集合 `roster` 中不存在任何成员，则为默认值。

* `accumulator`: 
  The `accumulator` function takes two parameters: a partial result of the reduction (in this example, the sum of all processed integers so far) and the next element of the stream (in this example, an integer). 
  It returns a new partial result. 
  In this example, the accumulator function is a lambda expression that adds two `Integer` values and returns an `Integer` value:

    `(a, b) -> a + b`

* `accumulator`:
  `accumulator` 函数有两个参数：归约的部分结果（在此示例中，为到目前为止所有已处理整数的总和）和流的下一个元素（在此示例中为整数）。
  它返回一个新的部分结果。
  在此示例中，累加器函数是一个 lambda 表达式，它将两个 `Integer` 值相加并返回一个 `Integer` 值：

    `(a, b) -> a + b`


The `reduce` operation always returns a new value. 
However, the accumulator function also returns a new value every time it processes an element of a stream. 
Suppose that you want to reduce the elements of a stream to a more complex object, such as a collection. 
This might hinder the performance of your application. 
If your `reduce` operation involves adding elements to a collection, then every time your accumulator function processes an element, it creates a new collection that includes the element, which is inefficient. 
It would be more efficient for you to update an existing collection instead. 
You can do this with the [`Stream.collect`](https://docs.oracle.com/javase/8/docs/api/java/util/stream/Stream.html#collect-java.util.function.Supplier-java.util.function.BiConsumer-java.util.function.BiConsumer-) method, which the next section describes.


`reduce` 操作总是返回一个新值。
但是，累加器函数在每次处理流的元素时也会返回一个新值。
假设您要将流的元素简化为更复杂的对象，例如集合。
这可能会妨碍您的应用程序的性能。
如果您的 `reduce` 操作涉及将元素添加到集合中，那么每次您的累加器函数处理一个元素时，它都会创建一个包含该元素的新集合，这是低效的。
相反，更新现有集合对您来说会更有效。
您可以使用 [`Stream.collect`](https://docs.oracle.com/javase/8/docs/api/java/util/stream/Stream.html#collect-java.util.function.Supplier-java.util.function.BiConsumer-java.util.function.BiConsumer-) 方法执行此操作，下一节将对此进行介绍。


## The `Stream.collect` Method


Unlike the `reduce` method, which always creates a new value when it processes an element, the [`collect`](https://docs.oracle.com/javase/8/docs/api/java/util/stream/Stream.html#collect-java.util.function.Supplier-java.util.function.BiConsumer-java.util.function.BiConsumer-) method modifies, or mutates, an existing value.


与在处理元素时总是创建新值的 `reduce` 方法不同， [`collect`](https://docs.oracle.com/javase/8/docs/api/java/util/stream/Stream.html#collect-java.util.function.Supplier-java.util.function.BiConsumer-java.util.function.BiConsumer-) 方法修改或改变现有值。


Consider how to find the average of values in a stream. 
You require two pieces of data: the total number of values and the sum of those values. 
However, like the `reduce` method and all other reduction methods, the `collect` method returns only one value. 
You can create a new data type that contains member variables that keep track of the total number of values and the sum of those values, such as the following class, [`Averager`](https://docs.oracle.com/javase/tutorial/collections/streams/examples/Averager.java):


考虑如何找到流中值的平均值。
您需要两条数据：值的总数和这些值的总和。
但是，与 `reduce` 方法和所有其他归约方法一样， `collect` 方法只返回一个值。
您可以创建一个包含成员变量的新数据类型，这些变量会跟踪值的总数和这些值的总和，例如以下类 [`Averager`](./examples/Averager.java)：


```java
class Averager implements IntConsumer
{
    private int total = 0;
    private int count = 0;
        
    public double average() {
        return count > 0 ? ((double) total)/count : 0;
    }
        
    public void accept(int i) { total += i; count++; }
    public void combine(Averager other) {
        total += other.total;
        count += other.count;
    }
}
```


The following pipeline uses the `Averager` class and the `collect` method to calculate the average age of all male members:


以下管道使用 `Average` 类和 `collect` 方法计算所有男性成员的平均年龄：


```text
Averager averageCollect = roster.stream()
    .filter(p -> p.getGender() == Person.Sex.MALE)
    .map(Person::getAge)
    .collect(Averager::new, Averager::accept, Averager::combine);
                   
System.out.println("Average age of male members: " +
    averageCollect.average());
```


The `collect` operation in this example takes three arguments:


本例中的 `collect` 操作采用三个参数：


* `supplier`: 
  The `supplier` is a factory function; it constructs new instances. 
  For the `collect` operation, it creates instances of the result container. 
  In this example, it is a new instance of the `Averager` class.

* `supplier`:
  `supplier` 是一个工厂函数；它构造新的实例。
  对于 `collect` 操作，它会创建结果容器的实例。
  在这个例子中，它是 `Averager` 类的一个新实例。

* `accumulator`: 
  The `accumulator` function incorporates a stream element into a result container. 
  In this example, it modifies the `Averager` result container by incrementing the `count` variable by one and adding to the `total` member variable the value of the stream element, which is an integer representing the age of a male member.

* `accumulator`:
  `accumulator` 函数将流元素合并到结果容器中。
  在这个例子中，它修改了 `Averager` 结果容器，将“count”变量加一，并将流元素的值加到“total”成员变量中，该值是一个表示男性成员年龄的整数。

* `combiner`: 
  The `combiner` function takes two result containers and merges their contents. 
  In this example, it modifies an `Averager` result container by incrementing the `count` variable by the `count` member variable of the other `Averager` instance and adding to the `total` member variable the value of the other `Averager` instance's `total` member variable.

* `combiner`:
  `combiner` 函数接受两个结果容器并合并它们的内容。
  在此示例中，它通过将 `count` 变量增加另一个 `Averager` 实例的 `count` 成员变量并将另一个 `Averager` 的值添加到 `total` 成员变量来修改 `Averager` 结果容器实例的 `total` 成员变量。


Note the following:


请注意以下事项：


* The `supplier` is a lambda expression (or a method reference) as opposed to a value like the identity element in the `reduce` operation.

* `supplier` 是一个 lambda 表达式（或方法引用），而不是像 `reduce` 操作中的标识元素这样的值。

* The `accumulator` and `combiner` functions do not return a value.

* `accumulator` 和 `combiner` 函数不返回值。

* You can use the `collect` operations with parallel streams; see the section [Parallelism](https://docs.oracle.com/javase/tutorial/collections/streams/parallelism.html) for more information. 
  (If you run the `collect` method with a parallel stream, then the JDK creates a new thread whenever the combiner function creates a new object, such as an `Averager` object in this example. 
  Consequently, you do not have to worry about synchronization.)

* 您可以对并行流使用“收集”操作；有关详细信息，请参阅 [Parallelism](./parallelism.md) 部分。
  (如果您使用并行流运行 `collect` 方法，则只要组合器函数创建一个新对象（例如本例中的 `Averager` 对象），JDK 就会创建一个新线程。
  因此，您不必担心同步问题。)


Although the JDK provides you with the `average` operation to calculate the average value of elements in a stream, you can use the `collect` operation and a custom class if you need to calculate several values from the elements of a stream.


尽管 JDK 为您提供了 `average` 操作来计算流中元素的平均值，但如果您需要从流中的元素计算多个值，您可以使用 `collect` 操作和自定义类。


The `collect` operation is best suited for collections. 
The following example puts the names of the male members in a collection with the `collect` operation:


`collect` 操作最适合收集。
以下示例使用 `collect` 操作将男性成员的姓名放入集合中：


```text
List<String> namesOfMaleMembersCollect = roster
    .stream()
    .filter(p -> p.getGender() == Person.Sex.MALE)
    .map(p -> p.getName())
    .collect(Collectors.toList());
```


This version of the `collect` operation takes one parameter of type [`Collector`](https://docs.oracle.com/javase/8/docs/api/java/util/stream/Collector.html). 
This class encapsulates the functions used as arguments in the `collect` operation that requires three arguments (`supplier`, `accumulator`, and `combiner` functions).


此版本的 `collect` 操作采用 [`Collector`](https://docs.oracle.com/javase/8/docs/api/java/util/stream/Collector.html) 类型的一个参数。
此类封装在需要三个参数（ `supplier`、 `accumulator`和 `combiner` 函数）的 `collect` 操作中用作参数的函数。


The [`Collectors`](https://docs.oracle.com/javase/8/docs/api/java/util/stream/Collectors.html) class contains many useful reduction operations, such as accumulating elements into collections and summarizing elements according to various criteria. 
These reduction operations return instances of the class `Collector`, so you can use them as a parameter for the `collect` operation.


[`Collectors`](https://docs.oracle.com/javase/8/docs/api/java/util/stream/Collectors.html) 类包含许多有用的归约操作，例如将元素累积到集合中以及根据各种标准汇总元素。
这些归约操作返回 `Collector` 类的实例，因此您可以将它们用作 `collect` 操作的参数。


This example uses the [`Collectors.toList`](https://docs.oracle.com/javase/8/docs/api/java/util/stream/Collectors.html#toList--) operation, which accumulates the stream elements into a new instance of `List`. 
As with most operations in the `Collectors` class, the `toList` operator returns an instance of `Collector`, not a collection.


此示例使用 [`Collectors.toList`](https://docs.oracle.com/javase/8/docs/api/java/util/stream/Collectors.html#toList--) 操作，它将流元素累积到 `List` 的新实例中。
与 `Collectors` 类中的大多数操作一样， `toList` 运算符返回 `Collector` 的实例，而不是集合。


The following example groups members of the collection `roster` by gender:


以下示例按性别对集合 `roster` 的成员进行分组：


```text
Map<Person.Sex, List<Person>> byGender =
    roster
        .stream()
        .collect(
            Collectors.groupingBy(Person::getGender));
```


The [`groupingBy`](https://docs.oracle.com/javase/8/docs/api/java/util/stream/Collectors.html#groupingBy-java.util.function.Function-) operation returns a map whose keys are the values that result from applying the lambda expression specified as its parameter (which is called a _classification function_). 
In this example, the returned map contains two keys, `Person.Sex.MALE` and `Person.Sex.FEMALE`. 
The keys' corresponding values are instances of `List` that contain the stream elements that, when processed by the classification function, correspond to the key value. 
For example, the value that corresponds to key `Person.Sex.MALE` is an instance of `List` that contains all male members.


[`groupingBy`](https://docs.oracle.com/javase/8/docs/api/java/util/stream/Collectors.html#groupingBy-java.util.function.Function-) 操作返回一个映射，其键是应用指定为其参数的 lambda 表达式（称为*分类函数*）所产生的值。
在此示例中，返回的映射包含两个键， `Person.Sex.MALE` 和 `Person.Sex.FEMALE` 。
键的对应值是 `List` 的实例，其中包含流元素，当分类函数处理时，这些元素对应于键值。
例如，键 `Person.Sex.MALE` 对应的值是包含所有男性成员的 `List` 的实例。


The following example retrieves the names of each member in the collection `roster` and groups them by gender:


以下示例检索集合 `roster` 中每个成员的名称，并按性别对它们进行分组：


```text
Map<Person.Sex, List<String>> namesByGender =
    roster
        .stream()
        .collect(
            Collectors.groupingBy(
                Person::getGender,                      
                Collectors.mapping(
                    Person::getName,
                    Collectors.toList())));
```


The [`groupingBy`](https://docs.oracle.com/javase/8/docs/api/java/util/stream/Collectors.html#groupingBy-java.util.function.Function-java.util.stream.Collector-) operation in this example takes two parameters, a classification function and an instance of `Collector`. 
The `Collector` parameter is called a _downstream collector_. 
This is a collector that the Java runtime applies to the results of another collector. 
Consequently, this `groupingBy` operation enables you to apply a `collect` method to the `List` values created by the `groupingBy` operator. 
This example applies the collector mapping, which applies the [`mapping`](https://docs.oracle.com/javase/8/docs/api/java/util/stream/Collectors.html#mapping-java.util.function.Function-java.util.stream.Collector-java.util.stream.Collector-) function `Person::getName` to each element of the stream. 
Consequently, the resulting stream consists of only the names of members. 
A pipeline that contains one or more downstream collectors, like this example, is called a multilevel reduction.


这个例子中的 [`groupingBy`](https://docs.oracle.com/javase/8/docs/api/java/util/stream/Collectors.html#groupingBy-java.util.function.Function-java.util.stream.Collector-) 操作有两个参数，一个分类函数和一个 `Collector` 的实例。
`Collector` 参数称为 _下游收集器_。
这是一个收集器，Java 运行时将其应用于另一个收集器的结果。
因此，此 `groupingBy` 操作使您可以将 `collect` 方法应用于由 `groupingBy` 运算符创建的 `List` 值。
此示例应用收集器映射，它将 [`mapping`](https://docs.oracle.com/javase/8/docs/api/java/util/stream/Collectors.html#mapping-java.util.function.Function-java.util.stream.Collector-java.util.stream.Collector-) 函数 `Person::getName` 应用于流的每个元素。
因此，生成的流仅包含成员的名称。
包含一个或多个下游收集器的管道（如此示例）称为多级归约。


The following example retrieves the total age of members of each gender:


以下示例检索每种性别成员的总年龄：


```text
Map<Person.Sex, Integer> totalAgeByGender =
    roster
        .stream()
        .collect(
            Collectors.groupingBy(
                Person::getGender,                      
                Collectors.reducing(
                    0,
                    Person::getAge,
                    Integer::sum)));
```


The [`reducing`](https://docs.oracle.com/javase/8/docs/api/java/util/stream/Collectors.html#reducing-U-java.util.function.Function-java.util.function.BinaryOperator-) operation takes three parameters:


[`reducing`](https://docs.oracle.com/javase/8/docs/api/java/util/stream/Collectors.html#reducing-U-java.util.function.Function-java.util.function.BinaryOperator-) 操作采用三个参数：


* `identity`: 
  Like the `Stream.reduce` operation, the identity element is both the initial value of the reduction and the default result if there are no elements in the stream. 
  In this example, the identity element is `0`; this is the initial value of the sum of ages and the default value if no members exist.

* `identity`:
  与 `Stream.reduce` 操作一样，标识元素既是归约的初始值，也是流中没有元素时的默认结果。
  在此示例中，标识元素为 `0` ；这是年龄总和的初始值，如果不存在成员，则为默认值。

* `mapper`: 
  The `reducing` operation applies this mapper function to all stream elements. 
  In this example, the mapper retrieves the age of each member.

* `mapper`:
  `reducing` 操作将此映射器函数应用于所有流元素。
  在此示例中，映射器检索每个成员的年龄。

* `operation`: 
  The operation function is used to reduce the mapped values. 
  In this example, the operation function adds `Integer` values.

* `operation`:
  操作函数用于减少映射值。
  在此示例中，操作函数添加了 `Integer` 值。


The following example retrieves the average age of members of each gender:


以下示例检索每种性别成员的平均年龄：


```text
Map<Person.Sex, Double> averageAgeByGender = roster
    .stream()
    .collect(
        Collectors.groupingBy(
            Person::getGender,                      
            Collectors.averagingInt(Person::getAge)));
```
