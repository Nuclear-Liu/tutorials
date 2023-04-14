# Hamcrest Distributables *Hamcrest 发行版*

## Introduction *引言*

This document describes the current and previous versions of the various Hamcrest distributables, and the functionality contained in each of them.


本文档描述了各种 Hamcrest 发行版的当前和以前版本，以及每个发行版中包含的功能。

---

The latest version of Hamcrest consists of a single `jar` file which contains base classes and a library of useful matcher implementations. 
This is different from older versions.


最新版本的 Hamcrest 由**一个单独的 `jar` 文件组成**，其中包含基类和一个有用的匹配器实现库。
这与旧版本不同。

---

Older versions of Hamcrest consisted of a number of different `jar`s matching the different needs of applications. 
The change in the `jar` packaging requires care when upgrading.


旧版本的 Hamcrest 由许多不同的 `jar` 组成，以满足应用程序的不同需求。
`jar` 包装的变化在升级时需要小心。

---

## The Hamcrest Jar *Hamcrest Jar*

All the base classes and standard matcher implementations are contained in a single `jar` file called `hamcrest-2.2.jar`.


所有基类和标准匹配器实现都包含在一个名为 `hamcrest-2.2.jar` 的 `jar` 文件中。

---

### Using Hamcrest in a Gradle Project *在 Gradle 项目中使用 Hamcrest*

Add "`org.hamcrest:hamcrest:2.2`" to the dependencies section of your `build.gradle`, for example:


将 “`org.hamcrest:hamcrest:2.2`” 添加到 `build.gradle` 的依赖项部分，例如：

---

```groovy
apply plugin: 'java'

dependencies {
    testImplementation 'org.hamcrest:hamcrest:2.2'
}

```

Note: older versions of Gradle use the `testCompile` configuration instead of the `testImplementation` configuration.


注意：旧版本的 Gradle 使用 **`testCompile`** 配置而不是 **`testImplementation`** 配置。

---

### Using Hamcrest in a Maven Project *在 Maven 项目中使用 Hamcrest*

Add the following to the `<dependencies>` section in your `pom.xml`:


将以下内容添加到 `pom.xml` 的 `<dependencies>` 部分：

---

```xml
<dependency>
    <groupId>org.hamcrest</groupId>
    <artifactId>hamcrest</artifactId>
    <version>2.2</version>
    <scope>test</scope>
</dependency>

```

### Download the Hamcrest Jar *下载 Hamcrest Jar*

You can download the `jar`s directly from Maven Central. 
You can find the `jar`s by searching Maven Central for `groupId` `org.hamcrest` using the following link:


你可以直接从 Maven Central 下载 `jar` 。
您可以使用以下链接通过在 Maven Central 中搜索 `groupId` `org.hamcrest` 来找到 `jar` ：

---

