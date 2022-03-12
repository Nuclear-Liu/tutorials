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
> JVM 中一个线程栈大小默认为 `1M` 。
> 
> * 线程多了调度成本 CPU 浪费；
> * 内存成本增加；


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


> **多路复用的 NIO 同步非阻塞时期**
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
> * `epoll_create` : 返回 `epfd` 文件描述符，创建一个共享空间。
> * `epoll_ctl` : 向 `interest list` 添加 socket `fd` 文件描述符， 从 `interest list` 删除 socket `fd` 文件描述符。
> * `epoll_wait` : 等待事件（事件驱动）。
> 
> 零拷贝系统调用 `ssize_t sendfile(int out_fd, int in_fd, off_t *offset, size_t count);`
> 
> `sendfile` + `mmap` => Kafka # `mmap` 对发送来的消息的 `fd` 进行用户空间和内核空间的零拷贝， `sendfile` 实现消费这对磁盘文件的网络传输的零拷贝传递给消费者。


Redis 和内核之间使用的是 `epoll` (eventpoll) （非阻塞多路复用）系统调用。
因为是单线程， Redis 对命令的执行是顺序性（每链接内的命令顺序执行）的。


## 命令使用


redis-cli 使用帮助： `redis-cli -h` ；

redis-cli 中提供了 `help` 帮助。

### `@generic` 通用组

* `TYPE key`

  > Determine the type stored at key

* `OBJECT subcommand [arguments [arguments ...]]`

  > Inspect the internals of Redis objects
  
  `OBJECT help` 获取 `OBJECT` 命令参数的帮助；

  **Subcommands**:

  * `ENCODING <key>`
  
    Return the kind of internal representation used in order to store the value associated with a key.
  
  * `FREQ <key>`
  
    Return the access frequency index of the key. 
    The returned integer is proportional to the logarithm of the recent access frequency of the key.
  
  * `IDETINE <key>`
  
    Return the idle time of the key, that is the approximated number of seconds elapsed since the last access to the key.
  
  * `REFCOUNT <key>`
  
    Return the number of references of the value associated with the specified key.

### `@string` 字符串 (`byte`) 组

Redis string 二进制安全；
Redis 只存储字节流，没有对字符串进行编解码

> 具有 5 种类型：

* `SET`
  * `[NX|XX]` 
    `NX` : 只有 `key` 不存在的时候才会设置 `value` ；可以用于**分布式锁**，成功的获得到锁。
    `XX` : 只有 `key` 存在的时候才可以设置 `value` ；

* `MSET`

* `MGET`

* `MSETNX`

  Set multiple keys to multiple values, only if none of the keys exist

  有一笔操作失败，所有的多比操作都失败。

* Int Operation

  > 数值类型应用场景：
  > 
  > 使用 `INCR key` `DECR key` 实现。
  > 
  > 将对数据库的事务操作完全转换为 redis 的内存操作，降低数据库压力。
  > 
  > 1. 秒杀
  > 2. 抢购
  > 3. 详情页的一些统计数据，评论数，好友数

* Bitmap Operation

  * `SETBIT key offset value`
  
    Sets or clears the bit at offset in the string value stored at key
  
  * `BITCOUNT key [start end]`
  
    Count set bits in a string
  
  * `BITPOS key bit [start] [end]`
  
    Find first bit set or clear in a string
  
  * `BITOP operation destkey key [key ...]`
  
    Perform bitwise operations between strings

> 位图类型应用场景：
> 
> 1. 统计用户登录天数，且时间窗口随机：
>
>  使用时间（天）和用户 id 构成一个二位数组；
>  如果登陆了，对应时间和用户id的位置二进制位置为 `1` ;
>  一年最多 366 天， `366d/8bit < 46byte` 需要的存储空间： `46byte*count(user)`
>
> 2. 电商做活动，假设电商有 2 亿用户，登录的用户送礼物，没人只能送一次，大库备货多少礼物？
>
>  用户分为 僵尸用户、冷热用户；
>
>  过往某一个时间段内的活跃用户量。再将活跃用户量膨胀一定的比例，既是备货量。
>
>  活跃用户量的计算：再统计时间窗口内多次登陆的用户需要去重只记一次。
>
>  将时间段内的 `bit` 数组做或运算。然后统计运算结果中 `1` 的个数。

