# 新集合类型 New Collection Types

Guava 扩展许多非常有用的集合类型，遵循 JDK 接口协定。

## `Multiset`

> **例如：计算一个单词在文档中出现的次数**
> 
> 传统 Java 方式：
> ```jshelllanguage
> Map<String, Integer> counts = new HashMap<>();
> for (String word : words) {
> 	Integer count = counts.get(word);
> 	if (count == null) {
> 		counts.put(word, 1);
> 	} else {
> 		counts.put(word, count + 1);
> 	}
> }
> ```
> 
> 不支持收集各种有用的统计信息（例如：单词总数）。

Guava 提供的 `Multiset` 支持添加多个元素（元素可重复），并且顺序无关。

`Multiset` 两种思考方式：
* 类似于没有顺序约束的 `ArrayList` : 排序并不重要（无序）
* 类似于 `Map<E, Integer>` 带有元素和计数

> **`Multiset` 的内存消耗与不同元素的数量成线性关系**

`Multiset` API 结合了 `Multiset` 的两种思考方式：
* 当作为普通的 `Collection` 时，行为类似于 `ArrayList` ：
  * `add()`: 添加元素
  * `iterator()`: 遍历每个元素
  * `size()`: 所有元素的数量
* 当作 `Map<E, Integer>` ：
  * `count(E)`: 返回元素计数
    * `HashMultiset`: 开销是 `O(1)`
    * `TreeMultiset`: 开销是 `O(log n)`
  * `entrySet()` 返回一个 `Set<Multiset.Entry<E>>` 类似于 `Map.entrySet()`
  * `elementSet()` 返回多集的不同元素 `Set<E>` 类似于 `Map.keySet()`

`Multiset` 与 `Collection` 接口的契约完全一致。

| 方法                 | 描述                                                                                 |
|--------------------|------------------------------------------------------------------------------------|
| `count(E)`         | 计算已添加到 `Multiset` 中的元素的出现次数                                                        |
| `elementSet()`     | 将 `Multiset<E>` 的不同元素视为 `Set<E>`                                                   |
| `entrySet()`       | 类似于 `Map.entrySet()` 返回一个 `Set<Multset.Entry<E>>` 包括支持 `getElement()` `getCount()` |
| `add(E, int)`      | 增加指定元素出现的次数                                                                        |
| `remove(E, int)`   | 删除指定元素的指定出现次数                                                                      |
| `setCount(E, int)` | 将指定元素的出现次数设置为非负值                                                                   |
| `size()`           | 返回 `Multiset` 中所有元素出现的总次数                                                          |

> **`Multiset<E>` 不是 `Map<E, Integer>`:**
> 
> * `Multiset<E>` 只有正计数的元素；没有元素的已有负计数，计数为 `0` 被认为不在 `Multiset` 中，不会出现在 `elementSet()` 或 `entrySet()` 视图中
> * `Multiset.size()` 返回集合的大小，等于所有元素的计数之和；对于不同元素的数量，请使用 `elementSet().size()`
> * `Multiset.iterator()` 对每个元素的每次出现进行迭代，所以迭代的长度等于 `Multiset.size()`
> * `Multiset<E>` 支持添加元素、删除元素或直接设置元素的计数； `setCount(E,0)` 等同于删除该元素
> * 对于不在 `Multiset` 中的元素 `Multiset.count(E)` 总是返回 `0`

##### 实现

Guava 提供许多 `Multiset` 的实现，与 JDK 的 `Map` 实现对应：

| `Multiset` Implementations | 支持 `null` 元素 | `Map` Implementations |
|----------------------------|--------------|-----------------------|
| `HashMultiset`             | Y            | `HashMap`             |
| `TreeMultiset`             | Y            | `TreeMap`             |
| `LinkedHashMultiset`       | Y            | `LinkedHashMap`       |
| `ConcurrentHashMultiset`   | N            | `ConcurrentHashMap`   |
| `ImmutableMultiset`        | N            | `ImmutableMap`        |

##### `SortedMultiset`

