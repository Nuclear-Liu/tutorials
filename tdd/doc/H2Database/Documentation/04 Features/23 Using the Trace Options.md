## Using the Trace Options *使用跟踪选项*

To find problems in an application, it is sometimes good to see what database operations where executed.
This database offers the following trace features:


要查找应用程序中的问题，有时最好查看执行了哪些数据库操作。
该数据库提供以下跟踪功能：

---

* Trace to `System.out` and/or to a file
* Support for trace levels `OFF, ERROR, INFO, DEBUG`
* The maximum size of the trace file can be set
* It is possible to generate Java source code from the trace file
* Trace can be enabled at runtime by manually creating a file


* 跟踪到 `System.out` 和或一个文件
* 支持跟踪级别 `OFF、ERROR、INFO、DEBUG`
* 可以设置跟踪文件的最大大小
* 可以从跟踪文件生成 Java 源代码
* 可以通过手动创建文件在运行时启用跟踪

---

### Trace Options *跟踪选项*

The simplest way to enable the trace option is setting it in the database URL.
There are two settings, one for `System.out` (`TRACE_LEVEL_SYSTEM_OUT`) tracing, and one for file tracing (`TRACE_LEVEL_FILE`).
The trace levels are 0 for `OFF`, 1 for `ERROR` (the default), 2 for `INFO`, and 3 for `DEBUG`.
A database URL with both levels set to `DEBUG` is:


启用跟踪选项的最简单方法是在数据库 URL 中设置它。
有两种设置，一种用于 `System.out` （ `TRACE_LEVEL_SYSTEM_OUT` ）跟踪，另一种用于文件跟踪（ `TRACE_LEVEL_FILE` ）。
`OFF` 的跟踪级别为 0， `ERROR` （默认）为 1， `INFO` 为 2， `DEBUG` 为 3。
两个级别都设置为“DEBUG”的数据库 URL 是：

---

`jdbc:h2:~/test;TRACE_LEVEL_FILE=3;TRACE_LEVEL_SYSTEM_OUT=3`

The trace level can be changed at runtime by executing the SQL command `SET TRACE_LEVEL_SYSTEM_OUT level` (for `System.out` tracing) or `SET TRACE_LEVEL_FILE level` (for file tracing).
Example:


可以在运行时通过执行 SQL 命令“SET TRACE_LEVEL_SYSTEM_OUT level”（用于“System.out”跟踪）或“SET TRACE_LEVEL_FILE level”（用于文件跟踪）来更改跟踪级别。
例子：

---

`SET TRACE_LEVEL_SYSTEM_OUT 3`

### Setting the Maximum Size of the Trace File *设置跟踪文件的最大大小*

When using a high trace level, the trace file can get very big quickly.
The default size limit is 16 MB, if the trace file exceeds this limit, it is renamed to `.old` and a new file is created.
If another such file exists, it is deleted. To limit the size to a certain number of megabytes, use `SET TRACE_MAX_FILE_SIZE mb`.
Example:


使用高跟踪级别时，跟踪文件会很快变得很大。
默认大小限制为 16 MB，如果跟踪文件超过此限制，则将其重命名为 `.old` 并创建一个新文件。
如果存在另一个这样的文件，则将其删除。要将大小限制为一定数量的兆字节，请使用`SET TRACE_MAX_FILE_SIZE mb`。
例子：

---

`SET TRACE_MAX_FILE_SIZE 1`

### Java Code Generation

When setting the trace level to `INFO` or `DEBUG`, Java source code is generated as well.
This simplifies reproducing problems.
The trace file looks like this:


将跟踪级别设置为 `INFO` 或 `DEBUG` 时，还会生成 Java 源代码。
这简化了复制问题
跟踪文件如下所示：

---

```
...
12-20 20:58:09 jdbc[0]:
/**/dbMeta3.getURL();
12-20 20:58:09 jdbc[0]:
/**/dbMeta3.getTables(null, "", null, new String[]{"TABLE", "VIEW"});
...
```

To filter the Java source code, use the `ConvertTraceFile` tool as follows:


要过滤 Java 源代码，请使用 `ConvertTraceFile` 工具，如下所示：

---

```shell
java -cp h2*.jar org.h2.tools.ConvertTraceFile
    -traceFile "~/test.trace.db" -javaClass "Test"
```

The generated file `Test.java` will contain the Java source code.
The generated source code may be too large to compile (the size of a Java method is limited).
If this is the case, the source code needs to be split in multiple methods.
The password is not listed in the trace file and therefore not included in the source code. 


生成的文件`Test.java` 将包含Java 源代码。
生成的源代码可能太大而无法编译（Java 方法的大小有限）。
如果是这种情况，则需要将源代码拆分为多种方法。
密码未在跟踪文件中列出，因此未包含在源代码中。

---
