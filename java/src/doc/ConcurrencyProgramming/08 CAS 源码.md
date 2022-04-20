# CAS 源码

AQS (CLH, 三个人名字首字母) 的核心是 `private volatile int state;` ：
* `volatile` 保证线程间可见；
* `state` 的具体含义由子类定义；
    * `ReentrantLock` 
        * `0` 解锁（没有线程获得锁）
        * `1` 加锁（有线程获得锁）

    * `CountDownLatch` 表示 `countDown()` 次数

`state` 关联一个 `Node`(`Thread`) 双向链表；

`addWaiter(Node mode)`

`VarHandler` 提供原子性线程安全操作（基于CAS）；
