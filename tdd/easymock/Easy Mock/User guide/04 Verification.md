# Verification _验证_

## A first verification _第一个验证_

There is one error that we have not handled so far: 
If we specify behavior, we would like to verify that it is actually used. 
The current test would pass if no method on the Mock Object is called. 
To verify that the specified behavior has been used, we have to call `verify(mock)`:


到目前为止，我们还没有处理过一个错误：
如果我们指定行为，我们想验证它是否被实际使用。
如果没有调用 Mock 对象上的方法，则当前测试将通过。
要验证指定的行为是否已被使用，我们必须调用 `verify(mock)`：

---

```text
@Test
public void testAddDocument() {
  mock.documentAdded("New Document"); // 2
  replay(mock); // 3
  classUnderTest.addDocument("New Document", new byte[0]);
  verify(mock);
}
```

If the method is not called on the Mock Object, we now get the following exception:


如果没有在 Mock 对象上调用该方法，我们现在会得到以下异常：

---

```log
java.lang.AssertionError:
  Expectation failure on verify:
    documentAdded("New Document"): expected: 1, actual: 0
      at org.easymock.internal.MocksControl.verify(MocksControl.java:70)
      at org.easymock.EasyMock.verify(EasyMock.java:536)
      at org.easymock.samples.ExampleTest.testAddDocument(ExampleTest.java:31)
      ...
```

The message of the exception lists all missed expectations.


异常消息列出了所有未达到的预期。

---

## Expecting an Explicit Number of Calls _期望明确的调用次数_

Up to now, our test has only considered a single method call. 
The next test should check whether the addition of an already existing document leads to a call to `mock.documentChanged()` with the appropriate argument. 
To be sure, we check this three times (hey, it is an example ;-)):


到目前为止，我们的测试只考虑了单个方法调用。
下一个测试应该检查添加已经存在的文档是否会导致使用适当的参数调用 `mock.documentChanged()` 。
可以肯定的是，我们检查了三遍（嘿，这是一个例子;-)）：

---

```text
@Test
public void testAddAndChangeDocument() {
  mock.documentAdded("Document");
  mock.documentChanged("Document");
  mock.documentChanged("Document");
  mock.documentChanged("Document");
  replay(mock);
  classUnderTest.addDocument("Document", new byte[0]);
  classUnderTest.addDocument("Document", new byte[0]);
  classUnderTest.addDocument("Document", new byte[0]);
  classUnderTest.addDocument("Document", new byte[0]);
  verify(mock);
}
```

To avoid the repetition of `mock.documentChanged("Document")`, EasyMock provides a shortcut. 
We may specify the call count with the method `times(int times)` on the object returned by `expectLastCall()`. 
The code then looks like:


为了避免`mock.documentChanged("Document")`的重复，EasyMock 提供了一个快捷方式。
我们可以使用 `expectLastCall()` 返回的对象上的 `times(int times)` 方法指定调用计数。
代码如下所示：

---

```text
@Test
public void testAddAndChangeDocument() {
  mock.documentAdded("Document");
  mock.documentChanged("Document");
  expectLastCall().times(3);
  replay(mock);
  classUnderTest.addDocument("Document", new byte[0]);
  classUnderTest.addDocument("Document", new byte[0]);
  classUnderTest.addDocument("Document", new byte[0]);
  classUnderTest.addDocument("Document", new byte[0]);
  verify(mock);
}
```

If the method is called too often, we get an exception that tells us that the method has been called too many times. 
The failure occurs immediately at the first method call exceeding the limit:


如果该方法被调用得太频繁，我们会得到一个异常，告诉我们该方法被调用了太多次。
在第一个超过限制的方法调用时立即发生故障：

---

```log
java.lang.AssertionError:
  Unexpected method call documentChanged("Document"):
    documentChanged("Document"): expected: 3, actual: 4
      at org.easymock.internal.MockInvocationHandler.invoke(MockInvocationHandler.java:29)
      at org.easymock.internal.ObjectMethodsFilter.invoke(ObjectMethodsFilter.java:44)
      at $Proxy0.documentChanged(Unknown Source)
      at org.easymock.samples.ClassUnderTest.notifyListenersDocumentChanged(ClassUnderTest.java:67)
      at org.easymock.samples.ClassUnderTest.addDocument(ClassUnderTest.java:26)
      at org.easymock.samples.ExampleTest.testAddAndChangeDocument(ExampleTest.java:43)
      ...
```

