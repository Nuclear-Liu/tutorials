# Assertions *断言*

> Dariusz Andrzej Stefański edited this page on 19 Mar 2016 · 7 revisions

> Dariusz Andrzej Stefański 于 2016 年 3 月 19 日编辑了此页面 · 7 次修订


JUnit provides overloaded assertion methods for all primitive types and Objects and arrays (of primitives or Objects). 
The parameter order is expected value followed by actual value. 
Optionally the first parameter can be a String message that is output on failure. 
There is a slightly different assertion, `assertThat` that has parameters of the optional failure message, the actual value, and a `Matcher` object. 
Note that expected and actual are reversed compared to the other assert methods.

JUnit 为所有基本类型和对象以及（基本的或对象的）数组提供重载断言方法。
**参数顺序**为**预期值**后接**实际值**。
**可选地**，**第一个参数**可以是**失败时输出的字符串消息**。
有一个稍微**不同**的断言， `assertThat` 具有可选的**失败消息**、**实际值**和 **`Matcher` 对象**的参数。
请注意，与其他断言方法相比，预期和实际是相反的。

---

## Examples *示例*

A representative of each assert method is shown.


显示了每个断言的典型方法。

---

* `org.junit.Assert.assertTrue`

    断言条件为 `true` 。如果断言不成立抛出 `AssertionError` 异常（携带可选的字符串参数）。

* `org.junit.Assert.assertFalse`

    断言条件为 `false` 。如果断言不成立抛出 `AssertionError` 异常（携带可选的字符串参数）。

    > 通过调用 `assertTrue` 方法实现。

* `org.junit.Assert.assertNull`

    断言对象为 `null` 。如果断言不成立抛出 `AssertionError` 异常（携带可选的字符串参数）。

* `org.junit.Assert.assertNotNull`

    断言对象不为 `null` 。如果断言不成立抛出 `AssertionError` 异常（携带可选的字符串参数）。

* `org.junit.Assert.assertSame`

    断言两个对象引用同一个对象。如果断言不成立抛出 `AssertionError` 异常（携带可选的字符串参数）。

* `org.junit.Assert.assertNotSame`

    断言两个对象不引用同一个对象。如果断言不成立抛出 `AssertionError` 异常（携带可选的字符串参数）。

* `org.junit.Assert.assertEquals`

    * `long` (其他整形可以自动转换为 `long` 达到覆盖其他基本整形的效果)
    
        断言两个 `long` 相等。 如果断言不成立抛出 `AssertionError` 异常（携带可选的字符串参数）。
    
    * `Object`

        断言两个对象相等。如果断言不成立抛出 `AssertionError` 异常（携带可选的字符串参数）。
        如果 `unexpected` 和 `actual` 为 `null` ，则认为它们**相等**。
    
    * `double` / `float`

        断言两个 `double` / `float` 值在正增量( `delta` )内相等。 如果断言不成立抛出 `AssertionError` 异常（携带可选的字符串参数）。
        
        如果**预期值**(`expected`)为无穷大，则忽略增量值。 
        `NaN` 被认为是相等的： `assertEquals(Double.NaN, Double.NaN, *)` 通过

* `org.junit.Assert.assertNotEquals`

  * `long` (其他整形可以自动转换为 `long` 达到覆盖其他基本整形的效果)

    断言两个 long不相等。 如果断言不成立抛出 `AssertionError` 异常（携带可选的字符串参数）。

  * `Object`

    断言两个对象不相等。如果断言不成立抛出 `AssertionError` 异常（携带可选的字符串参数）。
    如果 `unexpected` 和 `actual` 为 `null` ，则认为它们**相等**。

  * `double` / `float`

    断言两个 `double` / `float` 值在正增量( `delta` )内不相等。 如果断言不成立抛出 `AssertionError` 异常（携带可选的字符串参数）。

    如果**预期值**(`expected`)为无穷大，则忽略增量值。
    `NaN` 被认为是相等的： `assertEquals(Double.NaN, Double.NaN, *)` 通过

