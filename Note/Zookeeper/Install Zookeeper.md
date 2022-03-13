# Install Zookeeper

> 前置条件：
> 
> 安装 JDK 设置 JDK 环境变量。

## Download Package and Unzip Package

Download Zookeeper:

```shell
wget https://www.apache.org/dyn/closer.lua/zookeeper/zookeeper-3.8.0/apache-zookeeper-3.8.0-bin.tar.gz
```

Unzip Zookeeper:

```shell
tar xf apache-zookeeper-3.8.0-bin.tar.gz
```

```shell
mv ./zookeeper-3.8.0 /opt/
cd /opt/zookeeper-3.8.0/
```

## `zoo.cfg`

* `tickTime=2000` : 服务之间心跳间隔 (`ms`)
* `initLimit=10` : 初始化延时上限次数
* `syncLimit=5` : 同步延时上限次数
* `dataDir=/var/lib/zookeeper` : 数据持久化目录
* `clientPort=2181` : 提供给客户端连接的端口号
* `maxClientCnxns=60` : 客户端连接的最大连接数

> 需要提前告诉 Zookeeper 由多少个节点，便于过半判断；
>
> `3888` : 如果 Leader 挂掉或集群初始化， Followers 通过该端口号选择 Leader 节点；
>
> `2888` : 节点（ Leader 和 Followers ）间用于数据同步和连接通信
> 
> `server.x` : `x` 用于选举 Leader （推让制，启动的过半的 node 中最大的 x 为 Leader ）

* `server.1=zookeeper1:2888:3888`
* `server.2=zookeeper2:2888:3888`
* `server.3=zookeeper3:2888:3888`

## config `dataDir` Create file `myid`

> id 设置范围 `[1, 255]`


## 配置环境变量

```shell
vim /etc/profile
```

```shell
export ZOOKEEPER_HOME=/
export PATH=$PATH:$ZOOKEEPER_HOME/bin
```

`. /etc/profile` or `source /etc/profile`
