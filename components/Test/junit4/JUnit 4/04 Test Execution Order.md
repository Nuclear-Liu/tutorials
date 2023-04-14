# Test execution order *测试执行顺序*

> Kevin Cooney edited this page on Mar 28 · 15 revisions 

> 凯文·库尼 (Kevin Cooney) 于 3 月 28 日编辑了此页面 · 15 次修订

# Test execution order *测试执行顺序*

By design, JUnit does not specify the execution order of test method invocations. 
Originally the methods were simply invoked in the order returned by the reflection API. 
However, using the JVM order is unwise since the Java platform does not specify any particular order, and in fact JDK 7 returns a more or less random order. 
Of course, well-written test code would not assume any order, but some do, and a predictable failure is better than a random failure on certain platforms.


按照设计，JUnit 不指定测试方法调用的执行顺序。
最初，这些方法只是按照反射 API 返回的顺序调用。
然而，使用 JVM 顺序是不明智的，因为 Java 平台没有指定任何特定的顺序，实际上 JDK 7 返回或多或少的随机顺序。
当然，编写良好的测试代码不会假定任何顺序，但有些会这样做，并且在某些平台上，可预测的故障比随机故障要好。

---

From version 4.11, JUnit will by default use a deterministic, but not predictable, order. 
The following sections describe how to change the method execution order


从 4.11 版开始，JUnit 将默认使用确定性但不可预测的顺序。
以下部分描述了如何更改方法执行顺序

---

## `@OrderWith` annotation *`@OrderWith` 注解*

From version 4.13, to can specify a different method execution order via the [`@OrderWith` annotation](https://junit.org/junit4/javadoc/latest/org/junit/runner/OrderWith.html). 
See the example below.


从 4.13 版本开始，可以通过 [`@OrderWith` annotation](https://junit.org/junit4/javadoc/latest/org/junit/runner/OrderWith.html) 指定不同的方法执行顺序。
请参阅下面的示例。

---

The parameter to `@OrderWith` is an instance of `Ordering.Factory`. 
JUnit provides implementations of `Ordering.Factory` in `org.junit.tests.manipulation`. 
Users can create their own instances of `Ordering.Factory` to provide `Ordering` implementations that reorder tests.
Implementations of `Ordering.Factory` should have a public constructor that takes in a Ordering.Context (see [the Alphanumeric source code](https://github.com/junit-team/junit4/blob/main/src/main/java/org/junit/runner/manipulation/Alphanumeric.java) for an example).


`@OrderWith` 的参数是 `Ordering.Factory` 的一个实例。
JUnit 在 `org.junit.tests.manipulation` 中提供了 `Ordering.Factory` 的实现。
用户可以创建自己的 `Ordering.Factory` 实例来提供重新排序测试的 `Ordering` 实现。
`Ordering.Factory` 的实现应该有一个接受 Ordering.Context 的公共构造函数（参见 [the Alphanumeric source code](https://github.com/junit-team/junit4/blob/main/src/main/java/org/junit/runner/manipulation/Alphanumeric.java) 示例）。

---

The `Ordering` class provides some static methods that simplify creation of `Ordering` instances (for example, `Ordering.shuffledBy(Random random)` will reorder the tests using the passed-in `Random` instance). 
The `Request` class has been updated to have a `orderWith(Ordering)` method that mirrors the older `sortWith(Comparator<Description> comparator)` method.


`Ordering` 类提供了一些静态方法来简化 `Ordering` 实例的创建（例如， `Ordering.shuffledBy(Random random)` 将使用传入的 `Random` 实例对测试重新排序）。
`Request` 类已更新为具有 `orderWith(Ordering)` 方法，该方法反映了较旧的 `sortWith(Comparator<Description> Comparator)` 方法。

---

The `@OrderWith` annotation works with any runner that implements `Orderable`. 
The `ParentRunner` abstract class was been retrofitted to implement `Orderable` so the runners provided by JUnit support `@OrderWith`, and many third-party runners will support it.


`@OrderWith` 注解适用于任何实现 `Orderable` 的运行器。
`ParentRunner` 抽象类被改造为实现 `Orderable` ，因此 JUnit 提供的运行器支持 `@OrderWith`，并且许多第三方运行器将支持它。
---

### Example *示例*

```java
import org.junit.Test;
import org.junit.runner.OrderWith;
import org.junit.runner.manipulation.Alphanumeric;

@OrderWith(Alphanumeric.class)
public class TestMethodOrder {

    @Test
    public void testA() {
        System.out.println("first");
    }
    @Test
    public void testB() {
        System.out.println("second");
    }
    @Test
    public void testC() {
        System.out.println("third");
    }
}

```

Above code will execute the test methods in the order of their names, sorted in ascending order.


上面的代码将按照名称的顺序执行测试方法，按升序排序。

---

## `@FixMethodOrder` annotation *`@FixMethodOrder` 注解*

From version 4.11, you can change the test execution order simply annotate your test class using `@FixMethodOrder` and specify one of the available MethodSorters:


从版本 4.11 开始，您可以更改测试执行顺序，只需使用 `@FixMethodOrder` 注解您的测试类，并指定一个可用的 MethodSorter ：

---

`@FixMethodOrder(MethodSorters.JVM)`: 
Leaves the test methods in the order returned by the JVM. 
This order may vary from run to run.


`@FixMethodOrder(MethodSorters.JVM)`:
按照 JVM 返回的顺序保留测试方法。
此顺序可能因运行而异。

---

`@FixMethodOrder(MethodSorters.NAME_ASCENDING)`: Sorts the test methods by method name, in lexicographic order.


`@FixMethodOrder(MethodSorters.NAME_ASCENDING)`: 按方法名称字典顺序对测试方法进行排序。

---

If you do no specify either `@FixMethodOrder` or `@OrderWith`, the default ordering is equivalent to `@FixMethodOrder(MethodSorters.DEFAULT)`


如果你没有指定 `@FixMethodOrder` 或 `@OrderWith` ，默认排序等价于 `@FixMethodOrder(MethodSorters.DEFAULT)`

---

### Example *示例*

```java
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class TestMethodOrder {

    @Test
    public void testA() {
        System.out.println("first");
    }
    @Test
    public void testB() {
        System.out.println("second");
    }
    @Test
    public void testC() {
        System.out.println("third");
    }
}

```

Above code will execute the test methods in the order of their names, sorted in ascending order.


上面的代码将按照名称的顺序执行测试方法，按升序排序。

---
