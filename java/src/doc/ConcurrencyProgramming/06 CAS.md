# CAS 自旋锁、轻量级锁

`java.util.concurrent.atomic.Atomic*.java`
**`AtomicStampedReference.java`**

`Unsafe` 通过单例获取 `Unsafe` 对象；
* 直接操作内存；
    * `allocateMemory()`
    * `freeMemory()`
* 直接生成类实例；
* 直接操作类或实例变量
* CAS 相关操作；
 
## `LongAdder` **分段锁**

`LongAdder`: 内部会把值放到数组中，线程锁定数组中的每个元素（**分段锁**），最后将各个数组元素值求和得到结果；

## `ReentrantLock`

`ReentrantLock` 基于 CAS 实现的是可重入锁，可以替代 `synchronized` ；

* `tryLock()` 方法尝试锁定，决定获取不到锁的处理逻辑；
* `lockInterruptibly()` 可以对 `interrupt()` 方法做出响应；
* `ReentrantLock()` 构造函数如果传入 `true` 表示为**公平锁**（线程等待队列中队列前部的线程优先获得锁，队列为空则当前线程获得锁），默认为非公平锁；

## `CountDownLatch` 门闩

用来等待线程结束；比 `join()` 更加灵活；

* `CountDownLatch(int count)` count — 线程通过 `await` 之前必须调用 `countDown` 的次数；
* `countDown()` 计数减一；
* `await()` 等待门闩非释放（计数为零）；
* `getCount()` 返回当前计数；

## `CyclicBarrier` 循环栅栏

它允许一组线程都等待彼此到达一个共同的障碍点；
`CyclicBarrier` 在涉及固定大小的线程群的程序中很有用，这些线程群必须偶尔相互等待；
该屏障称为循环，因为它可以在释放等待线程后重复使用；

> 限流
> 
> client 访问，需要限流，限流入口访问量大出口访问量要少，实际中使用的是 Guava `RateLimiter` ；

* `CyclicBarrier(int parties)`
* `CyclicBarrier(int parties, Runnable barrierAction)`

## `Phaser`

分阶段执行，可以控制栅栏个数，也可以控制栅栏上线程的个数；

> 遗传算法会使用到；

## `ReadWriteLock` 读写锁

即共享锁（读）、排它锁（写）；

## `Semaphore` 信号量

默认非公平；

> 限流

* `Semaphore(int permits)` `permits` 信号量的个数
* `Semaphore(int permits, boolean fair)` `permits` 信号量的个数， `fair` : `true` 公平锁， `false` 非公平锁（默认）；
* `acquire()` 阻塞等待获取信号量
* `release()` 释放信号量

## `Exchanger` 交换器

线程之间交换数据（有类似于 `CyclicBarrier` 的特性，两个线程都需要到达 `exchanger()` 方法处才触发交换，否则阻塞）；
