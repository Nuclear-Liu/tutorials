# LMAX Disruptor User Guide

LMAX Disruptor 是一个高性能线程间消息传递库。
Disruptor 是一个提供并发**环形缓冲区**数据结构的库。
旨在为异步事件处理体系结构提供低延迟、高吞吐量的工作队列。

## 核心概念 Core Concepts

Disruptor 工作原理相关术语（对于 DDD 倾向的人来说，可以视作 Disruptor 域中无处不在的语言）：

* **Ring Buffer**:

  环形缓冲区(Ring Buffer)通常被认为是中断器的主要方面。
  但是，从 3.0 开始，环形缓冲区仅负责**存储**和**更新**通过 Disruptor 移动的数据(事件(`Event`))。
  对于某些高级用例，它甚至可以由用户替换。

* **Sequence**:

  Disruptor 使用 `Sequence` 作为一种方法标识特定组件的位置。
  每个消费者(Event Processor)都会像 Disruptor 本身一样维护一个 `Sequence` 。
  大多数的并发代码依赖于 `Sequence` 的移动，因此 `Sequence` 支持 `AtomicLong` 的许多当前功能。
  事实上，两者之间唯一真正的区别是 `Sequence` 包含额外的功能，防止 `Sequence` 和其他值之间的错误共享。

* **Sequencer**:

  `Sequencer`(序列器)是 Disruptor 的**核心**。
  此接口的两个实现（单生产者、多生产者）了所有并发算法，以便在生产者和消费者之间快速、正确的传递数据。

* **Sequence Barrier**:

  `Sequencer` 生成一个 Sequence Barrier(序列屏障) ，其中包含对来自 `Sequencer` 的主要已发布 `Sequence` 和任何依赖消费者的 `Sequence` 的引用。
  它包含用来确定是否有任何事件可供消费者处理的逻辑。

* **Wait Strategy**:

  等待策略确定用户将如何等待生产者将事件放入 Disruptor 。
  更多细节可以在可选无锁部分找到。

* **Event**:

  从生产者传递到消费者的数据单位。
  事件没有特定的代码表示形式，由用户定义。

* **Event Processor**:

  用来处理来自 Disruptor 事件的主事件循环，并拥有消费者的 `Sequence` 的所有权。
  有一个名为 `BatchEventProcessor` 的单一表示形式，它包含了事件循环的有效实现，并将回调到一个使用过的 `EventHandler` 接口的实现。

* **Event Handler**:

  由用户实现的接口，代表 Disruptor 的一个消费者

* **Producer**:

  这是调用 Disruptor 以将事件排队的用户代码。
  此概念在代码中没有体现。

下面是 LMAX 如何在其高性能核心服务中使用 Disruptor 的示例。

![user-guide-models](user-guide-models.png)
_Figure 1. Disruptor with a set of dependent consumers._

### 多播事件 Multicast Events

这是队列和 Disruptor 之间最大的行为差异。

当有多个消费者监听同一个 Disruptor 时，它将所有事件发布给所有消费者。
相比之下，队列只会向单个消费者发送的那个事件。
当需要对同一数据进行独立的多个并行操作时，可以使用 Disruptor 的这种行为。

> **示例用例**
>
> _Figure 1_ 有三个监听操作的地方：
>
> * journal(日志): 将数据写入持久日志文档
> * replication(复制): 将输入数据发送到另一台计算机
> * application(业务逻辑): 真正的处理工作
>
> 这些消费者中的每一个会接收到 Disruptor 中所有的消息。
> 这使得这些消费者中的每一个人的工作都可以并行地进行。

### 消费者依赖图 Consumer Dependency Graph

为了支持并行处理行为的实现应用。
有必要支持消费者之间的协调。
我们把这个概念称为“**门控**”（或者正确地说，这个功能是**门控**之间的一种形式）。

> _Figure 1_ 中有必要阻止业务逻辑(application)消费者取得进展，直到日志(journal)和复制(replication)完成。

“**门控**”发生的两个位置：
* 确保生产者不会超支消费者

  通过将相关消费者添加到 Disruptor 来处理: `RingBuffer.addGatingConsumers()`
* 维护消费者之间的消费依赖关系

  通过构造一个 `SequenceBarrier` 来实现，其中包含来自必须首先完成其处理的组件的序列

> _Figure 1_ 有3个消费者侦听来自环形缓冲区的事件。
> application 依赖于 journal 和 replication 。

