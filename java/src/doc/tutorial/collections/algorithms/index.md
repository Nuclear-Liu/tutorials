# Lesson: Algorithms _算法_


The _polymorphic_ algorithms described here are pieces of reusable functionality provided by the Java platform. 
All of them come from the `Collections` class, and all take the form of static methods whose first argument is the collection on which the operation is to be performed. 
The great majority of the algorithms provided by the Java platform operate on `List` instances, but a few of them operate on arbitrary `Collection` instances. 
This section briefly describes the following algorithms:


这里描述的 _多态的_ 算法是 Java 平台提供的可重用功能。
它们都来自 `Collections` 类，并且都采用静态方法的形式，其第一个参数是要对其执行操作的集合。
Java 平台提供的绝大多数算法都在 `List` 实例上运行，但其中一些算法在任意 `Collection` 实例上运行。
本节简要介绍以下算法：


* [Sorting](./index.md#sorting)
* [Shuffling](./index.md#shuffling)
* [Routine Data Manipulation](./index.md#routine-data-manipulation)
* [Searching](./index.md#searching)
* [Composition](./index.md#composition)
* [Finding Extreme Values](./index.md#finding-extreme-values)


## Sorting


The `sort` algorithm reorders a `List` so that its elements are in ascending order according to an ordering relationship. 
Two forms of the operation are provided. 
The simple form takes a `List` and sorts it according to its elements' _natural ordering_. 
If you're unfamiliar with the concept of natural ordering, read the [Object Ordering](https://docs.oracle.com/javase/tutorial/collections/interfaces/order.html) section.


`sort` 算法对 `List` 进行重新排序，使其元素根据排序关系按升序排列。
提供了两种操作形式。
简单形式采用 `List` 并根据其元素的 _自然排序_ 对其进行排序。
如果您不熟悉自然排序的概念，请阅读 [Object Ordering](../interfaces/order.md) 部分。


The `sort` operation uses a slightly optimized _merge sort_ algorithm that is fast and stable:


`sort` 操作使用稍微优化的归并排序算法，快速且稳定：


* **Fast**: It is guaranteed to run in `n log(n)` time and runs substantially faster on nearly sorted lists. 
  Empirical tests showed it to be as fast as a highly optimized quicksort. 
  A quicksort is generally considered to be faster than a merge sort but isn't stable and doesn't guarantee `n log(n)` performance.

* **Fast**: 它保证在 `n log(n)` 时间内运行，并且在几乎排序的列表上运行得更快。
  实证测试表明它与高度优化的快速排序一样快。
  快速排序通常被认为比归并排序更快，但不稳定并且不能保证 `n log(n)` 的性能。

* **Stable**: It doesn't reorder equal elements. 
  This is important if you sort the same list repeatedly on different attributes. 
  If a user of a mail program sorts the inbox by mailing date and then sorts it by sender, the user naturally expects that the now-contiguous list of messages from a given sender will (still) be sorted by mailing date. 
  This is guaranteed only if the second sort was stable.

* **Stable**: 它不会重新排序相等的元素。
  如果您在不同的属性上重复对同一个列表进行排序，这一点很重要。
  如果邮件程序的用户按邮寄日期对收件箱进行排序，然后按发件人对其进行排序，则用户自然希望来自给定发件人的现在连续的邮件列表将（仍然）按邮寄日期排序。
  仅当第二个排序稳定时，才能保证这一点。


The following [`trivial program`](https://docs.oracle.com/javase/tutorial/collections/algorithms/examples/Sort.java) prints out its arguments in lexicographic (alphabetical) order.


下面的 [`trivial program`](examples/Sort.java) 按字典（字母）顺序打印出它的参数。


```java
import java.util.*;

public class Sort {
    public static void main(String[] args) {
        List<String> list = Arrays.asList(args);
        Collections.sort(list);
        System.out.println(list);
    }
}
```


Let's run the program.


让我们运行程序。


`% java Sort i walk the line`


The following output is produced.


产生以下输出。


`[i, line, the, walk]`


The program was included only to show you that algorithms really are as easy to use as they appear to be.


包含该程序只是为了向您展示算法确实像看起来一样易于使用。


The second form of `sort` takes a [`Comparator`](https://docs.oracle.com/javase/8/docs/api/java/util/Comparator.html) in addition to a `List` and sorts the elements with the `Comparator`. 
Suppose you want to print out the anagram groups from our earlier example in reverse order of size — largest anagram group first. 
The example that follows shows you how to achieve this with the help of the second form of the `sort` method.


`sort` 的第二种形式除了 `List` 外还采用 [`Comparator`](https://docs.oracle.com/javase/8/docs/api/java/util/Comparator.html) 并使用 `Comparator` 对元素进行排序。
假设您想以大小相反的顺序打印出我们前面示例中的字谜组——首先是最大的字谜组。
下面的示例向您展示了如何借助 `sort` 方法的第二种形式来实现这一点。


Recall that the anagram groups are stored as values in a `Map`, in the form of `List` instances. 
The revised printing code iterates through the `Map`'s values view, putting every `List` that passes the minimum-size test into a `List` of `List`s. 
Then the code sorts this `List`, using a `Comparator` that expects `List` instances, and implements reverse size-ordering. 
Finally, the code iterates through the sorted `List`, printing its elements (the anagram groups). 
The following code replaces the printing code at the end of the `main` method in the `Anagrams` example.


回想一下，字谜组作为值存储在 `Map` 中，以 `List` 实例的形式。
修改后的打印代码遍历 `Map` 的值视图，将每个通过最小尺寸测试的 `List` 放入 `List` 的 `List` 中。
然后代码使用期望 `List` 实例的 `Comparator` 对这个 `List` 进行排序，并实现反向大小排序。
最后，代码遍历排序的 `List` ，打印其元素（字谜组）。
以下代码替换了 `Anagrams` 示例中 `main` 方法末尾的打印代码。


```text
// Make a List of all anagram groups above size threshold.
List<List<String>> winners = new ArrayList<List<String>>();
for (List<String> l : m.values())
    if (l.size() >= minGroupSize)
        winners.add(l);

// Sort anagram groups according to size
Collections.sort(winners, new Comparator<List<String>>() {
    public int compare(List<String> o1, List<String> o2) {
        return o2.size() - o1.size();
    }});

// Print anagram groups.
for (List<String> l : winners)
    System.out.println(l.size() + ": " + l);
```


Running [`the program`](https://docs.oracle.com/javase/tutorial/collections/algorithms/examples/Anagrams2.java) on the [`same dictionary`](https://docs.oracle.com/javase/tutorial/collections/interfaces/examples/dictionary.txt) as in [The Map Interface](https://docs.oracle.com/javase/tutorial/collections/interfaces/map.html) section, with the same minimum anagram group size (eight), produces the following output.


在 [`same dictionary`](examples/dictionary.txt) 上运行 [`the program`](examples/Anagrams2.java) 与 [The Map Interface](../interfaces/map.md) 部分相同，具有相同的最小字谜组大小（八），产生以下输出。


```log
12: [apers, apres, asper, pares, parse, pears, prase,
       presa, rapes, reaps, spare, spear]
11: [alerts, alters, artels, estral, laster, ratels,
       salter, slater, staler, stelar, talers]
10: [least, setal, slate, stale, steal, stela, taels,
       tales, teals, tesla]
9: [estrin, inerts, insert, inters, niters, nitres,
       sinter, triens, trines]
9: [capers, crapes, escarp, pacers, parsec, recaps,
       scrape, secpar, spacer]
9: [palest, palets, pastel, petals, plates, pleats,
       septal, staple, tepals]
9: [anestri, antsier, nastier, ratines, retains, retinas,
       retsina, stainer, stearin]
8: [lapse, leaps, pales, peals, pleas, salep, sepal, spale]
8: [aspers, parses, passer, prases, repass, spares,
       sparse, spears]
8: [enters, nester, renest, rentes, resent, tenser,
       ternes,��treens]
8: [arles, earls, lares, laser, lears, rales, reals, seral]
8: [earings, erasing, gainers, reagins, regains, reginas,
       searing, seringa]
8: [peris, piers, pries, prise, ripes, speir, spier, spire]
8: [ates, east, eats, etas, sate, seat, seta, teas]
8: [carets, cartes, caster, caters, crates, reacts,
       recast,��traces]
```


## Shuffling


The `shuffle` algorithm does the opposite of what `sort` does, destroying any trace of order that may have been present in a `List`. 
That is, this algorithm reorders the `List` based on input from a source of randomness such that all possible permutations occur with equal likelihood, assuming a fair source of randomness. 
This algorithm is useful in implementing games of chance. 
For example, it could be used to shuffle a `List` of `Card` objects representing a deck. 
Also, it's useful for generating test cases.


`shuffle` 算法的作用与 `sort` 的作用相反，它破坏了 `List` 中可能存在的任何顺序痕迹。
也就是说，该算法基于来自随机源的输入对 `List` 重新排序，使得所有可能的排列以相等的可能性发生，假设随机源是公平的。
该算法在实现机会游戏时很有用。
例如，它可以用来洗牌代表一副牌的 `Card` 对象的 `List` 。
此外，它对于生成测试用例也很有用。


This operation has two forms: one takes a `List` and uses a default source of randomness, and the other requires the caller to provide a [Random](https://docs.oracle.com/javase/8/docs/api/java/util/Random.html) object to use as a source of randomness. 
The code for this algorithm is used as an example in the [`List` section](https://docs.oracle.com/javase/tutorial/collections/interfaces/list.html#shuffle).


此操作有两种形式：一种采用 `List` 并使用默认随机源，另一种需要调用者提供 [Random](https://docs.oracle.com/javase/8/docs/api/java/util/Random.html) 对象以用作随机源。
该算法的代码在 [`List` section](../interfaces/list.md) 中用作示例。


## Routine Data Manipulation


The `Collections` class provides five algorithms for doing routine data manipulation on `List` objects, all of which are pretty straightforward:


`Collections` 类提供了五种算法来对 `List` 对象进行常规数据操作，所有这些都非常简单：


* `reverse` — reverses the order of the elements in a `List`.

* `reverse` — 反转 `List` 中元素的顺序。

* `fill` — overwrites every element in a `List` with the specified value. 
  This operation is useful for reinitializing a `List`.

* `fill` — 用指定的值覆盖 `List` 中的每个元素。
  此操作对于重新初始化 `List` 很有用。

* `copy` — takes two arguments, a destination `List` and a source `List`, and copies the elements of the source into the destination, overwriting its contents. 
  The destination `List` must be at least as long as the source. 
  If it is longer, the remaining elements in the destination `List` are unaffected.

* `copy` — 接受两个参数，一个目标 `List` 和一个源 `List` ，并将源的元素复制到目标，覆盖其内容。
  目标 `List` 必须至少与源一样长。
  如果它更长，则目标 `List` 中的剩余元素不受影响。

* `swap` — swaps the elements at the specified positions in a `List`.

* `swap` — 交换 `List` 中指定位置的元素。

* `addAll` — adds all the specified elements to a `Collection`. 
  The elements to be added may be specified individually or as an array.

* `addAll` — 将所有指定的元素添加到 `Collection` 中。
  要添加的元素可以单独指定，也可以作为数组指定。


## Searching


The `binarySearch` algorithm searches for a specified element in a sorted `List`. 
This algorithm has two forms. 
The first takes a `List` and an element to search for (the "search key"). 
This form assumes that the `List` is sorted in ascending order according to the natural ordering of its elements. 
The second form takes a `Comparator` in addition to the `List` and the search key, and assumes that the `List` is sorted into ascending order according to the specified `Comparator`. 
The `sort` algorithm can be used to sort the `List` prior to calling `binarySearch`.


`binarySearch` 算法在已排序的 `List` 中搜索指定元素。
该算法有两种形式。
第一个接受一个 `List` 和一个要搜索的元素（“搜索键”）。
这种形式假定 `List` 根据其元素的自然顺序按升序排序。
第二种形式除了 `List` 和搜索键外，还需要一个 `Comparator` ，并假设 `List` 按照指定的 `Comparator` 升序排序。
`sort` 算法可用于在调用 `binarySearch` 之前对 `List` 进行排序。


The return value is the same for both forms. 
If the `List` contains the search key, its index is returned. 
If not, the return value is `(-(insertion point) - 1)`, where the insertion point is the point at which the value would be inserted into the `List`, or the index of the first element greater than the value or `list.size()` if all elements in the `List` are less than the specified value. 
This admittedly ugly formula guarantees that the return value will be `>= 0` if and only if the search key is found. 
It's basically a hack to combine a boolean `(found)` and an integer `(index)` into a single `int` return value.


两种形式的返回值相同。
如果 `List` 包含搜索键，则返回其索引。
如果不是，则返回值为 `(-(insertion point) - 1)` ，其中插入点是值将插入到 `List` 中的点，或大于该值的第一个元素的索引如果 `List` 中的所有元素都小于指定值，则为 `list.size()` 。
这个公认的丑陋公式保证当且仅当找到搜索键时返回值将是 `> = 0` 。
将布尔值 `(found)` 和整数 `(index)` 组合成一个 `int` 返回值基本上是一种技巧。


The following idiom, usable with both forms of the `binarySearch` operation, looks for the specified search key and inserts it at the appropriate position if it's not already present.


以下惯用语可用于两种形式的 `binarySearch` 操作，查找指定的搜索键，如果它不存在，则将其插入到适当的位置。


```text
int pos = Collections.binarySearch(list, key);
if (pos < 0)
   l.add(-pos-1, key);
```


## Composition


The frequency and disjoint algorithms test some aspect of the composition of one or more `Collections`:


频率和不相交算法测试一个或多个 `Collections` 组成的某些方面：


* `frequency` — counts the number of times the specified element occurs in the specified collection

* `frequency` — 计算指定元素在指定集合中出现的次数

* `disjoint` — determines whether two `Collections` are disjoint; that is, whether they contain no elements in common

* `disjoint` — 判断两个 `Collections` 是否不相交；也就是说，它们是否不包含共同的元素


## Finding Extreme Values


The `min` and the `max` algorithms return, respectively, the minimum and maximum element contained in a specified `Collection`. 
Both of these operations come in two forms. 
The simple form takes only a `Collection` and returns the minimum (or maximum) element according to the elements' natural ordering. 
The second form takes a `Comparator` in addition to the `Collection` and returns the minimum (or maximum) element according to the specified `Comparator`.


`min` 和 `max` 算法分别返回包含在指定 `Collection` 中的最小和最大元素。
这两种操作都有两种形式。
简单的形式只接受一个 `Collection` 并根据元素的自然顺序返回最小（或最大）元素。
第二种形式除了 `Collection` 之外还采用 `Comparator` ，并根据指定的 `Comparator` 返回最小（或最大）元素。
