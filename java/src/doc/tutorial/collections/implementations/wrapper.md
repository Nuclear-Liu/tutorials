# Wrapper Implementations


Wrapper implementations delegate all their real work to a specified collection but add extra functionality on top of what this collection offers. 
For design pattern fans, this is an example of the decorator pattern. 
Although it may seem a bit exotic, it's really pretty straightforward.


Wrapper 实现将其所有实际工作委托给指定的集合，但在此集合提供的之上添加额外的功能。
对于设计模式爱好者来说，这是装饰器模式的一个例子。
虽然它可能看起来有点异国情调，但它确实非常简单。


These implementations are anonymous; rather than providing a public class, the library provides a static factory method. 
All these implementations are found in the [`Collections`](https://docs.oracle.com/javase/8/docs/api/java/util/Collections.html) class, which consists solely of static methods.


这些实现是匿名的；该库不提供公共类，而是提供静态工厂方法。
所有这些实现都可以在 [`Collections`](https://docs.oracle.com/javase/8/docs/api/java/util/Collections.html) 类中找到，该类仅由静态方法组成。


## Synchronization Wrappers _同步包装器_


The synchronization wrappers add automatic synchronization (thread-safety) to an arbitrary collection. 
Each of the six core collection interfaces — [`Collection`](https://docs.oracle.com/javase/8/docs/api/java/util/Collection.html), [`Set`](https://docs.oracle.com/javase/8/docs/api/java/util/Set.html), [`List`](https://docs.oracle.com/javase/8/docs/api/java/util/List.html), [`Map`](https://docs.oracle.com/javase/8/docs/api/java/util/Map.html), [`SortedSet`](https://docs.oracle.com/javase/8/docs/api/java/util/SortedSet.html), and [`SortedMap`](https://docs.oracle.com/javase/8/docs/api/java/util/SortedMap.html) — has one static factory method.


同步包装器将自动同步（线程安全）添加到任意集合。
六个核心集合接口中的每一个 - [`Collection`](https://docs.oracle.com/javase/8/docs/api/java/util/Collection.html) 、 [`Set`](https://docs.oracle.com/javase/8/docs/api/java/util/Set.html) 、 [`List`](https://docs.oracle.com/javase/8/docs/api/java/util/List.html) 、 [`Map`](https://docs.oracle.com/javase/8/docs/api/java/util/Map.html) 、 [`SortedSet`](https://docs.oracle.com/javase/8/docs/api/java/util/SortedSet.html) 和 [ `SortedMap`](https://docs.oracle.com/javase/8/docs/api/java/util/SortedMap.html) — 有一个静态工厂方法。


```text
public static <T> Collection<T> synchronizedCollection(Collection<T> c);
public static <T> Set<T> synchronizedSet(Set<T> s);
public static <T> List<T> synchronizedList(List<T> list);
public static <K,V> Map<K,V> synchronizedMap(Map<K,V> m);
public static <T> SortedSet<T> synchronizedSortedSet(SortedSet<T> s);
public static <K,V> SortedMap<K,V> synchronizedSortedMap(SortedMap<K,V> m);
```


Each of these methods returns a synchronized (thread-safe) `Collection` backed up by the specified collection. 
To guarantee serial access, all access to the backing collection must be accomplished through the returned collection. 
The easy way to guarantee this is not to keep a reference to the backing collection. 
Create the synchronized collection with the following trick.


这些方法中的每一个都返回由指定集合备份的同步（线程安全） `Collection` 。
为了保证串行访问，对后备集合的所有访问都必须通过返回的集合来完成。
保证这一点的简单方法是不保留对支持集合的引用。
使用以下技巧创建同步集合。


`List<Type> list = Collections.synchronizedList(new ArrayList<Type>());`


A collection created in this fashion is every bit as thread-safe as a normally synchronized collection, such as a [`Vector`](https://docs.oracle.com/javase/8/docs/api/java/util/Vector.html).


以这种方式创建的集合与通常同步的集合一样线程安全，例如 [`Vector`](https://docs.oracle.com/javase/8/docs/api/java/util/Vector.html) 。


In the face of concurrent access, it is imperative that the user manually synchronize on the returned collection when iterating over it. 
The reason is that iteration is accomplished via multiple calls into the collection, which must be composed into a single atomic operation. 
The following is the idiom to iterate over a wrapper-synchronized collection.


面对并发访问，用户必须在迭代时手动同步返回的集合。
原因是迭代是通过对集合的多次调用来完成的，集合必须组合成一个原子操作。
以下是迭代包装器同步集合的习惯用法。


```text
Collection<Type> c = Collections.synchronizedCollection(myCollection);
synchronized(c) {
    for (Type e : c)
        foo(e);
}
```


If an explicit iterator is used, the `iterator` method must be called from within the `synchronized` block. 
Failure to follow this advice may result in nondeterministic behavior. 
The idiom for iterating over a `Collection` view of a synchronized `Map` is similar. 
It is imperative that the user synchronize on the synchronized `Map` when iterating over any of its `Collection` views rather than synchronizing on the `Collection` view itself, as shown in the following example.


如果使用显式迭代器，则必须从 `synchronized` 块中调用 `iterator` 方法。
不遵循此建议可能会导致不确定的行为。
迭代同步 `Map` 的 `Collection` 视图的习惯用法是相似的。
当迭代其任何 `Collection` 视图时，用户必须在同步的 `Map` 上同步，而不是在 `Collection` 视图本身上同步，如下例所示。


```text
Map<KeyType, ValType> m = Collections.synchronizedMap(new HashMap<KeyType, ValType>());
    ...
Set<KeyType> s = m.keySet();
    ...
// Synchronizing on m, not s!
synchronized(m) {
    while (KeyType k : s)
        foo(k);
}
```


One minor downside of using wrapper implementations is that you do not have the ability to execute any _noninterface_ operations of a wrapped implementation. 
So, for instance, in the preceding `List` example, you cannot call `ArrayList`'s `ensureCapacity` operation on the wrapped `ArrayList`.


使用包装器实现的一个小缺点是您无法执行包装器实现的任何 _noninterface_ 操作。
因此，例如，在前面的 `List` 示例中，您不能对包装的 `ArrayList` 调用 `ArrayList` 的 `ensureCapacity` 操作。


## Unmodifiable Wrappers _不可修改的包装器_


Unlike synchronization wrappers, which add functionality to the wrapped collection, the unmodifiable wrappers take functionality away. 
In particular, they take away the ability to modify the collection by intercepting all the operations that would modify the collection and throwing an `UnsupportedOperationException`. 
Unmodifiable wrappers have two main uses, as follows:


与将功能添加到包装集合的同步包装器不同，不可修改的包装器带走了功能。
特别是，它们通过拦截所有会修改集合的操作并抛出 `UnsupportedOperationException` 来剥夺修改集合的能力。
不可修改的包装器有两个主要用途，如下所示：


* To make a collection immutable once it has been built. 
  In this case, it's good practice not to maintain a reference to the backing collection. 
  This absolutely guarantees immutability.

* 使集合在构建后不可变。
  在这种情况下，最好不要维护对支持集合的引用。
  这绝对保证了不变性。

* To allow certain clients read-only access to your data structures. 
  You keep a reference to the backing collection but hand out a reference to the wrapper. 
  In this way, clients can look but not modify, while you maintain full access.

* 允许某些客户端只读访问您的数据结构。
  您保留对支持集合的引用，但分发对包装器的引用。
  通过这种方式，客户可以查看但不能修改，同时您保持完全访问权限。


Like synchronization wrappers, each of the six core `Collection` interfaces has one static factory method.


与同步包装器一样，六个核心 `Collection` 接口中的每一个都有一个静态工厂方法。


```text
public static <T> Collection<T> unmodifiableCollection(Collection<? extends T> c);
public static <T> Set<T> unmodifiableSet(Set<? extends T> s);
public static <T> List<T> unmodifiableList(List<? extends T> list);
public static <K,V> Map<K, V> unmodifiableMap(Map<? extends K, ? extends V> m);
public static <T> SortedSet<T> unmodifiableSortedSet(SortedSet<? extends T> s);
public static <K,V> SortedMap<K, V> unmodifiableSortedMap(SortedMap<K, ? extends V> m);
```


## Checked Interface Wrappers _检查接口包装器_


The `Collections.checked` _interface_ wrappers are provided for use with generic collections. 
These implementations return a _dynamically_ type-safe view of the specified collection, which throws a `ClassCastException` if a client attempts to add an element of the wrong type. 
The generics mechanism in the language provides compile-time (static) type-checking, but it is possible to defeat this mechanism. 
Dynamically type-safe views eliminate this possibility entirely.


`Collections.checked` _interface_ 包装器提供用于通用集合。
这些实现返回指定集合的 _dynamically_ 类型安全视图，如果客户端尝试添加错误类型的元素，则会抛出 `ClassCastException` 。
语言中的泛型机制提供编译时（静态）类型检查，但有可能破坏这种机制。
动态类型安全视图完全消除了这种可能性。
