# Discovering Class Members _发现 Class 成员_


There are two categories of methods provided in [`Class`](https://docs.oracle.com/javase/8/docs/api/java/lang/Class.html) for accessing fields, methods, and constructors: 
methods which enumerate these members and methods which search for particular members. 
Also there are distinct methods for accessing members declared directly on the class versus methods which search the superinterfaces and superclasses for inherited members. 
The following tables provide a summary of all the member-locating methods and their characteristics.


[`Class`](https://docs.oracle.com/javase/8/docs/api/java/lang/Class.html) 中提供了两类用于访问字段、方法和构造函数的方法：
枚举这些成员的方法和搜索特定成员的方法。
也有不同的方法来访问直接在类上声明的成员与在超接口和超类中搜索继承成员的方法。
下表提供了所有成员定位方法及其特征的摘要。


**Class Methods for Locating Fields**

| [`Class`](https://docs.oracle.com/javase/8/docs/api/java/lang/Class.html) API                                             | List of members? | Inherited members? | Private members? |
|---------------------------------------------------------------------------------------------------------------------------|------------------|--------------------|------------------|
| [`getDeclaredField()`](https://docs.oracle.com/javase/8/docs/api/java/lang/Class.html#getDeclaredField-java.lang.String-) | no               | no                 | yes              |
| [`getField()`](https://docs.oracle.com/javase/8/docs/api/java/lang/Class.html#getField-java.lang.String-)                 | no               | yes                | no               |
| [`getDeclaredFields()`](https://docs.oracle.com/javase/8/docs/api/java/lang/Class.html#getDeclaredFields--)               | yes              | no                 | yes              |
| [`getFields()`](https://docs.oracle.com/javase/8/docs/api/java/lang/Class.html#getFields--)                               | yes              | yes                | no               |


**定位字段的 Class 方法**

| [`Class`](https://docs.oracle.com/javase/8/docs/api/java/lang/Class.html) API                                             | 成员名单？ | 继承成员？ | 私有成员？ |
|---------------------------------------------------------------------------------------------------------------------------|-------|-------|-------|
| [`getDeclaredField()`](https://docs.oracle.com/javase/8/docs/api/java/lang/Class.html#getDeclaredField-java.lang.String-) | no    | no    | yes   |
| [`getField()`](https://docs.oracle.com/javase/8/docs/api/java/lang/Class.html#getField-java.lang.String-)                 | no    | yes   | no    |
| [`getDeclaredFields()`](https://docs.oracle.com/javase/8/docs/api/java/lang/Class.html#getDeclaredFields--)               | yes   | no    | yes   |
| [`getFields()`](https://docs.oracle.com/javase/8/docs/api/java/lang/Class.html#getFields--)                               | yes   | yes   | no    |


**Class Methods for Locating Methods**

| [`Class`](https://docs.oracle.com/javase/8/docs/api/java/lang/Class.html) API                                                                  | List of members? | Inherited members? | Private members? |
|------------------------------------------------------------------------------------------------------------------------------------------------|------------------|--------------------|------------------|
| [`getDeclaredMethod()`](https://docs.oracle.com/javase/8/docs/api/java/lang/Class.html#getDeclaredMethod-java.lang.String-java.lang.Class...-) | no               | no                 | yes              |
| [`getMethod()`](https://docs.oracle.com/javase/8/docs/api/java/lang/Class.html#getMethod-java.lang.String-java.lang.Class...-)                 | no               | yes                | no               |
| [`getDeclaredMethods()`](https://docs.oracle.com/javase/8/docs/api/java/lang/Class.html#getDeclaredMethods--)                                  | yes              | no                 | yes              |
| [`getMethods()`](https://docs.oracle.com/javase/8/docs/api/java/lang/Class.html#getMethods--)                                                  | yes              | yes                | no               |


**定位方法的类方法**

| [`Class`](https://docs.oracle.com/javase/8/docs/api/java/lang/Class.html) API                                                                  | 成员名单？ | 继承成员？ | 私有成员？ |
|------------------------------------------------------------------------------------------------------------------------------------------------|-------|-------|-------|
| [`getDeclaredMethod()`](https://docs.oracle.com/javase/8/docs/api/java/lang/Class.html#getDeclaredMethod-java.lang.String-java.lang.Class...-) | no    | no    | yes   |
| [`getMethod()`](https://docs.oracle.com/javase/8/docs/api/java/lang/Class.html#getMethod-java.lang.String-java.lang.Class...-)                 | no    | yes   | no    |
| [`getDeclaredMethods()`](https://docs.oracle.com/javase/8/docs/api/java/lang/Class.html#getDeclaredMethods--)                                  | yes   | no    | yes   |
| [`getMethods()`](https://docs.oracle.com/javase/8/docs/api/java/lang/Class.html#getMethods--)                                                  | yes   | yes   | no    |


**Class Methods for Locating Constructors**

| [`Class`](https://docs.oracle.com/javase/8/docs/api/java/lang/Class.html) API                                                           | List of members? | Inherited members? | Private members? |
|-----------------------------------------------------------------------------------------------------------------------------------------|------------------|--------------------|------------------|
| [`getDeclaredConstructor()`](https://docs.oracle.com/javase/8/docs/api/java/lang/Class.html#getDeclaredConstructor-java.lang.Class...-) | no               | N/A <sup>1</sup>   | yes              |
| [`getConstructor()`](https://docs.oracle.com/javase/8/docs/api/java/lang/Class.html#getConstructor-java.lang.Class...-)                 | no               | N/A <sup>1</sup>   | no               |
| [`getDeclaredConstructors()`](https://docs.oracle.com/javase/8/docs/api/java/lang/Class.html#getDeclaredConstructors--)                 | yes              | N/A <sup>1</sup>   | yes              |
| [`getConstructors()`](https://docs.oracle.com/javase/8/docs/api/java/lang/Class.html#getConstructors--)                                 | yes              | N/A <sup>1</sup>   | no               |

<sup>1</sup> Constructors are not inherited.


**定位构造函数的类方法**

| [`Class`](https://docs.oracle.com/javase/8/docs/api/java/lang/Class.html) API                                                           | 成员名单？ | 继承成员？            | 私有成员？ |
|-----------------------------------------------------------------------------------------------------------------------------------------|-------|------------------|-------|
| [`getDeclaredConstructor()`](https://docs.oracle.com/javase/8/docs/api/java/lang/Class.html#getDeclaredConstructor-java.lang.Class...-) | no    | N/A <sup>1</sup> | yes   |
| [`getConstructor()`](https://docs.oracle.com/javase/8/docs/api/java/lang/Class.html#getConstructor-java.lang.Class...-)                 | no    | N/A <sup>1</sup> | no    |
| [`getDeclaredConstructors()`](https://docs.oracle.com/javase/8/docs/api/java/lang/Class.html#getDeclaredConstructors--)                 | yes   | N/A <sup>1</sup> | yes   |
| [`getConstructors()`](https://docs.oracle.com/javase/8/docs/api/java/lang/Class.html#getConstructors--)                                 | yes   | N/A <sup>1</sup> | no    |

<sup>1</sup> 构造函数不被继承。


Given a class name and an indication of which members are of interest, the [`ClassSpy`](https://docs.oracle.com/javase/tutorial/reflect/class/example/ClassSpy.java) example uses the `get*s()` methods to determine the list of all public elements, including any which are inherited.


给定一个类名和感兴趣的成员的指示， [`ClassSpy`](./example/ClassSpy.java) 示例使用 `gets()` 方法来确定所有公共元素的列表，包括任何继承的元素。


```java
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Member;
import static java.lang.System.out;

enum ClassMember { CONSTRUCTOR, FIELD, METHOD, CLASS, ALL }

public class ClassSpy {
    public static void main(String... args) {
	try {
	    Class<?> c = Class.forName(args[0]);
	    out.format("Class:%n  %s%n%n", c.getCanonicalName());

	    Package p = c.getPackage();
	    out.format("Package:%n  %s%n%n",
		       (p != null ? p.getName() : "-- No Package --"));

	    for (int i = 1; i < args.length; i++) {
		switch (ClassMember.valueOf(args[i])) {
		case CONSTRUCTOR:
		    printMembers(c.getConstructors(), "Constructor");
		    break;
		case FIELD:
		    printMembers(c.getFields(), "Fields");
		    break;
		case METHOD:
		    printMembers(c.getMethods(), "Methods");
		    break;
		case CLASS:
		    printClasses(c);
		    break;
		case ALL:
		    printMembers(c.getConstructors(), "Constuctors");
		    printMembers(c.getFields(), "Fields");
		    printMembers(c.getMethods(), "Methods");
		    printClasses(c);
		    break;
		default:
		    assert false;
		}
	    }

        // production code should handle these exceptions more gracefully
	} catch (ClassNotFoundException x) {
	    x.printStackTrace();
	}
    }

    private static void printMembers(Member[] mbrs, String s) {
	out.format("%s:%n", s);
	for (Member mbr : mbrs) {
	    if (mbr instanceof Field)
		out.format("  %s%n", ((Field)mbr).toGenericString());
	    else if (mbr instanceof Constructor)
		out.format("  %s%n", ((Constructor)mbr).toGenericString());
	    else if (mbr instanceof Method)
		out.format("  %s%n", ((Method)mbr).toGenericString());
	}
	if (mbrs.length == 0)
	    out.format("  -- No %s --%n", s);
	out.format("%n");
    }

    private static void printClasses(Class<?> c) {
	out.format("Classes:%n");
	Class<?>[] clss = c.getClasses();
	for (Class<?> cls : clss)
	    out.format("  %s%n", cls.getCanonicalName());
	if (clss.length == 0)
	    out.format("  -- No member interfaces, classes, or enums --%n");
	out.format("%n");
    }
}
```


This example is relatively compact; however the `printMembers()` method is slightly awkward due to the fact that the [`java.lang.reflect.Member`](https://docs.oracle.com/javase/8/docs/api/java/lang/reflect/Member.html) interface has existed since the earliest implementations of reflection and it could not be modified to include the more useful `getGenericString()` method when generics were introduced. 
The only alternatives are to test and cast as shown, replace this method with `printConstructors()`, `printFields()`, and `printMethods()`, or to be satisfied with the relatively spare results of [`Member.getName()`](https://docs.oracle.com/javase/8/docs/api/java/lang/reflect/Member.html#getName--).


这个例子比较紧凑；然而， `printMembers()` 方法有点笨拙，因为 [`java.lang.reflect.Member`](https://docs.oracle.com/javase/8/docs/api/java/lang/reflect/Member.html) 接口自最早的反射实现以来就已经存在，并且无法修改以包含更有用的接口引入泛型时的 `getGenericString()` 方法。
唯一的选择是测试和转换如图所示，用 `printConstructors()` 、 `printFields()` 和 `printMethods()` 替换这个方法，或者满足于 [`Member.getName( )`](https://docs.oracle.com/javase/8/docs/api/java/lang/reflect/Member.html#getName--) 。


Samples of the output and their interpretation follows. 
User input is in italics.


输出样本及其解释如下。
用户输入以斜体显示。


```text
$ java ClassSpy java.lang.ClassCastException CONSTRUCTOR
Class:
  java.lang.ClassCastException

Package:
  java.lang

Constructor:
  public java.lang.ClassCastException()
  public java.lang.ClassCastException(java.lang.String)
```


Since constructors are not inherited, the exception chaining mechanism constructors (those with a [`Throwable`](https://docs.oracle.com/javase/8/docs/api/java/lang/Throwable.html) parameter) which are defined in the immediate super class [`RuntimeException`](https://docs.oracle.com/javase/8/docs/api/java/lang/RuntimeException.html) and other super classes are not found.


由于构造函数不是继承的，因此在直接超类 [`RuntimeException`](https://docs.oracle.com/javase/8/docs/api/java/lang/Throwable.html) 和其他超类中定义的异常链接机制构造函数（具有 [`Throwable`](https://docs.oracle.com/javase/8/docs/api/java/lang/RuntimeException.html) 参数的构造函数）找不到。


```text
$ java ClassSpy java.nio.channels.ReadableByteChannel METHOD
Class:
  java.nio.channels.ReadableByteChannel

Package:
  java.nio.channels

Methods:
  public abstract int java.nio.channels.ReadableByteChannel.read
    (java.nio.ByteBuffer) throws java.io.IOException
  public abstract void java.nio.channels.Channel.close() throws
    java.io.IOException
  public abstract boolean java.nio.channels.Channel.isOpen()
```


The interface [`java.nio.channels.ReadableByteChannel`](https://docs.oracle.com/javase/8/docs/api/java/nio/channels/ReadableByteChannel.html) defines [`read()`](https://docs.oracle.com/javase/8/docs/api/java/nio/channels/ReadableByteChannel.html#read-java.nio.ByteBuffer-). 
The remaining methods are inherited from a super interface. 
This code could easily be modified to list only those methods that are actually declared in the class by replacing `get*s()` with `getDeclared*s()`.


接口 [`java.nio.channels.ReadableByteChannel`](https://docs.oracle.com/javase/8/docs/api/java/nio/channels/ReadableByteChannel.html) 定义了 [`read()`](https://docs.oracle.com/javase/8/docs/api/java/nio/channels/ReadableByteChannel.html#read-java.nio.ByteBuffer-) 。
其余方法继承自超接口。
通过将 `gets()` 替换为 `getDeclareds()`，可以轻松修改此代码以仅列出在类中实际声明的那些方法。


```text
$ java ClassSpy ClassMember FIELD METHOD
Class:
  ClassMember

Package:
  -- No Package --

Fields:
  public static final ClassMember ClassMember.CONSTRUCTOR
  public static final ClassMember ClassMember.FIELD
  public static final ClassMember ClassMember.METHOD
  public static final ClassMember ClassMember.CLASS
  public static final ClassMember ClassMember.ALL

Methods:
  public static ClassMember ClassMember.valueOf(java.lang.String)
  public static ClassMember[] ClassMember.values()
  public final int java.lang.Enum.hashCode()
  public final int java.lang.Enum.compareTo(E)
  public int java.lang.Enum.compareTo(java.lang.Object)
  public final java.lang.String java.lang.Enum.name()
  public final boolean java.lang.Enum.equals(java.lang.Object)
  public java.lang.String java.lang.Enum.toString()
  public static <T> T java.lang.Enum.valueOf
    (java.lang.Class<T>,java.lang.String)
  public final java.lang.Class<E> java.lang.Enum.getDeclaringClass()
  public final int java.lang.Enum.ordinal()
  public final native java.lang.Class<?> java.lang.Object.getClass()
  public final native void java.lang.Object.wait(long) throws
    java.lang.InterruptedException
  public final void java.lang.Object.wait(long,int) throws
    java.lang.InterruptedException
  public final void java.lang.Object.wait() hrows java.lang.InterruptedException
  public final native void java.lang.Object.notify()
  public final native void java.lang.Object.notifyAll()
```


In the fields portion of these results, enum constants are listed. 
While these are technically fields, it might be useful to distinguish them from other fields. 
This example could be modified to use [`java.lang.reflect.Field.isEnumConstant()`](https://docs.oracle.com/javase/8/docs/api/java/lang/reflect/Field.html#isEnumConstant--) for this purpose. 
The [`EnumSpy`](https://docs.oracle.com/javase/tutorial/reflect/special/example/EnumSpy.java) example in a later section of this trail, [Examining Enums](https://docs.oracle.com/javase/tutorial/reflect/special/enumMembers.html), contains a possible implementation.


在这些结果的字段部分中，列出了枚举常量。
虽然这些是技术领域，但将它们与其他领域区分开来可能很有用。
此示例可以修改为使用 [`java.lang.reflect.Field.isEnumConstant()`](https://docs.oracle.com/javase/8/docs/api/java/lang/reflect/Field.html#isEnumConstant--) 来实现此目的。
本教程后面部分 [Examineing Enums](./../special/enumMembers.md) 中的 [`EnumSpy`](./example/EnumSpy.java) 示例包含一个可能的实现。


In the methods section of the output, observe that the method name includes the name of the declaring class. 
Thus, the `toString()` method is implemented by [`Enum`](https://docs.oracle.com/javase/8/docs/api/java/lang/Enum.html#toString--), not inherited from [`Object`](https://docs.oracle.com/javase/8/docs/api/java/lang/Object.html). 
The code could be amended to make this more obvious by using [`Field.getDeclaringClass()`](https://docs.oracle.com/javase/8/docs/api/java/lang/reflect/Field.html#getDeclaringClass--). 
The following fragment illustrates part of a potential solution.


在输出的方法部分，观察方法名称包括声明类的名称。
因此， `toString()` 方法是由 [`Enum`](https://docs.oracle.com/javase/8/docs/api/java/lang/Enum.html#toString--) 实现的，而不是从 [`Object`](https://docs.oracle.com/javase/8/docs/api/java/lang/Object.html) 继承的。
可以使用 [`Field.getDeclaringClass()`](https://docs.oracle.com/javase/8/docs/api/java/lang/reflect/Field.html#getDeclaringClass--) 修改代码以使其更加明显。
以下片段说明了部分潜在解决方案。


```text
if (mbr instanceof Field) {
    Field f = (Field)mbr;
    out.format("  %s%n", f.toGenericString());
    out.format("  -- declared in: %s%n", f.getDeclaringClass());
}
```
