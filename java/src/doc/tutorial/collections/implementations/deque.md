# Deque Implementations


The `Deque` interface, pronounced as "deck", represents a double-ended queue. 
The `Deque` interface can be implemented as various types of `Collections`. 
The `Deque` interface implementations are grouped into general-purpose and concurrent implementations.


`Deque` 接口，读作 "deck" ，代表一个双端队列。
`Deque` 接口可以实现为各种类型的 `Collections` 。
`Deque` 接口实现分为通用实现和并发实现。


## General-Purpose `Deque` Implementations _通用 `Deque` 实现_


The general-purpose implementations include `LinkedList` and `ArrayDeque` classes. 
The `Deque` interface supports insertion, removal and retrieval of elements at both ends. 
The [`ArrayDeque`](https://docs.oracle.com/javase/8/docs/api/java/util/ArrayDeque.html) class is the resizeable array implementation of the `Deque` interface, whereas the [`LinkedList`](https://docs.oracle.com/javase/8/docs/api/java/util/LinkedList.html) class is the list implementation.


通用实现包括 `LinkedList` 和 `ArrayDeque` 类。
`Deque` 接口支持两端元素的插入、删除和检索。
[`ArrayDeque`](https://docs.oracle.com/javase/8/docs/api/java/util/ArrayDeque.html) 类是 `Deque` 接口的可调整大小的数组实现，而 [`LinkedList`](https://docs.oracle.com/javase/8/docs/api/java/util/LinkedList.html) 类是列表实现。


The basic insertion, removal and retrieval operations in the `Deque` interface `addFirst`, `addLast`, `removeFirst`, `removeLast`, `getFirst` and `getLast`. 
The method `addFirst` adds an element at the head whereas `addLast` adds an element at the tail of the `Deque` instance.


`Deque` 接口中的基本插入、删除和检索操作 `addFirst` 、 `addLast` 、 `removeFirst` 、 `removeLast` 、 `getFirst` 和 `getLast` 。
方法 `addFirst` 在头部添加一个元素，而 `addLast` 在 `Deque` 实例的尾部添加一个元素。


The `LinkedList` implementation is more flexible than the `ArrayDeque` implementation. 
`LinkedList` implements all optional list operations. 
`null` elements are allowed in the `LinkedList` implementation but not in the `ArrayDeque` implementation.


`LinkedList` 实现比 `ArrayDeque` 实现更灵活。
`LinkedList` 实现了所有可选的列表操作。
在 `LinkedList` 实现中允许使用 `null` 元素，但在 `ArrayDeque` 实现中不允许。


In terms of efficiency, `ArrayDeque` is more efficient than the `LinkedList` for add and remove operation at both ends. 
The best operation in a `LinkedList` implementation is removing the current element during the iteration. 
`LinkedList` implementations are not ideal structures to iterate.


在效率方面， `ArrayDeque` 比 `LinkedList` 更高效，用于两端的添加和删除操作。
`LinkedList` 实现中的最佳操作是在迭代期间删除当前元素。
`LinkedList` 实现不是迭代的理想结构。


The `LinkedList` implementation consumes more memory than the `ArrayDeque` implementation. 
For the `ArrayDeque` instance traversal use any of the following:


`LinkedList` 实现比 `ArrayDeque` 实现消耗更多内存。
对于 `ArrayDeque` 实例遍历，使用以下任何一种：


### foreach


The `foreach` is fast and can be used for all kinds of lists.


`foreach` 速度很快，可用于各种列表。


```text
ArrayDeque<String> aDeque = new ArrayDeque<String>();

. . .
for (String str : aDeque) {
    System.out.println(str);
}
```


### Iterator


The `Iterator` can be used for the forward traversal on all kinds of lists for all kinds of data.


`Iterator` 可用于对各种数据的各种列表进行前向遍历。


```text
ArrayDeque<String> aDeque = new ArrayDeque<String>();
. . .
for (Iterator<String> iter = aDeque.iterator(); iter.hasNext();  ) {
    System.out.println(iter.next());
}
```


The `ArrayDeque` class is used in this tutorial to implement the `Deque` interface. 
The complete code of the example used in this tutorial is available in [`ArrayDequeSample`](https://docs.oracle.com/javase/tutorial/collections/interfaces/examples/ArrayDequeSample.java). 
Both the `LinkedList` and `ArrayDeque` classes do not support concurrent access by multiple threads.


本教程中使用 `ArrayDeque` 类来实现 `Deque` 接口。
本教程中使用的示例的完整代码可在 [`ArrayDequeSample`](examples/ArrayDequeSample.java) 中找到。
`LinkedList` 和 `ArrayDeque` 类都不支持多线程并发访问。


## Concurrent `Deque` Implementations


The [`LinkedBlockingDeque`](https://docs.oracle.com/javase/8/docs/api/java/util/concurrent/LinkedBlockingDeque.html) class is the concurrent implementation of the `Deque` interface. 
If the deque is empty then methods such as `takeFirst` and `takeLast` wait until the element becomes available, and then retrieves and removes the same element. 


[`LinkedBlockingDeque`](https://docs.oracle.com/javase/8/docs/api/java/util/concurrent/LinkedBlockingDeque.html) 类是 `Deque` 接口的并发实现。
如果双端队列为空，则诸如 `takeFirst` 和 `takeLast` 等方法会等到元素可用，然后检索并删除相同的元素。