> [https://search.maven.org/artifact/org.hamcrest/hamcrest](https://search.maven.org/artifact/org.hamcrest/hamcrest)

## Previous Versions of Hamcrest *Hamcrest 之前的版本*

Prior to version 2.x, Hamcrest was distributed through multiple `jar`s, described below.


在 2.x 版之前，Hamcrest 是通过多个 `jar` 分发的，如下所述。

---

* `hamcrest-core.jar`: This was the core API to be used by third-party framework providers. 
    This includes a foundation set of matcher implementations for common operations. 
    This library was used as a dependency for many third-party libraries, including JUnit 4.x. From Hamcrest version 2.x, all the classes in `hamcrest-core.jar` were moved into `hamcrest.jar`.

* `hamcrest-library.jar`: The library of Matcher implementations which are based on the core functionality in `hamcrest-core.jar`. 
    From Hamcrest version 2.x, all the classes in `hamcrest-core.jar` were moved into `hamcrest.jar`.

* `hamcrest-integration.jar`: Provides integration between Hamcrest and other testing tools, such as jMock and EasyMock. 
    It depends upon `hamcrest-core.jar` and `hamcrest-library.jar`. 
    There are no new releases of this library since version 1.3.

* `hamcrest-generator.jar`: A tool to allow many Matcher implementations to be combined into a single class with static methods returning the different matchers so users don’t have to remember many classes/packages to import. 
    Generates code. 
    This library is only used internally at compile time. 
    It is not necessary for the use of any of the other hamcrest libraries at runtime. 
    There are no new releases of this library since version 1.3.

* `hamcrest-all.jar`: One jar containing all classes of all the other `jar`s. 
    There are no new releases of this library since version 1.3. 
    Please use the single `hamcrest.jar` instead.


* `hamcrest-core.jar`: 这是第三方框架提供商要使用的核心 API。
    这包括用于常见操作的一组**基础匹配器**实现。
    该库被用作许多第三方库的依赖项，包括 JUnit 4.x。**从 Hamcrest 2.x 版开始，`hamcrest-core.jar` 中的所有类都移到了 `hamcrest.jar` 中。**

* `hamcrest-library.jar`: 这个库的 Matcher 实现基于 `hamcrest-core.jar` 中的核心功能。
    从 Hamcrest 2.x 版开始，`hamcrest-core.jar` 中的所有类都移到了 `hamcrest.jar` 中。

* `hamcrest-integration.jar`: 提供 Hamcrest 与其他测试工具（如 jMock 和 EasyMock）之间的集成。
    它依赖于 `hamcrest-core.jar` 和 `hamcrest-library.jar`。
    自 1.3 版以来，此库没有新版本。

* `hamcrest-generator.jar`: 一个允许将许多匹配器实现组合到一个类中的工具，静态方法返回不同的匹配器，因此用户不必记住要导入的许多类/包。
    生成代码。
    该库仅在编译时在内部使用
    在运行时不需要使用任何其他 hamcrest 库。
    自 1.3 版以来，此库没有新版本

* `hamcrest-all.jar`: 一个 jar 包含所有其他 `jar` 的所有类。
    自 1.3 版以来，此库没有新版本。
    请改用单个 `hamcrest.jar` 。

---

### Upgrading from Hamcrest 1.x *从 Hamcrest 1.x 升级*

Care must be taken when upgrading from Hamcrest 1.3 or earlier. 
Due to the change in packaging, the version conflict resolution that happens in dependency management tools won’t happen automatically. 
A common example is projects that depend upon JUnit 4. 
JUnit 4 declares a transitive dependency upon `hamcrest-core-1.3.jar`. 
Because `hamcrest-core` is not the same `artifact` as `hamcrest`, it will not be upgraded.


从 Hamcrest 1.3 或更早版本升级时必须小心。
由于打包的变化，**依赖管理工具**中发生的版本冲突解决不会自动发生。
一个常见的例子是依赖 JUnit 4 的项目。
JUnit 4 声明了对 `hamcrest-core-1.3.jar` 的传递依赖。
因为 `hamcrest-core` 和 `hamcrest` 不是同一个 `artifact` ，所以它不会升级。

---

To address this issue, Hamcrest 2.1 and later also publish artifacts for `hamcrest-core` and `hamcrest-library`. 
Although these `jar`s contain no classes, they trigger the version conflict upgrade in the dependency manager, and correctly declare transitive dependencies upon the new `hamcrest` packaging. 
Users can directly declare a dependency upon these shim `jar`s to force the upgrade.


为了解决这个问题，Hamcrest 2.1 及更高版本还发布了 `hamcrest-core` 和 `hamcrest-library` 的工件。
虽然这些 `jar` 不包含任何类，但它们会触发依赖管理器中的版本冲突升级，并在新的 `hamcrest` 包装上正确声明传递依赖。
用户可以直接声明对这些 shim `jar` 的依赖来强制升级

---

#### Gradle Upgrade Example *Gradle 升级示例*

```groovy
apply plugin: 'java'

dependencies {
    testImplementation 'org.hamcrest:hamcrest:2.2'
    testImplementation 'org.hamcrest:hamcrest-library:2.2'
    testImplementation 'junit:junit:4.13'
}

```

#### Maven Upgrade Example *Maven 升级示例*

**Warning**: Maven users should declare a dependency upon `hamcrest-library` **before** other dependencies, otherwise the older version will take precedence.


**警告**: Maven 用户应该在其他依赖项之前声明对 `hamcrest-library` 的依赖项，否则旧版本将优先。

---

```xml
<dependencies>
    <dependency>
        <groupId>org.hamcrest</groupId>
        <artifactId>hamcrest</artifactId>
        <version>2.2</version>
        <scope>test</scope>
    </dependency>
    <dependency>
        <groupId>org.hamcrest</groupId>
        <artifactId>hamcrest-library</artifactId>
        <version>2.2</version>
        <scope>test</scope>
    </dependency>
    <dependency>
        <groupId>junit</groupId>
        <artifactId>junit</artifactId>
        <version>4.13</version>
        <scope>test</scope>
    </dependency>
</dependencies>

```
