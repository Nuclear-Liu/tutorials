# Disruptor (base: 4.0.0.RC1)

## Disruptor 提供的关键功能：

* Disruptor 中的同一个消息会向所有消费者发送，即**多播能力(Multicast Event)**。

* 为**事件预分配内存(Event Preallocation)**，避免运行时因频繁**回收垃圾**与**内存分配**增加开销。

* **可选择无锁(Optionally)**，使用两阶段协议，让多个线程可以同时修改不同元素。

* **缓存行填充，避免伪共享(prevent false sharing)**。

## Disruptor 中的 DDD(Domain-Driven Design) 域对象：

* `Ring Buffer` : 环形缓冲区，通常被认为是 Disruptor 的核心，从 3.0 版本开始， `Ring Buffer` 负责存储和更新 Disruptor 中的数据（**事件**）。 

  对于某些高级用例，它甚至可以完全由用户替换

* `Sequence` : Disruptor 使用 `Sequence` 作为标识特定组件所在位置的方法。每个消费者(**`EventProcessor`**)都像 Disruptor 本身一样维护一个  `Sequence` 。

  大多数并发代码依赖于 `Sequence` 值的移动，因此 `Sequence` 支持 `AtomicLong` 的许多当前功能。

  > 3.0 版本与 2.0 版本之间唯一的真正区别是防止 `Sequence` 和其他变量之间出现伪共享。

* `Sequencer` : `Sequencer` 是 Disruptor 的真正**核心**。

  该接口的两个实现（**单生产者**和**多生产者**）实现了所有并发算法，用于在生产者和消费者之间快速、正确地传递数据。

* `Sequence Barrier` : 序列屏障，由 `Sequencer` 产生，包含对 `Sequencer` 中主要发布者的序列 `Sequence` 和任何依赖的消费者的序列 `Sequence` 的引用。它包含了确定是否有可供消费者处理的事件的逻辑。

* `Wait Strategy` : 等待策略，确定消费者如何等待生产者将事件放入 Disruptor 。

* `Event` : 从生产者传递给消费者的数据单位。事件没有特定的代码表示，因为它完全由用户定义。

* `EventProcessor` : 用于处理来自 Disruptor 的事件的主事件循环，并拥有消费者序列的所有权。

  其有一个名为 `BatchEventProcessor` 的实现，它包含事件循环的有效实现，并将回调使用者提供的 `EventHandler` 接口实现（在线程池内运行 `BatchEventProcessor` 的 `run` 方法）。

* `EventHandler` : 由用户实现并代表 Disruptor 的消费者的接口。

* `Producer` : 调用 Disruptor 以将**事件**放入队列的用户代码。这个概念在代码中也没有具体表示。


![Disruptor with a set of dependent consumers](./models.bmp)

> 示例： 图中有3个消费者，即日志记录 `JournalConsumer` （将输入数据写入持久性日志文件）、复制 `ReplicationConsumer` （将输入数据发送到另一台机器以确保存在数据的远程副本）和业务逻辑 `ApplicationConsumer` （真正的处理处理工作）。
> 其中 `JournalConsumer` 和 `ReplicationConsumer` 是可以并行执行。
> 
> * `Producer` 向 Disruptor 的 `Ring Buffer` 中写入事件；
> * 消费者 `JournalConsumer` 和 `ReplicationConsumer` 使用多播方式同时消费 `Ring Buffer` 中的每一个元素，两者都有各自的 `SequenceBarrier` 用来控制当前 `Ring Buffer` 消费进度，并且当不存在可消费事件时如何处理(`Wait Strategy` 等待策略)；
> * 消费者 `ApplicationConsumer` 则是等 `JournalComsumer` 和 `ReplicationConsumer` 对用一个元素消费后，在处理该元素。

## 参考资料

* [User Guide](https://lmax-exchange.github.io/disruptor/user-guide/index.html)
* [API documentation](https://lmax-exchange.github.io/disruptor/javadoc/index.html)
* [Frequently Asked Questions](https://github.com/LMAX-Exchange/disruptor/wiki/Frequently-Asked-Questions)
* [Developer Guide](https://lmax-exchange.github.io/disruptor/developer-guide/index.html)


* [Disruptor Google Group](https://groups.google.com/g/lmax-disruptor)
* [Disruptor Paper](https://lmax-exchange.github.io/disruptor/disruptor.html) [Disruptor 论文]()
* [Martin Fowler's Technical Review](https://martinfowler.com/articles/lmax.html)
* [Mechanical Sympathy(Martin Thompson)](https://mechanical-sympathy.blogspot.com/)
* [Bad Concurrency(Michael Barker)](https://bad-concurrency.blogspot.com/)
* [LMAX Staff Blogs](https://www.lmax.com/blog/staff-blogs/)
* [.NET Disruptor Port](https://github.com/disruptor-net/Disruptor-net)
* [LMAX Exchange](https://www.lmax.com/)


* [Disruptor presentation @ QCon SF](https://www.infoq.com/presentations/LMAX/)
* [Introduction to the Disruptor](https://www.slideshare.net/trishagee/introduction-to-the-disruptor)
