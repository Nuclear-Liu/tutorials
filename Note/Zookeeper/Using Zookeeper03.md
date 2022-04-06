# 分布式锁


> Redis 单点实现分布式锁（是因为 Redis 的速度优势）的缺点：
>
> * 单点
>
>   单点的故障有可能导致多个人获得锁。
> 
> * 不能开启持久化
> 
>   开启持久化，会涉及到 IO 操作，导致速度下降。


目前实现分布式锁，最方便的是 Zookeeper 实现分布式锁。


**分布式锁需要考虑的问题点**：

1. 争抢锁，要保证只有一个客户端可以获得锁；
2. 获得锁的客户端出现不可用：

  使用 Zookeeper 的临时节点(`Seeesion`)的特性，避免获得锁的客户端出现不可用导致锁死锁。

3. 获得锁的客户端成功后，释放锁；
4. 锁被释放、删除，其他客户端如何收到；

  每个客户端主动轮询、心跳等方式；（延迟、网络负载， Zookeeper 压力）

  `watch` 回调方式，可以解决延迟；（当所释放回调时，需要同时所有抢夺该锁的客户端，会对 Zookeeper 造成访问压力）

  `sequence` + `watch` ：所有的锁在同一个目录下通过临时节点加 `watch` 自己的前一个节点的方式；最小的获得锁；
  一旦最小的释放了锁，Zookeeper 仅需要给 `watch` 了当前节点的第二个节点产生回调。


**可重入锁：**某个线程已经获得某个锁，可以再次获取锁而不会出现死锁；