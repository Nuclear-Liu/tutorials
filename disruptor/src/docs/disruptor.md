# [LMAX Disruptor: High performance alternative to bounded queues for exchanging data between concurrent threads 用于在并发线程之间交换数据的有界队列的高性能替代品](https://lmax-exchange.github.io/disruptor/disruptor.html)

>  **Martin Thompson** · **Dave Farley** · **Michael Barker** · **Patricia Gee** · **Andrew Stewart** - Version 4.0.0.RC2-SNAPSHOT, May 2011 

[https://github.com/LMAX-Exchange/disruptor](https://github.com/LMAX-Exchange/disruptor)

> **Abstract** **摘要**
> 
> 
> LMAX was established to create a very high performance financial exchange. 
> As part of our work to accomplish this goal we have evaluated several approaches to the design of such a system, but as we began to measure these we ran into some fundamental limits with conventional approaches.
> 
> LMAX 的成立是为了创建一个非常高性能的金融交易所。
> 作为我们实现这一目标的工作的一部分，我们评估了设计这种系统的几种方法，但是当我们开始测量这些方法时，我们遇到了传统方法的一些基本限制。
> 
> 
> Many applications depend on queues to exchange data between processing stages. 
> Our performance testing showed that the latency costs, when using queues in this way, were in the same order of magnitude as the cost of IO operations to disk (RAID or SSD based disk system) – dramatically slow. 
> If there are multiple queues in an end-to-end operation, this will add hundreds of microseconds to the overall latency. 
> There is clearly room for optimisation.
> 
> 许多应用程序依赖队列在处理阶段之间交换数据。
> 我们的性能测试表明，以这种方式使用队列时，延迟成本与磁盘（基于 RAID 或 SSD 的磁盘系统）的 IO 操作成本处于同一数量级 - 非常缓慢。
> 如果端到端操作中有多个队列，这将增加数百微秒的整体延迟。
> 显然存在优化的空间。
> 
> 
> Further investigation and a focus on the computer science made us realise that the conflation of concerns inherent in conventional approaches, (e.g. queues and processing nodes) leads to contention in multi-threaded implementations, suggesting that there may be a better approach.
> 
> 进一步的调查和对计算机科学的关注使我们意识到传统方法（例如队列和处理节点）中固有的关注点的混合导致了多线程实现中的争用，这表明可能有更好的方法。
> 
> 
> Thinking about how modern CPUs work, something we like to call “mechanical sympathy”, using good design practices with a strong focus on teasing apart the concerns, we came up with a data structure and a pattern of use that we have called the Disruptor.
> 
> 考虑到现代 CPU 的工作方式，我们喜欢称之为“**机械同情**”，使用良好的设计实践，强调分离关注点，我们提出了一个数据结构和使用模式，我们称之为 Disruptor。
> 
> 
> Testing has shown that the mean latency using the Disruptor for a three-stage pipeline is 3 orders of magnitude lower than an equivalent queue-based approach. 
> In addition, the Disruptor handles approximately 8 times more throughput for the same configuration.
> 
> 测试结果显示，使用 Disruptor 处理三级管道的平均延迟比等效的基于队列的方法低 3 数量级。
> 此外，Disruptor 处理相同配置的大约 8 倍以上的吞吐量。
> 
> 
> These performance improvements represent a step change in the thinking around concurrent programming. 
> This new pattern is an ideal foundation for any asynchronous event processing architecture where high-throughput and low-latency is required.
> 
> 这些性能改进代表了围绕并发编程的思想的一个进步。
> 这种新模式是任何需要高吞吐量和低延迟的异步事件处理架构的理想基础。
> 
> 
> At LMAX we have built an order matching engine, real-time risk management, and a highly available in-memory transaction processing system all on this pattern to great success. 
> Each of these systems has set new performance standards that, as far as we can tell, are unsurpassed.
> 
> 在 LMAX，我们基于这种模式构建了一个订单匹配引擎、实时风险管理和一个高可用的内存交易处理系统，并取得了巨大的成功。
> 这些系统中的每一个都设定了新的性能指标，据我们所知，是无与伦比的。
> 
> 
> However this is not a specialist solution that is only of relevance in the Finance industry. 
> The Disruptor is a general-purpose mechanism that solves a complex problem in concurrent programming in a way that maximizes performance, and that is simple to implement. 
> Although some of the concepts may seem unusual it has been our experience that systems built to this pattern are significantly simpler to implement than comparable mechanisms.
> 
> 然而，这并不是一个只与金融业相关的专业解决方案。
> Disruptor 是一种通用机制，它以最大化性能的方式解决并发编程中的一个复杂问题，并且易于实现。
> 虽然有些概念看起来不太寻常，但我们的经验是，根据这种模式构建的系统比可比机制实现起来要简单得多。
> 
> 
> The Disruptor has significantly less write contention, a lower concurrency overhead and is more cache friendly than comparable approaches, all of which results in greater throughput with less jitter at lower latency. 
> On processors at moderate clock rates we have seen over 25 million messages per second and latencies lower than 50 nanoseconds. 
> This performance is a significant improvement compared to any other implementation that we have seen. 
> This is very close to the theoretical limit of a modern processor to exchange data between cores.
> 
> Disruptor 具有明显较少的写争用，较低的并发开销，并且比同类方法更加友好的缓存，所有这些都导致更大的吞吐量和更低的延迟抖动。
> 在中等时钟频率的处理器上，我们已经看到每秒超过2500万条消息，延迟低于50纳秒。
> 与我们已经看到的任何其他实现相比，这个性能是一个显著的改进。
> 这非常接近现代处理器在内核之间交换数据的理论极限。


## 1. Overview 概述


The Disruptor is the result of our efforts to build the world’s highest performance financial exchange at LMAX. 
Early designs focused on architectures derived from SEDA [1] and Actors [2] using pipelines for throughput. 
After profiling various implementations it became evident that the queuing of events between stages in the pipeline was dominating the costs. 
We found that queues also introduced latency and high levels of jitter. 
We expended significant effort on developing new queue implementations with better performance. 
However it became evident that queues as a fundamental data structure are limited due to the conflation of design concerns for the producers, consumers, and their data storage. 
The Disruptor is the result of our work to build a concurrent structure that cleanly separates these concerns.

Disruptor 是我们努力在 LMAX 建立世界上性能最高的金融交易所的结果。
早期的设计侧重于源自 SEDA [1]和 Actors [2]的体系结构，使用管道来提高吞吐量。
在分析了各种实现之后，很明显，管道中各阶段之间的事件排队占据了成本的主导地位。
我们发现队列还引入了延迟和高水平的抖动。
我们在开发性能更好的新队列实现方面投入了大量精力。
然而，由于生产者、消费者及其数据存储的设计关注点混为一谈，显然队列作为基本数据结构是有限的。
Disruptor 是我们构建一个并发结构的结果，该结构可以清楚地分离这些关注点。


## 2. The Complexities of Concurrency 并发的复杂性


In the context of this document, and computer science in general, concurrency means not only that two or more tasks happen in parallel, but also that they contend on access to resources. 
The contended resource may be a database, file, socket or even a location in memory.

在本文以及一般的计算机科学的上下文中，并发不仅意味着两个或多个任务并行发生，而且还意味着它们争夺对资源的访问权。
争用的资源可能是数据库、文件、套接字，甚至是内存中的一个位置。


Concurrent execution of code is about two things, mutual exclusion and visibility of change. 
Mutual exclusion is about managing contended updates to some resource. 
Visibility of change is about controlling when such changes are made visible to other threads. 
It is possible to avoid the need for mutual exclusion if you can eliminate the need for contended updates. 
If your algorithm can guarantee that any given resource is modified by only one thread, then mutual exclusion is unnecessary. 
Read and write operations require that all changes are made visible to other threads. 
However only contended write operations require the mutual exclusion of the changes.

代码的并发执行包括两个方面，互斥锁和变更的可见性。
互斥锁是关于管理对某些资源的争用更新。
变更的可见性是关于控制何时使这些变更对其他线程可见。
如果你能消除争用更新的需要，就有可能避免使用互斥锁。
如果您的算法可以保证任何给定的资源只被一个线程修改，那么互斥锁就是不必要的。
读写操作要求所有更改都对其他线程可见。
然而，只有争用写操作才需要更改的互斥锁。


The most costly operation in any concurrent environment is a contended write access. 
To have multiple threads write to the same resource requires complex and expensive coordination. 
Typically this is achieved by employing a locking strategy of some kind.

在任何并发环境中，开销最大的操作是争用写访问。
让多个线程写入同一资源需要复杂而昂贵的协调。
这通常是通过使用某种锁策略来实现的。


### 2.1. The Cost of Locks 锁开销


Locks provide mutual exclusion and ensure that the visibility of change occurs in an ordered manner. 
Locks are incredibly expensive because they require arbitration when contended. 
This arbitration is achieved by a context switch to the operating system kernel which will suspend threads waiting on a lock until it is released. 
During such a context switch, as well as releasing control to the operating system which may decide to do other house-keeping tasks while it has control, execution context can lose previously cached data and instructions. 
This can have a serious performance impact on modern processors. 
Fast user mode locks can be employed but these are only of any real benefit when not contended.

锁提供了互斥锁，并确保变更的可见性以有序的方式发生。
锁是难以置信的昂贵，因为他们在竞争时需要仲裁。
这种仲裁是通过上下文切换到操作系统内核来实现的，操作系统内核将挂起等待锁的线程，直到锁被释放。
在这样的上下文切换过程中，以及将控制权释放给操作系统(操作系统可能决定在其拥有控制权的情况下执行其他内部管理任务)时，执行上下文可能会丢失以前缓存的数据和指令。
这会对现代处理器的性能产生严重影响。
可以使用快速用户模式锁，但这些锁只有在不争用时才有真正的好处。


We will illustrate the cost of locks with a simple demonstration. 
The focus of this experiment is to call a function which increments a 64-bit counter in a loop 500 million times. 
This can be executed by a single thread on a 2.4Ghz Intel Westmere EP in just 300ms if written in Java. 
The language is unimportant to this experiment and results will be similar across all languages with the same basic primitives.

我们将通过一个简单的演示来说明锁的成本。
这个实验的重点是调用一个函数，它在一个循环中将一个 64-bit 计数器递增 5 亿次。
如果使用 Java 编写，这可以在 2.4 Ghz Intel Westmere EP 上通过单个线程在 300 毫秒内执行。
语言对于这个实验来说并不重要，并且使用相同的基本原语的所有语言的结果都是相似的。


Once a lock is introduced to provide mutual exclusion, even when the lock is as yet un-contended, the cost goes up significantly. 
The cost increases again, by orders of magnitude, when two or more threads begin to contend. 
The results of this simple experiment are shown in the table below:

一旦引入一个锁来提供互斥，即使当锁还没有竞争时，成本也会显著上升。
当两个或更多线程开始竞争时，成本再次数量级增加。
这一简单实验的结果如下表所示:


_Table 1. Comparative costs of contention_ _竞争开销比较_

| Method                            | Time(ms) |
|-----------------------------------|----------|
| Single thread                     | 300      |
| Single thread with lock           | 10,000   |
| Two threads with lock             | 224.000  |
| Single thread with CAS            | 5,700    |
| Two threads with CAS              | 30,000   |
| Single thread with volatile write | 4,700    |


### 2.2. The Costs of "CAS" "CAS" 开销


A more efficient alternative to the use of locks can be employed for updating memory when the target of the update is a single word. 
These alternatives are based upon the atomic, or interlocked, instructions implemented in modern processors. 
These are commonly known as CAS (Compare And Swap) operations, e.g. “lock cmpxchg” on x86. 
A CAS operation is a special machine-code instruction that allows a word in memory to be conditionally set as an atomic operation. 
For the “increment a counter experiment” each thread can spin in a loop reading the counter then try to atomically set it to its new incremented value. 
The old and new values are provided as parameters to this instruction. 
If, when the operation is executed, the value of the counter matches the supplied expected value, the counter is updated with the new value. 
If, on the other hand, the value is not as expected, the CAS operation will fail. 
It is then up to the thread attempting to perform the change to retry, re-reading the counter incrementing from that value and so on until the change succeeds. 
This CAS approach is significantly more efficient than locks because it does not require a context switch to the kernel for arbitration. 
However CAS operations are not free of cost. 
The processor must lock its instruction pipeline to ensure atomicity and employ a memory barrier to make the changes visible to other threads. 
CAS operations are available in Java by using the `java.util.concurrent.Atomic*` classes.

当更新的目标是单个字时，可以使用更有效的锁替代方法来更新内存。
这些替代方案基于在现代处理器中实现的原子指令或互锁指令。
这些操作通常称为 CAS (Compare And Swap)操作，例如 x86 上的 "lock cmpxchg" 。
CAS 操作是一种特殊的机器码指令，它允许有条件地将内存中的字设置为原子操作。
对于“计数器递增实验”，每个线程都可以循环读取计数器，然后尝试以原子方式将其设置为新的递增值。
新旧值都作为此指令的参数提供。
如果在执行操作时，计数器的值与提供的预期值相匹配，则计数器将使用新值进行更新。
另一方面，如果该值与预期不符，则 CAS 操作将失败。
然后由尝试执行更改的线程重试，重新读取从该值递增的计数器，以此类推，直到更改成功。
这种 CAS 方法明显比锁更有效，因为它不需要上下文切换到内核进行仲裁。
然而，CAS 操作并不是免费的。
处理器必须锁定自己的指令流水线以确保原子性，并使用内存屏障使更改对其他线程可见。
CAS 操作在 Java 中可以通过使用 `java.util.concurrent.Atomic*` 类。

### 2.3. Memory Barriers 内存屏障

### 2.4. Cache Lines 缓存行

### 2.5. The Problems of Queues 队列的问题

### 2.6. Pipelines and Graphs 管道与图(管道的图形拓扑)

## 3. Design of the LMAX Disruptor LMAX Disruptor 的设计

### 3.1. Memory Allocation 内存分配

### 3.2. Teasing Apart the Concerns 梳理不同的关注点

### 3.3. Sequencing

### 3.4. Batching Effect

### 3.5. Dependency Graphs

### 3.6. Disruptor Class Diagram

### 3.7. Code Example

## 4. Throughput Performance Testing

## 5. Latency Performance Testing

## 6. Conclusion