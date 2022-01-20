# The Queue Interface


A [`Queue`](https://docs.oracle.com/javase/8/docs/api/java/util/Queue.html) is a collection for holding elements prior to processing. 
Besides basic `Collection` operations, queues provide additional insertion, removal, and inspection operations. 
The `Queue` interface follows.


[`Queue`](https://docs.oracle.com/javase/8/docs/api/java/util/Queue.html) 是在处理之前保存元素的集合。
除了基本的 `Collection` 操作外，队列还提供额外的插入、删除和检查操作。
`Queue` 接口如下。


```java
public interface Queue<E> extends Collection<E> {
    E element();
    boolean offer(E e);
    E peek();
    E poll();
    E remove();
}
```


Each `Queue` method exists in two forms: (1) one throws an exception if the operation fails, and (2) the other returns a special value if the operation fails (either `null` or `false`, depending on the operation). 
The regular structure of the interface is illustrated in the following table.


每个 `Queue` 方法以两种形式存在：（1）如果操作失败则抛出异常，（2）如果操作失败，则另一种返回特殊值（ `null` 或 `false` ，取决于操作）。
接口的常规结构如下表所示。


**Queue Interface Structure**

| Type of Operation | Throws exception | Returns special value |
| ---- | ---- | ---- |
| Insert | `add(e)` | `offer(e)` |
| Remove | `remove()` | `poll()` |
| Examine | `element()` | `peek()` |


**队列接口结构**

| 操作类型 | 抛出异常 | 返回特殊值 |
| ---- | ---- | ---- |
| Insert | `add(e)` | `offer(e)` |
| Remove | `remove()` | `poll()` |
| Examine | `element()` | `peek()` |


Queues typically, but not necessarily, order elements in a FIFO (first-in-first-out) manner. 
Among the exceptions are priority queues, which order elements according to their values — see the [Object Ordering](https://docs.oracle.com/javase/tutorial/collections/interfaces/order.html) section for details. 
Whatever ordering is used, the head of the queue is the element that would be removed by a call to `remove` or `poll`. 
In a FIFO queue, all new elements are inserted at the tail of the queue. 
Other kinds of queues may use different placement rules. 
Every `Queue` implementation must specify its ordering properties.


队列通常但不一定以 FIFO （先进先出）方式对元素进行排序。
其中的例外是优先级队列，它根据元素的值对元素进行排序 —— 有关详细信息，请参阅 [Object Ordering](order.md) 部分。
无论使用什么排序，队列的头部都是将通过调用 `remove` 或 `poll` 删除的元素。
在 FIFO 队列中，所有新元素都插入到队列的尾部。
其他类型的队列可能使用不同的放置规则。
每个 `Queue` 实现都必须指定其排序属性。


It is possible for a `Queue` implementation to restrict the number of elements that it holds; such queues are known as bounded. 
Some `Queue` implementations in `java.util.concurrent` are bounded, but the implementations in `java.util` are not.


`Queue` 实现可以限制它所拥有的元素数量；这样的队列称为有界队列。
`java.util.concurrent` 中的一些 `Queue` 实现是有界的，但 `java.util` 中的实现没有。


The `add` method, which `Queue` inherits from `Collection`, inserts an element unless it would violate the queue's capacity restrictions, in which case it throws `IllegalStateException`. 
The `offer` method, which is intended solely for use on bounded queues, differs from `add` only in that it indicates failure to insert an element by returning `false`.


`Queue` 继承自 `Collection` 的 `add` 方法插入一个元素，除非它违反队列的容量限制，在这种情况下它会抛出 `IllegalStateException`。
`offer` 方法仅用于有界队列，与 `add` 不同之处仅在于它通过返回 `false` 指示插入元素失败。


The `remove` and `poll` methods both remove and return the head of the queue. 
Exactly which element gets removed is a function of the queue's ordering policy. 
The `remove` and `poll` methods differ in their behavior only when the queue is empty. 
Under these circumstances, `remove` throws `NoSuchElementException`, while `poll` returns `null`.


`remove` 和 `poll` 方法都删除并返回队列的头部。
究竟哪个元素被删除是队列排序策略的函数。
`remove` 和 `poll` 方法仅在队列为空时的行为不同。
在这种情况下， `remove` 会抛出 `NoSuchElementException` ，而 `poll` 会返回 `null` 。


The `element` and `peek` methods return, but do not remove, the head of the queue. 
They differ from one another in precisely the same fashion as `remove` and `poll`: If the queue is empty, `element` throws `NoSuchElementException`, while `peek` returns `null`.


`element` 和 `peek` 方法返回但不移除队列的头部。
它们之间的区别与 `remove` 和 `poll` 完全相同：如果队列为空，则 `element` 会抛出 `NoSuchElementException`，而 `peek` 返回 `null`。


`Queue` implementations generally do not allow insertion of `null` elements. 
The `LinkedList` implementation, which was retrofitted to implement `Queue`, is an exception. 
For historical reasons, it permits `null` elements, but you should refrain from taking advantage of this, because `null` is used as a special return value by the `poll` and `peek` methods.


`Queue` 实现通常不允许插入 `null` 元素。
被改造为实现 Queue 的 LinkedList 实现是一个例外。
由于历史原因，它允许 `null` 元素，但您应该避免利用这一点，因为 `poll` 和 `peek` 方法使用 `null` 作为特殊返回值。


Queue implementations generally do not define element-based versions of the `equals` and `hashCode` methods but instead inherit the identity-based versions from `Object`.


队列实现通常不定义 `equals` 和 `hashCode` 方法的基于元素的版本，而是从 `Object` 继承基于身份的版本。


The `Queue` interface does not define the blocking queue methods, which are common in concurrent programming. 
These methods, which wait for elements to appear or for space to become available, are defined in the interface [`java.util.concurrent.BlockingQueue`](https://docs.oracle.com/javase/8/docs/api/java/util/concurrent/BlockingQueue.html), which extends `Queue`.


`Queue` 接口没有定义并发编程中常见的阻塞队列方法。
这些等待元素出现或空间可用的方法在接口 [`java.util.concurrent.BlockingQueue`](https://docs.oracle.com/javase/8/docs/api/java/util/concurrent/BlockingQueue.html) 中定义，它扩展了 `Queue` 。


In the following example program, a queue is used to implement a countdown timer. 
The queue is preloaded with all the integer values from a number specified on the command line to zero, in descending order. 
Then, the values are removed from the queue and printed at one-second intervals. 
The program is artificial in that it would be more natural to do the same thing without using a queue, but it illustrates the use of a queue to store elements prior to subsequent processing.


在下面的示例程序中，队列用于实现倒数计时器。
队列预加载了从命令行上指定的数字到零的所有整数值，按降序排列。
然后，这些值将从队列中删除并以一秒的间隔打印。
该程序是人为的，因为在不使用队列的情况下做同样的事情会更自然，但它说明了在后续处理之前使用队列来存储元素。


```java
import java.util.*;

public class Countdown {
    public static void main(String[] args) throws InterruptedException {
        int time = Integer.parseInt(args[0]);
        Queue<Integer> queue = new LinkedList<Integer>();

        for (int i = time; i >= 0; i--)
            queue.add(i);

        while (!queue.isEmpty()) {
            System.out.println(queue.remove());
            Thread.sleep(1000);
        }
    }
}
```


In the following example, a priority queue is used to sort a collection of elements. 
Again this program is artificial in that there is no reason to use it in favor of the `sort` method provided in `Collections`, but it illustrates the behavior of priority queues.


在以下示例中，优先级队列用于对元素集合进行排序。
同样，这个程序是人为的，因为没有理由使用它来支持 `Collections` 中提供的 `sort` 方法，但它说明了优先级队列的行为。


```text
static <E> List<E> heapSort(Collection<E> c) {
    Queue<E> queue = new PriorityQueue<E>(c);
    List<E> result = new ArrayList<E>();

    while (!queue.isEmpty())
        result.add(queue.remove());

    return result;
}
```
