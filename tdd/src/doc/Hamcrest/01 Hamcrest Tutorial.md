# The Hamcrest Tutorial *Hamcrest 教程*

## Introduction *介绍*

Hamcrest is a framework for writing matcher objects allowing 'match' rules to be defined declaratively. 
There are a number of situations where matchers are invaluble, such as UI validation, or data filtering, but it is in the area of writing flexible tests that matchers are most commonly used. 
This tutorial shows you how to use Hamcrest for unit testing.


Hamcrest 是一个用于编写匹配器对象的框架，允许以声明方式定义 'match' 规则
在许多情况下，匹配器是无价的，例如 UI 验证或数据过滤，但在编写灵活的测试领域，匹配器最常用。
本教程向您展示如何使用 Hamcrest 进行单元测试。

---

When writing tests it is sometimes difficult to get the balance right between overspecifying the test (and making it brittle to changes), and not specifying enough (making the test less valuable since it continues to pass even when the thing being tested is broken). 
Having a tool that allows you to pick out precisely the aspect under test and describe the values it should have, to a controlled level of precision, helps greatly in writing tests that are "just right". 
Such tests fail when the behaviour of the aspect under test deviates from the expected behaviour, yet continue to pass when minor, unrelated changes to the behaviour are made.


在编写测试时，有时很难在过度指定测试（并使其易受更改）和指定不足测试（使测试的价值降低，因为即使被测试的东西坏了它也会继续通过）之间取得平衡。
拥有一个工具可以让您精确地挑选出被测试的切面并描述它应该具有的值，以控制精度水平，极大地帮助编写“恰到好处”的测试。
当被测试切面的行为偏离预期行为时，此类测试失败，但在对行为进行微小的、不相关的更改时继续通过。

---

### My first Hamcrest test *我的第一个 Hamcrest 测试*

We'll start by writing a very simple JUnit 3 test, but instead of using JUnit's `assertEquals` methods, we use Hamcrest's `assertThat` construct and the standard set of matchers, both of which we statically import:


我们将从编写一个非常简单的 JUnit 3 测试开始，但我们不使用 JUnit 的 `assertEquals` 方法，而是使用 Hamcrest 的 `assertThat` 构造和标准匹配器集，我们静态导入它们：

---

* [Biscuit](./../../../hamcrest/src/main/java/org/hui/tdd/hamcrest/tutorial/Biscuit.java)
* [BiscuitTest](./../../../hamcrest/src/test/java/org/hui/tdd/hamcrest/tutorial/BiscuitTest.java)

```java
package org.hui.tdd.hamcrest.tutorial;

import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;

/**
 * @author BadMan
 * @since 2021-11-02 18:38
 */
public class BiscuitTest {
    @Test
    public void testEquals() {
        Biscuit theBiscuit = new Biscuit("Ginger");
        Biscuit myBiscuit = new Biscuit("Ginger");
        assertThat("Printing", theBiscuit, equalTo(myBiscuit));
    }
    @Test(expected = AssertionError.class)
    public void testNotEquals() {
        Biscuit theBiscuit = new Biscuit("Ginger");
        Biscuit myBiscuit = new Biscuit("Pink");
        assertThat("biscuit color:", theBiscuit, equalTo(myBiscuit));
    }
}

```

> `Biscuit` 在断言测试时必须重写 **`equals`** 方法， `equalTo` 匹配器内部实现调用 `equals` 判断相等性。

The `assertThat` method is a stylized sentence for making a test assertion. 
In this example, the subject of the assertion is the object `Biscuit` that is the first method parameter. 
The second method parameter is a matcher for `Biscuit` objects, here a matcher that checks one object is equal to another using the `Object` `equals` method. 
The test passes since the Biscuit class defines an equals method.


`assertThat` 方法是用于进行测试断言的程式化语句。
在这个例子中，断言的主题是对象 `Biscuit` ，它是第一个方法参数。
第二个方法参数是 `Biscuit` 对象的匹配器，这里是使用 `Object` `equals` 方法检查一个对象是否等于另一个对象的匹配器。

---

If you have more than one assertion in your test you can include an identifier for the tested value in the assertion:


如果您的测试中有多个断言，您可以**在断言中包含测试值的标识符**：
 
---

```text
assertThat("chocolate chips", theBiscuit.getChocolateChipCount(), equalTo(10)); 

assertThat("hazelnuts", theBiscuit.getHazelnutCount(), equalTo(3));

```

### Other test frameworks *其他测试框架*

Hamcrest has been designed from the outset to integrate with different unit testing frameworks.
For example, Hamcrest can be used with JUnit (all versions) and TestNG. 
(For details have a look at the examples that come with the full Hamcrest distribution.) 
It is easy enough to migrate to using Hamcrest-style assertions in an existing test suite, since other assertion styles can co-exist with Hamcrest’s.


Hamcrest 从一开始就被设计为与不同的单元测试框架集成。
例如，Hamcrest 可以与 JUnit（所有版本）和 TestNG 一起使用。
（有关详细信息，请查看完整的 Hamcrest 发行版附带的示例。）
在现有测试套件中迁移到使用 Hamcrest 风格断言很容易，因为其他断言风格可以与 Hamcrest 共存。

