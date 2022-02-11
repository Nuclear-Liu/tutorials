# Summary of Implementations


Implementations are the data objects used to store collections, which implement the interfaces described in the [Interfaces lesson](https://docs.oracle.com/javase/tutorial/collections/interfaces/index.html).


实现是用于存储集合的数据对象，它实现了 [Interfaces lesson](./../interfaces/index.md) 中描述的接口。


The Java Collections Framework provides several general-purpose implementations of the core interfaces:


Java Collections Framework 提供了几个核心接口的通用实现：


* For the `Set` interface, `HashSet` is the most commonly used implementation.

* 对于 `Set` 接口， `HashSet` 是最常用的实现。

* For the `List` interface, `ArrayList` is the most commonly used implementation.

* 对于 `List` 接口， `ArrayList` 是最常用的实现。

* For the `Map` interface, `HashMap` is the most commonly used implementation.

* 对于 `Map` 接口， `HashMap` 是最常用的实现。

* For the `Queue` interface, `LinkedList` is the most commonly used implementation.

* 对于 `Queue` 接口， `LinkedList` 是最常用的实现。

* For the `Deque` interface, `ArrayDeque` is the most commonly used implementation.

* 对于 `Deque` 接口， `ArrayDeque` 是最常用的实现。


Each of the general-purpose implementations provides all optional operations contained in its interface.


每个通用实现都提供了包含在其接口中的所有可选操作。


The Java Collections Framework also provides several special-purpose implementations for situations that require nonstandard performance, usage restrictions, or other unusual behavior.


Java Collections Framework 还为需要非标准性能、使用限制或其他异常行为的情况提供了几个特殊用途的实现。


The `java.util.concurrent` package contains several collections implementations, which are thread-safe but not governed by a single exclusion lock.


`java.util.concurrent` 包包含多个集合实现，它们是线程安全的，但不受单个排除锁的控制。


The `Collections` class (as opposed to the `Collection` interface), provides static methods that operate on or return collections, which are known as Wrapper implementations.


`Collections` 类（相对于 `Collection` 接口）提供了对集合进行操作或返回的静态方法，这些方法称为 Wrapper 实现。


Finally, there are several Convenience implementations, which can be more efficient than general-purpose implementations when you don't need their full power. 
The Convenience implementations are made available through static factory methods.


最后，还有几个 Convenience 实现，当您不需要它们的全部功能时，它们可能比通用实现更有效。
Convenience 实现是通过静态工厂方法提供的。
