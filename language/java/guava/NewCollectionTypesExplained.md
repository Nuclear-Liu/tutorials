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

> `Multiset<E>` 不是 `Map<E, Integer>`:
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

通常， `Multimap` 接口最好使用第一种角度；但也允许你使用 `asMap()` 视图来使用，返回一个 `Map<K, Collection<V>>` 。
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
* `entries`: 将 `Multimap<K, V>` 视为 `Collection<Map.Entry<K, V>>` （对于 `SetMultimap` 是 `Set<Map.Entry<K, V>>`）
* `keySet`: 将 `Multimap<K, V>` 的不同键视为 `Set<K>`
* `keys`: 将 `Multimap`
* `values`:

## `BitMap`

## `Table`

## `ClassToInstanceMap`

## `RangeSet`

## `RangeMap`