---

Hamcrest can also be used with mock objects frameworks by using adaptors to bridge from the mock objects framework’s concept of a matcher to a Hamcrest matcher. 
For example, JMock 1’s constraints are Hamcrest’s matchers. 
Hamcrest provides a JMock 1 adaptor to allow you to use Hamcrest matchers in your JMock 1 tests. 
JMock 2 doesn’t need such an adaptor layer since it is designed to use Hamcrest as its matching library. 
Hamcrest also provides adaptors for EasyMock 2. 
Again, see the Hamcrest examples for more details.


通过使用适配器将 mock 对象框架的匹配器概念桥接到 Hamcrest 匹配器，Hamcrest 还可以与 mock 对象框架一起使用。
例如，JMock 1 的约束是 Hamcrest 的匹配器。
Hamcrest 提供了一个 JMock 1 适配器，允许您在 JMock 1 测试中使用 Hamcrest 匹配器。
JMock 2 不需要这样的适配器层，因为它被设计为使用 Hamcrest 作为其匹配库。
Hamcrest 还为 EasyMock 2 提供了适配器。
同样，请参阅 Hamcrest 示例以获取更多详细信息。

---

### A tour of common matchers *常见匹配器之旅*

Hamcrest comes with a library of useful matchers. 
Here are some of the most important ones.


Hamcrest 带有一个有用的匹配器库。
以下是一些最重要的。

---

#### Core *核心的*

* `anything` - always matches, useful if you don’t care what the object under test is

* `describedAs` - decorator to adding custom failure description

* `is` - decorator to improve readability - see “Sugar”, below


* `anything` - 总是匹配，如果你不关心被测对象是什么，很有用

* `describedAs` - 装饰器添加自定义失败描述

* `is` - 装饰器以提高可读性 - 请参阅下面的 “Sugar”

#### Logical *逻辑的*

* `allOf` - matches if all matchers match, short circuits (like Java `&&`)

* `anyOf` - matches if any matchers match, short circuits (like Java `||`)

* `not` - matches if the wrapped matcher doesn’t match and vice versa


* `allOf` - 如果所有匹配器匹配，则匹配，支持短路运算（如 Java `&&`）

* `anyOf` - 如果任何匹配器匹配，则匹配，支持短路运算（如 Java `||`）

* `not` - 如果包装的匹配器不匹配则匹配，反之亦然

#### Object *对象的*

* `equalTo` - test object equality using `Object.equals`

* `hasToString` - test `Object.toString`

* `instanceOf`, `isCompatibleType` - test type

* `notNullValue`, `nullValue` - test for null

* `sameInstance` - test object identity


* `equalTo` - 使用 `Object.equals` 测试对象相等性

* `hasToString` - 测试 `Object.toString`

* `instanceOf`, `isCompatibleType` - 测试类型

* `notNullValue`, `nullValue` - 测试是否为空

* `sameInstance` - 测试对象标识

#### Beans *Beans*

* `hasProperty` - test JavaBeans properties


* `hasProperty` - 测试 JavaBeans 属性

#### Collections *集合的*

* `array` - test an array’s elements against an array of matchers

* `hasEntry`, `hasKey`, `hasValue` - test a map contains an entry, key or value

* `hasItem`, `hasItems` - test a collection contains elements

* `hasItemInArray` - test an array contains an element


* `array` - 针对匹配器数组测试数组元素

* `hasEntry`, `hasKey`, `hasValue` - 测试包含条目、键或值的映射

* `hasItem`, `hasItems` - 测试集合包含元素

* `hasItemInArray` - 测试一个数组包含一个元素

#### Number *数字的*

* `closeTo` - test floating point values are close to a given value

* `greaterThan`, `greaterThanOrEqualTo`, `lessThan`, `lessThanOrEqualTo` - test ordering


* `closeTo` - 测试浮点值接近给定值

* `greaterThan`, `greaterThanOrEqualTo`, `lessThan`, `lessThanOrEqualTo` - 测试次序

#### Text *文本的*

* `equalToIgnoringCase` - test string equality ignoring case

* `equalToIgnoringWhiteSpace` - test string equality ignoring differences in runs of whitespace

* `containsString`, `endsWith`, `startsWith` - test string matching


* `equalToIgnoringCase` - 忽略大小写的测试字符串相等性

* `equalToIgnoringWhiteSpace` - 忽略空白运行差异的测试字符串相等性

* `containsString`, `endsWith`, `startsWith` - 测试字符串匹配

#### Sugar *语法糖*

Hamcrest strives to make your tests as readable as possible. 
For example, the `is` matcher is a wrapper that doesn’t add any extra behavior to the underlying matcher. 
The following assertions are all equivalent:


Hamcrest 努力使您的测试尽可能具有可读性。
例如， `is` 匹配器是一个包装器，它不会向底层匹配器添加任何额外的行为。
以下断言都是等价的：

---

```text
assertThat(theBiscuit, equalTo(myBiscuit)); 
assertThat(theBiscuit, is(equalTo(myBiscuit))); 
assertThat(theBiscuit, is(myBiscuit));

```

