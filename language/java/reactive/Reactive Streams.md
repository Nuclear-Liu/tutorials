# Reactive Streams


Reactive Streams is an initiative to provide a standard for asynchronous stream processing with non-blocking back pressure. 
This encompasses efforts aimed at runtime environments (JVM and JavaScript) as well as network protocols.

Reactive Streams 是一个提供非阻塞背压异步流处理标准的倡议。
这包括针对运行时环境 (JVM 和 JavaScript) 以及网络协议的工作。


## JDK9 `java.util.concurrent.Flow`


The interfaces available in JDK >= 9 `java.util.concurrent.Flow`, are 1:1 semantically equivalent to their respective Reactive Streams counterparts. 
This means that there will be a migratory period, while libraries move to adopt the new types in the JDK, however this period is expected to be short - due to the full semantic equivalence of the libraries, as well as the Reactive Streams <-> `Flow` adapter library as well as a TCK compatible directly with the JDK `Flow` types.

JDK >= 9 `java.util.concurrent.Flow` 接口在语义上与它们各自的 Reactive Streams 对应物是 1:1 等效的。
这意味着会有一个迁移期，而库会迁移以采用 JDK 中的新类型，但预计这段时间会很短 —— 由于库的完全语义等效，以及 Reactive Streams <-> `Flow` 适配器库以及与 JDK `Flow` 类型直接兼容的 TCK。


Read [this]() if you are interested in learning more about Reactive Streams for the JVM.

如果您有兴趣了解有关 JVM 的反应式流的更多信息，请阅读 [this]()。


## The Problem


Handling streams of data—especially “live” data whose volume is not predetermined—requires special care in an asynchronous system. 
The most prominent issue is that resource consumption needs to be controlled such that a fast data source does not overwhelm the stream destination. 
Asynchrony is needed in order to enable the parallel use of computing resources, on collaborating network hosts or multiple CPU cores within a single machine.

在异步系统中处理数据流——尤其是容量未预先确定的“实时”数据——需要特别小心。
最突出的问题是需要控制资源消耗，以便快速数据源不会压倒流目的地。
为了在协作的网络主机或单个机器内的多个 CPU 内核上实现计算资源的并行使用，需要异步。


The main goal of Reactive Streams is to govern the exchange of stream data across an asynchronous boundary—think passing elements on to another thread or thread-pool—while ensuring that the receiving side is not forced to buffer arbitrary amounts of data. 
In other words, back pressure is an integral part of this model in order to allow the queues which mediate between threads to be bounded. 
The benefits of asynchronous processing would be negated if the communication of back pressure were synchronous (see also the [Reactive Manifesto]()), therefore care has to be taken to mandate fully non-blocking and asynchronous behavior of all aspects of a Reactive Streams implementation.

Reactive Streams 的主要目标是管理跨异步边界的流数据交换——考虑将元素传递到另一个线程或线程池——同时确保接收端不会被迫缓冲任意数量的数据。
换句话说，背压是这个模型不可分割的一部分，以便允许在线程之间调解的队列有界。
如果背压的通信是同步的，那么异步处理的好处将被否定(参见 [Reactive Manifesto]() ) ，因此必须注意要求 Reactive Streams 的所有方面的完全非阻塞和异步行为执行。


It is the intention of this specification to allow the creation of many conforming implementations, which by virtue of abiding by the rules will be able to interoperate smoothly, preserving the aforementioned benefits and characteristics across the whole processing graph of a stream application.

本规范的目的是允许创建许多符合规范的实现，通过遵守规则，这些实现将能够顺利地进行互操作，在流应用程序的整个处理图中保留上述好处和特征。


## Scope


The scope of Reactive Streams is to find a minimal set of interfaces, methods and protocols that will describe the necessary operations and entities to achieve the goal—asynchronous streams of data with non-blocking back pressure.

Reactive Streams 的范围是找到一组最小的接口、方法和协议，描述实现目标所需的操作和实体 —— 具有非阻塞反压力的异步数据流。


End-user DSLs or protocol binding APIs have purposefully been left out of the scope to encourage and enable different implementations that potentially use different programming languages to stay as true as possible to the idioms of their platform.

最终用户 DSL 或协议绑定 API 被有意地排除在这个范围之外，以鼓励和支持可能使用不同编程语言的不同实现尽可能忠实于其平台的习惯用法。


We anticipate that acceptance of this Reactive Streams specification and experience with its implementations will together lead to wide integration, for example including Java platform support in future JDK releases or network protocol support in future web browsers.

