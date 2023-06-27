# Disruptor

> 论文： [LMAX Disruptor: High performance alternative to bounded queues for exchanging data between concurrent threads](./Technical%20Paper.md)

利用 Java 平台实现非常低延迟和高吞吐量。
性能测试表明，使用**队列**在系统各个阶段之间传递数据会引入延迟，因此重点优化该领域。

Disruptor 是一种通用机制，用于解决并发编程中的难题。

[用户手册](./UserGuide.md)
