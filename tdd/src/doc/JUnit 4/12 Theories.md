# Theories *Theories*

> John Freeman edited this page on Sep 1, 2016 · 5 revisions 

> John Freeman 于 2016 年 9 月 1 日编辑了此页面 · 5 次修订

More flexible and expressive assertions, combined with the ability to state assumptions clearly, lead to a new kind of statement of intent, which we call a "Theory". 
A test captures the intended behavior in one particular scenario. 
A theory captures some aspect of the intended behavior in possibly infinite numbers of potential scenarios. 
For example:


更加灵活和富有表现力的断言，再加上清楚地陈述假设的能力，导致了一种新的意图陈述，我们称之为 "Theory" 。
测试捕获特定场景中的预期行为。
一个 theory 在可能无限数量的潜在场景中捕获了预期行为的某些方面。
例如：

---

```java
@RunWith(Theories.class)
public class UserTest {
    @DataPoint
    public static String GOOD_USERNAME = "optimus";
    @DataPoint
    public static String USERNAME_WITH_SLASH = "optimus/prime";

    @Theory
    public void filenameIncludesUsername(String username) {
        assumeThat(username, not(containsString("/")));
        assertThat(new User(username).configFileName(), containsString(username));
    }
}

```

This makes it clear that the user's username should be included in the config file name, only if it doesn't contain a slash. 
Another test or theory might define what happens when a username does contain a slash.


这清楚地表明用户的用户名应该包含在配置文件名中，只有当它不包含斜杠时。
另一个测试或理论可能会定义当用户名包含斜杠时会发生什么。

---

UserTest will attempt to run `filenameIncludesUsername` on every compatible DataPoint defined in the class. 
If any of the assumptions fail, the data point is silently ignored. 
If all of the assumptions pass, but an assertion fails, the test fails.


UserTest 将尝试在类中定义的每个兼容数据点上运行 `filenameIncludesUsername` 。
如果任何假设失败，则数据点将被默默忽略。
如果所有假设都通过，但断言失败，则测试失败。

---

