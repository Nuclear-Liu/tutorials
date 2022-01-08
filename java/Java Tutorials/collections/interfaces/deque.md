# The Deque Interface


Usually pronounced as `deck`, a deque is a double-ended-queue. 
A double-ended-queue is a linear collection of elements that supports the insertion and removal of elements at both end points. 
The `Deque` interface is a richer abstract data type than both `Stack` and `Queue` because it implements both stacks and queues at the same time. 
The `Deque` interface, defines methods to access the elements at both ends of the `Deque` instance. 
Methods are provided to insert, remove, and examine the elements. 
Predefined classes like `ArrayDeque` and `LinkedList` implement the `Deque` interface.


通常发音为 `deck` ，双端队列是一个双端队列。
双端队列是元素的线性集合，支持在两个端点插入和删除元素。
`Deque` 接口是比 `Stack` 和 `Queue` 更丰富的抽象数据类型，因为它同时实现了堆栈和队列。
`Deque` 接口，定义了访问 `Deque` 实例两端元素的方法。
提供了插入、删除和检查元素的方法。
`ArrayDeque` 和 `LinkedList` 等预定义类实现了 `Deque` 接口。


Note that the `Deque` interface can be used both as last-in-first-out stacks and first-in-first-out queues. 
The methods given in the `Deque` interface are divided into three parts:


请注意， `Deque` 接口既可以用作后进先出堆栈，也可以用作先进先出队列。
`Deque` 接口中给出的方法分为三部分：


## Insert _插入_


The `addFirst` and `offerFirst` methods insert elements at the beginning of the `Deque` instance. 
The methods `addLast` and `offerLast` insert elements at the end of the `Deque` instance. 
When the capacity of the `Deque` instance is restricted, the preferred methods are `offerFirst` and `offerLast` because `addFirst` might fail to throw an exception if it is full.


`addFirst` 和 `offerFirst` 方法在 `Deque` 实例的开头插入元素。
`addLast` 和 `offerLast` 方法在 `Deque` 实例的末尾插入元素。
当 `Deque` 实例的容量受限时，首选方法是 `offerFirst` 和 `offerLast` ，因为如果 `addFirst` 已满，可能无法抛出异常。


## Remove _移除_


The `removeFirst` and `pollFirst` methods remove elements from the beginning of the `Deque` instance. 
The `removeLast` and `pollLast` methods remove elements from the end. 
The methods `pollFirst` and `pollLast` return `null` if the `Deque` is empty whereas the methods `removeFirst` and `removeLast` throw an exception if the `Deque` instance is empty.


`removeFirst` 和 `pollFirst` 方法从 `Deque` 实例的开头删除元素。
`removeLast` 和 `pollLast` 方法从末尾删除元素。
如果 `Deque` 为空，则方法 `pollFirst` 和 `pollLast` 返回 `null` ，而如果 `Deque` 实例为空，则方法 `removeFirst` 和 `removeLast` 抛出异常


## Retrieve _检索_


The methods `getFirst` and `peekFirst` retrieve the first element of the `Deque` instance. 
These methods dont remove the value from the `Deque` instance. 
Similarly, the methods `getLast` and `peekLast` retrieve the last element. 
The methods `getFirst` and `getLast` throw an exception if the `deque` instance is empty whereas the methods `peekFirst` and `peekLast` return `NULL`.


`getFirst` 和 `peekFirst` 方法检索 `Deque` 实例的第一个元素。
这些方法不会从 `Deque` 实例中删除值。
类似地， `getLast` 和 `peekLast` 方法检索最后一个元素。
如果 `deque` 实例为空， `getFirst` 和 `getLast` 方法会抛出异常，而 `peekFirst` 和 `peekLast` 方法返回 `NULL` 。


The 12 methods for insertion, removal and retrieval of Deque elements are summarized in the following table: 


Deque 元素的 12 种插入、移除和检索方法总结如下表：


**Deque Methods**

| Type of Operation | First Element (Beginning of the `Deque` instance) | Last Element (End of the `Deque` instance) |
|-------------------| ---- |--------------------------------------------|
| **Insert**        | `addFirst(e)` <br\> `offerFirst(e)` | `addLast(e)` <br\> `offerLast(e)` |
| **Remove**        | `removeFirst()` <br\> `pollFirst()` | `removeLast()` <br\> `pollLast()` |
| **Examine**       | `getFirst()` <br\> `peekFirst()` | `getLast()` <br\> `peekLast()` |


**Deque 方法**

| 操作类型        | 第一个元素（ `Deque` 实例的开始） | 最后一个元素（ `Deque` 实例的结尾） |
|-------------|-------------------------------------|-----------------------------------|
| **插入** | `addFirst(e)` <br\> `offerFirst(e)` | `addLast(e)` <br\> `offerLast(e)` |
| **移除** | `removeFirst()` <br\> `pollFirst()` | `removeLast()` <br\> `pollLast()` |
| **检查** | `getFirst()` <br\> `peekFirst()` | `getLast()` <br\> `peekLast()` |


In addition to these basic methods to insert,remove and examine a `Deque` instance, the `Deque` interface also has some more predefined methods. 
One of these is `removeFirstOccurence`, this method removes the first occurrence of the specified element if it exists in the `Deque` instance. 
If the element does not exist then the `Deque` instance remains unchanged. 
Another similar method is `removeLastOccurence`; this method removes the last occurrence of the specified element in the `Deque` instance. 
The return type of these methods is `boolean`, and they return `true` if the element exists in the `Deque` instance.


除了插入、删除和检查 `Deque` 实例的这些基本方法外， `Deque` 接口还有一些更多预定义的方法。
其中之一是 `removeFirstOccurence` ，如果指定元素存在于 `Deque` 实例中，此方法将删除第一次出现的指定元素。
如果元素不存在，则 `Deque` 实例保持不变。
另一个类似的方法是 `removeLastOccurence` ；此方法删除 `Deque` 实例中指定元素的最后一次出现。
这些方法的返回类型是 `boolean` ，如果元素存在于 `Deque` 实例中，它们返回 `true` 。
