# Trail: The Reflection API _反射 API_


## Uses of Reflection _反射的用途_


Reflection is commonly used by programs which require the ability to examine or modify the runtime behavior of applications running in the Java virtual machine. 
This is a relatively advanced feature and should be used only by developers who have a strong grasp of the fundamentals of the language. 
With that caveat in mind, reflection is a powerful technique and can enable applications to perform operations which would otherwise be impossible.


反射通常由需要能够检查或修改在 Java 虚拟机中运行的应用程序的运行时行为的程序使用。
这是一个相对高级的特性，只应该由对语言基础有深入了解的开发人员使用。
考虑到这一点，反射是一种强大的技术，可以使应用程序执行原本不可能的操作。


* **Extensibility Features**

  An application may make use of external, user-defined classes by creating instances of extensibility objects using their fully-qualified names.

* **可扩展性功能**

  应用程序可以通过使用其完全限定名称创建可扩展性对象的实例来使用外部的、用户定义的类。

* **Class Browsers and Visual Development Environments**

  A class browser needs to be able to enumerate the members of classes. 
  Visual development environments can benefit from making use of type information available in reflection to aid the developer in writing correct code.

* **类浏览器和可视化开发环境**

  类浏览器需要能够枚举类的成员。
  可视化开发环境可以受益于利用反射中可用的类型信息来帮助开发人员编写正确的代码。

* **Debuggers and Test Tools**

  Debuggers need to be able to examine private members on classes. 
  Test harnesses can make use of reflection to systematically call a discoverable set APIs defined on a class, to insure a high level of code coverage in a test suite.

* **调试器和测试工具**

  调试器需要能够检查类的私有成员。
  测试工具可以利用反射系统地调用定义在类上的可发现集 API，以确保测试套件中的高水平代码覆盖率。


## Drawbacks of Reflection _反射的缺点_


Reflection is powerful, but should not be used indiscriminately. 
If it is possible to perform an operation without using reflection, then it is preferable to avoid using it. 
The following concerns should be kept in mind when accessing code via reflection.


反射很强大，但不应该乱用。
如果可以在不使用反射的情况下执行操作，那么最好避免使用它。
通过反射访问代码时应牢记以下问题。


* **Performance Overhead**

  Because reflection involves types that are dynamically resolved, certain Java virtual machine optimizations can not be performed. 
  Consequently, reflective operations have slower performance than their non-reflective counterparts, and should be avoided in sections of code which are called frequently in performance-sensitive applications.

* **性能开销**

  由于反射涉及动态解析的类型，因此无法执行某些 Java 虚拟机优化。
  因此，反射操作的性能比它们的非反射对应物慢，并且应该避免在性能敏感的应用程序中经常调用的代码部分中。

* **Security Restrictions**

  Reflection requires a runtime permission which may not be present when running under a security manager. 
  This is in an important consideration for code which has to run in a restricted security context, such as in an Applet.

* **安全限制**

  反射需要在安全管理器下运行时可能不存在的运行时权限。
  对于必须在受限安全上下文中运行的代码（例如在 Applet 中），这是一个重要的考虑因素。

* **Exposure of Internals**

  Since reflection allows code to perform operations that would be illegal in non-reflective code, such as accessing `private` fields and methods, the use of reflection can result in unexpected side-effects, which may render code dysfunctional and may destroy portability. 
  Reflective code breaks abstractions and therefore may change behavior with upgrades of the platform.

* **内部暴露**

  由于反射允许代码执行在非反射代码中非法的操作，例如访问 `private` 字段和方法，因此使用反射可能会导致意想不到的副作用，这可能会导致代码功能失调并可能破坏可移植性。
  反射代码打破了抽象，因此可能会随着平台的升级而改变行为。


## Trail Lessons _课程_


This trail covers common uses of reflection for accessing and manipulating classes, fields, methods, and constructors. 
Each lesson contains code examples, tips, and troubleshooting information.


这篇文章涵盖了反射在访问和操作类、字段、方法和构造函数时的常见用途。
每节课都包含代码示例、提示和故障排除信息。


* [Classes](https://docs.oracle.com/javase/tutorial/reflect/class/index.html)

  This lesson shows the various ways to obtain a [`Class`](https://docs.oracle.com/javase/8/docs/api/java/lang/Class.html) object and use it to examine properties of a class, including its declaration and contents.

* [Classes](./class/index.md)

  本课展示了获取 [`Class`](https://docs.oracle.com/javase/8/docs/api/java/lang/Class.html) 对象并使用它来检查类的属性（包括其声明和内容）的各种方法。

* [Members](https://docs.oracle.com/javase/tutorial/reflect/member/index.html)

  This lesson describes how to use the Reflection APIs to find the fields, methods, and constructors of a class. 
  Examples are provided for setting and getting field values, invoking methods, and creating new instances of objects using specific constructors.

* [Members](./member/index.md)

  本课介绍如何使用反射 API 来查找类的字段、方法和构造函数。
  提供了设置和获取字段值、调用方法以及使用特定构造函数创建对象的新实例的示例。

* [Arrays and Enumerated Types](https://docs.oracle.com/javase/tutorial/reflect/special/index.html)

  This lesson introduces two special types of classes: arrays, which are generated at runtime, and `enum` types, which define unique named object instances. 
  Sample code shows how to retrieve the component type for an array and how to set and get fields with array or `enum` types.

* [Arrays and Enumerated Types](./special/index.md)

  本课介绍了两种特殊类型的类：在运行时生成的数组和定义唯一命名对象实例的 `enum` 类型。
  示例代码展示了如何检索数组的组件类型以及如何设置和获取数组或 `enum` 类型的字段。


> **Note: **
> 
> The examples in this trail are designed for experimenting with the Reflection APIs. 
> The handling of exceptions therefore is not the same as would be used in production code. 
> In particular, in production code it is not recommended to dump stack traces that are visible to the user.


> **注意: **
> 
> 本教程中的示例旨在用于试验反射 API。
> 因此，对异常的处理与在生产代码中使用的不同。
> 特别是，在生产代码中，不建议转储用户可见的堆栈跟踪。
