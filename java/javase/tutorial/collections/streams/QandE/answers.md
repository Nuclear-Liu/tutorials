# Answers to Questions and Exercises: Aggregate Operations _问题和练习的答案：聚合操作_


## Questions


* Q: A sequence of aggregate operations is known as a ___ .

  A: Pipeline

* Q: 一系列聚合操作称为 ___ 。

  A: Pipeline

* Q: Each pipeline contains zero or more ___ operations.

  A: Intermediate

* Q: 每个管道包含零个或多个 ___ 操作。

  A: Intermediate

* Q: Each pipeline ends with a ___ operation.

  A: Terminal

* Q: 每个管道都以 ___ 操作结束。

  A: Terminal

* Q: What kind of operation produces another stream as its output?

  A: Intermediate

* Q: 什么样的操作会产生另一个流作为其输出？

  A: Intermediate

* Q: Describe one way in which the `forEach` aggregate operation differs from the enhanced `for` statement or iterators.

  A: The `forEach` aggregate operation lets the system decide "how" the iteration takes place. 
  Using aggregate operations lets you focus on "what" instead of "how."

* Q: 描述 `forEach` 聚合操作与增强的 `for` 语句或迭代器不同的一种方式。

  A: `forEach` 聚合操作让系统决定迭代 "how" 发生。
  使用聚合操作可以让您专注于 "what" 而不是 "how" 。

* Q: True or False: A stream is similar to a collection in that it is a data structure that stores elements.

  A: False. 
  Unlike a collection, a stream is not a data structure. 
  It instead carries values from a source through a pipeline.

* Q: True or False: 流类似于集合，因为它是存储元素的数据结构。

  A: False.
  与集合不同，流不是数据结构。
  相反，它通过管道从源中携带值。

