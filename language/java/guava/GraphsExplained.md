# Graphs

Guava 的 `common.graph` 是一个用于**图结构**数据（即实体及其之间的关系）建模的库。
其目的是为处理此类数据提供一种通用的、可扩展的语言。

## 定义

图由一组**节点**（也称顶点）和一组**边**（也称链接或弧）组成；每条边将节点相互连接。
与边相关的节点称为边的**端点**。

> 下面我们将介绍一个名为 `graph` 的接口，我们将使用 `graph` (小写 `g`) 作为此类数据结构的统称。
> 当我们要引用本库中的特定类型时，我们会将其大写。

如果一条边有确定的起点(**源**)和终点(**目标**，也称为目的地)，那么这条边就是**有向的**。
否则，它就是**无向**。
**有向边适合建模不对称关系**（**源自**、**链接到**、**由著作**），而**无向边适合建模对称关系**（**与...共同创作了一篇论文**、**之间的距离**、**同伴**）。

如果图的每条边都是有向的，就是有向图，如果图的每条边都是无向的，就是无向图。
（**`common.graph` 不支持既有有向边又有无向边的图**）

**自循环**是一条将节点连接到自身的边；等同于一条端点为同一节点的边。
如果自循环是有向的，那么它既是入射节点的出站边，也是入射节点的入站边，其入射节点既是自循环边的源节点，也是自循环边的目标节点。

如果两条边以相同的顺序（如果有的话）连接相同的节点，那么它们就是**平行**；如果它们以相反的顺序连接相同的节点，那么它们就是**反平行**。
（**无向边不能是反平行的**）

## 能力

`common.graph` 专注于提供接口和类来支持处理图。
不提供 I/O 和可视化支持等功能，并且它的工具类非常有限。

总的来说， `common.graph` 支持以下类型的图：

* 有向图
* 无向图
* 带有相关值(权重、标签等)的节点和/或边
* 支持/不支持自循环的图
* 支持/不支持平行边的图(具有平行边的图形有时称为**多图**)
* 节点/边为插入顺序、排序和无序排序的图

特定 `common.graph` 类型支持的图类型在其 Javadoc 中指定。
每个图类型的内置实现所支持的图类型在其关联的 `Builder` 类型的 Javadoc 中指定。
库中各种类型的特定*实现*（尤其是第三方实现）不需要支持所有这些变体，并且可以另外支持其它变体。

该库对于底层数据结构的选择是不可知的：关系可以存储为矩阵、邻接列表、邻接图等，具体取决于实现者想要优化的用例。

`common.graph` 目前不包括对以下图变体的明确支持，尽管可以使用现有类型对它们进行建模：

* 树、森林
* 具有不同类型的同类元素(节点或边)的图(例如：二分/k图、多模式图)
* 超图

> **`common.graph` 不允许图同时具有有向边和无向边。**

`Graphs` 类提供了一些实用工具（例如，复制图、比较图）

## 图类型

有三个顶级图接口，它们通过**边的表示**来区分： **`Graph`** 、 **`ValueGraph`** 和 **`Network`** 。

