# Network

**OSI 七层参考模型** : **分层解耦**

应用层 <=> 表示层 <=> 会话层 <=> 传输控制层 <=> 网络层 <=> 链路层 <=> 物理层

* 应用层： 应用软件、 Tomcat、浏览器；与人交互或后台运行提供服务。
* 表示层： 协议、语义、加密
* 会话层： 会话保持，Session
* 传输层： 传输控制
* 网络层： 数据路由、传输，
* 链路层： 点对点通信控制
* 物理成： 传输媒介，硬件


**TCP/IP 协议：**

应用层 <=> 传输控制层 <=> 网络层 <=> 链路层 <=> 物理层


* 应用层： 端点

  HTTP SSH SMTP

* 传输控制层： 三次握手

  TCP 面向连接，可靠传输 UDP 无连接，不可靠传输 Socket

    * 创建连接：**三次握手** 完成创建网络连接资源

      从方向和 IO 两个层次角度解释

        1. 第一次请求连接，发起方发送 syn（发起方进入 SYN_SEND 状态等待被链接方确认）
        2. 第二次响应连接 被链接方发送 syn+ack （发起方接收确认连接建立进入 SYN_RECV 状态，服务方还没有完成连接确认）
        3. 第三次确认响应 发送方发送确认响应 ACK （发送方与被连接方进入 ESTABLISHED ），在**内核开辟资源**（资源代表了链接）

    * 断开链接：**四次挥手** 释放资源

      Socket (一台机器可以有65535个端口资源)

        1. 第一次 请求断开方发送断开连接请求 `FIN`
        2. 第二次 **被链接方****确认**收到断开连接请求，响应 `FIN_ACK`，**服务端**进入 `close_wait` 状态，**请求断开方**收到响应进入 `fin_wait2` 状态 
        3. 第三次 **被链接方**发送断开连接请求 `FIN`
        4. 第四次 **请求断开方****确认**被链接方的断开请求，响应 `ACK` ，双方销毁资源，**被链接方**进入 `closed` 状态，**请求断开方**进入 `time_wait` 状态；

        > `time_wait` : 2*`MSL` 两倍报文最大生存时间，因为有可能最后的确认 `ACK` 没有到达对方服务器，多存活一段时间，等待对方重发 `FIN`
        > 
        > `MSL` Maximum Segment Lifetime 最大报文段寿命：任何报文在网络上存在的最长的最长时间(`s`)；
        > 
        > `TTL` Time to live 生存时间：IP 数据包可以经过的最大路由数；

  > 三次握手 ==>> 数据传输 ==>> 四次挥手
  >
  > 通过三次握手与四次挥手来回的数据包互相确认把系统资源的创建和销毁的过程叫做面向连接。
  >
  > 三次握手创建连接、数据传输和四次挥手断开链接称为一个最小粒度，不可被分割。

