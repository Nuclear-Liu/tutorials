# HDFS 基础

> 更好的支持分布式计算。

## 1. HDFS 存储模型

* 文件线性按**字节**切割成**块(block)**，具有 **offset**/**id**
* 不用文件的 **block** 大小可以不一样
* 同一个文件的最后一个 **block** ，其他 **block(高版本默认：`256M`)** 大小一致
* **block** 的大小依据硬件的 I/O 特性调整
* **block** 被分散存放在集群的节点中，具有 **location**（block在集群中的位置）
* **block** 具有**副本(replication)** 没有主从概念，**副本不能在同一个节点**
* 副本保证**可靠性**和**性能**
* 文件**上传**可以指定 **block** 大小和**副本数**，**上传后只能修改副本数**
* 一次写入多次读取，**不支持修改**（会导致**数据偏移量**的变动）
* 支持追加数据


## 2. HDFS 架构设计

> HDFS 中**角色**即**进程**。

* HDFS 是一个主从(Master/Slaves)架构
* 由一个 NameNode(**主**)和一些 DataNode(**从**)组成
* 面向文件包含：文件数据(data)和文件元数据(metadata)
* NameNode 负责存储和管理**文件元数据**，并维护一个**层次型文件目录书**
* DataNode 负责存储文件数据(block 块)并提供 block 的读写
* DataNode 与 NameNode 维持心跳，并汇报自己持有的 block 信息
* Client 和 NameNode 交互文件元数据和 DataNode 交互文件 block 数据

![hdfs architecture](hdfsarchitecture.png)

![hdfs datanodes](./hdfsdatanodes.png)

## 3. HDFS 角色功能

* NameNode
* DataNode

## 4. HDFS 元数据持久化
## 5. HDFS 安全模式
## 6. HDFS (block) 的副本放置策略
## 7. HDFS 读写流程