### `@list` 链表组

> 底层实现双向链表；
> 
> 同样的提供了正负索引的功能。
> 
> 可以抽象提供：链表操作、队列操作、堆栈操作、数组操作、阻塞单播队列( `FIFO` )。

* Left Operation

  * `LPUSH key value [value ...]`
  
    Prepend one or multiple values to a list

  * `LPOP key`
  
    Remove and get the first element in a list
  
  * `LPUSH key value`
  
    Prepend a value to a list, only if the list exists

* Right Operation

  * `RPUSH key value [value ...]`
  
    Append one or multiple values to a list
  
  * `RPOP key`
  
    Remove and get the last element in a list

* Blocking Operation

  > `timeout = 0` ：一直阻塞；
  > 
  > `timeout > 0` ： 阻塞 `timeout` 时间；

  * `BLPOP key [key ...] timeout`
  
    Remove and get the first element in a list, or block until one is available
  
  * `BRPOP key [key ...] timeout`

* General Operation

  * `LRANGE key start stop`

    Get a range of elements from a list

  * `LINDEX key index`

    Get an element from a list by its index

  * `LSET key index value`

    Set the value of an element in a list by its index

  * `LREM key count value`

    Remove elements from a list

    > `count > 0` ：从左侧开始移除 `|count|` 个 `value` ；
    > `count = 0` : 移除链表中出现的所有 `value` ；
    > `count < 0` ： 从右侧开始移除 `|count|` 个 `value` ；

  * `LINSERT key BEFORE|AFTER pivot value`
  
    Insert an element before or after another element in a list
  
  * `LTRIM key start stop`
  
    Trim a list to the specified range

### `@hash` 字典组


* General Operation

  * `HSET key field value`

    Set the string value of a hash field

  * `HMSET key field value [field value ...]`

    Set multiple hash fields to multiple values

* Calculating Operation

  * `HINCRBY key field increment`
  
    Increment the integer value of a hash field by the given number
  
    > `increment > 0` : add
    > 
    > `increment < 0` : sub
  * `HINCRBYFLOAT key field increment`
  
    Increment the float value of a hash field by the given amount

> 应用场景：
> 
> 商品的详情页的各种数据的查询与计算。

### `@set` 集合组

> 无重复，随机；

* General Operation

  * `SADD key member [member ...]`

    Add one or more members to a set
  
  * `SCARD key`
  
    Get the number of members in a set
    
  * `SISMEMBER key member`
  
    Determine if a given value is a member of a set
  
  * `SMEMBERS key`
  
    Get all the members in a set
  
  * `SRANDMEMBER key [count]`
  
    Get one or multiple random members from a set
  
    > `count > 0` : 返回集合中元素数量和 `count` 中最小一个数量的随机元素；
    > `count = 0` : 返回空；
    > `count < 0` : 返回一个长度一定是 `|count|` 的结果集（可能重复）；
    
    > 应用场景： 
    > 
    > **抽奖**
    > 
    > 参与人数于奖品数量、中奖是否可以重复；
    >
    > 参与人放入 `set` 
    > 
    > 如果参与人数大于奖品数量： 通过控制 `count` 的正负，实现是否可以重复抽奖， `count` 值是抽取的奖品数量。
    > 
    > 如果参与人数小于奖品数量： `count` 为负， `count` 值为礼品数量。

  * `SPOP key [count]`
  
    Remove and return one or multiple random members from a set

    > 应用场景：
    > 
    > **年会抽奖**

* Calculating Operation

  * `SDIFF key [key ...]`
  
    Subtract multiple sets

  * `SDIFFSTORE destination key [key ...]`
  
    Subtract multiple sets and store the resulting set in a key

  * `SINTER key [key ...]`

    Intersect multiple sets
  
  * `SINTERSTORE destination key [key ...]`

    Intersect multiple sets and store the resulting set in a key
  
  * `SUNION key [key ...]`

    Add multiple sets
  
  * `SUNIONSTORE destination key [key ...]`

    Add multiple sets and store the resulting set in a key

