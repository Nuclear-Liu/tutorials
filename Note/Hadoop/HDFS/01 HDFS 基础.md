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

* NameNode(易失、容量有限)
    * 完全**基于内存**存储**文件元数据**、**目录结构**、**文件 block 的映射**
    * 需要**持久化方案保证数据可靠性**
    * 提供副本放置策略
* DataNode
    * 基于**本地磁盘存储** block (文件的形式)
    * 保存 block 的**校验和**保证数据的可靠性
    * 与 NameNode 保持**心跳**，汇报 block 列表状态

## 4. HDFS 元数据持久化

HDFS 同时使用了**日志文件**和**镜像**的方式保证持久化：
* 日志 `EditLog`
* 镜像 `FsImage`

**恢复通过：最新的 `FsImage` + 增量 `EditLog`**

> 事务的实现方式：
> 
> 1. 日志文件：记录实时发生的增删改操作
>   * 优点：完整性比较好
>   * 缺点：恢复加载慢、占用空间
> 2. 镜像/快照/dump/db：内存全量数据基于某个时间点做向磁盘的溢写
>   * 优点：恢复速度快
>   * 缺点：数据部分缺失

* 任何对文件系统**元数据**产生的修改的操作， `NameNode` 使用 `EditLog` 事务日志记录下来
* 使用 `FsImage` 存储内存所有的**元数据**
* 使用本地磁盘保存 `EditLog` 和 `FsImage`
* `EditLog` 具有完整性、数据丢失少，但回复速度慢、体积易膨胀
* `FsImage` 恢复速度块、体积与内存相当，但不能实时保存
* `NameNode` 使用 `FsImage` + `EditsLog` 整合方案：
    
    滚动增量的 `EditLog` 更新至 `FsImage` ，以保证更近时点的 `FsImage` 和更小的 `EditLog` 体积（ `EditLog` 向 `FsImage` 的合并过程使用另外的几个节点处理

## 5. HDFS 安全模式

1. HDFS 搭建时会格式化，格式化操作会产生一个空的 `FsImage`
2. 当 `NameNode` 启动时，从硬盘中读取 `EditLog` 和 `FsImage`
3. 将所有 `EditLog` 中的事务合并到内存中的 `FsImage` 上
4. 将新版本的 `FsImage` 从内存中保存到本地磁盘上
5. 删除 `EditLog` 因为这个旧的 `EditLog` 的事务都已经合并到 `FsImage` 上
6. `NameNode` 启动后会进入一个称为**安全模式**的特殊状态
7. 安全模式的 `NameNode` 不会进行数据块的复制
8. `NameNode` 从所有的 `DataNode` 接受心跳信号和块状态报告
9. 每当 `NameNode` 检测确认某个数据块的**副本数达到这个最小值**，那么该数据块就会被认为**副本安全(Safely Replicated)**
10. 在一定百分比(参数可配置)的数据块被 `NameNode` 检测确认时安全之后(加一个额外的 `30s` 等待时间)，　`NameNode` 将推出安全模式
11. 接下来确定还有那些数据块的副本没有达到指定数目，并将这些数据块复制到其他 `DataNode` 上

### HDFS 中的 SNN(Secondary Name Node)

* 在非 HA(高可用) 模式下， `SecondaryNameNode` 一般时独立的节点，周期完成对 `NameNode` 的 `EditLog` 向 `FsImage` 合并，减少 `EditLog` 大小，减少 `NameNode` 启动时间
* 根据配置文件设置时间间隔： `fs.checkpoint.period` (默认 `3600` 秒)
* 根据配置文件设置 `EditLog` 大小： `fs.checkpoint.size` (规定 `EditLog` 文件的最大值默认是 `64MB`)

> 实际生产之中基本不用**伪分布式(Pseudo-Distributed Mode)**

## 6. HDFS (block) 的副本放置策略-机架服务器

* 第一个副本：放置在上传文件的 `DataNode` ；如果是集群外提交，则随机挑选一台磁盘不满，CPU 不太忙的节点
* 第二个副本：放置与第一台副本不用的机架的节点上
* 第三个副本：与第二个副本相同机器的节点
* 更多副本：随机节点

## 7. HDFS 读写流程

### HDFS 写流程

1. Client 和 `NameNode` 连接创建文件元数据
2. `NameNode` 判定元数据是否有效
3. `NameNode` 触发副本放置策略，返回一个有效的 `DataNode` 列表
4. Client 和 `DataNode` 建立 Pipeline 连接
5. Client 将数据块（默认 `64M` ）切分成 packet （默认 `64KB` ），并使用 `chunk(512B)` + `chucksum(4B)` 填充
6. Client 将 packet 放入发送队列 `dataqueue` 中并向第一个 `DataNode` 发送
7. 第一个 `DataNode` 收到 packet 后本地保存并发送给第二个 `DataNode`
8. 第二个 `DataNode` 收到 packet 后本地保存并发送给第三个 `DataNode`
9. 这一个过程中，上有节点同事发送下一个 packet （类似于工厂的流水线：**流式其实也是一种变种的并行计算**）
10. HDFS 副本数对于 Client 是透明的
11. 当 block 传输完成， `DataNode` 各自向 `NameNode` 汇报，同时 Client 继续传输下一个 `block`
12. HDFS 中 Client 传输和 block 的汇报也是**并行**的

### HDFS 读流程

1. 为了降低整体的带宽消耗和读取延迟时，HDFS 会尽量让读取程序读取离它最近的副本
2. 如果在读取程序的同一个机架上有一个副本，那么就读取该副本
3. 如果一个 HDFS 集群跨越多个数据中心，那么客户端也将首先读取本地数据中心的副本

* 语义：下载一个文件
    * Client 和 `NameNode` 交互文件元数据获取 `fileBlockLocation`
    * `NameNode` 会按距离策略排序返回
    * Client 尝试下载 block 并校验数据完整性

* 语义：下载一个文件其实是获取文件的所有的 block 元数据，那么**子集获取某些 block 应该成立**。
    * HDFS 支持 Client 给出文件的 `offset` 自定义连接那些 block 的 `DataNode` 自定义获取数据（MapReduce 每个任务可以独立所需数据）
    * 这个是支持计算层的分治，**并行计算的核心**

## HDFS 支持的部署模式

* Local(Standalone) Mode _单个 Java 进程包含所有角色_
* Pseudo-Distributed Mode _为分布式_
* Fully-Distributed Mode _完全分布式_


> Hadoop 安装包目录：
> 
> * `sbin` 服务类命令
> * `bin` 普通命令
