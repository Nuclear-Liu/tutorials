# Java contract test helpers *Java 合约测试助手*

> chas678 edited this page on Aug 27, 2014 · 6 revisions 

> chas678 于 2014-08-27 编辑本页 · 6次修改

Certain code you override or implement is expected to adhere to contracts laid out in the Java SDK. 
Examples of these are:


您覆盖或实现的某些代码应遵守 Java SDK 中规定的合同。
例如：

---

* `equals` method: [http://docs.oracle.com/javase/8/docs/api/java/lang/Object.html#equals(java.lang.Object)](http://docs.oracle.com/javase/8/docs/api/java/lang/Object.html#equals(java.lang.Object))
* `hashcode` method: [http://docs.oracle.com/javase/8/docs/api/java/lang/Object.html#hashCode()](http://docs.oracle.com/javase/8/docs/api/java/lang/Object.html#hashCode())
* `Comparable` interface: [http://docs.oracle.com/javase/8/docs/api/java/lang/Comparable.html](http://docs.oracle.com/javase/8/docs/api/java/lang/Comparable.html)
* `Serializable` interface: [http://docs.oracle.com/javase/8/docs/api/java/io/Serializable.html](http://docs.oracle.com/javase/8/docs/api/java/io/Serializable.html)


* `equals` 方法: [http://docs.oracle.com/javase/8/docs/api/java/lang/Object.html#equals(java.lang.Object)](http://docs.oracle.com/javase/8/docs/api/java/lang/Object.html#equals(java.lang.Object))
* `hashcode` 方法: [http://docs.oracle.com/javase/8/docs/api/java/lang/Object.html#hashCode()](http://docs.oracle.com/javase/8/docs/api/java/lang/Object.html#hashCode())
* `Comparable` 接口: [http://docs.oracle.com/javase/8/docs/api/java/lang/Comparable.html](http://docs.oracle.com/javase/8/docs/api/java/lang/Comparable.html)
* `Serializable` 接口: [http://docs.oracle.com/javase/8/docs/api/java/io/Serializable.html](http://docs.oracle.com/javase/8/docs/api/java/io/Serializable.html)

---

A project called junit-addons on SourceForge has test superclasses that enable compliance testing of objects to these java contracts


SourceForge 上一个名为 junit-addons 的项目具有测试超类，可以对这些 Java 合同的对象进行合规性测试

---

* [http://junit-addons.sourceforge.net/junitx/extensions/package-frame.html](http://junit-addons.sourceforge.net/junitx/extensions/package-frame.html)

Although these test helper classes work without issue, they are designed for pre-generic java, and JUnit 3.x and could be confusing to developers used to JUnit 4.x annotations style of test implementation.


尽管这些测试助手类可以正常工作，但它们是为预泛型 java 和 JUnit 3.x 设计的，可能会让习惯于 JUnit 4.x 注释测试实现风格的开发人员感到困惑。

---

The project [Equals Verifier](https://github.com/jqno/equalsverifier) provides support for testing `equals` and `hashCode`.


项目 [Equals Verifier](https://github.com/jqno/equalsverifier) 为测试 `equals` 和 `hashCode` 提供支持。

---
