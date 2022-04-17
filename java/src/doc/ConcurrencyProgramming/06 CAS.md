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
