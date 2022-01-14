# Object Ordering _对象排序_


A `List` `l` may be sorted as follows.


`List` `l` 可以按如下方式排序。


`Collections.sort(l);`


If the `List` consists of `String` elements, it will be sorted into alphabetical order. 
If it consists of `Date` elements, it will be sorted into chronological order. 
How does this happen? 
`String` and `Date` both implement the [`Comparable`](https://docs.oracle.com/javase/8/docs/api/java/lang/Comparable.html) interface. 
`Comparable` implementations provide a _natural ordering_ for a class, which allows objects of that class to be sorted automatically. 
The following table summarizes some of the more important Java platform classes that implement `Comparable`.


如果 `List` 包含 `String` 元素，它将按字母顺序排序。
如果它由 `Date` 元素组成，它将按时间顺序排序。
这是怎么发生的？
`String` 和 `Date` 都实现了 [`Comparable`](https://docs.oracle.com/javase/8/docs/api/java/lang/Comparable.html) 接口。
`Comparable` 实现为一个类提供了一个*自然排序*，它允许该类的对象自动排序。
下表总结了一些实现 `Comparable` 的更重要的 Java 平台类。


**Classes Implementing `Comparable`**

| Class          | Natural Ordering                            |
|----------------|---------------------------------------------|
| `Byte`         | Signed numerical                            |
| `Character`    | Unsigned numerical                          |
| `Long`         | Signed numerical                            |
| `Integer`      | Signed numerical                            |
| `Short`        | Signed numerical                            |
| `Double`       | Signed numerical                            |
| `Float`        | Signed numerical                            |
| `BigInteger`   | Signed numerical                            |
| `BigDecimal`   | Signed numerical                            |
| `Boolean`      | `Boolean.FALSE < Boolean.TRUE`              |
| `File`         | System-dependent lexicographic on path name |
| `String`       | Lexicographic                               |
| `Date`         | Chronological                               |
| `CollationKey` | Locale-specific lexicographic               |


**实现 `Comparable` 的类**

| 类              | 原生排序                           |
|----------------|--------------------------------|
| `Byte`         | 带符号的数字                         |
| `Character`    | 无符号数字                          |
| `Long`         | 带符号的数字                         |
| `Integer`      | 带符号的数字                         |
| `Short`        | 带符号的数字                         |
| `Double`       | 带符号的数字                         |
| `Float`        | 带符号的数字                         |
| `BigInteger`   | 带符号的数字                         |
| `BigDecimal`   | 带符号的数字                         |
| `Boolean`      | `Boolean.FALSE < Boolean.TRUE` |
| `File`         | 路径名上依赖于系统的字典                   |
| `String`       | 字典序                            |
| `Date`         | 按时间顺序排列                        |
| `CollationKey` | 特定于语言环境的词典                     |


If you try to sort a list, the elements of which do not implement `Comparable`, `Collections.sort(list)` will throw a [`ClassCastException`](https://docs.oracle.com/javase/8/docs/api/java/lang/ClassCastException.html). 
Similarly, `Collections.sort(list, comparator)` will throw a `ClassCastException` if you try to sort a list whose elements cannot be compared to one another using the `comparator`. 
Elements that can be compared to one another are called _mutually comparable_. 
Although elements of different types may be mutually comparable, none of the classes listed here permit interclass comparison.


如果您尝试对列表进行排序，其中的元素未实现 `Comparable` ，`Collections.sort(list)` 将抛出 [`ClassCastException`](https://docs.oracle.com/javase/8/docs/api/java/lang/ClassCastException.html) 。
类似地，如果您尝试使用 `comparator` 对其元素无法相互比较的列表进行排序， `Collections.sort(list, comparator)` 将抛出 `ClassCastException` 。
可以相互比较的元素称为*相互比较*。
尽管不同类型的元素可以相互比较，但此处列出的所有类都不允许进行类间比较。


This is all you really need to know about the `Comparable` interface if you just want to sort lists of comparable elements or to create sorted collections of them. 
The next section will be of interest to you if you want to implement your own `Comparable` type.


如果您只想对可比较元素的列表进行排序或创建它们的排序集合，这就是您真正需要了解的有关 `Comparable` 接口的全部内容。
如果您想实现自己的 `Comparable` 类型，下一部分将会对您感兴趣。


## Writing Your Own Comparable Types _编写自己的可比类型_


The `Comparable` interface consists of the following method.


`Comparable` 接口由以下方法组成。


```text
public interface Comparable<T> {
    public int compareTo(T o);
}
```


The `compareTo` method compares the receiving object with the specified object and returns a negative integer, 0, or a positive integer depending on whether the receiving object is less than, equal to, or greater than the specified object. 
If the specified object cannot be compared to the receiving object, the method throws a `ClassCastException`.


`compareTo` 方法将接收对象与指定对象进行比较，并根据接收对象是小于、等于还是大于指定对象返回负整数、0 或正整数。
如果指定的对象无法与接收对象进行比较，则该方法将抛出 `ClassCastException` 。


The [following class representing a person's name](https://docs.oracle.com/javase/tutorial/collections/interfaces/examples/Name.java) implements `Comparable`.


[following class representing a person's name](./examples/Name.java) 实现了 `Comparable`。


```java
import java.util.*;

public class Name implements Comparable<Name> {
    private final String firstName, lastName;

    public Name(String firstName, String lastName) {
        if (firstName == null || lastName == null)
            throw new NullPointerException();
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public String firstName() { return firstName; }
    public String lastName()  { return lastName;  }

    public boolean equals(Object o) {
        if (!(o instanceof Name))
            return false;
        Name n = (Name) o;
        return n.firstName.equals(firstName) && n.lastName.equals(lastName);
    }

    public int hashCode() {
        return 31*firstName.hashCode() + lastName.hashCode();
    }

    public String toString() {
	return firstName + " " + lastName;
    }

    public int compareTo(Name n) {
        int lastCmp = lastName.compareTo(n.lastName);
        return (lastCmp != 0 ? lastCmp : firstName.compareTo(n.firstName));
    }
}
```


To keep the preceding example short, the class is somewhat limited: It doesn't support middle names, it demands both a first and a last name, and it is not internationalized in any way. 
Nonetheless, it illustrates the following important points:


为了保持前面的例子简短，这个类有一些限制：它不支持中间名，它需要名字和姓氏，并且它没有以任何方式国际化。
尽管如此，它说明了以下要点：


* `Name` objects are _immutable_. 
  All other things being equal, immutable types are the way to go, especially for objects that will be used as elements in `Set`s or as keys in `Map`s. 
  These collections will break if you modify their elements or keys while they're in the collection.

* `Name` 对象是*不可变的*。
  在所有其他条件相同的情况下，不可变类型是可行的方法，特别是对于将用作 `Set` 中的元素或用作 `Map` 中的键的对象。
  如果您在集合中修改它们的元素或键，这些集合将打破。

* The constructor checks its arguments for `null`. 
  This ensures that all `Name` objects are well formed so that none of the other methods will ever throw a `NullPointerException`.

* 构造函数检查其参数是否为 `null`。
  这确保了所有 `Name` 对象的格式正确，因此其他方法都不会抛出 `NullPointerException`。

* The `hashCode` method is redefined. 
  This is essential for any class that redefines the `equals` method. (Equal objects must have equal hash codes.)

* `hashCode` 方法被重新定义。
  这对于任何重新定义 `equals` 方法的类来说都是必不可少的。 （相同的对象必须具有相同的哈希码。）

* The `equals` method returns `false` if the specified object is `null` or of an inappropriate type. 
  The `compareTo` method throws a runtime exception under these circumstances. 
  Both of these behaviors are required by the general contracts of the respective methods.

* 如果指定的对象是 `null` 或不合适的类型， `equals` 方法会返回 `false` 。
  在这些情况下， `compareTo` 方法会引发运行时异常。
  这两种行为都是各自方法的一般契约所要求的。

* The `toString` method has been redefined so it prints the `Name` in human-readable form. 
  This is always a good idea, especially for objects that are going to get put into collections. 
  The various collection types' `toString` methods depend on the `toString` methods of their elements, keys, and values.

* `toString` 方法已被重新定义，因此它以人类可读的形式打印 `Name` 。
  这总是一个好主意，尤其是对于将要放入集合中的对象。
  各种集合类型的 `toString` 方法取决于其元素、键和值的 `toString` 方法。


Since this section is about element ordering, let's talk a bit more about `Name`'s `compareTo` method. 
It implements the standard name-ordering algorithm, where last names take precedence over first names. 
This is exactly what you want in a natural ordering. 
It would be very confusing indeed if the natural ordering were unnatural!


由于本节是关于元素排序的，所以让我们多谈谈 `Name` 的 `compareTo` 方法。
它实现了标准的名称排序算法，其中姓氏优先于名字。
这正是您想要的自然顺序。
如果自然排序不自然，那确实会非常混乱！


Take a look at how `compareTo` is implemented, because it's quite typical. 
First, you compare the most significant part of the object (in this case, the last name). 
Often, you can just use the natural ordering of the part's type. 
In this case, the part is a `String` and the natural (lexicographic) ordering is exactly what's called for. 
If the comparison results in anything other than zero, which represents equality, you're done: You just return the result. 
If the most significant parts are equal, you go on to compare the next most-significant parts. 
In this case, there are only two parts — first name and last name. 
If there were more parts, you'd proceed in the obvious fashion, comparing parts until you found two that weren't equal or you were comparing the least-significant parts, at which point you'd return the result of the comparison.


看看 `compareTo` 是如何实现的，因为它很典型。
首先，您比较对象的最重要部分（在本例中为姓氏）。
通常，您可以只使用部件类型的自然顺序。
在这种情况下，该部分是一个 `String` ，自然（字典）排序正是我们所要求的。
如果比较结果不是零，代表相等，你就完成了：你只返回结果。
如果最重要的部分相等，则继续比较下一个最重要的部分。
在这种情况下，只有两个部分 —— 名字和姓氏。
如果有更多部分，您将以明显的方式继续，比较部分，直到您发现两个不相等或者您正在比较最不重要的部分，此时您将返回比较结果。


Just to show that it all works, here's [a program that builds a list of names and sorts them](https://docs.oracle.com/javase/tutorial/collections/interfaces/examples/NameSort.java).


只是为了证明这一切都有效，这里是 [a program that builds a list of names and sorts them](./examples/NameSort.java) 。


```java
import java.util.*;

public class NameSort {
    public static void main(String[] args) {
        Name nameArray[] = {
            new Name("John", "Smith"),
            new Name("Karl", "Ng"),
            new Name("Jeff", "Smith"),
            new Name("Tom", "Rich")
        };

        List<Name> names = Arrays.asList(nameArray);
        Collections.sort(names);
        System.out.println(names);
    }
}
```


If you run this program, here's what it prints.


如果你运行这个程序，它会打印出来。


`[Karl Ng, Tom Rich, Jeff Smith, John Smith]`


There are four restrictions on the behavior of the `compareTo` method, which we won't go into now because they're fairly technical and boring and are better left in the API documentation. 
It's really important that all classes that implement `Comparable` obey these restrictions, so read the documentation for `Comparable` if you're writing a class that implements it. 
Attempting to sort a list of objects that violate the restrictions has undefined behavior. 
Technically speaking, these restrictions ensure that the natural ordering is a _total order_ on the objects of a class that implements it; this is necessary to ensure that sorting is well defined.


`compareTo` 方法的行为有**四个限制**，我们现在不会讨论，因为它们相当技术性和无聊，最好留在 API 文档中。
实现 `Comparable` 的所有类都遵守这些限制非常重要，因此如果您正在编写实现它的类，请阅读 `Comparable` 的文档。
尝试对违反限制的对象列表进行排序具有未定义的行为。
从技术上讲，这些限制确保自然排序是实现它的类的对象的 _total order_ ；这对于确保明确定义排序是必要的。


## Comparators _比较器_


What if you want to sort some objects in an order other than their natural ordering? 
Or what if you want to sort some objects that don't implement `Comparable`? 
To do either of these things, you'll need to provide a [`Comparator`](https://docs.oracle.com/javase/8/docs/api/java/util/Comparator.html) — an object that encapsulates an ordering. 
Like the `Comparable` interface, the `Comparator` interface consists of a single method.


如果您想以不同于自然顺序的顺序对某些对象进行排序怎么办？
或者如果你想对一些没有实现 `Comparable` 的对象进行排序呢？
要执行上述任一操作，您需要提供一个 [`Comparator`](https://docs.oracle.com/javase/8/docs/api/java/util/Comparator.html) — 一个封装排序的对象。
与 `Comparable` 接口一样， `Comparator` 接口由一个方法组成。


```java
public interface Comparator<T> {
    int compare(T o1, T o2);
}
```


The `compare` method compares its two arguments, returning a negative integer, 0, or a positive integer depending on whether the first argument is less than, equal to, or greater than the second. 
If either of the arguments has an inappropriate type for the `Comparator`, the `compare` method throws a `ClassCastException`.


`compare` 方法比较它的两个参数，根据第一个参数是小于、等于还是大于第二个参数返回一个负整数、0 或一个正整数。
如果任一参数的类型不适合 `Comparator` ，则 `compare` 方法会抛出 `ClassCastException` 。


Much of what was said about `Comparable` applies to `Comparator` as well. 
Writing a `compare` method is nearly identical to writing a `compareTo` method, except that the former gets both objects passed in as arguments. 
The `compare` method has to obey the same four technical restrictions as `Comparable`'s `compareTo` method for the same reason — a `Comparator` must induce a total order on the objects it compares.


关于 `Comparable` 的大部分内容也适用于 `Comparator` 。
编写 `compare` 方法与编写 `compareTo` 方法几乎相同，只是前者将两个对象都作为参数传入。
`compare` 方法必须遵守与 `Comparable` 的 `compareTo` 方法相同的四个技术限制，原因相同 - `Comparator` 必须在它比较的对象上引入总顺序。


Suppose you have a class called `Employee`, as follows.


假设您有一个名为 `Employee` 的类，如下所示。


```text
public class Employee implements Comparable<Employee> {
    public Name name()     { ... }
    public int number()    { ... }
    public Date hireDate() { ... }
       ...
}
```


Let's assume that the natural ordering of `Employee` instances is `Name` ordering (as defined in the previous example) on employee name. 
Unfortunately, the boss has asked for a list of employees in order of seniority. 
This means we have to do some work, but not much. 
The following program will produce the required list.


让我们假设 `Employee` 实例的自然排序是员工姓名的 `Name` 排序（如前面示例中所定义）。
不幸的是，老板要求按资历排列员工名单。
这意味着我们必须做一些工作，但不多。
以下程序将生成所需的列表。


```text
import java.util.*;
public class EmpSort {
    static final Comparator<Employee> SENIORITY_ORDER = 
                                        new Comparator<Employee>() {
            public int compare(Employee e1, Employee e2) {
                return e2.hireDate().compareTo(e1.hireDate());
            }
    };

    // Employee database
    static final Collection<Employee> employees = ... ;

    public static void main(String[] args) {
        List<Employee> e = new ArrayList<Employee>(employees);
        Collections.sort(e, SENIORITY_ORDER);
        System.out.println(e);
    }
}
```


The `Comparator` in the program is reasonably straightforward. 
It relies on the natural ordering of `Date` applied to the values returned by the `hireDate` accessor method. 
Note that the `Comparator` passes the hire date of its second argument to its first rather than vice versa. 
The reason is that the employee who was hired most recently is the least senior; sorting in the order of hire date would put the list in reverse seniority order. 
Another technique people sometimes use to achieve this effect is to maintain the argument order but to negate the result of the comparison.


程序中的 `Comparator` 相当简单。
它依赖于应用于 `hireDate` 访问器方法返回的值的 `Date` 的自然顺序。
请注意， `Comparator` 将其第二个参数的雇用日期传递给其第一个参数，而不是反之亦然。
原因是最近聘用的员工资历最低；按雇用日期的顺序排序将使列表以相反的资历顺序排列。
人们有时用来实现这种效果的另一种技术是保持参数顺序但否定比较的结果。


```text
// Don't do this!!
return -r1.hireDate().compareTo(r2.hireDate());
```


You should always use the former technique in favor of the latter because the latter is not guaranteed to work. 
The reason for this is that the `compareTo` method can return any negative `int` if its argument is less than the object on which it is invoked. 
There is one negative `int` that remains negative when negated, strange as it may seem.


您应该始终使用前一种技术来支持后者，因为后者不能保证有效。
这样做的原因是， `compareTo` 方法可以返回任何负的 `int` ，如果它的参数小于调用它的对象。
有一个否定的 `int` 在被否定时仍然是否定的，尽管看起来很奇怪。


`-Integer.MIN_VALUE == Integer.MIN_VALUE`


The `Comparator` in the preceding program works fine for sorting a `List`, but it does have one deficiency: 
It cannot be used to order a sorted collection, such as `TreeSet`, because it generates an ordering that is _not compatible_ with `equals`. 
This means that this `Comparator` equates objects that the `equals` method does not. 
In particular, any two employees who were hired on the same date will compare as equal. 
When you're sorting a `List`, this doesn't matter; but when you're using the `Comparator` to order a sorted collection, it's fatal. 
If you use this `Comparator` to insert multiple employees hired on the same date into a `TreeSet`, only the first one will be added to the set; the second will be seen as a duplicate element and will be ignored.


前面程序中的 `Comparator` 可以很好地对 `List` 进行排序，但它确实有一个不足：
它不能用于对已排序的集合进行排序，例如 `TreeSet` ，因为它生成的排序与 `equals` _不兼容_ 。
这意味着这个 `Comparator` 等同于 `equals` 方法不等同的对象。
特别是，在同一日期雇用的任何两名员工都将进行比较。
当您对 `List` 进行排序时，这无关紧要；但是当您使用 `Comparator` 来排序排序集合时，这是致命的。
如果您使用此 `Comparator` 将同一日期雇用的多个员工插入到 `TreeSet` 中，则只会将第一个添加到集合中；第二个将被视为重复元素并将被忽略。


To fix this problem, simply tweak the `Comparator` so that it produces an ordering that _is compatible_ with `equals`. 
In other words, tweak it so that the only elements seen as equal when using `compare` are those that are also seen as equal when compared using `equals`. 
The way to do this is to perform a two-part comparison (as for `Name`), where the first part is the one we're interested in — in this case, the hire date — and the second part is an attribute that uniquely identifies the object. 
Here the employee number is the obvious attribute. 
This is the `Comparator` that results.


要解决这个问题，只需调整 `Comparator` 使其产生一个与 `equals` 兼容的排序。
换句话说，调整它以便在使用 `compare` 时唯一被视为相等的元素是那些在使用 `equals` 进行比较时也被视为相等的元素。
做到这一点的方法是执行两部分比较（对于 `Name` ），其中第一部分是我们感兴趣的部分——在这种情况下，是雇用日期——第二部分是一个属性唯一标识对象。
这里员工编号是明显的属性。
这是产生的 `Comparator` 。


```text
static final Comparator<Employee> SENIORITY_ORDER = 
                                        new Comparator<Employee>() {
    public int compare(Employee e1, Employee e2) {
        int dateCmp = e2.hireDate().compareTo(e1.hireDate());
        if (dateCmp != 0)
            return dateCmp;

        return (e1.number() < e2.number() ? -1 :
               (e1.number() == e2.number() ? 0 : 1));
    }
};
```


One last note: You might be tempted to replace the final `return` statement in the `Comparator` with the simpler:


最后一点：您可能想用更简单的替换 `Comparator` 中的最终 `return` 语句：


`return e1.number() - e2.number();`


Don't do it unless you're _absolutely sure_ no one will ever have a negative employee number! 
This trick does not work in general because the signed integer type is not big enough to represent the difference of two arbitrary signed integers. 
If `i` is a large positive integer and j is a large negative integer, `i - j` will overflow and will return a negative integer. 
The resulting `comparator` violates one of the four technical restrictions we keep talking about (transitivity) and produces horrible, subtle bugs. 
This is not a purely theoretical concern; people get burned by it.


不要这样做，除非您*绝对确定*没有人会有负的员工编号！
这个技巧通常不起作用，因为有符号整数类型不足以表示两个任意有符号整数的差异。
如果 `i` 是一个大的正整数，而 j 是一个大的负整数，则 `i - j` 将溢出并返回一个负整数。
由此产生的 `comparator` 违反了我们一直在谈论的四个技术限制之一（传递性），并产生了可怕的、微妙的错误。
这不是纯粹的理论问题；人们被它烧死。
