# 排序 Ordering

## 概述

`Ordering` 是 Guava **流程**的 `Comparator` 接口的实现类，它可以用来建立复杂的比较器并应用于对象集合。

在其核心部分， `Ordering` 实例只不过是一个特殊的 `Comparator` 实例。
`Ordering` 只是把依赖 `Comparator` 的方法（例如： `Collections.max`）作为实例方法使用。
为了获得额外的能力， `Ordering` 类提供了链式方法来调整和加强现有的比较器。

## 创建

常见的排序是由静态方法提供的：

| 方法                | 描述                                                                 | Java 8+                                  |
|-------------------|--------------------------------------------------------------------|------------------------------------------|
| `natural()`       | 返回一个可序列化的排序，该排序使用数值的**自然顺序**。当传递一个空参数时，抛出一个 `NullPointerException` | `Comparator.naturalOrder`                |
| `usingToString()` | 返回一个排序，通过 `toString()` 返回的它们的**字符串表示的自然排序**来比较对象。不支持**空值**。        | `Comparator.comparing(Object::toString)` |

将一个预先存在的 `Comparator` 变成一个 `Ordering` 使用 **`Ordering.from(Comparator)`** 。

创建一个自定义的 `Ordering` 的更常见的方法是完全跳过 `Comparator` ，而直接扩展 `Ordering` 抽象类：

```jshelllanguage
Ordering<String> byLengthOrdering = new Ordering<String>() {
    public int compare(String left, String right) {
        return Ints.compare(left.length(), right.length());
	}
};
```

## 链式 Chaining

可以包装一个给定的 `Ordering` 以获得派生的排序。
一些常用的变化包括：

| 方法                     | 描述                                                                      | Java 8+                                   |
|------------------------|-------------------------------------------------------------------------|-------------------------------------------| 
| `reverse()`            | 返回反向排序                                                                  |                                           |
| `nullsFirst()`         | 返回一个 `Ordering` ，将空元素排在非空元素之前，其他方面与原始 `Ordering` 的行为相同。参见 `nullsLast()` |                                           |
| `compound(Comparator)` | 返回一个 `Ordering` ，使用指定的 `Comparator` 来**打破平局**                           |                                           |
| `lexicographical()`    | 返回一个 `Ordering` ，该排序对象按元素的字典顺序排序。                                       | `Comparators.lexicographical(Comparator)` |
| `onResultOf(Function)` | 返回一个 `Ordering` ，通过首先对元素应用函数，然后使用此函数比较这些结果来对元素进行排序。                     |                                           |

## 应用

Guava 提供的许多方法来操作或检查使用排序的值或集合。

| 方法                                     | 描述                                                                          | 参见                                              |
|----------------------------------------|-----------------------------------------------------------------------------|-------------------------------------------------|
| `greatestOf(Iterable iterable, int k)` | 根据此 `Ordering` ，按从大到小的顺序返回指定可迭代对象的 `k` 最大元素。不一定稳定                           | `leastOf`                                       |
| `isOrdered(Iterable)`                  | 根据此 `Ordering` 顺序测试指定的 `Iterable` 是否非递减顺序排列                                 | `isStrictlyOrdered`                             |
| `sortedCopy(Iterable)`                 | 以 `List` 形式返回指定元素的副本                                                        | `immutableSortedCopy`                           |
| `min(E, E)`                            | 根据此顺序返回其两个参数中最小值，如果值相等，则返回第一个参数。                                            | `max(E, E)`                                     |
| `min(E, E, E, E...)`                   | 根据此顺序返回其参数的最小值。如果有多个最小值，则返回第一个。                                             | `max(E, E, E, E...)`                            |
| `min(Iterable)`                        | 返回指定的 `Iterable` 的最小元素。如果 `Iterable` 为 `null` ，则抛出 `NoSuchElementException` | `max(Iterable)` `min(Iterator)` `max(Iterator)` |
