# Network

## TCP

TCP 是面向连接的可靠传输协议；
通过三次握手确认链接（`syn` `syn+ack` `ack`）；
三次握手之后双方在内核开辟资源（资源代表了链接）；

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

* 创建Socket连接系统调用
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

* 创建Socket连接系统调用
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

#### `select()`

遵循 POSIX 规范；

> Select-NIO 相对于 NIO 的改进：
> 
> * 通过一次系统 `select()` 调用获取所有连接 Socket IO **状态**
> * 应用根据结果通过 `recv()` 读取有状态的 `fd`

`select()` 使用的时候受 **`FD_SETSIZE`** （默认1024）大小**限制**（已经逐渐使用的越来越少），当文件描述符 fd 数量超过 `FD_SETSIZE` 时，要分为多次 `select()` 进行查询；

> `poll` `epoll` 是对 `select` 的增强

#### `poll()`

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

#### `epoll()`
