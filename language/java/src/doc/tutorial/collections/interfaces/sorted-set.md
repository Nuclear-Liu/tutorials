# The `SortedSet` Interface


A [`SortedSet`](https://docs.oracle.com/javase/8/docs/api/java/util/SortedSet.html) is a [`Set`](https://docs.oracle.com/javase/8/docs/api/java/util/Set.html) that maintains its elements in ascending order, sorted according to the elements' natural ordering or according to a `Comparator` provided at `SortedSet` creation time. 
In addition to the normal `Set` operations, the `SortedSet` interface provides operations for the following:


[`SortedSet`](https://docs.oracle.com/javase/8/docs/api/java/util/SortedSet.html) 是 [`Set`](https://docs.oracle.com/javase/8/docs/api/java/util/Set.html) 以升序维护其元素，根据元素的自然顺序或根据在 `SortedSet` 创建时提供的 `Comparator` 排序。
除了常规的 `Set` 操作外，`SortedSet` 接口还提供以下操作：


* `Range view` — allows arbitrary range operations on the sorted set
* `Endpoints` — returns the first or last element in the sorted set
* `Comparator access` — returns the `Comparator`, if any, used to sort the set


* `Range view` — 允许对排序集进行任意范围操作
* `Endpoints` — 返回排序集中的第一个或最后一个元素
* `Comparator access` — 返回用于对集合进行排序的 `Comparator` （如果有）


The code for the `SortedSet` interface follows.


`SortedSet` 接口的代码如下。


```java
public interface SortedSet<E> extends Set<E> {
    // Range-view
    SortedSet<E> subSet(E fromElement, E toElement);
    SortedSet<E> headSet(E toElement);
    SortedSet<E> tailSet(E fromElement);

    // Endpoints
    E first();
    E last();

    // Comparator access
    Comparator<? super E> comparator();
}
```


## `Set` Operations _`Set` 操作_


The operations that `SortedSet` inherits from `Set` behave identically on sorted sets and normal sets with two exceptions:


`SortedSet` 从 `Set` 继承的操作在有序集合和普通集合上表现相同，但有两个例外：


* The `Iterator` returned by the `iterator` operation traverses the sorted set in order.

* `iterator` 操作返回的 `Iterator` 按顺序遍历有序集合。

* The array returned by `toArray` contains the sorted set's elements in order.

* `toArray` 返回的数组按顺序包含已排序集合的元素。


Although the interface doesn't guarantee it, the `toString` method of the Java platform's `SortedSet` implementations returns a string containing all the elements of the sorted set, in order.


尽管接口不保证，Java 平台的 `SortedSet` 实现的 `toString` 方法会按顺序返回一个包含已排序集合的所有元素的字符串。


## Standard Constructors _标准构造函数_


By convention, all general-purpose `Collection` implementations provide a standard conversion constructor that takes a `Collection`; `SortedSet` implementations are no exception. 
In `TreeSet`, this constructor creates an instance that sorts its elements according to their natural ordering. 
This was probably a mistake. 
It would have been better to check dynamically to see whether the specified collection was a `SortedSet` instance and, if so, to sort the new `TreeSet` according to the same criterion (comparator or natural ordering). 
Because `TreeSet` took the approach that it did, it also provides a constructor that takes a `SortedSet` and returns a new `TreeSet` containing the same elements sorted according to the same criterion. 
Note that it is the compile-time type of the argument, not its runtime type, that determines which of these two constructors is invoked (and whether the sorting criterion is preserved).


按照惯例，所有通用的 `Collection` 实现都提供了一个标准的转换构造函数，该构造函数采用 `Collection` ； `SortedSet` 实现也不例外。
在 `TreeSet` 中，此构造函数创建一个实例，该实例根据元素的自然顺序对其元素进行排序。
这可能是一个错误。
最好动态检查指定的集合是否为 `SortedSet` 实例，如果是，则根据相同的标准（比较器或自然排序）对新的 `TreeSet` 进行排序。
因为 `TreeSet` 采用了它所做的方法，所以它还提供了一个构造函数，该构造函数接受 `SortedSet` 并返回一个新的 `TreeSet`，其中包含根据相同标准排序的相同元素。
请注意，决定调用这两个构造函数中的哪一个（以及是否保留排序标准）的是参数的编译时类型，而不是其运行时类型。


`SortedSet` implementations also provide, by convention, a constructor that takes a `Comparator` and returns an empty set sorted according to the specified `Comparator`. 
If `null` is passed to this constructor, it returns a set that sorts its elements according to their natural ordering.


按照惯例， `SortedSet` 实现还提供了一个构造函数，它接受一个 `Comparator` 并返回一个根据指定的 `Comparator` 排序的空集。
如果 `null` 被传递给此构造函数，它会返回一个集合，该集合根据元素的自然顺序对其元素进行排序。


## Range-view Operations _Range-view 操作_


The `range-view` operations are somewhat analogous to those provided by the `List` interface, but there is one big difference. 
Range views of a sorted set remain valid even if the backing sorted set is modified directly. 
This is feasible because the endpoints of a range view of a sorted set are absolute points in the element space rather than specific elements in the backing collection, as is the case for lists. 
A `range-view` of a sorted set is really just a window onto whatever portion of the set lies in the designated part of the element space. 
Changes to the `range-view` write back to the backing sorted set and vice versa. 
Thus, it's okay to use `range-view`s on sorted sets for long periods of time, unlike `range-view`s on lists.


`range-view` 操作有点类似于 `List` 接口提供的操作，但有一个很大的不同。
即使直接修改支持排序集，排序集的范围视图仍然有效。
这是可行的，因为有序集合的范围视图的端点是元素空间中的绝对点，而不是支持集合中的特定元素，就像列表的情况一样。
排序集的 `range-view` 实际上只是一个窗口，可以看到位于元素空间指定部分的集合的任何部分。
对 `range-view` 的更改写回支持排序集，反之亦然。
因此，与列表上的 `range-view` 不同，长时间在排序集上使用 `range-view` 是可以的。


Sorted sets provide three `range-view` operations. 
The first, `subSet`, takes two endpoints, like `subList`. 
Rather than indices, the endpoints are objects and must be comparable to the elements in the sorted set, using the `Set`'s `Comparator` or the natural ordering of its elements, whichever the `Set` uses to order itself. 
Like `subList`, the range is half open, including its low endpoint but excluding the high one.


排序集提供三种 `range-view` 操作。
第一个， `subSet` ，有两个端点，比如 `subList` 。
端点不是索引，而是对象，并且必须与已排序集合中的元素进行比较，使用 `Set` 的 `Comparator` 或其元素的自然顺序，无论 `Set` 使用哪个来对自身进行排序。
像 `subList` 一样，范围是半开的，包括它的低端点但不包括高端点。


Thus, the following line of code tells you how many words between `"doorbell"` and `"pickle"`, including `"doorbell"` but excluding `"pickle"`, are contained in a `SortedSet` of strings called `dictionary`:


因此，下面的代码行告诉您在 `"doorbell"` 和 `"pickle"` 之间有多少单词，包括 `"doorbell"` 但不包括 `"pickle"`，包含在名为 `SortedSet` 的字符串中字典`：


`int count = dictionary.subSet("doorbell", "pickle").size();`


In like manner, the following one-liner removes all the elements beginning with the letter `f`.


以同样的方式，下面的单行删除所有以字母 `f` 开头的元素。


`dictionary.subSet("f", "g").clear();`


A similar trick can be used to print a table telling you how many words begin with each letter.


可以使用类似的技巧来打印一张表格，告诉您每个字母开头有多少个单词。


```text
for (char ch = 'a'; ch <= 'z'; ) {
    String from = String.valueOf(ch++);
    String to = String.valueOf(ch);
    System.out.println(from + ": " + dictionary.subSet(from, to).size());
}
```


Suppose you want to view a closed interval, which contains both of its endpoints, instead of an open interval. 
If the element type allows for the calculation of the successor of a given value in the element space, merely request the `subSet` from `lowEndpoint` to `successor(highEndpoint)`. 
Although it isn't entirely obvious, the successor of a string `s` in `String`'s natural ordering is `s + "\0"` — that is, `s` with a `null` character appended.


假设您要查看包含两个端点的闭区间，而不是开区间。
如果元素类型允许计算元素空间中给定值的后继，只需请求从 `lowEndpoint` 到 `successor(highEndpoint)` 的 `subSet` 。
虽然不是很明显，但在 `String` 的自然顺序中，字符串 `s` 的后继是 `s + "\0"` — 即，附加了一个 `null` 字符的 `s` 。


Thus, the following one-liner tells you how many words between `"doorbell"` and `"pickle"`, including `doorbell` and `pickle`, are contained in the dictionary.


因此，下面的单行代码告诉您字典中包含了 `"doorbell"` 和 `"pickle"` 之间的单词数量，包括 `doorbell` 和 `pickle` 。


`count = dictionary.subSet("doorbell", "pickle\0").size();`


A similar technique can be used to view an _open interval_, which contains neither endpoint. 
The open-interval view from `lowEndpoint` to `highEndpoint` is the half-open interval from `successor(lowEndpoint)` to `highEndpoint`. 
Use the following to calculate the number of words between `"doorbell"` and `"pickle"`, excluding both.


可以使用类似的技术来查看 _open interval_ ，它不包含端点。
从 `lowEndpoint` 到 `highEndpoint` 的开区间视图是从 `successor(lowEndpoint)` 到 `highEndpoint` 的半开区间。
使用下面的方法来计算 `"doorbell"` 和 `"pickle"` 之间的单词数，不包括两者。


`count = dictionary.subSet("doorbell\0", "pickle").size();`


The `SortedSet` interface contains two more `range-view` operations — `headSet` and `tailSet`, both of which take a single `Object` argument. 
The former returns a view of the initial portion of the backing `SortedSet`, up to but not including the specified object. 
The latter returns a view of the final portion of the backing `SortedSet`, beginning with the specified object and continuing to the end of the backing `SortedSet`. 
Thus, the following code allows you to view the dictionary as two disjoint `volumes` (`a-m` and `n-z`).


`SortedSet` 接口包含另外两个 `range-view` 操作 —— `headSet` 和 `tailSet` ，它们都接受一个 `Object` 参数。
前者返回支持 `SortedSet` 的初始部分的视图，最多但不包括指定的对象。
后者返回支持 `SortedSet` 的最后部分的视图，从指定的对象开始并继续到支持 `SortedSet` 的末尾。
因此，以下代码允许您将字典视为两个不相交的 `volumes` （ `a-m` 和 `n-z` ）。


```text
SortedSet<String> volume1 = dictionary.headSet("n");
SortedSet<String> volume2 = dictionary.tailSet("n");
```


## Endpoint Operations _端点操作_


The `SortedSet` interface contains operations to return the first and last elements in the sorted set, not surprisingly called `first` and `last`. 
In addition to their obvious uses, `last` allows a workaround for a deficiency in the `SortedSet` interface. 
One thing you'd like to do with a `SortedSet` is to go into the interior of the `Set` and iterate forward or backward. 
It's easy enough to go forward from the interior: 
Just get a `tailSet` and iterate over it. 
Unfortunately, there's no easy way to go backward.


`SortedSet` 接口包含返回排序集中第一个和最后一个元素的操作，这并不奇怪称为 `first` 和 `last` 。
除了它们明显的用途之外， `last` 还允许解决 `SortedSet` 接口中的缺陷。
您希望对 `SortedSet` 做的一件事是进入 `Set` 的内部并向前或向后迭代。
从内部前进很容易：
只需获取一个 `tailSet` 并对其进行迭代。
不幸的是，没有简单的方法可以倒退。


The following idiom obtains the first element that is less than a specified object `o` in the element space.


以下成语获取元素空间中小于指定对象 `o` 的第一个元素。


`Object predecessor = ss.headSet(o).last();`


This is a fine way to go one element backward from a point in the interior of a sorted set. 
It could be applied repeatedly to iterate backward, but this is very inefficient, requiring a lookup for each element returned.


这是从有序集合内部的一个点向后移动一个元素的好方法。
它可以重复应用以向后迭代，但这非常低效，需要查找返回的每个元素。


## Comparator Accessor _比较器访问器_


The `SortedSet` interface contains an accessor method called `comparator` that returns the `Comparator` used to sort the set, or `null` if the set is sorted according to the _natural ordering_ of its elements. 
This method is provided so that sorted sets can be copied into new sorted sets with the same ordering. 
It is used by the `SortedSet` constructor described [previously](https://docs.oracle.com/javase/tutorial/collections/interfaces/sorted-set.html#constructor).


`SortedSet` 接口包含一个名为 `comparator` 的访问器方法，该方法返回用于对集合进行排序的 `Comparator` ，如果集合根据其元素的*自然排序*排序，则返回 `null` 。
提供此方法以便可以将排序集复制到具有相同顺序的新排序集。
[previous](./sorted-set.md#standard-constructors-__) 中描述的 `SortedSet` 构造函数使用它。
