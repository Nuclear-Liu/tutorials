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

## 比较器

## `Tables`

`Tables` 类提供的一些方便的实用工具。

##### `newCustomTable`

类似于 `Multimaps.newXXXMultimap(Map, Supplier)` 实用工具；
`Tables.newCustomTable(Map, Supplier<Map>)` 允许你使用你喜欢的任何行或列映射指定一个 `Table` 实现。