If there are too few calls, `verify(mock)` throws an `AssertionError`:


如果调用太少，`verify(mock)` 会抛出 `AssertionError`：

---

```log
java.lang.AssertionError:
  Expectation failure on verify:
    documentChanged("Document"): expected: 3, actual: 2
      at org.easymock.internal.MocksControl.verify(MocksControl.java:70)
      at org.easymock.EasyMock.verify(EasyMock.java:536)
      at org.easymock.samples.ExampleTest.testAddAndChangeDocument(ExampleTest.java:43)
      ...
```

## Specifying Return Values _指定返回值_

For specifying return values, we wrap the expected call in `expect(T value)` and specify the return value with the method `andReturn(Object returnValue)` on the object returned by `expect(T value)`.


为了指定返回值，我们将预期的调用包装在 `expect(T value)` 中，并在 `expect(T value)` 返回的对象上使用方法 `andReturn(Object returnValue)` 指定返回值。

---

As an example, we check the workflow for document removal. 
If `ClassUnderTest` gets a call for document removal, it asks all collaborators for their vote for removal with calls to `byte voteForRemoval(String title)` value. 
Positive return values are a vote for removal. 
If the sum of all values is positive, the document is removed and `documentRemoved(String title)` is called on all collaborators:


例如，我们检查文档删除的工作流程。
如果 `ClassUnderTest` 收到删除文档的调用，它会请求所有协作者通过调用 `byte voteForRemoval(String title)` 值对删除进行投票。
正的返回值是对删除的投票。
如果所有值的总和为正，则删除文档并在所有协作者上调用 `documentRemoved(String title)` ：

---

```text
@Test
public void testVoteForRemoval() {
  mock.documentAdded("Document");
  // expect document addition
  // expect to be asked to vote for document removal, and vote for it
  expect(mock.voteForRemoval("Document")).andReturn((byte) 42);
  mock.documentRemoved("Document");
  // expect document removal
  replay(mock);
  classUnderTest.addDocument("Document", new byte[0]);
  assertTrue(classUnderTest.removeDocument("Document"));
  verify(mock);
}

@Test
public void testVoteAgainstRemoval() {
  mock.documentAdded("Document");
  // expect document addition
  // expect to be asked to vote for document removal, and vote against it
  expect(mock.voteForRemoval("Document")).andReturn((byte) -42);
  replay(mock);
  classUnderTest.addDocument("Document", new byte[0]);
  assertFalse(classUnderTest.removeDocument("Document"));
  verify(mock);
}
```

The type of the returned value is checked at compile time. 
As an example, the following code will not compile, as the type of the provided return value does not match the method's return value:


在编译时检查返回值的类型。
例如，以下代码将无法编译，因为提供的返回值的类型与方法的返回值不匹配：

---

`expect(mock.voteForRemoval("Document")).andReturn("wrong type");`

Instead of calling `expect(T value)` to retrieve the object for setting the return value, we may also use the object returned by `expectLastCall()`. 
Instead of


除了调用`expect(T value)`来检索设置返回值的对象，我们也可以使用`expectLastCall()`返回的对象。
代替

---

`expect(mock.voteForRemoval("Document")).andReturn((byte) 42);`

we may use


我们可能会使用

---

```text
mock.voteForRemoval("Document");
expectLastCall().andReturn((byte) 42);
```

This type of specification should only be used if the line gets too long, as it does not support type checking at compile time.


这种类型的规范应该只在行太长时使用，因为它不支持在编译时进行类型检查。

---

## Working with Exceptions _处理异常_

For specifying exceptions (more exactly: Throwables) to be thrown, the object returned by `expectLastCall()` and `expect(T value)` provides the method `andThrow(Throwable throwable)`. 
The method has to be called in record state after the call to the Mock Object for which it specifies the `Throwable` to be thrown.


为了指定要抛出的异常（更准确地说：Throwables）， `expectLastCall()` 和 `expect(T value)` 返回的对象提供了方法 `andThrow(Throwable throwable)` 。
在调用它指定要抛出的 `Throwable` 的模拟对象之后，必须在记录状态下调用该方法。

---

Unchecked exceptions (that is, `RuntimeException`, `Error` and all their subclasses) can be thrown from every method. 
Checked exceptions can only be thrown from the methods that do actually throw them.


未经检查的异常（即 `RuntimeException` 、 `Error` 及其所有子类）可以从每个方法中抛出。
检查的异常只能从实际抛出它们的方法中抛出。

