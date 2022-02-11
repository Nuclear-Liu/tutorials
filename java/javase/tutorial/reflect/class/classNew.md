# Retrieving Class Objects _检索类对象_


The entry point for all reflection operations is [`java.lang.Class`](https://docs.oracle.com/javase/8/docs/api/java/lang/Class.html). 
With the exception of [`java.lang.reflect.ReflectPermission`](https://docs.oracle.com/javase/8/docs/api/java/lang/reflect/ReflectPermission.html), none of the classes in [`java.lang.reflect`](https://docs.oracle.com/javase/8/docs/api/java/lang/reflect/package-summary.html) have public constructors. 
To get to these classes, it is necessary to invoke appropriate methods on [`Class`](https://docs.oracle.com/javase/8/docs/api/java/lang/Class.html). 
There are several ways to get a [`Class`](https://docs.oracle.com/javase/8/docs/api/java/lang/Class.html) depending on whether the code has access to an object, the name of class, a type, or an existing [`Class`](https://docs.oracle.com/javase/8/docs/api/java/lang/Class.html).


所有反射操作的入口点是 [`java.lang.Class`](https://docs.oracle.com/javase/8/docs/api/java/lang/Class.html) 。
除了 [`java.lang.reflect.ReflectPermission`](https://docs.oracle.com/javase/8/docs/api/java/lang/reflect/ReflectPermission.html) 之外，[`java.lang.reflect`](https://docs.oracle.com/javase/8/docs/api/java/lang/reflect/package-summary.html) 中的所有类都没有公共构造函数。
要访问这些类，有必要在 [`Class`](https://docs.oracle.com/javase/8/docs/api/java/lang/Class.html) 上调用适当的方法。
根据代码是否可以访问对象、类的名称、类型或现有的 [`Class`](https://docs.oracle.com/javase/8/docs/api/java/lang/Class.html) ，有几种方法可以获取 [`Class`](https://docs.oracle.com/javase/8/docs/api/java/lang/Class.html) 。


## `Object.getClass()`


If an instance of an object is available, then the simplest way to get its [`Class`](https://docs.oracle.com/javase/8/docs/api/java/lang/Class.html) is to invoke [`Object.getClass()`](https://docs.oracle.com/javase/8/docs/api/java/lang/Object.html#getClass--). 
Of course, this only works for reference types which all inherit from [`Object`](https://docs.oracle.com/javase/8/docs/api/java/lang/Object.html). 
Some examples follow.


如果一个对象的实例可用，那么获取其 [`Class`](https://docs.oracle.com/javase/8/docs/api/java/lang/Class.html) 的最简单方法是调用 [`Object.getClass()`](https://docs.oracle.com/javase/8/docs/api/java/lang/Object.html#getClass--) 。
当然，这只适用于所有继承自 [`Object`](https://docs.oracle.com/javase/8/docs/api/java/lang/Object.html) 的引用类型。
下面是一些例子。


`Class c = "foo".getClass();`


Returns the [`Class`](https://docs.oracle.com/javase/8/docs/api/java/lang/Class.html) for [`String`](https://docs.oracle.com/javase/8/docs/api/java/lang/String.html)


返回 [`String`](https://docs.oracle.com/javase/8/docs/api/java/lang/Class.html) 的 [`Class`](https://docs.oracle.com/javase/8/docs/api/java/lang/String.html)


`Class c = System.console().getClass();`


There is a unique console associated with the virtual machine which is returned by the `static` method [`System.console()`](https://docs.oracle.com/javase/8/docs/api/java/lang/System.html#console--). 
The value returned by [`getClass()`](https://docs.oracle.com/javase/8/docs/api/java/lang/Object.html#getClass--) is the [`Class`](https://docs.oracle.com/javase/8/docs/api/java/lang/Class.html) corresponding to [`java.io.Console`](https://docs.oracle.com/javase/8/docs/api/java/io/Console.html).


`static` 方法 [`System.console()`](https://docs.oracle.com/javase/8/docs/api/java/lang/System.html#console--) 返回与虚拟机关联的唯一控制台。
[`getClass()`](https://docs.oracle.com/javase/8/docs/api/java/lang/Object.html#getClass--) 返回的值是 [`java.io.Console`](https://docs.oracle.com/javase/8/docs/api/java/io/Console.html) 对应的 [`Class`](https://docs.oracle.com/javase/8/docs/api/java/lang/Class.html) 。


```text
enum E { A, B }
Class c = A.getClass();
```


`A` is an instance of the enum `E`; thus `getClass() `returns the `Class` corresponding to the enumeration type `E`.


`A` 是枚举 `E` 的一个实例；因此 `getClass()` 返回与枚举类型 `E` 对应的 `Class` 。


```text
byte[] bytes = new byte[1024];
Class c = bytes.getClass();
```


Since arrays are [`Objects`](https://docs.oracle.com/javase/8/docs/api/java/lang/Object.html), it is also possible to invoke [`getClass()`](https://docs.oracle.com/javase/8/docs/api/java/lang/Object.html#getClass--) on an instance of an array. 
The returned [`Class`](https://docs.oracle.com/javase/8/docs/api/java/lang/Class.html) corresponds to an array with component type `byte`.


由于数组是 [`Objects`](https://docs.oracle.com/javase/8/docs/api/java/lang/Object.html) ，因此也可以在数组的实例上调用 [`getClass()`](https://docs.oracle.com/javase/8/docs/api/java/lang/Object.html#getClass--) 。
返回的 [`Class`](https://docs.oracle.com/javase/8/docs/api/java/lang/Class.html) 对应于组件类型为 `byte` 的数组。


```text
import java.util.HashSet;
import java.util.Set;

Set<String> s = new HashSet<String>();
Class c = s.getClass();
```


In this case, [`java.util.Set`](https://docs.oracle.com/javase/8/docs/api/java/util/Set.html) is an interface to an object of type [`java.util.HashSet`](https://docs.oracle.com/javase/8/docs/api/java/util/HashSet.html). 
The value returned by [`getClass()`](https://docs.oracle.com/javase/8/docs/api/java/lang/Object.html#getClass--) is the class corresponding to [`java.util.HashSet`](https://docs.oracle.com/javase/8/docs/api/java/util/HashSet.html).


在这种情况下，[`java.util.Set`](https://docs.oracle.com/javase/8/docs/api/java/util/Set.html) 是类型为 [`java.util.HashSet`](https://docs.oracle.com/javase/8/docs/api/java/util/HashSet.html) 的对象的接口。
[`getClass()`](https://docs.oracle.com/javase/8/docs/api/java/lang/Object.html#getClass--) 返回的值是 [`java.util.HashSet`](https://docs.oracle.com/javase/8/docs/api/java/util/HashSet.html) 对应的类。


## The `.class` Syntax


## `Class.forName()`


## TYPE Field for Primitive Type Wrappers


## Methods that Return Classes


