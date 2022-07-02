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


If the type is available but there is no instance then it is possible to obtain a [`Class`](https://docs.oracle.com/javase/8/docs/api/java/lang/Class.html) by appending "`.class`" to the name of the type. 
This is also the easiest way to obtain the [`Class`](https://docs.oracle.com/javase/8/docs/api/java/lang/Class.html) for a primitive type.


如果类型可用但没有实例，则可以通过将 “`.class`” 附加到类型名称来获得 [`Class`](https://docs.oracle.com/javase/8/docs/api/java/lang/Class.html) 。
这也是获取原始类型的 [`Class`](https://docs.oracle.com/javase/8/docs/api/java/lang/Class.html) 的最简单方法。


```text
boolean b;
Class c = b.getClass();   // compile-time error

Class c = boolean.class;  // correct
```


Note that the statement `boolean.getClass()` would produce a compile-time error because a `boolean` is a primitive type and cannot be dereferenced. 
The `.class` syntax returns the [`Class`](https://docs.oracle.com/javase/8/docs/api/java/lang/Class.html) corresponding to the type `boolean`.


请注意，语句 `boolean.getClass()` 会产生编译时错误，因为 `boolean` 是原始类型并且不能取消引用。
`.class` 语法返回与`boolean` 类型对应的 [`Class`](https://docs.oracle.com/javase/8/docs/api/java/lang/Class.html) 。


`Class c = java.io.PrintStream.class;`


The variable `c` will be the [`Class`](https://docs.oracle.com/javase/8/docs/api/java/lang/Class.html) corresponding to the type [`java.io.PrintStream`](https://docs.oracle.com/javase/8/docs/api/java/io/PrintStream.html).


变量 `c` 将是对应于类型 [`java.io.PrintStream`](https://docs.oracle.com/javase/8/docs/api/java/io/PrintStream.html) 的 [`Class`](https://docs.oracle.com/javase/8/docs/api/java/lang/Class.html) 。


`Class c = int[][][].class;`


The `.class` syntax may be used to retrieve a [`Class`](https://docs.oracle.com/javase/8/docs/api/java/lang/Class.html) corresponding to a multi-dimensional array of a given type.


`.class` 语法可用于检索与给定类型的多维数组对应的 [`Class`](https://docs.oracle.com/javase/8/docs/api/java/lang/Class.html) 。


## `Class.forName()`


If the fully-qualified name of a class is available, it is possible to get the corresponding [`Class`]() using the static method [`Class.forName()`](). 
This cannot be used for primitive types. 
The syntax for names of array classes is described by [`Class.getName()`](). 
This syntax is applicable to references and primitive types.


如果类的完全限定名称可用，则可以使用静态方法 [`Class.forName()`]() 获取相应的 [`Class`]() 。
这不能用于原始类型。
数组类名称的语法由 [`Class.getName()`]() 描述。
此语法适用于引用和原始类型。


`Class c = Class.forName("com.duke.MyLocaleServiceProvider");`


This statement will create a class from the given fully-qualified name.


该语句将从给定的完全限定名称创建一个类。


```text
Class cDoubleArray = Class.forName("[D");

Class cStringArray = Class.forName("[[Ljava.lang.String;");
```


The variable `cDoubleArray` will contain the [`Class`](https://docs.oracle.com/javase/8/docs/api/java/lang/Class.html) corresponding to an array of primitive type `double` (that is, the same as `double[].class`). 
The `cStringArray` variable will contain the [`Class`](https://docs.oracle.com/javase/8/docs/api/java/lang/Class.html) corresponding to a two-dimensional array of [`String`](https://docs.oracle.com/javase/8/docs/api/java/lang/String.html) (that is, identical to `String[][].class`).


变量 `cDoubleArray` 将包含 [`Class`](https://docs.oracle.com/javase/8/docs/api/java/lang/Class.html) 对应于原始类型 `double` 的数组（即，与 `double[].class` 相同）。
`cStringArray` 变量将包含 [`Class`](https://docs.oracle.com/javase/8/docs/api/java/lang/Class.html) 对应于 [`String`](https://docs.oracle.com/javase/8/docs/api/java/lang/String.html) 的二维数组（即，等同于 `String[][].class` ）。


## `TYPE` Field for Primitive Type Wrappers _原始类型包装器的 `TYPE` 字段_


The `.class` syntax is a more convenient and the preferred way to obtain the [`Class`](https://docs.oracle.com/javase/8/docs/api/java/lang/Class.html) for a primitive type; however there is another way to acquire the [`Class`](https://docs.oracle.com/javase/8/docs/api/java/lang/Class.html). 
Each of the primitive types and `void` has a wrapper class in [`java.lang`](https://docs.oracle.com/javase/8/docs/api/java/lang/package-summary.html) that is used for boxing of primitive types to reference types. 
Each wrapper class contains a field named `TYPE` which is equal to the [`Class`](https://docs.oracle.com/javase/8/docs/api/java/lang/Class.html) for the primitive type being wrapped.


`.class` 语法是获取原始类型的 [`Class`](https://docs.oracle.com/javase/8/docs/api/java/lang/Class.html) 的更方便和首选的方式；但是还有另一种获取 [`Class`](https://docs.oracle.com/javase/8/docs/api/java/lang/Class.html) 的方法。
每个原始类型和 `void` 在 [`java.lang`](https://docs.oracle.com/javase/8/docs/api/java/lang/package-summary.html) 中都有一个包装类，用于将原始类型装箱到引用类型。
每个包装类都包含一个名为 `TYPE` 的字段，它等于被包装的原始类型的 [`Class`](https://docs.oracle.com/javase/8/docs/api/java/lang/Class.html) 。


`Class c = Double.TYPE;`


There is a class [`java.lang.Double`](https://docs.oracle.com/javase/8/docs/api/java/lang/Double.html) which is used to wrap the primitive type `double` whenever an [`Object`](https://docs.oracle.com/javase/8/docs/api/java/lang/Object.html) is required. 
The value of [`Double.TYPE`]() is identical to that of `double.class`.


有一个类 [`java.lang.Double`](https://docs.oracle.com/javase/8/docs/api/java/lang/Double.html) 用于在需要 [`Object`](https://docs.oracle.com/javase/8/docs/api/java/lang/Object.html) 时包装原始类型 `double` 。
[`Double.TYPE`]() 的值与`double.class` 的值相同。


`Class c = Void.TYPE;`


[`Void.TYPE`](https://docs.oracle.com/javase/8/docs/api/java/lang/Void.html#TYPE) is identical to `void.class`.


[`Void.TYPE`](https://docs.oracle.com/javase/8/docs/api/java/lang/Void.html#TYPE) 与 `void.class` 相同。


## Methods that Return Classes _返回类的方法_


There are several Reflection APIs which return classes but these may only be accessed if a [`Class`](https://docs.oracle.com/javase/8/docs/api/java/lang/Class.html) has already been obtained either directly or indirectly.


有几个反射 API 会返回类，但只有在已经直接或间接获得 [`Class`](https://docs.oracle.com/javase/8/docs/api/java/lang/Class.html) 时才能访问这些 API。


* [`Class.getSuperclass()`](https://docs.oracle.com/javase/8/docs/api/java/lang/Class.html#getSuperclass--)

  Returns the super class for the given class.

  `Class c = javax.swing.JButton.class.getSuperclass();`

  The super class of [`javax.swing.JButton`](https://docs.oracle.com/javase/8/docs/api/javax/swing/JButton.html) is [`javax.swing.AbstractButton`](https://docs.oracle.com/javase/8/docs/api/javax/swing/AbstractButton.html).

* [`Class.getSuperclass()`](https://docs.oracle.com/javase/8/docs/api/java/lang/Class.html#getSuperclass--)

  返回给定类的超类。

  `Class c = javax.swing.JButton.class.getSuperclass();`

  [`javax.swing.JButton`](https://docs.oracle.com/javase/8/docs/api/javax/swing/JButton.html) 的超类是 [`javax.swing.AbstractButton`](https://docs.oracle.com/javase/8/docs/api/javax/swing/AbstractButton.html) 。

* [`Class.getClasses()`](https://docs.oracle.com/javase/8/docs/api/java/lang/Class.html#getClasses--)

  Returns all the public classes, interfaces, and enums that are members of the class including inherited members.

  `Class<?>[] c = Character.class.getClasses();`

  [`Character`](https://docs.oracle.com/javase/8/docs/api/java/lang/Character.html) contains two member classes [`Character.Subset`](https://docs.oracle.com/javase/8/docs/api/java/lang/Character.Subset.html) and [`Character.UnicodeBlock`](https://docs.oracle.com/javase/8/docs/api/java/lang/Character.UnicodeBlock.html).

* [`Class.getClasses()`](https://docs.oracle.com/javase/8/docs/api/java/lang/Class.html#getClasses--)

  返回作为类成员的所有公共类、接口和枚举，包括继承的成员。

  `Class<?>[] c = Character.class.getClasses();`

  [`Character`](https://docs.oracle.com/javase/8/docs/api/java/lang/Character.html) 包含两个成员类 [`Character.Subset`](https://docs.oracle.com/javase/8/docs/api/java/lang/Character.Subset.html) 和 [`Character.UnicodeBlock`](https://docs.oracle.com/javase/8/docs/api/java/lang/Character.UnicodeBlock.html) 。

* [`Class.getDeclaredClasses()`](https://docs.oracle.com/javase/8/docs/api/java/lang/Class.html#getDeclaredClasses--)

  Returns all of the classes interfaces, and enums that are explicitly declared in this class.

  `Class<?>[] c = Character.class.getDeclaredClasses();`

  [`Character`]() contains two public member classes [`Character.Subset`]() and [`Character.UnicodeBlock`]() and one private class `Character.CharacterCache`.

* [`Class.getDeclaredClasses()`](https://docs.oracle.com/javase/8/docs/api/java/lang/Class.html#getDeclaredClasses--)

  返回在此类中显式声明的所有类接口和枚举。

  `Class<?>[] c = Character.class.getDeclaredClasses();`

  [`Character`]() contains two public member classes [`Character.Subset`]() and [`Character.UnicodeBlock`]() and one private class `Character.CharacterCache`.

* [`Class.getDeclaringClass()`](https://docs.oracle.com/javase/8/docs/api/java/lang/Class.html#getDeclaringClass--)

* [`Class.getDeclaringClass()`](https://docs.oracle.com/javase/8/docs/api/java/lang/Class.html#getDeclaringClass--)

* [`java.lang.reflect.Field.getDeclaringClass()`](https://docs.oracle.com/javase/8/docs/api/java/lang/reflect/Field.html#getDeclaringClass--)

* [`java.lang.reflect.Field.getDeclaringClass()`](https://docs.oracle.com/javase/8/docs/api/java/lang/reflect/Field.html#getDeclaringClass--)

* [`java.lang.reflect.Method.getDeclaringClass()`](https://docs.oracle.com/javase/8/docs/api/java/lang/reflect/Method.html#getDeclaringClass--)

* [`java.lang.reflect.Method.getDeclaringClass()`](https://docs.oracle.com/javase/8/docs/api/java/lang/reflect/Method.html#getDeclaringClass--)

* [`java.lang.reflect.Constructor.getDeclaringClass()`](https://docs.oracle.com/javase/8/docs/api/java/lang/reflect/Constructor.html#getDeclaringClass--)

  Returns the [`Class`](https://docs.oracle.com/javase/8/docs/api/java/lang/Class.html) in which these members were declared. 
  [Anonymous Class Declarations](https://docs.oracle.com/javase/specs/jls/se7/html/jls-15.html#jls-15.9.5) will not have a declaring class but will have an enclosing class.

  ```text
  import java.lang.reflect.Field;
  
  Field f = System.class.getField("out");
  Class c = f.getDeclaringClass();
  ```

  The field [`out`](https://docs.oracle.com/javase/8/docs/api/java/lang/System.html#out) is declared in [`System`](https://docs.oracle.com/javase/8/docs/api/java/lang/System.html).

  ```text
  public class MyClass {
      static Object o = new Object() {
          public void m() {} 
      };
      static Class<c> = o.getClass().getEnclosingClass();
  }
  ```

  The declaring class of the anonymous class defined by `o` is `null`.

* [`java.lang.reflect.Constructor.getDeclaringClass()`](https://docs.oracle.com/javase/8/docs/api/java/lang/reflect/Constructor.html#getDeclaringClass--)

  返回声明这些成员的 [`Class`](https://docs.oracle.com/javase/8/docs/api/java/lang/Class.html) 。
  [Anonymous Class Declarations](https://docs.oracle.com/javase/specs/jls/se7/html/jls-15.html#jls-15.9.5) 将没有声明类，但会有一个封闭类。

  ```text
  import java.lang.reflect.Field;
  
  Field f = System.class.getField("out");
  Class c = f.getDeclaringClass();
  ```

  [`out`](https://docs.oracle.com/javase/8/docs/api/java/lang/System.html#out) 字段在 [`System`](https://docs.oracle.com/javase/8/docs/api/java/lang/System.html) 中声明。

  ```text
  public class MyClass {
      static Object o = new Object() {
          public void m() {} 
      };
      static Class<c> = o.getClass().getEnclosingClass();
  }
  ```

  `o` 定义的匿名类的声明类是 `null` 。

* [`Class.getEnclosingClass()`](https://docs.oracle.com/javase/8/docs/api/java/lang/Class.html#getEnclosingClass--)

  Returns the immediately enclosing class of the class. 

  `Class c = Thread.State.class().getEnclosingClass();`

  The enclosing class of the enum [`Thread.State`](https://docs.oracle.com/javase/8/docs/api/java/lang/Thread.State.html) is [`Thread`](https://docs.oracle.com/javase/8/docs/api/java/lang/Thread.html). 

  ```text
  public class MyClass {
      static Object o = new Object() { 
          public void m() {} 
      };
      static Class<c> = o.getClass().getEnclosingClass();
  }
  ```

  The anonymous class defined by `o` is enclosed by `MyClass`.

* [`Class.getEnclosingClass()`](https://docs.oracle.com/javase/8/docs/api/java/lang/Class.html#getEnclosingClass--)

  返回类的直接封闭类。

  `Class c = Thread.State.class().getEnclosingClass();`

  枚举 [`Thread.State`](https://docs.oracle.com/javase/8/docs/api/java/lang/Thread.State.html) 的封闭类是 [`Thread`](https://docs.oracle.com/javase/8/docs/api/java/lang/Thread.html) 。

  ```text
  public class MyClass {
      static Object o = new Object() { 
          public void m() {} 
      };
      static Class<c> = o.getClass().getEnclosingClass();
  }
  ```

  由 `o` 定义的匿名类被 `MyClass` 包围。
