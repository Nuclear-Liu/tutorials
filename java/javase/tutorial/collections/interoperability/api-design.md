# API Design _API 设计_


In this short but important section, you'll learn a few simple guidelines that will allow your API to interoperate seamlessly with all other APIs that follow these guidelines. 
In essence, these rules define what it takes to be a good "citizen" in the world of collections.


在这个简短但重要的部分中，您将学习一些简单的指南，这些指南将允许您的 API 与遵循这些指南的所有其他 API 无缝互操作。
从本质上讲，这些规则定义了在收藏界成为一个好的“公民”所需要具备的条件。


## Parameters _参数_


If your API contains a method that requires a collection on input, it is of paramount importance that you declare the relevant parameter type to be one of the collection [interface](https://docs.oracle.com/javase/tutorial/collections/interfaces/index.html) types. 
**Never** use an [implementation](https://docs.oracle.com/javase/tutorial/collections/implementations/index.html) type because this defeats the purpose of an interface-based Collections Framework, which is to allow collections to be manipulated without regard to implementation details.


如果您的 API 包含需要输入集合的方法，则将相关参数类型声明为集合 [interface](./../interfaces/index.md) 类型之一至关重要。
**永远**不要使用 [implementation](./../implementations/index.md) 类型，因为这违背了基于接口的集合框架的目的，即允许在不考虑实现细节的情况下操作集合。


Further, you should always use the least-specific type that makes sense. 
For example, don't require a [`List`](https://docs.oracle.com/javase/tutorial/collections/interfaces/list.html) or a [`Set`](https://docs.oracle.com/javase/tutorial/collections/interfaces/set.html) if a [`Collection`](https://docs.oracle.com/javase/tutorial/collections/interfaces/collection.html) would do. 
It's not that you should never require a `List` or a `Set` on input; it is correct to do so if a method depends on a property of one of these interfaces. 
For example, many of the algorithms provided by the Java platform require a `List` on input because they depend on the fact that lists are ordered. 
As a general rule, however, the best types to use on input are the most general: `Collection` and `Map`.


此外，您应该始终使用最不具体的有意义的类型。
例如，如果可以使用 [`Collection`](./../interfaces/collection.md) ，则不需要 [`List`](./../interfaces/list.md) 或 [`Set`](./../interfaces/set.md) 。
并不是说您永远不应该在输入时要求 `List` 或 `Set` ；如果方法依赖于这些接口之一的属性，那么这样做是正确的。
例如，Java 平台提供的许多算法在输入时需要一个 `List` ，因为它们依赖于列表是有序的这一事实。
然而，作为一般规则，用于输入的最佳类型是最通用的： `Collection` 和 `Map` 。


> **Caution**: Never define your own ad hoc `collection` class and require objects of this class on input. 
> By doing this, you'd lose all the [benefits provided by the Java Collections Framework](https://docs.oracle.com/javase/tutorial/collections/intro/index.html). 


> **警告**: 永远不要定义你自己的 ad hoc `collection` 类并且在输入时需要这个类的对象。
> 这样做，您将失去所有 [benefits provided by the Java Collections Framework](./../intro/index.md) 。


## Return Values _返回值_


You can afford to be much more flexible with return values than with input parameters. 
It's fine to return an object of any type that implements or extends one of the collection interfaces. 
This can be one of the interfaces or a special-purpose type that extends or implements one of these interfaces.


与输入参数相比，返回值可以更加灵活。
可以返回实现或扩展集合接口之一的任何类型的对象。
这可以是接口之一，也可以是扩展或实现这些接口之一的特殊用途类型。


For example, one could imagine an image-processing package, called `ImageList`, that returned objects of a new class that implements `List`. 
In addition to the `List` operations, `ImageList` could support any application-specific operations that seemed desirable. 
For example, it might provide an `indexImage` operation that returned an image containing thumbnail images of each graphic in the `ImageList`. 
It's critical to note that even if the API furnishes `ImageList` instances on output, it should accept arbitrary `Collection` (or perhaps `List`) instances on input.


例如，可以想象一个名为 `ImageList` 的图像处理包，它返回实现 `List` 的新类的对象。
除了 `List` 操作， `ImageList` 可以支持任何看起来需要的特定于应用程序的操作。
例如，它可能提供一个 `indexImage` 操作，该操作返回一个包含 `ImageList` 中每个图形的缩略图的图像。
需要注意的是，即使 API 在输出上提供了 `ImageList` 实例，它也应该在输入上接受任意 `Collection` （或者可能是 `List` ）实例。


In one sense, return values should have the opposite behavior of input parameters: 
It's best to return the most specific applicable collection interface rather than the most general. 
For example, if you're sure that you'll always return a `SortedMap`, you should give the relevant method the return type of `SortedMap` rather than `Map`. 
`SortedMap` instances are more time-consuming to build than ordinary `Map` instances and are also more powerful. 
Given that your module has already invested the time to build a `SortedMap`, it makes good sense to give the user access to its increased power. 
Furthermore, the user will be able to pass the returned object to methods that demand a `SortedMap`, as well as those that accept any `Map`.


从某种意义上说，返回值应该具有与输入参数相反的行为：
最好返回最具体的适用集合接口，而不是最通用的。
例如，如果你确定你总是会返回一个 `SortedMap` ，你应该给相关的方法提供 `SortedMap` 的返回类型而不是 `Map` 。
`SortedMap` 实例的构建比普通的 `Map` 实例更耗时，也更强大。
鉴于您的模块已经投入时间来构建 `SortedMap` ，让用户访问其增强的功能是很有意义的。
此外，用户将能够将返回的对象传递给需要 `SortedMap` 的方法，以及接受任何 `Map` 的方法。


## Legacy APIs _旧版 API_


There are currently plenty of APIs out there that define their own ad hoc collection types. 
While this is unfortunate, it's a fact of life, given that there was no Collections Framework in the first two major releases of the Java platform. 
Suppose you own one of these APIs; here's what you can do about it.


目前有很多 API 定义了它们自己的 ad hoc 集合类型。
虽然这很不幸，但这是不争的事实，因为在 Java 平台的前两个主要版本中没有 Collections Framework 。
假设您拥有这些 API 之一；这是你可以做的。


If possible, retrofit your legacy collection type to implement one of the standard collection interfaces. 
Then all the collections you return will interoperate smoothly with other collection-based APIs. 
If this is impossible (for example, because one or more of the preexisting type signatures conflict with the standard collection interfaces), define an adapter class that wraps one of your legacy collections objects, allowing it to function as a standard collection. 
(The `Adapter` class is an example of a _[custom implementation](https://docs.oracle.com/javase/tutorial/collections/custom-implementations/index.html)_.)


如果可能，改造您的旧集合类型以实现标准集合接口之一。
然后，您返回的所有集合将与其他基于集合的 API 顺利互操作。
如果这是不可能的（例如，因为一个或多个预先存在的类型签名与标准集合接口冲突），请定义一个适配器类来包装您的遗留集合对象之一，使其能够作为标准集合运行。
（ `Adapter` 类是 _[custom implementation](./../custom-implementations/index.md)_ 的一个示例。）


Retrofit your API with new calls that follow the input guidelines to accept objects of a standard collection interface, if possible. 
Such calls can coexist with the calls that take the legacy collection type. 
If this is impossible, provide a constructor or static factory for your legacy type that takes an object of one of the standard interfaces and returns a legacy collection containing the same elements (or mappings). 
Either of these approaches will allow users to pass arbitrary collections into your API.


如果可能，使用遵循输入准则的新调用来改进您的 API ，以接受标准集合接口的对象。
此类调用可以与采用旧集合类型的调用共存。
如果这是不可能的，请为您的遗留类型提供一个构造函数或静态工厂，它接受一个标准接口的对象并返回一个包含相同元素（或映射）的遗留集合。
这些方法中的任何一种都将允许用户将任意集合传递到您的 API 。