* `org.junit.Assert.assertArrayEquals`

    断言两个数组( `Object[]` \ `boolean[]` \ `byte[]` \ `char[]` \ `short[]` \ `int[]` \ `long[]` \ `double[]` \ `float[]` ）相等。 如果断言不成立抛出 `ArrayComparisonFailure` (extends `AssertionError`) 异常（携带可选的字符串参数）。
    
    * `org.junit.internal.ArrayComparisonFailure` 当两个数组元素不同时抛出

* `org.junit.Assert.assertThat` **已弃用**

    断言actual满足matcher指定的条件。 如果断言不成立抛出 `AssertionError` 异常（携带可选的字符串参数 其中包含有关匹配器和失败值的原因和信息）。

    底层调用 `org.hamcrest.MatcherAssert.assertThat()` 。


[AssertTests](./../../junit4/src/test/java/org/hui/tdd/junit4/AssertTests.java)

```java
import static org.hamcrest.CoreMatchers.allOf;
import static org.hamcrest.CoreMatchers.anyOf;
import static org.hamcrest.CoreMatchers.both;
import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.everyItem;
import static org.hamcrest.CoreMatchers.hasItems;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.sameInstance;
import static org.hamcrest.CoreMatchers.startsWith;
import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;

import org.hamcrest.core.CombinableMatcher;
import org.junit.Test;

public class AssertTests {
  @Test
  public void testAssertArrayEquals() {
    byte[] expected = "trial".getBytes();
    byte[] actual = "trial".getBytes();
    assertArrayEquals("failure - byte arrays not same", expected, actual);
  }

  @Test
  public void testAssertEquals() {
    assertEquals("failure - strings are not equal", "text", "text");
  }

  @Test
  public void testAssertFalse() {
    assertFalse("failure - should be false", false);
  }

  @Test
  public void testAssertNotNull() {
    assertNotNull("should not be null", new Object());
  }

  @Test
  public void testAssertNotSame() {
    assertNotSame("should not be same Object", new Object(), new Object());
  }

  @Test
  public void testAssertNull() {
    assertNull("should be null", null);
  }

  @Test
  public void testAssertSame() {
    Integer aNumber = Integer.valueOf(768);
    assertSame("should be same", aNumber, aNumber);
  }

  // JUnit Matchers assertThat
  @Test
  public void testAssertThatBothContainsString() {
    assertThat("albumen", both(containsString("a")).and(containsString("b")));
  }

  @Test
  public void testAssertThatHasItems() {
    assertThat(Arrays.asList("one", "two", "three"), hasItems("one", "three"));
  }

  @Test
  public void testAssertThatEveryItemContainsString() {
    assertThat(Arrays.asList(new String[] { "fun", "ban", "net" }), everyItem(containsString("n")));
  }

  // Core Hamcrest Matchers with assertThat
  @Test
  public void testAssertThatHamcrestCoreMatchers() {
    assertThat("good", allOf(equalTo("good"), startsWith("good")));
    assertThat("good", not(allOf(equalTo("bad"), equalTo("good"))));
    assertThat("good", anyOf(equalTo("bad"), equalTo("good")));
    assertThat(7, not(CombinableMatcher.<Integer> either(equalTo(3)).or(equalTo(4))));
    assertThat(new Object(), not(sameInstance(new Object())));
  }

  @Test
  public void testAssertTrue() {
    assertTrue("failure - should be true", true);
  }
}

```

## 源码

```text
    /**
     * Fails a test with the given message.
     *
     * @param message the identifying message for the {@link AssertionError} (<code>null</code>
     * okay)
     * @see AssertionError
     */
    public static void fail(String message) {
        if (message == null) {
            throw new AssertionError();
        }
        throw new AssertionError(message);
    }
    
    private static void failSame(String message) {
        String formatted = "";
        if (message != null) {
            formatted = message + " ";
        }
        fail(formatted + "expected not same");
    }
```
