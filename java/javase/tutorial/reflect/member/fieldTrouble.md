# Troubleshooting _故障排除_


Here are a few common problems encountered by developers with explanations for why the occur and how to resolve them.


以下是开发人员遇到的一些常见问题，并解释了为什么会发生以及如何解决这些问题。


## `IllegalArgumentException` due to Inconvertible Types _由于不可转换类型导致的 `IllegalArgumentException`_


The [`FieldTrouble`]() example will generate an [`IllegalArgumentException`](). 
[`Field.setInt()`]() is invoked to set a field that is of the reference type `Integer` with a value of primitive type. 
In the non-reflection equivalent `Integer val = 42`, the compiler would convert (or box) the primitive type `42` to a reference type as `new Integer(42)` so that its type checking will accept the statement. 
When using reflection, type checking only occurs at runtime so there is no opportunity to box the value.


```java
import java.lang.reflect.Field;

public class FieldTrouble {
    public Integer val;

    public static void main(String... args) {
	FieldTrouble ft = new FieldTrouble();
	try {
	    Class<?> c = ft.getClass();
	    Field f = c.getDeclaredField("val");
  	    f.setInt(ft, 42);               // IllegalArgumentException

        // production code should handle these exceptions more gracefully
	} catch (NoSuchFieldException x) {
	    x.printStackTrace();
 	} catch (IllegalAccessException x) {
 	    x.printStackTrace();
	}
    }
}
```


```text
$ java FieldTrouble
Exception in thread "main" java.lang.IllegalArgumentException: Can not set
  java.lang.Object field FieldTrouble.val to (long)42
        at sun.reflect.UnsafeFieldAccessorImpl.throwSetIllegalArgumentException
          (UnsafeFieldAccessorImpl.java:146)
        at sun.reflect.UnsafeFieldAccessorImpl.throwSetIllegalArgumentException
          (UnsafeFieldAccessorImpl.java:174)
        at sun.reflect.UnsafeObjectFieldAccessorImpl.setLong
          (UnsafeObjectFieldAccessorImpl.java:102)
        at java.lang.reflect.Field.setLong(Field.java:831)
        at FieldTrouble.main(FieldTrouble.java:11)
```


To eliminate this exception, the problematic line should be replaced by the following invocation of [`Field.set(Object obj, Object value)`]():


`f.set(ft, new Integer(43));`


