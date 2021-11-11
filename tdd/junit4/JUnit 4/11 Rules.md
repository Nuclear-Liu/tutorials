# Rules *规则*

> shuebdalvi edited this page on Jun 6, 2018 · 33 revisions 

> shuebdalvi 于 2018 年 6 月 6 日编辑了此页面 · 33 次修订

### Rules *规则*

* Rules allow very flexible addition or redefinition of the behavior of each test method in a test class. 
    Testers can reuse or extend one of the provided Rules below, or write their own.

### Example *示例*

For an example of a rule usage, there follows a test using the TemporaryFolder and ExpectedException rules:

```java
public class DigitalAssetManagerTest {
  @Rule
  public final TemporaryFolder tempFolder = new TemporaryFolder();

  @Rule
  public final ExpectedException exception = ExpectedException.none();

  @Test
  public void countsAssets() throws IOException {
    File icon = tempFolder.newFile("icon.png");
    File assets = tempFolder.newFolder("assets");
    createAssets(assets, 3);

    DigitalAssetManager dam = new DigitalAssetManager(icon, assets);
    assertEquals(3, dam.getAssetCount());
  }

  private void createAssets(File assets, int numberOfAssets) throws IOException {
    for (int index = 0; index < numberOfAssets; index++) {
      File asset = new File(assets, String.format("asset-%d.mpg", index));
      Assert.assertTrue("Asset couldn't be created.", asset.createNewFile());
    }
  }

  @Test
  public void throwsIllegalArgumentExceptionIfIconIsNull() {
    exception.expect(IllegalArgumentException.class);
    exception.expectMessage("Icon is null, not a file, or doesn't exist.");
    new DigitalAssetManager(null, null);
  }
}

```

# Base Rules Provided in The Distribution *分发中提供的基本规则*

## TemporaryFolder Rule *临时文件夹规则*

* The TemporaryFolder Rule allows creation of files and folders that are deleted when the test method finishes (whether it passes or fails). 
    By default no exception is thrown if resources cannot be deleted:


* TemporaryFolder Rule 允许创建在测试方法完成（无论通过还是失败）时删除的文件和文件夹。
    默认情况下，如果无法删除资源，则不会抛出异常：

---

```java
public static class HasTempFolder {
  @Rule
  public final TemporaryFolder folder = new TemporaryFolder();

  @Test
  public void testUsingTempFolder() throws IOException {
    File createdFile = folder.newFile("myfile.txt");
    File createdFolder = folder.newFolder("subfolder");
    // ...
  }
} 

```

* `TemporaryFolder#newFolder(String... folderNames)` creates recursively deep temporary folders

* `TemporaryFolder#newFolder(String... folderNames)` 创建递归深度临时文件夹

* `TemporaryFolder#newFile()` creates a randomly named new file, and `#newFolder()` creates a randomly named new folder

* `TemporaryFolder#newFile()` 创建一个随机命名的新文件，`newFolder()` 创建一个随机命名的新文件夹

* Starting with version 4.13 `TemporaryFolder` optionally allows strict verification of deleted resources which fails the test with `AssertionError` if resources cannot be deleted. 
    This feature can only be opted for by using the `#builder()` method. 
    By default strict verification is disabled which maintains backward compatibility.


* 从 4.13 版开始，`TemporaryFolder` 可选地允许对已删除资源进行严格验证，如果资源无法删除，则该资源通过 `AssertionError` 测试失败。
    此功能只能通过使用 `builder()` 方法来选择。
    默认情况下禁用严格验证以保持向后兼容性。

```
@Rule 
public TemporaryFolder folder = TemporaryFolder.builder().assureDeletion().build();
```

## ExternalResource Rules *外部资源规则*

* ExternalResource is a base class for Rules (like TemporaryFolder) that set up an external resource before a test (a file, socket, server, database connection, etc.), and guarantee to tear it down afterward:


* ExternalResource 是规则（如 TemporaryFolder）的基类，它在测试之前设置外部资源（文件、套接字、服务器、数据库连接等），并保证之后将其拆除：

```java
public static class UsesExternalResource {
  Server myServer = new Server();
  
  @Rule
  public final ExternalResource resource = new ExternalResource() {
    @Override
    protected void before() throws Throwable {
      myServer.connect();
    };
    
    @Override
    protected void after() {
      myServer.disconnect();
    };
  };
  
  @Test
  public void testFoo() {
    new Client().run(myServer);
  }
}

```

## ErrorCollector Rule *ErrorCollector 规则*

* The ErrorCollector Rule allows execution of a test to continue after the first problem is found (for example, to collect all the incorrect rows in a table, and report them all at once):


* ErrorCollector 规则允许在发现第一个问题后继续执行测试（例如，收集表中所有不正确的行，并一次性报告它们）：

---

