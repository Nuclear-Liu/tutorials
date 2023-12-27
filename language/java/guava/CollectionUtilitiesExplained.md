# 集合工具

Guava 提供更多类似 `java.util.Collections` 中提供的实用程序：适用于所有集合的静态方法。
这是 Guava 最受欢迎和成熟的部分。

与特定接口对应的方法以相对直观的方式分组：

| 接口           | JDK / Guava ? | Guava 实用工具类    |
|--------------|---------------|----------------|
| `Collection` | JDK           | `Collections2` |
| `List`       | JDK           | `Lists`        |
| `Set`        | JDK           | `Sets`         |
| `SortedSet`  | JDK           | `Sets`         |
| `Map`        | JDK           | `Maps`         |
| `SortedMap`  | JDK           | `Maps`         |
| `Queue`      | JDK           | `Queues`       |
| `Multiset`   | Guava         | `Multisets`    |
| `Multimap`   | Guava         | `Multimaps`    |
| `BiMap`      | Guava         | `Maps`         |
| `Table`      | Guava         | `Tables`       |

## 静态构造函数

Guava 提供了实用泛型推断构造右侧类型的静态方法；与 JDK 7 中的菱形运算符相比，Guava 实用工厂方法模式：
* 方便地初始化具有起始元素的集合
* 通过重命名工厂方法(Effective Java item 1)，提高初始化集合大小的可读性

```jshelllanguage
// create empty collection
List<String> litst = Lists.neArrayList();
Map<String, Integer> map = Maps.newLinkedHashMap();
// create by elements
Set<Type> copySet = Sets.newHashSet(elements);
List<String> theseElements = Lists.newArrayList("alpha", "beta", "gamma");
// create to sizes
List<Type> exactly100 = Lists.newArrayListWithCapacity(100);
List<Type> approx100 = Lists.newArrayListWithExpectedSize(100);
Set<Type> approx100Set = Sets.newHashSetWithExpectedSize(100);
```
> **注意:** Guava 引入的新集合类型不公开原始构造函数，也不在实用类中提供初始化器。
> 它们直接暴露静态工厂方法。

## `Iterables`

Guava 更倾向于提供接受 `Iterable` 而不是 `Collection` 的实用程序。
在 Google 经常遇到的情况：**集合**实际上并不存储在主内存中，而是从数据库或其他数据中心收集的，因此无法在不实际抓取所有元素的情况下支持类似 `size()` 的操作。

## General

| 方法                                  | 描述                                            | 参见                                                            |
|-------------------------------------|-----------------------------------------------|---------------------------------------------------------------|
| `concat(Iterable<Iterable>`         | 返回多个可迭代对象连接的惰性视图                              | `concat(Iterable ...)`                                        |
| `frequency(Iterable, Object)`       | 返回对象的出现次数                                     | `Collections.frequency(Collection, Object)` `Multiset`        |
| `partition(Iterable, int)`          | 返回一个不可修改的迭代视图，该视图被分割成指定大小的块                   | `Lists.partition(List, int)` `paddedPartition(Iterable, int)` |
| `getFirst(Iterable, T default)`     | 返回迭代器的抵押给元素，如果为 `null` 则返回默认值                 | `Iterable.iterator().next()` `FluentIterable.first()`         |
| `getLast(Iterable)`                 | 返回迭代器的最后一个元素，如果为空则抛出 `NoSuchElementException` | `getLast(Iterable, T default)` `FluentIterable.last()`        |
| `elementsEqual(Iterable, Iterable)` | 如果迭代中的元素顺序相同，则返回 `true`                       | `List.equals(Object)`                                         |
| `unmodifiableIterable(Iterable)`    | 返回不可修改的迭代视图                                   | `Collections.unmodifiableCollection(Collection)`              |
| `limit(Iterable, int)`              | 返回一个 `Iterable` ，最多返回指定数量的元素                  | `FluentIterable.limit(int)`                                   |
| `getOnlyElement(Iterable)`          | 返回 `Iterable` 中的唯一元素。如果迭代为空或有多个元素，则快速失败       | `getOnlyElement(Iterable, T default)`                         |

##### 类似集合

通常情况下，集合在其他集合上自然支持这些操作，但在迭代上不支持。

