# Getting Started _入门_

You have brand new class. 
Let's call it `ClassTested`.


你有全新的类。
我们称之为 `ClassTested` 。


```java
public class ClassTested {

  private Collaborator listener;

  public void setListener(Collaborator listener) {
    this.listener = listener;
  }

  public void addDocument(String title, String document) {
    listener.documentAdded(title);
  }
}
```


Being a nice human being, you want to test your implementation. 
You might even be a disciple of TDD and haven't done your implementation yet. 
You want your test first!


作为一个好人，你想测试你的实现。
您甚至可能是 TDD 的弟子并且还没有完成您的实现。
你要先测试！


Your tested class will depend on others so you figured you need a mocking framework. 
Good for you. 
Add EasyMock dependency to your `POM` file.


您的测试类将依赖于其他类，因此您认为需要一个模拟框架。
对你有益。
将 EasyMock 依赖项添加到您的 `POM` 文件中


```xml
<dependency>
  <groupId>org.easymock</groupId>
  <artifactId>easymock</artifactId>
  <version>4.3</version>
  <scope>test</scope>
</dependency>
```


Ok. Now `addDocument` should do stuff and then notify a dependency. 
Let's call it `Collaborator`.


好的。现在 `addDocument` 应该做一些事情，然后通知一个依赖项。
我们称之为 `Collaborator` 。


```java
public interface Collaborator {
    void documentAdded(String title);
}
```


> Now, a word of warning. 
> I will mock an interface in this example. 
> That doesn't mean you should only mock interfaces. 
> I hate useless interfaces. 
> And you want me to be happy. 
> Please don't create an interface just for the pleasure of mocking it. 
> Just mock the concrete class. 
> Thank you.


> 现在，警告。
> 在这个例子中，我将模拟一个接口。 
> 这并不意味着您应该只模拟接口。
> 我讨厌无用的接口。
> 而你想让我开心。
> 请不要仅仅为了模拟它而创建一个接口。
> 只是模拟具体的类。
> 谢谢你。


So, we want to make sure `addDocument` is notifying `Collaborator` by calling `documentAdded` with the right `title` in argument. 
Our todo list to do that:


因此，我们希望通过在参数中使用正确的 `title` 调用 `documentAdded` 来确保 `addDocument` 正在通知 `Collaborator` 。
我们的待办事项清单可以做到这一点：


1. Create the mock
2. Have it set to the tested class
3. Record what we expect the mock to do
4. Tell all mocks we are now doing the actual testing
5. Test
6. Make sure everything that was supposed to be called was called


1. 创建模拟
2. 将其设置为测试类
3. 记录我们期望模拟执行的操作
4. 告诉所有的 mocks 我们现在正在做实际的测试
5. 测试
6. 确保所有应该被调用的东西都被调用了


Then the code fulfilling it:


然后实现它的代码：

```java
import static org.easymock.EasyMock.*;
import org.easymock.*;
import org.junit.*;

public class ExampleTest extends EasyMockSupport {

    @Rule
    public EasyMockRule rule = new EasyMockRule(this);

    @Mock
    private Collaborator collaborator; // 1

    @TestSubject
    private ClassTested classUnderTest = new ClassTested(); // 2

    @Test
    public void addDocument() {
        collaborator.documentAdded("New Document"); // 3
        replayAll(); // 4
        classUnderTest.addDocument("New Document", "content"); // 5
        verifyAll(); // 6
    }
}
```

And that's all you need to get you started. 
Some comments though:


这就是您开始所需的全部内容。
不过也有一些评论：


* Extending (or delegating to) `EasyMockSupport` is useful but not mandatory. 
    It allows to call `replayAll` instead of `replay(mock1, mock2, ...)` for instance
* Mock injection is done on fields (by `EasyMockRule`) so you won't need a `setter` only used for testing
* We are testing that `documentAdded` is called only once and receiving this exact parameter. 
    Any other call to our mock is a test failure


* 继承（或委托） `EasyMockSupport` 很有用，但不是强制性的。
  例如，它允许调用 `replayAll` 而不是 `replay(mock1, mock2, ...)`
* 模拟注入是在字段上完成的（通过 `EasyMockRule` ）所以你不需要一个只用于测试的 `setter`
* 我们正在测试 `documentAdded` 只被调用一次并接收这个确切的参数。
  对我们模拟的任何其他调用都是测试失败


From there, I will highly suggest you have a look at the [samples](https://github.com/easymock/easymock/tree/easymock-4.3/core/src/samples/java/org/easymock/samples) and the [full documentation](https://easymock.org/user-guide.html) to get a fair overview of EasyMock.


从那里，我强烈建议您查看 [samples](https://github.com/easymock/easymock/tree/easymock-4.3/core/src/samples/java/org/easymock/samples) 和 [full documentation](./User%20guide/User%20Guide.md) 以获得对 EasyMock 的大致情况。


> Happy mocking!


> mocking 快乐！

---
