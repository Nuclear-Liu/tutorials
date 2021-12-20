## Overview _概述_

DbUnit includes a comprehensive test suite.


DbUnit 包括一个全面的测试套件。

---

Most of the tests are unit tests, and do not rely on any particular database environment. 
The unit tests are executed in the normal Maven test phase.

Some of the tests are integration tests, and can test DbUnit functionality against a particular database. 
The integration tests are executed in the Maven integration-test phase.

## Running Databases _运行数据库_

### Overview _概述_

The database integration tests require a running database server with a few expected configuration items, such as credentials and schema. 
If needed, find the needed values defined in database-named `.properties` files located in the `src/test/resources` directory.


数据库集成测试需要一个正在运行的数据库服务器，其中包含一些预期的配置项，例如凭据和架构。
如果需要，请在位于 `src/test/resources` 目录中以数据库命名的 `.properties` 文件中找到定义的所需值。

---

Apache Derby, H2, and HSQLDB have an in-memory runtime option enabling the tests to automatically start and stop the database. 
Running the database integration tests with those databases requires no additional setup.


Apache Derby、H2 和 HSQLDB 有一个内存运行时选项，使测试能够自动启动和停止数据库。
使用这些数据库运行数据库集成测试不需要额外的设置。

---

However, the remaining databases require additional setup. 
This section outlines how to setup and run them.


但是，其余的数据库需要额外的设置。
本节概述了如何设置和运行它们。

---

### Using Docker Images _使用 Docker 镜像_

For typical dbUnit development with databases requiring setup, use the dbUnit database Docker images for easy, pre-configured setup. 
The dbUnit CI server uses these database Docker images for the tests.


对于需要设置数据库的典型 dbUnit 开发，请使用 dbUnit 数据库 Docker 映像进行简单的预配置设置。
dbUnit CI 服务器使用这些数据库 Docker 映像进行测试。

---

The next sections describe using Docker for the databases where needed. 
Using Docker is not required - having the databases for the tests is required - and using Docker is the easiest solution we have found for the database setups.


下一节将描述在需要的情况下将 Docker 用于数据库。
不需要使用 Docker - 需要有用于测试的数据库 - 使用 Docker 是我们为数据库设置找到的最简单的解决方案。

---

#### Install Docker _安装 Docker_

To use the dbUnit Docker database images, install Docker and follow the running tests instructions.


要使用 dbUnit Docker 数据库映像，请安装 Docker 并按照运行测试说明进行操作。

---

##### Windows

Install Docker for Windows from https://www.docker.com/docker-windows or using Chocolatey.


从 https://www.docker.com/docker-windows 或使用 Chocolatey 安装 Docker for Windows。

---

##### OS X

Using Homebrew


使用 Homebrew

---

`brew cask install docker`

Not Using Homebrew


不使用 Homebrew

---

Install Docker for Mac from https://docs.docker.com/docker-for-mac/install/


从 https://docs.docker.com/docker-for-mac/install/ 安装 Docker for Mac

---

##### Linux

Based on distro:


基于发行版：

---

* https://docs.docker.com/install/linux/docker-ce/ubuntu/
* https://docs.docker.com/install/linux/docker-ce/debian/
* https://docs.docker.com/install/linux/docker-ce/centos/
* https://docs.docker.com/install/linux/docker-ce/fedora/

#### Start Databases _启动数据库_

All instructions assume current directory is (path to)/dbunit-docker

##### Starting All Databases _启动所有数据库_

To start the databases all at once:


要一次启动所有数据库：

---

```shell
docker-compose up -d
```

To stop them:


停止它们：

---

```shell
docker-compose down
```

##### Starting One Database _启动一个数据库_

If desired, start only one database by also specifying the name, as in the following table.


如果需要，还可以通过指定名称来仅启动一个数据库，如下表所示。

---

Docker Database Start Commands


Docker 数据库启动命令

---

