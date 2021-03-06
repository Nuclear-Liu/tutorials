## Database URL Overview *数据库 URL 概述*

This database supports multiple connection modes and connection settings.
This is achieved using different database URLs.
Settings in the URLs are not case sensitive.


该数据库支持多种连接方式和连接设置。
这是使用不同的数据库 URL 实现的。
URL 中的设置不区分大小写。

---

| Topic| URL Format and Examples |
| ---- | ---- |
| [Embedded (local) connection]() | jdbc:h2:\[file:\]\[<path>\]<databaseName> jdbc:h2:~/test jdbc:h2:file:/data/sample jdbc:h2:file:C:/data/sample (Windows only) |
| [In-memory (private)]() | jdbc:h2:mem: |
| [In-memory (named)]() | jdbc:h2:mem:<databaseName> jdbc:h2:mem:test_mem  |
| [Server mode (remote connections) using TCP/IP]() | jdbc:h2:tcp://<server>\[:<port>\]/\[<path>\]<databaseName> jdbc:h2:tcp://localhost/~/test jdbc:h2:tcp://dbserv:8084/~/sample jdbc:h2:tcp://localhost/mem:test |
| [Server mode (remote connections) using TLS]() | jdbc:h2:ssl://<server>\[:<port>\]/\[<path>\]<databaseName> jdbc:h2:ssl://localhost:8085/~/sample;  |
| [Using encrypted files]() | jdbc:h2:<url>;CIPHER=AES jdbc:h2:ssl://localhost/~/test;CIPHER=AES jdbc:h2:file:~/secure;CIPHER=AES |
| [File locking methods]() | jdbc:h2:<url>;FILE_LOCK={FILE|SOCKET|NO} jdbc:h2:file:~/private;CIPHER=AES;FILE_LOCK=SOCKET |
| [Only open if it already exists]() | jdbc:h2:<url>;IFEXISTS=TRUE jdbc:h2:file:~/sample;IFEXISTS=TRUE |
| [Don't close the database when the VM exits]() | jdbc:h2:<url>;DB_CLOSE_ON_EXIT=FALSE |
| [Execute SQL on connection]() | jdbc:h2:<url>;INIT=RUNSCRIPT FROM '~/create.sql' jdbc:h2:file:~/sample;INIT=RUNSCRIPT FROM '~/create.sql'\;RUNSCRIPT FROM '~/populate.sql' |
| [User name and/or password]() | jdbc:h2:<url>\[;USER=<username>\]\[;PASSWORD=<value>\] jdbc:h2:file:~/sample;USER=sa;PASSWORD=123 |
| [Debug trace settings]() | jdbc:h2:<url>;TRACE_LEVEL_FILE=<level 0..3> jdbc:h2:file:~/sample;TRACE_LEVEL_FILE=3 |
| [Ignore unknown settings]() | jdbc:h2:<url>;IGNORE_UNKNOWN_SETTINGS=TRUE |
| [Custom file access mode]() | jdbc:h2:<url>;ACCESS_MODE_DATA=rws |
| [Database in a zip file]() | jdbc:h2:zip:<zipFileName>!/<databaseName> jdbc:h2:zip:~/db.zip!/test  |
| [Compatibility mode]() | jdbc:h2:<url>;MODE=<databaseType> jdbc:h2:~/test;MODE=MYSQL;DATABASE_TO_LOWER=TRUE  |
| [Auto-reconnect]() | jdbc:h2:<url>;AUTO_RECONNECT=TRUE jdbc:h2:tcp://localhost/~/test;AUTO_RECONNECT=TRUE  |
| [Automatic mixed mode]() |  	jdbc:h2:<url>;AUTO_SERVER=TRUE jdbc:h2:~/test;AUTO_SERVER=TRUE  |
| [Page size]() | jdbc:h2:<url>;PAGE_SIZE=512 |
| [Changing other settings]() | jdbc:h2:<url>;<setting>=<value>\[;<setting>=<value>...\] jdbc:h2:file:~/sample;TRACE_LEVEL_SYSTEM_OUT=3 |


| 主题 | URL 格式和示例 |
| ---- | ---- |
| [Embedded (local) connection]() | jdbc:h2:\[file:\]\[<path>\]<databaseName> jdbc:h2:~/test jdbc:h2:file:/data/sample jdbc:h2:file:C:/data/sample (Windows only) |
| [In-memory (private)]() | jdbc:h2:mem: |
| [In-memory (named)]() | jdbc:h2:mem:<databaseName> jdbc:h2:mem:test_mem  |
| [Server mode (remote connections) using TCP/IP]() | jdbc:h2:tcp://<server>\[:<port>\]/\[<path>\]<databaseName> jdbc:h2:tcp://localhost/~/test jdbc:h2:tcp://dbserv:8084/~/sample jdbc:h2:tcp://localhost/mem:test |
| [Server mode (remote connections) using TLS]() | jdbc:h2:ssl://<server>\[:<port>\]/\[<path>\]<databaseName> jdbc:h2:ssl://localhost:8085/~/sample;  |
| [Using encrypted files]() | jdbc:h2:<url>;CIPHER=AES jdbc:h2:ssl://localhost/~/test;CIPHER=AES jdbc:h2:file:~/secure;CIPHER=AES |
| [File locking methods]() | jdbc:h2:<url>;FILE_LOCK={FILE|SOCKET|NO} jdbc:h2:file:~/private;CIPHER=AES;FILE_LOCK=SOCKET |
| [Only open if it already exists]() | jdbc:h2:<url>;IFEXISTS=TRUE jdbc:h2:file:~/sample;IFEXISTS=TRUE |
| [Don't close the database when the VM exits]() | jdbc:h2:<url>;DB_CLOSE_ON_EXIT=FALSE |
| [Execute SQL on connection]() | jdbc:h2:<url>;INIT=RUNSCRIPT FROM '~/create.sql' jdbc:h2:file:~/sample;INIT=RUNSCRIPT FROM '~/create.sql'\;RUNSCRIPT FROM '~/populate.sql' |
| [User name and/or password]() | jdbc:h2:<url>\[;USER=<username>\]\[;PASSWORD=<value>\] jdbc:h2:file:~/sample;USER=sa;PASSWORD=123 |
| [Debug trace settings]() | jdbc:h2:<url>;TRACE_LEVEL_FILE=<level 0..3> jdbc:h2:file:~/sample;TRACE_LEVEL_FILE=3 |
| [Ignore unknown settings]() | jdbc:h2:<url>;IGNORE_UNKNOWN_SETTINGS=TRUE |
| [Custom file access mode]() | jdbc:h2:<url>;ACCESS_MODE_DATA=rws |
| [Database in a zip file]() | jdbc:h2:zip:<zipFileName>!/<databaseName> jdbc:h2:zip:~/db.zip!/test  |
| [Compatibility mode]() | jdbc:h2:<url>;MODE=<databaseType> jdbc:h2:~/test;MODE=MYSQL;DATABASE_TO_LOWER=TRUE  |
| [Auto-reconnect]() | jdbc:h2:<url>;AUTO_RECONNECT=TRUE jdbc:h2:tcp://localhost/~/test;AUTO_RECONNECT=TRUE  |
| [Automatic mixed mode]() |  	jdbc:h2:<url>;AUTO_SERVER=TRUE jdbc:h2:~/test;AUTO_SERVER=TRUE  |
| [Page size]() | jdbc:h2:<url>;PAGE_SIZE=512 |
| [Changing other settings]() | jdbc:h2:<url>;<setting>=<value>\[;<setting>=<value>...\] jdbc:h2:file:~/sample;TRACE_LEVEL_SYSTEM_OUT=3 |

---
