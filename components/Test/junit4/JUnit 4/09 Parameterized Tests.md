# Parameterized tests *参数化测试*

> Marc Philipp edited this page on Jun 25, 2020 · 27 revisions 

> Marc Philipp 于 2020 年 6 月 25 日编辑了此页面 · 27 次修订

The custom runner `Parameterized` implements parameterized tests. 
When running a parameterized test class, instances are created for the cross-product of the test methods and the test data elements.


自定义运行程序 `Parameterized` 实现参数化测试。
运行参数化测试类时，会为测试方法和测试数据元素的交叉产品创建实例。

---

For example, to test a Fibonacci function, write:


例如，要测试斐波那契函数，请编写：

---

```java

import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.Collection;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

@RunWith(Parameterized.class)
public class FibonacciTest {
    @Parameters
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][] {     
                 { 0, 0 }, { 1, 1 }, { 2, 1 }, { 3, 2 }, { 4, 3 }, { 5, 5 }, { 6, 8 }  
           });
    }

    private int fInput;

    private int fExpected;

    public FibonacciTest(int input, int expected) {
        this.fInput = input;
        this.fExpected = expected;
    }

    @Test
    public void test() {
        assertEquals(fExpected, Fibonacci.compute(fInput));
    }
}

```

```java
public class Fibonacci {
    public static int compute(int n) {
    	int result = 0;
    	
        if (n <= 1) { 
        	result = n; 
        } else { 
        	result = compute(n - 1) + compute(n - 2); 
        }
        
        return result;
    }
}

```

Each instance of FibonacciTest will be constructed using the two-argument constructor and the data values in the `@Parameters` method.


FibonacciTest 的每个实例都将使用双参数构造函数和 `@Parameters` 方法中的数据值来构造。

---

## Using `@Parameter` for Field injection instead of Constructor *使用 `@Parameter` 进行字段注入而不是构造函数*

It is also possible to inject data values directly into fields without needing a constructor using the `@Parameter` annotation, like so:


也可以直接将数据值注入到字段中，而无需使用 `@Parameter` 注释的构造函数，如下所示：

---

```java
import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.Collection;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameter;
import org.junit.runners.Parameterized.Parameters;

@RunWith(Parameterized.class)
public class FibonacciTest {
    @Parameters
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][] {
                 { 0, 0 }, { 1, 1 }, { 2, 1 }, { 3, 2 }, { 4, 3 }, { 5, 5 }, { 6, 8 }  
           });
    }

    @Parameter // first data value (0) is default
    public /* NOT private */ int fInput;

    @Parameter(1)
    public /* NOT private */ int fExpected;

    @Test
    public void test() {
        assertEquals(fExpected, Fibonacci.compute(fInput));
    }
}

public class Fibonacci {
    public static int compute(int n) {
        int result = 0;

        if (n <= 1) {
            result = n;
        } else {
            result = compute(n - 1) + compute(n - 2);
        }

        return result;
    }
}

```

