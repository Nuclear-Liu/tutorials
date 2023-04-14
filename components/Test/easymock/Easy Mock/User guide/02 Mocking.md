# Mocking


## The first Mock Object _第一个 Mock Object_


We will now build a test case and toy around with it to understand the functionalities of EasyMock. 
You can also have a look at the [samples](https://github.com/easymock/easymock/tree/easymock-4.3/core/src/samples/java/org/easymock/samples) and the [Getting Started](https://easymock.org/getting-started.html).


我们现在将构建一个测试用例并使用它来理解 EasyMock 的功能。
您还可以查看 [samples](https://github.com/easymock/easymock/tree/easymock-4.3/core/src/samples/java/org/easymock/samples) 和 [Getting Started](./../Getting%20Started.md)。


Our first test should check whether the removal of a non-existing document does **not** lead to a notification of the collaborator. 
Here is the test without the definition of the Mock Object:


我们的第一个测试应该检查删除不存在的文档是否**不会**导致协合作者的通知。
这是没有 Mock Object 定义的测试：


```java
import org.junit.*;

public class ExampleTest {

  private ClassUnderTest classUnderTest;

  private Collaborator mock;

  @Before
  public void setUp() {
    classUnderTest = new ClassUnderTest();
    classUnderTest.setListener(mock);
  }

  @Test
  public void testRemoveNonExistingDocument() {
    // This call should not lead to any notification
    // of the Mock Object:
    classUnderTest.removeDocument("Does not exist");
  }
}
```

For many tests using EasyMock, we only need a static import of methods of `org.easymock.EasyMock`.


对于许多使用 EasyMock 的测试，我们只需要静态导入 `org.easymock.EasyMock` 的方法。


```java
import static org.easymock.EasyMock.*;
import org.junit.*;

public class ExampleTest {
  private ClassUnderTest classUnderTest;
  private Collaborator mock;
}
```

To get a Mock Object, we need to


要获得一个 Mock Object ，我们需要


1. create a Mock Object for the interface we would like to simulate
2. record the expected behavior
3. switch the Mock Object to replay state


1. 为我们想要模拟的接口创建一个 Mock Object
2. 记录预期行为
3. 将 Mock Object 切换到重放状态


Here is a first example:


这是第一个例子：


```text
@Before
public void setUp() {
  mock = mock(Collaborator.class); // 1
  classUnderTest = new ClassUnderTest();
  classUnderTest.setListener(mock);
}

@Test
public void testRemoveNonExistingDocument() {
  // 2 (we do not expect anything)
  replay(mock); // 3
  classUnderTest.removeDocument("Does not exist");
}
```

After activation in step 3, `mock` is a Mock Object for the `Collaborator` interface that expects no calls. 
This means that if we change our `ClassUnderTest` to call any of the interface's methods, the Mock Object will throw an `AssertionError`:


在第 3 步激活后， `mock` 是一个不需要调用的 `Collaborator` 接口的 Mock Object 。
这意味着如果我们改变我们的 `ClassUnderTest` 来调用接口的任何方法， Mock Object 对象将抛出一个 `AssertionError` ：


```log
java.lang.AssertionError:
  Unexpected method call documentRemoved("Does not exist"):
    at org.easymock.internal.MockInvocationHandler.invoke(MockInvocationHandler.java:29)
    at org.easymock.internal.ObjectMethodsFilter.invoke(ObjectMethodsFilter.java:44)
    at $Proxy0.documentRemoved(Unknown Source)
    at org.easymock.samples.ClassUnderTest.notifyListenersDocumentRemoved(ClassUnderTest.java:74)
    at org.easymock.samples.ClassUnderTest.removeDocument(ClassUnderTest.java:33)
    at org.easymock.samples.ExampleTest.testRemoveNonExistingDocument(ExampleTest.java:24)
      ...
```


## Using annotations `Since 3.2` _使用注解_


There is a nice and shorter way to create your mocks and inject them to the tested class. 
Here is the example above, now using annotations:


有一种很好且更短的方法来创建模拟并将它们注入到测试类中。
这是上面的示例，现在使用注解：


```java
import static org.easymock.EasyMock.*;
import org.easymock.EasyMockRunner;
import org.easymock.TestSubject;
import org.easymock.Mock;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(EasyMockRunner.class)
public class ExampleTest {

  @TestSubject
  private ClassUnderTest classUnderTest = new ClassUnderTest(); // 2

  @Mock
  private Collaborator mock; // 1

  @Test
  public void testRemoveNonExistingDocument() {
    replay(mock);
    classUnderTest.removeDocument("Does not exist");
  }
}
```


The `mock` is instantiated by the runner at step 1. 
It is then set by the runner, to the `listener` field on step 2. 
The `setUp` method can be removed since all the initialization was done by the runner.


`mock` 由运行程序在第 1 步实例化。
然后由 runner 者将其设置为第 2 步中的 `listener` 字段。
`setUp` 方法可以被移除，因为所有的初始化都是由 runner 完成的。


On top of that, since `EasyMock 3.3`, if you need to use another runner on you tests, a JUnit rule is also available to you. 
Both have the exact same behavior. 
Choosing one of the other is a matter of taste.


最重要的是，从 `EasyMock 3.3` 开始，如果你需要在测试中使用另一个运行器，你也可以使用 JUnit 规则。
两者都有完全相同的行为。
选择其中之一是一个品味问题。


```java
import static org.easymock.EasyMock.*;
import org.easymock.EasyMockRule;
import org.easymock.TestSubject;
import org.easymock.Mock;
import org.junit.Rule;
import org.junit.Test;

public class ExampleTest {

  @Rule
  public EasyMockRule mocks = new EasyMockRule(this);

  @TestSubject
  private ClassUnderTest classUnderTest = new ClassUnderTest();

  @Mock
  private Collaborator mock;

  @Test
  public void testRemoveNonExistingDocument() {
    replay(mock);
    classUnderTest.removeDocument("Does not exist");
  }
}
```


Finally, since EasyMock 4.1, JUnit 5 extensions are supported.


最后，从 EasyMock 4.1 开始，支持 JUnit 5 扩展。


```java
import static org.easymock.EasyMock.*;
import org.easymock.EasyMockExtension;
import org.easymock.TestSubject;
import org.easymock.Mock;
import org.junit.Rule;
import org.junit.Test;

@ExtendWith(EasyMockExtension.class)
public class ExampleTest {

  @TestSubject
  private ClassUnderTest classUnderTest = new ClassUnderTest();

  @Mock
  private Collaborator mock;

  @Test
  public void testRemoveNonExistingDocument() {
    replay(mock);
    classUnderTest.removeDocument("Does not exist");
  }
}
```


The annotation has an optional element, `type`, to refine the `mock` as a `nice` mock or a `strict` mock. 
Another optional annotation, `name`, allows setting of a name for the mock that will be used in the `mock()` call, which will appear in expectation failure messages for example. 
Finally, an optional element, `fieldName`, allows specifying the target field name where the mock should be injected. 
Mocks are injected to any field in any `@TestSubject` that is of compatible type. 
If more than one mock can be assigned to the same field then this is considered an error. 
The `fieldName` qualifier can be used in this scenario to disambiguate the assignments.


注解有一个可选元素 `type` ，用于将 `mock` 优化为 `nice` 模拟或 `strict` 模拟。
另一个可选的注解， `name` ，允许为将在 `mock()` 调用中使用的模拟设置一个名称，例如，它将出现在预期失败消息中。
最后，可选元素 `fieldName` 允许指定应注入模拟的目标字段名称。
模拟被注入到任何兼容类型的 `@TestSubject` 中的任何字段。
如果可以将多个模拟分配给同一字段，则这将被视为错误。
在这种情况下可以使用 `fieldName` 限定符来消除分配的歧义。


```text
@Mock(type = MockType.NICE, name = "mock", fieldName = "someField")
private Collaborator mock;

@Mock(type = MockType.STRICT, name = "anotherMock", fieldName = "someOtherField")
private Collaborator anotherMock;
```


## `EasyMockSupport`

`EasyMockSupport` is a class that exist to help you keeping track of your mock. 
Your test cases should extend or delegate to it. 
It will automatically registers all created `mock`s and replay, reset or verify them in batch instead of explicitly. 
Here's an example:


`EasyMockSupport` 是一个用来帮助你跟踪你的模拟的类。
您的测试用例应该扩展或委托给它。
它将自动注册所有创建的 `mock` 并批量重放、重置或验证它们，而不是明确地。
下面是一个例子：

---

```java
public class SupportTest extends EasyMockSupport {

  private Collaborator firstCollaborator;
  private Collaborator secondCollaborator;
  private ClassTested classUnderTest;

  @Before
  public void setup() {
    classUnderTest = new ClassTested();
  }

  @Test
  public void addDocument() {
    // creation phase
    firstCollaborator = mock(Collaborator.class);
    secondCollaborator = mock(Collaborator.class);
    classUnderTest.addListener(firstCollaborator);
    classUnderTest.addListener(secondCollaborator);

    // recording phase
    firstCollaborator.documentAdded("New Document");
    secondCollaborator.documentAdded("New Document");
    replayAll(); // replay all mocks at once

    // test
    classUnderTest.addDocument("New Document", new byte[0]);
    verifyAll(); // verify all mocks at once
  }
}
```


Alternatively, you can also use `EasyMockSupport` through delegation as shown below.


或者，您也可以通过委托使用 `EasyMockSupport`，如下所示。


```java
public class SupportDelegationTest {

  private EasyMockSupport support = new EasyMockSupport();

  private Collaborator collaborator;

  private ClassTested classUnderTest;

  @Before
  public void setup() {
    classUnderTest = new ClassTested();
  }

  @Test
  public void addDocument() {
    collaborator = support.mock(Collaborator.class);
    classUnderTest.setListener(collaborator);
    collaborator.documentAdded("New Document");
    support.replayAll();
    classUnderTest.addDocument("New Document", "content");
    support.verifyAll();
  }

  @Test
  public void voteForRemovals() {

    IMocksControl ctrl = support.createControl();
    collaborator = ctrl.createMock(Collaborator.class);
    classUnderTest.setListener(collaborator);

    collaborator.documentAdded("Document 1");

    expect(collaborator.voteForRemovals("Document 1")).andReturn((byte) 20);

    collaborator.documentRemoved("Document 1");

    support.replayAll();

    classUnderTest.addDocument("Document 1", "content");
    assertTrue(classUnderTest.removeDocuments("Document 1"));

    support.verifyAll();
  }
}
```


## `Strict` Mocks _`Strict` 模拟_


On a Mock Object returned by a `EasyMock.mock()`, the order of method calls is not checked. 
If you would like a strict Mock Object that checks the order of method calls, use `EasyMock.strictMock()` to create it. 
The equivalent annotation is `@Mock(MockType.STRICT)`.


在由 `EasyMock.mock()` 返回的 Mock 对象上，不会检查方法调用的顺序。
如果您想要一个检查方法调用顺序的 strict Mock Object ，请使用 `EasyMock.strictMock()` 来创建它。
等效的注解是 `@Mock(MockType.STRICT)` 。


If an unexpected method is called on a strict Mock Object, the message of the exception will show the method calls expected at this point followed by the first conflicting one. 
`verify(mock)` shows all missing method calls.


如果对 strict Mock Object 调用意外的方法，异常消息将显示此时预期的方法调用，然后是第一个冲突的方法调用。
`verify(mock)` 显示所有缺少的方法调用。


## `Nice` Mocks _`Nice` 模拟_


On a Mock Object returned by `mock()` the default behavior for all methods is to throw an `AssertionError` for all unexpected method calls. 
If you would like a `nice` Mock Object that by default allows all method calls and returns appropriate empty values (`0`, `null` or `false`), use `niceMock()` instead. 
The equivalent annotation is `@Mock(MockType.NICE)`.


在由 `mock()` 返回的 Mock Object 上，所有方法的默认行为是为所有意外的方法调用抛出 `AssertionError` 。
如果你想要一个默认允许所有方法调用并返回适当的空值（`0`、`null` 或 `false`）的 `nice` 模拟对象，请改用 `niceMock()`。
等效的注解是 `@Mock(MockType.NICE)`。


## Partial mocking _部分的模拟_


Sometimes you may need to mock only some methods of a class and keep the normal behavior of others. 
This usually happens when you want to test a method that calls some others in the same class. 
So you want to keep the normal behavior of the tested method and mock the others.


有时你可能只需要模拟一个类的一些方法，而保持其他的正常行为。
这通常发生在您想要测试调用同一类中其他一些方法的方法时。
所以你想保持测试方法的正常行为并模拟其他方法。


In this case, the first thing to do is to consider a refactoring since most of the time this problem was caused by a bad design. 
If it's not the case, or if you can't do otherwise because of some development constraints, here's the solution:


在这种情况下，首先要做的是考虑重构，因为大多数情况下这个问题是由糟糕的设计引起的。
如果情况并非如此，或者由于某些开发限制而无法执行其他操作，请使用以下解决方案：


```text
ToMock mock = partialMockBuilder(ToMock.class)
  .addMockedMethod("mockedMethod").createMock();
```


In this case only the methods added with `addMockedMethod(s)` will be mocked (`mockedMethod()` in the example). 
The others will still behave as they used to. 
One exception: abstract methods are conveniently mocked by default.


在这种情况下，只会模拟添加了 `addMockedMethod(s)` 的方法（示例中的 `mockedMethod()` ）。
其他人仍然会像以前一样行事。
一个例外：默认情况下可以方便地模拟抽象方法。


`partialMockBuilder` returns a `IMockBuilder` interface. 
It contains various methods to easily create a partial mock. 
Have a look at the javadoc.


`partialMockBuilder` 返回一个 `IMockBuilder` 接口。
它包含各种方法来轻松创建部分模拟。
看看 javadoc。


**Remark**: EasyMock provides a default behavior for Object's methods (`equals`, `hashCode`, `toString`, `finalize`). 
However, for a partial mock, if these methods are not mocked explicitly, they will have their normal behavior instead of EasyMock default's one.


**备注**：EasyMock 为 Object 的方法（ `equals`、 `hashCode`、 `toString`、 `finalize`）提供了默认行为。
然而，对于部分模拟，如果这些方法没有被显式模拟，它们将具有其正常行为，而不是 EasyMock 默认的行为。


## Self testing _自测_


It is possible to create a mock by calling one of its constructor. 
This can be handy when a class method needs to be tested but the class other methods, mocked. 
For that you should do something like


可以通过调用其构造函数之一来创建模拟。
当需要测试类方法但模拟类的其他方法时，这会很方便。
为此，你应该做类似的事情


```text
ToMock mock = partialMockBuilder(ToMock.class)
  .withConstructor(1, 2, 3); // 1, 2, 3 are the constructor parameters
```


See the `ConstructorCalledMockTest` for an example.


有关示例，请参阅 `ConstructorCalledMockTest` 。


## Replace default class instantiator _替换默认类实例化器_


For some reason (usually an unsupported JVM), it is possible that EasyMock isn't able to mock a class mock in your environment. 
Under the hood, class instantiation is implemented with a factory pattern. 
In case of failure, you can replace the default instantiator with:


由于某种原因（通常是不受支持的 JVM），EasyMock 可能无法在您的环境中模拟类模拟。
在幕后，类实例化是用工厂模式实现的。
如果失败，您可以将默认实例化器替换为：


* The good old `DefaultClassInstantiator` which works well with `Serializable` classes and otherwise tries to guess the best constructor and parameters to use.
* You own instantiator which only needs to implement `IClassInstantiator`.


* 旧的 `DefaultClassInstantiator` 可以很好地与 `Serializable` 类配合使用，否则会尝试猜测要使用的最佳构造函数和参数。
* 您拥有只需要实现 `IClassInstantiator` 的实例化器。


You set this new instantiator using `ClassInstantiatorFactory.setInstantiator()`. 
You can set back the default one with `setDefaultInstantiator()`.


你使用 `ClassInstantiatorFactory.setInstantiator()` 设置这个新的实例化器。
您可以使用 `setDefaultInstantiator()` 设置回默认值。


**Important**:The instantiator is kept statically so it will stick between your unit tests. 
Make sure you reset it if needed.


**重要提示**：实例化器保持静态，因此它会在您的单元测试之间保持不变。
如果需要，请确保将其重置。


## Serialize a class mock _序列化类模拟_


A class mock can also be serialized. 
However, since it extends a serializable class, this class might have defined a special behavior using for instance `writeObject`. 
These methods will still be called when serializing the mock and might fail. 
The workaround is usually to **call a constructor when creating the mock**.


类模拟也可以序列化。
然而，由于它扩展了一个可序列化的类，这个类可能已经使用例如 `writeObject` 定义了一个特殊的行为。
这些方法在序列化模拟时仍会被调用，并且可能会失败。
解决方法通常是**在创建模拟时调用构造函数**。


Also, de-serializing the mock in a different class loader than the serialization might fail. 
It wasn't tested.


此外，在与序列化不同的类加载器中反序列化模拟可能会失败。
它没有经过测试。


## Class Mocking Limitations _类模拟限制_

* To be coherent with interface mocking, EasyMock provides a built-in behavior for `equals()`, `toString()`, `hashCode()` and `finalize()` even for class mocking. 
  It means that you cannot record your own behavior for these methods. 
  This limitation is considered to be a feature that prevents you from having to care about these methods.
* **Final methods cannot be mocked**. 
  If called, their normal code will be executed.
* **Private methods cannot be mocked**. 
  If called, their normal code will be executed. 
  During partial mocking, if your method under test is calling some private methods, you will need to test them as well since you cannot mock them.
* Class instantiation is performed using [Objenesis](http://objenesis.org/). 
  Supported JVMs are listed [here](https://github.com/easymock/objenesis/blob/master/SupportedJVMs.md).


* 为了与接口模拟保持一致，EasyMock 为 `equals()`、`toString()`、`hashCode()` 和 `finalize()` 提供了一个内置行为，甚至对于类模拟也是如此。
  这意味着您无法为这些方法记录自己的行为。
  此限制被认为是一种功能，可防止您不必关心这些方法。
* **最终方法不能被模拟**。
  如果调用，它们的正常代码将被执行。
* **私有方法不能被模拟**。
  如果调用，它们的正常代码将被执行。
  在部分模拟期间，如果您的被测方法正在调用一些私有方法，您也需要测试它们，因为您无法模拟它们。
* 使用 [Objenesis]() 执行类实例化。
  [here]() 列出了受支持的 JVM。


## Naming Mock Objects _命名模拟对象_


Mock Objects can be named at creation using `mock(String name, Class<T> toMock)`, `strictMock(String name, Class<T> toMock)` or `niceMock(String name, Class<T> toMock)`. 
The names will be shown in exception failures.


模拟对象可以在创建时使用 `mock(String name, Class<T> toMock)` 、 `strictMock(String name, Class<T> toMock)` 或 `niceMock(String name, Class<T> toMock)` 命名。
名称将显示在异常失败中。