| 方法                                                    | `Collection` 类似方法                  | `Fluentlterable` 等价方法             |
|-------------------------------------------------------|------------------------------------|-----------------------------------|
| `addAll(Collection addTo, Iterable toAdd)`            | `Collection.addAll(Collection)`    |                                   |
| `contains(Iterable, Object)`                          | `Collection.contains(Object)`      | `FluentIterable.contains(Object)` |
| `removeAll(Iterable removeFrom, Collection toRemove)` | `Collection.removeAll(Collection)` |                                   |
| `retainAll(Iterable removeFrom, Collection toRetain)` | `Collection.retainAll(Collection)` |                                   |
| `size(Iterable)`                                      | `Collection.size()`                | `FluentIterable.size()`           |
| `toArray(Iterable, Class)`                            | `Collection.toArray(T[])`          | `FluentIterable.toArray(Class)`   |
| `isEmpty(Iterable)`                                   | `Collection.isEmpty()`             | `FluentIterable.isEmpty()`        |
| `get(Iterable, int)`                                  | `List.get(int)`                    | `FluentIterable.get(int)`         |
| `toString(Iterable)`                                  | `Collection.toString()`            | `FluentIterable.toString()`       |

##### `FluentIterable`

`FluentIterable` 有一些方便的方法用于复制到不可变集合中：

| 结果类型                 | 方法                                 |
|----------------------|------------------------------------|
| `ImmutableList`      | `toImmutableList()`                |
| `ImmutableSet`       | `toImmutableSet()`                 |
| `ImmutableSortedSet` | `toImmutableSortedSet(Comparator)` |

## `Lists`

`Lists` 为 `List` 提供实用工具：

| 方法                     | 描述                                                        |
|------------------------|-----------------------------------------------------------|
| `partition(List, int)` | 返回底层列表的视图，该视图被划分为指定大小的块                                   |
| `reverse(List)`        | 返回指定列表的反向视图；_注意：如果列表是不可变的请考虑使用 `ImmutableList.reverse()`_ |

##### 静态工厂

`Lists` 提供了以下静态工厂方法：

| 实现           | 工厂                                                                                                                                                                                                               |
|--------------|------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| `ArrayList`  | basic: `newArrayList()` with elements: `newArrayList(E...)` from Iterable: `newArrayList(Iterable)` with exact capacity: `newArrayListWithCapacity(int)` with expected size: `newArrayListWithExpectedSize(int)` |
| `LinkedList` | basic: `newLinkedList()` from Iterable: `newLinkedList(Iterable)`                                                                                                                                                |

## `Comparators`

##### 查找某些元素的最小值或最大值

由于需要尽量减少分配、装箱和存在于不同为位置的 API 而变得复杂。
下表总结了这项任务的最佳实践。

> `min()` 同样适用

| 比较内容                                        | 2 实例                         | 2+ 实例                                   |
|---------------------------------------------|------------------------------|-----------------------------------------|
| 基本数据类型(`long` `int` `double` `float`)       | `Math.max(a, b)`             | `Longs.max(long...)` `Ints.max(int...)` |
| `Comparable` 实例(`Duration` `String` `Long`) | `Comparators.max(a, b)`      | `Collections.max(asList(a, b, c))`      |
| 使用自定义 `Comparator`                          | `Comparators.max(a, b, cmp)` | `Collections.max(asList(a, b, c), cmp)` |

## `Sets`

##### 集合论运算

提供了许多标准的集合论操作，作为参数集合的视图来实现。
这些操作返回一个 `SetView` 可以被使用：
* 直接作为一个 `Set` ，因为它实现了 `Set` 接口
* 使用 `copyInto(Set)` 将其复制到另一个可变集合中
* 使用 `immutableCopy()` 进行不可变拷贝

| 方法                              | 描述  |
|---------------------------------|-----|
| `union(Set, Set)`               | 合集  |
| `intersection(Set, Set)`        | 交集  |
| `difference(Set, Set)`          | 差集  |
| `symmetricDifference(Set, Set)` | 对称差 |

##### 其他 `Set` 工具

| 方法                            | 描述                                | 参见                         |
|-------------------------------|-----------------------------------|----------------------------|
| `cartesianProduct(List<Set>)` | 返回从每个集合中选择一个元素后得到的可能列表（集合间元素排列组合） | `cartesianProduct(Set...)` |
| `powerSet(Set)`               | 返回指定集合的子集                         |                            |

