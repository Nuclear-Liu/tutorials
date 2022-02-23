# Fields _字段_


A field is a class, interface, or enum with an associated value. 
Methods in the [`java.lang.reflect.Field`](https://docs.oracle.com/javase/8/docs/api/java/lang/reflect/Field.html) class can retrieve information about the field, such as its name, type, modifiers, and annotations. 
(The section [Examining Class Modifiers and Types](https://docs.oracle.com/javase/tutorial/reflect/class/classModifiers.html) in the [Classes](https://docs.oracle.com/javase/tutorial/reflect/class/index.html) lesson describes how to retrieve annotations.) 
There are also methods which enable dynamic access and modification of the value of the field. 
These tasks are covered in the following sections:


字段是具有关联值的类、接口或枚举。
[`java.lang.reflect.Field`](https://docs.oracle.com/javase/8/docs/api/java/lang/reflect/Field.html) 类中的方法可以检索有关字段的信息，例如其名称、类型、修饰符和注释。
（ [Classes](https://docs.oracle.com/javase/tutorial/reflect/class/index.html) 课程中的 [Examineing Class Modifiers and Types](https://docs.oracle.com/javase/tutorial/reflect/class/classModifiers.html) 部分描述了如何检索注解。）
还有一些方法可以动态访问和修改字段的值。
以下部分介绍了这些任务：


* [Obtaining Field Types](https://docs.oracle.com/javase/tutorial/reflect/member/fieldTypes.html) describes how to get the declared and generic types of a field

* [Obtaining Field Types](./fieldTypes.md) 描述如何获取字段的声明类型和泛型类型

* [Retrieving and Parsing Field Modifiers](https://docs.oracle.com/javase/tutorial/reflect/member/fieldModifiers.html) shows how to get portions of the field declaration such as `public` or `transient`

* [Retrieving and Parsing Field Modifiers](./fieldModifiers.md) 显示如何获取字段声明的部分，例如 `public` 或 `transient`

* [Getting and Setting Field Values](https://docs.oracle.com/javase/tutorial/reflect/member/fieldValues.html) illustrates how to access field values

* [Getting and Setting Field Values](./fieldValues.md) 说明如何访问字段值

* [Troubleshooting](https://docs.oracle.com/javase/tutorial/reflect/member/fieldTrouble.html) describes some common coding errors which may cause confusion

* [Troubleshooting](./fieldTrouble.md) 描述了一些可能导致混淆的常见编码错误


When writing an application such as a class browser, it might be useful to find out which fields belong to a particular class. 
A class's fields are identified by invoking [`Class.getFields()`](https://docs.oracle.com/javase/8/docs/api/java/lang/Class.html#getFields--). 
The [`getFields()`](https://docs.oracle.com/javase/8/docs/api/java/lang/Class.html#getFields--) method returns an array of [`Field`](https://docs.oracle.com/javase/8/docs/api/java/lang/reflect/Field.html) objects containing one object per accessible public field.


在编写诸如类浏览器之类的应用程序时，找出哪些字段属于特定类可能很有用。
类的字段通过调用 [`Class.getFields()`](https://docs.oracle.com/javase/8/docs/api/java/lang/Class.html#getFields--) 来标识。
[`getFields()`](https://docs.oracle.com/javase/8/docs/api/java/lang/Class.html#getFields--) 方法返回一个 [`Field`](https://docs.oracle.com/javase/8/docs/api/java/lang/reflect/Field.html) 对象数组，其中每个可访问的公共字段包含一个对象。


A public field is accessible if it is a member of either:


如果公共字段是以下任一成员，则它是可访问的：


* this class

* 这个 class

* a superclass of this class

* 这个类的超类

* an interface implemented by this class

* 此类实现的接口

* an interface extended from an interface implemented by this class

* 从此类实现的接口扩展的接口


A field may be a class (instance) field, such as [`java.io.Reader.lock`](https://docs.oracle.com/javase/8/docs/api/java/io/Reader.html#lock) , a static field, such as [`java.lang.Integer.MAX_VALUE`](https://docs.oracle.com/javase/8/docs/api/java/lang/Integer.html#MAX_VALUE) , or an enum constant, such as [`java.lang.Thread.State.WAITING`](https://docs.oracle.com/javase/8/docs/api/java/lang/Thread.State.html#WAITING).


字段可以是类（实例）字段，例如 [`java.io.Reader.lock`](https://docs.oracle.com/javase/8/docs/api/java/io/Reader.html#lock) ，静态字段，例如 [`java.lang.Integer.MAX_VALUE`](https://docs.oracle.com/javase/8/docs/api/java/lang/Integer.html#MAX_VALUE) ，或枚举常量，例如 [`java.lang.Thread.State.WAITING`](https://docs.oracle.com/javase/8/docs/api/java/lang/Thread.State.html#WAITING) 。