> **Tip:** _**提示：**_
> 
> When using reflection to set or get a field, the compiler does not have an opportunity to perform boxing. 
> It can only convert types that are related as described by the specification for [`Class.isAssignableFrom()`](https://docs.oracle.com/javase/8/docs/api/java/lang/Class.html#isAssignableFrom-java.lang.Class-). 
> The example is expected to fail because `isAssignableFrom()` will return `false` in this test which can be used programmatically to verify whether a particular conversion is possible: 
> 
>
> 使用反射设置或获取字段时，编译器没有机会执行装箱。
> 它只能转换 [`Class.isAssignableFrom()`](https://docs.oracle.com/javase/8/docs/api/java/lang/Class.html#isAssignableFrom-java.lang.Class-) 规范中描述的相关类型。
> 该示例预计会失败，因为 `isAssignableFrom()` 将在此测试中返回 `false` ，可以以编程方式使用它来验证是否可以进行特定转换：
>
>
> `Integer.class.isAssignableFrom(int.class) == false`
> 
> 
> Similarly, automatic conversion from primitive to reference type is also impossible in reflection.
> 
> 
> 同样，从原始类型到引用类型的自动转换在反射中也是不可能的。
> 
> 
> `int.class.isAssignableFrom(Integer.class) == false`


## `NoSuchFieldException` for Non-Public Fields _非公共字段的 `NoSuchFieldException`_


The astute reader may notice that if the [`FieldSpy`](https://docs.oracle.com/javase/tutorial/reflect/member/example/FieldSpy.java) example shown earlier is used to get information on a non-public field, it will fail:


精明的读者可能会注意到，如果前面显示的 [`FieldSpy`](./example/FieldSpy.java) 示例用于获取有关非公共字段的信息，它将失败：


```text
$ java FieldSpy java.lang.String count
java.lang.NoSuchFieldException: count
        at java.lang.Class.getField(Class.java:1519)
        at FieldSpy.main(FieldSpy.java:12)
```


> **Tip:**  _**提示：**_
> 
> The [`Class.getField()`](https://docs.oracle.com/javase/8/docs/api/java/lang/Class.html#getField-java.lang.String-) and [`Class.getFields()`](https://docs.oracle.com/javase/8/docs/api/java/lang/Class.html#getFields--) methods return the public member field(s) of the class, enum, or interface represented by the `Class` object. 
> To retrieve all fields declared (but not inherited) in the `Class`, use the [`Class.getDeclaredFields()`](https://docs.oracle.com/javase/8/docs/api/java/lang/Class.html#getDeclaredFields--) method. 
> 
> [`Class.getField()`](https://docs.oracle.com/javase/8/docs/api/java/lang/Class.html#getField-java.lang.String-) 和 [`Class.getFields()`](https://docs.oracle.com/javase/8/docs/api/java/lang/Class.html#getFields--) 方法返回由 `Class` 对象表示的类、枚举或接口的公共成员字段。
> 要检索在 `Class` 中声明（但未继承）的所有字段，请使用 [`Class.getDeclaredFields()`](https://docs.oracle.com/javase/8/docs/api/java/lang/Class.html#getDeclaredFields--) 方法。


## `IllegalAccessException` when Modifying Final Fields _修改最终字段时出现 `IllegalAccessException`_


An [`IllegalAccessException`](https://docs.oracle.com/javase/8/docs/api/java/lang/IllegalAccessException.html) may be thrown if an attempt is made to get or set the value of a `private` or otherwise inaccessible field or to set the value of a `final` field (regardless of its access modifiers).


如果尝试获取或设置 `private` 或其他不可访问字段的值或设置 `final` 字段的值（无论其访问修饰符如何），则可能会抛出 [`IllegalAccessException`](https://docs.oracle.com/javase/8/docs/api/java/lang/IllegalAccessException.html) 。


The [`FieldTroubleToo`](https://docs.oracle.com/javase/tutorial/reflect/member/example/FieldTroubleToo.java) example illustrates the type of stack trace which results from attempting to set a final field.


[`FieldTroubleToo`](./example/FieldTroubleToo.java) 示例说明了尝试设置最终字段导致的堆栈跟踪类型。


```java
import java.lang.reflect.Field;

public class FieldTroubleToo {
    public final boolean b = true;

    public static void main(String... args) {
	FieldTroubleToo ft = new FieldTroubleToo();
	try {
	    Class<?> c = ft.getClass();
	    Field f = c.getDeclaredField("b");
// 	    f.setAccessible(true);  // solution
	    f.setBoolean(ft, Boolean.FALSE);   // IllegalAccessException

        // production code should handle these exceptions more gracefully
	} catch (NoSuchFieldException x) {
	    x.printStackTrace();
	} catch (IllegalArgumentException x) {
	    x.printStackTrace();
	} catch (IllegalAccessException x) {
	    x.printStackTrace();
	}
    }
}
```


```text
$ java FieldTroubleToo
java.lang.IllegalAccessException: Can not set final boolean field
  FieldTroubleToo.b to (boolean)false
        at sun.reflect.UnsafeFieldAccessorImpl.
          throwFinalFieldIllegalAccessException(UnsafeFieldAccessorImpl.java:55)
        at sun.reflect.UnsafeFieldAccessorImpl.
          throwFinalFieldIllegalAccessException(UnsafeFieldAccessorImpl.java:63)
        at sun.reflect.UnsafeQualifiedBooleanFieldAccessorImpl.setBoolean
          (UnsafeQualifiedBooleanFieldAccessorImpl.java:78)
        at java.lang.reflect.Field.setBoolean(Field.java:686)
        at FieldTroubleToo.main(FieldTroubleToo.java:12)
```


> **Tip:** _**提示：**_
> 
> An access restriction exists which prevents `final` fields from being set after initialization of the class. 
> However, `Field` is declared to extend [`AccessibleObject`](https://docs.oracle.com/javase/8/docs/api/java/lang/reflect/AccessibleObject.html) which provides the ability to suppress this check.
> 
> 存在访问限制，可防止在类初始化后设置 `final` 字段。 
> 但是， `Field` 被声明为扩展 [`AccessibleObject`](https://docs.oracle.com/javase/8/docs/api/java/lang/reflect/AccessibleObject.html) ，它提供了禁止此检查的能力。
> 
> If [`AccessibleObject.setAccessible()`](https://docs.oracle.com/javase/8/docs/api/java/lang/reflect/AccessibleObject.html#setAccessible-boolean-) succeeds, then subsequent operations on this field value will not fail do to this problem. 
> This may have unexpected side-effects; for example, sometimes the original value will continue to be used by some sections of the application even though the value has been modified. 
> [`AccessibleObject.setAccessible()`](https://docs.oracle.com/javase/8/docs/api/java/lang/reflect/AccessibleObject.html#setAccessible-boolean-) will only succeed if the operation is allowed by the security context. 
> 
> 如果 [`AccessibleObject.setAccessible()`](https://docs.oracle.com/javase/8/docs/api/java/lang/reflect/AccessibleObject.html#setAccessible-boolean-) 成功，那么后续对该字段值的操作不会失败，不会解决这个问题。
> 这可能会产生意想不到的副作用；例如，有时原始值将继续被应用程序的某些部分使用，即使该值已被修改。
> [`AccessibleObject.setAccessible()`](https://docs.oracle.com/javase/8/docs/api/java/lang/reflect/AccessibleObject.html#setAccessible-boolean-) 只有在安全上下文允许操作时才会成功。
