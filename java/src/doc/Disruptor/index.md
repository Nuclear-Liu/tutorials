# [Disruptor](https://github.com/LMAX-Exchange/disruptor)

速度最快的单机 MQ ；
无锁、高并发、使用**环形** `Buffer` （基于**数组**实现），直接覆盖旧数据，降低 GC 频率；
实现了基于事件的生产者消费者模式（观察者模式）；

> 基于数组实现的环形 `Ring Buffer` 只需要维护一个 `sequence` 记录下一个有效的元素所在的位置；
> 不需要维护首位指针；
> 
> 当 `Ring Buffer` 被填满的时候，覆盖还是等待，由 Producer 决定；
> 
> 长度设为 `2^n` ，有利于二进制计算；
> `num % size` 与 `pos = num & (size - 1)` 结果一致；

## 开发步骤

> `Event` 即为 消息；

1. 定义 `Event` ：队列中需要处理的元素
2. 定义 `Event` 工厂（生产者） ： 用于填充队列

    Disruptor 初始化的时候，会调用 Event 工厂，对 `Ring Buffer` 数组内对象进行内存提前分配； GC 频率降低；

3. 定义 `EventHandler` (消费者) ：处理容器中的元素


> `Disruptor` 构造函数需要线程工厂，产生的线程用于消费者调用 `onEvent()` 方法；

## `ProducerType` 生产者线程模式

* `ProducerType.SINGLE`

    只有一个生产者；指定生产模式为单线程可以提高效率；

* `ProducerType.MULTI`

    多个生产者（默认）；指定生产者模式为多线程；

    > 如果实际生产者为多线程，生产者模式为单线程会出现线程之间的不安全，消息被覆盖；

## `WaitStrategy` 等待策略

* `BlockingWaitStrategy` ：通过线程阻塞的方式，等待生产者唤醒，被唤醒后，在循环检查依赖的 `sequence` 是否已经消费
* `BusySpinWaitStrategy` ：线程一直自旋等待（可能比较消耗 CPU ）
* `LiteBlockingWaitStrategy` ：线程阻塞等待生产者唤醒；
* `LiteTimeoutBlockingWaitStrategy` ：与 `LiteBlockingWaitStrategy` 相比，设置了阻塞时间，超时后抛出异常
* `PhasedBackoffWaitStrategy` ：根据时间参数和传入的等待策略决定使用暗中等待策略
* `SleepingWaitStrategy` ： `sleep` 等待
* `TimeoutBlockingWaitStrategy` ：与 `BlockingWaitStrategy` 来说，设置了等待时间，超过后抛异常
* `YieldingWaitStrategy` ：尝试 100 次，然后 `Thread.yield()` 让出 CPU

> 常用： `BlockingWaitStrategy` `SleepingWaitStrategy` `YieldingWaitStrategy`

## 消费者

多个消费者对应多个线程；

## 异常处理

使用 `disruptor.handleExceptionFor().with()` 指定异常处理器；
实现 `ExceptionHandler` 类的实例；
