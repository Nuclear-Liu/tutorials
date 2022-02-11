# The SortedMap Interface


A [`SortedMap`](https://docs.oracle.com/javase/8/docs/api/java/util/SortedMap.html) is a [`Map`](https://docs.oracle.com/javase/8/docs/api/java/util/Map.html) that maintains its entries in ascending order, sorted according to the keys' natural ordering, or according to a `Comparator` provided at the time of the `SortedMap` creation. 
Natural ordering and `Comparators` are discussed in the [Object Ordering](https://docs.oracle.com/javase/tutorial/collections/interfaces/order.html) section. 
The `SortedMap` interface provides operations for normal `Map` operations and for the following:


[`SortedMap`](https://docs.oracle.com/javase/8/docs/api/java/util/SortedMap.html) 是一个 [`Map`](https://docs.oracle.com/javase/8/docs/api/java/util/Map.html) 以升序维护其条目，根据键的自然顺序或根据 `SortedMap` 时提供的 `Comparator` 排序创建。
自然排序和 `Comparators` 在 [Object Ordering](order.md) 部分中讨论。
`SortedMap` 接口为普通的 `Map` 操作和以下操作提供操作：


* `Range view` — performs arbitrary range operations on the sorted map

* `Range view` — 对有序 map 执行任意范围操作

* `Endpoints` — returns the first or the last key in the sorted map

* `Endpoints` — 返回排序 map 中的第一个或最后一个键

* `Comparator access` — returns the `Comparator`, if any, used to sort the map

* `Comparator access` — 返回用于对 map 进行排序的 `Comparator` （如果有）


The following interface is the `Map` analog of [`SortedSet`](https://docs.oracle.com/javase/8/docs/api/java/util/SortedSet.html).


以下接口是 [`SortedSet`](https://docs.oracle.com/javase/8/docs/api/java/util/SortedSet.html) 的 `Map` 类比。


```java
public interface SortedMap<K, V> extends Map<K, V>{
    Comparator<? super K> comparator();
    SortedMap<K, V> subMap(K fromKey, K toKey);
    SortedMap<K, V> headMap(K toKey);
    SortedMap<K, V> tailMap(K fromKey);
    K firstKey();
    K lastKey();
}
```


## Map Operations _Map 操作_


The operations `SortedMap` inherits from `Map` behave identically on sorted maps and normal maps with two exceptions:


从 `Map` 继承的 `SortedMap` 操作在排序的 map 和普通的 map 上的行为相同，但有两个例外：


* The `Iterator` returned by the `iterator` operation on any of the sorted map's `Collection` views traverse the collections in order.

* 任何排序的 map 的 `Collection` 视图上的 `iterator` 操作返回的 `Iterator` 按顺序遍历集合。

* The arrays returned by the `Collection` views' `toArray` operations contain the keys, values, or entries in order.

* `Collection` 视图的 `toArray` 操作返回的数组按顺序包含键、值或条目。


Although it isn't guaranteed by the interface, the `toString` method of the `Collection` views in all the Java platform's `SortedMap` implementations returns a string containing all the elements of the view, in order.


尽管接口不保证，但在所有 Java 平台的 `SortedMap` 实现中， `Collection` 视图的 `toString` 方法会按顺序返回包含视图所有元素的字符串。


## Standard Constructors _标准构造函数_


By convention, all general-purpose `Map` implementations provide a standard conversion constructor that takes a `Map`; `SortedMap` implementations are no exception. 
In `TreeMap`, this constructor creates an instance that orders its entries according to their keys' natural ordering. 
This was probably a mistake. 
It would have been better to check dynamically to see whether the specified `Map` instance was a `SortedMap` and, if so, to sort the new map according to the same criterion (comparator or natural ordering). 
Because `TreeMap` took the approach it did, it also provides a constructor that takes a `SortedMap` and returns a new `TreeMap` containing the same mappings as the given `SortedMap`, sorted according to the same criterion. 
Note that it is the compile-time type of the argument, not its runtime type, that determines whether the `SortedMap` constructor is invoked in preference to the ordinary `map` constructor.


按照惯例，所有通用的 `Map` 实现都提供了一个标准的转换构造函数，该构造函数采用 `Map` ； `SortedMap` 的实现也不例外。
在 `TreeMap` 中，此构造函数创建一个实例，该实例根据其键的自然顺序对其条目进行排序。
这可能是一个错误。
最好动态检查指定的 `Map` 实例是否是 `SortedMap`，如果是，则根据相同的标准（比较器或自然排序）对新地图进行排序。
因为 `TreeMap` 采用了它所做的方法，它还提供了一个构造函数，该构造函数接受 `SortedMap` 并返回一个新的 `TreeMap` ，其中包含与给定 `SortedMap` 相同的映射，根据相同的标准排序。
请注意，它是参数的编译时类型，而不是其运行时类型，它决定了 `SortedMap` 构造函数是否优先于普通的 `map` 构造函数被调用。


`SortedMap` implementations also provide, by convention, a constructor that takes a `Comparator` and returns an empty map sorted according to the specified `Comparator`. 
If `null` is passed to this constructor, it returns a `Map` that sorts its mappings according to their keys' natural ordering.


按照惯例， `SortedMap` 实现还提供了一个构造函数，该构造函数接受一个 `Comparator` 并返回一个根据指定 `Comparator` 排序的空映射。
如果 `null` 被传递给这个构造函数，它会返回一个 `Map` ，它根据键的自然顺序对其映射进行排序。


## Comparison to `SortedSet` _与 `SortedSet` 的比较_


Because this interface is a precise `Map` analog of `SortedSet`, all the idioms and code examples in [The SortedSet Interface](https://docs.oracle.com/javase/tutorial/collections/interfaces/sorted-set.html) section apply to `SortedMap` with only trivial modifications.


因为这个接口是 `SortedSet` 的精确 `Map` 模拟，所以 [The SortedSet Interface](sorted-set.md) 部分中的所有习语和代码示例都适用于 `SortedMap`，只需进行细微的修改。