The support for Theories has been absorbed from the Popper project, and more complete documentation can be found from that projects documentation archived here: 
[http://web.archive.org/web/20071012143326/popper.tigris.org/tutorial.html](http://web.archive.org/web/20071012143326/popper.tigris.org/tutorial.html) (also reproduced below).


对理论的支持已从 Popper 项目中吸收，更完整的文档可以从此处存档的项目文档中找到：
[http://web.archive.org/web/20071012143326/popper.tigris.org/tutorial.html](http://web.archive.org/web/20071012143326/popper.tigris.org/tutorial.html) （也转载如下）。

---

A paper written on theories is here [http://web.archive.org/web/20110608210825/http://shareandenjoy.saff.net/tdd-specifications.pdf](http://web.archive.org/web/20110608210825/http://shareandenjoy.saff.net/tdd-specifications.pdf)


一篇关于理论的论文在这里 [tdd-specifications](./tdd-specifications.pdf)

---

Defining general statements in this way can jog the developer's memory about other potential data points and tests, also allows automated tools to search for new, unexpected data points that expose bugs.


以这种方式定义一般语句可以让开发人员记住其他潜在的数据点和测试，还允许自动化工具搜索新的、意外的暴露错误的数据点。

---

## Documentation from defunct Popper project *来自不复存在的 Popper 项目的文档*

Popper extends JUnit to allow you to specify theories, general statements about your code's behavior that may be true over infinite sets of input values. 
For a detailed description of why you might want to do this, see our paper. 
If you just want to try it out and get started, here's the place.


Popper 扩展了 JUnit 以允许您指定 theories ，关于您的代码行为的一般陈述，这些陈述在无限组输入值上可能是正确的。
有关您可能想要这样做的原因的详细说明，请参阅我们的论文。
如果您只是想尝试一下并开始使用，这里就是您的最佳选择。

---

First, if you haven't already, download the latest Popper. 
Popper should work with any version of JUnit between 4.0 and 4.3, and Java 5 and above.


首先，如果您还没有，请下载最新的 Popper 。
Popper 应该适用于 4.0 和 4.3 之间的任何 JUnit 版本，以及 Java 5 及更高版本。

---

Put Popper and JUnit on your classpath.


将 Popper 和 JUnit 放在您的类路径中。

---

Now, let's start with a simple test class for a type that performs arithmetic on numbers of dollars:


现在，让我们从一个简单的测试类开始，用于对美元进行算术运算：

---

```java
public class DollarTest {
    @Test
    public void multiplyByAnInteger() {
        assertThat(new Dollar(5).times(2).getAmount(), is(10));
    }
}

```

This demonstrates the syntax of JUnit 4 tests written with Popper's `assertThat` syntax, which is lovingly stolen from Hamcrest. 
If the `@Test` syntax is new to you, you may want to quickly get up to speed on JUnit 4. 
If the convenient `assertThat` assertion syntax is new, you may want to get up to speed on Hamcrest. 
(You'll need to add some imports. Eclipse can take care of most of them for you, but here's the whole list)


这演示了使用 Popper 的 `assertThat` 语法编写的 JUnit 4 测试的语法，该语法是从 Hamcrest 精心借鉴的。
如果 `@Test` 语法对你来说是新的，你可能想快速上手 JUnit 4。
如果方便的 `assertThat` 断言语法是新的，你可能想快速了解 Hamcrest。
（您需要添加一些导入。Eclipse 可以为您处理其中的大部分，但这里是整个列表）

---

Go ahead and get the test above to pass. 
I especially suggest that you cheat by always returning 10 from getAmount.


继续并通过上面的测试。
我特别建议你总是从 getAmount 返回 10 来作弊。

---

Now, we'll see how Popper extends JUnit to allow the specification and verification of general properties. 
First, give Dollar test a new superclass:


现在，我们将看到 Popper 如何扩展 JUnit 以允许通用属性的规范和验证。
首先，给 Dollar test 一个新的超类：

---

```java
import net.saff.theories.api.TheoryContainer;

public class DollarTest extends TheoryContainer {
    // ...
    // Next, add a theory to the test class:

    @Theory
    public void multiplyIsInverseOfDivide(int amount, int m) {
        assumeThat(m, not(0));
        assertThat(new Dollar(amount).times(m).divideBy(m).getAmount(), is(amount));
    }
}
```

You'll have to add a `divideBy` method to `Dollar` in order to get the class to compile. 
This theory expresses a general truth about currency multiplication and division: one is the inverse of the other. 
By adding two parameters to the method, and adding the assumption that m is not zero, you've specified that this theory should hold true for any value of amount and all non-zero values of m.


您必须向 `Dollar` 添加一个 `divideBy` 方法才能编译该类。
这个理论表达了关于货币乘除法的一个普遍真理：一个是另一个的倒数。
通过向该方法添加两个参数，并添加 m 不为零的假设，您已指定该理论应适用于任何数量值和所有非零值 m。

---

Go ahead and run the tests, and you'll notice two things:


继续运行测试，你会注意到两件事：

---

* Your multiplyByAnInteger test passes as before.
* multiplyIsInverseOfDivide fails, with the message "Never found parameters that satisfied method."


* 您的 `multiplyByAnInteger` 测试像以前一样通过。
* `multiplyIsInverseOfDivide` 失败，并显示消息“从未找到满足方法的参数”。

---

The Popper runner does not automatically provide potential values for a theory. 
It can take a long time between being able to assert a general principle, and actually making it work with any conceivable input. 
Because of this, Popper expects to only run theories on a set of accepted data points, which allows you to incrementally build up a set of inputs for which you know the theory passes, until you feel confident that the theory is universally true.


Popper runner 不会自动为理论提供潜在的价值。
在能够断言一般原则和实际使其与任何可以想象的输入一起工作之间可能需要很长时间。
因此， Popper 期望只在一组可接受的数据点上运行理论，这允许您逐步建立一组您知道理论通过的输入，直到您确信该理论普遍正确。

---

By default, Popper assumes that all public fields of a test class are accepted data points. 
For example, add the following constants to `DollarTest`:


默认情况下，Popper 假定测试类的所有公共字段都是可接受的数据点。
例如，将以下常量添加到 `DollarTest` ：

---

```
public static int TWO = 2;
public static int FIVE = 5;
public static int TEN = 10;

```

Now the theory will probably fail, telling you which inputs caused the failure. 
Fix your implementations of times and divideBy, and the theory should pass.


现在这个理论可能会失败，告诉你哪些输入导致了失败。
修正你的时间和除法的实现，理论应该会通过。

---

To customize how Popper finds accepted data points, you can use parameter suppliers. 
One parameter supplier comes with Popper by default, which allows you to specify inline a set of acceptable integer inputs to a theory:


要自定义 Popper 如何查找可接受的数据点，您可以使用参数提供者。
默认情况下， Popper 附带一个参数供应商，它允许您将一组可接受的整数输入指定为理论内联：

---

```
@Theory
public void multiplyIsInverseOfDivideWithInlineDataPoints(
        @TestedOn(ints = {0, 5, 10}) int amount,
        @TestedOn(ints = {0, 1, 2}) int m
) {
    assumeThat(m, not(0));
    assertThat(new Dollar(amount).times(m).divideBy(m).getAmount(), is(amount));
}

```

This theory ignores the public fields of DollarTest, and tests with every combination of 0, 5, and 10 for amount, and 0, 1, and 2 for m. 
Notice that even though 0 is tried as a value for m, it does not trigger an arithmetic exception or failure: values that violate assumptions are quietly ignored.


该理论忽略了 DollarTest 的公共字段，并使用 0、5 和 10 的数量和 0、1 和 2 的每个组合来测试 m 。
请注意，即使尝试将 0 作为 m 的值，它也不会触发算术异常或失败：违反假设的值会被悄悄忽略。

---

You can extend Popper by providing your own parameter supplier. 
For example, here is a supplier for values between two integers, and an annotation that references it:


您可以通过提供您自己的参数供应商来扩展 Popper。
例如，这里是两个整数之间值的供应商，以及引用它的注释：

---

```java
@Retention(RetentionPolicy.RUNTIME)
@ParametersSuppliedBy(BetweenSupplier.class)
public @interface Between {
    int first();

    int last();
}

public static class BetweenSupplier extends ParameterSupplier {
    @Override
    public List getValues(Object test, ParameterSignature sig) {
        Between annotation = (Between) sig.getSupplierAnnotation();

        ArrayList list = new ArrayList();
        for (int i = annotation.first(); i <= annotation.last(); i++)
            list.add(i);
        return list;
    }
}

```

With this supplier defined, you can accept data points as ranges:


定义此提供者后，您可以接受数据点作为范围：

---

```
@Theory
public void multiplyIsInverseOfDivideWithInlineDataPoints(
        @Between(first = -100, last = 100) int amount,
        @Between(first = -100, last = 100) int m
) {
    assumeThat(m, not(0));
    assertThat(new Dollar(amount).times(m).divideBy(m).getAmount(), is(amount));
}

```

The paper refers to a capacity for automatically generating stubs to pass as object parameters to theories.


该论文提到了一种自动生成存根以将其作为对象参数传递给理论的能力

---