This currently only works for public fields (see [https://github.com/junit-team/junit4/pull/737](https://github.com/junit-team/junit4/pull/737)).


这目前仅适用于公共字段（参见 [https://github.com/junit-team/junit4/pull/737](https://github.com/junit-team/junit4/pull/737) ）

---

## Tests with single parameter *单参数测试*

(Since 4.12-beta-3)


（从 4.12-beta-3 开始）

---

If your test needs a single parameter only, you don't have to wrap it with an array. 
Instead you can provide an Iterable or an array of objects.


如果您的测试只需要一个参数，则不必用数组包装它。
相反，您可以提供一个 Iterable 或一个对象数组。

---

```
@Parameters
public static Iterable<? extends Object> data() {
    return Arrays.asList("first test", "second test");
}

```

or


或者

---

```
@Parameters
public static Object[] data() {
    return new Object[] { "first test", "second test" };
}

```

## Identify Individual test cases *识别单个测试用例*

In order to easily identify the individual test cases in a Parameterized test, you may provide a name using the `@Parameters` annotation. 
This name is allowed to contain placeholders that are replaced at runtime:


为了轻松识别参数化测试中的各个测试用例，您可以使用 `@Parameters` 注解提供名称。
此名称允许包含在运行时替换的占位符：

---

* `{index}`: the current parameter index
* `{0}, {1}, …`: the first, second, and so on, parameter value. NOTE: single quotes `'` should be escaped as two single quotes `''`.


* `{index}`: 当前参数索引
* `{0}, {1}, …`: 第一个、第二个等参数值。注意：单引号 `'` 应该转义为两个单引号 `''`.

---

## Example *示例*

```java
import static org.junit.Assert.assertEquals;

import java.util.Arrays;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

@RunWith(Parameterized.class)
public class FibonacciTest {

    @Parameters(name = "{index}: fib({0})={1}")
    public static Iterable<Object[]> data() {
        return Arrays.asList(new Object[][] { 
                 { 0, 0 }, { 1, 1 }, { 2, 1 }, { 3, 2 }, { 4, 3 }, { 5, 5 }, { 6, 8 }
           });
    }

    private int input;
    private int expected;

    public FibonacciTest(int input, int expected) {
        this.input = input;
        this.expected = expected;
    }

    @Test
    public void test() {
        assertEquals(expected, Fibonacci.compute(input));
    }
}

public class Fibonacci {
    public static int compute(int n) {
        int result = 0;

        if (n <= 1) {
            result = n;
        } else {
            result = compute(n - 1) + compute(n - 2);
        }

        return result;
    }
}

```

In the example given above, the Parameterized runner creates names like \[3: fib(3)=2\]. 
If you don't specify a name, the current parameter index will be used by default.


在上面给出的示例中，参数化运行器创建的名称类似于 \[3: fib(3)=2\]。
如果不指定名称，则默认使用当前参数索引。

---

## IDE Bug (Eclipse) *IDE Bug (Eclipse)*

If using the `name` annotation param and one of the inputs has a rounded bracket, e.g. 
`@Parameters(name = "test({index})")`, then the name gets truncated in Eclipse versions prior to 4.4 (Luna). 
See [https://bugs.eclipse.org/bugs/show_bug.cgi?id=102512](https://bugs.eclipse.org/bugs/show_bug.cgi?id=102512).


如果使用 `name` 注解参数并且输入之一具有圆括号，例如
`@Parameters(name = "test({index})")` ，然后名称在 4.4 (Luna) 之前的 Eclipse 版本中被截断。
参见 [https://bugs.eclipse.org/bugs/show_bug.cgi?id=102512](https://bugs.eclipse.org/bugs/show_bug.cgi?id=102512) 。
---

Before the Mars M4 release Eclipse wasn't able to run individual test subtrees, such as the ones create by the Parameterized runner. 
See [http://blog.moritz.eysholdt.de/2014/11/new-eclipse-junit-feature-run-subtrees.html](http://blog.moritz.eysholdt.de/2014/11/new-eclipse-junit-feature-run-subtrees.html) and [https://bugs.eclipse.org/bugs/show_bug.cgi?id=443498](https://bugs.eclipse.org/bugs/show_bug.cgi?id=443498) .


在 Mars M4 发布之前，Eclipse 无法运行单独的测试子树，例如由参数化运行器创建的测试子树。
参见 [http://blog.moritz.eysholdt.de/2014/11/new-eclipse-junit-feature-run-subtrees.html](http://blog.moritz.eysholdt.de/2014/11/new-eclipse-junit-feature-run-subtrees.html) 和 [https://bugs.eclipse.org/bugs/show_bug.cgi?id=443498](https://bugs.eclipse.org/bugs/show_bug.cgi?id=443498) 。
---

## See also *也可参见*

* As an alternative to parameterized tests you can also use the plugin [JUnitParams](https://github.com/Pragmatists/JUnitParams)
* If you want to define the parameters for your tests at the tests' `Suite`, you can use the `ParameterizedSuite` runner that is available in [a separate library](https://github.com/PeterWippermann/parameterized-suite).


* 作为参数化测试的替代方案，您还可以使用插件 [JUnitParams](https://github.com/Pragmatists/JUnitParams)
* 如果要在测试的 `Suite` 中定义测试的参数，可以使用 [a separate library](https://github.com/PeterWippermann/parameterized-suite) 中提供的 `ParameterizedSuite` 运行程序。

---