| Database | Start Command | DB Ready Messages |
| ---- | ---- | ---- |
| DB2 | TODO |  |
| MySQL | docker-compose up -d mysql | Creating dbunitdocker_mysql_1 [v] |
| Oracle 11g | docker-compose up -d oracle11 | User altered. <br/> SQL> Disconnected from Oracle Database 11g Express Edition Release 11.2.0.2.0 - 64bit Production |
| PostgreSQL | docker-compose up -d postgres | Creating dbunitdocker_postgres_1 [v] |
| SQLServer | docker-compose up -d mssql | Creating dbunitdocker_mssql_1 [v] |


| 数据库 | 启动命令 | 数据库就绪消息 |
| ---- | ---- | ---- |
| DB2 | TODO |  |
| MySQL | docker-compose up -d mysql | Creating dbunitdocker_mysql_1 [v] |
| Oracle 11g | docker-compose up -d oracle11 | User altered. <br/> SQL> Disconnected from Oracle Database 11g Express Edition Release 11.2.0.2.0 - 64bit Production |
| PostgreSQL | docker-compose up -d postgres | Creating dbunitdocker_postgres_1 [v] |
| SQLServer | docker-compose up -d mssql | Creating dbunitdocker_mssql_1 [v] |

---

Stopping them is the same as with starting all:


停止它们与启动所有相同：

---

```shell
docker-compose down
```

### Using Databases Directly _直接使用数据库_

#### DB2

TODO

#### MySQL

1. Install MySQL database. 
  This can be on your local machine, or you can use any MySQL database you have access to.
2. Create a "dbunit" user and database, for example with these commands: 

    ```shell
    mysql -uroot -p <<EOF
    # (enter database root password)
    CREATE DATABASE dbunit;
    GRANT ALL ON dbunit.* TO dbunit@localhost IDENTIFIED BY "dbunit";
    EOF
    ```

    * These values are the defaults configured in the project. 
      For different values, override the properties.
    * To connect to a remote database, specify the client host name.
    * May also need to adjust the MySQL configuration to permit remote connection.


1. 安装 MySQL 数据库。
  这可以在您的本地机器上，也可以使用您有权访问的任何 MySQL 数据库。
2. 创建 "dbunit" 用户和数据库，例如使用以下命令：

    ```shell
    mysql -uroot -p <<EOF
    # (enter database root password)
    CREATE DATABASE dbunit;
    GRANT ALL ON dbunit.* TO dbunit@localhost IDENTIFIED BY "dbunit";
    EOF
    ```

    * 这些值是项目中配置的默认值。
      对于不同的值，覆盖属性。
    * 要连接到远程数据库，请指定客户端主机名。
    * 可能还需要调整 MySQL 配置以允许远程连接。

---

#### Oracle 11g

1. Install Oracle database. 
  This can be on your local machine, or you can use any Oracle database you have access to.
2. Create a "dbunit" user with password "dbunit".
    * These values are the defaults configured in the project. 
        For different values, override the properties.

1. 安装 Oracle 数据库。
   这可以在您的本地计算机上，也可以使用您有权访问的任何 Oracle 数据库。
2. 创建一个密码为“dbunit”的“dbunit”用户。
    * 这些值是项目中配置的默认值。
      对于不同的值，覆盖属性。

#### PostgreSQL

1. Install PostgreSQL database. 
  This can be on your local machine, or you can use any PostgreSQL database you have access to.
2. Create a "dbunit" user and database, for example with these commands: 

    ```shell
    sudo -u postgres psql <<EOF
    CREATE USER dbunit WITH PASSWORD 'dbunit';
    CREATE DATABASE dbunit OWNER dbunit;
    
    \q
    EOF
    ```

    * These values are the defaults configured in the project. 
      For different values, override the properties.


1. 安装 PostgreSQL 数据库。
   这可以在您的本地机器上，也可以使用您有权访问的任何 PostgreSQL 数据库。
2. 创建“dbunit”用户和数据库，例如使用以下命令：

    ```shell
    sudo -u postgres psql <<EOF
    CREATE USER dbunit WITH PASSWORD 'dbunit';
    CREATE DATABASE dbunit OWNER dbunit;
    
    \q
    EOF
    ```

    * 这些值是项目中配置的默认值。
      对于不同的值，覆盖属性。

#### SQLServer

TODO

## Running Integration Tests _运行集成测试_

### Database Properties _数据库属性_

