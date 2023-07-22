# Guava

Maven 依赖：
```xml
<dependency>
    <groupId>com.google.guava</groupId>
    <artifactId>guava</artifactId>
    <version>32.1.1-jre</version> <!-- or 32.1.1-android for the Android flavor -->
</dependency>
```

## 概述

Guava 是 Google 核心 Java 库的开源版本：Google 每天都在代码中使用的核心工具。
Guava 工具已经被精心设计、测试、优化并在 Google 的生产中使用。

Guava 是整个 Java 项目的生产力倍增器：目标是让使用 Java 语言的工作更愉快、更有成效。

> **Guava 的几个一般设计原则：**
> 
> * 应该总是有一些用例，对于这些用例， API 显然是最好的解决方案。如果不确定这是不是最好的 API ，那就考虑一下，直到确定。
> * 方法和类的语义应该从它们的签名中显而易见和直观，而不是**聪明**。在特殊情况下，内部实现可以做聪明的事情，但这些特殊情况（方法和类的语义与签名）应该是相同的。
> * 鼓励 Guava 用户养成良好的代码习惯，并在 Guava 源码中示范良好的代码习惯。（包括：快速失败、拒绝 null 等）
> * 不要试图单独处理每个用例——提供可以组合的通用工具来处理没有预想到的用例。
> * 强调维护性，并为将来重构留出空间。（推论：根据 Effective Java 的 17 项，大多数公开的类应该是最终的；公开的**骨架** `AbstractXXX` 类应该非常保守。）

Guava 项目包含了 Google 的几个核心库：  

##### 1. 基本实用工具 `Basic utilities`

**让使用 Java 语言更加友好。**

* [使用和避免 `null`](./UsingAndAvoidingNullExplained.md) : `null` 可能模棱两可，可能导致令人困惑的错误，有时只是令人讨厌
* [预处理](./PreconditionsExplained.md) : 更容易的预处理测试你的方法
* [公共对象方法](./CommonObjectUtilitiesExplained.md) : 简化实现对象方法（如： `hashCode()` `toString()` ）
* [排序](./OrderingExplained.md) : Guava 强大**流畅的 `Comparator`**类
* [可抛出的](./ThrowablesExplained.md) : 简化异常和错误的传播和检查

##### 2. 集合 `Collections`

Guava 对 JDK 集合生态系统的扩展。
这些是 Guava **最成熟和最受欢迎**的部分。

* [不可变集合](./ImmutableCollectionsExplained.md) : 用于防御性编程、常量集合同时提高效率
* [新集合类型](./NewCollectionTypesExplained.md) : 对 JDK 集合补充：多集合(multisets)、多映射(multimaps)、表(tables)、双向映射(bidirectional maps)等
* [强大的集合工具](./CollectionUtilitiesExplained.md) : 对 `java.util.Collections` 中没有提供的公用方法
* [扩展工具](./CollectionHelpersExplained.md) : 更容易：`Collection` 装饰器、实现 `Iterator`

##### 3. [图 `Graphs`](./GraphsExplained.md)

用于建模图结构数据的库，即实体和实体间关系。
主要功能：

* Graph:
* ValueGraph:
* Network:
* 支持可变和不可变、有向和无向图，以及其他一些属性

##### 4. [缓存 `Caches`](./CachesExplained.md)

正确的使用本地缓存，支持多种过期行为。

##### 5. 函数式风格 `Functional idioms`

##### 6. 并发 `Concurrency`

##### 7. 字符串 `Strings`

##### 8. 原语 `Primitivers`

##### 9. 范围 `Ranges`

##### 10. `I/O`

##### 11. 哈希 `Hashing`

##### 12. 事件总线 `EventBus`

##### 13. 数学 `Math`

##### 14. 反射 `Reflection`

## 使用 `ProGuard` 避免将 Guava 中不使用的部分与 JAR 捆绑
