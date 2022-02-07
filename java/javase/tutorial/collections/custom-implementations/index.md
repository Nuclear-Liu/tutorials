# Lesson: Custom Collection Implementations _自定义集合实现_


Many programmers will never need to implement their own `Collections` classes. 
You can go pretty far using the implementations described in the preceding sections of this chapter. 
However, someday you might want to write your own implementation. 
It is fairly easy to do this with the aid of the abstract implementations provided by the Java platform. 
Before we discuss how to write an implementation, let's discuss why you might want to write one.


许多程序员永远不需要实现自己的 `Collections` 类。
使用本章前面部分中描述的实现，您可以走得更远。
但是，有一天您可能想编写自己的实现。
借助 Java 平台提供的抽象实现，很容易做到这一点。
在我们讨论如何编写一个实现之前，让我们讨论一下为什么您可能想要编写一个。


## Reasons to Write an Implementation _编写实现的原因_


The following list illustrates the sort of custom `Collection`s you might want to implement. 
It is not intended to be exhaustive:


下面的列表说明了您可能想要实现的自定义 `Collection` 的种类。
它并非详尽无遗：


* **Persistent**: 
  All of the built-in `Collection` implementations reside in main memory and vanish when the program exits. 
  If you want a collection that will still be present the next time the program starts, you can implement it by building a veneer over an external database. 
  Such a collection might be concurrently accessible by multiple programs.

* **持久化**:
  所有内置的 `Collection` 实现都驻留在主内存中，并在程序退出时消失。
  如果您想要一个在程序下次启动时仍然存在的集合，您可以通过在外部数据库上构建一个胶合板来实现它。
  这样的集合可能由多个程序同时访问。

* **Application-specific**: 
  This is a very broad category. 
  One example is an unmodifiable `Map` containing real-time telemetry data. 
  The keys could represent locations, and the values could be read from sensors at these locations in response to the `get` operation.

* **特定应用**:
  这是一个非常广泛的类别。
  一个示例是包含实时遥测数据的不可修改的 `Map` 。
  键可以表示位置，并且可以从这些位置的传感器读取值以响应 `get` 操作。

* **High-performance, special-purpose**: 
  Many data structures take advantage of restricted usage to offer better performance than is possible with general-purpose implementations. 
  For instance, consider a `List` containing long runs of identical element values. 
  Such lists, which occur frequently in text processing, can be _run-length encoded_ — runs can be represented as a single object containing the repeated element and the number of consecutive repetitions. 
  This example is interesting because it trades off two aspects of performance: It requires less space but more time than an `ArrayList`.

* **高性能、专用**:
  许多数据结构利用受限使用来提供比通用实现更好的性能。
  例如，考虑一个包含大量相同元素值的 `List` 。
  此类列表在文本处理中经常出现，可以进行 _run-length encoded_ —— runs 可以表示为包含重复元素和连续重复次数的单个对象。
  这个例子很有趣，因为它权衡了两个方面的性能：它需要更少的空间，但比 `ArrayList` 需要更多的时间。

* **High-performance, general-purpose**: 
  The Java Collections Framework's designers tried to provide the best general-purpose implementations for each interface, but many, many data structures could have been used, and new ones are invented every day. 
  Maybe you can come up with something faster!

* **高性能、通用**:
  Java Collections Framework 的设计者试图为每个接口提供最好的通用实现，但是可以使用很多很多数据结构，并且每天都会发明新的数据结构。
  也许你能想出更快的东西！

* **Enhanced functionality**: 
  Suppose you need an efficient bag implementation (also known as a _multiset_): a `Collection` that offers constant-time containment checks while allowing duplicate elements. 
  It's reasonably straightforward to implement such a collection atop a `HashMap`.

* **增强的功能**:
  假设您需要一个高效的 bag 实现（也称为 _multiset_ ）：一个在允许重复元素的同时提供恒定时间包含检查的 `Collection` 。
  在 `HashMap` 上实现这样的集合相当简单。

* **Convenience**: 
  You may want additional implementations that offer conveniences beyond those offered by the Java platform. 
  For instance, you may frequently need `List` instances representing a contiguous range of `Integer`s.

* **便利**:
  您可能需要额外的实现来提供 Java 平台所提供的便利之外的便利。
  例如，您可能经常需要表示连续范围的 `Integer` 的 `List` 实例。

