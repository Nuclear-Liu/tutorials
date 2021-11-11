# Matchers and assertThat

> jason edited this page on Oct 27, 2017 · 1 revision 

> jason 于 2017 年 10 月 27 日编辑了此页面 · 1 次修订

# `assertThat` *`assertThat`*

Joe Walnes built a new assertion mechanism on top of what was then JMock 1. 
The method name was assertThat, and the syntax looked like this:


Joe Walnes 在当时的 JMock 1 之上构建了一个新的断言机制。
方法名称是 assertThat ，语法如下所示：

---

```
assertThat(x, is(3));
assertThat(x, is(not(4)));
assertThat(responseString, either(containsString("color")).or(containsString("colour")));
assertThat(myList, hasItem("3"));

```

More generally:


更普遍：

---

`assertThat([value], [matcher statement]);`

Advantages of this assertion syntax include:


这种断言语法的优点包括：

---

More readable and typeable: 
this syntax allows you to think in terms of subject, verb, object (assert "x is 3") rather than `assertEquals`, which uses verb, object, subject (assert "equals 3 x")


更具可读性和可键入性：
这种语法允许你根据主语、动词、宾语（assert "x is 3"）而不是 `assertEquals` 来思考，后者使用动词、宾语、主语（assert "equals 3 x"）

---

Combinations: any matcher statement s can be negated (`not(s)`), combined (`either(s).or(t)`), mapped to a collection (`each(s)`), or used in custom combinations (`afterFiveSeconds(s)`)


组合：任何匹配器语句都可以否定（ `not(s)` ）、组合（ `either(s).or(t)` ）、映射到集合（ `each(s)` ），或用于自定义组合（ `afterFiveSeconds（s）` ）

---

Readable failure messages. 
Compare:


可读的失败消息。
相比：

---

```
assertTrue(responseString.contains("color") || responseString.contains("colour"));
// ==> failure message: 
// java.lang.AssertionError:


assertThat(responseString, anyOf(containsString("color"), containsString("colour")));
// ==> failure message:
// java.lang.AssertionError: 
// Expected: (a string containing "color" or a string containing "colour")
//      got: "Please choose a font"

```

Custom Matchers. 
By implementing the Matcher interface yourself, you can get all of the above benefits for your own custom assertions.


自定义匹配器。
通过自己实现 Matcher 接口，您可以为自己的自定义断言获得上述所有好处。

---

For a more thorough description of these points, see Joe Walnes's original post.


有关这些要点的更详尽描述，请参阅 Joe Walnes's 的原始帖子。

---

We have decided to include this API directly in JUnit. 
It's an extensible and readable syntax, and it enables new features, like assumptions and theories.


我们决定将这个 API 直接包含在 JUnit 中。
它是一种可扩展且可读的语法，它支持新功能，如假设和理论。

---

Some notes:


一些注意事项：

---

The old assert methods are never, ever, going away. 
Developers may continue using the old `assertEquals`, `assertTrue`, and so on. 
The second parameter of an `assertThat` statement is a Matcher. 
We include the Matchers we want as static imports, like this:


旧的 assert 方法永远不会消失。
开发人员可以继续使用旧的 `assertEquals` 、`assertTrue` 等。
`assertThat` 语句的第二个参数是一个匹配器。
我们将我们想要的匹配器包含为静态导入，如下所示：

---

`import static org.hamcrest.CoreMatchers.is;`

or:


或者：

---

`import static org.hamcrest.CoreMatchers.*;`

Manually importing Matcher methods can be frustrating. 
Eclipse 3.3 includes the ability to define "Favorite" classes to import static methods from, which makes it easier (Search for "Favorites" in the Preferences dialog). 
We expect that support for static imports will improve in all Java IDEs in the future.


手动导入 Matcher 方法可能令人沮丧。
Eclipse 3.3 包括定义 "Favorite" 类以从中导入静态方法的能力，这使得它更容易（在 “Preferences” 对话框中搜索 "Favorites" ）。
我们期望未来所有 Java IDE 都会改进对静态导入的支持。

---

