# Lesson: Classes


* [Retrieving Class Objects](https://docs.oracle.com/javase/tutorial/reflect/class/classNew.html)

* [Retrieving Class Objects](classNew.md)

* [Examining Class Modifiers and Types](https://docs.oracle.com/javase/tutorial/reflect/class/classModifiers.html)

* [Examining Class Modifiers and Types](classModifiers.md)

* [Discovering Class Members](https://docs.oracle.com/javase/tutorial/reflect/class/classMembers.html)

* [Discovering Class Members](classMembers.md)

* [Troubleshooting](https://docs.oracle.com/javase/tutorial/reflect/class/classTrouble.html)

* [Troubleshooting](classTrouble.md)


Every type is either a reference or a primitive. 
Classes, enums, and arrays (which all inherit from [`java.lang.Object`](https://docs.oracle.com/javase/8/docs/api/java/lang/Object.html)) as well as interfaces are all reference types. 
Examples of reference types include [`java.lang.String`](https://docs.oracle.com/javase/8/docs/api/java/lang/String.html), all of the wrapper classes for primitive types such as [`java.lang.Double`](https://docs.oracle.com/javase/8/docs/api/java/lang/Double.html), the interface [`java.io.Serializable`](https://docs.oracle.com/javase/8/docs/api/java/io/Serializable.html), and the enum [`javax.swing.SortOrder`](https://docs.oracle.com/javase/8/docs/api/javax/swing/SortOrder.html). 
There is a fixed set of primitive types: `boolean`, `byte`, `short`, `int`, `long`, `char`, `float`, and `double`.


每种类型要么是引用，要么是原始类型。
类、枚举和数组（都继承自 [`java.lang.Object`](https://docs.oracle.com/javase/8/docs/api/java/lang/Object.html) ）以及接口都是引用类型。
引用类型的示例包括 [`java.lang.String`](https://docs.oracle.com/javase/8/docs/api/java/lang/String.html) 、原始类型的所有包装类，例如 [`java.lang.Double`](https://docs.oracle.com/javase/8/docs/api/java/lang/Double.html) 、接口 [`java.io.Serializable`](https://docs.oracle.com/javase/8/docs/api/java/io/Serializable.html) 和枚举 [`javax.swing.SortOrder`](https://docs.oracle.com/javase/8/docs/api/javax/swing/SortOrder.html) 。
有一组固定的原始类型： `boolean` 、 `byte` 、 `short` 、 `int` 、 `long` 、 `char` 、 `float` 和 `double` 。


For every type of object, the Java virtual machine instantiates an immutable instance of [`java.lang.Class`](https://docs.oracle.com/javase/8/docs/api/java/lang/Class.html) which provides methods to examine the runtime properties of the object including its members and type information. 
[`Class`](https://docs.oracle.com/javase/8/docs/api/java/lang/Class.html) also provides the ability to create new classes and objects. 
Most importantly, it is the entry point for all of the Reflection APIs. 
This lesson covers the most commonly used reflection operations involving classes:


对于每种类型的对象，Java 虚拟机都会实例化一个不可变的 [`java.lang.Class`](https://docs.oracle.com/javase/8/docs/api/java/lang/Class.html) 实例，它提供了检查对象的运行时属性（包括其成员和类型信息）的方法。
[`Class`](https://docs.oracle.com/javase/8/docs/api/java/lang/Class.html) 还提供了创建新类和对象的能力。
最重要的是，它是所有反射 API 的入口点。
本课涵盖了涉及类的最常用的反射操作：


* [Retrieving Class Objects](https://docs.oracle.com/javase/tutorial/reflect/class/classNew.html) describes the ways to get a [`Class`](https://docs.oracle.com/javase/8/docs/api/java/lang/Class.html)

* [Retrieving Class Objects](classNew.md) 描述了获取 [`Class`](https://docs.oracle.com/javase/8/docs/api/java/lang/Class.html) 的方法

* [Examining Class Modifiers and Types](https://docs.oracle.com/javase/tutorial/reflect/class/classModifiers.html) shows how to access the class declaration information

* [Examining Class Modifiers and Types](classModifiers.md) 显示如何访问类声明信息

* [Discovering Class Members](https://docs.oracle.com/javase/tutorial/reflect/class/classMembers.html) illustrates how to list the constructors, fields, methods, and nested classes in a class

* [Discovering Class Members](classMembers.md) 说明如何列出类中的构造函数、字段、方法和嵌套类

* [Troubleshooting](https://docs.oracle.com/javase/tutorial/reflect/class/classTrouble.html) describes common errors encountered when using [`Class`](https://docs.oracle.com/javase/8/docs/api/java/lang/Class.html)

* [Troubleshooting](classTrouble.md) 描述使用 [`Class`](https://docs.oracle.com/javase/8/docs/api/java/lang/Class.html) 时遇到的常见错误