##### 静态工厂

`Sets` 提供的静态工厂：

| 实现              | 工厂                                                                                                                                                                                     |
|-----------------|----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| `HashSet`       | basic: `newHashSet` with elements: `newHashSet(E...)` from `Iterable`: `newHashSet(Iterable)` with expected size: `newHashSetWithExpectedSize` from `Iterator`: `newHashSet(Iterator)` |
| `LinkedHashSet` | basic: `newLinkedHashSet` from `Iterable`: `newLinkedHashSet(Iterable)` with expected size: `newLinkedHashSetWithExpectedSize`                                                         |
| `TreeSet`       | basic: `newTreeSet` with `Comparator`: `newTreeSet(Comparator)` from `Iterable`: `newTreeSet(Iterable)`                                                                                |

## `Maps`

##### `uniqueIndex`

`Maps.uniqueIndex(Iterable, Function)` 解决问题：有一堆对象，每个对象都有一些独特的属性，希望能够通过这些属性来查找这些对象。

如果索引不唯一参见： `Multimaps.index`

##### `difference`

`Maps.difference(Map, Map)` 比较两个 `Map` 之间的所有差异。
它返回一个 `MapDifference` 对象，该对象将**维恩图**分解成以下及部分：

| 方法                     | 描述                                                              |
|------------------------|-----------------------------------------------------------------|
| `entriesInCommon()`    | 两个 `Map` 中的条目，其键和值均匹配 **交集**                                    |
| `entriesDiffering()`   | 键相同但值不同的条目；该映射中的值是 `MapDifference.ValueDifference` 类型，可以查看左右两个值 |
| `entriesOnlyOnLeft()`  | 返回键值是左侧映射中但不在右侧映射中的条目                                           |
| `entriesOnlyOnRight()` | 返回键值在右侧映射中但不在左侧映射中的条目                                           |

##### `BiMap` 工具

`BiMap` 也是 `Map` 类，因此 `BiMap` 上的 Guava 实用程序位于 `Maps` 类中：

| `BiMap` 工具                  | 对应的 `Map` 工具                       |
|-----------------------------|------------------------------------|
| `syncchronizedBiMap(BiMap)` | `Collections.synchronizedMap(Map)` |
| `unmodifiableBiMap(BiMap)`  | `Collections.unmodifiableMap(Map)` |

##### 静态工厂

`Maps` 提供的静态工厂方法：

| 实现                                           | 工厂                                                                                                         |
|----------------------------------------------|------------------------------------------------------------------------------------------------------------|
| `HashMap`                                    | basic: `newHashMap` from `Map`: `newHashMap(Map)` with expected size: `newHashMapWithExpectedSize`         |
| `LinkedHashMap`                              | basic: `newLinkedHashMap` from `Map`: `newLinkedHashMap(Map)`                                              |
| `TreeMap`                                    | basic: `newThreeMap` from `Comparator`: `newTreeMap(Comparator)` from `SortedMap`: `newTreeMap(SortedMap)` |
| `EnumMap`                                    | from `Class`: `newEnumMap(Class)` from `Map`: `newEnumMap(Map)`                                            |
| `ConcurrentMap`                              | basic: `newConcurrentMap`                                                                                  |
| `IdentityHashMap`                            | basic: `newIdentityHashMap`                                                                                |

## `Multisets`

标准的 `Collection` 操作(例如： `containsALL`)会忽略多集合中元素的数量，而只关心元素是否在多集合中。
多集合的 `Multisets` 提供了提供了许多考虑多重集中元素多重性的操作。

