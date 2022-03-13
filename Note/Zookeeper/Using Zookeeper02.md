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

## watch


## API 开发


## callback (响应式编程)


> 更充分压榨 OS 计算机硬件 的资源和性能。
