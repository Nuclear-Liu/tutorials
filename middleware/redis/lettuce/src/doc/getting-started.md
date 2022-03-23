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


Import required classes:


```java
import io.lettuce.core.*;
```


and now, write your code:


```text
RedisClient redisClient = RedisClient.create("redis://password@localhost:6379/0");
StatefulRedisConnection<String, String> connection = redisClient.connect();
RedisCommands<String, String> syncCommands = connection.sync();

syncCommands.set("key", "Hello, Redis!");

connection.close();
redisClient.shutdown();
```


Done!


Do you want to see working examples?


* [Standalone Redis]()

* [Standalone Redis with SSL]()

* [Redis Sentinel]()

* [Redis Cluster]()

* [Connecting to a ElastiCache Master]()

* [Connecting to ElastiCache with Master/Replica]()

* [Connecting to Azure Redis Cluster]()

* [Lettuce with Spring]()
