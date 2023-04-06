# ND4J

NDArray 本质商是一个 n 维数组：即有一定数量维度的数字矩形数组。

* NDArray 的等级是维度的数量；可以创建任意等级的 NDArray
* NDArray 的形状定义了每个维度的大小
* NDArray 的长度定义了数组中元素的总数
* NDArray 的 `stride` 定义每个维度中连续元素的间隔（在底层数据缓冲区）； `stride` 按维度定义，一个等级为 `N` 的 NDArray 有 `N` 个 `stride` 值，每个维度一个
* NDArray 的数据类型指的是 NDArray 的数据类型；注意， ND4J 中是全局设置的，所以所有 NDArray 都应该有相同的数据类型

## NDArray 在内存中的存储

NDArray 在内存中是作为一个单一的平面数字数组（更普遍的，作为一个单一的连续内存块）存储的，因此与典型的 Java 多维数组( `float[][]` `double[][]`)区别很大。

在物理上，支持 INDArray 的数据是存储在堆外的：存储在 JVM 虚拟机之外；

在编码方面， NDArray 可以按照 C (行优先) 或 Fortran (列优先) 顺序编码；
ND4J 可以同时使用 C 与 F 顺序数组的组合；

## 视图：两个或多个 NDArray 指向相同的数据

ND4J 中的一个关键概念是，两个NDArray 实际上可以指向内存中相同的底层数据。
通常，有一个 NDArray 指向另一个数组的子集，这只发生在某些操作中( `INDArray.get()` `INDArray.getRow()` )；

主要动机：
* 避免复制，提高性能
* 在对 NDArray 进行操作方面，获得了更大的权力

## Creating NDArrays

### Zero, One and Scalar-Value Initialized Arrays

* Zero: `Nd4j.zeros(int...)`
* One: `Nd4j.one(int...)`
* Scalar-Value: `INDArray tens = Nd4j.zeros(...).addi(long)`

## Random Arrays

Nd4j 提供了一些方法生成 INDArrays ，其中的内容是伪随机数。

* 生成 [0,1] 均匀分布随机数: `Nd4j.rand(...)`
* 生成平均数为 0 ，标准差为 1 的高斯随机数: `Nd4j.randn(...)`
* 对于可重复性（设置 Nd4j 的随机数发生器种子）: `Nd4j.getRandom().setSeed(long)`


