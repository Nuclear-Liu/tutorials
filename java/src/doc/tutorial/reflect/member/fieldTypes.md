# Obtaining Field Types _获取字段类型_


A field may be either of primitive or reference type. 
There are eight primitive types: `boolean`, `byte`, `short`, `int`, `long`, `char`, `float`, and `double`. 
A reference type is anything that is a direct or indirect subclass of [`java.lang.Object`](https://docs.oracle.com/javase/8/docs/api/java/lang/Object.html) including interfaces, arrays, and enumerated types.


字段可以是原始类型或引用类型。
有八种原始类型： `boolean`、 `byte`、 `short`、 `int`、 `long`、 `char`、 `float` 和 `double` 。
引用类型是 [`java.lang.Object`](https://docs.oracle.com/javase/8/docs/api/java/lang/Object.html) 的直接或间接子类，包括接口、数组和枚举类型。


The [`FieldSpy`](https://docs.oracle.com/javase/tutorial/reflect/member/example/FieldSpy.java) example prints the field's type and generic type given a fully-qualified binary class name and field name.


[`FieldSpy`](example/FieldSpy.java) 示例在给定完全限定的二进制类名称和字段名称的情况下打印字段的类型和泛型类型。


```java
import java.lang.reflect.Field;
import java.util.List;

public class FieldSpy<T> {
    public boolean[][] b = {{ false, false }, { true, true } };
    public String name  = "Alice";
    public List<Integer> list;
    public T val;

    public static void main(String... args) {
	try {
	    Class<?> c = Class.forName(args[0]);
	    Field f = c.getField(args[1]);
	    System.out.format("Type: %s%n", f.getType());
	    System.out.format("GenericType: %s%n", f.getGenericType());

        // production code should handle these exceptions more gracefully
	} catch (ClassNotFoundException x) {
	    x.printStackTrace();
	} catch (NoSuchFieldException x) {
	    x.printStackTrace();
	}
    }
}
```


Sample output to retrieve the type of the three public fields in this class (`b`, `name`, and the parameterized type `list`), follows. 
User input is in italics.


示例输出检索此类中三个公共字段的类型（ `b`、 `name` 和参数化类型 `list` ），如下所示。
用户输入以斜体显示。


```shell
$ java FieldSpy FieldSpy b
Type: class [[Z
GenericType: class [[Z
$ java FieldSpy FieldSpy name
Type: class java.lang.String
GenericType: class java.lang.String
$ java FieldSpy FieldSpy list
Type: interface java.util.List
GenericType: java.util.List<java.lang.Integer>
$ java FieldSpy FieldSpy val
Type: class java.lang.Object
GenericType: T
```


The type for the field `b` is two-dimensional array of boolean. 
The syntax for the type name is described in [`Class.getName()`](https://docs.oracle.com/javase/8/docs/api/java/lang/Class.html#getName--).


字段 `b` 的类型是布尔的二维数组。
[`Class.getName()`](https://docs.oracle.com/javase/8/docs/api/java/lang/Class.html#getName--) 中描述了类型名称的语法。


The type for the field `val` is reported as `java.lang.Object` because generics are implemented via type erasure which removes all information regarding generic types during compilation. 
Thus `T` is replaced by the upper bound of the type variable, in this case, `java.lang.Object`.


字段 `val` 的类型报告为 `java.lang.Object` 因为泛型是通过类型擦除实现的，它在编译期间删除了有关泛型类型的所有信息。
因此， `T` 被类型变量的上限替换，在这种情况下， `java.lang.Object` 。


[`Field.getGenericType()`](https://docs.oracle.com/javase/8/docs/api/java/lang/reflect/Field.html#getGenericType--) will consult the Signature Attribute in the class file if it's present. 
If the attribute isn't available, it falls back on [`Field.getType()`](https://docs.oracle.com/javase/8/docs/api/java/lang/reflect/Field.html#getType--) which was not changed by the introduction of generics. 
The other methods in reflection with name `getGenericFoo` for some value of _Foo_ are implemented similarly.


[`Field.getGenericType()`](https://docs.oracle.com/javase/8/docs/api/java/lang/reflect/Field.html#getGenericType--) 将查询类文件中的签名属性（如果存在）。
如果该属性不可用，它会回退到 [`Field.getType()`](https://docs.oracle.com/javase/8/docs/api/java/lang/reflect/Field.html#getType--) 上，这并没有因引入泛型而改变。
对于 _Foo_ 的某些值，名称为 `getGenericFoo` 的反射中的其他方法以类似方式实现。
