# Garbage Collector(GC) _垃圾回收器_

Java GC 自动垃圾回收，开发效率高，执行效率相对低；

## Garbage _垃圾_

没有被引用的对象归为**垃圾**(Garbage)；

## 定位 Garbage 算法

* reference count _引用计数_

    引用计数不能处理**循环引用**；

* root searching _根可达_

    GC `roots` (根对象): 线程栈变量、静态变量、常量池、 `JNI` 指针 (GC `roots` : JVM stack, native method stack, run-time-constant pool, static references in method area, Clazz)

## 清理 Garbage 算法

* mark-sweep _标记清除_

    通过 `root searching` 根可达算法，将垃圾做标记，然后清除释放空间；

    > **优点**：
    > 
    > * 算法相对简单，存活对象比较多的情况下（清理的对象少）效率比较高；
    > 
    > **缺点**：
    > 
    > * 两遍扫描（第一遍（**标记**）根据**根可达算法**确定不可回收对象内存，第二遍（**清除**）在第一遍的基础上确定可回收对象）效率偏低；
    > * 容易产生碎片；

* copying _拷贝_

    将不可回收对象移动拷贝在到一块新的区域，逻辑上分成两块内存区域（在垃圾回收前占用的空间和垃圾回收后存放拷贝的不可回收对象的区域），清理过程直接释放掉整个旧的内存区域；

    > **优点**：
    > 
    > * 适用于存活对象较少的情况，只扫描一次，效率提高，没有内存碎片；
    > 
    > **缺点**：
    > 
    > * 空间浪费（需要将当前内存中的不可回收对象拷贝到一块新的内存区域）；
    > * 移动空间，调整对象引用；

* mark-compact _标记压缩_

    将存活对象整理到内存区域的一端；

    > **优点**：
    >
    > * 不会产生碎片，方便对象分配；
    > * 不会产生可用内存减半；
    >
    > **缺点**：
    >
    > * 扫描两次；
    > * 需要移动对象，效率偏低；

## 堆内存逻辑分区

**不适用不分代垃圾收集器**；

> Hotspot 内部使用的是**分代算法**；

> 除 `Epsilon` `ZGC` `Shenandoah` 之外的 GC 都是使用逻辑分代模型；
> 
> `G1` 仅实现了逻辑分代；
> 其他 GC 不仅逻辑分代，并且物理分代；

### 逻辑分代模型

> 除了 `G1` 的其他逻辑分代模型 GC 默认： `new/young : old/tenured = 1 : 2`

新生代对象大量死去，少量存活，适合**复制算法**；
老年代存活率高，回收较少，适合**标记清除**或者**标记压缩**；

* `new` / `young`: `1` _新生代_ **Coping**

    实例化的新对象，首先位于**新生代**；
    * `eden`: `8` _伊甸_
        
        实例化对象栈 stack 上分配不下时，分配到当前区域；

        > （一）栈上分配：
        >
        > * 线程私有小对象
        > * 无逃逸：局部代码内使用
        > * 支持标量替换：可以用普通类型的组合代替整个对象（比如：类T包含两个**非对象属性**，此时可以用两个对应的类型相同的变量代替当前对象）；
        > * **无需调整**

        > （二）线程本地分配 `TLAB`(Thread Local Allocation Buffer) ：
        >
        > * 占用 `edon` ，默认 `1%`
        > * 多线程的时候不用竞争 `edon` 就可以申请空间，提高效率
        > * 小对象
        > * **无需调整**

        > （三）老年代：
        >
        > * 大对象

        > （四） `edon`：
        >
        > * 其他

    * `survivor`: `1` //S1
    * `survivor`: `1` //S2

        `survivor` 一共分为两个相同的区域： S1 S2 ；

        `eden` 中对象经过一次 `GC` 没有被回收，归属到当前区域；

        `servivor` 每个区中的对象经过一次 `GC` 没有被回收，年龄自增，归属到另外一个 `survivor` 区域；

    > `servivor` 两个区中对象进入 `old` 区的年龄通过参数： `--XX:MaxTenuringThreshold` 配置
    > 
    > * Parallel Scavenge 垃圾回收器默认： `15`
    > * CMS 垃圾回收器： `6`
    > * G1 垃圾回收器： `15`
    > 
    > 动态年龄：
    > 
    > * `survivor` 两个区域垃圾会后完后（edon+survivor），当前 `survivor` 空间使用超过 `50%` ，将年龄最大的移动到 `Old`

