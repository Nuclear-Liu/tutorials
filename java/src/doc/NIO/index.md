# NIO (java New IO)

特性：
* 为所有原始类型提供 `Buffer` 缓存支持；
* 字符集编解码解决方案；
* `Channel`: 一个新的原始 I/O 抽象；
* 支持锁和内存文件的文件访问接口；
* 支持多路非阻塞式(non-bloking)的高伸缩性网络 I/O；

> 原来的 I/O 以**流的方式处理数据**， NIO 以**块的方式处理数据**；
> 
> * 流处理方式； 效率慢，以字节为处理和消费单位，简单优雅容易理解；
> * 块处理方式： 效率高，以数据块为处理和消费单位，理解难度大；
> 
> JDK 1.4 旧的 IO 模型与 NIO 模型进行了集成， `java.io.*` 以 NIO 为基础重新实现；

## 缓冲区

`java.nio.Buffer` 将关于数据的**数据内容**和**数据信息**包含在一个单一对象中；
`java.nio.Buffer` 类及其子类（七大类基本类型缓冲区： `java.nio.ByteBuffer` `java.nio.CharBuffer` `java.nio.ShortBuffer` `java.nio.IntBuffer` `java.nio.LongBuffer` `java.nio.FloatBuffer` `java.nio.DoubleBuffer`）定义了一个用于处理数据缓冲区的 API；

`java.nio.Buffer` 基本属性：`mark <= position <= limit <= capacity`
* `capacity` 容量： 缓冲区能够容纳数据元素的最大数量；创建时设定，不能被改变；
* `limit` 上限：缓冲区的第一个不能被读或写的元素；或者说，缓冲区中现存元素的计数；
* `position` 位置： 下一个被读或写的元素的索引；位置由缓冲区操作自动更新（`get()` `put()`）；
* `mark` 标记：标记一个 `position` 位置； `mark()` 标记当前 `postion` 值， `reset()` 复位 `postion` 值；默认为**未定义**(undefined `-1`) ；

`java.nio.Buffer` 创建**基本类型**的缓冲区：不能够直接实例化；通过静态工厂方法来创建响应类的新实例；
* 通过 `allocation` 创建；
* 通过 `wrapping` 将一个现有数据类型数组包装到缓存区中来为缓冲区内容分配空间，或者通过创建现有字节缓冲区的视图类创建

缓存类型：
* 堆缓冲区
* 视图缓冲区
* 复制缓冲区
* 直接内存缓冲区

`java.nio.Buffer` 类方法：
* `int capacity()`
* `int position()`
* `Buffer position(int newPosition)`
* `int limit()`
* `Buffer limit(int newLimit)`
* `Buffer mark()`
* `Buffer reset()`
* `Buffer clear()`
* `Buffer flip()`
* `Buffer rewind()`
* `int remaining()`
* `boolean hasRemaining()`
* `boolean isReadOnly()`
* `boolean hasArray()`
* `Object array()`
* `int arrayOffset()`
* `boolean isDirect()`
* `Buffer slice()`
* `Buffer slice(int index, int length)`
* `Buffer duplicate()`

`java.nio.Buffer` 扩展类提供的基本操作：
* `get()` 数据读出缓冲区
* `put()` 数据写入缓冲区

## 通道 Channel

`java.nio.channels.Channel` 提供与 I/O 服务的直接连接；
用于在字节缓冲区和位于通道另一侧的实体（通常是一个文件或套接字）之间有效地传输数据；
通道是一种途径，借助该途径降低访问操作系统 I/O 的开销；
缓冲区则是通道内部用来发送和接收数据的端点；

> 通道 API 由接口指定，提供了可移植性，不同平台上通道实现 (ChannelImplementaion) 会有根本性的差异，通道经常使用操作系统本地代码；

通道是访问 I/O 服务的管道；
I/O 可以分为广义的两大类： `File I/O` 、 `Stream I/O` ；
通道相应的分为两大类： 文件通道和套接字通道；
通道通常分为： `java.nio.channels.FileChannel` `java.nio.channels.SocketChannel` `java.nio.channels.ServerSocketChannel` `java.nio.channels.DatagramChannel` ；

> 文件通道只能通过打开的文件或文件流对象调用 `getChannel()` 方法获取；不支持直接创建 `FileChannel` 对象；