* **Adapter**: 
  Suppose you are using a legacy API that has its own ad hoc collections' API. 
  You can write an adapter implementation that permits these collections to operate in the Java Collections Framework. 
  An _adapter implementation_ is a thin veneer that wraps objects of one type and makes them behave like objects of another type by translating operations on the latter type into operations on the former.

* **适配器**:
  假设您正在使用具有自己的临时集合 API 的旧版 API。
  您可以编写允许这些集合在 Java 集合框架中运行的适配器实现。
  _adapter implementation_ 是一种薄薄的胶合板，它包装一种类型的对象，并通过将后一种类型的操作转换为前一种类型的操作，使它们表现得像另一种类型的对象。


## How to Write a Custom Implementation _如何编写自定义实现_


Writing a custom implementation is surprisingly easy. 
The Java Collections Framework provides abstract implementations designed expressly to facilitate custom implementations. 
We'll start with the following example of an implementation of [`Arrays.asList`](https://docs.oracle.com/javase/8/docs/api/java/util/Arrays.html#asList-T...-).


编写自定义实现非常容易。
Java Collections Framework 提供了专门为方便自定义实现而设计的抽象实现。
我们将从以下 [`Arrays.asList`](https://docs.oracle.com/javase/8/docs/api/java/util/Arrays.html#asList-T...-) 的实现示例开始。


```text
public static <T> List<T> asList(T[] a) {
    return new MyArrayList<T>(a);
}

private static class MyArrayList<T> extends AbstractList<T> {

    private final T[] a;

    MyArrayList(T[] array) {
        a = array;
    }

    public T get(int index) {
        return a[index];
    }

    public T set(int index, T element) {
        T oldValue = a[index];
        a[index] = element;
        return oldValue;
    }

    public int size() {
        return a.length;
    }
}
```


Believe it or not, this is very close to the implementation that is contained in `java.util.Arrays`. 
It's that simple! 
You provide a constructor and the `get`, `set`, and `size` methods, and `AbstractList` does all the rest. 
You get the `ListIterator`, bulk operations, search operations, hash code computation, comparison, and string representation for free.


信不信由你，这与 `java.util.Arrays` 中包含的实现非常接近。
就是这么简单！
您提供了一个构造函数和 `get` 、 `set` 和 `size` 方法，而 `AbstractList` 完成所有其余的工作。
您可以免费获得 `ListIterator` 、批量操作、搜索操作、哈希码计算、比较和字符串表示。


Suppose you want to make the implementation a bit faster. 
The API documentation for abstract implementations describes precisely how each method is implemented, so you'll know which methods to override to get the performance you want. 
The preceding implementation's performance is fine, but it can be improved a bit. 
In particular, the `toArray` method iterates over the `List`, copying one element at a time. 
Given the internal representation, it's a lot faster and more sensible just to clone the array.


假设您想让实现更快一点。
抽象实现的 API 文档准确描述了每个方法的实现方式，因此您将知道要覆盖哪些方法以获得所需的性能。
前面的实现的性能很好，但可以稍微改进一下。
特别是， `toArray` 方法遍历 `List` ，一次复制一个元素。
鉴于内部表示，仅克隆数组会更快、更明智。


```text
public Object[] toArray() {
    return (Object[]) a.clone();
}
```


With the addition of this override and a few more like it, this implementation is exactly the one found in `java.util.Arrays`. 
In the interest of full disclosure, it's a bit tougher to use the other abstract implementations because you will have to write your own iterator, but it's still not that difficult.


添加了这个覆盖和更多类似的实现，这个实现正是在 `java.util.Arrays` 中找到的那个。
为了完全公开，使用其他抽象实现有点困难，因为您必须编写自己的迭代器，但这仍然不是那么困难。


The following list summarizes the abstract implementations:


以下列表总结了抽象实现：


* [`AbstractCollection`](https://docs.oracle.com/javase/8/docs/api/java/util/AbstractCollection.html) — a `Collection` that is neither a `Set` nor a `List`. 
  At a minimum, you must provide the `iterator` and the `size` methods.

* [`AbstractCollection`](https://docs.oracle.com/javase/8/docs/api/java/util/AbstractCollection.html) — 既不是 `Set` 也不是 `List` 的 `Collection` 。
  至少，您必须提供 `iterator` 和 `size` 方法。

* [`AbstractSet`](https://docs.oracle.com/javase/8/docs/api/java/util/AbstractSet.html) — a `Set`; use is identical to `AbstractCollection`.

* [`AbstractSet`](https://docs.oracle.com/javase/8/docs/api/java/util/AbstractSet.html) — 一个 `Set` ；用法与 `AbstractCollection` 相同。

* [`AbstractList`](https://docs.oracle.com/javase/8/docs/api/java/util/AbstractList.html) — a `List` backed up by a random-access data store, such as an array. 
  At a minimum, you must provide the `positional access` methods (`get` and, optionally, `set`, `remove`, and `add`) and the `size` method. 
  The abstract class takes care of `listIterator` (and `iterator`).

* [`AbstractList`](https://docs.oracle.com/javase/8/docs/api/java/util/AbstractList.html) — 由随机访问数据存储（例如数组）备份的 `List` 。
  至少，您必须提供 `positional access` 方法（ `get` 以及可选的 `set` 、 `remove` 和 `add` ）和 `size` 方法。
  抽象类负责 `listIterator` （和 `iterator` ）。

* [`AbstractSequentialList`](https://docs.oracle.com/javase/8/docs/api/java/util/AbstractSequentialList.html) — a `List` backed up by a sequential-access data store, such as a linked list. 
  At a minimum, you must provide the `listIterator` and `size` methods. 
  The abstract class takes care of the positional access methods. 
  (This is the opposite of `AbstractList`.)

* [`AbstractSequentialList`](https://docs.oracle.com/javase/8/docs/api/java/util/AbstractSequentialList.html) — 由顺序访问数据存储（例如链表）备份的 `List` 。
  至少，您必须提供 `listIterator` 和 `size` 方法。
  抽象类负责位置访问方法。
  （这与 `AbstractList` 正好相反。）

* [`AbstractQueue`](https://docs.oracle.com/javase/8/docs/api/java/util/AbstractQueue.html) — at a minimum, you must provide the `offer`, `peek`, `poll`, and `size` methods and an `iterator` supporting `remove`.

* [`AbstractQueue`](https://docs.oracle.com/javase/8/docs/api/java/util/AbstractQueue.html) — 至少，您必须提供 `offer` 、 `peek` 、 `poll` 和 `size` 方法以及支持 `remove` 的 `iterator` 。

* [`AbstractMap`](https://docs.oracle.com/javase/8/docs/api/java/util/AbstractMap.html) — a `Map`. 
  At a minimum you must provide the `entrySet` view. 
  This is typically implemented with the `AbstractSet` class. 
  If the `Map` is modifiable, you must also provide the `put` method.

* [`AbstractMap`](https://docs.oracle.com/javase/8/docs/api/java/util/AbstractMap.html) — 一个 `Map` 。
  至少您必须提供 `entrySet` 视图。
  这通常使用 `AbstractSet` 类来实现。
  如果 `Map` 是可修改的，你还必须提供 `put` 方法。


The process of writing a custom implementation follows:


编写自定义实现的过程如下：


* Choose the appropriate abstract implementation class from the preceding list.

* 从前面的列表中选择适当的抽象实现类。

* Provide implementations for all the abstract methods of the class. 
  If your custom collection is to be modifiable, you will have to override one or more of the concrete methods as well. 
  The API documentation for the abstract implementation class will tell you which methods to override.

* 为类的所有抽象方法提供实现。
  如果您的自定义集合是可修改的，您还必须覆盖一个或多个具体方法。
  抽象实现类的 API 文档将告诉您要覆盖哪些方法。

* Test and, if necessary, debug the implementation. 
  You now have a working custom collection implementation.

* 测试并在必要时调试实现。
  您现在有一个有效的自定义集合实现。

* If you are concerned about performance, read the API documentation of the abstract implementation class for all the methods whose implementations you're inheriting. 
  If any seem too slow, override them. 
  If you override any methods, be sure to measure the performance of the method before and after the override. 
  How much effort you put into tweaking performance should be a function of how much use the implementation will get and how critical to performance its use is. 
  (Often this step is best omitted.)

* 如果您担心性能，请阅读抽象实现类的 API 文档，了解您要继承其实现的所有方法。
  如果有任何看起来太慢，请覆盖它们。
  如果重写任何方法，请务必在重写之前和之后测量方法的性能。
  您在调整性能上付出了多少努力应该取决于实现将获得多少使用以及对其使用的性能有多重要。
  （通常最好省略这一步。）