### 事件预分配 Event Pre-allocation

Disruptor 的目标之一是在低延迟环境中使用。
在低延迟系统中，有必要减少或删除内存分配。
在基于 Java 的系统中，目的是为了减少由于垃圾收集而造成的停顿。

为了支持这一点，用户能够预先分配 Disruptor 内事件所需的存储空间。
在构建期间由用户提供，并将为 Disruptor 环形缓冲区中的每个条目调用。
当向 Disruptor 发布新数据时， API 将允许用户获取构造的对象，以便它们可以调用该存储对象上的方法或更新字段。
Disruptor 保证这些操作只要正确的实现，就将是并发安全的。

### 可选无锁 Optionally Lock-free

对低延迟的渴望所推动的另一个关键实施细节是广泛使用无锁算法来实现 Disruptor 。
所有的内存可见性和正确性保证都是通过**内存屏障**和/或**compare-and-swap**(cas)操作实现的。

> 只有一个用例需要实际的锁，即： `BlockingWaitStrategy`
>
> 这样做的目的仅仅是为了使用一个条件，以便在等待新的事件到来时可以释放一个消耗线程。
> 许多低延迟系统会使用忙等待，以避免使用条件可能产生的抖动；
> 然而，在许多系统中，忙等待操作会导致性能的显著下降，特别是在 CPU 资源受到严重限制的情况下，例如虚拟化环境中的网络服务器。

## 入门 Getting Started

依赖：
```xml
<dependency>
    <groupId>com.lmax</groupId>
    <artifactId>disruptor</artifactId>
    <version>4.0.0.RC1</version>
</dependency>
```

目前，生产者与消费者有几种使用风格，虽然本质上时相似的，但每种方法中可能存在细微差别。

### `Event`: `LongEvent`

```java
public class LongEvent {
    private long value;

    public void setValue(long value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "LongEvent{" + "value=" + value + '}';
    }
}
```

### `EventFactory`: `LongEventFactory`

  为了让 Disruptor 能够预分配事件对象，需要一个将执行该构造的方法。
	* `EventFactory` 接口实现类
	* 方法引用: `LongEvent::new`

```java
import com.lmax.disruptor.EventFactory;

public class LongEventFactory implements EventFactory<LongEvent> {
    @Override
    public LongEvent newInstance() {
        return new LongEvent();
    }
}
```

### `EventHandler`
```java
import com.lmax.disruptor.EventHandler;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hui.basic.event.LongEvent;

public class LongEventHandler implements EventHandler<LongEvent> {
    private static final Logger LOGGER = LogManager.getLogger(LongEventHandler.class);
    @Override
    public void onEvent(LongEvent event, long sequence, boolean endOfBatch) throws Exception {
        LOGGER.info("Event: " + event);
    }
}
```

### `publishEvent()`: Disruptor 3.0 开始首选使用 Lambda 风格AIP 来编写发布者
使用：
* Lambda 表达式
* 方法引用

接口重载：
	* `EventTranslator`
	* `EventTranslatorOneArg`
	* `EventTranslatorTwoArg`
	* `EventTranslatorThreeArg`
	* `EventTranslatorVararg`

> **注意**
>
> ```jshelllanguage // example-2
> ByteBuffer bb = ByteBuffer.allocate(8);
> for(long l = 0;true;l++) {
>     bb.putLong(0,l);
>     ringBuffer.publishEvent((event,sequence) -> event.set(bb.getLong(0)));
>     Thread.sleep(1000);
> }
> ```
>
> 这将创建一个捕获的 Lambda ，意味着当它将 Lambad 传递给调用时，它需要实例化一个对象来保存变量。
> 这将创建额外的（不必要的）垃圾，因此如果需要低 GC 压力，则应首选将参数传递给 Lambda 调用。

##### Example: using lambdas expression

```jshelllanguage
ByteBuffer bb = ByteBuffer.allocate(8);
    for(long l = 0; true; l++) {
        bb.putLong(0,l);
        ringBuffer.publishEvent((event, sequence, buffer) -> event.set(buffer.getLong(0)), bb);
        Thread.sleep(1000);
    }
```

##### Example: using method references

