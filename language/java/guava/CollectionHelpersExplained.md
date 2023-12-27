# Collection Helpers 集合辅助工具

## 简介

有时需要编写自己的集合扩展。（例如：在元素添加到列表时添加特殊行为；编写一个实际由数据库查询支持的 `Iterable` ）
Guava 提供了大量的工具使得对集合的扩展变得容易。

## Forwarding Decorators

对于所有各种集合接口， Guava 都提供了 `Forwarding` 抽象类，以简化**装饰器模式**的使用。

`Forwarding` 类定义了一个抽象方法 `delegate()` ，你应该重写该方法以返回修饰对象。
其他每个方法都直接委托给该委托：例如 `ForwardingList.get(int)` 简单的实现为 `delegate().get(int)` 。

通过子类化 `ForwardingXXX` 并实现 `delegate()` 方法，可以覆盖目标类中选定的方法，从而添加装饰功能，而不必自己委托每个方法。

此外，许多方法都有一个 `standerdMethod` 实现，可以用它来恢复预期行为，这与扩展 `AbstractList` 或 JDK 中的其他骨架类具有相同的优势。

> Example:
> 
> 假设我们想修饰一个 `List` ，使其记录所有添加到其中的元素。
> 当然，无论使用哪种方法添加元素： `add(int, E)` `add(E)` `addAll(Collection)`
> 我们都希望记录这些元素，因此必须覆盖所有这些方法。

一般来说，抽象集合骨架提供的大多数方法也会在 `Forwarding` 装饰器中作为 `standard` 实现提供。

提供特殊视图的接口有时会提供这些视图的 `Standard` 实现。
例如： `ForwardingMap` 提供了 `StandardKeySet` `StandardValues` `StandardEntrySet` 其中每个类都尽可能将方法委托给装饰后的映射，否则，它们会将无法委托的方法作为抽象方法保留。

| Interface       | Forwarding Decorator      |
|-----------------|---------------------------|
| `Collection`    | `ForwardingCollection`    |
| `List`          | `ForwardingList`          |
| `Set`           | `ForwardingSet`           |
| `SortedSet`     | `ForwardingSortedSet`     |
| `Map`           | `ForwardingMap`           |
| `SortedMap`     | `ForwardingSortedMap`     |
| `ConcurrentMap` | `ForwardingConcurrentMap` |
| `Map.Entry`     | `ForwardingMapEntry`      |
| `Queue`         | `ForwardingQueue`         |
| `Iterator`      | `ForwardingIterator`      |
| `ListIterator`  | `ForwardingListIterator`  |
| `Multiset`      | `ForwardingMultiset`      |
| `Multimap`      | `ForwardingMultimap`      |
| `ListMultimap`  | `ForwardingListMultimap`  |
| `SetMultimap`   | `ForwardingSetMultimap`   |

## PeekingIterator

有时，普通的 `Iterator` 接口还不够用。

`Iterators` 支持方法 `Iterators.peekingIterator(Iterator)` 它封装了一个 `Iterator` 并返回一个 `PeekingIterator` ，这是 `Iterator` 的一个子类型，支持 `peek()` 下一次调用 `next()` 时返回的元素。

> **注意：**
> 
> `Iterators.peekingIterator` 返回的 `PeekingIterator` 不支持 `peek()` 之后的 `remove()` 调用。

传统的方法是跟踪前一个元素，并在特定条件下后退，但这是一个棘手且容易出错的问题。
`PeekingIterator` 的理解和使用相对简单。

## AbstractIterator

实现自己的 `Iterator` ？
`AbstractIterator` 可以让你的生活更轻松。

> Example: 封装一个迭代器，以便跳过 `null`
> 
> 实现方法 `computeNext()` ，它只计算下一个值。
> 串行完成后，只需返回 `endOfData()` 来标记迭代的结束。

**注意：** `AbstracttIterator` 扩展了 `UnmodifiableIterable` ，它禁止实现 `remove()` 。
如果需要一个支持 `remove()` 的迭代器，不应该从 `AbstractIterator` 扩展。

##### `AbstractSequentialIterator`

一些迭代器更容易用其他方式表达。
`AbstractSequentialIterator` 是提供了另一种表达迭代的方式。