To allow compatibility with a wide variety of possible matchers, we have decided to include the classes from hamcrest-core, from the Hamcrest project. 
This is the first time that third-party classes have been included in JUnit.


为了与各种可能的匹配器兼容，我们决定包含来自 Hamcrest 项目的 hamcrest-core 的类。
这是第一次在 JUnit 中包含第三方类。

---

JUnit currently ships with a few matchers, defined in `org.hamcrest.CoreMatchers` and `org.junit.matchers.JUnitMatchers`.


JUnit 目前附带了一些匹配器，定义在 `org.hamcrest.CoreMatchers` 和 `org.junit.matchers.JUnitMatchers` 中。

---

To use many, many more, consider downloading the full hamcrest package:


要使用更多，请考虑下载完整的 hamcrest 包：

---

* Hamcrest page [http://code.google.com/p/hamcrest/wiki/Tutorial](http://code.google.com/p/hamcrest/wiki/Tutorial)
* Hamcrest Java on GitHub: [https://github.com/hamcrest/JavaHamcrest](https://github.com/hamcrest/JavaHamcrest)


* Hamcrest page [Hamcrest Tutorial](../../hamcrest/Hamcrest/Hamcrest%20Tutorial.md)
* Hamcrest Java on GitHub: [https://github.com/hamcrest/JavaHamcrest]()

JUnit contains special support for comparing string and array values, giving specific information on how they differ. 
This is not yet available using the `assertThat` syntax, but we hope to bring the two assert methods into closer alignment in future releases.


JUnit 包含对比较字符串和数组值的特殊支持，提供有关它们如何不同的具体信息。
使用 `assertThat` 语法尚不可用，但我们希望在未来的版本中使这两个 assert 方法更加一致。

---

# JUnit Matchers *JUnit 匹配器*

JUnit includes useful matchers for use with the assertThat method, but they are not currently included in the basic CoreMatchers class from hamcrest.


JUnit 包括与 assertThat 方法一起使用的有用匹配器，但它们目前未包含在 hamcrest 的基本 CoreMatchers 类中。

---

* Javadoc JUnitMatchers [http://junit.org/junit4/javadoc/latest/org/junit/matchers/JUnitMatchers.html](http://junit.org/junit4/javadoc/latest/org/junit/matchers/JUnitMatchers.html)

# Hamcrest CoreMatchers *Hamcrest CoreMatchers*

Useful Hamcrest CoreMatchers are included in the JUnit distribution


有用的 Hamcrest CoreMatchers 包含在 JUnit 发行版中

---

* JavaDoc Hamcrest CoreMatchers [http://junit.org/junit4/javadoc/latest/org/hamcrest/CoreMatchers.html](http://junit.org/junit4/javadoc/latest/org/hamcrest/CoreMatchers.html)

# Thirdparty Matchers *第三方匹配器*

Other, potentially Matchers out there include


其他潜在的匹配器包括

---

* [Excel spreadsheet matchers](https://github.com/tobyweston/simple-excel)
* [JSON matchers](https://github.com/hertzsprung/hamcrest-json)
* [XML/XPath matchers](https://github.com/davidehringer/xml-matchers)


* [Excel 电子表格匹配器](https://github.com/tobyweston/simple-excel)

    Simple-Excel 使用简单的 Java 构建器包装 Apache POI 项目，无需所有样板即可快速轻松地修改工作表。

    使用Hamcrest Matcher比较工作簿并在测试中获得快速反馈。
    比较两张纸将比较整个内容。
    您将获得差异的完整报告，而不仅仅是第一次遇到的差异。

* [JSON 匹配器](https://github.com/hertzsprung/hamcrest-json)

    用于比较 JSON 文档的 Hamcrest 匹配器，由JSONassert 库支持。

* [XML/XPath 匹配器](https://github.com/davidehringer/xml-matchers)

    XML 文档的 Hamcrest 匹配器集合。匹配器包括 XPath 1.0 和 2.0、使用 XML Schema 或 RelaxNG（实验）的模式验证以及 XML 文档等效性。
    查看[教程](https://github.com/davidehringer/xml-matchers/blob/master/docs/Tutorial.md)页面以获得快速概览。
