# Lesson: Members _成员_


* [Fields](https://docs.oracle.com/javase/tutorial/reflect/member/field.html)

* [Fields](./field.md)

  * [Obtaining Field Types](https://docs.oracle.com/javase/tutorial/reflect/member/fieldTypes.html)

  * [Obtaining Field Types](./fieldTypes.md)

  * [Retrieving and Parsing Field Modifiers](https://docs.oracle.com/javase/tutorial/reflect/member/fieldModifiers.html)

  * [Retrieving and Parsing Field Modifiers](./fieldModifiers.md)

  * [Getting and Setting Field Values](https://docs.oracle.com/javase/tutorial/reflect/member/fieldValues.html)

  * [Getting and Setting Field Values](./fieldValues.md)

  * [Troubleshooting](https://docs.oracle.com/javase/tutorial/reflect/member/fieldTrouble.html)

  * [Troubleshooting](./fieldTrouble.md)

* [Methods](https://docs.oracle.com/javase/tutorial/reflect/member/method.html)

* [Methods](./method.md)

  * [Obtaining Method Type Information](https://docs.oracle.com/javase/tutorial/reflect/member/methodType.html)

  * [Obtaining Method Type Information](./methodType.md)

  * [Obtaining Names of Method Parameters](https://docs.oracle.com/javase/tutorial/reflect/member/methodparameterreflection.html)

  * [Obtaining Names of Method Parameters](./methodparameterreflection.md)

  * [Retrieving and Parsing Method Modifiers](https://docs.oracle.com/javase/tutorial/reflect/member/methodModifiers.html)

  * [Retrieving and Parsing Method Modifiers](./methodModifiers.md)

  * [Invoking Methods](https://docs.oracle.com/javase/tutorial/reflect/member/methodInvocation.html)

  * [Invoking Methods](./methodInvocation.md)

  * [Troubleshooting](https://docs.oracle.com/javase/tutorial/reflect/member/methodTrouble.html)

  * [Troubleshooting](./methodTrouble.md)

* [Constructors](https://docs.oracle.com/javase/tutorial/reflect/member/ctor.html)

* [Constructors](./ctor.md)

  * [Finding Constructors](https://docs.oracle.com/javase/tutorial/reflect/member/ctorLocation.html)

  * [Finding Constructors](./ctorLocation.md)

  * [Retrieving and Parsing Constructor Modifiers](https://docs.oracle.com/javase/tutorial/reflect/member/ctorModifiers.html)

  * [Retrieving and Parsing Constructor Modifiers](./ctorModifiers.md)

  * [Creating New Class Instances](https://docs.oracle.com/javase/tutorial/reflect/member/ctorInstance.html)

  * [Creating New Class Instances](./ctorInstance.md)

  * [Troubleshooting](https://docs.oracle.com/javase/tutorial/reflect/member/ctorTrouble.html)

  * [Troubleshooting](./ctorTrouble.md)


Reflection defines an interface [`java.lang.reflect.Member`](https://docs.oracle.com/javase/8/docs/api/java/lang/reflect/Member.html) which is implemented by [`java.lang.reflect.Field`](https://docs.oracle.com/javase/8/docs/api/java/lang/reflect/Field.html), [`java.lang.reflect.Method`](https://docs.oracle.com/javase/8/docs/api/java/lang/reflect/Method.html), and [`java.lang.reflect.Constructor`](https://docs.oracle.com/javase/8/docs/api/java/lang/reflect/Constructor.html) . 
These objects will be discussed in this lesson. 
For each member, the lesson will describe the associated APIs to retrieve declaration and type information, any operations unique to the member (for example, setting the value of a field or invoking a method), and commonly encountered errors. 
Each concept will be illustrated with code samples and related output which approximate some expected reflection uses.


反射定义了一个接口 [`java.lang.reflect.Member`](https://docs.oracle.com/javase/8/docs/api/java/lang/reflect/Member.html) ，它由 [`java.lang.reflect.Field`](https://docs.oracle.com/javase/8/docs/api/java/lang/reflect/Field.html) 、 [`java.lang.reflect.Method`](https://docs.oracle.com/javase/8/docs/api/java/lang/reflect/Method.html) 和[`java.lang.reflect.Constructor`](https://docs.oracle.com/javase/8/docs/api/java/lang/reflect/Constructor.html) 。
本课将讨论这些对象。
对于每个成员，本课将描述用于检索声明和类型信息的关联 API、成员特有的任何操作（例如，设置字段的值或调用方法）以及常见的错误。
每个概念都将通过代码示例和相关输出来说明，这些输出近似于一些预期的反射用途。


> **Note**:
> 
> According to [The Java Language Specification, Java SE 7 Edition](https://docs.oracle.com/javase/specs/jls/se7/html/index.html), the members of a class are the inherited components of the class body including fields, methods, nested classes, interfaces, and enumerated types. 
> Since constructors are not inherited, they are not members. 
> This differs from the implementing classes of [`java.lang.reflect.Member`](https://docs.oracle.com/javase/8/docs/api/java/lang/reflect/Member.html). 


> **注意**:
> 
> 根据 [The Java Language Specification, Java SE 7 Edition](https://docs.oracle.com/javase/specs/jls/se7/html/index.html) ，类的成员是类主体的继承组件，包括字段、方法、嵌套类、接口和枚举类型。
> 由于构造函数不是继承的，因此它们不是成员。
> 这与 [`java.lang.reflect.Member`](https://docs.oracle.com/javase/8/docs/api/java/lang/reflect/Member.html) 的实现类不同。


## Fields _字段_


Fields have a type and a value. 
The [`java.lang.reflect.Field`](https://docs.oracle.com/javase/8/docs/api/java/lang/reflect/Field.html) class provides methods for accessing type information and setting and getting values of a field on a given object.


字段具有类型和值。
[`java.lang.reflect.Field`](https://docs.oracle.com/javase/8/docs/api/java/lang/reflect/Field.html) 类提供了用于访问类型信息以及设置和获取给定对象上字段值的方法。


* [Obtaining Field Types](https://docs.oracle.com/javase/tutorial/reflect/member/fieldTypes.html) describes how to get the declared and generic types of a field

* [Obtaining Field Types](./fieldTypes.md) 描述如何获取字段的声明类型和泛型类型

* [Retrieving and Parsing Field Modifiers](https://docs.oracle.com/javase/tutorial/reflect/member/fieldModifiers.html) shows how to get portions of the field declaration such as `public` or `transient`

* [Retrieving and Parsing Field Modifiers](./fieldModifiers.md) 显示如何获取字段声明的部分，例如“public”或“transient”

* [Getting and Setting Field Values](https://docs.oracle.com/javase/tutorial/reflect/member/fieldValues.html) illustrates how to access field values

* [Getting and Setting Field Values](./fieldValues.md) 说明如何访问字段值

* [Troubleshooting](https://docs.oracle.com/javase/tutorial/reflect/member/fieldTrouble.html) describes some common coding errors which may cause confusion

* [Troubleshooting](./fieldTrouble.md) 描述了一些可能导致混淆的常见编码错误


## Methods _方法_


Methods have return values, parameters, and may throw exceptions. 
The [`java.lang.reflect.Method`](https://docs.oracle.com/javase/8/docs/api/java/lang/reflect/Method.html) class provides methods for obtaining the type information for the parameters and return value. 
It may also be used to invoke methods on a given object.


方法有返回值、参数，并且可能抛出异常。
[`java.lang.reflect.Method`](https://docs.oracle.com/javase/8/docs/api/java/lang/reflect/Method.html) 类提供了获取参数和返回值的类型信息的方法。
它也可以用于调用给定对象的方法。


* [Obtaining Method Type Information](https://docs.oracle.com/javase/tutorial/reflect/member/methodType.html) shows how to enumerate methods declared in a class and obtain type information

* [Obtaining Method Type Information](./methodType.md) 展示如何枚举类中声明的方法并获取类型信息

* [Obtaining Names of Method Parameters](https://docs.oracle.com/javase/tutorial/reflect/member/methodparameterreflection.html) shows how to retrieve names and other information of a method or constructor's parameters

* [Obtaining Names of Method Parameters](./methodparameterreflection.md) 显示如何检索方法或构造函数参数的名称和其他信息

* [Retrieving and Parsing Method Modifiers](https://docs.oracle.com/javase/tutorial/reflect/member/methodModifiers.html) describes how to access and decode modifiers and other information associated with the method

* [Retrieving and Parsing Method Modifiers](./methodModifiers.md) 描述如何访问和解码修饰符以及与方法相关的其他信息

* [Invoking Methods](https://docs.oracle.com/javase/tutorial/reflect/member/methodInvocation.html) illustrates how to execute a method and obtain its return value

* [Invoking Methods](./methodInvocation.md) 说明如何执行方法并获取其返回值

* [Troubleshooting](https://docs.oracle.com/javase/tutorial/reflect/member/methodTrouble.html) covers common errors encountered when finding or invoking methods

* [Troubleshooting](./methodTrouble.md) 涵盖查找或调用方法时遇到的常见错误


## Constructors _构造函数_


The Reflection APIs for constructors are defined in [`java.lang.reflect.Constructor`](https://docs.oracle.com/javase/8/docs/api/java/lang/reflect/Constructor.html) and are similar to those for methods, with two major exceptions: 
first, constructors have no return values; second, the invocation of a constructor creates a new instance of an object for a given class.


构造函数的反射 API 在 [`java.lang.reflect.Constructor`](https://docs.oracle.com/javase/8/docs/api/java/lang/reflect/Constructor.html) 中定义，类似于方法的反射 API，但有两个主要例外：
首先，构造函数没有返回值；其次，构造函数的调用为给定类创建对象的新实例。


* [Finding Constructors](https://docs.oracle.com/javase/tutorial/reflect/member/ctorLocation.html) illustrates how to retrieve constructors with specific parameters

* [Finding Constructors](./ctorLocation.md) 说明如何检索具有特定参数的构造函数

* [Retrieving and Parsing Constructor Modifiers](https://docs.oracle.com/javase/tutorial/reflect/member/ctorModifiers.html) shows how to obtain the modifiers of a constructor declaration and other information about the constructor

* [Retrieving and Parsing Constructor Modifiers](./ctorModifiers.md) 展示了如何获取构造函数声明的修饰符和有关构造函数的其他信息

* [Creating New Class Instances](https://docs.oracle.com/javase/tutorial/reflect/member/ctorInstance.html) shows how to instantiate an instance of an object by invoking its constructor

* [Creating New Class Instances](./ctorInstance.md) 展示如何通过调用其构造函数来实例化对象的实例

* [Troubleshooting](https://docs.oracle.com/javase/tutorial/reflect/member/ctorTrouble.html) describes common errors which may be encountered while finding or invoking constructors

* [Troubleshooting](./ctorTrouble.md) 描述查找或调用构造函数时可能遇到的常见错误
