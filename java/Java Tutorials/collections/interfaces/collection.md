# The Collection Interface


A [`Collection`](https://docs.oracle.com/javase/8/docs/api/java/util/Collection.html) represents a group of objects known as its elements. 
The `Collection` interface is used to pass around collections of objects where maximum generality is desired. 
For example, by convention all general-purpose collection implementations have a constructor that takes a `Collection` argument. 
This constructor, known as a _conversion_ constructor, initializes the new collection to contain all of the elements in the specified collection, whatever the given collection's subinterface or implementation type. 
In other words, it allows you to convert the collection's type.


[`Collection`](https://docs.oracle.com/javase/8/docs/api/java/util/Collection.html) 表示一组称为其元素的对象。
`Collection` 接口用于传递需要最大通用性的对象集合。
例如，按照惯例，所有通用集合实现都有一个构造函数，它接受一个 `Collection` 参数。
此构造函数称为 _conversion_ 构造函数，它初始化新集合以包含指定集合中的所有元素，无论给定集合的子接口或实现类型如何。
换句话说，它允许您转换集合的类型。


Suppose, for example, that you have a `Collection<String> c`, which may be a `List`, a `Set`, or another kind of `Collection`. 
This idiom creates a new `ArrayList` (an implementation of the `List` interface), initially containing all the elements in `c`.


例如，假设您有一个 `Collection<String> c` ，它可能是一个 `List`、一个 `Set` 或另一种类型的 `Collection` 。
这个习惯用法创建了一个新的 `ArrayList` （ `List` 接口的实现），最初包含 `c` 中的所有元素


`List<String> list = new ArrayList<String>(c);`


Or — if you are using JDK 7 or later — you can use the diamond operator: 


或者——如果你使用的是 JDK 7 或更高版本——你可以使用菱形运算符：


`List<String> list = new ArrayList<>(c);`


The `Collection` interface contains methods that perform basic operations, such as `int size()`, `boolean isEmpty()`, `boolean contains(Object element)`, `boolean add(E element)`, `boolean remove(Object element)`, and `Iterator<E> iterator()`. 


`Collection` 接口包含执行基本操作的方法，例如 `int size()`、 `boolean isEmpty()`、 `boolean contains(Object element)`、 `boolean add(E element)`、 `boolean remove(对象元素)` 和 `Iterator<E> iterator()`。


It also contains methods that operate on entire collections, such as `boolean containsAll(Collection<?> c)`, `boolean addAll(Collection<? extends E> c)`, `boolean removeAll(Collection<?> c)`, `boolean retainAll(Collection<?> c)`, and `void clear()`.


它还包含对整个集合进行操作的方法，例如 `boolean containsAll(Collection<?> c)`、`boolean addAll(Collection<? extends E> c)`、`boolean removeAll(Collection<?> c)`、 `boolean retainAll(Collection<?> c)` 和 `void clear()`。


Additional methods for array operations (such as `Object[] toArray()` and `<T> T[] toArray(T[] a)`) exist as well.


数组操作的其他方法（例如 `Object[] toArray()` 和 `<T> T[] toArray(T[] a)` ）也存在。


In JDK 8 and later, the `Collection` interface also exposes methods `Stream<E> stream()` and `Stream<E> parallelStream()`, for obtaining sequential or parallel streams from the underlying collection. 
(See the lesson entitled [Aggregate Operations](https://docs.oracle.com/javase/tutorial/collections/streams/index.html) for more information about using streams.)


在 JDK 8 及更高版本中， `Collection` 接口还公开了 `Stream<E> stream()` 和 `Stream<E> parallelStream()` 方法，用于从底层集合中获取顺序或并行流
（有关使用流的更多信息，请参阅标题为 [Aggregate Operations](./../streams/index.md) 的课程。）


The `Collection` interface does about what you'd expect given that a `Collection` represents a group of objects. 
It has methods that tell you how many elements are in the collection (`size`, `isEmpty`), methods that check whether a given object is in the collection (`contains`), methods that add and remove an element from the collection (`add`, `remove`), and methods that provide an iterator over the collection (`iterator`).


`Collection` 接口可以满足您的期望，因为`Collection` 表示一组对象。
它有告诉你集合中有多少元素的方法（ `size`、 `isEmpty` ）、检查给定对象是否在集合中的方法（ `contains` ）、从集合中添加和删除元素的方法（ `add`、 `remove` ），以及在集合上提供迭代器的方法（ `iterator` ）。


The `add` method is defined generally enough so that it makes sense for collections that allow duplicates as well as those that don't. 
It guarantees that the `Collection` will contain the specified element after the call completes, and returns `true` if the `Collection` changes as a result of the call. 
Similarly, the `remove` method is designed to remove a single instance of the specified element from the `Collection`, assuming that it contains the element to start with, and to return `true` if the `Collection` was modified as a result.


`add` 方法定义得足够普遍，因此它对于允许重复的集合以及不允许重复的集合都有意义。
它保证在调用完成后 `Collection` 将包含指定的元素，如果 `Collection` 因调用而改变，则返回 `true` 。
类似地， `remove` 方法旨在从 `Collection` 中移除指定元素的单个实例，假设它包含要开始的元素，如果 `Collection` 结果被修改，则返回 `true` .


## Traversing Collections _遍历集合_


There are three ways to traverse collections: (1) using aggregate operations (2) with the `for-each` construct and (3) by using `Iterator`s.


有三种遍历集合的方法：(1) 使用聚合操作 (2) 使用 `for-each` 构造和 (3) 使用 `Iterator`。


### Aggregate Operations _聚合操作_


In JDK 8 and later, the preferred method of iterating over a collection is to obtain a stream and perform aggregate operations on it. 
Aggregate operations are often used in conjunction with lambda expressions to make programming more expressive, using less lines of code. 
The following code sequentially iterates through a collection of shapes and prints out the red objects:


在 JDK 8 及更高版本中，迭代集合的首选方法是获取流并对其执行聚合操作。
聚合操作通常与 lambda 表达式结合使用，以使用更少的代码行使编程更具表现力。
以下代码依次遍历一组形状并打印出红色对象：


```
myShapesCollection.stream()
.filter(e -> e.getColor() == Color.RED)
.forEach(e -> System.out.println(e.getName()));
```


Likewise, you could easily request a parallel stream, which might make sense if the collection is large enough and your computer has enough cores:


同样，您可以轻松请求并行流，如果集合足够大并且您的计算机有足够的内核，这可能是有意义的：


```
myShapesCollection.parallelStream()
.filter(e -> e.getColor() == Color.RED)
.forEach(e -> System.out.println(e.getName()));
```


There are many different ways to collect data with this API. 
For example, you might want to convert the elements of a `Collection` to `String` objects, then join them, separated by commas:


有许多不同的方法可以使用此 API 收集数据。
例如，您可能希望将 `Collection` 的元素转换为 `String` 对象，然后将它们连接起来，用逗号分隔：


```
String joined = elements.stream()
.map(Object::toString)
.collect(Collectors.joining(", "));
```


Or perhaps sum the salaries of all employees:


或者也许总结所有员工的工资：


```
int total = employees.stream()
.collect(Collectors.summingInt(Employee::getSalary)));
```


These are but a few examples of what you can do with streams and aggregate operations. 
For more information and examples, see the lesson entitled [Aggregate Operations](https://docs.oracle.com/javase/tutorial/collections/streams/index.html).


这些只是您可以使用流和聚合操作执行的操作的几个示例。
有关更多信息和示例，请参阅标题为 [Aggregate Operations](./../streams/index.md) 的课程。


The Collections framework has always provided a number of so-called "bulk operations" as part of its API. 
These include methods that operate on entire collections, such as `containsAll`, `addAll`, `removeAll`, etc. 
Do not confuse those methods with the aggregate operations that were introduced in JDK 8. 
The key difference between the new aggregate operations and the existing bulk operations (`containsAll`, `addAll`, etc.) is that the old versions are all _mutative_, meaning that they all modify the underlying collection. 
In contrast, the new aggregate operations do not modify the underlying collection. 
When using the new aggregate operations and lambda expressions, you must take care to avoid mutation so as not to introduce problems in the future, should your code be run later from a parallel stream.


Collections 框架一直提供许多所谓的“批量操作”作为其 API 的一部分。
这些包括对整个集合进行操作的方法，例如 `containsAll`、 `addAll`、 `removeAll` 等。
不要将这些方法与 JDK 8 中引入的聚合操作混淆。
新聚合操作与现有批量操作（ `containsAll`、 `addAll` 等）的主要区别在于，旧版本都是 _可变的_，这意味着它们都修改了底层集合。
相比之下，新的聚合操作不会修改底层集合。
使用新的聚合操作和 lambda 表达式时，如果您的代码稍后从并行流运行，您必须小心避免突变，以免在将来引入问题。


### for-each Construct _for-each 构造_


The `for-each` construct allows you to concisely traverse a collection or array using a `for` loop — see [The for Statement](https://docs.oracle.com/javase/tutorial/java/nutsandbolts/for.html). 
The following code uses the `for-each` construct to print out each element of a collection on a separate line.


`for-each` 构造允许您使用 `for` 循环简洁地遍历集合或数组 —— 参见 [The for Statement](./../../java/nutsandbolts/for.md)。
以下代码使用 `for-each` 构造在单独的行上打印出集合的每个元素。


```
for (Object o : collection)
    System.out.println(o);
```


### Iterators _迭代器_


An [`Iterator`](https://docs.oracle.com/javase/8/docs/api/java/util/Iterator.html) is an object that enables you to traverse through a collection and to remove elements from the collection selectively, if desired. 
You get an `Iterator` for a collection by calling its `iterator` method. 
The following is the `Iterator` interface.


[`Iterator`](https://docs.oracle.com/javase/8/docs/api/java/util/Iterator.html) 是一个对象，它使您能够遍历集合并在需要时有选择地从集合中删除元素。
您可以通过调用集合的 `iterator` 方法获得一个集合的 `Iterator` 。
下面是 `Iterator` 接口。


```java
public interface Iterator<E> {
    boolean hasNext();
    E next();
    void remove(); //optional
}
```


The `hasNext` method returns `true` if the iteration has more elements, and the `next` method returns the next element in the iteration. 
The `remove` method removes the last element that was returned by `next` from the underlying `Collection`. 
The `remove` method may be called only once per call to `next` and throws an exception if this rule is violated.


如果迭代有更多元素， `hasNext` 方法返回 `true`， `next` 方法返回迭代中的下一个元素。
`remove` 方法从底层 `Collection` 中移除 `next` 返回的最后一个元素。
`remove` 方法在每次调用 `next` 时只能调用一次，如果违反此规则，则抛出异常。


Note that `Iterator.remove` is the _only_ safe way to modify a collection during iteration; the behavior is unspecified if the underlying collection is modified in any other way while the iteration is in progress.


请注意， `Iterator.remove` 是在迭代期间修改集合的 _唯一_ 安全方式；如果在迭代过程中以任何其他方式修改了基础集合，则行为是未指定的。


Use `Iterator` instead of the `for-each` construct when you need to:


当您需要时，使用 `Iterator` 而不是 `for-each` 构造：


* Remove the current element. 
  The `for-each` construct hides the iterator, so you cannot call `remove`. 
  Therefore, the `for-each` construct is not usable for filtering.

* 删除当前元素。
  `for-each` 结构隐藏了迭代器，所以你不能调用 `remove` 。
  因此， `for-each` 构造不能用于过滤。

* Iterate over multiple collections in parallel.

* 并行迭代多个集合。


The following method shows you how to use an `Iterator` to filter an arbitrary `Collection` — that is, traverse the collection removing specific elements.


以下方法向您展示了如何使用 `Iterator` 来过滤任意的 `Collection` —— 即遍历集合以删除特定元素。


```
static void filter(Collection<?> c) {
    for (Iterator<?> it = c.iterator(); it.hasNext(); )
        if (!cond(it.next()))
            it.remove();
}
```


This simple piece of code is polymorphic, which means that it works for any `Collection` regardless of implementation. 
This example demonstrates how easy it is to write a polymorphic algorithm using the Java Collections Framework.


这段简单的代码是多态的，这意味着它适用于任何 `Collection` ，而不管实现如何。
此示例演示了使用 Java 集合框架编写多态算法是多么容易。


## Collection Interface Bulk Operations _集合接口批量操作_


_Bulk_ operations perform an operation on an entire `Collection`. 
You could implement these shorthand operations using the basic operations, though in most cases such implementations would be less efficient. 
The following are the bulk operations:


*批量*操作对整个 `Collection` 执行操作。
您可以使用基本操作来实现这些速记操作，但在大多数情况下，此类实现的效率较低。
以下是批量操作：


* `containsAll` — returns `true` if the target `Collection` contains all of the elements in the specified `Collection`.

* `containsAll` — 如果目标`Collection` 包含指定`Collection` 中的所有元素，则返回`true`。

* `addAll` — adds all of the elements in the specified `Collection` to the target `Collection`.

* `addAll` — 将指定 `Collection` 中的所有元素添加到目标 `Collection` 。

* `removeAll` — removes from the target `Collection` all of its elements that are also contained in the specified `Collection`.

* `removeAll` — 从目标 `Collection` 中删除所有也包含在指定 `Collection` 中的元素。

* `retainAll` — removes from the target `Collection` all its elements that are not also contained in the specified `Collection`. 
  That is, it retains only those elements in the target `Collection` that are also contained in the specified `Collection`.

* `retainAll` — 从目标 `Collection` 中删除所有未包含在指定 `Collection` 中的元素。
  也就是说，它只保留目标 `Collection` 中那些也包含在指定 `Collection` 中的元素。

* `clear` — removes all elements from the `Collection`.

* `clear` — 从 `Collection` 中删除所有元素。


The `addAll`, `removeAll`, and `retainAll` methods all return `true` if the target `Collection` was modified in the process of executing the operation.


如果在执行操作的过程中修改了目标 `Collection`，`addAll`、 `removeAll` 和 `retainAll` 方法都返回 `true` 。


As a simple example of the power of bulk operations, consider the following idiom to remove all instances of a specified element, `e`, from a `Collection`, `c`.


作为批量操作威力的一个简单示例，请考虑以下习惯用法，以从 `Collection`、`c` 中删除指定元素 `e` 的所有实例。


`c.removeAll(Collections.singleton(e));`


More specifically, suppose you want to remove all of the `null` elements from a `Collection`.


更具体地说，假设您想从 `Collection` 中删除所有的 `null` 元素。


`c.removeAll(Collections.singleton(null));`


This idiom uses `Collections.singleton`, which is a static factory method that returns an immutable `Set` containing only the specified element. 


这个习惯用法使用 `Collections.singleton` ，它是一个静态工厂方法，它返回一个不可变的 `Set` ，它只包含指定的元素。


## Collection Interface Array Operations _集合接口数组操作_


The `toArray` methods are provided as a bridge between collections and older APIs that expect arrays on input. 
The array operations allow the contents of a `Collection` to be translated into an array. 
The simple form with no arguments creates a new array of `Object`. 
The more complex form allows the caller to provide an array or to choose the runtime type of the output array.


`toArray` 方法被提供作为集合和期望输入数组的旧 API 之间的桥梁。
数组操作允许将 `Collection` 的内容转换为数组。
没有参数的简单形式创建了一个新的 `Object` 数组。
更复杂的形式允许调用者提供一个数组或选择输出数组的运行时类型。


For example, suppose that `c` is a `Collection`. 
The following snippet dumps the contents of `c` into a newly allocated array of `Object` whose length is identical to the number of elements in `c`.


例如，假设 `c` 是一个 `Collection` 。
以下代码段将 `c` 的内容转储到新分配的 `Object` 数组中，该数组的长度与 `c` 中的元素数量相同。


`Object[] a = c.toArray();`


Suppose that `c` is known to contain only strings (perhaps because `c` is of type `Collection<String>`). 
The following snippet dumps the contents of `c` into a newly allocated array of `String` whose length is identical to the number of elements in `c`.


假设已知 `c` 只包含字符串（可能是因为 `c` 的类型是 `Collection<String>` ）。
以下代码段将 `c` 的内容转储到新分配的 `String` 数组中，该数组的长度与 `c` 中的元素数量相同。


`String[] a = c.toArray(new String[0]);`