| 方法                                                          | 描述                                                     | 与 `Collection` 方法的不同                                    |
|-------------------------------------------------------------|--------------------------------------------------------|---------------------------------------------------------|
| `containsOccurrences(Multiset sup, Multiset sub)`           | 如果所有 `o` 的 `sub.count(o) <= super.count(o)` 则返回 `true` | `Collection.containsAll` 忽略计数，只测试元素是否包含                 |
| `removeOccurrences(Multiset removeFrom, Multiset toRemove)` | 对于 `toRemove` 中元素的每次出现，删除 `removeFrom` 中的一个匹配项         | `Collection.removeAll` 删除在 `toRemove` 中出现过一次的任何元素的所有匹配项 |
| `retainOccurrences(Multiset removeFrom, Multiset toRetain)` | 保证所有 `o` 的 `removeFrom.count(o) <= toRetain.count(o)`  | `Collection.retainAll` 保留所有在 `toRetain` 中出现甚至一次的元素      |
| `intersection(Multiset, Multiset)`                          | 返回两个多重集的交集视图： `retainOccurrences` 的非破坏性替代方案            |                                                         |
| ------                                                      | ------                                                 |
| `copyHighestCountFirst(Multiset)`                           | 返回按降频顺序迭代元素的多重集的不可变副本                                  |
| `unmodifiableMultiset(Multiset)`                            | 返回多级的不可修改视图                                            |
| `unmodifiableSortedMultiset(SortedMultiset)`                | 返回已排序的多集的不可修改视图                                        |

## `Multimaps`

##### `index`

`Multimaps.index(Iterable, Function)` 与 `Maps.uniqueIndex` 类似，可以解决以下问题：当你想查找所有具有某些共同属性的对象时，而这些属性并不一定是唯一的（例如按照字符长度进行分组）。

##### `invertFrom`

Guava 提供了 `invertForm(Multimap toInvoert, Multimap dest)` 可以将一个键映射到多个值。

> 如果使用的是 `ImmutableMultimap` 可以使用 `ImmutableMultimap.inverse()`

##### `forMap`

`Multimap.forMap(Map)` 将 `Map` 视为 `SetMultimap` （结合 `Multimaps.invertFrom` 使用）。

##### Wrappers

`Multimaps` 提供了传统的包装器方法，以及基于您选择的 `Map` 和 `Collection` 实现获取自定义 `Multimap` 实现的工具。

| `Multimap` 类型       | 不可修改                            | 线程安全                            | 自定义                    |
|---------------------|---------------------------------|---------------------------------|------------------------|
| `Multimap`          | `unmodifiableMultimap`          | `synchronizedMultimap`          | `newMultimap`          |
| `ListMultimap`      | `unmodifiableListMultimap`      | `synchronizedListMultimap`      | `newListMultimap`      |
| `SetMultimap`       | `unmodifiableSetMultimap`       | `synchronizedSetMultimap`       | `newSetMultimap`       |
| `SortedSetMultimap` | `unmodifiableSortedSetMultimap` | `synchronizedSortedSetMultimap` | `newSortedSetMultimap` |

自定义 `Multimap` 实现允许指定应在返回的 `Multimap` 中使用的特定实现。
注意事项包括：

* `Multimap` 假定对 `Map` 和工厂返回的列表拥有完全的所有权。这些对象不应手动更新，提供时应为空，并且不应使用软引用、弱引用或幽灵引用。
* 不保证在修改了 `Multimap` 后 `Map` 的内容会是什么样子。
* 当并发操作更新 `Multimap` 时，即使 `map` 和工厂生成的实例时线程安全的， `Multimap` 也不是线程安全的。不过，并发读取操作会正常工作。如果有必要可以使用 `synchronized` 包装器解决这个问题。
* 如果 `map` 、工厂、工厂生成的列表和 `multimap`内容都是可序列化的。
* `Multimap.get(key)` 返回的集合与 `Supplier` 返回的集合类型不同，如果你的供应商返回的是 `RandomAccess` 列表，则 `Multimap.get(key)` 也将是随机访问

> **注意：**自定义的 `Multimap` 方法需要一个 `Supplier` 参数来生成新的集合。

## `Tables`

`Tables` 类提供的一些方便的实用工具。

##### `customTable`'

`Tables.newCustomTable(Map, Supplier)` 允许指定行或列实现一个 `Tab` 。

##### `transpose`

使用 `transpose(Table<R, C, V>)` 方法，可以将 `Tab<R, C, V>` 视作 `Tab<C, R, V>`

##### `newCustomTable`

类似于 `Multimaps.newXXXMultimap(Map, Supplier)` 实用工具；
`Tables.newCustomTable(Map, Supplier<Map>)` 允许你使用你喜欢的任何行或列映射指定一个 `Table` 实现。

* `unmodifiableTable`
* `unmodifiableRowSortedTable`