`SortedMultiset` 是 `Multiset` 接口的一个变体，支持在指定范围内有效地获取子 `Multiset` 。
（例如：使用 `latencies.subMultiset(0, BoundType.CLOSED, 100, BoundType.OPEN).size()` 来确定由多少次点击网站延迟低于 100 ms ）

`TreeMultiset` 实现了 `SortedMultiset` 接口。

`ImmutableSortedMultiset` 只读实现。

## `Multimap`

> 场景：
> 
> 使用 `Map<K, List<V>>` / `Map<K, Set<V>>` ，例如使用 `Map<K, Set<V>>` 表示未标记有向图的典型方法。

Guava `Multimap` 将键与任意多的值相关联的数据结构。

在概念上，由两种角度来理解 `Multimap` ：
* 作为从单键到单值的映射集合: 
    ```text
    a -> 1
    a -> 2
    a -> 4
    b -> 3
    c -> 5
    ```
* 作为从唯一键到值集合的映射
    ```text
    a -> [1, 2, 4]
    b -> [3]
    c -> [5]
    ```

通常， `Multimap` 接口最好使用第一种角度；但也允许你使用 `asMap()` 视图来使用，返回一个 `Map<`K, Collection<V>>` 。
**在 `Multimap` 不存在映射到空集合的键：键要么映射到一个值，要么它根本不存在于 `Multimap` 中。**

一般很少直接使用 `Multimap` 接口；更多使用 `ListMultimap` （将**键**映射到 `List` ）或 `SetMultimap` （将**键**映射到 `Set` ）。

##### 构造

构造 `Multimap` 最及直接的方法是使用 `MultimapBuilder` ，允许配置键和值的表示方式。

```jshelllanguage
// creates a ListMultimap with tree keys and array list values
ListMultimap<String, Integer> treeListMultimap = 
    MultimapBuilder.treeKeys().arrayListValues().build();
// create a SetMultimap with hash keys and enum set values
SetMultimap<Integer, MyEnum> hashEnumMultimap =
    MultimapBuilder.hashKeys().enumSetValues(MyEnum.class).build();
