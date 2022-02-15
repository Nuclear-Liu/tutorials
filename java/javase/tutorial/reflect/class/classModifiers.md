# Examining Class Modifiers and Types _检查类修饰符和类型_


A class may be declared with one or more modifiers which affect its runtime behavior:


可以使用一个或多个影响其运行时行为的修饰符来声明一个类：


* Access modifiers: `public`, `protected`, and `private`

* 访问修饰符： `public` 、 `protected` 和 `private`

* Modifier requiring override: `abstract`

* 需要覆盖的修饰符： `abstract`

* Modifier restricting to one instance: `static`

* 修饰符限制为一个实例： `static`

* Modifier prohibiting value modification: `final`

* 禁止值修改的修饰符： `final`

* Modifier forcing strict floating point behavior: `strictfp`

* 强制严格浮点行为的修饰符： `strictfp`

* Annotations

* 注解


Not all modifiers are allowed on all classes, for example an interface cannot be `final` and an enum cannot be `abstract`. 
[`java.lang.reflect.Modifier`](https://docs.oracle.com/javase/8/docs/api/java/lang/reflect/Modifier.html) contains declarations for all possible modifiers. 
It also contains methods which may be used to decode the set of modifiers returned by [`Class.getModifiers()`](https://docs.oracle.com/javase/8/docs/api/java/lang/Class.html#getModifiers--).


并非所有类都允许使用所有修饰符，例如接口不能是 `final` ，枚举不能是 `abstract` 。
[`java.lang.reflect.Modifier`](https://docs.oracle.com/javase/8/docs/api/java/lang/reflect/Modifier.html) 包含所有可能修饰符的声明。
它还包含可用于解码 [`Class.getModifiers()`](https://docs.oracle.com/javase/8/docs/api/java/lang/Class.html#getModifiers--) 返回的修饰符集的方法。


The [`ClassDeclarationSpy`](https://docs.oracle.com/javase/tutorial/reflect/class/example/ClassDeclarationSpy.java) example shows how to obtain the declaration components of a class including the modifiers, generic type parameters, implemented interfaces, and the inheritance path. 
Since [`Class`](https://docs.oracle.com/javase/8/docs/api/java/lang/Class.html) implements the [`java.lang.reflect.AnnotatedElement`](https://docs.oracle.com/javase/8/docs/api/java/lang/reflect/AnnotatedElement.html) interface it is also possible to query the runtime annotations.


[`ClassDeclarationSpy`](./example/ClassDeclarationSpy.java) 示例展示了如何获取类的声明组件，包括修饰符、泛型类型参数、实现的接口和继承路径。
由于 [`Class`](https://docs.oracle.com/javase/8/docs/api/java/lang/Class.html) 实现了 [`java.lang.reflect.AnnotatedElement`](https://docs.oracle.com/javase/8/docs/api/java/lang/reflect/AnnotatedElement.html) 接口，因此还可以查询运行时注释。


```java
import java.lang.annotation.Annotation;
import java.lang.reflect.Modifier;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.util.Arrays;
import java.util.ArrayList;
import java.util.List;
import static java.lang.System.out;

public class ClassDeclarationSpy {
    public static void main(String... args) {
	try {
	    Class<?> c = Class.forName(args[0]);
	    out.format("Class:%n  %s%n%n", c.getCanonicalName());
	    out.format("Modifiers:%n  %s%n%n",
		       Modifier.toString(c.getModifiers()));

	    out.format("Type Parameters:%n");
	    TypeVariable[] tv = c.getTypeParameters();
	    if (tv.length != 0) {
		out.format("  ");
		for (TypeVariable t : tv)
		    out.format("%s ", t.getName());
		out.format("%n%n");
	    } else {
		out.format("  -- No Type Parameters --%n%n");
	    }

	    out.format("Implemented Interfaces:%n");
	    Type[] intfs = c.getGenericInterfaces();
	    if (intfs.length != 0) {
		for (Type intf : intfs)
		    out.format("  %s%n", intf.toString());
		out.format("%n");
	    } else {
		out.format("  -- No Implemented Interfaces --%n%n");
	    }

	    out.format("Inheritance Path:%n");
	    List<Class> l = new ArrayList<Class>();
	    printAncestor(c, l);
	    if (l.size() != 0) {
		for (Class<?> cl : l)
		    out.format("  %s%n", cl.getCanonicalName());
		out.format("%n");
	    } else {
		out.format("  -- No Super Classes --%n%n");
	    }

	    out.format("Annotations:%n");
	    Annotation[] ann = c.getAnnotations();
	    if (ann.length != 0) {
		for (Annotation a : ann)
		    out.format("  %s%n", a.toString());
		out.format("%n");
	    } else {
		out.format("  -- No Annotations --%n%n");
	    }

        // production code should handle this exception more gracefully
	} catch (ClassNotFoundException x) {
	    x.printStackTrace();
	}
    }

    private static void printAncestor(Class<?> c, List<Class> l) {
	Class<?> ancestor = c.getSuperclass();
 	if (ancestor != null) {
	    l.add(ancestor);
	    printAncestor(ancestor, l);
 	}
    }
}
```


A few samples of the output follows. 
User input is in italics.


下面是一些输出样本。
用户输入以斜体显示。


```text
$ java ClassDeclarationSpy java.util.concurrent.ConcurrentNavigableMap
Class:
  java.util.concurrent.ConcurrentNavigableMap

Modifiers:
  public abstract interface

Type Parameters:
  K V

Implemented Interfaces:
  java.util.concurrent.ConcurrentMap<K, V>
  java.util.NavigableMap<K, V>

Inheritance Path:
  -- No Super Classes --

Annotations:
  -- No Annotations --
```


This is the actual declaration for [`java.util.concurrent.ConcurrentNavigableMap`](https://docs.oracle.com/javase/8/docs/api/java/util/concurrent/ConcurrentNavigableMap.html) in the source code:


这是源代码中 [`java.util.concurrent.ConcurrentNavigableMap`](https://docs.oracle.com/javase/8/docs/api/java/util/concurrent/ConcurrentNavigableMap.html) 的实际声明：


```text
public interface ConcurrentNavigableMap<K,V>
    extends ConcurrentMap<K,V>, NavigableMap<K,V>
```


Note that since this is an interface, it is implicitly `abstract`. 
The compiler adds this modifier for every interface. 
Also, this declaration contains two generic type parameters, `K` and `V`. 
The example code simply prints the names of these parameters, but is it possible to retrieve additional information about them using methods in [`java.lang.reflect.TypeVariable`](https://docs.oracle.com/javase/8/docs/api/java/lang/reflect/TypeVariable.html). 
Interfaces may also implement other interfaces as shown above.


请注意，由于这是一个接口，因此它是隐含的 `abstract` 。
编译器为每个接口添加这个修饰符。
此外，此声明包含两个泛型类型参数， `K` 和 `V` 。
示例代码仅打印这些参数的名称，但是否可以使用 [`java.lang.reflect.TypeVariable`](https://docs.oracle.com/javase/8/docs/api/java/lang/reflect/TypeVariable.html) 中的方法检索有关它们的附加信息。
接口也可以实现如上所示的其他接口。


```text
$ java ClassDeclarationSpy "[Ljava.lang.String;"
Class:
  java.lang.String[]

Modifiers:
  public abstract final

Type Parameters:
  -- No Type Parameters --

Implemented Interfaces:
  interface java.lang.Cloneable
  interface java.io.Serializable

Inheritance Path:
  java.lang.Object

Annotations:
  -- No Annotations --
```


Since arrays are runtime objects, all of the type information is defined by the Java virtual machine. 
In particular, arrays implement [`Cloneable`](https://docs.oracle.com/javase/8/docs/api/java/lang/Cloneable.html) and [`java.io.Serializable`](https://docs.oracle.com/javase/8/docs/api/java/io/Serializable.html) and their direct superclass is always [`Object`](https://docs.oracle.com/javase/8/docs/api/java/lang/Object.html).


由于数组是运行时对象，所有类型信息都由 Java 虚拟机定义。
特别是，数组实现了 [`Cloneable`](https://docs.oracle.com/javase/8/docs/api/java/lang/Cloneable.html) 和 [`java.io.Serializable`](https://docs.oracle.com/javase/8/docs/api/java/io/Serializable.html) 并且它们的直接超类始终是 [`Object`](https://docs.oracle.com/javase/8/docs/api/java/lang/Object.html) 。


```text
$ java ClassDeclarationSpy java.io.InterruptedIOException
Class:
  java.io.InterruptedIOException

Modifiers:
  public

Type Parameters:
  -- No Type Parameters --

Implemented Interfaces:
  -- No Implemented Interfaces --

Inheritance Path:
  java.io.IOException
  java.lang.Exception
  java.lang.Throwable
  java.lang.Object

Annotations:
  -- No Annotations --
```


From the inheritance path, it may be deduced that [`java.io.InterruptedIOException`](https://docs.oracle.com/javase/8/docs/api/java/io/InterruptedIOException.html) is a checked exception because [`RuntimeException`](https://docs.oracle.com/javase/8/docs/api/java/lang/RuntimeException.html) is not present.


从继承路径可以推断出 [`java.io.InterruptedIOException`](https://docs.oracle.com/javase/8/docs/api/java/io/InterruptedIOException.html) 是检查异常，因为 [`RuntimeException`](https://docs.oracle.com/javase/8/docs/api/java/lang/RuntimeException.html) 不存在。


```text
$ java ClassDeclarationSpy java.security.Identity
Class:
  java.security.Identity

Modifiers:
  public abstract

Type Parameters:
  -- No Type Parameters --

Implemented Interfaces:
  interface java.security.Principal
  interface java.io.Serializable

Inheritance Path:
  java.lang.Object

Annotations:
  @java.lang.Deprecated()
```


This output shows that [`java.security.Identity`](https://docs.oracle.com/javase/8/docs/api/java/security/Identity.html), a deprecated API, possesses the annotation [`java.lang.Deprecated`](https://docs.oracle.com/javase/8/docs/api/java/lang/Deprecated.html). 
This may be used by reflective code to detect deprecated APIs.


此输出显示 [`java.security.Identity`](https://docs.oracle.com/javase/8/docs/api/java/security/Identity.html) ，一个已弃用的 API，拥有注释 [`java.lang.Deprecated`](https://docs.oracle.com/javase/8/docs/api/java/lang/Deprecated.html) 。
反射代码可以使用它来检测已弃用的 API。


> **Note**: 
> Not all annotations are available via reflection. 
> Only those which have a [`java.lang.annotation.RetentionPolicy`](https://docs.oracle.com/javase/8/docs/api/java/lang/annotation/RetentionPolicy.html) of [`RUNTIME`](https://docs.oracle.com/javase/8/docs/api/java/lang/annotation/RetentionPolicy.html#RUNTIME) are accessible. 
> Of the three annotations pre-defined in the language [`@Deprecated`](https://docs.oracle.com/javase/8/docs/api/java/lang/Deprecated.html), [`@Override`](https://docs.oracle.com/javase/8/docs/api/java/lang/Override.html), and [`@SuppressWarnings`](https://docs.oracle.com/javase/8/docs/api/java/lang/SuppressWarnings.html) only [`@Deprecated`](https://docs.oracle.com/javase/8/docs/api/java/lang/Deprecated.html) is available at runtime. 


> **注意**: 
> 并非所有注释都可以通过反射获得。
> 只有具有 [`RUNTIME`](https://docs.oracle.com/javase/8/docs/api/java/lang/annotation/RetentionPolicy.html#RUNTIME) 的 [`java.lang.annotation.RetentionPolicy`](https://docs.oracle.com/javase/8/docs/api/java/lang/annotation/RetentionPolicy.html) 的那些是可访问的。
> 在语言 [`@Deprecated`](https://docs.oracle.com/javase/8/docs/api/java/lang/Deprecated.html) 、 [`@Override`](https://docs.oracle.com/javase/8/docs/api/java/lang/Override.html) 和 [`@SuppressWarnings`](https://docs.oracle.com/javase/8/docs/api/java/lang/SuppressWarnings.html) 中预定义的三个注解中，只有 [`@Deprecated`](https://docs.oracle.com/javase/8/docs/api/java/lang/Deprecated.html) 在运行时可用.