* `old` / `tenured`: `2` _老年代_ **mark-sweep**/**mark-compact**

    经过多次 GC 没有被回收的对象位于**老年代**；

* `Metaspace` 元数空间（1.8+），JDK 1.7 叫做 `PermGen` 永久代

    > `PermGen` JDK 1.7 时必须指定大小限制，类多的情形下容易内存溢出；
    > 
    > **字符串常量**：
    > * JDK 1.7： 永久代
    > * JDK 1.8+：堆

    元数据空间没有大小限制，受限于物理内存；存放 `Class` 信息JNI

    > `MethodArea` : 方法区，逻辑概念（永久代、元数据）

> `MinorGC`/`YGC`: 年轻代空间耗尽是触发的 GC ；
> 
> `MajorGC`/`FullGC`: 老年代空间耗尽时触发，新生代、老年代同时 GC ；

> 年轻代空间大小控制参数： `-Xmn`
> 
> 老年代空间大小控制参数： `-Xms` `-Xmx`

![malloc](./malloc.png)

> `java -XX:+PrintFlagsFinal -version` 非标值，所有 JVM 参数

> **分配担保**
> 
> `YGC` 期间，新的对象进入 `survivor` 区空间不够了，直接进入老年代；

## 常见垃圾回收器

* `Serial` 年轻代（串行回收）

    a stop-the-word(STW), copying collector which uses a **single** GC thread.

    > safe point : 应用线程可以安全暂停的点

    单核CPU效率最高，虚拟机是 `Client` 模式的默认垃圾回收器；

* `PS(ParallelScavenge)` 年轻代（并行回收）

    a stop-the-world, copying collector which uses **multiple** GC threads.

* `PN(ParNew/ParallelNew)` 年轻代（配合 `CMS`(ConcurrentMarkSweep) 的并行回收）

    a stop-the-word, copying collector which uses multiple GC threads.

    It differs form "Parallel Scavenge" in that it has **enhancements** that make it usable with CMS.

    For example, "ParNew" does the **synchronization** needed so that it can **run during the concurrent phases of CMS**.

    默认线程数为 CPU 核数；

* `SO(SerialOld)` 老年代

    a stop-the-world mark-sweep-compact collector that uses a **single** GC thread.

* `PO(ParallelOld)` 老年代

    a compacting collector that uses **multiple** GC threads.

    mark-compact

* `CMS(ConcurrentMarkSweep)` 老年代（并发回收，**垃圾回和应用程序同时运行**，降低 **STW** 的时间(200ms) ）

    a mostly concurrent, low-pause collector.

    使用的**算法**：三色标记 + `Incremental Update`

    4 phases:
    * initial mark 初始标记(STW)（单线程），指标及根对象
    * concurrent mark 并发标记（多线程），于应用程序同时运行
    * remark 重新标记(STW)（多线程），多是已经并发标记完成，对新产生的垃圾进行标记
    * concurrent sweep 并发清理，清理过程也会产生新的垃圾，这种叫做浮动垃圾，需要下次CMS运行的时候进行清理；

    > CMS 问题：
    > 
    > * Mamory Fragmentation 内存碎片
    > * Float Garbage 浮动垃圾
    >     * ConcurrentModeFailure
    >         
    >         降低触发 CMS 的阈值
    >         
    >         `-XX:CMSInitiatingOccupancyFraction 92%` 默认 `92%` 将阈值降低 `68%` 保证有 FGC 过程中老年代有空间可以提供分配；
    >         
    >     * PromotionFailed
    >         
    >         保持老年代有足够的空间；
    > 
    > 
    > CMS 一旦有问题，需要使用 `SerialOld` 做标记压缩

    CMS 问题比较多，所以现在没有版本默认 CMS ，只能手工指定；
    CMS 既然是 MarkSweeep 一定会有内存碎片问题，达到一定成都后 CMS 老年代分配对象分贝不下的时候，使用 `SerialOld` 进行老年代回收；

    > PS + PO 加内存 换垃圾回收器 PN + CMS + SerialOld（几小时-几天的 STW）
    > 几十 G 内存，单线程回收 G1 + FGC 几十个 G 上 T 内存的服务器 ZGC

* `G1`(10ms)

    使用算法： 三色标记 + `SATB`

* `ZGC`(1ms, C++) 不分代

    使用算法：颜色指针 (colour pointers) + 写屏障

* `Shenandoah` 不分代

    使用算法：颜色指针 (colour pointers) + 读屏障

* `Eplison` Debug 使用

> **到目前位置还没有不会产生 `stop-the-word(STW)` 的垃圾回收器；**
> 
> JDK 诞生 `Serial` 追随，提高效率，产生了 PS ，为了配合 CMS ，诞生了 PN ，CMS 引入于 JDK 1.4 后期；
> 
> CMS 是里程碑式的 GC， 开启了并发回收的过程，问题较多，目前没有任何发行版本默认使用 CMS 。

JDK 1.8 默认垃圾回收器： `PS` + `PO`

常用的 GC 组合：
* `Serial` + `SerialOld`
* `ParallelScavenge` + `ParallelOld`
* `ParNew` + `ConcurrentMarkSweep`

> FGC
> 
> 整体内存需要回收；
> 触发条件：老年代没有足够的内存空间可用，默认是 `PS` + `PO` ，可以修改成 `PN` + `CMS` ，`PO`/`CMS` 来执行 FGC

![垃圾回收器](./GC.png)

> `ParNew` vs `ParallelScavenge`
> 
> PN 响应时间优先（配合CMS)
> PS 吞吐量优先
> 
> [HotSpot Virtual Machine Garbage Collection Tuning Guide](https://docs.oracle.com/en/java/javase/13/gctuning/ergonomics.html#GUID-DB4CAE94-2041-4A16-90EC-6AE3D91EC1F1)

> 垃圾回收器与内存大小关系：
> 
> * Serial : n0 MB
> * PS : n00 MB ~ n GB
> * CMS : 20 GB
> * G1 : n00 GB
> * ZGC : 4 TB

### Concurrent Remark （并发标记）阶段的算法；

* 三色扫描算法
    * 黑 自己已经标记， `fields` 都标记完成
    * 白 自己已经标记， `fields` 还未来得及标记
    * 灰 没有遍历到的节点
  
    > 在并发标记时，引用可能产生变化，白色对象有可能被错误回收；
* SATB(Snapshot At The Beginning)
    * 
* IncrementalUpdate

### GC 组合参数设定

* `-XX:+UseSerialGC` : SerialNew(DefNew) + SerialOld

    小型程序，默认情况下不适用此选项；Hotspot 会根据计算配置和JDK版本自动先择收集器；

* `-XX:+UseParNewGC` : ParNew + SerialOld

    组合已经很少使用（部分版本已经废弃）；

* `UseConc[urrent]MarkSweepGC` : ParNew + CMS + SerialOld
* `UseParallelGc` : ParallelScavenge + ParallelOld （JDK 1.8 默认）
* `UseParallelOldGC` : ParallelScavenge + ParallelOld
* `UseG1GC` : G1

> Windows 查看 GC 方法： 
> 
> * `java +XX:+PrintCommandLineFlags -version`
> * 通过 GC 日志分辨
> 
> Linux 查看 GC 方法

## GC 日志

参数：
* `-XX:+PrintGC`
* `-XX:+PrintGCDetails`
* `-XX:+PrintGCStamps`
* `-XX:+PrintGCCauses`

GC 日志解析：
* GC类型： 

    `GC`: YGC / `FUll GC`: FullGC

* GC 原因： `(xxxx)`
* GC 年代： 回收前后内存变化，年代总大小，回收耗时，回收前后堆的大小，堆总大小，整体 GC 耗时
* (linux) time ls: user 用户空间耗时， sys 内核空间耗时， real 总体耗时

#### Heap dump

内存溢出 GC 打印堆信息；

`eden space 5504K, 100% used [0x00000000fec00000,0x00000000ff160000, 0x00000000ff160000)`
内存地址含义：**起始地址**，**使用空间结束地址**，**整体空间结束地址**；

![HeapDump](./HeapDump.png)

* `reserved` 虚拟机内存保留
* `committed` 虚拟内存占用
* `capacity` 总容量
* `used` 已经使用

> `total = edon + survivor[from or to]`

## GC tuning _GC 调优_

> **概念：**
> 
> * **吞吐量**：**用户代码执行时间**/(**用户代码执行时间**+**垃圾回收时间**)
> * **响应时间**：**STW**越短，响应时间越好

调优的目标： 
* 吞吐量优先需求
* 响应时间需求

> 问题：
> 
> * 科学计算：吞吐量优先；一般是： `PS + PO` ；（数据挖掘，任务处理）
> * 用户交互类： 响应时间优先；JDK 1.8 一般是 `G1` ；（网站）

### GC 调优范畴

1. 根据需求进行 JVM 规划和预调优；
2. 优化运行 JVM 环境；
3. 解决 JVM 运行过程中出现的各种问题；