```jshelllanguage
class LongEventProcessorWithMethodRefTest {
    private static final Logger LOGGER = LogManager.getLogger();

    static void handleEvent(LongEvent event, long sequence, boolean endOfBatch) {
        LOGGER.info("consumer:{}",event);
    }
    static void translate(LongEvent event, long sequence, ByteBuffer buffer) {
        event.setValue(buffer.getLong(0));
        LOGGER.info("produce: {}", event);
    }
    @Test
    void test() throws InterruptedException {
        int bufferSize = 1024*1024;
        LongEventProcessor processor = new LongEventProcessor(bufferSize, LongEventProcessorWithMethodRefTest::handleEvent);

        ByteBuffer buffer = ByteBuffer.allocate(8);
        EventTranslatorVararg<LongEvent> translatorVararg = (event, sequence, args) -> {
            event.setValue(buffer.getLong(0));
            LOGGER.info("produce: {}", event);
        };
        for (int i = 0; i<Integer.MAX_VALUE; i++) {
            buffer.putLong(0, i);
            processor.produce(LongEventProcessorWithMethodRefTest::translate,buffer);
            TimeUnit.SECONDS.sleep(1);
        }
    }
}
```

##### Example: using translators

```jshelllanguage
class LongEventProcessorWithTranslatorTest {
    private static final Logger LOGGER = LogManager.getLogger();

    static void handleEvent(LongEvent event, long sequence, boolean endOfBatch) {
        LOGGER.info("consumer:{}",event);
    }
    private static final EventTranslatorOneArg<LongEvent, ByteBuffer> translator = new EventTranslatorOneArg<LongEvent, ByteBuffer>() {
        private static final Logger LOGGER = LogManager.getLogger();
        @Override
        public void translateTo(LongEvent event, long sequence, ByteBuffer arg0) {
            event.setValue(arg0.getLong(0));
            LOGGER.info("produce: {}", event);
        }
    };
    @Test
    void test() throws InterruptedException {
        int bufferSize = 1024*1024;
        LongEventProcessor processor = new LongEventProcessor(bufferSize, LongEventProcessorWithMethodRefTest::handleEvent);

        ByteBuffer buffer = ByteBuffer.allocate(8);

        for (int i = 0; i<Integer.MAX_VALUE; i++) {
            buffer.putLong(0, i);
            processor.produce(translator,buffer);
            TimeUnit.SECONDS.sleep(1);
        }
    }
}
```

##### Example: using the legacy API

### RingBufferSize: 大小必须是2的幂

## 基本调校选项 Basic Tuning Options


### `ProducerType` 生产者类型

* `ProducerType.SINGLE` 单生产者
* `ProducerType.MULTI` 多生产者

提高性能的最佳方法之一：**遵循大一写入原则**。

性能对比(ENV: i7 Sandy Bridge MacBook Air)：

| Producer Type |              Run 0 |              Run 1 |              Run 2 |              Run 3 |              Run 4 |              Run 5 |              Run 6 |
|---------------|-------------------:|-------------------:|-------------------:|-------------------:|-------------------:|-------------------:|-------------------:|
| Multiple      | 26,553,372 ops/sec | 28,727,337 ops/sec | 29,806,259 ops/sec | 29,717,682 ops/sec | 28,818,443 ops/sec | 29,103,608 ops/sec | 29,239,766 ops/sec |
| Single        | 89,365,504 ops/sec | 77,579,519 ops/sec | 78,678,206 ops/sec | 80,840,743 ops/sec | 81,037,277 ops/sec | 81,168,831 ops/sec | 81,699,346 ops/sec |

### `WaitStrategy` 等待策略

* `BlockingWaitStrategy`(**默认**) CPU使用最保守的
* `SleepingWaitStrategy` 在不需要低延迟但需要对生产线程影响较小的情况下效果最佳（日志记录）

	使用了 `LockSupport.parkNanos(1)` 的调用。在典型的 Linux 系统上，这会暂停线程大约 60us
* `YieldingWaitStrategy` 在低延迟系统中使用【适合低延迟系统的两种 `WaitStrategy` 之一】

	不断循环等待 `sequence` 递增到适当的值。
	在主循环体中使用 `Thread#yield()` 尝试让出 CPU 资源，允许其他排队的线程运行。

	当需要非常高的性能并且 `EventHandler` 线程数低于逻辑内核总数(例如：启用了**超线程**)时，推荐的等待策略。
* `BusySpinWaitStrategy` 适用于低延迟

	仅对 `EventHandler` 线程数量低于机器上的物理内核数，才应使用此等待策略(例如：禁用了**超线程**)。
* `LiteBlockWaitStrategy`
* `LiteTimeoutBlockWaitStrategy`
* `PhasedBackoffWaitStrategy`
* `TimeoutBlockingWaitStrategy`


