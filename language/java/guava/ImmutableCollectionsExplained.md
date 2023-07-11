# 不可变集合 Immutable Collections

不可变对象优势：
* 可以安全使用不受信任的库
* 线程安全：避免多线程争用
* 更有效的内存使用，不可变可以节省时间和空间
* 可以使用**常量**，预期不会被改变

对象的不可变副本是一种很好的**防御性编程**技术。
Guava 为每种标准集合（包括 Guava 自己的集合）提供了简易不可变版本。

> JDK 提供的 `Collections.unmodifiableXXX` 方法，存在的问题:
> 
> * 笨拙冗长：实现防御性编程的任何地方使用起来繁琐
> * 不安全：只有当没有人持有原始集合的引用时，返回的集合才是真正不可变的
> * 低效：数据结构仍具有可变集合的所有开销，包括**并发修改检查**、**哈希表中的额外空间**等

**当期望不修改一个集合，或者期望一个集合保持不变是，将其防御性地复制到一个不可变的集合中是一个好的实践。**

**注意：**
**Guava 的不可变集合的实现拒绝 `null` 值**，如果需要使用 `null` 值，可以考虑使用 `Collections.unmodifiableXXX` 集合，建议尽量参考[使用和避免 `null`](./UsingAndAvoidingNullExplained.md)

## 使用

创建 `ImmutableXXX` 集合的方式：
1. 使用 `copyOf` 方法

	`copyOf` 方法比预想的更加聪明： `ImmutableXXX.copyOf()` 视图在安全的情况下避免复制数据是很有用的——具体细节没有指定，实现起来通常是**智能**的。
	`ImmutableXXX.copyOf()` 试图避免线性时间复制：

	这有助于将良好的防御性编程风格的性能开销降至最低。
2. 使用 `of` 方法
3. 使用 `Builder` 

> 出了有序集合，元素顺序在构建时顺序确定。

`asList` 方法 : 所有不可变集合都可以通过 `asList()` 提供 `ImmutableList` 视图。
（例如： `ImmutableSortedSet.asList().get(index)` 获取排序好的 `index` 位置的元素。）
返回的 `ImmutableList` 通常是一个固定开销视图（并不总是）。通常比普通的 `List` 更聪明。

| 不可变集合                     | 可变集合                            |
|---------------------------|---------------------------------|
| `ImmutableCollection`     | JDK: `Collection`               |
| `ImmutableList`           | JDK: `List`                     |
| `ImmutableSet`            | JDK: `Set`                      |
| `ImmutableSortedSet`      | JDK: `SortedSet` `NavigableSet` |
| `ImmutableMap`            | JDK: `Map`                      |
| `ImmutableSortedMap`      | JDK: `SortedMap`                |
| `ImmutableMultiset`       | Guava: `Multiset`               |
| `ImmutableSortedMultiset` | Guava: `SortedMultiset`         |
| `ImmutableMultimap`       | Guava: `Multimap`               |
| ``                          |                                 |
