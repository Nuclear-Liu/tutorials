# Map Implementations


`Map` implementations are grouped into general-purpose, special-purpose, and concurrent implementations.


`Map` 实现分为通用、专用和并发实现。


## General-Purpose `Map` Implementations _通用 `Map` 实现_


The three general-purpose [`Map`](https://docs.oracle.com/javase/8/docs/api/java/util/Map.html) implementations are [`HashMap`](https://docs.oracle.com/javase/8/docs/api/java/util/HashMap.html), [`TreeMap`](https://docs.oracle.com/javase/8/docs/api/java/util/TreeMap.html) and [`LinkedHashMap`](https://docs.oracle.com/javase/8/docs/api/java/util/LinkedHashMap.html). 
If you need `SortedMap` operations or key-ordered `Collection`-view iteration, use `TreeMap`; if you want maximum speed and don't care about iteration order, use `HashMap`; if you want near-`HashMap` performance and insertion-order iteration, use `LinkedHashMap`. 
In this respect, the situation for `Map` is analogous to `Set`. 
Likewise, everything else in the [Set Implementations](https://docs.oracle.com/javase/tutorial/collections/implementations/set.html) section also applies to `Map` implementations.


三个通用的 [`Map`](https://docs.oracle.com/javase/8/docs/api/java/util/Map.html) 实现是 [`HashMap`](https://docs.oracle.com/javase/8/docs/api/java/util/HashMap.html) 、 [`TreeMap`](https://docs.oracle.com/javase/8/docs/api/java/util/TreeMap.html) 和 [`LinkedHashMap`](https://docs.oracle.com/javase/8/docs/api/java/util/LinkedHashMap.html) 。
如果需要 `SortedMap` 操作或键序 `Collection`-view 迭代，请使用 `TreeMap` ；如果您想要最大速度并且不关心迭代顺序，请使用 `HashMap` ；如果您想要接近 - `HashMap` 的性能和插入顺序迭代，请使用 `LinkedHashMap` 。
在这方面， `Map` 的情况类似于 `Set` 。
同样， [Set Implementations](./set.md) 部分中的所有其他内容也适用于 `Map` 实现。


`LinkedHashMap` provides two capabilities that are not available with `LinkedHashSet`. 
When you create a `LinkedHashMap`, you can order it based on key access rather than insertion. 
In other words, merely looking up the value associated with a key brings that key to the end of the map. 
Also, `LinkedHashMap` provides the `removeEldestEntry` method, which may be overridden to impose a policy for removing stale mappings automatically when new mappings are added to the map. 
This makes it very easy to implement a custom cache.


`LinkedHashMap` 提供了 `LinkedHashSet` 没有的两种功能。
当您创建一个 `LinkedHashMap` 时，您可以根据键访问而不是插入对其进行排序。
换句话说，仅查找与键关联的值就会将该键带到映射的末尾。
此外， `LinkedHashMap` 提供了 `removeEldestEntry` 方法，当新映射添加到映射时，该方法可能会被覆盖以强制执行自动删除陈旧映射的策略。
这使得实现自定义缓存变得非常容易。


For example, this override will allow the map to grow up to as many as 100 entries and then it will delete the eldest entry each time a new entry is added, maintaining a steady state of 100 entries.


例如，此覆盖将允许映射增长到多达 100 个条目，然后每次添加新条目时都会删除最旧的条目，从而保持 100 个条目的稳定状态。


```text
private static final int MAX_ENTRIES = 100;

protected boolean removeEldestEntry(Map.Entry eldest) {
    return size() > MAX_ENTRIES;
}
```


## Special-Purpose `Map` Implementations _专用 `Map` 实现_


There are three special-purpose Map implementations — [`EnumMap`](https://docs.oracle.com/javase/8/docs/api/java/util/EnumMap.html), [`WeakHashMap`](https://docs.oracle.com/javase/8/docs/api/java/util/WeakHashMap.html) and [`IdentityHashMap`](https://docs.oracle.com/javase/8/docs/api/java/util/IdentityHashMap.html). 
`EnumMap`, which is internally implemented as an `array`, is a high-performance `Map` implementation for use with enum keys. 
This implementation combines the richness and safety of the `Map` interface with a speed approaching that of an array. 
If you want to map an enum to a value, you should always use an `EnumMap` in preference to an array.


有三种特殊用途的 Map 实现 —— [`EnumMap`](https://docs.oracle.com/javase/8/docs/api/java/util/EnumMap.html) 、 [`WeakHashMap`](https://docs.oracle.com/javase/8/docs/api/java/util/WeakHashMap.html) 和 [`IdentityHashMap`](https://docs.oracle.com/javase/8/docs/api/java/util/IdentityHashMap.html) 。
`EnumMap` 内部实现为 `array`，是一个高性能的 `Map` 实现，用于枚举键。
这个实现结合了 Map 接口的丰富性和安全性以及接近数组的速度。
如果你想将一个枚举映射到一个值，你应该总是使用一个 `EnumMap` 而不是一个数组。


`WeakHashMap` is an implementation of the `Map` interface that stores only weak references to its keys. 
Storing only weak references allows a key-value pair to be garbage-collected when its key is no longer referenced outside of the `WeakHashMap`. 
This class provides the easiest way to harness the power of weak references. 
It is useful for implementing "registry-like" data structures, where the utility of an entry vanishes when its key is no longer reachable by any thread.


`WeakHashMap` 是 `Map` 接口的一个实现，它只存储对其键的弱引用。
仅存储弱引用允许在 `WeakHashMap` 之外不再引用其键时对键值对进行垃圾收集。
此类提供了利用弱引用功能的最简单方法。
它对于实现“类似注册表”的数据结构很有用，当任何线程不再能够访问条目的键时，条目的实用程序就会消失。


`IdentityHashMap` is an identity-based `Map` implementation based on a hash table. 
This class is useful for topology-preserving object graph transformations, such as serialization or deep-copying. 
To perform such transformations, you need to maintain an identity-based "node table" that keeps track of which objects have already been seen. 
Identity-based maps are also used to maintain object-to-meta-information mappings in dynamic debuggers and similar systems. 
Finally, identity-based maps are useful in thwarting "spoof attacks" that are a result of intentionally perverse `equals` methods because `IdentityHashMap` never invokes the `equals` method on its keys. 
An added benefit of this implementation is that it is fast.


`IdentityHashMap` 是基于哈希表的基于身份的 `Map` 实现。
此类对于保留拓扑的对象图转换很有用，例如序列化或深度复制。
要执行此类转换，您需要维护一个基于身份的“节点表”，以跟踪哪些对象已被看到。
基于身份的映射也用于维护动态调试器和类似系统中的对象到元信息的映射。
最后，基于身份的映射在阻止“欺骗攻击”方面很有用，这种攻击是故意不正当的 `equals` 方法的结果，因为 `IdentityHashMap` 永远不会在其键上调用 `equals` 方法。
这种实现的另一个好处是速度很快。


## Concurrent `Map` Implementations _并发 `Map` 实现_


The [`java.util.concurrent`](https://docs.oracle.com/javase/8/docs/api/java/util/concurrent/package-summary.html) package contains the [`ConcurrentMap`](https://docs.oracle.com/javase/8/docs/api/java/util/concurrent/ConcurrentMap.html) interface, which extends `Map` with atomic `putIfAbsent`, `remove`, and `replace` methods, and the [`ConcurrentHashMap`](https://docs.oracle.com/javase/8/docs/api/java/util/concurrent/ConcurrentHashMap.html) implementation of that interface.


[`java.util.concurrent`](https://docs.oracle.com/javase/8/docs/api/java/util/concurrent/package-summary.html) 包包含 [`ConcurrentMap`](https://docs.oracle.com/javase/8/docs/api/java/util/concurrent/ConcurrentMap.html) 接口，它使用原子 `putIfAbsent` 、`remove` 和 `replace` 方法以及 [`ConcurrentHashMap `](https://docs.oracle.com/javase/8/docs/api/java/util/concurrent/ConcurrentHashMap.html) 该接口的实现。


`ConcurrentHashMap` is a highly concurrent, high-performance implementation backed up by a hash table. 
This implementation never blocks when performing retrievals and allows the client to select the concurrency level for updates. 
It is intended as a drop-in replacement for `Hashtable`: in addition to implementing `ConcurrentMap`, it supports all the legacy methods peculiar to `Hashtable`. 
Again, if you don't need the legacy operations, be careful to manipulate it with the `ConcurrentMap` interface.


`ConcurrentHashMap` 是一个由哈希表支持的高并发、高性能实现。
此实现在执行检索时从不阻塞，并允许客户端选择更新的并发级别。
它旨在替代 `Hashtable` ：除了实现 `ConcurrentMap` ，它还支持 `Hashtable` 特有的所有遗留方法。
同样，如果您不需要遗留操作，请小心使用 `ConcurrentMap` 接口对其进行操作。