## 从环形缓冲区中清除对象 Clearing Objects From the Ring Buffer

通过 Disruptor 传递数据时，对象的生存期可能比预期的更长。
为避免这种情况发生，可能需要在处理事件后清除事件。

如果只有一个事件处理进程，则清理同一处理进程中的值就足够了。
如果有多个事件处理链，则可能需要在链的末尾放置一个特定的处理进程，来负责清理对象。

```java
public class ObjectEvent<T> {
    private T value;

    public void setValue(T value) {
        this.value = value;
    }
    public void clear() {
        value = null;
    }

    @Override
    public String toString() {
        return "ObjectEvent{" +
                "value=" + value +
                '}';
    }
}
```

```java
public class ClearingEventHandler<T> implements EventHandler<ObjectEvent<T>> {
    @Override
    public void onEvent(ObjectEvent<T> event, long sequence, boolean endOfBatch) throws Exception {
        event.clear();
    }
}
```

## 动态添加/移除消费者 Dynamically `EventHandler`

相关类与接口：
* `BatchEventProcessor`
* `BatchEventProcessor#halt()`
* `RingBuffer#addGatingSequences()`
* `RingBuffer#removeGatingSequences()`

```java
public class DynamicallyAddHandlerFeature {
    private static final Logger LOGGER = LogManager.getLogger();

    public static void main(String[] args) throws InterruptedException {
        ExecutorService executor = Executors.newCachedThreadPool(DaemonThreadFactory.INSTANCE);

        // Build a disruptor and start it.
        Disruptor<StubEvent> disruptor = new Disruptor<>(
                StubEvent.EVENT_FACTORY,
                1024,
                ProcessorThreadFactory.INSTANCE);
        RingBuffer<StubEvent> ringBuffer = disruptor.start();

        // Construct 2 batch event processors.
        DynamicHandler handler1 = new DynamicHandler();
        BatchEventProcessor<StubEvent> processor1 = new BatchEventProcessor<>(
                ringBuffer,
                ringBuffer.newBarrier(),
                handler1);

        DynamicHandler handler2 = new DynamicHandler();
        BatchEventProcessor<StubEvent> processor2 = new BatchEventProcessor<>(
                ringBuffer,
                ringBuffer.newBarrier(),
                handler2);

        // Dynamically add both sequences to the ring buffer
        ringBuffer.addGatingSequences(processor1.getSequence(), processor2.getSequence());

        // Start the new batch processors.
        executor.execute(processor1);
        executor.execute(processor2);

        // Async produce
        CompletableFuture.runAsync(() -> {
            for (int i = 0; i < Integer.MAX_VALUE; i++) {
                ringBuffer.publishEvent(StubEvent.TRANSLATOR, i, i + "message");
                try {
                    TimeUnit.NANOSECONDS.sleep(20_000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        });

        TimeUnit.SECONDS.sleep(4);

        // Remove a processor.
        // Stop the processor
        processor2.halt();
        handler2.awaitShutdown();
        // Remove the gating sequence from the ring buffer
        ringBuffer.removeGatingSequence(processor2.getSequence());

        while (true) ;
    }
}

class DynamicHandler implements EventHandler<StubEvent> {
    private static final Logger LOGGER = LogManager.getLogger();
    private final CountDownLatch shutdownLatch = new CountDownLatch(1);

    @Override
    public void onEvent(StubEvent event, long sequence, boolean endOfBatch) throws Exception {
        LOGGER.info("consumer:{}", event);
    }

    @Override
    public void onStart() {
        LOGGER.info("consumer start");
    }

    @Override
    public void onShutdown() {
        LOGGER.info("consumer stop");
        shutdownLatch.countDown();
        LOGGER.info("release shutdown latch");
    }

    public void awaitShutdown() throws InterruptedException {
        LOGGER.info("await shutdown latch");
        shutdownLatch.await();
        LOGGER.info("acquired shutdown latch");
    }
}
```

## 转换器异常处理 Handler Exception Translate

