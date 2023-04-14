## Overview _概述_

Building DbUnit is quite simple. 
You need the following software installed:


构建 DbUnit 非常简单。
您需要安装以下软件：

---

* Java SE SDK 8+
* Maven 3
* Git client

Once you have them all, you can build DbUnit by just typing `mvn`! 
The items below describe every step.


一旦你拥有了它们，你就可以通过输入 `mvn` 来构建 DbUnit！
下面的列表描述了每个步骤。

---

## Generating the JAR _生成 JAR_

* Install Java SE SDK and Maven.
* Obtain DbUnit code, either current or released source (see Quick Links on left menu)
* On the root directory, simply type `mvn` in the command line. 
    (If you need to clean up the binaries, run `mvn clean install` instead.) The jar file will be generated in the target directory.


* 安装 Java SE JDK 和 Maven。
* 获取 DbUnit 代码，当前或已发布的源代码（请参阅左侧菜单上的快速链接）
* 在根目录中，只需在命令行中键入 `mvn` 。
    （如果您需要清理二进制文件，请改为运行 `mvn clean install`。）jar 文件将在目标目录中生成。

---

## Creating the site _创建站点_

Run `mvn site` in the command line; the site will be available on `target/site/index.html`. 
Note that you might get an `OutOfMemoryExceptionError`; if that happens, you must increase the heap size through the `MAVEN_OPTS` variable (for instance, on Unix systems, you could run `MAVEN_OPTS=-mx512M mvn site`).


在命令行中运行 `mvn site`；该站点将在 `target/site/index.html` 上可用。
请注意，您可能会收到“OutOfMemoryExceptionError”；如果发生这种情况，您必须通过 `MAVEN_OPTS` 变量增加堆大小（例如，在Unix 系统上，您可以运行 `MAVEN_OPTS=-mx512M mvn site` ）。
---

## Updating the repository and site _更新存储库和站点_

Once new code is committed, it is necessary to update the Maven repository with new snapshots, and also update the site. 
These 2 tasks can be done with a simple command:


提交新代码后，需要使用新快照更新 Maven 存储库，并更新站点。
这两个任务可以通过一个简单的命令来完成：

---

`mvn clean source:jar javadoc:jar deploy site site:deploy`
