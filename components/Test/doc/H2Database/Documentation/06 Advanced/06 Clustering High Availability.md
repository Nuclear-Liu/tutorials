## Clustering / High Availability *集群高可用性*

This database supports a simple clustering / high availability mechanism.
The architecture is: two database servers run on two different computers, and on both computers is a copy of the same database.
If both servers run, each database operation is executed on both computers.
If one server fails (power, hardware or network failure), the other server can still continue to work.
From this point on, the operations will be executed only on one server until the other server is back up.


该数据库支持简单的集群/高可用机制。
架构是：两台数据库服务器运行在两台不同的计算机上，两台计算机上都是同一个数据库的副本。
如果两台服务器都运行，则每个数据库操作都会在两台计算机上执行。
如果一台服务器出现故障（电源、硬件或网络故障），另一台服务器仍然可以继续工作。
从这时起，操作将只在一台服务器上执行，直到另一台服务器备份。

---

Clustering can only be used in the server mode (the embedded mode does not support clustering).
The cluster can be re-created using the `CreateCluster` tool without stopping the remaining server.
Applications that are still connected are automatically disconnected, however when appending `;AUTO_RECONNECT=TRUE`, they will recover from that.


集群只能在服务器模式下使用（嵌入式模式不支持集群）。
可以使用 `CreateCluster` 工具重新创建集群，而无需停止剩余的服务器。
仍然连接的应用程序会自动断开连接，但是当附加 `;AUTO_RECONNECT=TRUE` 时，它们会从中恢复。

---

To initialize the cluster, use the following steps:


要初始化集群，请使用以下步骤：

---

* Create a database
* Use the `CreateCluster` tool to copy the database to another location and initialize the clustering.
  Afterwards, you have two databases containing the same data.
* Start two servers (one for each copy of the database)
* You are now ready to connect to the databases with the client application(s)


* 创建数据库
* 使用 `CreateCluster` 工具将数据库复制到另一个位置并初始化集群。
  之后，您有两个包含相同数据的数据库。
* 启动两台服务器（每个数据库副本一台）
* 您现在已准备好使用客户端应用程序连接到数据库

---

### Using the CreateCluster Tool *使用 CreateCluster 工具*

To understand how clustering works, please try out the following example.
In this example, the two databases reside on the same computer, but usually, the databases will be on different servers.


要了解聚类的工作原理，请尝试以下示例。
在此示例中，两个数据库位于同一台计算机上，但通常数据库将位于不同的服务器上。

---

* Create two directories: `server1`, `server2`.
  Each directory will simulate a directory on a computer.

* Start a TCP server pointing to the first directory.
  You can do this using the command line:

    ```shell
    java org.h2.tools.Server
        -tcp -tcpPort 9101
        -baseDir server1
    ```

* Start a second TCP server pointing to the second directory.
  This will simulate a server running on a second (redundant) computer.
  You can do this using the command line:

    ```shell
    java org.h2.tools.Server
        -tcp -tcpPort 9102
        -baseDir server2
    ```

* Use the `CreateCluster` tool to initialize clustering.
  This will automatically create a new, empty database if it does not exist.
  Run the tool on the command line:

  ```shell
  java org.h2.tools.CreateCluster
      -urlSource jdbc:h2:tcp://localhost:9101/~/test
      -urlTarget jdbc:h2:tcp://localhost:9102/~/test
      -user sa
      -serverList localhost:9101,localhost:9102
  ```

* You can now connect to the databases using an application or the H2 Console using the JDBC URL `jdbc:h2:tcp://localhost:9101,localhost:9102/~/test`

* If you stop a server (by killing the process), you will notice that the other machine continues to work, and therefore the database is still accessible.

* To restore the cluster, you first need to delete the database that failed, then restart the server that was stopped, and re-run the `CreateCluster` tool.


* 创建两个目录：`server1`、`server2`。
  每个目录将模拟计算机上的一个目录。
  
* 启动一个指向第一个目录的 TCP 服务器。
  您可以使用命令行执行此操作：
  
  ```shell
  java org.h2.tools.Server
      -tcp -tcpPort 9101
      -baseDir server1
  ```
  
* 启动指向第二个目录的第二个 TCP 服务器。
  这将模拟在第二台（冗余）计算机上运行的服务器。
  您可以使用命令行执行此操作：
  
  ```shell
  java org.h2.tools.Server
      -tcp -tcpPort 9102
      -baseDir server2
  ```
  
* 使用 `CreateCluster` 工具初始化集群。
  如果它不存在，这将自动创建一个新的空数据库。
  在命令行上运行该工具：
  
  ```shell
  java org.h2.tools.CreateCluster
      -urlSource jdbc:h2:tcp://localhost:9101/~/test
      -urlTarget jdbc:h2:tcp://localhost:9102/~/test
      -user sa
      -serverList localhost:9101,localhost:9102
  ```
  
