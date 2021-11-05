# Timeout for tests *超时测试*

> Marc Philipp edited this page on Jun 25, 2020 · 19 revisions 

> Marc Philipp 于 2020 年 6 月 25 日编辑了此页面 · 19 次修订

Tests that 'runaway' or take too long, can be automatically failed. 
There are two options for implementing this behavior:


“失控”或耗时过长的测试可能会自动失败。
实现此行为有两种选择：

---

### Timeout parameter on `@Test` Annotation (applies to test method) *`@Test` 注解上的超时参数（适用于测试方法）*

You can optionally specify timeout in milliseconds to cause a test method to fail if it takes longer than that number of milliseconds. 
If the time limit is exceeded, then the failure is triggered by an `Exception` being thrown:


您可以选择以毫秒为单位指定超时，以导致测试方法在花费的时间超过该毫秒数时失败。
如果超过了时间限制，则抛出 `Exception` 会触发失败：

---

```
@Test(timeout=1000)
public void testWithTimeout() {
  ...
}

```

This is implemented by running the test method in a separate thread. 
If the test runs longer than the allotted timeout, the test will fail and JUnit will interrupt the thread running the test. 
If a test times out while executing an interruptible operation, the thread running the test will exit (if the test is in an infinite loop, the thread running the test will run forever, while other tests continue to execute).


这是通过在单独的线程中运行测试方法来实现的。
如果测试运行时间超过分配的超时时间，则测试将失败并且 JUnit 将中断运行测试的线程。
如果在执行可中断操作时测试超时，则运行测试的线程将退出（如果测试处于无限循环中，则运行测试的线程将永远运行，而其他测试继续执行）。

---

### Timeout Rule (applies to all test cases in the test class) *超时规则（适用于测试类中的所有测试用例）*

The Timeout Rule applies the same timeout to all test methods in a class, and will currently execute in addition to any timeout specified by the `timeout` parameter on an individual Test annotation.


超时规则将相同的超时应用于类中的所有测试方法，并且当前将在单个 Test 注解上的 `timeout` 参数指定的任何超时之外执行。

---

```java
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.Timeout;

public class HasGlobalTimeout {
    public static String log;
    private final CountDownLatch latch = new CountDownLatch(1);

    @Rule
    public Timeout globalTimeout = Timeout.seconds(10); // 10 seconds max per method tested

    @Test
    public void testSleepForTooLong() throws Exception {
        log += "ran1";
        TimeUnit.SECONDS.sleep(100); // sleep for 100 seconds
    }

    @Test
    public void testBlockForever() throws Exception {
        log += "ran2";
        latch.await(); // will block 
    }
}

```

The timeout specified in the `Timeout` rule applies to the entire test fixture, including any `@Before` or `@After` methods. 
If the test method is in an infinite loop (or is otherwise not responsive to interrupts) then `@After` methods will not be called.


`Timeout` 规则中指定的超时适用于整个测试装置，包括任何 `@Before` 或 `@After` 方法。
如果测试方法处于无限循环中（或者不响应中断），则不会调用 `@After` 方法。

---

### Global Timeout Management with JUnit Foundation *使用 JUnit Foundation 进行全局超时管理*

[JUnit Foundation](https://github.com/Nordstrom/JUnit-Foundation) provides a timeout management feature that enables you to specify a configurable global timeout interval. 
The timeout behavior is provided by the **JUnit** framework via the `timeout` parameter of the **`@Test`** annotation. 
If no `timeout` parameter has been specified, or if the configured global timeout specifies a longer interval, **JUnit Foundation** overrides the `@Test` annotation with a mutable replacement that specifies the configured `timeout` parameter.


[JUnit Foundation](https://github.com/Nordstrom/JUnit-Foundation) 提供了超时管理功能，可以让您指定可配置的全局超时间隔。
超时行为由 **JUnit** 框架通过 **`@Test`** 注释的 **`timeout`** 参数提供。
如果没有指定 `timeout` 参数，或者如果配置的全局超时指定了更长的间隔，**JUnit Foundation** 将使用指定配置的 `timeout` 参数的可变替换覆盖 `@Test` 注释。

---

Timeout management is applied automatically by the **JUnit Foundation** Java agent, activated by setting the `TEST_TIMEOUT` configuration option to the desired default test timeout interval in milliseconds. 
This timeout specification is applied to every test method that doesn't explicitly specify a longer interval.


超时管理由 **JUnit Foundation** Java 代理自动应用，通过将 `TEST_TIMEOUT` 配置选项设置为所需的默认测试超时间隔（以毫秒为单位）来激活。
此超时规范适用于未明确指定更长间隔的每个测试方法。

---
