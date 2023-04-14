# `ThreadLocal`

`ThreadLocal` 中的内容是线程内独有；维护一份独立副本；

`ThreadMap<ThreadLocal, Objec>` 位于当前线程；
不使用的对象必须调用 `remove（）` ，否则会有**内存溢出**；

* `set()` 向当前线程设置值
* `get()` 从当前线程获取值

> 声明式事务：保证使用同一个 `Connection` 
> 
> 连接放到当前线程的 `ThreadLocal` 中，其他数据库操作直接从 `ThreadLocal` 中获取，不在从连接池中获取；

## Java 的引用

* 强引用（普通引用）

    只要有普通引用对象不会被垃圾回收器 GC 回收；

* 软引用

    当有一个对象被一个软引用引用时，只有当系统内存不够用的时候才会回收此对象的内存；

    > 用作缓存；

    `SoftReference`: 软引用对象（软引用对象内部可以再指向具体对象）；使用 `get()` 获取软引用内的对象；

* 弱引用

    只要遇到垃圾回收 GC 就会被回收；

    如果有强引用同时存在，当强引用小时候，弱引用本身不用单独处理既可以被正确回收；

    > 一般用在容器中； 
    > 
    > `WeakHashMap`
    > 
    > 典型应用： `ThreadLocal` ，保证 `ThreadLocal` 对象可以被回收；
    > 
    > 当前线程中的 `ThreadLocalMap` 的 `key` 通过一个**弱引用**指向 `ThreadLocal` 对象；
    > 如果是强引用则会出现内存泄露；线程中对于 `ThreadLocal` 引用结束后，`ThreadLocal` ；

    `WeakReference`: 弱引用对象（弱引用对象内部指向具体对象）；

* 虚引用

    用于管理堆外内存； `get()` 获取不到值；

    `PhantomReference`: 虚引用对象，第二个参数为 `ReferenceQueue` 实例；内部有虚引用指向的对象和一个队列；虚引用被回收，向队列中放入值（通知）；

    > `DirectByteBuffer` 直接内存（堆外内存）；
    > 
    > 直接内存回收时可以使用虚引用；通过 `Queue` 检测堆外内存需要回收（Java中使用 `Unsafe` 管理内存；
