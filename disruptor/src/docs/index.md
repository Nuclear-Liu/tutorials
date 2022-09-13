# Disruptor

Disruptor 中的 DDD(Domain-Driven Design) 域对象：

* `Ring Buffer` : 环形缓冲区，通常被认为是 Disruptor 的核心，从 3.0 版本开始， `Ring Buffer` 负责存储和更新 Disruptor 中的数据（**事件**）。

* `Sequence` : Disruptor 使用 `Sequence` 作为标识特定组件所在位置的方法。每个消费者(**EventProcessor**)都像 Disruptor 本身一样维护一个  `Sequence` 。

  并发代码依赖于 `Sequence` 值的移动，因此 `Sequence` 支持 `AtomicLong` 的许多当前功能。

  > 3.0 版本与 2.0 版本之间唯一的真正区别是防止 `Sequence` 和其他变量之间出现伪共享。

* `Sequencer` : `Sequencer` 是 Disruptor 的真正**核心**。该接口的两个实现（**单生产者**和**多生产者**）实现了所有并发算法，用于在生产者和消费者之间快速、正确地传递数据。

* `Sequence Barrier` : 序列屏障，由 `Sequencer` 产生，包含对 `Sequencer` 中主要发布者的序列 `Sequence` 和任何依赖的消费者的序列 `Sequence` 的引用。它包含了确定是否有可供消费者处理的事件的逻辑。

* `Wait Strategy` : 等待策略，确定消费者如何等待生产者将事件放入 Disruptor 。

* `Event` : 从生产者传递给消费者的数据单位。事件没有特定的代码表示，因为它完全由用户定义。

* `EventProcessor` : 用于处理来自 Disruptor 的事件的主事件循环，并拥有消费者序列的所有权。其有一个名为 `BatchEventProcessor` 的实现，它包含事件循环的有效实现，并将回调使用者提供的 `EventHandler` 接口实现（在线程池内运行 `BatchEventProcessor` 的 `run` 方法）。

* `EventHandler` : 由用户实现并代表 Disruptor 的消费者的接口。

* `Producer` : 调用 Disruptor 以将事件放入队列的用户代码。这个概念在代码中也没有具体表示。

![Disruptor with a set of dependent consumers](./models.bmp)
