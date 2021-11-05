# Test fixtures *测试夹具*

> Pat Hawks edited this page on Jun 18, 2017 · 9 revisions 

> Pat Hawks 于 2017 年 6 月 18 日编辑了此页面 · 9 次修订

A test fixture is a fixed state of a set of objects used as a baseline for running tests. 
The purpose of a test fixture is to ensure that there is a well known and fixed environment in which tests are run so that results are repeatable. 
Examples of fixtures:


测试装置是一组对象的固定状态，用作运行测试的基线。
测试夹具的目的是确保有一个众所周知的固定环境来运行测试，以便结果是可重复的。
夹具示例：

---

* Preparation of input data and setup/creation of fake or mock objects
* Loading a database with a specific, known set of data
* Copying a specific known set of files creating a test fixture will create a set of objects initialized to certain states.


* 准备输入数据和设置创建假或模拟对象
* 使用一组特定的已知数据加载数据库
* 复制一组特定的已知文件以创建测试装置将创建一组初始化为某些状态的对象。

---

JUnit provides annotations so that test classes can have fixture run before or after every test, or one time fixtures that run before and after only once for all test methods in a class.


JUnit 提供了注解，以便测试类可以在每次测试之前或之后运行固定装置，或者对类中的所有测试方法只在之前和之后运行一次固定装置。

---

There are four fixture annotations: two for class-level fixtures and two for method-level ones. 
At the class level, you have `@BeforeClass` and `@AfterClass`, and at the method (or test) level, you have `@Before` and `@After`.


有四个夹具注解：两个用于类级夹具，两个用于方法级夹具。
在类级别，你有`@BeforeClass` 和`@AfterClass`，在方法（或测试）级别，你有`@Before` 和`@After`。

---

A deeper explanation of fixtures, and how they could also be implemented using `Rules` is discussed [here](https://garygregory.wordpress.com/2011/09/25/understaning-junit-method-order-execution/).


[此处](https://garygregory.wordpress.com/2011/09/25/understaning-junit-method-order-execution/) 讨论了对装置的更深入解释，以及如何使用“规则”来实现它们。

---

An example of usage:


用法示例：

---

```java
package test;

import java.io.Closeable;
import java.io.IOException;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class TestFixturesExample {
  static class ExpensiveManagedResource implements Closeable {
    @Override
    public void close() throws IOException {}
  }

  static class ManagedResource implements Closeable {
    @Override
    public void close() throws IOException {}
  }

  @BeforeClass
  public static void setUpClass() {
    System.out.println("@BeforeClass setUpClass");
    myExpensiveManagedResource = new ExpensiveManagedResource();
  }

  @AfterClass
  public static void tearDownClass() throws IOException {
    System.out.println("@AfterClass tearDownClass");
    myExpensiveManagedResource.close();
    myExpensiveManagedResource = null;
  }

  private ManagedResource myManagedResource;
  private static ExpensiveManagedResource myExpensiveManagedResource;

  private void println(String string) {
    System.out.println(string);
  }

  @Before
  public void setUp() {
    this.println("@Before setUp");
    this.myManagedResource = new ManagedResource();
  }

  @After
  public void tearDown() throws IOException {
    this.println("@After tearDown");
    this.myManagedResource.close();
    this.myManagedResource = null;
  }

  @Test
  public void test1() {
    this.println("@Test test1()");
  }

  @Test
  public void test2() {
    this.println("@Test test2()");
  }
}

```

Will Output something like the following:


将输出如下内容：

---

```text
@BeforeClass setUpClass
@Before setUp
@Test test2()
@After tearDown
@Before setUp
@Test test1()
@After tearDown
@AfterClass tearDownClass

```
