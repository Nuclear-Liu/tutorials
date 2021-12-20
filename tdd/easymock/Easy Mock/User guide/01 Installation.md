# Installation _安装_

## Requirements _要求_

* EasyMock 3.5+ requires Java 1.6 and above
* EasyMock 3.4- requires Java 1.5 and above
* Objenesis (2.0+) must be in the classpath to perform class mocking


* EasyMock 3.5+ 需要 Java 1.6 及更高版本
* EasyMock 3.4- 需要 Java 1.5 及以上
* Objenesis (2.0+) 必须在类路径中才能执行类模拟

---

## Using Maven _使用 Maven_

EasyMock is available in the Maven central repository. 
Just add the following dependency to your `pom.xml`:


EasyMock 在 Maven 中央存储库中可用。
只需将以下依赖项添加到您的 `pom.xml` 中：

---

```xml
<dependency>
  <groupId>org.easymock</groupId>
  <artifactId>easymock</artifactId>
  <version>4.3</version>
  <scope>test</scope>
</dependency>
```

You can obviously use any other dependency tool compatible with the Maven repository.


您显然可以使用任何其他与 Maven 存储库兼容的依赖工具。

---

## Standalone _独立_

* Download the [EasyMock zip file]()
* It contains the `easymock-4.3.jar` to add to your classpath
* To perform class mocking, also add [Objenesis]() to your classpath.
* The bundle also contains jars for the javadoc, the tests, the sources and the samples


* 下载 [EasyMock zip file]()
* 它包含要添加到类路径的 `easymock-4.3.jar`
* 要执行类模拟，还要将 [Objenesis]() 添加到您的类路径中。
* 该包还包含用于 javadoc、测试、源代码和示例的 jar

---

## Android `Since 3.2`

EasyMock can be used on Android VM (Dalvik). 
Just add EasyMock and Dexmaker as dependencies of the apk project used to test your application. 
It is a good idea to exclude Cglib since Dexmaker is used instead. 
If you use Maven, the final required dependencies will look like this:


EasyMock 可以在 Android VM (Dalvik) 上使用。
只需添加 EasyMock 和 Dexmaker 作为用于测试应用程序的 apk 项目的依赖项。
由于使用的是 Dexmaker，因此排除 Cglib 是个好主意。
如果您使用 Maven，最终所需的依赖项将如下所示：

---

```text
<dependency>
  <groupId>org.easymock</groupId>
  <artifactId>easymock</artifactId>
  <version>4.3</version>
</dependency>
<dependency>
  <groupId>org.droidparts.dexmaker</groupId>
  <artifactId>dexmaker</artifactId>
  <version>1.5</version>
</dependency>
```