---

## Creating Return Values or Exceptions _创建返回值或异常_

Sometimes we would like our mock object to return a value or throw an exception that is created at the time of the actual call. 
Since EasyMock 2.2, the object returned by `expectLastCall()` and `expect(T value)` provides the method `andAnswer(IAnswer answer)` which allows to specify an implementation of the interface `IAnswer` that is used to create the return value or exception.


有时我们希望我们的模拟对象返回一个值或抛出在实际调用时创建的异常。
从 EasyMock 2.2 开始， `expectLastCall()` 和 `expect(T value)` 返回的对象提供了方法 `andAnswer(IAnswer answer)`，该方法允许指定用于创建返回的接口 `IAnswer` 的实现值或异常。

---

Inside an `IAnswer` callback, the arguments passed to the mock call are available via `EasyMock.getCurrentArgument(int index)`. 
If you use these, refactorings like reordering parameters may break your tests. 
You have been warned.


在 `IAnswer` 回调中，传递给模拟调用的参数可通过 `EasyMock.getCurrentArgument(int index)` 获得。
如果你使用这些，像重新排序参数这样的重构可能会破坏你的测试。
你被警告了。

---

An alternative to `IAnswer` are the `andDelegateTo` and `andStubDelegateTo` methods. 
They allow to delegate the call to a concrete implementation of the mocked interface that will then provide the answer. 
The pros are that the arguments found in `EasyMock.getCurrentArgument()` for `IAnswer` are now passed to the method of the concrete implementation. 
This is refactoring safe. 
The cons are that you have to provide an implementation which is kind of doing a mock manually... Which is what you try to avoid by using EasyMock. 
It can also be painful if the interface has many methods. 
Finally, the type of the concrete class can't be checked statically against the mock type. 
If for some reason, the concrete class isn't implementing the method that is delegated, you will get an exception during the replay only. 
However, this case should be quite rare.


`IAnswer` 的替代方法是 `andDelegateTo` 和 `andStubDelegateTo` 方法。
它们允许将调用委托给模拟接口的具体实现，然后提供答案。
优点是在 `EasyMock.getCurrentArgument()` 中为 `IAnswer` 找到的参数现在被传递给具体实现的方法。
这是重构安全。
缺点是您必须提供一种类似于手动模拟的实现......这是您使用 EasyMock 试图避免的。
如果接口有很多方法，也会很痛苦。
最后，不能根据模拟类型静态检查具体类的类型。
如果由于某种原因，具体类没有实现被委托的方法，您只会在重放期间收到异常。
但是，这种情况应该很少见。

---

To understand correctly the two options, here is an example:


为了正确理解这两个选项，这里有一个例子：

---

```text
List<String> l = mock(List.class);

// andAnswer style
expect(l.remove(10)).andAnswer(() -> getCurrentArgument(0).toString());

// andDelegateTo style
expect(l.remove(10)).andDelegateTo(new ArrayList<String>() {
  @Override
  public String remove(int index) {
    return Integer.toString(index);
  }
});
```

## Checking Method Call Order Between Mocks _检查模拟之间的方法调用顺序_

Up to this point, we have seen a mock object as a single object that is configured by static methods on the class `EasyMock`. 
But many of these static methods just identify the hidden control of the Mock Object and delegate to it. 
A Mock Control is an object implementing the `IMocksControl` interface.


到目前为止，我们已经将模拟对象看作是由类 `EasyMock` 上的静态方法配置的单个对象。
但是许多这些静态方法只是识别 Mock 对象的隐藏控件并将其委托给它。
Mock Control 是一个实现 `IMocksControl` 接口的对象。

---

So instead of


所以代替

---

```text
IMyInterface mock = strictMock(IMyInterface.class);
replay(mock);
verify(mock);
reset(mock);
```

we may use the equivalent code:


我们可以使用等效的代码：

---

```text
IMocksControl ctrl = createStrictControl();
IMyInterface mock = ctrl.createMock(IMyInterface.class);
ctrl.replay();
ctrl.verify();
ctrl.reset();
```

The `IMocksControl` allows to create more than one Mock Object, and so it is possible to check the order of method calls between mocks. 
As an example, we set up two mock objects for the interface `IMyInterface`, and we expect the calls `mock1.a()` and `mock2.a()` ordered, then an open number of calls to `mock1.c()` and `mock2.c()`, and finally `mock2.b()` and `mock1.b()`, in this order:


