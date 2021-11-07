# Aggregating tests in suites *在套件中聚合测试*

> jason edited this page on Oct 27, 2017 · 7 revisions 

> jason 于 2017 年 10 月 27 日编辑了此页面 · 7 次修订

Using `Suite` as a runner allows you to manually build a suite containing tests from many classes. 
It is the JUnit 4 equivalent of the JUnit 3.8.x `static Test suite()` method. 
To use it, annotate a class with `@RunWith(Suite.class)` and `@SuiteClasses(TestClass1.class, ...)`. 
When you run this class, it will run all the tests in all the suite classes.


使用 `Suite` 作为运行器允许您手动构建包含来自许多类的测试的套件。
它是 JUnit 4 等效于 JUnit 3.8.x 的 `static Test suite()` 方法。
要使用它，使用 `@RunWith(Suite.class)` 和 `@SuiteClasses(TestClass1.class, ...)` 注解一个类。
当您运行这个类时，它将运行所有套件类中的所有测试。

---

## Example *示例*

The class below is a placeholder for the suite annotations, no other implementation is required. 
Note the `@RunWith` annotation, which specifies that the JUnit 4 test runner to use is `org.junit.runners.Suite` for running this particular test class. 
This works in conjunction with the `@Suite.SuiteClasses` annotation, which tells the Suite runner which test classes to include in this suite and in which order.


下面的类是套件注解的占位符，不需要其他实现。
请注意 `@RunWith` 注解，它指定要使用的 JUnit 4 测试运行器是 `org.junit.runners.Suite` 用于运行此特定测试类的。
这与 `@Suite.SuiteClasses` 注解一起工作，它告诉套件运行器在这个套件中包含哪些测试类以及以什么顺序包含。

---

```java
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
  TestFeatureLogin.class,
  TestFeatureLogout.class,
  TestFeatureNavigate.class,
  TestFeatureUpdate.class
})

public class FeatureTestSuite {
  // the class remains empty,
  // used only as a holder for the above annotations
}

```
