# `voliatile`

* 保证线程可见性
    
    堆内存所有线程共享，线程有自己独占内存，当线程需要访问变量时，会将变量拷贝的线程自己独占内存中；
    对于变量的更改，首先再线程独占内存空间中变更；
    变量从独占内存变更的同时更新到堆内存；
    同时堆内存的变量更新进入线程堆空间的时机并不可控；
    即线程之间不可见；
    
    变量被 `volatile` 修饰后，可以保证线程之间可见（MESI，缓存一致性协议）；

* 禁止指令重排序

    双重检查锁，用于防止指令重排序，保证指令顺序；

# 锁优化

1. 锁粒度变细；
    
    锁争抢不是很多的时候，锁的粒度尽量小；

2. 锁粒度变粗；

    锁争抢很多的时候，锁的粒度尽量大（代替多一个细琐）；