* 您现在可以使用应用程序或使用 JDBC URL `jdbc:h2:tcp:localhost:9101,localhost:9102~test` 的 H2 控制台连接到数据库

* 如果您停止服务器（通过终止进程），您会注意到另一台机器继续工作，因此数据库仍然可以访问。

* 要恢复集群，首先需要删除失败的数据库，然后重新启动停止的服务器，并重新运行 `CreateCluster` 工具。

---

### Detect Which Cluster Instances are Running *检测哪些集群实例正在运行*

To find out which cluster nodes are currently running, execute the following SQL statement:


要找出当前正在运行的集群节点，请执行以下 SQL 语句：

---

```sql
SELECT VALUE FROM INFORMATION_SCHEMA.SETTINGS WHERE NAME='CLUSTER'
```

If the result is `''` (two single quotes), then the cluster mode is disabled.
Otherwise, the list of servers is returned, enclosed in single quote.
Example: `'server1:9191,server2:9191'`.


如果结果是 `''` （两个单引号），则集群模式被禁用。
否则，返回服务器列表，用单引号括起来。
示例：`'server1:9191,server2:9191'`。

---

It is also possible to get the list of servers by using `Connection.getClientInfo()` .


也可以使用 `Connection.getClientInfo()` 获取服务器列表。

---

The property list returned from `getClientInfo()` contains a numServers property that returns the number of servers that are in the connection list.
To get the actual servers, `getClientInfo()` also has properties `server0` .. `serverX`, where serverX is the number of servers minus 1.


`getClientInfo()` 返回的属性列表包含一个 numServers 属性，该属性返回连接列表中的服务器数量。
为了获得实际的服务器，`getClientInfo()` 也有属性 `server0` .. `serverX` ，其中 serverX 是服务器数量减 1。

---

Example: To get the 2nd server in the connection list one uses `getClientInfo('server1')`.
Note: The `serverX` property only returns IP addresses and ports and not hostnames.


示例：要获取连接列表中的第二个服务器，请使用 `getClientInfo('server1')` 。
注意： `serverX` 属性只返回 IP 地址和端口，而不返回主机名。

---

### Clustering Algorithm and Limitations *聚类算法和限制*

Read-only queries are only executed against the first cluster node, but all other statements are executed against all nodes.
There is currently no load balancing made to avoid problems with transactions.
The following functions may yield different results on different cluster nodes and must be executed with care: 
`UUID()`, `RANDOM_UUID()`, `SECURE_RAND()`, `SESSION_ID()`, `MEMORY_FREE()`, `MEMORY_USED()`, `CSVREAD()`, `CSVWRITE()`, `RAND()` [when not using a seed].
Those functions should not be used directly in modifying statements (for example `INSERT`, `UPDATE`, `MERGE`).
However, they can be used in read-only statements and the result can then be used for modifying statements.
Using auto-increment and identity columns is currently not supported.
Instead, sequence values need to be manually requested and then used to insert data (using two statements).


只读查询仅针对第一个集群节点执行，但所有其他语句针对所有节点执行。
目前没有进行负载平衡来避免事务问题。
以下函数在不同的集群节点上可能会产生不同的结果，必须小心执行：
`UUID()`、`RANDOM_UUID()`、`SECURE_RAND()`、`SESSION_ID()`、`MEMORY_FREE()`、`MEMORY_USED()`、`CSVREAD()`、`CSVWRITE()`、`RAND ()` [不使用种子时] 。
这些函数不应直接用于修改语句（例如 `INSERT`、 `UPDATE`、 `MERGE` ）。
但是，它们可用于只读语句，然后结果可用于修改语句。
当前不支持使用自动增量和标识列。
相反，序列值需要手动请求，然后用于插入数据（使用两个语句）。

---

When using the cluster modes, result sets are read fully in memory by the client, so that there is no problem if the server dies that executed the query.
Result sets must fit in memory on the client side.


使用集群模式时，结果集由客户端在内存中完全读取，因此如果执行查询的服务器死亡，则没有问题。
结果集必须适合客户端的内存。

---

The SQL statement `SET AUTOCOMMIT FALSE` is not supported in the cluster mode.
To disable autocommit, the method `Connection.setAutoCommit(false)` needs to be called.


集群模式不支持 SQL 语句 `SET AUTOCOMMIT FALSE`。
要禁用自动提交，需要调用方法 `Connection.setAutoCommit(false)` 。

---

It is possible that a transaction from one connection overtakes a transaction from a different connection.
Depending on the operations, this might result in different results, for example when conditionally incrementing a value in a row.


来自一个连接的事务可能会超过来自不同连接的事务。
根据操作的不同，这可能会导致不同的结果，例如在有条件地递增一行中的值时。

---