Each database environment is configured by properties and dependencies, set by the Maven profile or other means. 
The properties relate to standard JDBC connection parameters, and the dependencies cover the database-specific JDBC driver.


每个数据库环境都由属性和依赖项配置，由 Maven 配置文件或其他方式设置。
属性与标准 JDBC 连接参数相关，依赖项涵盖特定于数据库的 JDBC 驱动程序。

---

The properties include:


属性包括：

---

* `dbunit.profile` - the name of the database environment
* `dbunit.profile.driverClass` - JDBC driver class
* `dbunit.profile.url` - JDBC connection URL
* `dbunit.profile.schema` - database schema (may be case-sensitive)
* `dbunit.profile.user` - database connection user name
* `dbunit.profile.password` - database connection password
* `dbunit.profile.unsupportedFeatures` - comma-separated list of features not to test
* `dbunit.profile.ddl` - database-specific DDL script to create tables
* `dbunit.profile.multiLineSupport` - true if database supports SQL line continuation


* `dbunit.profile` - 数据库环境的名称
* `dbunit.profile.driverClass` - JDBC驱动类
* `dbunit.profile.url` - JDBC 连接 URL
* `dbunit.profile.schema` - 数据库 schema （可能区分大小写）
* `dbunit.profile.user` - 数据库连接用户名
* `dbunit.profile.password` - 数据库连接密码
* `dbunit.profile.unsupportedFeatures` - 不测试的以逗号分隔的功能列表
* `dbunit.profile.ddl` - 用于创建表的特定于数据库的 DDL 脚本
* `dbunit.profile.multiLineSupport` - 如果数据库支持 SQL 行延续，则为 true

----

Each Maven database integration test profile defines these properties. 
You can override the profile property values by setting them in Maven's `settings.xml` file, located in `~/.m2/settings.xml`:


每个 Maven 数据库集成测试配置文件都定义了这些属性。
您可以通过在位于 `~/.m2/settings.xml` 的 Maven 的 `settings.xml` 文件中设置它们来覆盖配置文件属性值：

---

```xml
<settings>
  <profiles>
    <profile>
      <id>oracle-ojdbc14</id>
      <properties>
        <dbunit.profile.url>jdbc:oracle:thin:@myhost:1521:mysid</dbunit.profile.url>
      </properties>
    </profile>
  </profiles>
</settings>
```

### Running Integration Tests with Maven _使用 Maven 运行集成测试_

#### Maven Profiles _Maven 配置文件_

Maven profiles control which database configuration is in use for a particular test run. 
The profiles are named for the database (see specific database sections below). 
List all profiles using this command:


Maven 配置文件控制用于特定测试运行的数据库配置。
配置文件以数据库命名（请参阅下面的特定数据库部分）。
使用此命令列出所有配置文件：

---

`mvn help:all-profiles`

The default profile (when not specifying a profile) runs the tests using an HSQLDB in-memory database.


默认配置文件（未指定配置文件时）使用 HSQLDB 内存数据库运行测试。

---

For example, to run the tests using the DbUnit Oracle10DataTypeFactory and the Oracle 8 JDBC driver, use this command:


例如，要使用 DbUnit Oracle10DataTypeFactory 和 Oracle 8 JDBC 驱动程序运行测试，请使用以下命令：

---

`mvn verify -Poracle10-ojdbc8`

**Note:** The integration tests are run using the Maven failsafe-plugin.


**注意：**集成测试使用 Maven 故障安全插件运行。

---

#### Clean Build _清洁构建_

It's important to occasionally run a clean build to ensure no problems are hidden by existing compiled classes or resource files. 
Simply run the Maven `clean` goal:


偶尔运行干净的构建以确保现有编译类或资源文件不会隐藏任何问题很重要。
只需运行 Maven `clean` 目标：

---

`mvn clean`

#### Optionally Skip Unit Tests When Running Integration Tests _可选地在运行集成测试时跳过单元测试_

To skip running the unit tests with the integration tests, specify the "it-config" profile in addition to the database-named profile. 
While we typically run all tests, it's useful to save the unit tests run time when focusing on one or more integration tests.


要跳过使用集成测试运行单元测试，除了指定数据库命名的配置文件外，还指定“it-config”配置文件。
虽然我们通常运行所有测试，但在关注一个或多个集成测试时节省单元测试运行时间很有用。

