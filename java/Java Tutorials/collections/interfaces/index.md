# Lesson: Interfaces


* [The Collection Interface](https://docs.oracle.com/javase/tutorial/collections/interfaces/collection.html)

* [The Collection Interface](./collection.md)

* [The Set Interface](https://docs.oracle.com/javase/tutorial/collections/interfaces/set.html)

* [The Set Interface](./set.md)

* [The List Interface](https://docs.oracle.com/javase/tutorial/collections/interfaces/list.html)

* [The List Interface](./list.md)

* [The Queue Interface](https://docs.oracle.com/javase/tutorial/collections/interfaces/queue.html)

* [The Queue Interface](./queue.md)

* [The Deque Interface](https://docs.oracle.com/javase/tutorial/collections/interfaces/deque.html)

* [The Deque Interface](./deque.md)

* [The Map Interface](https://docs.oracle.com/javase/tutorial/collections/interfaces/map.html)

* [The Map Interface](./map.md)

* [Object Ordering](https://docs.oracle.com/javase/tutorial/collections/interfaces/order.html)

* [Object Ordering]()

* [The SortedSet Interface](https://docs.oracle.com/javase/tutorial/collections/interfaces/sorted-set.html)

* [The SortedSet Interface]()

* [The SortedMap Interface](https://docs.oracle.com/javase/tutorial/collections/interfaces/sorted-map.html)

* [The SortedMap Interface]()

* [Summary of Interfaces](https://docs.oracle.com/javase/tutorial/collections/interfaces/summary.html)

* [Summary of Interfaces]()

* [Questions and Exercises](https://docs.oracle.com/javase/tutorial/collections/interfaces/QandE/questions.html)

* [Questions and Exercises]()


The _core collection_ interfaces encapsulate different types of collections, which are shown in the figure below. 
These interfaces allow collections to be manipulated independently of the details of their representation. 
Core collection interfaces are the foundation of the Java Collections Framework. 
As you can see in the following figure, the core collection interfaces form a hierarchy.


*核心集合*接口封装了不同类型的集合，如下图所示。
这些接口允许独立于其表示的细节来操作集合。
核心集合接口是 Java 集合框架的基础。
如下图所示，核心集合接口形成了一个层次结构。


![The core collection interfaces.](./colls-coreInterfaces.gif)


A `Set` is a special kind of `Collection`, a `SortedSet` is a special kind of `Set`, and so forth. 
Note also that the hierarchy consists of two distinct trees — a `Map` is not a true Collection.


`Set` 是一种特殊的 `Collection` ，`SortedSet` 是一种特殊的 `Set` ，等等。
还要注意，层次结构由两个不同的树组成 —— `Map` 不是真正的集合。


Note that all the core collection interfaces are generic. 
For example, this is the declaration of the `Collection` interface.


请注意，所有核心集合接口都是通用的。
例如，这是 `Collection` 接口的声明。


`public interface Collection<E>...`


The `<E>` syntax tells you that the interface is generic. 
When you declare a `Collection` instance you can and should specify the type of object contained in the collection. 
Specifying the type allows the compiler to verify (at compile-time) that the type of object you put into the collection is correct, thus reducing errors at runtime. 
For information on generic types, see the [Generics (Updated)](https://docs.oracle.com/javase/tutorial/java/generics/index.html) lesson.


`<E>` 语法告诉你接口是通用的。
当你声明一个 `Collection` 实例时，你可以并且应该指定集合中包含的对象的类型。
指定类型允许编译器（在编译时）验证您放入集合中的对象类型是否正确，从而减少运行时的错误。
有关泛型类型的信息，请参阅 [Generics (Updated)]() 课程。


When you understand how to use these interfaces, you will know most of what there is to know about the Java Collections Framework. 
This chapter discusses general guidelines for effective use of the interfaces, including when to use which interface. 
You'll also learn programming idioms for each interface to help you get the most out of it.


当您了解如何使用这些接口时，您将了解有关 Java 集合框架的大部分知识。
本章讨论有效使用接口的一般准则，包括何时使用哪个接口。
您还将学习每个接口的编程习惯用法，以帮助您充分利用它。


To keep the number of core collection interfaces manageable, the Java platform doesn't provide separate interfaces for each variant of each collection type. 
(Such variants might include immutable, fixed-size, and append-only.) Instead, the modification operations in each interface are designated optional — a given implementation may elect not to support all operations. 
If an unsupported operation is invoked, a collection throws an `UnsupportedOperationException`. 
Implementations are responsible for documenting which of the optional operations they support. 
All of the Java platform's general-purpose implementations support all of the optional operations.


为了保持核心集合接口的数量可管理，Java 平台没有为每个集合类型的每个变体提供单独的接口。
（此类变体可能包括不可变、固定大小和仅附加。）相反，每个接口中的修改操作被指定为可选的——给定的实现可能选择不支持所有操作。
如果调用了不受支持的操作，则集合会抛出 `UnsupportedOperationException` 。
实现负责记录它们支持哪些可选操作。
Java 平台的所有通用实现都支持所有可选操作。


The following list describes the core collection interfaces:


以下列表描述了核心集合接口：



* `Collection` — the root of the collection hierarchy. 
  A collection represents a group of objects known as its elements. 
  The `Collection` interface is the least common denominator that all collections implement and is used to pass collections around and to manipulate them when maximum generality is desired. 
  Some types of collections allow duplicate elements, and others do not. 
  Some are ordered and others are unordered. 
  The Java platform doesn't provide any direct implementations of this interface but provides implementations of more specific subinterfaces, such as `Set` and `List`. 
  Also see [The Collection Interface](https://docs.oracle.com/javase/tutorial/collections/interfaces/collection.html) section.

* `Collection` — 集合层次结构的根。
  集合表示一组称为其元素的对象。
  `Collection` 接口是所有集合实现的最小公分母，用于传递集合并在需要最大通用性时操作它们。
  某些类型的集合允许重复元素，而其他类型则不允许。
  有些是有序的，有些是无序的。
  Java 平台不提供该接口的任何直接实现，但提供了更具体的子接口的实现，例如 `Set` 和 `List` 。
  另请参阅 [The Collection Interface](./collection.md) 部分。

* `Set` — a collection that cannot contain duplicate elements. 
  This interface models the mathematical set abstraction and is used to represent sets, such as the cards comprising a poker hand, the courses making up a student's schedule, or the processes running on a machine. 
  See also [The Set Interface](https://docs.oracle.com/javase/tutorial/collections/interfaces/set.html) section.

* `Set` — 不能包含重复元素的集合。
  该接口对数学集合抽象进行建模，并用于表示集合，例如构成一手牌的卡片、构成学生日程安排的课程或在机器上运行的进程。
  另请参阅 [The Set Interface]() 部分。

* `List` — an ordered collection (sometimes called a _sequence_). 
  `List`s can contain duplicate elements. 
  The user of a `List` generally has precise control over where in the list each element is inserted and can access elements by their integer index (position). 
  If you've used `Vector`, you're familiar with the general flavor of `List`. 
  Also see [The List Interface]() section.

* `List` — 有序集合（有时称为 _sequence_）。
  `List` 可以包含重复的元素。
  `List` 的用户通常可以精确控制每个元素在列表中的插入位置，并且可以通过它们的整数索引（位置）访问元素。
  如果你使用过 `Vector` ，你就会熟悉 `List` 的一般风格。
  另请参阅 [The List Interface]() 部分。

* `Queue` — a collection used to hold multiple elements prior to processing. 
  Besides basic `Collection` operations, a `Queue` provides additional insertion, extraction, and inspection operations.

  Queues typically, but do not necessarily, order elements in a FIFO (first-in, first-out) manner. 
  Among the exceptions are priority queues, which order elements according to a supplied comparator or the elements' natural ordering. 
  Whatever the ordering used, the head of the queue is the element that would be removed by a call to `remove` or `poll`. 
  In a FIFO queue, all new elements are inserted at the tail of the queue. 
  Other kinds of queues may use different placement rules. 
  Every `Queue` implementation must specify its ordering properties. 
  Also see [The Queue Interface](https://docs.oracle.com/javase/tutorial/collections/interfaces/queue.html) section.

* `Queue` — 用于在处理之前保存多个元素的集合。
  除了基本的 `Collection` 操作， `Queue` 还提供额外的插入、提取和检查操作。

  队列通常但不一定以 FIFO（先进先出）方式对元素进行排序。
  例外情况是优先级队列，它根据提供的比较器或元素的自然顺序对元素进行排序。
  无论使用何种顺序，队列的头部都是将通过调用 `remove` 或 `poll` 删除的元素。
  在 FIFO 队列中，所有新元素都插入到队列的尾部。
  其他类型的队列可能使用不同的放置规则。
  每个 `Queue` 实现都必须指定其排序属性。
  另请参阅 [The Queue Interface]() 部分。

* `Deque` — a collection used to hold multiple elements prior to processing. 
  Besides basic `Collection` operations, a `Deque` provides additional insertion, extraction, and inspection operations.

  Deques can be used both as FIFO (first-in, first-out) and LIFO (last-in, first-out). 
  In a deque all new elements can be inserted, retrieved and removed at both ends. 
  Also see [The Deque Interface](https://docs.oracle.com/javase/tutorial/collections/interfaces/deque.html) section.

* `Deque` — 用于在处理之前保存多个元素的集合。
  除了基本的 `Collection` 操作， `Deque` 还提供额外的插入、提取和检查操作。

  双端队列既可以用作 FIFO（先进先出），也可以用作 LIFO（后进先出）。
  在双端队列中，所有新元素都可以在两端插入、检索和删除。
  另请参阅 [The Deque Interface]() 部分。

* `Map` — an object that maps keys to values. 
  A `Map` cannot contain duplicate keys; each key can map to at most one value. 
  If you've used `Hashtable`, you're already familiar with the basics of `Map`. 
  Also see [The Map Interface](https://docs.oracle.com/javase/tutorial/collections/interfaces/map.html) section.

* `Map` — 将键映射到值的对象。
  `Map` 不能包含重复的键；每个键最多可以映射到一个值。
  如果您使用过 `Hashtable`，那么您已经熟悉`Map` 的基础知识。
  另请参阅 [The Map Interface]() 部分。


The last two core collection interfaces are merely sorted versions of `Set` and `Map`:


最后两个核心集合接口只是 `Set` 和 `Map` 的排序版本：


* `SortedSet` — a `Set` that maintains its elements in ascending order. 
  Several additional operations are provided to take advantage of the ordering. 
  Sorted sets are used for naturally ordered sets, such as word lists and membership rolls. 
  Also see [The SortedSet Interface](https://docs.oracle.com/javase/tutorial/collections/interfaces/sorted-set.html) section.

* `SortedSet` — 一个以升序维护其元素的`Set`。
  提供了几个额外的操作来利用排序。
  排序集用于自然排序的集，例如单词列表和成员列表。
  另请参阅 [The SortedSet Interface]() 部分。

* `SortedMap` — a `Map` that maintains its mappings in ascending key order. 
  This is the `Map` analog of `SortedSet`. 
  Sorted maps are used for naturally ordered collections of key/value pairs, such as dictionaries and telephone directories. 
  Also see [The SortedMap Interface](https://docs.oracle.com/javase/tutorial/collections/interfaces/sorted-map.html) section.

* `SortedMap` — 一个按键升序维护其映射的`Map`。
  这是 `SortedSet` 的 `Map` 模拟。
  排序映射用于 key/value 对的自然排序集合，例如字典和电话簿。
  另请参阅 [The SortedMap Interface]() 部分。


To understand how the sorted interfaces maintain the order of their elements, see the [Object Ordering](https://docs.oracle.com/javase/tutorial/collections/interfaces/order.html) section. 


要了解排序接口如何维护其元素的顺序，请参阅 [Object Ordering]() 部分。 
