# Questions and Exercises: Aggregate Operations _问题和练习：聚合操作_


## Questions


* A sequence of aggregate operations is known as a ___ .

* 一系列聚合操作称为 ___ 。

* Each pipeline contains zero or more ___ operations.

* 每个管道包含零个或多个 ___ 操作。

* Each pipeline ends with a ___ operation.

* 每个管道都以 ___ 操作结束。

* What kind of operation produces another stream as its output?

* 什么样的操作会产生另一个流作为其输出？

* Describe one way in which the `forEach` aggregate operation differs from the enhanced `for` statement or iterators.

* 描述 `forEach` 聚合操作与增强的 `for` 语句或迭代器不同的一种方式。

* True or False: A stream is similar to a collection in that it is a data structure that stores elements.

* 判断题：流类似于集合，因为它是存储元素的数据结构。

* Identify the intermediate and terminal operations in this code: 

    ```text
    double average = roster
        .stream()
        .filter(p -> p.getGender() == Person.Sex.MALE)
        .mapToInt(Person::getAge)
        .average()
        .getAsDouble();
    ```

* 识别这段代码中的中间操作和终端操作：

    ```text
    double average = roster
        .stream()
        .filter(p -> p.getGender() == Person.Sex.MALE)
        .mapToInt(Person::getAge)
        .average()
        .getAsDouble();
    ```

* The code `p -> p.getGender() == Person.Sex.MALE` is an example of what?

* 代码 `p -> p.getGender() == Person.Sex.MALE` 就是一个例子？

* The code `Person::getAge` is an example of what?

* 代码 `Person::getAge` 就是一个例子？

* Terminal operations that combine the contents of a stream and return one value are known as what?

* 结合流的内容并返回一个值的终端操作称为什么？

* Name one important difference between the `Stream.reduce` method and the `Stream.collect` method.

* 列举 `Stream.reduce` 方法和 `Stream.collect` 方法之间的一个重要区别。

* If you wanted to process a stream of names, extract the male names, and store them in a new `List`, would `Stream.reduce` or `Stream.collect` be the most appropriate operation to use?

* 如果你想处理一个名字流，提取男性名字，并将它们存储在一个新的 `List` 中， `Stream.reduce` 或 `Stream.collect` 是最适合使用的操作吗？

* True or False: Aggregate operations make it possible to implement parallelism with non-thread-safe collections.

* 对或错：聚合操作使得使用非线程安全集合实现并行性成为可能。

* Streams are always serial unless otherwise specified. 
  How do you request that a stream be processed in parallel? 

* 除非另有说明，流始终是串行的。
  您如何请求并行处理流？


## Exercises


* Write the following enhanced `for` statement as a pipeline with lambda expressions. 
  Hint: Use the `filter` intermediate operation and the `forEach` terminal operation. 

    ```text
    for (Person p : roster) {
        if (p.getGender() == Person.Sex.MALE) {
            System.out.println(p.getName());
        }
    }
    ```

* 将以下增强的 `for` 语句编写为带有 lambda 表达式的管道。
  提示：使用 `filter` 中间操作和 `forEach` 终端操作。

    ```text
    for (Person p : roster) {
        if (p.getGender() == Person.Sex.MALE) {
            System.out.println(p.getName());
        }
    }
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

* 将以下代码转换为使用 lambda 表达式和聚合操作而不是嵌套的 `for` 循环的新实现。
  提示：创建一个按顺序调用 `filter`、 `sorted` 和 `collect` 操作的管道。

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


[Check your answers.](https://docs.oracle.com/javase/tutorial/collections/streams/QandE/answers.html)

[Check your answers.](./answers.md)