`IMocksControl` 允许创建多个模拟对象，因此可以检查模拟之间的方法调用顺序。
例如，我们为接口 `IMyInterface` 设置了两个模拟对象，我们期望调用`mock1.a()` 和`mock2.a()` 是有序的，然后是对`mock1.c 的开放调用次数()` 和 `mock2.c()`，最后是 `mock2.b()` 和 `mock1.b()`，按以下顺序：

---

```text
IMocksControl ctrl = createStrictControl();
IMyInterface mock1 = ctrl.createMock(IMyInterface.class);
IMyInterface mock2 = ctrl.createMock(IMyInterface.class);
mock1.a();
mock2.a();
ctrl.checkOrder(false);
mock1.c();
expectLastCall().anyTimes();
mock2.c();
expectLastCall().anyTimes();
ctrl.checkOrder(true);
mock2.b();
mock1.b();
ctrl.replay();
```

## Relaxing Call Counts _放宽呼叫计数_

To relax the expected call counts, there are additional methods that may be used instead of `times(int count)`:


为了放宽预期的调用次数，可以使用其他方法来代替 `times(int count)` ：

---

* `times(int min, int max)`
  to expect between `min` and `max` calls,

* `atLeastOnce()`
  to expect at least one call, and

* `anyTimes()`
  to expected an unrestricted number of calls.


* `times(int min, int max)`
  期望在 `min` 和 `max` 调用之间，

* `atLeastOnce()`
  预计至少有一个调用，并且

* `anyTimes()`
  预计调用次数不受限制。

---

If no call count is specified, one call is expected. 
If we would like to state this explicitely, `once()` or `times(1)` may be used.


如果未指定调用计数，则预期调用一次。
如果我们想明确说明这一点，可以使用 `once()` 或 `times(1)` 。

---

## Switching Order Checking On and Off _打开和关闭订单检查_

Sometimes, it is necessary to have a Mock Object that checks the order of only some calls. 
In record phase, you may switch order checking on by calling `checkOrder(mock, true)` and switch it off by calling `checkOrder(mock, false)`.


有时，需要一个 Mock 对象来检查一些调用的顺序。
在记录阶段，您可以通过调用 `checkOrder(mock, true)` 来打开订单检查，并通过调用 `checkOrder(mock, false)` 来关闭它。

---

There are two differences between a strict Mock Object and a normal Mock Object:


严格的 Mock 对象和普通的 Mock 对象有两个区别：

---

* A strict Mock Object has order checking enabled after creation.
* A strict Mock Object has order checking enabled after reset (see Reusing a Mock Object).


* 严格的模拟对象在创建后启用了顺序检查。
* 严格的模拟对象在重置后启用了顺序检查（请参阅重用模拟对象）。

---

## Flexible Expectations with Argument Matchers _参数匹配器的灵活期望_

To match an actual method call on the Mock Object with an expectation, `Object` arguments are by default compared with `equals()`. 
Arrays are by default `since 3.5` compared with `Arrays.equals()`.


为了将模拟对象上的实际方法调用与预期相匹配，默认情况下将 `Object` 参数与 `equals()` 进行比较。
与 `Arrays.equals()` 相比，数组默认是从 3.5 开始的。

---

But once in a while, you will want to match you parameter in a different way. 
As an example, we consider the following expectation:


但偶尔，您会希望以不同的方式匹配您的参数。
例如，我们考虑以下期望：

---

```text
Document document = getFromCache();
expect(mock.voteForRemovals(document)).andReturn(42);
```

Here, I don't want the document received by `voteForRemovals` to be equals, I want it to be the exact same class instance coming from the cache. 
My current expectation is not testing what I want.


在这里，我不希望 `voteForRemovals` 收到的文档是相等的，我希望它是来自缓存的完全相同的类实例。
我目前的期望不是测试我想要的。

---

To specify that the exact same instance is needed for this call, we use the method `same` that is statically imported from the `EasyMock` class:


为了指定这个调用需要完全相同的实例，我们使用从 `EasyMock` 类静态导入的方法 `same`：

---

```text
Document document = getFromCache();
expect(mock.voteForRemovals(same(document))).andReturn(42);
```

**Important**: When you use matchers in a call, you have to specify matchers **for all arguments of the method call**.


**重要提示**：在调用中使用匹配器时，必须为方法**调用的所有参数**指定匹配器。

---

There are a couple of predefined argument matchers available.


有几个预定义的参数匹配器可用。