The last form is allowed since `is(T value)` is overloaded to return `is(equalTo(value))`.


最后一种形式是允许的，因为 `is(T value)` 被重载以返回 `is(equalTo(value))` 。

---

## Writing custom matchers *编写自定义匹配器*

Hamcrest comes bundled with lots of useful matchers, but you’ll probably find that you need to create your own from time to time to fit your testing needs. 
This commonly occurs when you find a fragment of code that tests the same set of properties over and over again (and in different tests), and you want to bundle the fragment into a single assertion. 
By writing your own matcher you’ll eliminate code duplication and make your tests more readable!


Hamcrest 捆绑了许多有用的匹配器，但您可能会发现需要不时创建自己的匹配器以满足您的测试需求。
当您发现一个代码片段一遍又一遍地（在不同的测试中）测试相同的属性集时，通常会发生这种情况，并且您希望将该片段捆绑到一个断言中。
通过编写自己的匹配器，您将消除代码重复并使您的测试更具可读性！

---

Let’s write our own matcher for testing if a `double` value has the value `NaN` (not a number). 
This is the test we want to write:


让我们编写自己的匹配器来测试 `double` 值是否为 `NaN` （不是数字）。
这是我们要编写的测试：

---

[IsNotANumberTest](./../../test/java/org/hui/tdd/hamcrest/tutorial/IsNotANumberTest.java)

```text
package org.hui.tdd.hamcrest.tutorial;

import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.hui.tdd.hamcrest.tutorial.IsNotANumber.notANumber;

/**
 * @author BadMan
 * @since 2021-11-02 20:40
 */
public class IsNotANumberTest {

    @Test
    public void testSquareRootOfMinusOneIsNotANumber() {
        assertThat(Math.sqrt(-1), is(notANumber()));
    }
}
```

And here’s the implementation:


这是实现：

---

[IsNotANumber](./../../main/java/org/hui/tdd/hamcrest/tutorial/IsNotANumber.java)

```java
package org.hui.tdd.hamcrest.tutorial;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;

/**
 * @author BadMan
 * @since 2021-11-02 20:26
 */
public class IsNotANumber extends TypeSafeMatcher<Double> {

    @Override
    public boolean matchesSafely(Double number) {
        return number.isNaN();
    }

    public void describeTo(Description description) {
        description.appendText("not a number");
    }

    public static Matcher<Double> notANumber() {
        return new IsNotANumber();
    }
}

```

The `assertThat` method is a generic method which takes a Matcher parameterized by the type of the subject of the assertion. 
We are asserting things about `Double` values, so we know that we need a `Matcher<Double>`. 
For our Matcher implementation it is most convenient to subclass `TypeSafeMatcher`, which does the cast to a `Double` for us. 
We need only implement the `matchesSafely` method - which simply checks to see if the `Double` is `NaN` - and the `describeTo` method - which is used to produce a failure message when a test fails. 
Here’s an example of how the failure message looks:


`assertThat` 方法是一个通用方法，它采用由主题类型断言参数化的 Matcher。
我们正在断言关于 `Double` 值的事情，所以我们知道我们需要一个 `Matcher<Double>` 。
对于我们的 Matcher 实现，最方便的是将 `TypeSafeMatcher` 子类化，它会为我们转换为 `Double` 类型。
我们只需要实现 `matchesSafely` 方法 - 它只是检查 `Double` 是否为 `NaN` - 和 `describeTo` 方法 - 用于在测试失败时生成失败消息。
以下是失败消息外观的示例：

---

[IsNotANumberTest](./../../test/java/org/hui/tdd/hamcrest/tutorial/IsNotANumberTest.java)

```text
assertThat(1.0, is(notANumber()));

// fails with the message

java.lang.AssertionError: Expected: is not a number got : <1.0>

```

The third method in our matcher is a convenience factory method. 
We statically import this method to use the matcher in our test:


我们的匹配器中的第三个方法是便利工厂方法。
我们静态导入此方法以在我们的测试中使用匹配器：

---

[IsNotANumberTest](./../../test/java/org/hui/tdd/hamcrest/tutorial/IsNotANumberTest.java)

```java
package org.hui.tdd.hamcrest.tutorial;

import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.hui.tdd.hamcrest.tutorial.IsNotANumber.notANumber;

/**
 * @author BadMan
 * @since 2021-11-02 20:40
 */
public class IsNotANumberTest {

    @Test
    public void testSquareRootOfMinusOneIsNotANumber() {
        assertThat(Math.sqrt(-1), is(notANumber()));
    }

    @Test(expected = AssertionError.class)
    public void testNotANumber() {
        assertThat(1.0, is(notANumber()));
    }
}
```

Even though the `notANumber` method creates a new matcher each time it is called, you should not assume this is the only usage pattern for your matcher. 
Therefore you should make sure your matcher is stateless, so a single instance can be reused between matches.


即使 `notANumber` 方法在每次调用时都会创建一个新的匹配器，您也不应该假设这是您的匹配器的唯一使用模式。
因此，您应该确保您的匹配器是**无状态的**，以便可以在匹配之间**重用**单个实例。

---
