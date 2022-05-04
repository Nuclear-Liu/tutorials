# CMS (ConcurrentMarkSweep) _并发标记清除_

## CMS 阶段

1. `initial mark` 初始标记(STW)（单线程），指标及根对象

    根据根可达算法，扫面跟对象（线程栈变量、静态变量、常量池、JNI指针）；只需要找到根对象，所以 STW 时间比较短；

2. `concurrent mark` 并发标记（多线程），于应用程序同时运行

    （最耗时的阶段，不产生 STW）在阶段一的基础上

3. `remark` 重新标记(STW)（多线程），多是已经并发标记完成，对新产生的垃圾进行标记

    需要重新标记的数量相对较少，所以 STW 停顿时间比较短；

4. `concurrent sweep` 并发清理，清理过程也会产生新的垃圾，这种叫做**浮动垃圾**，需要下次CMS运行的时候进行清理；

# [G1 (Garbage First)](https://www.oracle.com/technical-resources/articles/java/g1gc.html)

> 计算机领域重要思想： **分而治之**、**分层**

Garbage First Garbage Collector (G1 GC) 是适用于 Java HotSpot VM 的**低暂停**、服务器风格的分代式垃圾回收器。
G1 GC 使用并发和并行阶段实现其目标暂停时间，并保持良好的吞吐量。
当 G1 GC 确定有必要进行垃圾回收时，它会**先收集存活数据最少的区域**（**垃圾优先**）。

G1 中将内存划分为大小不同的 `Region` 进行管理；
`Region` 从 `1M` 开始最大 `32M` （按照2的幂次一次底层）；
每一个 `Region` 在逻辑上依然分代不同的代；

**压缩机制**：G1 是一种带压缩的收集器，在回收老年代的 `Region` 时，可以将存活对象从一个 `Region` 拷贝到另一个 `Region` ，拷贝的过程实现了局部的压缩。

> **G1 代体系**：
> 
> * `eden`
> * `servivor`
> * `old`
> * `humongous` ：超过单个 Region 的 50% 称为大对象，大对象区（可能包含多个连续区域的 `region` ）
>
> **新生代**中的对象，要么被**回**收、要么**晋升**，至于新生代也采取分区机制的原因，则是因为与老年代的策略统一，方便调整代的大小。

GC 特点：
* 并发收集
* 压缩空闲空间不会延长 GC 的暂停时间
* 更易预测的 GC 暂停时间
* 适用不需要实现很高的吞吐量的场景

G1 中新生代和老年代比例是动态的，不建议手动指定；是 G1 预测停顿时间的基准；老年区从 5% 到 60% 区间浮动。

### 基本概念

* `CSet` Collection Set

    一组可被回收的 Region 的集合。
    在 `CSet` 中存活的数据会在 GC 过程中被移动到另一个可用分区， `CSet` 中的分区可以来自 `eden` 空间、 `servivor` 空间或者 `old` 。
    `CSet` 会占用不到整个堆空间的 `1%` 大小。

* Card Table

    用于分代模型中提高垃圾回收速度。 YGC 时需要扫描整个 Old 区，效率低，JVM 设计 Card Table ，垃圾扫描时只需要扫描 `dirty` 的 Card Table 。

    将内存分成 Card 具体对象存在于 Card 中，如果有一个老年代的 Card 指向了新生代内存对象，标记当前 Card 为 `dirty` **脏**；通过使用一个 BitMap 指示每个 `Card` 是否是脏；

* `RSet` Remembered Set

    记录了其他 Region 中的对象到本 Region 的引用，存放到当前 Region 中。

    `RSet` 的价值在于使的垃圾收集器不需要扫描整个堆，查找引用当前 Region 中对象的位置，只需要扫描 `RSet` 即可。

### GC 动作触发

* YGC(多线程执行)
    * Eden 空间不足
* FGC(JDK 10 支持并行 FGC )
    * Old 空间不足
    * 调用了 `System.gc()`

> G1 FGC 优化处理：
> 
> * 扩内存
> * 提高 CPU 性能（回收的块，业务逻辑产生对象的速度固定，垃圾回收越快，内存空间越大）
> * 降低 `MixedGC` 触发的阈值，让 `MixedGC` 提早发生（默认 45% ）
> 
> `MixedGC` 相当于一个 `CMS` ，通过参数 `-XX:InitiatingHeapOccupacyPercent`
> 当老年代适用超过阈值，启动 `MixedGC` 。

### `MixedGC` 的过程

类似于 CMS

* 初始标记 STW
* 并发标记
* 最终标记 STW （重新标记）
* 筛选回收 STW（并行） 筛选最需要回收的区域


> **颜色指针**
> 
> 在对象引用的 64位 地址中最高的 3 个 Bite 进行颜色标记；
> 当对象的引用发生改变进行颜色标记；
> 
> **三色扫描算法**
>     * 黑 自己已经标记， `fields` 都标记完成
>     * 白 自己已经标记， `fields` 还未来得及标记
>     * 灰 没有遍历到的节点
> 
>     > 在并发标记时，引用可能产生变化，白色对象有可能被错误回收；
> 
>  **SATB(Snapshot At The Beginning)**
>     *
> 
>  **IncrementalUpdate**