```java
public class HandlerExceptionOnTranslateFeature {
    private static final Logger LOGGER = LogManager.getLogger();
    static final int NO_VALUE_SPECIFIED = -1;
    public static void main(String[] args) throws InterruptedException {
        Disruptor<StubEvent> disruptor = new Disruptor<>(
                StubEvent.EVENT_FACTORY,
                1024,
                DaemonThreadFactory.INSTANCE);
        disruptor.handleEventsWith(new StubHandler());
        disruptor.start();

        EventTranslator<StubEvent> translator = ((event, sequence) -> {
            event.setValue(NO_VALUE_SPECIFIED);
            if (sequence % 3==0){
                throw new RuntimeException("skipping");
            }
            event.setValue((int)sequence);
        });

        for (int i = 0; i < 10; i++) {
            try {
                disruptor.publishEvent(translator);
            } catch (Exception e) {
                LOGGER.error(e.getMessage());
            }
        }
        TimeUnit.SECONDS.sleep(5);
    }
}
class StubHandler implements EventHandler<StubEvent> {
    private static final Logger LOGGER = LogManager.getLogger();
    @Override
    public void onEvent(StubEvent event, long sequence, boolean endOfBatch) throws Exception {
        if (event.getValue() == HandlerExceptionOnTranslateFeature.NO_VALUE_SPECIFIED) {
            LOGGER.info("discarded");
        } else {
            LOGGER.info("processed:{}",event);
        }
    }
}
```

## 批量消费 Bathing Event Handler

> 消费回调 `EventHandler#onEvent` 的 `endOfBatch` 有时会在没有结束时触发，导致实际批量大小小于设定的批次大小。

```java
public class BatchingEventHandlerFeature {
    private static final Logger LOGGER = LogManager.getLogger();

    public static void main(String[] args) throws InterruptedException {
        Disruptor<StubEvent> disruptor = new Disruptor<>(
                StubEvent.EVENT_FACTORY,
                1024,
                DaemonThreadFactory.INSTANCE);
        disruptor.handleEventsWith(new BatchingEventHandler());
        disruptor.start();

        for (int i = 0; i < 120; i++) {
            disruptor.publishEvent((event, sequence, arg0, arg1) -> {
                event.setValue(arg0);
                event.setTestString(arg1);
            }, i, i + "mess");
        }

        TimeUnit.SECONDS.sleep(2);
        LOGGER.info("new batch");

        for (int i = 0; i < 326; i++) {
            disruptor.publishEvent((event, sequence, arg0, arg1) -> {
                event.setValue(arg0);
                event.setTestString(arg1);
            }, i, i + "mess");
        }
        TimeUnit.SECONDS.sleep(1);
    }
}

class BatchingEventHandler implements EventHandler<StubEvent> {
    private static final Logger LOGGER = LogManager.getLogger();
    private static final int MAX_BATCH_SIZE = 100;
    private final List<StubEvent> batch = new ArrayList<>();

    @Override
    public void onEvent(StubEvent event, long sequence, boolean endOfBatch) throws Exception {
        batch.add(event);
        if (endOfBatch || batch.size() >= MAX_BATCH_SIZE) {
            LOGGER.info("consumer state:{}",endOfBatch);
            processBatch(batch);
        }
    }

    private void processBatch(final List<StubEvent> batch) {
        LOGGER.info("consumer size:{}", batch.size());
        batch.clear();
    }
}
```

## 多生产者

> **注意**：
> 
> 当 Disruptor 没有启动时，可以生产，待启动后，消费者依此消费环形缓冲区中的数据。

```java
public class MultiProducerWithTranslatorFeature {
    private static final Logger LOGGER = LogManager.getLogger();

    public static void main(String[] args) {
        Disruptor<StubEvent> disruptor = new Disruptor<>(
                StubEvent.EVENT_FACTORY,
                1024,
                DaemonThreadFactory.INSTANCE,
                ProducerType.MULTI,
                new YieldingWaitStrategy());
        disruptor.handleEventsWith(new Consumer()).then(new Consumer()).then(new ClearConsumer());

        Publisher p = new Publisher();

        for (int i = 0; i < 1024; i++)
        {
            disruptor.getRingBuffer().publishEvent(p, i, i+Thread.currentThread().getName());
        }
        LOGGER.info("start disruptor");
        disruptor.start();
        CompletableFuture.runAsync(() -> {
            for (int i = 0; i < Integer.MAX_VALUE; i++) {
                disruptor.getRingBuffer().publishEvent(p,i, Thread.currentThread().getName() + i);
                try {
                    TimeUnit.NANOSECONDS.sleep(1000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        });
        CompletableFuture.runAsync(() -> {
            for (int i = 0; i < Integer.MAX_VALUE; i++) {
                disruptor.getRingBuffer().publishEvent(p,i, Thread.currentThread().getName() + i);
                try {
                    TimeUnit.NANOSECONDS.sleep(1000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        });
        while (true);
    }
}
class Publisher implements EventTranslatorTwoArg<StubEvent,Integer,String> {
    private static final Logger LOGGER = LogManager.getLogger();
    @Override
    public void translateTo(StubEvent event, long sequence, Integer arg0, String arg1) {
        event.setValue(arg0);
        event.setTestString(arg1);
        LOGGER.info("publish:{}",event);
    }
}
class Consumer implements EventHandler<StubEvent> {
    private static final Logger LOGGER = LogManager.getLogger();
    @Override
    public void onEvent(StubEvent event, long sequence, boolean endOfBatch) throws Exception {
        LOGGER.info("consume: {}", event);
    }
}
class ClearConsumer implements EventHandler<StubEvent> {
    @Override
    public void onEvent(StubEvent event, long sequence, boolean endOfBatch) throws Exception {
        event.clear();
    }
}
```