通道从方向上分为： 单向读通道(具体 `channel` 类实现了 `java.nio.channels.ReadableByteChannel`)、单向写同党(具体 `channel` 类实现了 `java.nio.channels.WritableByteChannel`)、双向通道(具体 `channel` 类实现了 `java.nio.channels.ReadableByteChannel` `java.nio.channels.WritableByteChannel`)；

> 通道实例的性能受它所连接的 I/O 服务的特征限制；
> 基于此需要直到通道是如何打开的避免尝试一个底层 I/O 不允许的操作；

通道支持**阻塞**(`blocking`)或**非阻塞**(`nonblocking`)运行，通过 `configureBlocking()` 方法设置： `true` 阻塞模式， `false` 非阻塞模式；

`java.nio.channels.Channel` 提供的操作：
* `isOpen()`: 检查通道是否打开
* `close()`: 关闭一个打开的通道；可能会导致关闭底层 I/O 服务时发生阻塞（与通道阻塞或非阻塞无关）；

> 只有文件 `Channel` 支持 `map()` 做 `pagecache` 映射，通过 `MappedByteBuffer` 操作缓存页内容；
> 
> 基于 `mmap` 系统调用实现；将内核的 `pagecache` 内存**映射**， `dirty` 的处理基于内核；对文件的操作不再需要经过系统调用；

> Java 没有 Direct I/O ，可以通过 JNI 调用基于 C 的 Direct IO 将对 `pagecache` 的操作交由应用控制；

### `java.nio.channels.FileChannel`

`FileChannel` 文件通道为**阻塞式**通道；支持异步读写文件；线程安全；

文件通道访问文件：
* `read()` 读文件
* `write` 写文件

### `java.nio.channels.SocketChannel`

`SocketChannel` Socket 通道可以选择**阻塞**或**非阻塞**方式；
`SocketChannel` 被实例化都会创建一个对等的 `java.net.Socket` 对象；
或者通过 `java.net.Socket` 的 `getChannel()` 方法可以获取 `SocketChannel` 实例；

`Socket` 和 `SocketChannel` 类封装点对点、有序的网络连接；

### `java.nio.channels.ServerSocketChannel`

`ServerSocketChannel` 通道可以选择**阻塞**或**非阻塞**方式；
`ServerSocketChannel` 被实例化都会创建一个对等的 `java.net.ServerSocket` 对象；
或者通过 `java.net.ServerSocket` 的 `getChannel()` 方法获取 `ServerSocketChannel` 实例；

`ServerSocketChannel` 是一个基于通道的 Socket 监听器；
因为 `ServerSocketChannel` 没有 `bind()` 方法，通道不饿能被封装在随意的 Socket 对象外

### `java.nio.channels.DatagramChannel`

`DatagramChannel` 通道可以选择**阻塞**或**非阻塞**方式；
`DatagramChannel` 被实例化都会创建一个对等的 `java.net.DatagramSocket` 对象；
或者通过 `java.net.DatagramSocket` 的 `getChannel()` 方法获取 `DatagramChannel` 实例；


## Socket


## 文件


## 选择器 `Selector`

`java.nio.channels.Selector` 提供查询通道是否已已经准备好执行每个 I/O 操作的能力；
选择器类管理一个被注册的通道集合的信息和它们的就绪状态；
通道是和选择器一起被注册的，并且使用选择器来更新通道的就绪状态；


### 可选择器 `SelectableChannel`

提供通道可选择性所需要的公共方法的抽象；
一个通道可以被注册到多个选择器上，但是每个选择器只能被注册一次；

> `FileChannel` 不可选择（没有继承 `java.nio.channels.SelectableChannel` ）；

### 选择键 `SelectionKey`

封装**通道**与**选择器**的注册关系；
选择对象被 `SelectableChannel.register()` 返回并提供一个表示这种注册关系的标记；
通道在被注册到一个选择器上之前，必须先设置为非阻塞模式；

通过 `SelectableChannel.register()` 方法将通道主责到一个选择器上；
通道一旦被注册就不能回到阻塞状态；

I/O 操作：
* `java.nio.channels.SelectionKey.OP_READ`: `1`
* `java.nio.channels.SelectionKey.OP_WRITE`: `4`
* `java.nio.channels.SelectionKey.OP_CONNECT`: `8`
* `java.nio.channels.SelectionKey.OP_ACCEPT`: `16`

使用非阻塞 I/O 编写服务器处理程序的大致步骤：
1. 向 `Selector` 对象注册感兴趣的事件；
2. 从 `Selector` 中获取感兴趣的事件；
3. 根据不同的事件进行响应的处理；
