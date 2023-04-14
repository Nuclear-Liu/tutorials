# Use with Maven *与 Maven 一起使用*

> redwin edited this page on May 27, 2017 · 5 revisions 

> redwin 2017年5月27日编辑本页 · 5次修改

# Maven Dependency *Maven 依赖*

```xml
<dependency>
  <groupId>junit</groupId>
  <artifactId>junit</artifactId>
  <version>4.13.2</version>
  <scope>test</scope>
</dependency> 

```

## Execution *执行*

See [Maven Surefire plugin](http://maven.apache.org/plugins/maven-surefire-plugin/) and [Maven Surefire plugin](http://maven.apache.org/plugins/maven-surefire-plugin/).


参见 [Maven Surefire plugin](http://maven.apache.org/plugins/maven-surefire-plugin/) 和 [Maven Surefire plugin](http://maven.apache.org/plugins/maven-surefire-plugin/) 。

## Use JUnit and Hamcrest in Maven *在 Maven 中使用 JUnit 和 Hamcrest*

### Version 4.11 and later *版本 4.11 及更高版本*

Up until now there were two Maven artifacts for JUnit: `junit:junit-dep` and `junit:junit`. 
From a Maven point-of-view only the former made sense because it did not contain the Hamcrest classes but declared a dependency to the Hamcrest Maven artifact. 
The latter included the Hamcrest classes which was very un-Maven-like.


到目前为止，JUnit 有两个 Maven 工件：`junit:junit-dep` 和 `junit:junit`。
从 Maven 的角度来看，只有前者有意义，因为它不包含 Hamcrest 类，但声明了对 Hamcrest Maven 工件的依赖。
后者包括非常不像 Maven 的 Hamcrest 类。

---

From this release (4.11) on, you should use `junit:junit` which will be what `junit:junit-dep` used to. 
If you still reference `junit:junit-dep`, Maven will automatically relocate you to the new `junit:junit` and issue a warning for you to fix.


从这个版本 (4.11) 开始，你应该使用 `junit:junit` ，这将是 `junit:junit-dep` 曾经使用的。
如果你仍然引用 `junit:junit-dep` ，Maven 会自动将你重新定位到新的 `junit:junit` 并发出警告让你修复。

---

### Version 4.10 and earlier *4.10 及更早版本*

In your `pom.xml`, declare dependency used as `junit-dep`, and also override transitive dependency to `hamcrest-core`, so you can use Hamcrest full library of matchers:

在你的 `pom.xml` 中，将依赖声明为 `junit-dep` ，并将传递依赖覆盖到 `hamcrest-core` ，这样你就可以使用 Hamcrest 完整的匹配器库：

---

```text
<dependency>
  <groupId>org.hamcrest</groupId>
  <artifactId>hamcrest-core</artifactId>
  <version>1.3</version>
  <scope>test</scope>
</dependency>
<dependency>
  <groupId>junit</groupId>
  <artifactId>junit-dep</artifactId>
  <version>4.10</version>
  <scope>test</scope>
</dependency>         
<dependency>
  <groupId>org.hamcrest</groupId>
  <artifactId>hamcrest-library</artifactId>
  <version>1.3</version>
  <scope>test</scope>
</dependency>
<dependency>
  <groupId>org.mockito</groupId>
  <artifactId>mockito-core</artifactId>
  <version>1.9.0</version>
  <scope>test</scope>
</dependency>

```

## Discover slowest test from Surefire xml output: *从 Surefire xml 输出中发现最慢的测试：*

The following one-liner described [here](http://stackoverflow.com/questions/5094410/how-to-list-the-slowest-junit-tests-in-a-multi-module-maven-build).


[此处](http://stackoverflow.com/questions/5094410/how-to-list-the-slowest-junit-tests-in-a-multi-module-maven-build) 描述的以下单行。

---

Reports on the test times, slowest sorted to the top:


关于测试时间的报告，最慢的排序到顶部：

---

```shell
$ grep -h "<testcase" `find . -iname "TEST-*.xml"` | sed 's/<testcase name="\(.*\)" classname="\(.*\)" time="\(.*\)".*/\3\t\2.\1/' | sort -rn | head
```
