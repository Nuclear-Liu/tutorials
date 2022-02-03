# Queue Implementations


The `Queue` implementations are grouped into general-purpose and concurrent implementations.


`Queue` 实现分为通用实现和并发实现。


## General-Purpose `Queue` Implementations _通用 `Queue` 实现_


As mentioned in the previous section, `LinkedList` implements the `Queue` interface, providing first in, first out (FIFO) queue operations for `add`, `poll`, and so on.


上一节提到， `LinkedList` 实现了 `Queue` 接口，为 `add` 、 `poll` 等提供先进先出（FIFO）队列操作。


The [`PriorityQueue`](https://docs.oracle.com/javase/8/docs/api/java/util/PriorityQueue.html) class is a priority queue based on the _heap_ data structure. 
This queue orders elements according to the order specified at construction time, which can be the elements' natural ordering or the ordering imposed by an explicit `Comparator`.


[`PriorityQueue`](https://docs.oracle.com/javase/8/docs/api/java/util/PriorityQueue.html) 类是基于 _heap_ 数据结构的优先级队列。
该队列根据构建时指定的顺序对元素进行排序，该顺序可以是元素的自然顺序，也可以是显式 `Comparator` 强加的顺序。


The queue retrieval operations — `poll`, `remove`, `peek`, and `element` — access the element at the head of the queue. 
The _head of the queue_ is the least element with respect to the specified ordering. 
If multiple elements are tied for least value, the head is one of those elements; ties are broken arbitrarily.


队列检索操作 —— `poll` 、 `remove` 、 `peek` 和 `element` —— 访问队列头部的元素。
_队列的头部_ 是相对于指定排序的最小元素。
如果多个元素被捆绑为最小值，则头部是这些元素之一；关系被任意打破。


`PriorityQueue` and its iterator implement all of the optional methods of the `Collection` and `Iterator` interfaces. 
The iterator provided in method `iterator` is not guaranteed to traverse the elements of the `PriorityQueue` in any particular order. 
For ordered traversal, consider using `Arrays.sort(pq.toArray())`.


`PriorityQueue` 及其迭代器实现了 `Collection` 和 `Iterator` 接口的所有可选方法。
方法 `iterator` 中提供的迭代器不能保证以任何特定顺序遍历 `PriorityQueue` 的元素。
对于有序遍历，请考虑使用 `Arrays.sort(pq.toArray())` 。


## Concurrent `Queue` Implementations _并发 `Queue` 实现_


The `java.util.concurrent` package contains a set of synchronized `Queue` interfaces and classes. 
[`BlockingQueue`](https://docs.oracle.com/javase/8/docs/api/java/util/concurrent/BlockingQueue.html) extends `Queue` with operations that wait for the queue to become nonempty when retrieving an element and for space to become available in the queue when storing an element. 
This interface is implemented by the following classes:


`java.util.concurrent` 包包含一组同步的 `Queue` 接口和类。
[`BlockingQueue`](https://docs.oracle.com/javase/8/docs/api/java/util/concurrent/BlockingQueue.html) 扩展了 `Queue` 的操作，在检索元素时等待队列变为非空，并在存储元素时等待队列中的空间可用。
该接口由以下类实现：


* [`LinkedBlockingQueue`](https://docs.oracle.com/javase/8/docs/api/java/util/concurrent/LinkedBlockingQueue.html) — an optionally bounded FIFO blocking queue backed by linked nodes

* [`LinkedBlockingQueue`](https://docs.oracle.com/javase/8/docs/api/java/util/concurrent/LinkedBlockingQueue.html) — 由链接节点支持的可选有界 FIFO 阻塞队列

* [`ArrayBlockingQueue`](https://docs.oracle.com/javase/8/docs/api/java/util/concurrent/ArrayBlockingQueue.html) — a bounded FIFO blocking queue backed by an array

* [`ArrayBlockingQueue`](https://docs.oracle.com/javase/8/docs/api/java/util/concurrent/ArrayBlockingQueue.html) — 由数组支持的有界 FIFO 阻塞队列

* [`PriorityBlockingQueue`](https://docs.oracle.com/javase/8/docs/api/java/util/concurrent/PriorityBlockingQueue.html) — an unbounded blocking priority queue backed by a heap

* [`PriorityBlockingQueue`](https://docs.oracle.com/javase/8/docs/api/java/util/concurrent/PriorityBlockingQueue.html) — 由堆支持的无限阻塞优先级队列

* [`DelayQueue`](https://docs.oracle.com/javase/8/docs/api/java/util/concurrent/DelayQueue.html) — a time-based scheduling queue backed by a heap

* [`DelayQueue`](https://docs.oracle.com/javase/8/docs/api/java/util/concurrent/DelayQueue.html) — 由堆支持的基于时间的调度队列

* [`SynchronousQueue`](https://docs.oracle.com/javase/8/docs/api/java/util/concurrent/SynchronousQueue.html) — a simple rendezvous mechanism that uses the `BlockingQueue` interface

* [`SynchronousQueue`](https://docs.oracle.com/javase/8/docs/api/java/util/concurrent/SynchronousQueue.html) — 使用 `BlockingQueue` 接口的简单集合机制


In JDK 7, [`TransferQueue`](https://docs.oracle.com/javase/8/docs/api/java/util/concurrent/TransferQueue.html) is a specialized `BlockingQueue` in which code that adds an element to the queue has the option of waiting (blocking) for code in another thread to retrieve the element. 
`TransferQueue` has a single implementation:


在 JDK 7 中， [`TransferQueue`](https://docs.oracle.com/javase/8/docs/api/java/util/concurrent/TransferQueue.html) 是一个专门的 `BlockingQueue` ，其中将元素添加到队列的代码可以选择等待（阻塞）另一个线程中的代码来检索该元素。
`TransferQueue` 有一个单一的实现：


* [`LinkedTransferQueue`](https://docs.oracle.com/javase/8/docs/api/java/util/concurrent/LinkedTransferQueue.html) — an unbounded `TransferQueue` based on linked nodes

* [`LinkedTransferQueue`](https://docs.oracle.com/javase/8/docs/api/java/util/concurrent/LinkedTransferQueue.html) — 基于链接节点的无界 `TransferQueue`
