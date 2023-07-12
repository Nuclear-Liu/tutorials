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

## `BitMap`

## `Table`

## `ClassToInstanceMap`

## `RangeSet`

## `RangeMap`