我们预计，接受此 Reactive Streams 规范及其实现经验将共同导致广泛的集成，例如包括未来 JDK 版本中的 Java 平台支持或未来 Web 浏览器中的网络协议支持。


### Working Groups

#### Basic Semantics


The basic semantics define how the transmission of stream elements is regulated through back-pressure. 
How elements are transmitted, their representation during transfer, or how back-pressure is signaled is not part of this specification.

基本语义定义了如何通过背压调节流元素的传输。
元件是如何传输的，它们在传输过程中的表现形式，或者背压是如何发出信号的，都不属于本规范的一部分。


#### JVM Interfaces


This working group applies the basic semantics to a set of programming interfaces whose main purpose is to allow the interoperation of different conforming implementations and language bindings for passing streams between objects and threads within the JVM, using the shared memory heap.

这个工作组将基本语义应用于一组编程接口，其主要目的是允许不同的一致性实现和语言绑定之间的互操作，以便使用共享内存堆在 JVM 中的对象和线程之间传递流。


As of May 26th, 2022 we have released version 1.0.4 of Reactive Streams for the JVM, including Java [API](), a textual [Specification](), a [TCK]() and [implementation examples]().

截至2022年5月26日，我们已经为 JVM 发布了 Reactive Streams 的1.0.4版本，包括 Java [API]()、文本[规范]()、 [TCK]() 和[实现示例]()。


Corresponding code artifacts are available on Maven Central:

相应的代码组件可以在 Maven Central 上找到:

```xml
<dependency>
  <groupId>org.reactivestreams</groupId>
  <artifactId>reactive-streams</artifactId>
  <version>1.0.4</version>
</dependency>
<dependency>
  <groupId>org.reactivestreams</groupId>
  <artifactId>reactive-streams-tck</artifactId>
  <version>1.0.4</version>
</dependency>
<dependency>
  <groupId>org.reactivestreams</groupId>
  <artifactId>reactive-streams-tck-flow</artifactId>
  <version>1.0.4</version>
</dependency>
<dependency>
  <groupId>org.reactivestreams</groupId>
  <artifactId>reactive-streams-examples</artifactId>
  <version>1.0.4</version>
</dependency>
```


The source code for these is available on [github](). 
Please use github issues for providing feedback.

这些的源代码可在 [github]() 上找到。
请使用 github issues 提供反馈。


All artifacts and specifications are released under MIT No Attribution (SPDX: MIT-0).

所有的工件和规范都是在 MIT 无署名 (SPDX: MIT-0) 下发布的。


Read more about Reactive Streams 1.0.4 for the JVM [here]().

点击[这里]()阅读更多关于 Reactive Streams 1.0.4 for the JVM 的信息。


##### A Note for Implementors


To get started implementing the final specification, it is recommended to start by reading the [README]() and the [Java API documentation](), then taking a look at the [Specification]() then taking a look at the [TCK]() and the [example implementations](). 
If you have an issue with any of the above, please take a look at [closed issues]() and then open a [new issue]() if it has not already been answered.

为了开始实现最终的规范，建议从阅读 [README]() 和 [Java API 文档]()开始，然后查看[规范]()，然后查看 [TCK]() 和[示例实现]()。
如果你对以上任何一个问题有疑问，请看一下已经[结束的问题]()，如果还没有得到回答，请打开一个[新的问题]()。


This work was performed in the [reactive-streams-jvm]() repository.

这项工作是在 [reactive-streams-jvm]() 仓库中执行的。


#### JavaScript Interfaces


This working group defines a minimal set of object properties for observing a stream of elements within a JavaScript runtime environment. 
The goal is to provide a testable specification that allows different implementations to interoperate within that same runtime environment.

这个工作组定义了一个最小的对象属性集，用于观察 JavaScript 执行期函式库中的元素流。
我们的目标是提供一个可测试的规范，允许不同的实现在同一个执行期函式库内互操作。


This work is performed in the [reactive-streams-js]() repository.

这项工作在 [reactive-streams-js]() 仓库中执行。


#### Network Protocols


This working group defines network protocols for passing reactive streams over various transport media that involve serialization and deserialization of the data elements. 
Examples of such transports are TCP, UDP, HTTP and WebSockets.

该工作组定义了网络协议，用于在各种传输介质上传递反应流，这些传输介质涉及数据元素的序列化和反序列化。
这种传输的例子有 TCP、 UDP、 HTTP 和 WebSocket。


This work is performed in the [reactive-streams-io]() repository.

这项工作是在 [reactive-streams-io]() 仓库中执行的。