```

也可以选择直接使用实现类上使用 `create()` 方法。
但是推荐使用 `MultimapBuilder` 构建。

##### 修改

`Multimap.get(key)` 返回与指定键（即使当前没有）关联的值的**视图**。
* 对于 `ListMultimap.get(key)` 返回一个 `List`
* 对于 `SetMultimap.get(key)` 返回一个 `Set`

**修改将写入底层的 `Multimap` 。**

修改相关 API ：

| 签名                              | 描述                                                                  | 等效                                                                    |
|---------------------------------|---------------------------------------------------------------------|-----------------------------------------------------------------------|
| `put(K, V)`                     | 将**键**和**值**之间添加关联                                                  | `Multimap.get(K).add(V)`                                              |
| `putAll(K, Iterable<V>)`        | 依此添加**键**到每个**值**的关联                                                | `Iterables.addAll(Multimap.get(K), values)`                           |
| `remove(K, V)`                  | 删除从**键**到**值**的一个关联；如果多重映射发生更改，则返回 `true`                           | `Multimap.get(K).remove(V)`                                           |
| `removeAll(K)`                  | 删除并返回与指定**键**关联的所有值；返回的集合可能是可修改的，也可嫩不可修改，但修改不会影响多重映射（**返回相应的集合类型**） | `Multimap.get(K).clear()`                                             |
| `replaceValues(K, Iterable<V>)` | 清理与**键**关联的**所有值**，并将**键**设置为与每个**值**相关联；返回以前与**键关联的值**             | `Multimap.get(K).clear()` `Iterables.addAll(Multimap.get(K), values)` |

##### 视图

`Multimap` 支持多种强大的视图。

* `asMap`: 将 `Multimap<K, V>` 视为 `Map<K, Collection<V>>`

    返回的映射支持 `remove` ，并且对返回集合的更改会写入；映射**不支持 `put` `putAll`** 。
    当想在不存在的键上使用 `null` 而不是一个新的，可写的空集合是，可以使用 `asMap().get(key)`
* `entries`: 将 `Multimap<K, V>` 视为 `Collection<Map.Entry<K, V>>` （对于 `SetMultimap` 是 `Set<Map.Entry<K, V>>`）
* `keySet`: 将 `Multimap<K, V>` 的不同键视为 `Set<K>`
* `keys`: 将 `Multimap` 的**键**看作一个 `Multiset` 其多重性等于与该键相关的值的数量。支持从 `Multiset` 中**删除和更改**，**不支持添加**。
* `values`: 将 `Multimap` 中的所有**值**视为一个扁平化的 `Collection<V>` ，所有的值都是一个集合。（类似于 `Iterables.concat(multimap.asMap().values())` 但返回的是一个完整的 `Collection` ）

> **`Multimap` is not a `Map` :**
> 
> `Multimap<K, V>` 不是 `Map<K, Collection<V>>` 区别包括：
> * `Multimap.get(key)` 总是返回一个非 `null` 可能为空的集合。这并不意味着 `Multimap` 会花费与**键**关联的任何内存，相反，返回的集合是一个视图，允许根据需要添加与**键**关联的内容。
> * 如果更喜欢类似 `Map` 的行为，对于不在 `Multimap` 中的键返回 `null` ，使用 `asMap()` 视图获取 `Map<K, Collection<V>>` 。
> * 当且仅当存在与指定**键**关联的元素时 `Multimap.containsKey(key)` 为 `true` 。
> * `Multimap.entries()` 返回 `Multimap` 中所有**键**的所有条目。如果想要所有**键**的集合条目，请使用 `asMap().entrySet()`
> * `Multimap.size()` 返回的是整个 `Multimap` 中的条目数，而不是不同键的个数。使用 `Multimap.keySet().size()` 获取不同键的个数。

##### 实现

**推荐使用 `MultimapBuilder`** 创建实例。

| Implementation          | Keys behave like... | Values behave like... |
|-------------------------|---------------------|-----------------------|
| `ArrayListMultimap`     | `HashMap`           | `ArrayList`           |
| `HashMultimap`          | `HashMap`           | `HashSet`             |
| `LinkedListMultimap`    | `LinkedHashMap`     | `LinkedList`          |
| `LinkedHashMultimap`    | `LinkedHashMap`     | `LinkedHashSet`       |
| `TreeMultimap`          | `TreeMap`           | `TreeSet`             |
| `ImmutableListMultimap` | `ImmutableMap`      | `ImmutableList`       |
| `ImmutableSetMultimap`  | `ImmutableMap`      | `ImmutableSet`        |

## `BitMap`

将**值**映射回**键**的传统方法是维护两个单独的 `Map` ，并保持它们的同步，但这种方法容易出错，而且当一个值已经存在于映射中时，会变得非常混乱。

`BiMap<K, V>` 是一个 `Map<K, V>` 同时：
* 使用 `inverse()` 查看逆 `BiMap<V, K>`
* 确保值是唯一的。

如果将一个已存在的**键**映射到一个已存在的**值**，将抛出 `IllegalArgumentException` 。
强制放置使用 `forcePut(key,value)` 。

##### 实现

| Key-Value Map Impl | Value-Key Map Impl | Corresponding `BitMap` |
|--------------------|--------------------|------------------------|
| `HashMap`          | `HashMap`          | `HashBiMap`            |
| `ImmutableMap`     | `ImmutableMap`     | `ImmutableBiMap`       |
| `EnumMap`          | `EnumMap`          | `EnumBiMap`            |
| `EnumMap`          | `HashMap`          | `EnumHashBiMap`        |

> 注： `BiMap` 工具，如 `synchronizedBiMap` 在 `Maps` 中。

## `Table`

Guava 提供了集合类型 `Table` 支持任何类型 `row` 和任何类型 `colum` 。
`Table` 支持多种视图，可以从任何角度使用数据：

* `rowMap()` 将 `Table<R, C, V>` 视作 `Map<R, Map<C, V>>`
* `rowKeySet()` 将 `Table<R, C, V>` 视作 `Set<R>`
* `row(r)` 返回一个非 `null` 的`Map<C, V>` ；对 `Map` 的写入将写入底层 `Table`
* 提供类似的**列**方法: `columnMap()` `columnKeySet()` `column(c)` （**基于列的访问比基于行的访问效率低**）
* `cellSet()` 返回一个由 `Table.Cell<R, C, V>` 组成的 `Table` 视图

Guava 提供多种 `Table` 的实现：
* `HashBasedTable`: 基于 `HashMap<R, HashMap<C, V>>`
* `TreeBasedTable`: 基于 `TreeMap<R, TreeMap<C, V>>`
* `ImmutableTable`
* `ArrayTable`: 基于二维数组，构造时要指定列和行；适用于在表密集时提高速度和内存效率。

## `ClassToInstanceMap`

`Map` 的**键**并不都是相同的类型：它们是**类型**，将它们映射到该类型的值。
Guava 提供了 `ClassToInstanceMap` 。

`ClassToInstanceMap` 提供了 `getInstance(Class<T>)` `putInstance(Class<T>, T)` 方法，消除强制类型转换。

`ClassToInstanceMap` 有一个类型参数，通常命名为 `B` 代表 `Map` 管理的类型的上限。

Guava 提供的 `ClassToInstanceMap` 实现类：
* `MutableClassToInstanceMap`
* `ImmutableClassToInstanceMap`

> **注意**：
> 
> 与任何其他 `Map<Class, Object>` 一样， `ClassToInstanceMap` 可能包含原始类型的条目，并且原始类型以及对应的封装类型可能映射到不同的值。

## `RangeSet`

`RangeSet` 描述一组**不相连的非空**范围。
当添加一个范围到一个可变的 `RangeSet` 时，任何连接的范围都会被合并在一起，空的范围会被忽略。

> **注意**：
> 
> 要合并 `Range.closed(1, 10)` 和 `Range.closedOpen(11, 15)` 这样的范围，必须先使用 `Range.canonical(DiscreteDomain` ，例如 `DiscreteDomain.integers()` 对范围进行预处理。

##### 视图

`RangeSet` 实现支持广泛的视图，包括：
* `complement()`: 查看 `RangeSet` 的补集。 `complement()` 也是一个 `RangeSet` 因为它包含断开的、非空的范围。
* `subRangeSet(Range<C>)`: 返回 `RangeSet` 与指定 `Range` 的交集的视图。这概括了传统排序集合的 `hashSet` `subSet` `tailSet` 视图。
* `asRanges()`: 将 `RangeSet` 看作一个 `Set<Range<C>>` 支持遍历。
* `asSet(DisreteDomain<C>)`(仅 `ImmutableRangeSet`): 将 `RangeSet<C>` 看作一个 `ImmutableSortedSet<C>` ，查看范围中的元素而不是元素范围本身。

##### 查询

`RangeSet` 支持的查询操作：
* `contains(C)`: 查询 `RangeSet` 中的任何范围是否包含指定的元素
* `rangeContaining(C)`: 返回包含指定元素的 `Range` ，如果没有则返回 `null`
* `encloses(Range<C>)`: 测试 `RangeSet` 中的 `Range` 是否包围指定的范围
* `span()`: 返回包围 `RangeSet` 中每个范围的最小 `Range`

## `RangeMap`

`RangeMap` 是一个集合类型，描述了从不相干的非空范围到值的映射。
与 `RangeSet` 不同， `RangeMap` 从不**凝聚**相邻的映射，即时相邻的范围映射到相同的值。

##### 视图

`RangeMap` 提供两种视图：
* `asMapOfRanges()`: 将 `RangeMap` 视为 `Map<Range<K>, V>` 用来遍历 `RangeMap`
* `subRangeMap(Range<K>)`: 将 `RangeMap` 与指定 `Range` 的交集视为 `RangeMap`