* Q: Identify the intermediate and terminal operations in this code:

  ```text
  double average = roster
      .stream()
      .filter(p -> p.getGender() == Person.Sex.MALE)
      .mapToInt(Person::getAge)
      .average()
      .getAsDouble();
  ```

  A: Intermediate: `filter`, `mapToInt`

  Terminal: `average`

  The terminal operation `average` returns an `OptionalDouble`. 
  The `getAsDouble` method is then invoked on that returned object. 
  It is always a good idea to consult the [API Specification](https://docs.oracle.com/javase/8/docs/api/index.html) for information about whether an operation is intermediate or terminal.

* Q: 识别这段代码中的中间操作和终端操作：

  ```text
  double average = roster
      .stream()
      .filter(p -> p.getGender() == Person.Sex.MALE)
      .mapToInt(Person::getAge)
      .average()
      .getAsDouble();
  ```

  A: Intermediate: `filter`, `mapToInt`

  Terminal: `average`

  终端操作 `average` 返回一个 `OptionalDouble` 。
  然后在返回的对象上调用 `getAsDouble` 方法。
  查阅 [API Specification](https://docs.oracle.com/javase/8/docs/api/index.html) 以了解有关操作是中间操作还是终端操作的信息总是一个好主意。

* Q: The code `p -> p.getGender() == Person.Sex.MALE` is an example of what?
  
  A: A lambda expression.

* Q: 代码 `p -> p.getGender() == Person.Sex.MALE` 就是一个什么例子？
  
  A: A lambda expression.

* Q: The code `Person::getAge` is an example of what?
  
  A: A method reference.

* Q: 代码 `Person::getAge` 就是一个什么例子？
  
  A: A method reference.

* Q: Terminal operations that combine the contents of a stream and return one value are known as what?
  
  A: Reduction operations.

* Q: 结合流的内容并返回一个值的终端操作称为什么？
  
  A: Reduction operations.

* Q: Name one important difference between the `Stream.reduce` method and the `Stream.collect` method.
  
  A: `Stream.reduce` always creates a new value when it processes an element. 
  `Stream.collect` modifies (or mutates) the existing value.

* Q: 列举 `Stream.reduce` 方法和 `Stream.collect` 方法之间的一个重要区别。
  
  A: `Stream.reduce` 在处理元素时总是会创建一个新值。
  `Stream.collect` 修改（或改变）现有值。

* Q: If you wanted to process a stream of names, extract the male names, and store them in a new `List`, would `Stream.reduce` or `Stream.collect` be the most appropriate operation to use?
  
  A: The `collect` operation is most appropriate for collecting into a `List`.

  Example:

  ```text
  List<String> namesOfMaleMembersCollect = roster
      .stream()
      .filter(p -> p.getGender() == Person.Sex.MALE)
      .map(p -> p.getName())
      .collect(Collectors.toList());
  ```

* Q: 如果你想处理一个名字流，提取男性名字，并将它们存储在一个新的 `List` 中，`Stream.reduce` 或 `Stream.collect` 是最适合使用的操作吗？
  
  A: `collect` 操作最适合收集到 `List` 中。

  Example:

  ```text
  List<String> namesOfMaleMembersCollect = roster
      .stream()
      .filter(p -> p.getGender() == Person.Sex.MALE)
      .map(p -> p.getName())
      .collect(Collectors.toList());
  ```

* Q: True or False: Aggregate operations make it possible to implement parallelism with non-thread-safe collections.
  
  A: True, provided that you do not modify (mutate) the underlying collection while you are operating on it.

* Q: True or False: 聚合操作使得使用非线程安全集合实现并行性成为可能。
  
  A: True, 前提是您在操作时不修改（变异）基础集合。

* Q: Streams are always serial unless otherwise specified. 
  How do you request that a stream be processed in parallel?

  A: Obtain the parallel stream by invoking `parallelStream()` instead of `stream()`.

* Q: 除非另有说明，流始终是串行的。
  您如何请求并行处理流？

  A: 通过调用 `parallelStream()` 而不是 `stream()` 来获取并行流。


## Exercises


* Exercise: Write the following enhanced `for` statement as a pipeline with lambda expressions. 
  Hint: Use the `filter` intermediate operation and the `forEach` terminal operation. 

  ```text
  for (Person p : roster) {
      if (p.getGender() == Person.Sex.MALE) {
          System.out.println(p.getName());
      }
  }
  ```

  Answer:

  ```text
  roster
      .stream()
      .filter(e -> e.getGender() == Person.Sex.MALE)
      .forEach(e -> System.out.println(e.getName());
  ```

* Exercise: 将以下增强的“for”语句编写为带有 lambda 表达式的管道。
  Hint: 使用 `filter` 中间操作和 `forEach` 终端操作。

  ```text
  for (Person p : roster) {
      if (p.getGender() == Person.Sex.MALE) {
          System.out.println(p.getName());
      }
  }
  ```

  Answer:

  ```text
  roster
      .stream()
      .filter(e -> e.getGender() == Person.Sex.MALE)
      .forEach(e -> System.out.println(e.getName());
  ```

* Convert the following code into a new implementation that uses lambda expressions and aggregate operations instead of nested `for` loops. 
  Hint: Make a pipeline that invokes the `filter`, `sorted`, and `collect` operations, in that order. 

  ```text
  List<Album> favs = new ArrayList<>();
  for (Album a : albums) {
      boolean hasFavorite = false;
      for (Track t : a.tracks) {
          if (t.rating >= 4) {
              hasFavorite = true;
              break;
          }
      }
      if (hasFavorite)
          favs.add(a);
  }
  Collections.sort(favs, new Comparator<Album>() {
                             public int compare(Album a1, Album a2) {
                                 return a1.name.compareTo(a2.name);
                             }});
  ```

  Answer: 

  ```text
  List<Album> sortedFavs =
    albums.stream()
          .filter(a -> a.tracks.anyMatch(t -> (t.rating >= 4)))
          .sorted(Comparator.comparing(a -> a.name))
          .collect(Collectors.toList());
  ```

* 将以下代码转换为使用 lambda 表达式和聚合操作而不是嵌套的 `for` 循环的新实现。
  Hint: 创建一个按顺序调用 `filter` 、 `sorted` 和 `collect` 操作的管道。

  ```text
  List<Album> favs = new ArrayList<>();
  for (Album a : albums) {
      boolean hasFavorite = false;
      for (Track t : a.tracks) {
          if (t.rating >= 4) {
              hasFavorite = true;
              break;
          }
      }
      if (hasFavorite)
          favs.add(a);
  }
  Collections.sort(favs, new Comparator<Album>() {
                             public int compare(Album a1, Album a2) {
                                 return a1.name.compareTo(a2.name);
                             }});
  ```

  Answer: 

  ```text
  List<Album> sortedFavs =
    albums.stream()
          .filter(a -> a.tracks.anyMatch(t -> (t.rating >= 4)))
          .sorted(Comparator.comparing(a -> a.name))
          .collect(Collectors.toList());
  ```


Here we have used the stream operations to simplify each of the three major steps -- identification of whether any track in an album has a rating of at least 4 (`anyMatch`), the sorting, and the collection of albums matching our criteria into a `List`. 
The `Comparator.comparing()` method takes a function that extracts a `Comparable` sort key, and returns a `Comparator` that compares on that key. 


在这里，我们使用流操作来简化三个主要步骤中的每一个——识别专辑中是否有任何曲目的评分至少为 4 ( `anyMatch` )、排序以及符合我们标准的专辑集合到一个 `List` 。
`Comparator.comparing()` 方法采用一个函数，该函数提取 `Comparable` 排序键，并返回一个在该键上进行比较的 `Comparator` 。