```java
public static class UsesErrorCollectorTwice {
  @Rule
  public final ErrorCollector collector = new ErrorCollector();
  
  @Test
  public void example() {
    collector.addError(new Throwable("first thing went wrong"));
    collector.addError(new Throwable("second thing went wrong"));
  }
}

```

## Verifier Rule *验证规则*

* Verifier is a base class for Rules like ErrorCollector, which can turn otherwise passing test methods into failing tests if a verification check is failed.


* Verifier 是 ErrorCollector 等规则的基类，如果验证检查失败，它可以将其他通过的测试方法转换为失败的测试。

---

```java

public static class UsesVerifier {
  
  private static String sequence;
  
  @Rule
  public final Verifier collector = new Verifier() {
    @Override
    protected void verify() {
      sequence += "verify ";
    }
  };

  @Test
  public void example() {
    sequence += "test ";
  }
  
  @Test
  public void verifierRunsAfterTest() {
    sequence = "";
    assertThat(testResult(UsesVerifier.class), isSuccessful());
    assertEquals("test verify ", sequence);
  }

}

```

## `TestWatchman` / `TestWatcher` Rules *`TestWatchman` / `TestWatcher` 规则*

* `TestWatcher` replaces `TestWatchman` from version 4.9. 
    It implements TestRule, not MethodRule -- [http://junit.org/javadoc/latest/org/junit/rules/TestWatcher.html](http://junit.org/javadoc/latest/org/junit/rules/TestWatcher.html)

* `TestWatchman` was introduced in JUnit 4.7, it uses a `MethodRule`, which is now deprecated. -- [http://junit.org/javadoc/latest/org/junit/rules/TestWatchman.html](http://junit.org/javadoc/latest/org/junit/rules/TestWatchman.html)

* `TestWatcher` (and the deprecated TestWatchman) are base classes for Rules that take note of the testing action, without modifying it. 
    For example, this class will keep a log of each passing and failing test:


* `TestWatcher` 取代了 4.9 版中的 `TestWatchman`。
    它实现了 TestRule，而不是 MethodRule -- [http://junit.org/javadoc/latest/org/junit/rules/TestWatcher.html](http://junit.org/javadoc/latest/org/junit/rules/TestWatcher.html)

* `TestWatchman` 是在 JUnit 4.7 中引入的，它使用 `MethodRule`，现在已弃用。 -- [http://junit.org/javadoc/latest/org/junit/rules/TestWatchman.html](http://junit.org/javadoc/latest/org/junit/rules/TestWatchman.html)

* `TestWatcher`（以及已弃用的 TestWatchman）是规则的基类，它们记录测试操作，而不对其进行修改。
    例如，这个类将记录每个通过和失败的测试：

---

```java
import static org.junit.Assert.fail; 
import org.junit.AssumptionViolatedException; 
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestRule;
import org.junit.rules.TestWatcher;
import org.junit.runner.Description;
import org.junit.runners.model.Statement;

public class WatchmanTest {
  private static String watchedLog;

  @Rule
  public final TestRule watchman = new TestWatcher() {
    @Override
    public Statement apply(Statement base, Description description) {
      return super.apply(base, description);
    }

    @Override
    protected void succeeded(Description description) {
      watchedLog += description.getDisplayName() + " " + "success!\n";
    }

    @Override
    protected void failed(Throwable e, Description description) {
      watchedLog += description.getDisplayName() + " " + e.getClass().getSimpleName() + "\n";
    }

    @Override
    protected void skipped(AssumptionViolatedException e, Description description) {
      watchedLog += description.getDisplayName() + " " + e.getClass().getSimpleName() + "\n";
    }

    @Override
    protected void starting(Description description) {
      super.starting(description);
    }

    @Override
    protected void finished(Description description) {
      super.finished(description);
    }
  };

  @Test
  public void fails() {
    fail();
  }

  @Test
  public void succeeds() {
  }
}

```

## `TestName` Rule *`TestName` 规则*

* The `TestName` Rule makes the current test name available inside test methods:


* `TestName` 规则使当前测试名称在测试方法中可用：

---

```java
public class NameRuleTest {
  @Rule
  public final TestName name = new TestName();
  
  @Test
  public void testA() {
    assertEquals("testA", name.getMethodName());
  }
  
  @Test
  public void testB() {
    assertEquals("testB", name.getMethodName());
  }
}

```

## `Timeout` Rule *`Timeout` 规则*

* The `Timeout` Rule applies the same timeout to all test methods in a class:


* `Timeout` 规则对类中的所有测试方法应用相同的超时：

---

```java
public static class HasGlobalTimeout {
  public static String log;
  
  @Rule
  public final TestRule globalTimeout = Timeout.millis(20);
  
  @Test
  public void testInfiniteLoop1() {
    log += "ran1";
    for(;;) {}
  }
  
  @Test
  public void testInfiniteLoop2() {
    log += "ran2";
    for(;;) {}
  }
}

```

## `ExpectedException` Rules *`ExpectedException` 规则*

* The `ExpectedException` Rule allows in-test specification of expected exception types and messages:


* `ExpectedException` 规则允许预期异常类型和消息的测试规范：

---

```java
public static class HasExpectedException {
  @Rule
  public final ExpectedException thrown = ExpectedException.none();

  @Test
  public void throwsNothing() {

  }

  @Test
  public void throwsNullPointerException() {
    thrown.expect(NullPointerException.class);
    throw new NullPointerException();
  }

  @Test
  public void throwsNullPointerExceptionWithMessage() {
    thrown.expect(NullPointerException.class);
    thrown.expectMessage("happened?");
    thrown.expectMessage(startsWith("What"));
    throw new NullPointerException("What happened?");
  }
}

```

## `ClassRule` *`ClassRule`*

The `ClassRule` annotation extends the idea of method-level Rules, adding static fields that can affect the operation of a whole class. 
Any subclass of `ParentRunner`, including the standard `BlockJUnit4ClassRunner` and `Suite` classes, will support `ClassRule`s.


`ClassRule` 注解扩展了方法级规则的思想，添加了可以影响整个类操作的静态字段。
`ParentRunner` 的任何子类，包括标准的 `BlockJUnit4ClassRunner` 和 `Suite` 类，都将支持 `ClassRule` 。

---

For example, here is a test suite that connects to a server once before all the test classes run, and disconnects after they are finished:


例如，这里有一个测试套件，它在所有测试类运行之前连接到服务器一次，并在它们完成后断开连接：

---

```java
@RunWith(Suite.class)
@SuiteClasses({A.class, B.class, C.class})
public class UsesExternalResource {
  public static final Server myServer = new Server();

  @ClassRule
  public static final ExternalResource resource = new ExternalResource() {
    @Override
    protected void before() throws Throwable {
      myServer.connect();
    };

    @Override
    protected void after() {
      myServer.disconnect();
    };
  };
}

```

## `RuleChain` *`RuleChain`*

The `RuleChain` rule allows ordering of TestRules:


`RuleChain` 规则允许对 TestRule 进行排序

---

```java
public static class UseRuleChain {
    @Rule
    public final TestRule chain = RuleChain
                           .outerRule(new LoggingRule("outer rule"))
                           .around(new LoggingRule("middle rule"))
                           .around(new LoggingRule("inner rule"));

    @Test
    public void example() {
        assertTrue(true);
    }
}

```

writes the log


写日志

---

```text
starting outer rule
starting middle rule
starting inner rule
finished inner rule
finished middle rule
finished outer rule

```

# Custom Rules *自定义规则*

Most custom rules can be implemented as an extension of the `ExternalResource` rule. 
However, if you need more information about the test class or method in question, you'll need to implement the `TestRule` interface.


大多数自定义规则可以作为 `ExternalResource` 规则的扩展来实现。
但是，如果您需要有关所讨论的测试类或方法的更多信息，则需要实现 `TestRule` 接口。

---

```java
import org.junit.rules.TestRule;
import org.junit.runner.Description;
import org.junit.runners.model.Statement;

public class IdentityRule implements TestRule {
  @Override
  public Statement apply(final Statement base, final Description description) {
    return base;
  }
}

```

Of course, the power from implementing `TestRule` comes from using a combination of custom constructors, adding methods to the class for use in tests, and wrapping the provided `Statement` in a new `Statement`. 
For instance, consider the following test rule that provides a named logger for every test:


当然，实现 `TestRule` 的强大功能来自于使用自定义构造函数的组合、向类添加方法以用于测试，以及将提供的 `Statement` 包装在新的 `Statement` 中。
例如，考虑以下为每个测试提供命名记录器的测试规则：

---

```java
package org.example.junit;

import java.util.logging.Logger;

import org.junit.rules.TestRule;
import org.junit.runner.Description;
import org.junit.runners.model.Statement;

public class TestLogger implements TestRule {
  private Logger logger;

  public Logger getLogger() {
    return this.logger;
  }

  @Override
  public Statement apply(final Statement base, final Description description) {
    return new Statement() {
      @Override
      public void evaluate() throws Throwable {
        logger = Logger.getLogger(description.getTestClass().getName() + '.' + description.getDisplayName());
        base.evaluate();
      }
    };
  }
}

```

Then that rule could be applied like so:


然后可以像这样应用该规则：

---

```java
import java.util.logging.Logger;

import org.example.junit.TestLogger;
import org.junit.Rule;
import org.junit.Test;

public class MyLoggerTest {

  @Rule
  public final TestLogger logger = new TestLogger();

  @Test
  public void checkOutMyLogger() {
    final Logger log = logger.getLogger();
    log.warn("Your test is showing!");
  }

}

```
