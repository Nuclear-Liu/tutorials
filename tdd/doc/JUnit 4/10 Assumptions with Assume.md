# Assumptions with assume *Assumptions 假设*

> Daan van Berkel edited this page on Jul 30, 2015 · 4 revisions 

> Daan van Berkel 于 2015 年 7 月 30 日编辑了此页面 · 4 次修订

# Assumptions *假设*

Ideally, the developer writing a test has control of all of the forces that might cause a test to fail. 
If this isn't immediately possible, making dependencies explicit can often improve a design. 
For example, if a test fails when run in a different locale than the developer intended, it can be fixed by explicitly passing a locale to the domain code.


理想情况下，编写测试的开发人员可以控制可能导致测试失败的所有因素。
如果这不是立即可能的，那么使依赖关系显式通常可以改进设计。
例如，如果测试在与开发人员预期不同的语言环境中运行时失败，则可以通过将语言环境显式传递给域代码来修复。

---

However, sometimes this is not desirable or possible. 
It's good to be able to run a test against the code as it is currently written, implicit assumptions and all, or to write a test that exposes a known bug. 
For these situations, JUnit now includes the ability to express "assumptions":


然而，有时这是不可取的或不可能的。
能够针对当前编写的代码、隐式假设等运行测试，或者编写暴露已知错误的测试，这很好。
对于这些情况，JUnit 现在包括表达 "assumptions" 的能力：

---

```
    import static org.junit.Assume.*
    @Test public void filenameIncludesUsername() {
        assumeThat(File.separatorChar, is('/'));
        assertThat(new User("optimus").configFileName(), is("configfiles/optimus.cfg"));
    }

    @Test public void correctBehaviorWhenFilenameIsNull() {
       assumeTrue(bugFixed("13356"));  // bugFixed is not included in JUnit
       assertThat(parse(null), is(new NullDocument()));
    }

```

The default JUnit runner treats tests with failing assumptions as ignored. 
Custom runners may behave differently.


默认的 JUnit 运行器将假设失败的测试视为忽略。
自定义跑步者的行为可能有所不同。

---

We have included `assumeTrue` for convenience, but thanks to the inclusion of Hamcrest, we do not need to create `assumeEquals`, `assumeSame`, and other analogues to the `assert*` methods. 
All of those functionalities are subsumed in `assumeThat`, with the appropriate matcher.


为方便起见，我们包含了 `assumeTrue`，但由于包含了 Hamcrest，我们不需要创建 `assumeEquals` 、`assumeSame` 和其他类似于 `assert` 方法的方法。
所有这些功能都包含在 `assumeThat` 中，并带有适当的匹配器。

---

A failing assumption in a `@Before` or `@BeforeClass` method will have the same effect as a failing assumption in each `@Test` method of the class.


`@Before` 或 `@BeforeClass` 方法中的失败假设将与类的每个 `@Test` 方法中的失败假设具有相同的效果。

---
