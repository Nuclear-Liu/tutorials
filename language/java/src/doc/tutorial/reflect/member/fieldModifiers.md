# Retrieving and Parsing Field Modifiers _检索和解析字段修饰符_


There are several modifiers that may be part of a field declaration:


有几个修饰符可能是字段声明的一部分：


* Access modifiers: `public`, `protected`, and `private`

* 访问修饰符： `public` 、 `protected` 和 `private`

* Field-specific modifiers governing runtime behavior: `transient` and `volatile`

* 控制运行时行为的特定于字段的修饰符： `transient` 和 `volatile`

* Modifier restricting to one instance: `static`

* 修饰符限制为一个实例： `static`

* Modifier prohibiting value modification: `final`

* 禁止值修改的修饰符： `final`

* Annotations

* 注解


The method [`Field.getModifiers()`](https://docs.oracle.com/javase/8/docs/api/java/lang/reflect/Field.html#getModifiers--) can be used to return the integer representing the set of declared modifiers for the field. 
The bits representing the modifiers in this integer are defined in [`java.lang.reflect.Modifier`](https://docs.oracle.com/javase/8/docs/api/java/lang/reflect/Modifier.html).


方法 [`Field.getModifiers()`](https://docs.oracle.com/javase/8/docs/api/java/lang/reflect/Field.html#getModifiers--) 可用于返回表示字段声明的修饰符集的整数。
在这个整数中表示修饰符的位在 [`java.lang.reflect.Modifier`](https://docs.oracle.com/javase/8/docs/api/java/lang/reflect/Modifier.html) 中定义。


The [`FieldModifierSpy`](https://docs.oracle.com/javase/tutorial/reflect/member/example/FieldModifierSpy.java) example illustrates how to search for fields with a given modifier. 
It also determines whether the located field is synthetic (compiler-generated) or is an enum constant by invoking [`Field.isSynthetic(https://docs.oracle.com/javase/8/docs/api/java/lang/reflect/Field.html#isSynthetic--)`]() and [`Field.isEnumCostant()`](https://docs.oracle.com/javase/8/docs/api/java/lang/reflect/Field.html#isEnumConstant--) respectively.


[`FieldModifierSpy`](example/FieldModifierSpy.java) 示例说明了如何搜索具有给定修饰符的字段。
它还通过分别调用 [`Field.isSynthetic()`](https://docs.oracle.com/javase/8/docs/api/java/lang/reflect/Field.html#isSynthetic--) 和 [`Field.isEnumCostant()`](https://docs.oracle.com/javase/8/docs/api/java/lang/reflect/Field.html#isEnumConstant--) 来确定定位的字段是合成的（编译器生成的）还是枚举常量。


```java
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import static java.lang.System.out;

enum Spy { BLACK , WHITE }

public class FieldModifierSpy {
    volatile int share;
    int instance;
    class Inner {}

    public static void main(String... args) {
	try {
	    Class<?> c = Class.forName(args[0]);
	    int searchMods = 0x0;
	    for (int i = 1; i < args.length; i++) {
		searchMods |= modifierFromString(args[i]);
	    }

	    Field[] flds = c.getDeclaredFields();
	    out.format("Fields in Class '%s' containing modifiers:  %s%n",
		       c.getName(),
		       Modifier.toString(searchMods));
	    boolean found = false;
	    for (Field f : flds) {
		int foundMods = f.getModifiers();
		// Require all of the requested modifiers to be present
		if ((foundMods & searchMods) == searchMods) {
		    out.format("%-8s [ synthetic=%-5b enum_constant=%-5b ]%n",
			       f.getName(), f.isSynthetic(),
			       f.isEnumConstant());
		    found = true;
		}
	    }

	    if (!found) {
		out.format("No matching fields%n");
	    }

        // production code should handle this exception more gracefully
	} catch (ClassNotFoundException x) {
	    x.printStackTrace();
	}
    }

    private static int modifierFromString(String s) {
	int m = 0x0;
	if ("public".equals(s))           m |= Modifier.PUBLIC;
	else if ("protected".equals(s))   m |= Modifier.PROTECTED;
	else if ("private".equals(s))     m |= Modifier.PRIVATE;
	else if ("static".equals(s))      m |= Modifier.STATIC;
	else if ("final".equals(s))       m |= Modifier.FINAL;
	else if ("transient".equals(s))   m |= Modifier.TRANSIENT;
	else if ("volatile".equals(s))    m |= Modifier.VOLATILE;
	return m;
    }
}
```


Sample output follows:


示例输出如下：


```text
$ java FieldModifierSpy FieldModifierSpy volatile
Fields in Class 'FieldModifierSpy' containing modifiers:  volatile
share    [ synthetic=false enum_constant=false ]

$ java FieldModifierSpy Spy public
Fields in Class 'Spy' containing modifiers:  public
BLACK    [ synthetic=false enum_constant=true  ]
WHITE    [ synthetic=false enum_constant=true  ]

$ java FieldModifierSpy FieldModifierSpy\$Inner final
Fields in Class 'FieldModifierSpy$Inner' containing modifiers:  final
this$0   [ synthetic=true  enum_constant=false ]

$ java FieldModifierSpy Spy private static final
Fields in Class 'Spy' containing modifiers:  private static final
$VALUES  [ synthetic=true  enum_constant=false ]
```


Notice that some fields are reported even though they are not declared in the original code. 
This is because the compiler will generate some synthetic fields which are needed during runtime. 
To test whether a field is synthetic, the example invokes [`Field.isSynthetic()`](https://docs.oracle.com/javase/8/docs/api/java/lang/reflect/Field.html#isSynthetic--). 
The set of synthetic fields is compiler-dependent; however commonly used fields include `this$0` for inner classes (that is, nested classes that are not static member classes) to reference the outermost enclosing class and `$VALUES` used by enums to implement the implicitly defined static method `values()`. 
The names of synthetic class members are not specified and may not be the same in all compiler implementations or releases. 
These and other synthetic fields will be included in the array returned by [`Class.getDeclaredFields()`](https://docs.oracle.com/javase/8/docs/api/java/lang/Class.html#getDeclaredFields--) but not identified by [`Class.getField()`](https://docs.oracle.com/javase/8/docs/api/java/lang/Class.html#getField-java.lang.String-) since synthetic members are not typically `public`.


请注意，即使在原始代码中未声明某些字段，也会报告它们。
这是因为编译器会生成一些在运行时需要的合成字段。
为了测试一个字段是否是合成的，该示例调用 [`Field.isSynthetic()`](https://docs.oracle.com/javase/8/docs/api/java/lang/reflect/Field.html#isSynthetic--) 。
合成字段集取决于编译器；然而，常用的字段包括用于内部类（即不是静态成员类的嵌套类）的 `this$0` 以引用最外层的封闭类，以及枚举用于实现隐式定义的静态方法 `values()` 的 `$VALUES` 。
合成类成员的名称未指定，并且在所有编译器实现或版本中可能不同。
这些和其他合成字段将包含在 [`Class.getDeclaredFields()`](https://docs.oracle.com/javase/8/docs/api/java/lang/Class.html#getDeclaredFields--) 返回的数组中，但不会由 [`Class.getField()`](https://docs.oracle.com/javase/8/docs/api/java/lang/Class.html#getField-java.lang.String-) 标识，因为合成成员通常不是 `public` 。


Because [`Field`](https://docs.oracle.com/javase/8/docs/api/java/lang/reflect/Field.html) implements the interface [`java.lang.reflect.AnnotatedElement`](**https://docs.oracle.com/javase/8/docs/api/java/lang/reflect/AnnotatedElement.html**), it is possible to retrieve any runtime annotation with [`java.lang.annotation.RetentionPolicy.RUNTIME`](https://docs.oracle.com/javase/8/docs/api/java/lang/annotation/RetentionPolicy.html#RUNTIME). 
For an example of obtaining annotations see the section [Examining Class Modifiers and Types](https://docs.oracle.com/javase/tutorial/reflect/class/classModifiers.html).


因为 [`Field`](https://docs.oracle.com/javase/8/docs/api/java/lang/reflect/Field.html) 实现了接口 [`java.lang.reflect.AnnotatedElement`](https://docs.oracle.com/javase/8/docs/api/java/lang/reflect/AnnotatedElement.html) ，所以可以使用 [`java.lang.annotation.RetentionPolicy.RUNTIME`](https://docs.oracle.com/javase/8/docs/api/java/lang/annotation/RetentionPolicy.html#RUNTIME) 检索任何运行时注解。
有关获取注释的示例，请参见 [Examining Class Modifiers and Types](../class/classModifiers.md) 部分。
