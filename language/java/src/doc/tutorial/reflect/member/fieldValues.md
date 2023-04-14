# Getting and Setting Field Values _获取和设置字段值_


Given an instance of a class, it is possible to use reflection to set the values of fields in that class. 
This is typically done only in special circumstances when setting the values in the usual way is not possible. 
Because such access usually violates the design intentions of the class, it should be used with the utmost discretion.


给定一个类的实例，可以使用反射来设置该类中字段的值。
这通常仅在无法以通常方式设置值的特殊情况下进行。
因为这种访问通常违反了类的设计意图，所以应该谨慎使用它。


The [`Book`](https://docs.oracle.com/javase/tutorial/reflect/member/example/Book.java) class illustrates how to set the values for long, array, and enum field types. 
Methods for getting and setting other primitive types are described in [Field](https://docs.oracle.com/javase/8/docs/api/java/lang/reflect/Field.html#method_summary).


[`Book`](example/Book.java) 类说明了如何设置 long、 array 和 enum 字段类型的值。
[Field](https://docs.oracle.com/javase/8/docs/api/java/lang/reflect/Field.html#method_summary) 中描述了获取和设置其他原始类型的方法。


```java
import java.lang.reflect.Field;
import java.util.Arrays;
import static java.lang.System.out;

enum Tweedle { DEE, DUM }

public class Book {
    public long chapters = 0;
    public String[] characters = { "Alice", "White Rabbit" };
    public Tweedle twin = Tweedle.DEE;

    public static void main(String... args) {
	Book book = new Book();
	String fmt = "%6S:  %-12s = %s%n";

	try {
	    Class<?> c = book.getClass();

	    Field chap = c.getDeclaredField("chapters");
	    out.format(fmt, "before", "chapters", book.chapters);
  	    chap.setLong(book, 12);
	    out.format(fmt, "after", "chapters", chap.getLong(book));

	    Field chars = c.getDeclaredField("characters");
	    out.format(fmt, "before", "characters",
		       Arrays.asList(book.characters));
	    String[] newChars = { "Queen", "King" };
	    chars.set(book, newChars);
	    out.format(fmt, "after", "characters",
		       Arrays.asList(book.characters));

	    Field t = c.getDeclaredField("twin");
	    out.format(fmt, "before", "twin", book.twin);
	    t.set(book, Tweedle.DUM);
	    out.format(fmt, "after", "twin", t.get(book));

        // production code should handle these exceptions more gracefully
	} catch (NoSuchFieldException x) {
	    x.printStackTrace();
	} catch (IllegalAccessException x) {
	    x.printStackTrace();
	}
    }
}
```


This is the corresponding output:


这是相应的输出：


```text
$ java Book
BEFORE:  chapters     = 0
 AFTER:  chapters     = 12
BEFORE:  characters   = [Alice, White Rabbit]
 AFTER:  characters   = [Queen, King]
BEFORE:  twin         = DEE
 AFTER:  twin         = DUM
```


**Note:** 
Setting a field's value via reflection has a certain amount of performance overhead because various operations must occur such as validating access permissions. 
From the runtime's point of view, the effects are the same, and the operation is as atomic as if the value was changed in the class code directly.


**注意：**
通过反射设置字段的值会产生一定的性能开销，因为必须进行各种操作，例如验证访问权限。
从运行时的角度来看，效果是一样的，并且操作是原子的，就好像直接在类代码中更改了值一样。


Use of reflection can cause some runtime optimizations to be lost. 
For example, the following code is highly likely be optimized by a Java virtual machine: 


使用反射可能会导致一些运行时优化丢失。
例如，以下代码极有可能被 Java 虚拟机优化：


```text
int x = 1;
x = 2;
x = 3;
```


Equivalent code using `Field.set*()` may not.


使用 `Field.set*()` 的等效代码可能不会。