* 网络层： 下一跳

  网络使用下一跳机制：当前主机一步之内的网络信息（包括网关）路由表（`route -n ）通过路由判定（按位与）

  IP 点分字节

* 链路层： 节点 (`arp -a`)

  arp 协议：发送ARP协议（目标 Mac 地址是全 `F` 目标 IP 为网关，源 Mac 源 IP ）会被广播，广播到网关，会回复给发送ARP协议的主机

  交换机会学习 mac 地址和网络端口 的关系

* 物理层：


> TCP/IP 协议：基于下一跳的机制，IP是端点间，Mac地址是节点间的

> 传输控制层一下 基本上属于内核空间。

### TCP/IP 参数

* `SendBufferSize` 发送缓冲区大小
* `TcpNoDelay` 是否不延迟立即发送
* `OOBInline` 是否优先发送第一个字节
* `Keepalive` 链接保持

### Socket

Socket 是四元组(`client ip` `client port` `server ip` `server port`)；握手完成后内核创建和维护四元组信息（包括缓冲区 Buffer ）；

Socket 的四元组分配给对应的程序时，映射为程序内的 `FD` （表示流）；FD作用范围时线程，线程内 `FD` 唯一；
在程序中通过文件描述符 `FD` 来映射到对应的 Socket 四元组；

`BACK_LOG` 备用链接数量（默认50个）；

> Socket 是四元组，所以端口号可以被多个 Socket 共用只要四元组保证唯一就可以；
> 
> 服务端Socket：因为是监听状态，如果再启动一个同一个端口，不能保证四元组唯一；

> Socket:
> 
> 1. listen socket : 连接请求三次握手后，通过 accept 处理连接请求，获得 connection socket
> 2. connection socket : 连接建立后的数据读写使用

## 网络 IO 模型

> **同步** **异步** / **阻塞** **非阻塞**
> 
> **同步**：应用自己处理IO的读写；
> 
> **异步**：由Kernel完成IO的读写；（类似于程序访问buffer而不是访问IO）（只有 Windows iocp 支持）
> 
> **阻塞**：BLOCKING
> 
> **非阻塞**：NONBLOCKING
> 
> * 同步阻塞：BIO
> * 同步非阻塞：NIO、NIO-Multiplexing(Select-NIO、Poll-NIO、Epoll-NIO)
> * 异步非阻塞

> [C10K](http://www.kegel.com/c10k.html)

### BIO (Blocking IO)

创建Socket连接系统调用
1. `fd = socket()` 创建 Socket 获取文件描述符
2. `bind(fd, port)` 绑定
3. `listen(fd)`
4. `fd = accept(fd,` 阻塞等待连接（连接建立返回新的 fd （对应新建连接的四元组））
5. `clone()` 创建新线程
6. `recv(fd,` 阻塞等待接收数据

> 多线程优化处理多个连接：
> 
> * `4` 步骤阻塞等待新的连接，获取连接后创建新线程 `5` 处理具体连接
> * `5` 在创建的新线程中阻塞 `6` 等待数据

> **BIO 问题**：
> 
> 内核提供的是**阻塞API**导致程序等待调用返回（**BLOCKING**）；
> 
> * 内核空间应用空间切换成本
> * 每连接一线程
> * 线程切换（CPU调度）成本
> * 线程内存开销

### NIO (Nonblocking IO)

> NIO 含义：
> 
> * JDK : New IO (socketChannel, buffer)
> * OS : Nonblocking IO

> NIO 相对 BIO 的改进:
> 
> 内核系统调用支持异步调用，模型上做到一个线程可以完成接收连接和处理连接；
> 
> 1. (`ServerSocketChannel.configureBlocking(false)` 监听服务设置非阻塞 ) 系统调用 `accept()` 不阻塞
> 2. (`SocketChannel.configureBlocking(false)` client 连接设置非阻塞 ) 系统调用 `read()` 不阻塞

创建Socket连接系统调用
1. `fd = socket()` 创建 Socket 获取文件描述符
2. `bind(fd, port)` 绑定
3. `listen(fd)`
4. `fd = accept(fd)` 非阻塞等待连接（连接建立返回新的 fd （对应新建连接的四元组），返回 `-1` 表示没有连接进入）（新连接添加到集合中）
5. `recv(fd,` 非阻塞等待接收数据（遍历连接集合的所有 socket 连接）

> NIO 非阻塞模型，使用单一线程（或者几个线程）就可以处理很多连接请求和传输处理；

> **Too many open file**
> 
> `ulimit -n 1024` 设置线程可用打开的文件数量（普通用户（非root）受约束）；
> 
> `/proc/sys/fs/file-max` : Kernel 能到创建的文件描述符数量

> **NIO 问题**：
> 
> IO 连接数量接近 C10K ，每次循环一次需要对已经连接的客户端进行 `recv()` 系统调用获取是否有数据到达（很多调用是无意义的、浪费的）；
> 
> * 全量遍历已经连接的文件描述符；

### NIO-Multiplexing(Nonblocking IO Multiplexing)

基于 `select()` `poll()` `epoll()` 的多了复用器非阻塞IO；
`select()` `poll()` `epoll()` 可以监控多个文件描述符，等待一个或多个文件描述符进入就绪状态；

#### `select`

遵循 POSIX 规范；

> Select-NIO 相对于 NIO 的改进：
> 
> * 通过一次系统 `select()` 调用获取所有连接 Socket IO **状态**
> * 应用根据结果通过 `recv()` 读取有状态的 `fd`

`select()` 使用的时候受 **`FD_SETSIZE`** （默认1024）大小**限制**（已经逐渐使用的越来越少），当文件描述符 fd 数量超过 `FD_SETSIZE` 时，要分为多次 `select()` 进行查询；

> `poll` `epoll` 是对 `select` 的增强

创建Socket连接系统调用
1. `fd = socket()` 创建 Socket 获取文件描述符
2. `bind(fd, port)` 绑定
3. `listen(fd)`
4. `fd = accept(fd)` 非阻塞等待连接（连接建立返回新的 fd （对应新建连接的四元组），返回 `-1` 表示没有连接进入）（新连接添加到集合中）
5. `recv(fd,` 非阻塞等待接收数据（遍历连接集合的所有 socket 连接）


#### `poll`

`poll()` 相比于 `select()` 没有 **`FD_SETSIZE`** （默认1024）大小**限制**

> `select` / `poll` vs `NIO`
> 
> 都需要遍历所有的连接的 fd ；
> * `NIO` 下每次遍历都需要从用户态到内核态的切换
> * `select` `poll` 遍历过程发生在内核态中，只需要一次用户态到内核态的切换

> `select` `poll` 弊端：
> 
> * 每次重复传递文件描述符；
> * 每次内核调用，内核对传递的文件描述符做全量的遍历；

创建Socket连接系统调用
1. `fd = socket()` 创建 Socket 获取文件描述符
2. `fcntl(fd)` 设置非阻塞
3. `bind(fd, port)` 绑定
4. `listen(fd)`
5. `poll()` 调用 poll 获取是否有链接
6. `fd = accept(fd)` 非阻塞等待连接（连接建立返回新的 fd （对应新建连接的四元组），返回 `-1` 表示没有连接进入）（新连接添加到集合中）
7. `recv(fd,` 非阻塞等待接收数据（遍历连接集合的所有 socket 连接）、
8. `fcntl(fd)` 连接 socket 设置非阻塞


#### `epoll`

> `epoll()` 之前的模型中，内核仅仅将将 IO 设备（缓冲区）与对应文件描述符缓冲区关联，作需要**应用**主动查询（通过系统调用）某一个或某些文件描述符是否可以读写，并返回对应的状态；
> 
> `epoll()` 模型下，规避了从用户空间切换内核空间对fd的全量遍历；内核提供了基于事件的回调函数

`epoll` 提供的系统调用：
* `epoll_create()`
* `epoll_ctl()`
* `epoll_wait()`


创建Socket连接系统调用
1. `fd = socket()` 创建 Socket 获取文件描述符
2. `fcntl(fd)` 设置非阻塞
3. `bind(fd, port)` 绑定
4. `listen(fd)`
5. `epfd = epoll_create()` 内核开辟内存空间（用来存放红黑树）返回描述开辟空间的文件描述符 `epfd` （仅调用一次）
6. `epoll_ctl(epfd, op, fd, event)` 对内核空间存放的文件描述符进行操作；
    * `epfd`: 内核存放红黑树空间的 fd
    * `op`: 操作类型
        * `EPOLL_CTL_ADD`
        * `EPOLL_CTL_MOD`
        * `EPOLL_CTL_DEL`
    * `fd`: 需要添加到红黑树中的文件描述符 可以是监听(listen socket)、连接(connect socket)类型
    * `event`: 关注的事件(accept/read...)
7. `epoll_wait()`: 等待内核中文件描述符由**红黑树转移到链表**的结果集；
8. `accept()`
9. `fcntl(fd)`

> `epoll` vs `select` / `poll`
> 
> epoll 基于中断将数据从设备拷贝到文件描述符缓存完成后，将红黑树中的fd状态转移到链表结果集中；
> 避免了 `select` `poll` 切换用户态内核态遍历查询文件描述符状态；
> 
> * 用户态内核态切换不传递文件描述符集合
> * 避免 select 调用，内核遍历文件描述符集合

## 写事件 vs 读事件

写事件：
send-queue 只要是空，就一定会给你返回可以写的事件，回掉写方法；

>需要明确什么时候写：不依赖于 send-queue ，首先需要关心什么时候需要写，然后关心 send-queue 是否有空间；
> 
> **什么时候需要写，什么时候注册写事件；**

读事件：需要在程序启动时注册，一边时刻接收连接发来的数据；

> * `FC` 函数调用（寻址）
> * `SC` 系统调用（init 0x80)
> * `RPC` 远程调用（socket）
> * `IPC` 进程间调用（管道、信号量、socket）
