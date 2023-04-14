# Zookeeper


Zookeeper 分布式协调：

* 可扩展

  * 架构或框架中最重要的是**角色**；
  
    Zookeeper 包括 Leader 、 Followers 与 Observers （比Followers 等级更低，不参与投票）。

  * 读写分离：
  
    Leader 完成对数据的增删改。
  
    Followers 和 Observers 完成读功能。 Observers 不参与投票，只会追随（放大查询能力）。
  

* 可靠性

  > 攘其外必先安其内；

  快速选出 Leader 的过程；

  * 数据可靠、可用、一致性：
  
    > Zookeeper 一致性是最终一致性；
    
    过半更新，最终一致性过程中，没有更新的节点是否对外提供服务。

    没有组建过半数的服务最终会 Shutdown 自己的服务。

* 时序性

* 速度快

  * 正常状态响应快
  * 异常恢复快
  
    集群中 Leader 不可用时，有 Followers 参与投票选主， Observers 不参与投票，只会追随（放大查询能力）。
    所以集群投票选举的速度由 Followers 角色的数量来决定。


> 配置节点为 Observers
> 
> Observers 节点配置文件添加 `peerType=observer` ；
> 
> 其它所有节点配置文件中：在每个服务器配置定义关于 Observer 服务起的信息后添加 `:observer`
> 
> `server.1:ip:2181:3181:observer`

## Paxos 协议


Paxos 是一个基于消息传递的一致性算法。
被认为是到目前为止唯一的分布式一致性算法。
其他算法都是 Paxos 的改进或简化。


Paxos 前提是没有拜占庭将军问题；即可信网络。


两阶段提交（过半通过，两阶段提交）：

* 第一阶段：将要做的事情发送出去，记录到日志；

* 第二阶段：第一阶段的回应过半，第二阶段才可以进行，将事情正式提交。


> 小岛(Island)——ZK Server Cluster
> 
> 议员(Senator)——ZK Server
> 
> 提议(Proposal)——ZNode Change(Create/Delete/SetData…)
> 
> 提议编号(PID)——Zxid(ZooKeeper Transaction Id)
> 
> 正式法令——所有ZNode及其数据
> 
> 总统——ZK Server Leader


## ZAB 协议 Zookeeper 原子广播协议


ZAB 协议工作在可用状态，有 Leader 的时候。


原子广播协议：

* 原子：不可分割；要么成功，要么失败，没有中间状态（基于队列+两阶段提交）；

* 广播：分布式多节点；（并不代表全部知道，过半则成功）；

* 队列：FIFO（顺序性）；


ZAB 协议：

1. Client 请求创建节点 `A` 发送给 Client 连接的 Follower ；
2. Follower 将 Client 的请求创建节点 `A` 发送给 Zookeeper 的 Leader ；
3. Leader 对创建请求进行创建（创建事务id(`cZxid`)等；
4. 第一阶段提交：

    1. Leader 给所有的 Followers 发起向**磁盘**中**写日志(`log`)**； 
    
        > Leader 为每一个连接创建一个队列

    2. Followers 返回给 Leader 确认消息； Leader 本身也有一个确认消息，总数是否过半，过半则成功，否则失败；
   
5. 第二阶段提交（确认消息过半）：

    1. 将创建的信息由 Leader 发送给 Followers 写入内存中；
    2. Followers 回复 Leader 确认消息；
   
6. 给 Client 返回成功；


> Zookeeper `sync` 命令是可选项；
> 
> 取决于你的具体应用长久，是否允许暂时的不一致的出现。


## Zookeeper 选举过程


> * `3888` 端口构建了两两通信的模型；
> * 只要任何节点投票都会触发那个准 Leader 发起自己的投票，别人也会给它起投票，一旦过半直接成为了真正 Leader ；
> * 推选制：先比较 `zxid` ，再比较 `myid` ；
> 
> 如果集群从整体不可用状态恢复到可选主状态的时候，新加入的主将不会称为 Leader （不同代次，但是再 Docker 实验过程中时可以的）

场景：

* 第一次启动集群

  1. 首先判断节点的 `Zxid` 是否过半一致；
  2. 过半一致，选择 `myid` 最大的当作 Leader ；

* 重启集群，或 Leader 不可用

    1. 旧的 Leader 不可用，首先发现的节点会首先投自己一篇将自己最大的 `Zxid` 发送给其他节点请求投票；
    2. 如果其他节点收到发起投票的的节点的消息，发现发起节点的 `Zxid` 不是最大的，纠正发起节点的 `Zxid` 将自己的 `Zxid` 发送给发起投票的节点；
    3. 发起纠正的节点同时触发新一轮的投票，首先投自己，然后将 `Zxid` 发送给初始发起投票节点，同时广播给其他节点；
    4. 纠正发起者如果投票过半，自己直接称为 Leader ；


不同角色会有自己的 `myid` `Zxid` 


新的 Leader 选举的顺序条件(所有的数据都必须是过半通过的。)：
1. 数据最全的 `Zxid` 最大
2. `myid` 最大的


## watch 监控、观察


Zookeeper 会尽量做到统一视图，提供了目录树结构，


Client 之间可能会存在一定的依赖关系，依赖与被依赖之间并没有实现分布式协调的问题；
如果通过 Zookeeper 可以知道需要依赖其他 Client 状态，就可以不用去考虑分布式的协调问题，即 Zookeeper 的 `Watch` 机制。

> 首先 `get` 获取到需要依赖的 Client 的资源，然后调用 `watch` 监控依赖的状态；
> 取代了自己实现心跳的功能；
> 
> 心跳 vs `watch`
> 
> * 方向性：由主动轮询转变成被动等待事件触发；
> 
> * 时效性：心跳机制受轮询时间窗口的限制，相比而言事件机制更加及时。
> 
> 同时也节省网络带宽和计算资源；


提供的事件类型：

1. `create`
2. `delete`
3. `change`
4. `children`


## API 开发


* 基础 API 使用

* 分布式配置（配置中心、服务发现）

  > 使用 Zookeeper 主要是因为它的回调机制，不需要轮询查询变更。

  将所有的配置信息存储到 Zookeeper 中去，客户端通过 `get` 获取配置信息，同时 `watch` 当配置信息发生变更，对应 `watch` 差生回调，更新客户端配置。

* 分布式锁

## callback (响应式编程)


> 更充分压榨 OS 计算机硬件 的资源和性能。
