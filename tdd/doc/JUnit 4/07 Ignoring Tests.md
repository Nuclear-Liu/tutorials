# Ignoring tests *忽略测试*

> Daan van Berkel edited this page on Jul 30, 2015 · 4 revisions 

> Daan van Berkel 于 2015 年 7 月 30 日编辑了此页面 · 4 次修订

# Ignoring a Test *忽略一个测试*

If for some reason, you don't want a test to fail, you just want it ignored, you temporarily disable a test.


如果出于某种原因，您不希望测试失败，而只想忽略它，则暂时禁用测试。

---

To ignore a test in JUnit you can either comment a method, or delete the `@Test` annotation; but the test runner will not report such a test. 
Alternatively, you can add the `@Ignore` annotation in front or after `@Test`. 
Test runners will report the number of ignored tests, along with the number of tests that ran and the number of tests that failed.


要忽略 JUnit 中的测试，您可以注释方法，或删除 `@Test` 注释；但是测试运行器不会报告这样的测试。
或者，您可以在 `@Test` 之前或之后添加 `@Ignore` 注释。
测试运行器将报告被忽略测试的数量，以及运行的测试数量和失败的测试数量。

---

Note that `@Ignore` takes an optional parameter (a String) if you want to record a reason why a test is being ignored.


请注意，如果您想记录测试被忽略的原因， `@Ignore` 需要一个可选参数（字符串）。

---

```
@Ignore("Test is ignored as a demonstration")
@Test
public void testSame() {
    assertThat(1, is(1));
}

```
