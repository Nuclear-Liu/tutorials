# Lesson: Implementations _实现_


* [Set Implementations](https://docs.oracle.com/javase/tutorial/collections/implementations/set.html)

* [Set Implementations](set.md)

* [List Implementations](https://docs.oracle.com/javase/tutorial/collections/implementations/list.html)

* [List Implementations](list.md)

* [Map Implementations](https://docs.oracle.com/javase/tutorial/collections/implementations/map.html)

* [Map Implementations](map.md)

* [Queue Implementations](https://docs.oracle.com/javase/tutorial/collections/implementations/queue.html)

* [Queue Implementations](queue.md)

* [Deque Implementations](https://docs.oracle.com/javase/tutorial/collections/implementations/deque.html)

* [Deque Implementations](deque.md)

* [Wrapper Implementations](https://docs.oracle.com/javase/tutorial/collections/implementations/wrapper.html)

* [Wrapper Implementations](wrapper.md)

* [Convenience Implementations](https://docs.oracle.com/javase/tutorial/collections/implementations/convenience.html)

* [Convenience Implementations](convenience.md)

* [Summary of Implementations](https://docs.oracle.com/javase/tutorial/collections/implementations/summary.html)

* [Summary of Implementations](summary.md)

* [Questions and Exercises](https://docs.oracle.com/javase/tutorial/collections/implementations/QandE/questions.html)

* [Questions and Exercises](QandE/questions.md)


Implementations are the data objects used to store collections, which implement the interfaces described in [the Interfaces section](https://docs.oracle.com/javase/tutorial/collections/interfaces/index.html). 
This lesson describes the following kinds of implementations:


实现是用于存储集合的数据对象，它实现了 [the Interfaces section](../interfaces/index.md) 中描述的接口。
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

* **Abstract implementations** 是有助于构建自定义实现的骨架实现 —— 稍后在 [Custom Collection Implementations](../custom-implementations/index.md) 部分中进行描述。
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


As you can see from the table, the Java Collections Framework provides several general-purpose implementations of the [`Set`](https://docs.oracle.com/javase/8/docs/api/java/util/Set.html), [`List`](https://docs.oracle.com/javase/8/docs/api/java/util/List.html) , and [`Map`](https://docs.oracle.com/javase/8/docs/api/java/util/Map.html) interfaces. 
In each case, one implementation — [`HashSet`](https://docs.oracle.com/javase/8/docs/api/java/util/HashSet.html), [`ArrayList`](https://docs.oracle.com/javase/8/docs/api/java/util/ArrayList.html), and [`HashMap`](https://docs.oracle.com/javase/8/docs/api/java/util/HashMap.html) — is clearly the one to use for most applications, all other things being equal. 
Note that the [`SortedSet`](https://docs.oracle.com/javase/8/docs/api/java/util/SortedSet.html) and the [`SortedMap`](https://docs.oracle.com/javase/8/docs/api/java/util/SortedMap.html) interfaces do not have rows in the table. 
Each of those interfaces has one implementation ([`TreeSet`](https://docs.oracle.com/javase/8/docs/api/java/util/TreeSet.html) and [`TreeMap`](https://docs.oracle.com/javase/8/docs/api/java/util/TreeMap.html)) and is listed in the `Set` and the `Map` rows. 
There are two general-purpose `Queue` implementations — [`LinkedList`](https://docs.oracle.com/javase/8/docs/api/java/util/LinkedList.html), which is also a `List` implementation, and [`PriorityQueue`](https://docs.oracle.com/javase/8/docs/api/java/util/PriorityQueue.html), which is omitted from the table. 
These two implementations provide very different semantics: `LinkedList` provides FIFO semantics, while `PriorityQueue` orders its elements according to their values.


从表中可以看出，Java 集合框架提供了 [`Set`](https://docs.oracle.com/javase/8/docs/api/java/util/Set.html) 、 [`List`](https://docs.oracle.com/javase/8/docs/api/java/util/List.html) 和 [`Map`](https://docs.oracle.com/javase/8/docs/api/java/util/Map.html) 接口的几个通用实现。
在每种情况下，一种实现—— [`HashSet`](https://docs.oracle.com/javase/8/docs/api/java/util/HashSet.html) 、 [`ArrayList`](https://docs.oracle.com/javase/8/docs/api/java/util/ArrayList.html) 和 [`HashMap`](https://docs.oracle.com/javase/8/docs/api/java/util/HashMap.html) —— 显然是大多数应用程序使用的一种，所有其他条件都相同。
请注意， [`SortedSet`](https://docs.oracle.com/javase/8/docs/api/java/util/SortedSet.html) 和 [`SortedMap`](https://docs.oracle.com/javase/8/docs/api/java/util/SortedMap.html) 接口在表中没有行。
这些接口中的每一个都有一个实现（ [`TreeSet`](https://docs.oracle.com/javase/8/docs/api/java/util/TreeSet.html) 和 [`TreeMap`](https://docs.oracle.com/javase/8/docs/api/java/util/TreeMap.html) ），并列在 `Set` 和 `Map` 行中。
有两个通用的 `Queue` 实现 - [`LinkedList`](https://docs.oracle.com/javase/8/docs/api/java/util/LinkedList.html) ，它也是一个 `List` 实现，和 [`PriorityQueue`](https://docs.oracle.com/javase/8/docs/api/java/util/PriorityQueue.html) ，从表中省略。
这两种实现提供了非常不同的语义： `LinkedList` 提供 FIFO 语义，而 `PriorityQueue` 根据它们的值对其元素进行排序。


Each of the general-purpose implementations provides all optional operations contained in its interface. 
All permit `null` elements, keys, and values. 
None are synchronized (thread-safe). 
All have fail-fast iterators, which detect illegal concurrent modification during iteration and fail quickly and cleanly rather than risking arbitrary, non-deterministic behavior at an undetermined time in the future. 
All are `Serializable` and all support a public `clone` method.


每个通用实现都提供了包含在其接口中的所有可选操作。
所有允许 `null` 元素、键和值。
没有一个是同步的（线程安全的）。
它们都有快速失败的迭代器，它们在迭代期间检测非法的并发修改，并快速而干净地失败，而不是在未来不确定的时间冒着任意的、非确定性的行为的风险。
所有都是 `Serializable` 并且都支持公共的 `clone` 方法。


The fact that these implementations are unsynchronized represents a break with the past: 
The legacy collections `Vector` and `Hashtable` are synchronized. 
The present approach was taken because collections are frequently used when the synchronization is of no benefit. 
Such uses include single-threaded use, read-only use, and use as part of a larger data object that does its own synchronization. 
In general, it is good API design practice not to make users pay for a feature they don't use. 
Furthermore, unnecessary synchronization can result in deadlock under certain circumstances.


这些实现不同步的事实代表了与过去的决裂：
旧的集合 `Vector` 和 `Hashtable` 是同步的。
采用目前的方法是因为当同步没有好处时经常使用集合。
此类使用包括单线程使用、只读使用以及作为执行自身同步的更大数据对象的一部分使用。
一般来说，不让用户为他们不使用的功能付费是一种很好的 API 设计实践。
此外，在某些情况下，不必要的同步可能会导致死锁。


If you need thread-safe collections, the synchronization wrappers, described in the [Wrapper Implementations](https://docs.oracle.com/javase/tutorial/collections/implementations/wrapper.html) section, allow any collection to be transformed into a synchronized collection. 
Thus, synchronization is optional for general-purpose implementations, whereas it is mandatory for legacy implementations. 
Moreover, the `java.util.concurrent` package provides concurrent implementations of the `BlockingQueue` interface, which extends `Queue`, and of the `ConcurrentMap` interface, which extends `Map`. 
These implementations offer much higher concurrency than mere synchronized implementations.


如果您需要线程安全的集合，[Wrapper Implementations](wrapper.md) 部分中描述的同步包装器允许将任何集合转换为同步集合。
因此，同步对于通用实现是可选的，而对于遗留实现是强制性的。
此外， `java.util.concurrent` 包提供了扩展 `Queue` 的 `BlockingQueue` 接口和扩展 `Map` 的 `ConcurrentMap` 接口的并发实现。
这些实现提供了比单纯的同步实现更高的并发性。


As a rule, you should be thinking about the interfaces, not the implementations. 
That is why there are no programming examples in this section. 
For the most part, the choice of implementation affects only performance. 
The preferred style, as mentioned in the [Interfaces](https://docs.oracle.com/javase/tutorial/collections/interfaces/index.html) section, is to choose an implementation when a `Collection` is created and to immediately assign the new collection to a variable of the corresponding interface type (or to pass the collection to a method expecting an argument of the interface type). 
In this way, the program does not become dependent on any added methods in a given implementation, leaving the programmer free to change implementations anytime that it is warranted by performance concerns or behavioral details.


通常，您应该考虑接口，而不是实现。
这就是本节中没有编程示例的原因。
在大多数情况下，实现的选择只影响性能。
如 [Interfaces](../index.md) 部分所述，首选样式是在创建 `Collection` 时选择一个实现，并立即将新集合分配给相应接口类型的变量（或将集合传递给方法需要接口类型的参数）。
这样，程序就不会依赖于给定实现中的任何添加方法，从而使程序员可以在性能问题或行为细节保证的任何时候自由更改实现。


The sections that follow briefly discuss the implementations. 
The performance of the implementations is described using words such as _constant-time_, _log_, _linear_, _n log(n)_, and _quadratic_ to refer to the asymptotic upper-bound on the time complexity of performing the operation. 
All this is quite a mouthful, and it doesn't matter much if you don't know what it means. 
If you're interested in knowing more, refer to any good algorithms textbook. 
One thing to keep in mind is that this sort of performance metric has its limitations. 
Sometimes, the nominally slower implementation may be faster. 
When in doubt, measure the performance!


以下部分简要讨论了实现。
使用 _constant-time_ 、 _log_ 、 _linear_ 、 _n log(n)_ 和 _quadratic_  等词来描述实现的性能，以指代执行操作的时间复杂度的渐近上限。
这一切都相当拗口，如果你不知道这意味着什么，那也没关系。
如果您有兴趣了解更多信息，请参阅任何好的算法教科书。
要记住的一件事是，这种性能指标有其局限性。
有时，名义上较慢的实现可能会更快。
如有疑问，请测量性能！