---

For example, this runs the unit tests and the integration tests with Apache Derby:


例如，这将使用 Apache Derby 运行单元测试和集成测试：

---

`mvn -Pderby verify`

and this skips the unit tests and runs the integration tests with Apache Derby:


这将跳过单元测试并使用 Apache Derby 运行集成测试：

---

`mvn -Pit-config,derby verify`

#### Maven Commands _Maven 命令_

| Database | Maven Command |
| ---- | ---- |
| Apache Derby | `mvn -e -Pit-config,derby verify` |
| DB2 | `mvn -e -Pit-config,db2 verify` |
| H2 | `mvn -e -Pit-config,h2 verify` |
| HSQLDB | `mvn -e -Pit-config,hsqldb verify` |
| MySQL | `mvn -e -Pit-config,mysql verify` |
| Oracle | `mvn -e -Pit-config,oracle-ojdbc8 verify` <br/> `mvn -e -Pit-config,oracle10-ojdbc8 verify` <br/> `mvn -e -Pit-config,oracle10-ojdbc8v12 verify` <br/> `mvn -e -Pit-config,oracle-ojdbc6 verify` |
| PostgreSQL | `mvn -e -Pit-config,postgresql verify` |
| SQLServer | `mvn -e -Pit-config,mssql41 verify` |
| SQLServer 2019 | `mvn -e -Pit-config,mssql2019 verify` |

### Running Integration Tests with IDEs _使用 IDE 运行集成测试_

#### Eclipse / Maven Integration _Eclipse / Maven 集成_

The m2e plugin has an "Active Maven Profile" setting in a project's properties which activates the specified profile(s) during builds. 
Access it from the project's context menu:


m2e 插件在项目的属性中有一个“Active Maven Profile”设置，它在构建期间激活指定的配置文件。
从项目的上下文菜单访问它：

---

`(project context menu) -> Properties -> Maven -> Active Maven Profiles`

Enter the Maven profile name(s) to always run, including the integration test profiles such as "derby".


输入要始终运行的 Maven 配置文件名称，包括集成测试配置文件，例如“derby”。

---

#### Using dbunit.properties File _使用 dbunit.properties 文件_

"`dbunit.properties`" is useful for environments which make it difficult if not impossible to use Maven's profiles. 
For instance, directly running JUnit tests within IDEs typically don't use Maven and therefore are not using Maven's profiles (which set the the database configuration) and tests fail.


“`dbunit.properties`”对于难以使用 Maven 配置文件的环境很有用。
例如，在 IDE 中直接运行 JUnit 测试通常不使用 Maven，因此不使用 Maven 的配置文件（设置数据库配置）并且测试失败。

---

`dbunit.properties` contains the properties a profile sets. It is an optional file and is not provided by DbUnit; one must create it under `src/test/resources`.


`dbunit.properties` 包含配置文件设置的属性。它是一个可选文件，不由 DbUnit 提供；必须在 `src/test/resources` 下创建它。

---

Following is an example content of `dbunit.properties` that works for activating the profile h2:


以下是用于激活配置文件 h2 的 `dbunit.properties` 的示例内容：

---

```text
dbunit.profile=h2
dbunit.profile.driverClass=org.h2.Driver
dbunit.profile.url=jdbc:h2:target/h2/test
dbunit.profile.schema=PUBLIC
dbunit.profile.user=sa
dbunit.profile.password=
dbunit.profile.ddl=h2.sql
dbunit.profile.unsupportedFeatures=BLOB,CLOB,SCROLLABLE_RESULTSET,INSERT_IDENTITY,TRUNCATE_TABLE,SDO_GEOMETRY,XML_TYPE
dbunit.profile.multiLineSupport=true
```

The directory `src/test/resources` has the properties files for each supported database (the Maven profiles use them for configuring the tests accordingly). 
They are convenient for simply copying the desired one to `dbunit.properties`.


目录 `src/test/resources` 包含每个受支持数据库的属性文件（Maven 配置文件使用它们来相应地配置测试）。
它们很方便简单地将所需的复制到 `dbunit.properties` 。

---
