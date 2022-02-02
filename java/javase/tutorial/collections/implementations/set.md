# Set Implementations


The `Set` implementations are grouped into general-purpose and special-purpose implementations.


`Set` 实现分为通用实现和专用实现。


## General-Purpose `Set` Implementations _通用 `Set` 实现_


There are three general-purpose [`Set`](https://docs.oracle.com/javase/8/docs/api/java/util/Set.html) implementations — [`HashSet`](https://docs.oracle.com/javase/8/docs/api/java/util/HashSet.html), [`TreeSet`](https://docs.oracle.com/javase/8/docs/api/java/util/TreeSet.html), and [`LinkedHashSet`](https://docs.oracle.com/javase/8/docs/api/java/util/LinkedHashSet.html). 
Which of these three to use is generally straightforward. 
`HashSet` is much faster than `TreeSet` (constant-time versus log-time for most operations) but offers no ordering guarantees. 
If you need to use the operations in the `SortedSet` interface, or if value-ordered iteration is required, use `TreeSet`; otherwise, use `HashSet`. 
It's a fair bet that you'll end up using `HashSet` most of the time.


有三种通用的 [`Set`](https://docs.oracle.com/javase/8/docs/api/java/util/Set.html) 实现 —— [`HashSet`](https://docs.oracle.com/javase/8/docs/api/java/util/HashSet.html) 、 [`TreeSet`](https://docs.oracle.com/javase/8/docs/api/java/util/TreeSet.html) 和 [`LinkedHashSet`](https://docs.oracle.com/javase/8/docs/api/java/util/LinkedHashSet.html) 。
使用这三个中的哪一个通常很简单。
`HashSet` 比 `TreeSet` 快得多（大多数操作的常数时间与日志时间），但不提供排序保证。
如果需要使用 `SortedSet` 接口中的操作，或者需要值序迭代，请使用 `TreeSet` ；否则，使用 `HashSet` 。
可以肯定的是，您最终会在大多数情况下使用 `HashSet` 。


`LinkedHashSet` is in some sense intermediate between `HashSet` and `TreeSet`. 
Implemented as a hash table with a linked list running through it, it provides _insertion-ordered_ iteration (least recently inserted to most recently) and runs nearly as fast as `HashSet`. 
The `LinkedHashSet` implementation spares its clients from the unspecified, generally chaotic ordering provided by `HashSet` without incurring the increased cost associated with `TreeSet`.


`LinkedHashSet` 在某种意义上介于 `HashSet` 和 `TreeSet` 之间。
实现为带有链表的哈希表，它提供 _insertion-ordered_ 迭代（最近插入到最近插入）并且运行速度几乎与 `HashSet` 一样快。
`LinkedHashSet` 实现使其客户免于 `HashSet` 提供的未指定的、通常是混乱的排序，而不会增加与 `TreeSet` 相关的成本。


One thing worth keeping in mind about `HashSet` is that iteration is linear in the sum of the number of entries and the number of buckets (the _capacity_). 
Thus, choosing an initial capacity that's too high can waste both space and time. 
On the other hand, choosing an initial capacity that's too low wastes time by copying the data structure each time it's forced to increase its capacity. 
If you don't specify an initial capacity, the default is 16. 
In the past, there was some advantage to choosing a prime number as the initial capacity. 
This is no longer true. 
Internally, the capacity is always rounded up to a power of two. 
The initial capacity is specified by using the `int` constructor. 
The following line of code allocates a `HashSet` whose initial capacity is 64.


关于 `HashSet` 值得记住的一件事是迭代在条目数和桶数（ _容量_ ）的总和中是线性的。
Thus, choosing an initial capacity that's too high can waste both space and time. 
On the other hand, choosing an initial capacity that's too low wastes time by copying the data structure each time it's forced to increase its capacity. 
If you don't specify an initial capacity, the default is 16. 
In the past, there was some advantage to choosing a prime number as the initial capacity. 
This is no longer true. 
Internally, the capacity is always rounded up to a power of two. 
The initial capacity is specified by using the `int` constructor. 
The following line of code allocates a `HashSet` whose initial capacity is 64.


`Set<String> s = new HashSet<String>(64);`


The `HashSet` class has one other tuning parameter called the _load factor_. 
If you care a lot about the space consumption of your `HashSet`, read the `HashSet` documentation for more information. 
Otherwise, just accept the default; it's almost always the right thing to do.


If you accept the default load factor but want to specify an initial capacity, pick a number that's about twice the size to which you expect the set to grow. 
If your guess is way off, you may waste a bit of space, time, or both, but it's unlikely to be a big problem.


`LinkedHashSet` has the same tuning parameters as `HashSet`, but iteration time is not affected by capacity. 
`TreeSet` has no tuning parameters.


## Special-Purpose `Set` Implementations _特殊用途的 `Set` 实现_


There are two special-purpose `Set` implementations — [`EnumSet`]() and [`CopyOnWriteArraySet`]().


`EnumSet` is a high-performance `Set` implementation for enum types. 
All of the members of an enum set must be of the same enum type. 
Internally, it is represented by a bit-vector, typically a single `long`. 
Enum sets support iteration over ranges of enum types. 
For example, given the enum declaration for the days of the week, you can iterate over the weekdays. 
The `EnumSet` class provides a static factory that makes it easy.


```text
for (Day d : EnumSet.range(Day.MONDAY, Day.FRIDAY))
    System.out.println(d);
```


Enum sets also provide a rich, typesafe replacement for traditional bit flags.


`EnumSet.of(Style.BOLD, Style.ITALIC)`


`CopyOnWriteArraySet` is a `Set` implementation backed up by a copy-on-write array. 
All mutative operations, such as `add`, `set`, and `remove`, are implemented by making a new copy of the array; no locking is ever required. 
Even iteration may safely proceed concurrently with element insertion and deletion. 
Unlike most `Set` implementations, the `add`, `remove`, and contains methods require time proportional to the size of the set. 
This implementation is _only_ appropriate for sets that are rarely modified but frequently iterated. 
It is well suited to maintaining event-handler lists that must prevent duplicates.
