# Behavior _行为_

## A second test _第二次测试_

Let us write a second test. 
If a document is added on the class under test, we expect a call to `mock.documentAdded()` on the Mock Object with the title of the document as argument:


让我们编写第二个测试。
如果在被测试的类上添加了一个文档，我们期望在 Mock 对象上调用 `mock.documentAdded()`，并将文档的标题作为参数：

---

```text
@Test
public void testAddDocument() {
  mock.documentAdded("New Document"); // 2
  replay(mock); // 3
  classUnderTest.addDocument("New Document", new byte[0]);
}
```

So in the record state (before calling `replay`), the Mock Object does not behave like a Mock Object, but it records method calls. 
After calling `replay`, it behaves like a Mock Object, checking whether the expected method calls are really done.


所以在记录状态（在调用`replay` 之前），Mock 对象的行为不像 Mock 对象，而是记录方法调用。
在调用 `replay` 之后，它的行为就像一个 Mock 对象，检查预期的方法调用是否真的完成了。

---

If `classUnderTest.addDocument("New Document", new byte[0])` calls the expected method with a wrong argument, the Mock Object will complain with an `AssertionError`:


如果 `classUnderTest.addDocument("New Document", new byte[0])` 使用错误的参数调用预期的方法，Mock 对象将抱怨`AssertionError`：

---

```log
java.lang.AssertionError:
  Unexpected method call documentAdded("Wrong title"):
    documentAdded("New Document"): expected: 1, actual: 0
      at org.easymock.internal.MockInvocationHandler.invoke(MockInvocationHandler.java:29)
      at org.easymock.internal.ObjectMethodsFilter.invoke(ObjectMethodsFilter.java:44)
      at $Proxy0.documentAdded(Unknown Source)
      at org.easymock.samples.ClassUnderTest.notifyListenersDocumentAdded(ClassUnderTest.java:61)
      at org.easymock.samples.ClassUnderTest.addDocument(ClassUnderTest.java:28)
      at org.easymock.samples.ExampleTest.testAddDocument(ExampleTest.java:30)
      ...
```

All missed expectations are shown, as well as all fulfilled expectations for the unexpected call (none in this case). 
If the method call is executed too often, the Mock Object complains, too:


显示了所有未达到的期望，以及对意外调用的所有满足的期望（在本例中没有）。
如果方法调用执行得太频繁，Mock 对象也会抱怨：

---

```log
java.lang.AssertionError:
  Unexpected method call documentAdded("New Document"):
    documentAdded("New Document"): expected: 1, actual: 2
      at org.easymock.internal.MockInvocationHandler.invoke(MockInvocationHandler.java:29)
      at org.easymock.internal.ObjectMethodsFilter.invoke(ObjectMethodsFilter.java:44)
      at $Proxy0.documentAdded(Unknown Source)
      at org.easymock.samples.ClassUnderTest.notifyListenersDocumentAdded(ClassUnderTest.java:62)
      at org.easymock.samples.ClassUnderTest.addDocument(ClassUnderTest.java:29)
      at org.easymock.samples.ExampleTest.testAddDocument(ExampleTest.java:30)
      ...
```

## Changing Behavior for the Same Method Call _更改相同方法调用的行为_

It is also possible to specify a changing behavior for a method. 
The methods `times`, `andReturn`, and `andThrow` may be chained. 
As an example, we define `voteForRemoval("Document")` to


也可以为方法指定变化的行为。
方法 `times` 、 `andReturn` 和 `andThrow` 可以链接起来。
例如，我们将 `voteForRemoval("Document")` 定义为

---

* return 42 for the first three calls,
* throw a `RuntimeException` for the next four calls,
* return -42 once.


* 前三个调用返回 42，
* 为接下来的四次调用抛出一个 `RuntimeException` ，
* 返回 -42 一次。

---

```text
expect(mock.voteForRemoval("Document"))
  .andReturn((byte) 42).times(3)
  .andThrow(new RuntimeException(), 4)
  .andReturn((byte) -42);
```

## Altering EasyMock default behavior _改变 EasyMock 默认行为_

EasyMock provides a property mechanisim allowing to alter its behavior. 
It mainly aims at allowing to use a legacy behavior on a new version. 
Currently supported properties are:

`easymock.notThreadSafeByDefault`
If true, a mock won't be thread-safe by default. Possible values are "true" or "false". 
Default is false

`easymock.enableThreadSafetyCheckByDefault`
If true, thread-safety check feature will be on by default. 
Possible values are "true" or "false". 
Default is false

`easymock.disableClassMocking`
Do not allow class mocking (only allow interface mocking). 
Possible values are "true" or "false". 
Default is false.

Properties can be set in two ways.

* In an `easymock.properties` file set in the classpath default package
* By calling `EasyMock.setEasyMockProperty`. 
    Constants are available in the `EasyMock` class. 
    Setting properties in the code obviously override any property set in `easymock.properties`

## Object Methods _对象方法_

The behavior for the four Object methods `equals()`, `hashCode()`, `toString()` and `finalize()` cannot be changed for Mock Objects created with EasyMock, even if they are part of the interface for which the Mock Object is created.

## Using Stub Behavior for Methods _对方法使用 Stub 行为_

Sometimes, we would like our Mock Object to respond to some method calls, but we do not want to check how often they are called, when they are called, or even if they are called at all. 
This stub behavoir may be defined by using the methods `andStubReturn(Object value)`, `andStubThrow(Throwable throwable)`, `andStubAnswer(IAnswer<T> answer)` and `asStub()`. 
The following code configures the MockObject to answer 42 to `voteForRemoval("Document")` once and -1 for all other arguments:

```text
expect(mock.voteForRemoval("Document")).andReturn(42);
expect(mock.voteForRemoval(not(eq("Document")))).andStubReturn(-1);
```

## Reusing a Mock Object _重用模拟对象_

Mock Objects may be reset by `reset(mock)`.

If needed, a mock can also be converted from one type to another by calling `resetToNice(mock)`, `resetToDefault(mock)` or `resetToStrict(mock)`.
