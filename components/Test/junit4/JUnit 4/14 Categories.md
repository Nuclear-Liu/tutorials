# Categories *类别*

> Jeremy W. Sherman edited this page on Aug 17, 2018 · 12 revisions 

> Jeremy W. Sherman 于 2018 年 8 月 17 日编辑了此页面 · 12 次修订

From a given set of test classes, the `Categories` runner runs only the classes and methods that are annotated with either the category given with the `@IncludeCategory` annotation, or a subtype of that category. 
Either classes or interfaces can be used as categories. 
Subtyping works, so if you say `@IncludeCategory(SuperClass.class)` , a test marked `@Category({SubClass.class})` will be run.


从一组给定的测试类中， `Categories` 运行程序仅运行使用 `@IncludeCategory` 注解给出的类别或该类别的子类型进行批注的类和方法。
类或接口都可以用作类别。
子类型有效，所以如果你说 `@IncludeCategory(SuperClass.class)` ，一个标记为 `@Category({SubClass.class})` 的测试将会运行。

---

You can also exclude categories by using the `@ExcludeCategory` annotation


您还可以使用 `@ExcludeCategory` 注释排除类别

---

Example:


示例：

---

```java
public interface FastTests { /* category marker */ }
public interface SlowTests { /* category marker */ }

public class A {
  @Test
  public void a() {
    fail();
  }

  @Category(SlowTests.class)
  @Test
  public void b() {
  }
}

@Category({SlowTests.class, FastTests.class})
public class B {
  @Test
  public void c() {

  }
}

@RunWith(Categories.class)
@IncludeCategory(SlowTests.class)
@SuiteClasses( { A.class, B.class }) // Note that Categories is a kind of Suite
public class SlowTestSuite {
  // Will run A.b and B.c, but not A.a
}

@RunWith(Categories.class)
@IncludeCategory(SlowTests.class)
@ExcludeCategory(FastTests.class)
@SuiteClasses( { A.class, B.class }) // Note that Categories is a kind of Suite
public class SlowTestSuite {
  // Will run A.b, but not A.a or B.c
}

```

## Using categories with Maven *在 Maven 中使用类别*

You can use categories with either [maven-surefire-plugin](http://maven.apache.org/surefire/maven-surefire-plugin/examples/junit.html) (for unit tests) or [maven-failsafe-plugin](http://maven.apache.org/surefire/maven-failsafe-plugin/examples/junit.html) (for integration tests). 
Using either plugin, you can configure a list of categories to include or exclude. 
Without using either option, all tests will be executed by default.


您可以将类别与 [maven-surefire-plugin](http://maven.apache.org/surefire/maven-surefire-plugin/examples/junit.html) （用于单元测试）或 [maven-failsafe-plugin](http://maven.apache.org/surefire/maven-failsafe-plugin/examples/junit.html) （用于集成测试）一起使用。
使用任一插件，您都可以配置要包含或排除的类别列表。
不使用任何一个选项，默认情况下将执行所有测试。

---

```xml
<build>
  <plugins>
    <plugin>
      <artifactId>maven-surefire-plugin</artifactId>
      <configuration>
        <groups>com.example.FastTests,com.example.RegressionTests</groups>
      </configuration>
    </plugin>
  </plugins>
</build>

```

Similarly, to exclude a certain list of categories, you would use the `<excludedGroups/>` configuration element.


同样，要排除某个类别列表，您可以使用 `<excludedGroups>` 配置元素。

---

## Using categories with Gradle *在 Gradle 中使用类别*

Gradle's test task allows the specification of the JUnit categories you want to include and exclude.


Gradle 的测试任务允许指定要包含和排除的 JUnit 类别。

---

```groovy
test {
    useJUnit {
        includeCategories 'org.gradle.junit.CategoryA'
        excludeCategories 'org.gradle.junit.CategoryB'
    }
}

```

## Using categories with SBT *在 SBT 中使用类别*

SBT's [junit-interface]() allows the specification of JUnit categories via `--include-categories=<CLASSES>` and `--exclude-categories=<CLASSES>`.


SBT 的 [junit-interface]() 允许通过 `--include-categories=<CLASSES>` 和 `--exclude-categories=<CLASSES>` 指定 JUnit 类别。

---

## Typical usages for categories *类别的典型用法*

Categories are used to add metadata on the tests.


类别用于在测试中添加元数据。

---

The frequently encountered categories usages are about:


经常遇到的类别用法是关于：

---

* The type of automated tests: UnitTests, IntegrationTests, SmokeTests, RegressionTests, PerformanceTests ...
* How quick the tests execute: SlowTests, QuickTests
* In which part of the ci build the tests should be executed: NightlyBuildTests
* The state of the test: UnstableTests, InProgressTests


* 自动化测试的类型：UnitTests、IntegrationTests、SmokeTests、RegressionTests、PerformanceTests...
* 测试执行的速度：SlowTests、QuickTests
* 应该在 ci 构建的哪个部分执行测试：NightlyBuildTests
* 测试状态：UnstableTests、InProgressTests

---

This is also used to add project specific metadata like which feature of a project is covered by the test.


这也用于添加项目特定的元数据，例如测试涵盖项目的哪个功能。

---

[See usages of Junit Categories on github hosted projects](https://github.com/search?o=asc&p=11&q=%22import+org.junit.experimental.categories.Category%22+%22%40Category%22&ref=searchresults&s=indexed&type=Code&utf8=%E2%9C%93) 


[查看 github 托管项目上 Junit Categories 的用法](https://github.com/search?o=asc&p=11&q=%22import+org.junit.experimental.categories.Category%22+%22%40Category%22&ref=searchresults&s=indexed&type=Code&utf8=%E2%9C%93) 

---