## 重命名消费者线程名 Named Event Handler

```java
public class NamedEventHandlerFeature {
    private static final Logger LOGGER = LogManager.getLogger();
    public static void main(String[] args) throws InterruptedException {
        Disruptor<StubEvent> disruptor = new Disruptor<>(
                StubEvent.EVENT_FACTORY,
                1024,
                DaemonThreadFactory.INSTANCE);
        disruptor.handleEventsWith(new NamedEventHandler("consumer_1"))
                .then(new NamedEventHandler("consumer_2"));
        disruptor.start();

        for (int i = 0; i < Integer.MAX_VALUE; i++) {
            disruptor.getRingBuffer().publishEvent((event, sequence, arg0, arg1) -> {
                event.setValue(arg0);
                event.setTestString(arg1);
            },i, "message");
            TimeUnit.MICROSECONDS.sleep(180);
        }
    }
}
class NamedEventHandler implements EventHandler<StubEvent> {
    private static final Logger LOGGER = LogManager.getLogger();
    private final String name;

    NamedEventHandler(String name) {
        this.name = name;
    }

    @Override
    public void onEvent(StubEvent event, long sequence, boolean endOfBatch) throws Exception {
        LOGGER.info("consume:{}",event);
    }

    @Override
    public void onStart() {
        final Thread currentThread = Thread.currentThread();
        LOGGER.info("old thread name:{}",currentThread.getName());
        currentThread.setName(name);
    }
}
```

## 管道 Pipeline

```java
public class PipelinerFeature {
    private static final Logger LOGGER = LogManager.getLogger();
    public static void main(String[] args) throws InterruptedException {
        Disruptor<StubEvent> disruptor = new Disruptor<>(
                StubEvent.EVENT_FACTORY,
                1024,
                DaemonThreadFactory.INSTANCE);
        disruptor.handleEventsWith(
                        new ParallelHandler(0, 3),
                        new ParallelHandler(1, 3),
                        new ParallelHandler(2, 3))
                .then(new JoiningHandler());
        RingBuffer<StubEvent> ringBuffer = disruptor.start();

        for (int i = 0; i < 100; i++) {
            ringBuffer.publishEvent((event, sequence, arg0, arg1) -> {
                event.setValue(arg0);
                event.setTestString(null);
            }, i, null);
            TimeUnit.MILLISECONDS.sleep(100);
        }
    }
}
class ParallelHandler implements EventHandler<StubEvent> {
    private static final Logger LOGGER = LogManager.getLogger();
    private final int ordial;
    private final int totalHandlers;
    ParallelHandler(int ordial, int totalHandlers) {
        this.ordial = ordial;
        this.totalHandlers = totalHandlers;
    }
    @Override
    public void onEvent(StubEvent event, long sequence, boolean endOfBatch) throws Exception {
        if (sequence % totalHandlers == ordial) {
            event.setTestString(ordial + "-" + event.getValue());
        }
    }
}
class JoiningHandler implements EventHandler<StubEvent> {
    private static final Logger LOGGER = LogManager.getLogger();
    @Override
    public void onEvent(StubEvent event, long sequence, boolean endOfBatch) throws Exception {
        if (event.getTestString() == null) {
            LOGGER.error("joining handler:{}", event);
        } else {
            LOGGER.info("consumer:{},comsumer:{}", this.getClass().getSimpleName(), event);
        }
    }
}
```

## 

## 批量回放 Batch Rewind
