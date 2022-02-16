# Troubleshooting _故障排除_


The following examples show typical errors which may be encountered when reflecting on classes.


以下示例显示了在反射类时可能遇到的典型错误。


## Compiler Warning: "Note: ... uses unchecked or unsafe operations"


When a method is invoked, the types of the argument values are checked and possibly converted. 
[`ClassWarning`](https://docs.oracle.com/javase/tutorial/reflect/class/example/ClassWarning.java) invokes [`getMethod()`](https://docs.oracle.com/javase/8/docs/api/java/lang/Class.html#getMethod-java.lang.String-java.lang.Class...-) to cause a typical unchecked conversion warning:


调用方法时，会检查参数值的类型并可能进行转换。
[`ClassWarning`](https://docs.oracle.com/javase/tutorial/reflect/class/example/ClassWarning.java) 调用 [`getMethod()`](https://docs.oracle.com/javase/8/docs/api/java/lang/Class.html#getMethod-java.lang.String-java.lang.Class...-) 导致典型的未经检查的转换警告：


```java
import java.lang.reflect.Method;

public class ClassWarning {
    void m() {
	try {
	    Class c = ClassWarning.class;
	    Method m = c.getMethod("m");  // warning

        // production code should handle this exception more gracefully
	} catch (NoSuchMethodException x) {
    	    x.printStackTrace();
    	}
    }
}
```


```text
$ javac ClassWarning.java
Note: ClassWarning.java uses unchecked or unsafe operations.
Note: Recompile with -Xlint:unchecked for details.
$ javac -Xlint:unchecked ClassWarning.java
ClassWarning.java:6: warning: [unchecked] unchecked call to getMethod
  (String,Class<?>...) as a member of the raw type Class
Method m = c.getMethod("m");  // warning
                      ^
1 warning
```


Many library methods have been retrofitted with generic declarations including several in [`Class`](https://docs.oracle.com/javase/8/docs/api/java/lang/Class.html). 
Since `c` is declared as a _raw_ type (has no type parameters) and the corresponding parameter of [`getMethod()`](https://docs.oracle.com/javase/8/docs/api/java/lang/Class.html#getMethod-java.lang.String-java.lang.Class...-) is a parameterized type, an unchecked conversion occurs. 
The compiler is required to generate a warning. 
(See [The Java Language Specification, Java SE 7 Edition](https://docs.oracle.com/javase/specs/jls/se7/html/index.html), sections [Unchecked Conversion](https://docs.oracle.com/javase/specs/jls/se7/html/jls-5.html#jls-5.1.9) and [Method Invocation Conversion](https://docs.oracle.com/javase/specs/jls/se7/html/jls-5.html#jls-5.3).)


许多库方法已经用泛型声明进行了改造，包括 [`Class`](https://docs.oracle.com/javase/8/docs/api/java/lang/Class.html) 中的几个。
由于 `c` 被声明为 _raw_ 类型（没有类型参数）并且 [`getMethod()`](https://docs.oracle.com/javase/8/docs/api/java/lang/Class.html#getMethod-java.lang.String-java.lang.Class...-) 的相应参数是参数化类型，因此会发生未经检查的转换。
编译器需要生成警告。
（请参阅 [The Java Language Specification, Java SE 7 Edition](https://docs.oracle.com/javase/specs/jls/se7/html/index.html) ， [Unchecked Conversion](https://docs.oracle.com/javase/specs/jls/se7/html/jls-5.html#jls-5.1.9) 和 [Method Invocation Conversion](https://docs.oracle.com/javase/specs/jls/se7/html/jls-5.html#jls-5.3) 部分。）


There are two possible solutions. 
The more preferable it to modify the declaration of `c` to include an appropriate generic type. 
In this case, the declaration should be:


有两种可能的解决方案。
更可取的是修改 `c` 的声明以包含适当的泛型类型。
在这种情况下，声明应该是：


`Class<?> c = warn.getClass();`


Alternatively, the warning could be explicitly suppressed using the predefined annotation [`@SuppressWarnings`](https://docs.oracle.com/javase/8/docs/api/java/lang/SuppressWarnings.html) preceding the problematic statement.


或者，可以使用有问题的语句之前的预定义注释 [`@SuppressWarnings`](https://docs.oracle.com/javase/8/docs/api/java/lang/SuppressWarnings.html) 显式抑制警告。


```text
Class c = ClassWarning.class;
@SuppressWarnings("unchecked")
Method m = c.getMethod("m");  
// warning gone
```


> **Tip**:
> 
> As a general principle, warnings should not be ignored as they may indicate the presence of a bug. 
> Parameterized declarations should be used as appropriate. 
> If that is not possible (perhaps because an application must interact with a library vendor's code), annotate the offending line using [`@SuppressWarnings`](https://docs.oracle.com/javase/8/docs/api/java/lang/SuppressWarnings.html). 


> **提示**:
> 
> 作为一般原则，不应忽略警告，因为它们可能表明存在错误。
> 应酌情使用参数化声明。
> 如果这是不可能的（可能是因为应用程序必须与库供应商的代码交互），请使用 [`@SuppressWarnings`](https://docs.oracle.com/javase/8/docs/api/java/lang/SuppressWarnings.html) 注释有问题的行。


## InstantiationException when the Constructor is Not Accessible


[`Class.newInstance()`](https://docs.oracle.com/javase/8/docs/api/java/lang/Class.html#newInstance--) will throw an [`InstantiationException`](https://docs.oracle.com/javase/8/docs/api/java/lang/InstantiationException.html) if an attempt is made to create a new instance of the class and the zero-argument constructor is not visible. 
The [`ClassTrouble`](https://docs.oracle.com/javase/tutorial/reflect/class/example/ClassTrouble.java) example illustrates the resulting stack trace.


如果尝试创建类的新实例并且零参数构造函数不可见， [`Class.newInstance()`](https://docs.oracle.com/javase/8/docs/api/java/lang/Class.html#newInstance--) 将抛出 [`InstantiationException`](https://docs.oracle.com/javase/8/docs/api/java/lang/InstantiationException.html) 。
[`ClassTrouble`](./example/ClassTrouble.java) 示例说明了生成的堆栈跟踪。


```java
class Cls {
    private Cls() {}
}

public class ClassTrouble {
    public static void main(String... args) {
	try {
	    Class<?> c = Class.forName("Cls");
	    c.newInstance();  // InstantiationException

        // production code should handle these exceptions more gracefully
	} catch (InstantiationException x) {
	    x.printStackTrace();
	} catch (IllegalAccessException x) {
	    x.printStackTrace();
	} catch (ClassNotFoundException x) {
	    x.printStackTrace();
	}
    }
}
```


```text
$ java ClassTrouble
java.lang.IllegalAccessException: Class ClassTrouble can not access a member of
  class Cls with modifiers "private"
        at sun.reflect.Reflection.ensureMemberAccess(Reflection.java:65)
        at java.lang.Class.newInstance0(Class.java:349)
        at java.lang.Class.newInstance(Class.java:308)
        at ClassTrouble.main(ClassTrouble.java:9)
```


[`Class.newInstance()`](https://docs.oracle.com/javase/8/docs/api/java/lang/Class.html#newInstance--) behaves very much like the `new` keyword and will fail for the same reasons `new` would fail. 
The typical solution in reflection is to take advantage of the [`java.lang.reflect.AccessibleObject`](https://docs.oracle.com/javase/8/docs/api/java/lang/reflect/AccessibleObject.html) class which provides the ability to suppress access control checks; however, this approach will not work because [`java.lang.Class`](https://docs.oracle.com/javase/8/docs/api/java/lang/Class.html) does not extend [`AccessibleObject`](https://docs.oracle.com/javase/8/docs/api/java/lang/reflect/AccessibleObject.html). 
The only solution is to modify the code to use [`Constructor.newInstance()`](https://docs.oracle.com/javase/8/docs/api/java/lang/reflect/Constructor.html#newInstance-java.lang.Object...-) which does extend [`AccessibleObject`](https://docs.oracle.com/javase/8/docs/api/java/lang/reflect/AccessibleObject.html).


[`Class.newInstance()`](https://docs.oracle.com/javase/8/docs/api/java/lang/Class.html#newInstance--) 的行为与 `new` 关键字非常相似，并且会因为 `new` 失败的原因而失败。
反射的典型解决方案是利用 [`java.lang.reflect.AccessibleObject`](https://docs.oracle.com/javase/8/docs/api/java/lang/reflect/AccessibleObject.html) 类，它提供了抑制访问控制检查的能力；但是，这种方法不起作用，因为 [`java.lang.Class`](https://docs.oracle.com/javase/8/docs/api/java/lang/Class.html) 没有扩展 [`AccessibleObject`](https://docs.oracle.com/javase/8/docs/api/java/lang/reflect/AccessibleObject.html) 。
唯一的解决方案是修改代码以使用 [`Constructor.newInstance()`](https://docs.oracle.com/javase/8/docs/api/java/lang/reflect/Constructor.html#newInstance-java.lang.Object...-) 扩展 [`AccessibleObject`](https://docs.oracle.com/javase/8/docs/api/java/lang/reflect/AccessibleObject.html) 。


> **Tip**:
> 
> In general, it is preferable to use [`Constructor.newInstance()`](https://docs.oracle.com/javase/8/docs/api/java/lang/reflect/Constructor.html#newInstance-java.lang.Object...-) for the reasons described in the [Creating New Class Instances](https://docs.oracle.com/javase/tutorial/reflect/member/ctorInstance.html) section in the [Members](https://docs.oracle.com/javase/tutorial/reflect/member/index.html) lesson. 


> **提示**:
> 
> 一般来说，最好使用 [`Constructor.newInstance()`](https://docs.oracle.com/javase/8/docs/api/java/lang/reflect/Constructor.html#newInstance-java.lang.Object...-) ，原因在 [Members](./../member/index.md) 课程的 [Creating New Class Instances](./../member/ctorInstance.md) 部分中描述。


Additional examples of potential problems using [`Constructor.newInstance()`](https://docs.oracle.com/javase/8/docs/api/java/lang/reflect/Constructor.html#newInstance-java.lang.Object...-) may be found in the [Constructor Troubleshooting](https://docs.oracle.com/javase/tutorial/reflect/member/ctorTrouble.html) section of the [Members](https://docs.oracle.com/javase/tutorial/reflect/member/index.html) lesson.


使用 [`Constructor.newInstance()`](https://docs.oracle.com/javase/8/docs/api/java/lang/reflect/Constructor.html#newInstance-java.lang.Object...-) 的潜在问题的其他示例可以在 [Members](./../member/index.md) 课程的 [Constructor Troubleshooting](./../member/ctorInstance.md) 部分中找到。
