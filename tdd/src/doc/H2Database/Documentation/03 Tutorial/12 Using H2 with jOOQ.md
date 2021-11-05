## Using H2 with jOOQ *在 jOOQ 中使用 H2*

jOOQ adds a thin layer on top of JDBC, allowing for type-safe SQL construction, including advanced SQL, stored procedures and advanced data types.
jOOQ takes your database schema as a base for code generation.
If this is your example schema:


jOOQ 在 JDBC 之上添加了一个薄层，允许类型安全的 SQL 构造，包括高级 SQL、存储过程和高级数据类型。
jOOQ 将您的数据库模式作为代码生成的基础。
如果这是您的示例架构：

----

```sql
CREATE TABLE USER (ID INT, NAME VARCHAR(50));
```

then run the jOOQ code generator on the command line using this command:


然后使用以下命令在命令行上运行 jOOQ 代码生成器：

----

```shell
java -cp jooq.jar;jooq-meta.jar;jooq-codegen.jar;h2-1.4.199.jar;.
org.jooq.util.GenerationTool /codegen.xml
```

...where `codegen.xml` is on the classpath and contains this information


...其中 `codegen.xml` 在类路径上并包含此信息

----

```xml
<?xml version="1.0" encoding="UTF-8" standalone="yes"?>
<configuration xmlns="http://www.jooq.org/xsd/jooq-codegen-3.11.0.xsd">
    <jdbc>
        <driver>org.h2.Driver</driver>
        <url>jdbc:h2:~/test</url>
        <user>sa</user>
        <password></password>
    </jdbc>
    <generator>
        <database>
            <includes>.*</includes>
            <excludes></excludes>
            <inputSchema>PUBLIC</inputSchema>
        </database>
        <target>
            <packageName>org.jooq.h2.generated</packageName>
            <directory>./src</directory>
        </target>
    </generator>
</configuration>
```

Using the generated source, you can query the database as follows:


使用生成的源，您可以按如下方式查询数据库：

----

```
DSLContext dsl = DSL.using(connection);
Result<UserRecord> result =
dsl.selectFrom(USER)
    .where(NAME.like("Johnny%"))
    .orderBy(ID)
    .fetch();
```

See more details on [jOOQ Homepage](https://www.jooq.org/) and in the [jOOQ Tutorial](https://www.jooq.org/tutorial)


在 [jOOQ Homepage]() 和 [jOOQ Tutorial]() 中查看更多详细信息

----
