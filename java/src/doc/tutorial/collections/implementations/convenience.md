# Convenience Implementations


This section describes several mini-implementations that can be more convenient and more efficient than general-purpose implementations when you don't need their full power. 
All the implementations in this section are made available via static factory methods rather than `public` classes.


本节描述了几个迷你实现，当您不需要它们的全部功能时，它们比通用实现更方便、更高效。
本节中的所有实现都是通过静态工厂方法而不是 `public` 类提供的。


## List View of an Array _数组的列表视图_


The [`Arrays.asList`](https://docs.oracle.com/javase/8/docs/api/java/util/Arrays.html#asList-T...-) method returns a `List` view of its array argument. 
Changes to the `List` write through to the array and vice versa. 
The size of the collection is that of the array and cannot be changed. 
If the `add` or the `remove` method is called on the `List`, an `UnsupportedOperationException` will result.


[`Arrays.asList`](https://docs.oracle.com/javase/8/docs/api/java/util/Arrays.html#asList-T...-) 方法返回其数组参数的 `List` 视图。
对 `List` 的更改会写入数组，反之亦然。
集合的大小是数组的大小，不能更改。
如果在 `List` 上调用 `add` 或 `remove` 方法，将导致 `UnsupportedOperationException` 。


The normal use of this implementation is as a bridge between array-based and collection-based APIs. 
It allows you to pass an array to a method expecting a `Collection` or a `List`. 
However, this implementation also has another use. 
If you need a fixed-size `List`, it's more efficient than any general-purpose `List` implementation. 
This is the idiom.


此实现的正常使用是作为基于数组和基于集合的 API 之间的桥梁。
它允许您将数组传递给需要 `Collection` 或 `List` 的方法。
然而，这个实现还有另一个用途。
如果您需要一个固定大小的 `List` ，它比任何通用的 `List` 实现更有效。
这是成语。


`List<String> list = Arrays.asList(new String[size]);`


Note that a reference to the backing array is not retained.


请注意，不保留对支持数组的引用。


## Immutable Multiple-Copy `List` _不可变的多副本 `List`_


Occasionally you'll need an immutable `List` consisting of multiple copies of the same element. 
The [`Collections.nCopies`](https://docs.oracle.com/javase/8/docs/api/java/util/Collections.html#nCopies-int-T-) method returns such a list. 
This implementation has two main uses. 
The first is to initialize a newly created `List`; for example, suppose you want an `ArrayList` initially consisting of 1,000 `null` elements. 
The following incantation does the trick.


有时您需要一个不可变的 `List` ，其中包含同一元素的多个副本。
[`Collections.nCopies`](https://docs.oracle.com/javase/8/docs/api/java/util/Collections.html#nCopies-int-T-) 方法返回这样一个列表。
这个实现有两个主要用途。
首先是初始化一个新创建的 `List` ；例如，假设您想要一个最初由 1,000 个 `null` 元素组成的 `ArrayList` 。
下面的咒语可以解决问题。


`List<Type> list = new ArrayList<Type>(Collections.nCopies(1000, (Type)null));`


Of course, the initial value of each element need not be `null`. 
The second main use is to grow an existing `List`. 
For example, suppose you want to add 69 copies of the string `"fruit bat"` to the end of a `List<String>`. 
It's not clear why you'd want to do such a thing, but let's just suppose you did. 
The following is how you'd do it.


当然，每个元素的初始值不必是 `null` 。
第二个主要用途是增加现有的 `List` 。
例如，假设您想将字符串 `"fruit bat"` 的 69 个副本添加到 `List<String>` 的末尾。
目前尚不清楚您为什么要这样做，但让我们假设您这样做了。
以下是您的操作方法。


`lovablePets.addAll(Collections.nCopies(69, "fruit bat"));`


By using the form of `addAll` that takes both an index and a `Collection`, you can add the new elements to the middle of a `List` instead of to the end of it.


通过使用同时接受索引和集合的 `addAll` 形式，您可以将新元素添加到 `List` 的中间而不是其末尾。


## Immutable Singleton `Set` _不可变单例 `Set`_


Sometimes you'll need an immutable _singleton_ `Set`, which consists of a single, specified element. 
The [`Collections.singleton`](https://docs.oracle.com/javase/8/docs/api/java/util/Collections.html#singleton-T-) method returns such a `Set`. 
One use of this implementation is to remove all occurrences of a specified element from a `Collection`.


有时你需要一个不可变的 _singleton_ `Set` ，它由一个单一的、指定的元素组成。
[`Collections.singleton`](https://docs.oracle.com/javase/8/docs/api/java/util/Collections.html#singleton-T-) 方法返回这样的 `Set` 。
此实现的一个用途是从 `Collection` 中删除所有出现的指定元素。


`c.removeAll(Collections.singleton(e));`


A related idiom removes all elements that map to a specified value from a `Map`. 
For example, suppose you have a `Map` — `job` — that maps people to their line of work and suppose you want to eliminate all the lawyers. 
The following one-liner will do the deed.


一个相关的习惯用法会从 `Map` 中删除所有映射到指定值的元素。
例如，假设你有一个 `Map` —— `job` ——将人们映射到他们的工作范围，并假设你想消除所有的律师。
以下单行将做事。


`job.values().removeAll(Collections.singleton(LAWYER));`


One more use of this implementation is to provide a single input value to a method that is written to accept a collection of values.


此实现的另一个用途是向编写为接受值集合的方法提供单个输入值。


## Empty `Set`, `List`, and `Map` Constants _空 `Set` 、`List` 和 `Map` 常量_


The [`Collections`](https://docs.oracle.com/javase/8/docs/api/java/util/Collections.html) class provides methods to return the empty `Set`, `List`, and `Map` — [`emptySet`](https://docs.oracle.com/javase/8/docs/api/java/util/Collections.html#emptySet--), [`emptyList`](https://docs.oracle.com/javase/8/docs/api/java/util/Collections.html#emptyList--), and [`emptyMap`](https://docs.oracle.com/javase/8/docs/api/java/util/Collections.html#emptyMap--). 
The main use of these constants is as input to methods that take a `Collection` of values when you don't want to provide any values at all, as in this example.


[`Collections`](https://docs.oracle.com/javase/8/docs/api/java/util/Collections.html) 类提供了返回空的 `Set` 、 `List` 和 `Map` 的方法 —— [`emptySet`](https://docs.oracle.com/javase/8/docs/api/java/util/Collections.html#emptySet--) 、 [`emptyList`](https://docs.oracle.com/javase/8/docs/api/java/util/Collections.html#emptyList--) 和 [`emptyMap`](https://docs.oracle.com/javase/8/docs/api/java/util/Collections.html#emptyMap--) 。
当您根本不想提供任何值时，这些常量的主要用途是作为获取 `Collection` 值的方法的输入，如本例所示。


`tourist.declarePurchases(Collections.emptySet());`
