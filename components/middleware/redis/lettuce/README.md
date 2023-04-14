# LETTUCE


> **Build elastic data access**
> 
> Lettuce is a scalable Redis client for building non-blocking Reactive applications 


> **构建弹性数据访问**
> 
> Lettuce 是一个可扩展的 Redis 客户端，用于构建非阻塞响应式应用程序


* Lettuce
  
  Lettuce is a fully non-blocking Redis client built with [netty](https://netty.io/) providing Reactive, Asynchronous and Synchronous Data Access . 

* Java 8 types

  Lettuce provides asynchronous API with `RedisFuture` (`CompletionStage`) and Reactive types `Flux` [N] and `Mono` [0|1]. 

* Non blocking I/O

  Low-latency communication, backpressure-enabled network engine for NIO TCP, epoll TCP and Unix Domain Sockets. 
  Reactive Streaming is fully supported. 


* Lettuce

  Lettuce 是一个完全非阻塞的 Redis 客户端，使用 [netty](https://netty.io/) 构建，提供 Reactive 、异步和同步数据访问。

* Java 8 types

  Lettuce 提供带有 `RedisFuture` (`CompletionStage`) 和 Reactive 类型 Flux [N] 和 Mono [0|1] 的异步 API。

* 非阻塞 IO

  用于 NIO TCP 、 epoll TCP 和 Unix 域套接字的低延迟通信、支持背压的网络引擎。
  完全支持 Reactive 流式传输。


## [Simple, yet Powerful](https://lettuce.io/docs/getting-started.html) _[简单但功能强大](./src/doc/getting-started.md)_


Lettuce comes with an API that gets you started quickly. 
Its simple yet powerful programming model allows you for trivial use-cases as well as for chained asynchronous flows. 


Lettuce 附带一个 API，可让您快速入门。
其简单而强大的编程模型允许您处理琐碎的用例以及链式异步流。


```text
RedisClient redisClient = RedisClient.create("redis://localhost/0");
StatefulRedisConnection<String, String> connection = redisClient.connect();

System.out.println("Connected to Redis");
connection.sync().set("key", "Hello World");

connection.close();
redisClient.shutdown(); 

```


## [Scale Out](https://lettuce.io/core/release/reference/index.html#ha-sharding) _[向外扩展]()_


Redis Standalone, Master/Slave, Redis Sentinel and Redis Cluster. 
Lettuce connects with all operational models natively supported by Redis. 
Partition-tolerance, Read Slaves and Transport-Level-Security provide the required foundation for highly scalable applications. 


Redis Standalone, Master/Slave, Redis Sentinel 与 Redis Cluster.
Lettuce 与 Redis 原生支持的所有操作模型相连接。
分区容错、从读和传输级安全性为高度可扩展的应用程序提供了所需的基础。


## [Dynamic API](https://lettuce.io/core/release/reference/index.html#redis-command-interfaces) _[动态 API]()_


Redis Modules drive evolution and demand a client that is able to react to change. 
Lettuce's dynamic Redis Commands Interfaces leverage the dynamic module API with custom API interfaces. 


Redis 模块驱动演化，并要求能够对变化做出反应的客户端。
Lettuce 的动态 Redis 命令接口利用带有自定义 API 接口的动态模块 API。


```text
interface MyCommandInterface extends Commands {

  String get(String key);

  @Command("GET")
  byte[] getAsByteArray(String key);

  @Command("NR.RUN net")
  double neuralRun(int from, int to);
}

```


## [Cloud Ready](https://lettuce.io/core/release/reference/index.html#ha-sharding) _[云就绪]()_


Battle-tested with major Cloud-based Redis services. 
Lettuce adopts the specifics of various cloud offerings to seamlessly integrate for best performance. 


经过主要基于云的 Redis 服务的实战测试。
Lettuce 采用各种云产品的特性来无缝集成以获得最佳性能。
