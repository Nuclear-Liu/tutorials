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

##### 保证

每个 `Immutable` 类型都提供一下保证：

* **浅层不变性**：元素永远不能添加、删除和替换（这些类不实现 `Mutable` 接口）
* **确定性迭代**：迭代顺序始终与输入图的迭代顺序相关
* **线程安全**：从多个线程同时访问此图是安全的
* **完整性**：此类不能在此包之外进行子类化

> _将这些类视为**接口**，而不是实现：_
>
> 每个 `Immutable` 类都是一种提供有意义的行为保证的类型——而不仅仅是特定的实现。
> 应该将它们视为每个重要意义上的接口。
> 
> 存储 `Immutable*` 实例（例如 `ImmutableGraph` ）的字段和方法返回值应声明为 `Immutable` 类型，而不是相应的接口类型（例如 `Graph` ）。
> 这向调用者传达了上面列出的所有语义保证，这几乎总是非常有用的信息。
> 
> **警告**：如果其它地方所述，当元素包含在集合中时，修改元素几乎总是一个坏主意。
> 这将导致未定义的行为和错误。
> 最好避免将可变对象用作 `Immutable` 实例的元素，因为用户可能希望你的 `Immutable` 不可变对象是深度不可变的。

## 图元素（节点和边）

#### 元素必须可用做 `Map` 的键

用户提供的图元素应被视为图实现所维护的内部数据结构的**键**。
因此，用于表示图元素的类必须具有 `equals()` 和 `hashCode()` 实现，这些实现具有或诱导下列属性。

##### 唯一性

如果 `A` 和 `B` 满足 `A.equals(B) == true` ，那么这两个元素种最多有一个可能是图的元素。

##### `hashCode()` 和 `equals()` 之间的一致性

根据 `Object.hashCode()` 的定义， `hashCode()` 必须与 `equals()` 一致。

##### 与 `equals()` 的排序一致性

如果对节点进行排序（例如通过 `GraphBuilder.orderNodes()` ），则排序必须与 `Comparator` 和 `Comparable` 一致。

##### 非递归性

`hashCode` 和 `equals` 不能递归引用其他元素。
例如：

```java
import java.util.Objects;

// DON'T use a class like this as a graph element (or Map key/Set element)
public final class Node<T> {
    T value;
    Set<Node<T>> successors;

    @Override
    public boolean equals(Object o) {
        Node<T> other = (Node<T>) o;
        return Objects.equals(value, other.value) && Objects.equals(successors, other.successors);
    }
    @Override
    public int hashCode() {
        return Objects.hash(value, successors);
    }
}
```

使用这样的类作为 `common.graph` 元素类型存在一下问题：

* **冗余**：由 `common.graph` 库提供的 `Graph`实现已经存储了这些关系
* **效率低下**：添加/访问此类元素调用 `equals` （可能还有 `hashCode` ），这需要 `O(n)` 时间
* **不可行性**：如果图中存在循环， `equals()` 和 `hashCode()` 可能永远不会终止

#### 元素与可变状态

如果元素具有可变状态：
* 可变状态不能反映在 `equals()`/`hashCode()` 方法中（在 `Map` 文档中有详细讨论）
* 不要构造多个彼此相等的元素，并期望它们可以互换。尤其是在向图中添加此类元素时，如果在创建过程中需要多次引用这些元素，则应一次性创建并存储引用（而不是每次调用 `add` 时都传递 `new MyMutableNode(id)` ）

如果需要存储每个元素的可变状态，一种方法是使用不可变元素，并将可变状态存储在单独的数据结构种（例如元素到状态的映射）。

#### 元素必须为非空

根据契约规定，向图中添加元素的方法必须拒绝空元素。

## 库的契约与行为

本节讨论 `common.graph` 类型的内置实现的行为。

#### 变化

可以添加一条边，这条边的附带节点之前尚未添加到图中。
如果它们还未出现，则会被静默添加到图中：

```jshelllanguage
Graph<Integer> graph = GraphBuilder.directed().build(); // graph is empty
graph.putEdge(1, 2); // this adds 1 and 2 as nodes of this graph, and puts an edge between them

if (graph.nodes().contains(1)) { // evaluates to "true"
    // ...
}
```

#### 图的 `equals()` 方法与图的等价

从 Guava 22 开始， `common.graph` 的图类型都以特定类型有意义的方式定义 `equals()` ：