---

* `eq(X value)`
  Matches if the actual value is equals the expected value. 
  Available for all primitive types and for objects.
  
  > 如果实际值等于预期值，则匹配。
  > 适用于所有原始类型和对象。

* `anyBoolean()`, `anyByte()`, `anyChar()`, `anyDouble()`, `anyFloat()`, `anyInt()`, `anyLong()`, `anyObject()`, `anyObject(Class clazz)`, `anyShort()`, `anyString()`
  Matches any value. 
  Available for all primitive types and for objects.
  
  > 匹配任何值。
  > 适用于所有原始类型和对象。

* `eq(X value, X delta)`
  Matches if the actual value is equal to the given value allowing the given delta. 
  Available for `float` and `double`.

  > 如果实际值等于允许给定增量的给定值，则匹配。
  > 可用于 `float` 和 `double` 。

* `aryEq(X value)`
  Matches if the actual value is equal to the given value according to `Arrays.equals()`. 
  Available for primitive and object arrays.

  > 根据 `Arrays.equals()` 匹配实际值是否等于给定值。
  > 可用于原始数组和对象数组。

* `isNull()`, `isNull(Class clazz)`
  Matches if the actual value is null. 
  Available for objects.

  > 如果实际值为空则匹配。
  > 可用于对象。

* `notNull()`, `notNull(Class clazz)`
  Matches if the actual value is not null. 
  Available for objects.

  > 如果实际值不为空则匹配。
  > 可用于对象。

* `same(X value)`
  Matches if the actual value is the same as the given value. 
  Available for objects.

  > 如果实际值与给定值相同，则匹配。
  > 可用于对象。

* `isA(Class clazz)`
  Matches if the actual value is an instance of the given class, or if it is in instance of a class that extends or implements the given class. 
  Null always return false. 
  Available for objects.

  > 如果实际值是给定类的实例，或者它是扩展或实现给定类的类的实例，则匹配。
  > Null 总是返回 false。
  > 可用于对象。

* `lt(X value)`, `leq(X value)`, `geq(X value)`, `gt(X value)`
  Matches if the actual value is less/less or equal/greater or equal/greater than the given value. 
  Available for all numeric primitive types and Comparable.

  > 如果实际值小于或等于大于或等于给定值，则匹配。
  > 适用于所有数字基元类型和 Comparable。

* `startsWith(String prefix)`, `contains(String substring)`, `endsWith(String suffix)`
  Matches if the actual value starts with/contains/ends with the given value. 
  Available for Strings.

  > 如果实际值以包含给定值开头，则匹配。
  > 可用于字符串。

* `matches(String regex)`, `find(String regex)`
  Matches if the actual value/a substring of the actual value matches the given regular expression. 
  Available for Strings.

  > 如果实际值的实际值子字符串与给定的正则表达式匹配，则匹配。
  > 可用于字符串。

* `and(X first, X second)`
  Matches if the matchers used in first and second both match. 
  Available for all primitive types and for objects.

  > 如果第一次和第二次使用的匹配器都匹配，则匹配。
  > 适用于所有原始类型和对象。

* `or(X first, X second)`
  Matches if one of the matchers used in first and second match. 
  Available for all primitive types and for objects.

  > 如果在第一场和第二场比赛中使用了一个匹配器，则匹配。
  > 适用于所有原始类型和对象。

* `not(X value)`
  Matches if the matcher used in value does not match.

  > 如果 value 中使用的匹配器不匹配，则匹配。

* `cmpEq(X value)`
  Matches if the actual value is equals according to `Comparable.compareTo(X o)`. 
  Available for all numeric primitive types and `Comparable`.

  > 如果实际值根据 `Comparable.compareTo(X o)` 相等，则匹配。
  > 适用于所有数字原始类型和 `Comparable` 。

* `cmp(X value, Comparator<X> comparator, LogicalOperator operator)`
  Matches if `comparator.compare(actual, value) operator 0` where the operator is `<`,`<=`,`>`,`>=` or `==`. 
  Available for objects.

  > 匹配 if `comarator.compare(actual, value) operator 0` ，其中操作符为 `<` 、 `<=` 、 `>` 、 `>=` 或 `==` 。
  > 可用于对象。

