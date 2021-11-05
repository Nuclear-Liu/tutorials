# Exception testing *测试异常*

> Kevin Cooney edited this page on Feb 10, 2020 · 24 revisions 

> 凯文·库尼 (Kevin Cooney) 于 2020 年 2 月 10 日编辑了此页面 · 24 次修订

How do you verify that code throws exceptions as expected? 
Verifying that code completes normally is important, but making sure the code behaves as expected in exceptional situations is vital too. 
For example:


您如何验证代码是否按预期抛出异常？
验证代码是否正常完成很重要，但确保代码在异常情况下按预期运行也很重要。
例如：

---

`new ArrayList<Object>().get(0);`

This code should throw an `IndexOutOfBoundsException`. 
There are multiple ways in JUnit to write a test to verify this behavior.


此代码应抛出 `IndexOutOfBoundsException` 。
JUnit 中有多种方式可以编写测试来验证此行为。

---

### Using `assertThrows` Method *使用 `assertThrows` 方法*

The method `assertThrows` has been added to the `Assert` class in version 4.13. 
With this method you can assert that a given function call (specified, for instance, as a lambda expression or method reference) results in a particular type of exception being thrown. 
In addition it returns the exception that was thrown, so that further assertions can be made (e.g. to verify that the message and cause are correct). 
Furthermore, you can make assertions on the state of a domain object after the exception has been thrown:


在 4.13 版本中， `assertThrows` 方法已添加到 `Assert` 类中。
使用此方法，您可以断言给定的函数调用（例如，指定为 lambda 表达式或方法引用）导致抛出特定类型的异常。
此外，它还返回抛出的异常，以便可以进行进一步的断言（例如，验证消息和原因是否正确）。
此外，您可以在抛出异常后对域对象的状态进行断言：

---

```
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;
import org.junit.Test;

@Test
public void testExceptionAndState() {
  List<Object> list = new ArrayList<>();

  IndexOutOfBoundsException thrown = assertThrows(
      IndexOutOfBoundsException.class,
      () -> list.add(1, new Object()));

  // assertions on the thrown exception
  assertEquals("Index: 1, Size: 0", thrown.getMessage());
  // assertions on the state of a domain object after the exception has been thrown
  assertTrue(list.isEmpty());
}

```

### Try/Catch Idiom *Try/Catch 惯用法*

If you project is not yet using JUnit 4.13 or your code base does not support lambdas, you can use the try/catch idiom which prevailed in JUnit 3.x:


如果您的项目尚未使用 JUnit 4.13 或您的代码库不支持 lambda，您可以使用在 JUnit 3.x 中流行的 trycatch 惯用法：

---

```
@Test
public void testExceptionMessage() {
  List<Object> list = new ArrayList<>();
    
  try {
    list.get(0);
    fail("Expected an IndexOutOfBoundsException to be thrown");
  } catch (IndexOutOfBoundsException anIndexOutOfBoundsException) {
    assertThat(anIndexOutOfBoundsException.getMessage(), is("Index: 0, Size: 0"));
  }
}

```

Be aware that `fail()` throws an `AssertionError`, so you cannot use the above idiom to verify that a method call should throw an `AssertionError`.


请注意， `fail()` 会抛出一个 `AssertionError` ，因此您不能使用上述习语来验证方法调用是否应该抛出一个 `AssertionError` 。

---

## Specifying the expected annotation via the `@Test` annotation. *通过 `@Test` 注释指定预期的注释*

The `@Test` annotation has an optional parameter "`expected`" that takes as values subclasses of `Throwable`. 
If we wanted to verify that `ArrayList` throws the correct exception, we could write:


`@Test` 注释有一个可选参数 “`expected`” ，它将 `Throwable` 的子类作为值。
如果我们想验证 ArrayList 是否抛出了正确的异常，我们可以这样写：

---

```
@Test(expected = IndexOutOfBoundsException.class) 
public void empty() { 
  new ArrayList<Object>().get(0); 
}

```

The `expected` parameter should be used with care. 
The above test will pass if any code in the method throws `IndexOutOfBoundsException`. 
Using the method you also cannot test the value of the message in the exception, or the state of a domain object after the exception has been thrown.


应谨慎使用 `expected` 参数。
如果方法中的任何代码抛出 `IndexOutOfBoundsException` ，则上述测试将通过。
使用该方法也不能测试异常中消息的值，或抛出异常后域对象的状态。

---

For these reasons, the previous approaches are recommended.


由于这些原因，建议使用以前的方法。

---

### ExpectedException Rule *预期异常规则*

Another way to test exceptions is the `ExpectedException` rule, but that approach has been deprecated in JUnit 4.13. 
This rule let you indicate not only what exception you are expecting, but also the exception message you are expecting:


另一种测试异常的方法是 `ExpectedException` 规则，但这种方法在 JUnit 4.13 中已被弃用。
这条规则不仅让您指出您期望的异常，还可以指示您期望的异常消息：

---

```
@Rule
public ExpectedException thrown = ExpectedException.none();

@Test
public void shouldTestExceptionMessage() throws IndexOutOfBoundsException {
  List<Object> list = new ArrayList<Object>();
 
  thrown.expect(IndexOutOfBoundsException.class);
  thrown.expectMessage("Index: 0, Size: 0");
  list.get(0); // execution will never get past this line
}

```

The expectMessage also lets you use Matchers, which gives you a bit more flexibility in your tests. 
An example:


expectMessage 还允许您使用匹配器，这为您的测试提供了更大的灵活性。
一个例子：

---

`thrown.expectMessage(CoreMatchers.containsString("Size: 0"));`

Moreover, you can use Matchers to inspect the Exception, useful if it has embedded state you wish to verify. 
For example


此外，您可以使用匹配器来检查异常，如果它具有您希望验证的嵌入状态，则非常有用。
例如

---

```java
import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.startsWith;

import javax.ws.rs.NotFoundException;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class TestExy {
  @Rule
  public ExpectedException thrown = ExpectedException.none();

  @Test
  public void shouldThrow() {
    TestThing testThing = new TestThing();
    thrown.expect(NotFoundException.class);
    thrown.expectMessage(startsWith("some Message"));
    thrown.expect(hasProperty("response", hasProperty("status", is(404))));
    testThing.chuck();
  }

  private class TestThing {
    public void chuck() {
      Response response = Response.status(Status.NOT_FOUND).entity("Resource not found").build();
      throw new NotFoundException("some Message", response);
    }
  }
}

```

For an expanded discussion of the `ExpectedException` rule, see this blog post.


有关 `ExpectedException` 规则的详细讨论，请参阅此博客文章。

---

Do note that when the test calls the method under test that throws the exception, no code in the test after the method will execute (because the method under test is throwing the exception). 
This can lead to confusion, which is one of the reasons why `ExpectedException.none()` is deprecated.


请注意，当测试调用抛出异常的被测方法时，该方法之后的测试中不会执行任何代码（因为被测方法正在抛出异常）。
这可能会导致混淆，这也是不推荐使用“ExpectedException.none()”的原因之一。

---
