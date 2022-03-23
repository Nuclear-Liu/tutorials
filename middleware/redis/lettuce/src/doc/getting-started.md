# Getting Started _入门_


> Get started with Lettuce 6.1.8.RELEASE


> 入门使用 Lettuce 6.1.8.RELEASE


You can get started with Lettuce in various ways:


您可以通过多种方式开始使用 Lettuce ：


## 1. Get it _获取_


### For Maven users: _Maven 用户：_


Add these lines to file `pom.xml`:


将这些行添加到文件 `pom.xml`：


```xml
<dependency>
    <groupId>io.lettuce</groupId>
    <artifactId>lettuce-core</artifactId>
    <version>6.1.8.RELEASE</version>
</dependency>
```


### For Ivy users: _Ivy 用户：_


Add these lines to file `ivy.xml`:


将这些行添加到文件 `ivy.xml` 中：


```xml
<ivy-module>
  <dependencies>
    <dependency org="io.lettuce" name="lettuce-core" rev="6.1.8.RELEASE"/>
  </dependencies>
</ivy-module>
```


### For Gradle users: _Gradle 用户：_


Add these lines to file `build.gradle`:


将这些行添加到文件 `build.gradle` 中：


```gradle
dependencies {
  compile 'io.lettuce:lettuce-core:6.1.8.RELEASE
}
```


### Plain Java _纯 java_


Download the latest binary package from [https://github.com/lettuce-io/lettuce-core/releases](https://github.com/lettuce-io/lettuce-core/releases) and extract the archive.


从 [https:github.comlettuce-iolettuce-corereleases](https://github.com/lettuce-io/lettuce-core/releases) 下载最新的二进制包并解压缩存档。


## 2. Start coding _开始编码_


So easy! No more boring routines, we can start.


太简单！没有更多无聊的套路，我们可以开始了。


Import required classes:


导入所需的类：


```java
import io.lettuce.core.*;
```


and now, write your code:


现在，编写您的代码：


```text
RedisClient redisClient = RedisClient.create("redis://password@localhost:6379/0");
StatefulRedisConnection<String, String> connection = redisClient.connect();
RedisCommands<String, String> syncCommands = connection.sync();

syncCommands.set("key", "Hello, Redis!");

connection.close();
redisClient.shutdown();
```


Done!


完毕！


Do you want to see working examples?


您想查看工作示例吗？


* [Standalone Redis](https://github.com/lettuce-io/lettuce-core/blob/6.1.8.RELEASE/src/test/java/io/lettuce//ConnectToRedis.java)

* [Standalone Redis with SSL](https://github.com/lettuce-io/lettuce-core/blob/6.1.8.RELEASE/src/test/java/io/lettuce/examples/ConnectToRedisSSL.java)

* [Redis Sentinel](https://github.com/lettuce-io/lettuce-core/blob/6.1.8.RELEASE/src/test/java/io/lettuce/examples/ConnectToRedisUsingRedisSentinel.java)

* [Redis Cluster](https://github.com/lettuce-io/lettuce-core/blob/6.1.8.RELEASE/src/test/java/io/lettuce/examples/ConnectToRedisCluster.java)

* [Connecting to a ElastiCache Master](https://github.com/lettuce-io/lettuce-core/blob/6.1.8.RELEASE/src/test/java/io/lettuce/examples/ConnectToElastiCacheMaster.java)

* [Connecting to ElastiCache with Master/Replica](https://github.com/lettuce-io/lettuce-core/blob/6.1.8.RELEASE/src/test/java/io/lettuce/examples/ConnectToMasterSlaveUsingElastiCacheCluster.java)

* [Connecting to Azure Redis Cluster](https://github.com/lettuce-io/lettuce-core/blob/6.1.8.RELEASE/src/test/java/io/lettuce/examples//ConnectToRedisClusterSSL.java)

* [Lettuce with Spring](https://docs.spring.io/spring-data/redis/docs/current/reference/html/#redis:connectors:lettuce)


* [Standalone Redis]()

* [Standalone Redis with SSL]()

* [Redis Sentinel]()

* [Redis Cluster]()

* [Connecting to a ElastiCache Master]()

* [Connecting to ElastiCache with Master/Replica]()

* [Connecting to Azure Redis Cluster]()

* [Lettuce with Spring]()