* `capture(Capture<T> capture)`, `captureXXX(Capture<T> capture)`
  Matches any value but captures it in the `Capture` parameter for later access. 
  You can do `and(someMatcher(...), capture(c))` to capture a parameter from a specific call to the method. 
  You can also specify a `CaptureType` telling that a given `Capture` should keep the first, the last, all or no captured values.

  > 匹配任何值，但在 `Capture` 参数中捕获它以供以后访问。
  > 您可以执行 `and(someMatcher(...), capture(c))` 来从对方法的特定调用中捕获参数。
  > 您还可以指定一个 `CaptureType` 告诉给定的 `Capture` 应该保留第一个、最后一个、全部或没有捕获的值。

## Defining your own Argument Matchers _定义你自己的参数匹配器_

Sometimes it is desirable to define own argument matchers. 
Let's say that an argument matcher is needed that matches an exception if the given exception has the same type and an equal message. 
It should be used this way:


有时需要定义自己的参数匹配器。
假设如果给定的异常具有相同的类型和相同的消息，则需要一个参数匹配器来匹配异常。
应该这样使用：

---

```text
IllegalStateException e = new IllegalStateException("Operation not allowed.")
expect(mock.logThrowable(eqException(e))).andReturn(true);
```

Two steps are necessary to achieve this: 
The new argument matcher has to be defined, and the static method `eqException` has to be declared.


实现这一点需要两个步骤：
必须定义新的参数匹配器，并且必须声明静态方法 `eqException` 。

---

To define the new argument matcher, we implement the interface `org.easymock.IArgumentMatcher`. 
This interface contains two methods: 
`matches(Object actual)` checks whether the actual argument matches the given argument, and `appendTo(StringBuffer buffer)` appends a string representation of the argument matcher to the given string buffer. 
The implementation is straightforward:


为了定义新的参数匹配器，我们实现了接口 `org.easymock.IArgumentMatcher` 。
该接口包含两个方法：
`matches(Object actual)` 检查实际参数是否与给定的参数匹配，并且 `appendTo(StringBuffer buffer)` 将参数匹配器的字符串表示形式附加到给定的字符串缓冲区。
实现很简单：

---

```java
import org.easymock.IArgumentMatcher;

public class ThrowableEquals implements IArgumentMatcher {

  private Throwable expected;

  public ThrowableEquals(Throwable expected) {
    this.expected = expected;
  }

  public boolean matches(Object actual) {
    if (!(actual instanceof Throwable)) {
      return false;
    }

    String actualMessage = ((Throwable) actual).getMessage();
    return expected.getClass().equals(actual.getClass()) &amp;&amp; expected.getMessage().equals(actualMessage);
  }

  public void appendTo(StringBuffer buffer) {
    buffer.append("eqException(");
    buffer.append(expected.getClass().getName());
    buffer.append(" with message \"");
    buffer.append(expected.getMessage());
    buffer.append("\"")");
  }
}
```

The method `eqException` must create the argument matcher with the given Throwable, report it to EasyMock via the static method `reportMatcher(IArgumentMatcher matcher)`, and return a value so that it may be used inside the call (typically `0`, `null` or `false`). 
A first attempt may look like:


方法 `eqException` 必须使用给定的 Throwable 创建参数匹配器，通过静态方法 `reportMatcher(IArgumentMatcher matcher)` 将其报告给 EasyMock，并返回一个值以便它可以在调用中使用（通常为 `0` 、 `null` 或 `false` ）。
第一次尝试可能如下所示：

---

```text
public static Throwable eqException(Throwable in) {
  EasyMock.reportMatcher(new ThrowableEquals(in));
  return null;
}
```

However, this only works if the method `logThrowable` in the example usage accepts `Throwable`s, and does not require something more specific like a `RuntimeException`. 
In the latter case, our code sample would not compile:


但是，这仅在示例用法中的 `logThrowable` 方法接受 `Throwable` 时才有效，并且不需要像 `RuntimeException` 这样的更具体的东西。
在后一种情况下，我们的代码示例将无法编译：

---

```text
IllegalStateException e = new IllegalStateException("Operation not allowed.")
expect(mock.logThrowable(eqException(e))).andReturn(true);
```

Java 5.0 to the rescue: 
Instead of defining `eqException` with a `Throwable` as parameter and return value, we use a generic type that extends `Throwable`:


Java 5.0 来拯救：
我们没有使用 `Throwable` 作为参数和返回值定义 `eqException`，而是使用扩展 `Throwable` 的泛型类型：

---

```text
public static&lt;T extends Throwable&gt;T eqException(T in) {
  reportMatcher(new ThrowableEquals(in));
  return null;
}
```
