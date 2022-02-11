# List Implementations


`List` implementations are grouped into general-purpose and special-purpose implementations.


`List` 实现分为通用实现和专用实现。


## General-Purpose `List` Implementations _通用 `List` 实现_


There are two general-purpose [`List`](https://docs.oracle.com/javase/8/docs/api/java/util/List.html) implementations — [`ArrayList`](https://docs.oracle.com/javase/8/docs/api/java/util/ArrayList.html) and [`LinkedList`](https://docs.oracle.com/javase/8/docs/api/java/util/LinkedList.html). 
Most of the time, you'll probably use `ArrayList`, which offers constant-time positional access and is just plain fast. 
It does not have to allocate a node object for each element in the `List`, and it can take advantage of `System.arraycopy` when it has to move multiple elements at the same time. 
Think of `ArrayList` as `Vector` without the synchronization overhead.


有两种通用的 [`List`](https://docs.oracle.com/javase/8/docs/api/java/util/List.html) 实现 —— [`ArrayList`](https://docs.oracle.com/javase/8/docs/api/java/util/ArrayList.html) 和 [`LinkedList`](https://docs.oracle.com/javase/8/docs/api/java/util/LinkedList.html) 。
大多数时候，您可能会使用 `ArrayList` ，它提供恒定时间的位置访问，而且速度非常快。
它不必为 `List` 中的每个元素分配一个节点对象，当它必须同时移动多个元素时，它可以利用 `System.arraycopy` 。
将 `ArrayList` 视为 `Vector` 没有同步开销。


If you frequently add elements to the beginning of the `List` or iterate over the `List` to delete elements from its interior, you should consider using `LinkedList`. 
These operations require constant-time in a `LinkedList` and linear-time in an `ArrayList`. 
But you pay a big price in performance. 
Positional access requires linear-time in a `LinkedList` and constant-time in an `ArrayList`. 
Furthermore, the constant factor for `LinkedList` is much worse. 
If you think you want to use a `LinkedList`, measure the performance of your application with both `LinkedList` and `ArrayList` before making your choice; `ArrayList` is usually faster.


如果您经常在 `List` 的开头添加元素或遍历 `List` 以从其内部删除元素，则应考虑使用 `LinkedList` 。
这些操作需要 `LinkedList` 中的常数时间和 `ArrayList` 中的线性时间。
但是你在性能上付出了很大的代价。
位置访问需要 `LinkedList` 中的线性时间和 `ArrayList` 中的恒定时间。
此外， `LinkedList` 的常数因子要差得多。
如果您想使用 `LinkedList` ，请在做出选择之前使用 `LinkedList` 和 `ArrayList` 衡量应用程序的性能； `ArrayList` 通常更快。


`ArrayList` has one tuning parameter — the _initial capacity_, which refers to the number of elements the `ArrayList` can hold before it has to grow. 
`LinkedList` has no tuning parameters and seven optional operations, one of which is `clone`. 
The other six are `addFirst`, `getFirst`, `removeFirst`, `addLast`, `getLast`, and `removeLast`. 
`LinkedList` also implements the `Queue` interface.


`ArrayList` 有一个调整参数 - _初始容量_，它指的是 `ArrayList` 在必须增长之前可以容纳的元素数量。
`LinkedList` 没有调优参数和七个可选操作，其中之一是 `clone` 。
其他六个是`addFirst`、`getFirst`、`removeFirst`、`addLast`、`getLast`和`removeLast`。
`LinkedList` 也实现了 `Queue` 接口。


## Special-Purpose `List` Implementations _特殊用途的 `List` 实现_


[`CopyOnWriteArrayList`](https://docs.oracle.com/javase/8/docs/api/java/util/concurrent/CopyOnWriteArrayList.html) is a `List` implementation backed up by a copy-on-write array. 
This implementation is similar in nature to `CopyOnWriteArraySet`. 
No synchronization is necessary, even during iteration, and iterators are guaranteed never to throw `ConcurrentModificationException`. 
This implementation is well suited to maintaining event-handler lists, in which change is infrequent, and traversal is frequent and potentially time-consuming.


[`CopyOnWriteArrayList`](https://docs.oracle.com/javase/8/docs/api/java/util/concurrent/CopyOnWriteArrayList.html) 是由写时复制数组支持的 `List` 实现。
这个实现本质上类似于 `CopyOnWriteArraySet` 。
即使在迭代期间也不需要同步，并且保证迭代器永远不会抛出 `ConcurrentModificationException` 。
此实现非常适合维护事件处理程序列表，其中更改很少，并且遍历很频繁并且可能很耗时。


If you need synchronization, a `Vector` will be slightly faster than an `ArrayList` synchronized with `Collections.synchronizedList`. 
But `Vector` has loads of legacy operations, so be careful to always manipulate the `Vector` with the `List` interface or else you won't be able to replace the implementation at a later time.


如果需要同步， `Vector` 会比与 `Collections.synchronizedList` 同步的 `ArrayList` 稍快。
但是 `Vector` 有很多遗留操作，所以要小心始终使用 `List` 接口来操作 `Vector` ，否则您以后将无法替换实现。


If your `List` is fixed in size — that is, you'll never use `remove`, `add`, or any of the bulk operations other than `containsAll` — you have a third option that's definitely worth considering. 
See `Arrays.asList` in the [Convenience Implementations](https://docs.oracle.com/javase/tutorial/collections/implementations/convenience.html) section for more information.


如果你的 `List` 的大小是固定的——也就是说，你永远不会使用 `remove` 、 `add` 或除 `containsAll` 之外的任何批量操作 —— 那么第三个选项绝对值得考虑。
有关详细信息，请参阅 [Convenience Implementations](./convenience.md) 部分中的 `Arrays.asList`。
