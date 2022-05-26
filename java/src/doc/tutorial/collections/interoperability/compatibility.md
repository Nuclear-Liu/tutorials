# Compatibility _兼容性_


The Java Collections Framework was designed to ensure complete interoperability between the core [collection interfaces](https://docs.oracle.com/javase/tutorial/collections/interfaces/index.html) and the types that were used to represent collections in the early versions of the Java platform: [`Vector`](https://docs.oracle.com/javase/8/docs/api/java/util/Vector.html), [`Hashtable`](https://docs.oracle.com/javase/8/docs/api/java/util/Hashtable.html), [array](https://docs.oracle.com/javase/tutorial/java/nutsandbolts/arrays.html), and [`Enumeration`](https://docs.oracle.com/javase/8/docs/api/java/util/Enumeration.html). 
In this section, you'll learn how to transform old collections to the Java Collections Framework collections and vice versa.


Java 集合框架旨在确保核心 [collection interfaces](../index.md) 与 Java 平台早期版本中用于表示集合的类型之间的完全互操作性：[`Vector`](https://docs.oracle.com/javase/8/docs/api/java/util/Vector.html) 、 [`Hashtable`](https://docs.oracle.com/javase/8/docs/api/java/util/Hashtable.html) 、 [array](../../java/nutsandbolts/arrays.md) 和 [`Enumeration`](https://docs.oracle.com/javase/8/docs/api/java/util/Enumeration.html) 。
在本节中，您将学习如何将旧集合转换为 Java 集合框架集合，反之亦然。


## Upward Compatibility _向上兼容_


Suppose that you're using an API that returns legacy collections in tandem with another API that requires objects implementing the collection interfaces. 
To make the two APIs interoperate smoothly, you'll have to transform the legacy collections into modern collections. 
Luckily, the Java Collections Framework makes this easy.


假设您使用的 API 与另一个需要实现集合接口的对象的 API 一起返回旧集合。
为了使这两个 API 顺利互操作，您必须将旧集合转换为现代集合。
幸运的是，Java 集合框架使这变得简单。


Suppose the old API returns an array of objects and the new API requires a `Collection`. 
The Collections Framework has a convenience implementation that allows an array of objects to be viewed as a `List`. 
You use [`Arrays.asList`](https://docs.oracle.com/javase/8/docs/api/java/util/Arrays.html#asList-T...-) to pass an array to any method requiring a `Collection` or a `List`.


假设旧 API 返回一个对象数组，而新 API 需要一个 `Collection` 。
Collections 框架有一个方便的实现，它允许将对象数组视为 `List` 。
您可以使用 [`Arrays.asList`](https://docs.oracle.com/javase/8/docs/api/java/util/Arrays.html#asList-T...-) 将数组传递给任何需要 `Collection` 或 `List` 的方法。


```text
Foo[] result = oldMethod(arg);
newMethod(Arrays.asList(result));
```


If the old API returns a `Vector` or a `Hashtable`, you have no work to do at all because `Vector` was retrofitted to implement the `List` interface, and `Hashtable` was retrofitted to implement `Map`. 
Therefore, a `Vector` may be passed directly to any method calling for a `Collection` or a `List`.


如果旧 API 返回一个 `Vector` 或 `Hashtable` ，你根本没有工作可做，因为 `Vector` 被改造为实现 `List` 接口，而 `Hashtable` 被改造为实现 `Map` 。
因此， `Vector` 可以直接传递给调用 `Collection` 或 `List` 的任何方法。


```text
Vector result = oldMethod(arg);
newMethod(result);
```


Similarly, a `Hashtable` may be passed directly to any method calling for a `Map`.


类似地， `Hashtable` 可以直接传递给任何调用 `Map` 的方法。


```text
Hashtable result = oldMethod(arg);
newMethod(result);
```


Less frequently, an API may return an `Enumeration` that represents a collection of objects. 
The `Collections.list` method translates an `Enumeration` into a `Collection`.


不太常见的是，API 可能会返回一个表示对象集合的 `Enumeration` 。
`Collections.list` 方法将 `Enumeration` 转换为 `Collection` 。


```text
Enumeration e = oldMethod(arg);
newMethod(Collections.list(e));
```


## Backward Compatibility _向后兼容性_


Suppose you're using an API that returns modern collections in tandem with another API that requires you to pass in legacy collections. 
To make the two APIs interoperate smoothly, you have to transform modern collections into old collections. 
Again, the Java Collections Framework makes this easy.


假设您使用的 API 与另一个要求您传入旧集合的 API 一起返回现代集合。
为了使这两个 API 顺利互操作，您必须将现代集合转换为旧集合。
同样，Java 集合框架使这变得容易。


Suppose the new API returns a `Collection`, and the old API requires an array of `Object`. 
As you're probably aware, the `Collection` interface contains a `toArray` method designed expressly for this situation.


假设新 API 返回一个 `Collection` ，而旧 API 需要一个 `Object` 数组。
你可能知道， `Collection` 接口包含一个专门为这种情况设计的 `toArray` 方法。


```text
Collection c = newMethod();
oldMethod(c.toArray());
```


What if the old API requires an array of `String` (or another type) instead of an array of `Object`? 
You just use the other form of `toArray` — the one that takes an array on input.


如果旧 API 需要 `String` （或其他类型）数组而不是 `Object` 数组怎么办？
你只需要使用另一种形式的 `toArray` —— 接受一个数组作为输入。


```text
Collection c = newMethod();
oldMethod((String[]) c.toArray(new String[0]));
```


If the old API requires a `Vector`, the standard collection constructor comes in handy.


如果旧 API 需要 `Vector` ，标准集合构造函数就派上用场了。


```text
Collection c = newMethod();
oldMethod(new Vector(c));
```


The case where the old API requires a `Hashtable` is handled analogously.


旧 API 需要 `Hashtable` 的情况类似处理。


```text
Map m = newMethod();
oldMethod(new Hashtable(m));
```


Finally, what do you do if the old API requires an `Enumeration`? 
This case isn't common, but it does happen from time to time, and the [`Collections.enumeration`](https://docs.oracle.com/javase/8/docs/api/java/util/Collections.html#enumeration-java.util.Collection-) method was provided to handle it. 
This is a static factory method that takes a `Collection` and returns an `Enumeration` over the elements of the `Collection`.


最后，如果旧 API 需要 `Enumeration` ，你会怎么做？
这种情况并不常见，但它确实时有发生，并且提供了 [`Collections.enumeration`](https://docs.oracle.com/javase/8/docs/api/java/util/Collections.html#enumeration-java.util.Collection-) 方法来处理它。
这是一个静态工厂方法，它接受一个 `Collection` 并在 `Collection` 的元素上返回一个 `Enumeration` 。


```text
Collection c = newMethod();
oldMethod(Collections.enumeration(c));
```