* `Graph.equals()` 如果两个 `Graph` 具有相同的节点和边集（即每个边在两个图中具有相同的端点和相同的方向），则定义它们相等。
* `ValueGraph.equals()` 如果两个 `ValueGraph` 具有相同的节点和边集，且相等的边具有相等的值，则定义这两个 `ValueGraph` 相等。
* `Network.equals()` 如果两个 `Network` 具有相同的节点和边，且每个边对象在相同方向上连接相同的节点，则定义这两个 `Network` 相等。

此外，对于每种图类型，只有当两个图的边具有相同的有向性（两个图形都是有向的或都是无向的）时，两个图才可以相等。

当然，对于每种图类型， `hashCode()` 与 `equals()` 的定义是一致的。

如果只想根据**连通性**来比较两个 `Network` 或两个 `ValueGraph` ，或比较一个 `Network` 或一个 `ValueGraph` 与一个 `Graph` ，可以使用 `Network` 和 `ValueGraph` 提供的 `Graph` 视图：

```jshelllanguage
Graph<Integer> graph1, graph2;
ValueGraph<Integer, Double> valueGraph1, valueGraph2;
Network<Integer, MyEdge> network1, network2;

// compare based on nodes and node relationships only
if (graph1.equals(graph2)) {
    // ...
}
if (valueGraph1.asGraph().equals(valueGraph2.asGraph())) {
    // ...
}
if (network.asGraph().equals(graph1.asGraph())) {
    // ...
}

// compare base on nodes, node relationships, and edge values
if (valueGraph1.equals(valueGrahp2)) {
    // ...
}

// compare base on nodes, node relationships, and edge identities
if (network1.equals(network2)) {
    // ...
}
```

#### 访问器方法

返回集合的访问器：

* 可能返回图的视图；不支持对图进行影响视图的修改，并可能导致抛出 `ConcurrentModificationException` 。
* 如果输入有效，但没有元素满足请求，则**将返回空集合**。

如果传递了一个不在图中的元素，访问器将抛出 `IllegalArgumentException` 。

虽然一些 Java 集合框架方法（如 `contains` ）采用 `Object` 参数而不是适当的泛型类型说明符，但从 Guava 22 开始， `common.graph` 方法采用泛型类型说明符来提高类型安全性。

#### 同步

由每个图具体实现来确定自己的同步策略。
默认情况下，未定义的行为可能是由于调用由另一个线程改变的图上的任何方法而导致的。

一般来说，内置的可变实现不提供同步保证，但 `Immutable` 类（由于不可变）是线程安全的。

#### 元素对象

添加到图中的节点、边和值对象与内置实现无关；它们只是用作内部数据结构的**键**。
这意味着节点/边可以在图实例之间共享。

默认情况下，节点和边对象是按插入顺序排列的（即，由 `node()` 和 `edges()` 的 `Iterator` 按照它们添加到图中的顺序进行访问，就像 `LinkdedHashSet` ）。

## 执行器注意事项

#### 存储模型

`common.graph` 支持多种机制来存储图的拓扑结构，包括

* 图实现存储拓扑结构（例如，通过存储将节点映射到其相邻节点的 `Map<N,Set<N>>`）；这意味着节点只是键，可以在图之间共享。
* 节点存储拓扑结构（例如，通过存储相邻节点的 `List<E>`）；这（通常）意味着是特殊图的节点。
* 单独的数据存储库（例如数据库）存储拓扑结构

**注意**： `Multimap` 对于支持**隔离节点**的 `Graph` 实现来说不是足够的内部数据结构，因为它们限制**键**要么映射到至少一个值，要么不存在于 `Multimap` 中。

#### 访问行为

对于返回集合的访问器，有多种语义选项，包括：

1. 集合是一个不可变的副本（如 `ImmutableSet` ）：以任何方式视图修改集合都会抛出异常，而且对图的修改**不会**放映在集合中。
2. 集合石不可修改的视图（如 `Collections.unmodifiableSet()`）：尝试以任何方式修改集合都将抛出异常，对图像的修改将反映在集合中。
3. 集合是一个可变副本：它可以被修改，但是对集合的修改将**不会**反映在图中，反之亦然。
4. 集合是一个可修改的视图：它是可以修改的，对集合的修改会反映在图中，反之亦然。

> 理论上，可以返回一个在一个方向上传递写入的集合，但不能在另一个方向上传递，但这基本上永远不会有用或清晰，所以请不要这样做。

（1）和（2）通常是优先的；内置通常使用（2）

#### `Abstract*` 类

每种图类型都有一个相应的 `Abstract` 类。

如果可能的话，图接口的实现者应该扩展适当的抽象类，而不是直接实现接口。
抽象类提供了几个关键方法的实现，这些方法的正确实现可能比较棘手，或者说拥有一致的实现会很有帮助，例如：

