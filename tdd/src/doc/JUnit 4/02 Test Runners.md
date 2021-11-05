# Test runners *测试运行器*

> Marc Philipp edited this page on 25 Jun 2020 · 37 revisions

> Marc Philipp 于 2020 年 6 月 25 日编辑了此页面 · 37 次修订

### IDE support - graphical runners *IDE 支持图形的运行器*

NetBeans, Eclipse and IntelliJ IDEA have native graphical test runners built in.


NetBeans, Eclipse 和 IntelliJ IDEA 具有内置的原生图形测试运行器。

---

### Console based Test runner *基于控制台的测试运行器*

JUnit provides tools to define the suite to be run and to display its results. 
To run tests and see the results on the console, run this from a Java program:


JUnit 提供工具来定义要运行的套件并显示其结果。
要运行测试并在控制台上查看结果，请从 Java 程序运行：

---

`org.junit.runner.JUnitCore.runClasses(TestClass1.class, ...);`

or this from the command line, with both your test class and junit on the classpath:


或者从命令行，在类路径上同时使用您的测试类和 junit ：

---

`java org.junit.runner.JUnitCore TestClass1 [...other test classes...]`

This usage is documented further here: [http://junit.org/javadoc/latest/org/junit/runner/JUnitCore.html](http://junit.org/javadoc/latest/org/junit/runner/JUnitCore.html)


此处进一步记录了此用法： [http://junit.org/javadoc/latest/org/junit/runner/JUnitCore.html](http://junit.org/javadoc/latest/org/junit/runner/JUnitCore.html)

---

### Using older runners with Adapter *使用带有适配器的旧运行器*

`JUnit4TestAdapter` enables running JUnit-4-style tests using a JUnit-3-style test runner. 
To use it, add the following to a test class:


`JUnit4TestAdapter` 允许使用 JUnit-3-style 的测试运行器运行 JUnit-4-style 的测试。
要使用它，请将以下内容添加到测试类中：

---

```
      public static Test suite() {
            return new JUnit4TestAdapter('YourJUnit4TestClass'.class);
      }
```

Caveat: See [#1189](https://github.com/junit-team/junit4/issues/1189) for issues with including a JUnit-4-style suite that contains a JUnit-3-style suite.


警告：请参阅 [#1189](https://github.com/junit-team/junit4/issues/1189) 以了解包含 JUnit-4-style 套件的问题，该套件包含 JUnit-3-style 套件。

---

## `@RunWith` annotation *`@RunWith` 注解*

When a class is annotated with `@RunWith` or extends a class annotated with `@RunWith`, JUnit will invoke the class it references to run the tests in that class instead of the runner built into JUnit.


当一个类用 `@RunWith` 注解或继承一个用 `@RunWith` 注解的类时， JUnit 将调用它引用的类来运行该类中的测试，而不是 JUnit 中内置的运行器。

---

* JavaDoc for @RunWith [http://junit.org/javadoc/latest/org/junit/runner/RunWith.html](http://junit.org/javadoc/latest/org/junit/runner/RunWith.html)
* The default runner is `BlockJUnit4ClassRunner` which supersedes the older `JUnit4ClassRunner`
* Annotating a class with `@RunWith(JUnit4.class)` will always invoke the default JUnit 4 runner in the current version of JUnit, this class aliases the current default JUnit 4 class runner.

* @RunWith 的 JavaDoc [http://junit.org/javadoc/latest/org/junit/runner/RunWith.html](http://junit.org/javadoc/latest/org/junit/runner/RunWith.html)
* 默认运行器是 `BlockJUnit4ClassRunner` ，它取代了旧的 `JUnit4ClassRunner`
* 使用 `@RunWith(JUnit4.class)` 注解一个类将始终调用当前 JUnit 版本中的默认 JUnit 4 运行器，该类别名为当前默认的 JUnit 4 类运行器。

---

## Specialized Runners *专业的运行器*

### Suite *套件测试器*

* `Suite` is a standard runner that allows you to manually build a suite containing tests from many classes.
* More information at [Aggregating tests in Suites](https://github.com/junit-team/junit4/wiki/Aggregating-tests-in-suites) page.
* JavaDoc: [http://junit.org/javadoc/latest/org/junit/runners/Suite.html](http://junit.org/javadoc/latest/org/junit/runners/Parameterized.html)


* `Suite` 是一个标准的运行器，它允许您手动构建一个包含来自许多类的测试的套件。
* 更多信息见 [Aggregating tests in Suites](./03%20Aggregating%20tests%20in%20Suites.md) 页面。
* JavaDoc: [http://junit.org/javadoc/latest/org/junit/runners/Suite.html](http://junit.org/javadoc/latest/org/junit/runners/Parameterized.html)

---

### Parameterized *参数化运行器*

* `Parameterized` is a standard runner that implements parameterized tests. 
    When running a parameterized test class, instances are created for the cross-product of the test methods and the test data elements.
* More information at [Parameterized Tests](https://github.com/junit-team/junit4/wiki/Parameterized-tests) page.
* JavaDoc: [http://junit.org/javadoc/latest/org/junit/runners/Parameterized.html](http://junit.org/javadoc/latest/org/junit/runners/Parameterized.html)


* `Parameterized` 是一个实现参数化测试的标准运行器。
    运行参数化测试类时，会为测试方法和测试数据元素的交叉产品创建实例
* 更多页面信息 [Parameterized Tests](./09%20Parameterized%20Tests.md)
* JavaDoc: [http://junit.org/javadoc/latest/org/junit/runners/Parameterized.html](http://junit.org/javadoc/latest/org/junit/runners/Parameterized.html)

---

### Categories *类别运行器*

You can specify groups of tests to be excluded or included by using the `Categories` runner. 
Once you have annotated certain methods with `@Category(MyCategory.class)`, you can use the `--filter` option to restrict which tests will be run:


您可以使用 `Categories` 运行器指定要排除或包含的测试组。
一旦你用 `@Category(MyCategory.class)` 注解了某些方法，你可以使用 `--filter` 选项来限制将运行哪些测试：

---

`java org.junit.runner.JUnitCore --filter=org.junit.experimental.categories.IncludeCategories=MyCat1,MyCat2 --filter=org.junit.experimental.categories.ExcludeCategories=MyCat3,MyCat4`

You may filter tests according to any instance of `FilterFactory`. 
The `--filter` option takes the general form:


您可以根据 `FilterFactory` 的任何实例过滤测试。
`--filter` 选项采用一般形式：

---

`java [Runner] --filter=[FilterFactory]=[Categories,]`

* `Categories` is a standard runner enabling subsets of tests tagged with certain categories to execute/be excluded from a given test run.
* More information at [Categories](https://github.com/junit-team/junit4/wiki/Categories) page.


* `Categories` 是一个标准的运行器，允许使用特定类别标记的测试子集在给定的测试运行中执行/排除。
* 更多信息请访问 [Categories](./14%20Categories.md) 页面。

---

## Experimental Runners *实验性的运行器*

### Enclosed *封闭式*

* `Enclosed` - If you put tests in inner classes, Ant, for example, won't find them. 
    By running the outer class with Enclosed, the tests in the inner classes will be run. 
    You might put tests in inner classes to group them for convenience or to share constants.
* JavaDoc: [http://junit.org/javadoc/latest/org/junit/experimental/runners/Enclosed.html](http://junit.org/javadoc/latest/org/junit/experimental/runners/Enclosed.html)
* Working Example of use on the ['Enclosed'-test-runner-example](https://github.com/junit-team/junit4/wiki/%27Enclosed%27-test-runner-example) page


* `Enclosed` - 如果将测试放在内部类中，Ant，示例，将找不到它们。
    通过使用 Enclosed 运行外部类，将运行内部类中的测试。
    为了方便或共享常量，您可以将测试放在内部类中以将它们分组。
* JavaDoc: [http://junit.org/javadoc/latest/org/junit/experimental/runners/Enclosed.html](http://junit.org/javadoc/latest/org/junit/experimental/runners/Enclosed.html)
* 在 ['Enclosed'-test-runner-example](./A01%20%60Enclosed%60-test-runner-example.md) 页面上使用的工作示例

---

## Third Party Runners *第三方运行器*

Some popular third party implementations of runners for use with `@RunWith` include:


一些与 `@RunWith` 一起使用的流行的第三方运行器实现包括：

---

* [SpringJUnit4ClassRunner](http://docs.spring.io/spring/docs/current/javadoc-api/org/springframework/test/context/junit4/SpringJUnit4ClassRunner.html)
* [MockitoJUnitRunner](http://site.mockito.org/mockito/docs/current/org/mockito/runners/MockitoJUnitRunner.html)
* [HierarchicalContextRunner](https://github.com/bechte/junit-hierarchicalcontextrunner/wiki)
* [Avh4's Nested](https://github.com/avh4/junit-nested)
* [NitorCreation's NestedRunner](https://github.com/NitorCreations/CoreComponents/tree/master/junit-runners)
* [Others...](https://github.com/junit-team/junit4/wiki/Custom-runners)

