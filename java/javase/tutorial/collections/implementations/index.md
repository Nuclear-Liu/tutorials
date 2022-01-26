# Lesson: Implementations _实现_


* [Set Implementations]()

* [Set Implementations]()

* [List Implementations]()

* [List Implementations]()

* [Map Implementations]()

* [Map Implementations]()

* [Queue Implementations]()

* [Queue Implementations]()

* [Deque Implementations]()

* [Deque Implementations]()

* [Wrapper Implementations]()

* [Wrapper Implementations]()

* [Convenience Implementations]()

* [Convenience Implementations]()

* [Summary of Implementations]()

* [Summary of Implementations]()

* [Questions and Exercises]()

* [Questions and Exercises]()


Implementations are the data objects used to store collections, which implement the interfaces described in [the Interfaces section](https://docs.oracle.com/javase/tutorial/collections/interfaces/index.html). 
This lesson describes the following kinds of implementations:


实现是用于存储集合的数据对象，它实现了 [the Interfaces section](./../interfaces/index.md) 中描述的接口。
本课描述了以下几种实现：


* **General-purpose implementations** are the most commonly used implementations, designed for everyday use. 
  They are summarized in the table titled General-purpose-implementations.

* **General-purpose implementations** 是最常用的实现，专为日常使用而设计。
  它们在标题为通用实现的表中进行了总结。

* **Special-purpose implementations** are designed for use in special situations and display nonstandard performance characteristics, usage restrictions, or behavior.

* **Special-purpose implementations** 专为在特殊情况下使用而设计，并显示非标准的性能特征、使用限制或行为。

* **Concurrent implementations** are designed to support high concurrency, typically at the expense of single-threaded performance. 
  These implementations are part of the `java.util.concurrent` package.

* **Concurrent implementations** 旨在支持高并发，通常以牺牲单线程性能为代价。
  这些实现是 `java.util.concurrent` 包的一部分。

* **Wrapper implementations** are used in combination with other types of implementations, often the general-purpose ones, to provide added or restricted functionality.

* **Wrapper implementations** 与其他类型的实现（通常是通用的实现）结合使用，以提供添加或受限的功能。

* **Convenience implementations** are mini-implementations, typically made available via static factory methods, that provide convenient, efficient alternatives to general-purpose implementations for special collections (for example, singleton sets).

* **Convenience implementations** 是微型实现，通常通过静态工厂方法提供，为特殊集合（例如，单例集）的通用实现提供方便、有效的替代方案。

* **Abstract implementations** are skeletal implementations that facilitate the construction of custom implementations — described later in the [Custom Collection Implementations](https://docs.oracle.com/javase/tutorial/collections/custom-implementations/index.html) section. 
  An advanced topic, it's not particularly difficult, but relatively few people will need to do it.

* **Abstract implementations** 是有助于构建自定义实现的骨架实现 —— 稍后在 [Custom Collection Implementations](./../custom-implementations/index.md) 部分中进行描述。
  一个高级的话题，它不是特别难，但相对来说很少有人需要去做。


The general-purpose implementations are summarized in the following table.


下表总结了通用实现。


**General-purpose Implementations**

| Interfaces | Hash table Implementations | Resizable array Implementations | Tree Implementations | Linked list Implementations | Hash table + Linked list Implementations |
|------------|----------------------------|---------------------------------|----------------------|-----------------------------|------------------------------------------|
| `Set`      | `HashSet`                  |                                 | `TreeSet`            |                             | `LinkedHashSet`                          |
| `List`     |                            | `ArrayList`                     |                      | `LinkedList`                |                                          |
| `Queue`    |                            |                                 |                      |                             |                                          |
| `Deque`    |                            | `ArrayDeque`                    |                      | `LinkedList`                |                                          |
| `Map`      | `HashMap`                  |                                 | `TreeMap`            |                             | `LinkedHashMap`                          |


**通用实现**

| Interfaces | Hash table Implementations | Resizable array Implementations | Tree Implementations | Linked list Implementations | Hash table + Linked list Implementations |
|------------|----------------------------|---------------------------------|----------------------|-----------------------------|------------------------------------------|
| `Set`      | `HashSet`                  |                                 | `TreeSet`            |                             | `LinkedHashSet`                          |
| `List`     |                            | `ArrayList`                     |                      | `LinkedList`                |                                          |
| `Queue`    |                            |                                 |                      |                             |                                          |
| `Deque`    |                            | `ArrayDeque`                    |                      | `LinkedList`                |                                          |
| `Map`      | `HashMap`                  |                                 | `TreeMap`            |                             | `LinkedHashMap`                          |


As you can see from the table, the Java Collections Framework provides several general-purpose implementations of the `Set`, `List` , and `Map` interfaces. 
In each case, one implementation — `HashSet`, `ArrayList`, and `HashMap` — is clearly the one to use for most applications, all other things being equal. 
Note that the `SortedSet` and the `SortedMap` interfaces do not have rows in the table. 
Each of those interfaces has one implementation (`TreeSet` and `TreeMap`) and is listed in the `Set` and the `Map` rows. 
There are two general-purpose `Queue` implementations — `LinkedList`, which is also a List implementation, and `PriorityQueue`, which is omitted from the table. 
These two implementations provide very different semantics: `LinkedList` provides FIFO semantics, while `PriorityQueue` orders its elements according to their values.


从表中可以看出，Java 集合框架提供了 `Set`、 `List` 和 `Map` 接口的几个通用实现。
在每种情况下，一种实现—— `HashSet`、 `ArrayList` 和 `HashMap` —— 显然是大多数应用程序使用的一种，所有其他条件都相同。
请注意， `SortedSet` 和 `SortedMap` 接口在表中没有行。
Each of those interfaces has one implementation (`TreeSet` and `TreeMap`) and is listed in the `Set` and the `Map` rows. 
There are two general-purpose `Queue` implementations — `LinkedList`, which is also a List implementation, and `PriorityQueue`, which is omitted from the table. 
These two implementations provide very different semantics: `LinkedList` provides FIFO semantics, while `PriorityQueue` orders its elements according to their values.


Each of the general-purpose implementations provides all optional operations contained in its interface. 
All permit `null` elements, keys, and values. 
None are synchronized (thread-safe). 
All have fail-fast iterators, which detect illegal concurrent modification during iteration and fail quickly and cleanly rather than risking arbitrary, non-deterministic behavior at an undetermined time in the future. 
All are `Serializable` and all support a public `clone` method.


The fact that these implementations are unsynchronized represents a break with the past: 
The legacy collections `Vector` and `Hashtable` are synchronized. 
The present approach was taken because collections are frequently used when the synchronization is of no benefit. 
Such uses include single-threaded use, read-only use, and use as part of a larger data object that does its own synchronization. 
In general, it is good API design practice not to make users pay for a feature they don't use. 
Furthermore, unnecessary synchronization can result in deadlock under certain circumstances.


If you need thread-safe collections, the synchronization wrappers, described in the [Wrapper Implementations]() section, allow any collection to be transformed into a synchronized collection. 
Thus, synchronization is optional for general-purpose implementations, whereas it is mandatory for legacy implementations. 
Moreover, the `java.util.concurrent` package provides concurrent implementations of the `BlockingQueue` interface, which extends `Queue`, and of the `ConcurrentMap` interface, which extends `Map`. 
These implementations offer much higher concurrency than mere synchronized implementations.


As a rule, you should be thinking about the interfaces, not the implementations. 
That is why there are no programming examples in this section. 
For the most part, the choice of implementation affects only performance. 
The preferred style, as mentioned in the [Interfaces]() section, is to choose an implementation when a `Collection` is created and to immediately assign the new collection to a variable of the corresponding interface type (or to pass the collection to a method expecting an argument of the interface type). 
In this way, the program does not become dependent on any added methods in a given implementation, leaving the programmer free to change implementations anytime that it is warranted by performance concerns or behavioral details.


The sections that follow briefly discuss the implementations. 
The performance of the implementations is described using words such as _constant-time_, _log_, _linear_, _n log(n)_, and _quadratic_ to refer to the asymptotic upper-bound on the time complexity of performing the operation. 
All this is quite a mouthful, and it doesn't matter much if you don't know what it means. 
If you're interested in knowing more, refer to any good algorithms textbook. 
One thing to keep in mind is that this sort of performance metric has its limitations. 
Sometimes, the nominally slower implementation may be faster. 
When in doubt, measure the performance!