* `*degree()`
* `toString()`
* `Graph.edges()`
* `Network.asGraph()`

## 示例代码

#### `node` 是否在图中

`graph.nodes().contains(node)`

#### 图中节点 `u` 和 `v` 之间是否存在边

在无向图的情况下，一下实例中参数 `u` 和 `v` 的顺序无关紧要。

```jshelllanguage
// This is the preferred syntax since 23.0 for all graph types. 23.0 版本后的首选方法。
graphs.hasEdgeConnecting(u, v);

// These are equivalent (to each other and to the above expression). 它们是等效的（彼此之间以及与上面的表达式）。
graphs.successors(u).contains(v);
graphs.predecessors(v).contains(u);

// This is equivalent to the expressions above if the graph is undirected. 如果图形是无向的，这等效于上面的表达式。
graphs.adjacentNodes(u).contains(v);

// This works only for Networks. 这只适用于网络。
!network.edgesConnecting(u, v).isEmpty();

// This works only if "network" has at most a single edge connecting u to v. 仅当 "network" 最多有一个边将您连接到 v 时，这才有效。
network.edgeConnecting(u, v).isPresent(); // Java 8 only
network.edgeConnectingOrNull(u, v) != null;

// These work only for ValueGraphs. 仅适用于 ValueGraph
valueGraph.edgeValue(u, v).isPresent(); // Java 8 only
valueGraph.edgeValueOrDefalut(u, v, null) != null;
```

#### 基本 `Graph` 案例

```jshelllanguage
ImmutableGraph<Integer> graph = GraphBuilder
        .directed()
        .<Integer>immutable()
        .addNode(1)
        .putEdge(2, 3) // also adds nodes 2 and 3 if not already present
        .putEdge(2, 3) // no effect; Graph does not support parallel edges
        .build();
Set<Integer> successorOfTwo = graph.successors(2); // returns {3}
```

#### 基本 `ValueGraph` 案例

```jshelllanguage
MutableValueGraph<Integer, Double> weightedGraph = ValueGraphBuilder.directed().build();
weightedGraph.addNode(1);
weightedGraph.putEdgeValue(2, 3, 1.5);  // also adds nodes 2 and 3 if not already present
weightedGraph.putEdgeValue(3, 5, 1.5);  // edge values (like Map values) need not be unique
// ...
weightedGraph.putEdgeValue(2, 3, 2.0);  // updates the value for (2,3) to 2.0
```

#### 基本 `Network` 案例

```jshelllanguage
MutableNetwork<Integer, String> network = NetworkBuilder.directed().build();
network.addNode(1);
network.addEdge("2->3", 2, 3);  // also adds nodes 2 and 3 if not already present

Set<Integer> successorsOfTwo = network.successors(2);  // returns {3}
Set<String> outEdgesOfTwo = network.outEdges(2);   // returns {"2->3"}

network.addEdge("2->3 too", 2, 3);  // throws; Network disallows parallel edges
                                    // by default
network.addEdge("2->3", 2, 3);  // no effect; this edge is already present
                                // and connecting these nodes in this order

Set<String> inEdgesOfFour = network.inEdges(4); // throws; node not in graph
```

#### 逐节点遍历无向图

```jshelllanguage
// Return all nodes reachable by traversing 2 edges starting from "node" (ignoring edge direction and edge weights, if any, and not including  "node").

import java.util.HashSet;Set<N> getTwoHopNeighbors(Graph<N> graph, N node) {
    Set<N> twoHopNeighbors = new HashSet<>();
    for (N neighbor : graph.adjacentNodes(node)) {
        twoHopNeighbors.addAll(graph.adjacentNodes(neighbor));
    }
    twoHopNeighbors.remove(node);
    return twoHopNeighbors;
}
```

#### 沿边遍历有向图

```jshelllanguage
// Update the shortest-path weighted distances of the successors to "node" in a directed Network (inner loop of Dijkstra's algorithm) given a known distance for {@code node} stored in a {@code Map<N, Double>}, and a {@code Function<E, Double>} for retrieving a weight for an edge.
void updateDistancesFrom(Network<N, E> network, N node) {
  double nodeDistance = distances.get(node);
  for (E outEdge : network.outEdges(node)) {
    N target = network.target(outEdge);
    double targetDistance = nodeDistance + edgeWeights.apply(outEdge);
    if (targetDistance < distances.getOrDefault(target, Double.MAX_VALUE)) {
      distances.put(target, targetDistance);
    }
  }
}
```