每个顶级接口都继承了 `SuccessorsFunction` 和 `PredecessorsFunction` 。
这些接口旨在用作**图算法（如广度优先遍历）的参数类型**，这些算法只需要一种访问图中节点的**后继**/**前驱**节点的方法。
如果图的所有者已经有了一种适合自己的表示方法，而且并不特别希望为了运行一种图算法而将其表示方法序列化为一种 `common.graph` 类型，那么这种方法就特别有用（即继承相关接口扩展即可）。

##### `Graph`

`Graph` 是最简单、最基本的图类型。
它定义了处理节点间关系的底层操作符，如： `successors(node)` `adjacendtNodes(node)` `inDegree(node)` 。
节点是 `Graph` 的一等的唯一对象；可以将它们视为类似 `Map` 中的键。

`Graph` 的边是完全匿名的；它们只根据**端点**来定义。

用例示例 `Graph<Airport>` 表示连接了可以直飞的机场。

##### `ValueGraph`

`ValueGraph` 具有 `Graph` 相同的节点相关方法，添加了几个**检索指定边缘值**的方法。

`ValueGraph` 的每个边都有一个关联的用户指定值。
这些值不必是唯一的（因为**节点是唯一的**； `ValueGraph` 和 `Graph` 之间的关系类似于 `Map` 和 `Set` 之间的关系； `Graph` 的**边**是**端点对**的集合，而 `ValueGraph` 的**边**是从**端点到值**的映射）

`ValueGraph` 提供了一个 `asGraph()` 方法，用于返回 `ValueGraph` 的 `Graph` 视图。
这样对于 `Graph` 实例进行操作的方法也可以用于 `ValueGraph` 实例。

用例示例： `ValueGraph<Airport, Integer>` 边的值表示在边连接的两个 `Airport` 之间往返的时间。

##### `Network`

`Network` 具有与 `Graph` 相同的所有节点相关方法，增加了处理边和节点到边关系的方法，例如： `outEdges(node)` `incidentNodes(edge)` `edgesConnecting(nodeU, nodeV)` 。

`Network` 的**边**是头等（唯一）对象，就像所有图类型中的节点一样。
边的唯一性约束允许 `Network` 原生的支持**平行边**，以及与边和节点到边关系相关的方法。

用例示例： `Network<Airport, Flight>` 其中边表示从一个机场到另一个机场的具体航班。

> **正确选择图类型**
> 
> 三种图类型的本质区别在于它们对**边**的表示。
> 
> * `Graph`: 边是节点之间的**匿名连接**，没有自己的身份或属性。如果每对节点之间最**多只有一条边相连**，而且不**需要任何信息与边关联起来**，那么就应该使用 `Graph` 。
> * `ValueGraph`: 具有**值**（如边权重或标签）的边，这些值**可能是唯一**的，也**可能不是唯一**的。如果每对节点**最多由一条边**连接，并且需要为不同的**边关联可能相同的信息**（例如边权重），则应该使用 `ValueGraph` 。
> * `Network`: 边和节点一样，都是第一类唯一对象。如果**边**对象是唯一的，并且希望能够发布引用它们的查询，应该使用 `Network` （注意：这种唯一性允许 `Network` 支持并行边）。

##### 构建图实例

根据设计， `common.graph` 提供的实现类不是公共的。
这减少了用户需要了解的公共类数量，并且可以更轻松地浏览内置实现提供的各种功能，而不会让只想创建图的用户不知所措。

要创建一个图类型内置实现的实例，请使用相应的 `Builder` 类： `GraphBuilder` `ValueGraphBuilder` `NetworkBuilder` 。

* 可以通过两种方式之一获得图 `Builder` 的实例：
    * 静态方法： `directed()` - 有向 `undirected()` - 无向
    * 静态方法： `from()` 根据现有图实例创建一个 `Builder`
* 创建了 `Builder` 实例后，可以选择性地指定其他特性和功能
* 构建**可变图**
  	* 可以在同一个 `Builder` 实例上多次调用 `build()` 以构建具有相同配置的多个图实例。
    * 无需在 `Builder` 中指定元素类型；在图类型本身中指定即可。
    * `build()` 方法返回关联图类型的**可变(`Mutable`)子类型**，它提供了突变方法；更多内容参见： `Mutable` and `Immutable` graphs
* 构建**不可变图**
    * 可以在同一个 `Builder` 实例上多次调用 `immutable()` 创建具有多个相同配置的 `ImmutableGraph.Builder` 实例
    * 需要在 `immutable()` 中指定元素类型

> **`Builder` 约束与优化提示**
> 
> `Builder` 类型通常提供两种类型的选项： 约束和优化提示。
> 
> **约束**指定由给定 `Builder` 实例创建的图必须满足的行为和属性。例如：
> 
> * 图是否有向
> * 图是否允许环路
> * 图的边是否有序
> 
> 实现类可以选择使用**优化提示**来提高效率。例如，确定内部数据结构的类型或初始大小。
> 
> 每种图类型都提供与其 `Builder` 指定的约束相对应的访问器，但不提供优化提示的访问器。

## `Mutable` 与 `Immutable` 图

##### `Mutable*` 类型

每个图类型都有一个对应的 `Multiable*` 子类型： `MutableGraph` `MutableValueGraph` `MutableNetwork` 。
这些子类型定义了变种方法：

* 添加和删除节点的方法：
    * `addNode(node)`
    * `removeNode(node)`
* 添加和删除边的方法：
    * `MultiableGraph`
        * `putEdge(nodeU, nodeV)`
        * `removeEdge(nodeU, nodeV)`
    * `MultiableValueGraph`
        * `putEdge(nodeU, nodeV, value`
        * `removeEdge(nodeU, nodeV)`
    * `MultiableNetwork`
        * `addEdge(nodeU, nodeV, edge)`
        * `removeEdge(edge)`

这与 Java 集合框架（以及 Guava 的新集合类型）历来的工作方式不同；这些类型中的每一种都包含（可选的）突变方法的签名。
我们选择将可变方法分成不同的子类型，部分原因是为了鼓励防御性编程：一般来说，如果您的代码只检查或遍历图形而不对其进行变换，那么其输入应指定为 `Graph`、 `ValueGraph` 或 `Network` ，而不是它们的可变子类型。
另一方面，如果您的代码确实需要变异对象，那么使用标有 `Mutable` 的类型会有助于您的代码引起对这一事实的注意。

由于 `Graph` 等是接口，即使它们不包含突变方法，向调用者提供该接口的实例也不能保证它不会被调用者突变，因为（如果它实际上是一个 `Mutable` 子类型）调用者可以将它转换为该子类型。
如果你想以契约的形式保证作为方法参数或返回值的图不被修改，应该使用 `Immutable` 实现；下文将对此进行详细介绍。

##### `Immutable*` 实现

每种图类型具有相应的 `Immutable` 实现。
这些类类似与 Guava 的 `ImmutableSet` `ImmutableList` `ImmutableMap` 等：一旦构建完成，它们就不能被修改，并且它们在内部使用高效的不可变数据结构。

与 Guava 其他 `Immutable` 类型不同的是，这些实现没有任何突变方法的方法签名，因此它们不需要为尝试的突变抛出 `UnsupportedOperationException` 。

可以通过哟以下两种方式之一创建 `ImmutableGraph` 实例：

* 使用 `GraphBuilder`

    `ImmutableGraph<Country> immutableGraph = GraphBuilder.undirected().<Country>immutable().putEdge(FRANCE, GERMANY).build()`

* 使用 `ImmutableGraph.copyOf()`

    `ImmutableGraph<Integer> immutableGraph = ImmutableGraph.copyOf(otherGraph);`

不可变图总是保证提供稳定的事件边缘顺序。
如果使用 `GraphBuilder` 填充图，那么附带边的顺序将尽可能是插入顺序。
如果使用 `copyOf()` 则附带边顺序是它们在复制过程中被访问的顺序。


