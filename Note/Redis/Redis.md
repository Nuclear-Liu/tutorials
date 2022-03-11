# Redis


> 常识：
> 
> * 磁盘
>   1. 寻址 `ms` 级别
>   2. 带宽 `GB` / `MB`
>
> * 内存：
>   1. 寻址 `ns` 级别
>   2. 带宽 `GB` / `MB`
> 
> * I/O buffer ： 体现成本问题
> 
>    磁盘每个扇区 `512Byte` 带来一个成本变大：索引；
>    
>    所以格式化磁盘时， `4K` 对齐， 操作系统读写都已 `4K` 为单位。


> 数据库：
> 
> 数据库中存储数据以 `data page` 为单位，大小为 `4K` 正好与硬件中 `4K` 一致。
> 数据库通过 索引（ `data page` 中存放的是索引）索引实际的存储数据的 `data page` 。
> 
> 关系型数据库建表：必须给出 schema 。
> 
> schema 中包含了表的列数量和列对应的类型（字节宽度）。
> 倾向于行级存储（表中每行数据宽度一定，当存储到磁盘时没有数据的字段会占用空间，未来对数据进行增删改不需要移动数据，可以在对应位置上进行复写）
> 
> 数据和索引都存储在硬盘中，内存中存在一个 B+T 。
> B+T 的树干都是在内存中（区间和偏移），叶子是每一个 `data page` ，如果查询时条件命中索引，会将对应索引读入内存，进而将对应命中的存储数据的 `data page` 读入内存。
> 
> 最终降低 IO 量。


> 数据库表很大的时候：
> 
> 如果有索引，增删改**变慢**，因为要维护索引；
> 如果有索引，查询速度在 B+T 树干都能存放在内存中（不需要出现内存交换），一个或少量查询可以命中索引，仅需要读取一个 `data page` 进入内存，查询速度依然很快；
> 
> 当有并发大的时候，导致大量数据进入内存，受磁盘IO带宽限制，速度下降。

> 数据在磁盘和内存中体积不一样。内存中通过共享机制和压缩机制减少存储空间的需求。

计算机领域的两个基本基础设施：

  * 冯诺依曼体系的硬件
  * TCP/IP 网络

## 缓存


* Memcached

  Memcached Value 没有类型的概念

* Redis

  Redis Value 提供了多种类型

> Memcached 需要全量请求 Value 到 Client 端进行处理；加重网络负担，Client 需要编解码。
> 
> Redis 中类型不是最重要的， Redis 的 Server 中对每种类型都有自己的方法。
> 
> 本质上是解耦；**计算向数据移动**。

## Redis 网络模型


> Linux 下的一切皆文件，每一个网络连接都是一个文件描述符 fd 。


> **BIO(Blocking IO) 时期**:
> 
> 早先 Linux 用户空间进程通过 `read` 系统调用 读网络连接中的信息 `read fd` 。
> 这个时期 Socket 是 blocking (阻塞，Socket 产生的这些文件描述符，当被读的时候，如果数据包没有到 `read` 系统调用不能返回，处于阻塞状体) 。
> 
> 需要创建多了线程进行读网络文件描述符，且需要频繁的线程切换。


> **NIO (SYN NONBLOCK) 同步非阻塞时期** 
> 
> 创建 Socket 连接的时候可以通过传入 `type: SOCK_NONBLOCK` 设置为非阻塞。
> 应用程序可以单一进程，循环所有用到的网络文件描述符调用系统调用 `read fd` 检查是否有数据到达。
> 
> `int socket(int domain, int type, int protocol);` 
> 
> `type` : `SOCK_NONBLOCK`
> 
> 轮询所有的网络文件描述符发生在用户空间。每次轮询都需要调用内核系统调用 `read` ，内核调用成本大。


> **多路复用的 NIO**
> 
> 内核增加了 `select` 系统调用，用户空间调用新的 `select` 系统调用将所有需要的监听的网络文件描述符作为传参给 `select` 调用，知道有一个或多个文件描述符变成 `ready` 。
> 然后在根据返回的文件描述符依次调用 `read fd` 读取数据（ `read` 只读取有数据的文件描述符）。
> 
> `int select(int nfds, fd_set *readfds, fd_set *writefds, fd_set *exceptfds, struct timeval *timeout);`
> 
> 让系统调用更加有效准确。用户空间应用程序复杂度降低。
> 
> 每次系统 `select` 系统调用都还需要对 `fd` 做从内核空间和用户空间之间的来回复制。


> **epoll 同步非阻塞 多路复用**
> 
> 内核增加 `mmap` 系统调用，为用户空间和内核空间提供了一块内存共享空间。
> 共享内存空间中存放了**`interest list` 链表**（存放需要监控的 `fd` ）和**`ready list` 链表**（数据到达的 `fd` ），
> 
> epoll - I/O event notification facility # I/O 事件通知功能
> `epoll` 包括： `epoll_create`(2) `epoll_ctl`(2) `epoll_wait`(2)
> 
> 零拷贝系统调用 `ssize_t sendfile(int out_fd, int in_fd, off_t *offset, size_t count);`
> 
> `sendfile` + `mmap` => Kafka # `mmap` 对发送来的消息的 `fd` 进行用户空间和内核空间的零拷贝， `sendfile` 实现消费这对磁盘文件的网络传输的零拷贝传递给消费者。


Redis 和内核之间使用的是 epoll (eventpoll) （非阻塞多路复用）系统调用。
因为是单线程， Redis 对命令的执行是顺序的。



## Redis 提供的 `Value` 类型

### `String`
#### `BitMaps`
#### 字符类型
#### 数值类型
### `Hashes`
### `Lists`
### `Sets`
### `Sorted Sets`
