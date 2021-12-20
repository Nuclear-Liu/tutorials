## Best Practices _最佳实践_

1. Use one database instance per developer
2. Good setup don't need cleanup!
3. Use multiple small datasets
4. Perform setup of stale data once for entire test class or test suite
5. Connection management strategies


1. 每位开发人员使用一个数据库实例
2. 好的设置不需要清理！
3. 使用多个小数据集
4. 为整个测试类或测试套件执行一次陈旧数据设置
5. 连接管理策略

---

## Use one database instance per developer _每位开发人员使用一个数据库实例_

Testing can be simplified if you can get your database in a known state before a test is run. 
A database should only be used for one test at a time; otherwise the database state cannot be guarantied.


如果您可以在运行测试之前使数据库处于已知状态，则可以简化测试。
一个数据库一次只能用于一个测试；否则无法保证数据库状态。

---

So multiple developers working on the same project should have their own database instance to prevent data corruption. 
This also simplifies database cleanup, as you don't necessarily need needs to revert it to its initial state.


所以在同一个项目上工作的多个开发人员应该有自己的数据库实例，以防止数据损坏。
这也简化了数据库清理，因为您不一定需要将其恢复到其初始状态。

---

## Good setup don't need cleanup! _好的设置不需要清理！_

You should always avoid creating tests that depends on results of preceding tests; thankfully this is the main purpose of DbUnit.


您应该始终避免创建依赖于先前测试结果的测试；幸运的是，这是 DbUnit 的主要目的。

---

Don't be afraid to leave your trace after a test; principally if you are using one database instance per developer. 
If you always put your database in a known state before a test execution, you usually don't need to clean it up.
This simplifies your tests maintenance and reduces the overhead taken by the cleanup procedure. 
And sometimes, this is very helpful to manually verify the database state after executing a test that fails.


不要害怕在测试后留下痕迹；主要是如果您为每个开发人员使用一个数据库实例。
如果在执行测试之前总是将数据库置于已知状态，则通常不需要清理它。
这简化了您的测试维护并减少了清理过程的开销。
有时，这对于在执行失败的测试后手动验证数据库状态非常有帮助。

---

## Use multiple small datasets _使用多个小数据集_

Most of your tests do not require the entire database to be re-initialized. 
So, instead of putting your entire database data in one large dataset, try to break it into many smaller chunks.


大多数测试不需要重新初始化整个数据库。
因此，与其将整个数据库数据放在一个大数据集中，不如尝试将其分解为许多更小的块。

---

These chunks could roughly corresponding to logical units, or components. 
This reduces the overhead caused by initializing your database for each test. 
This also facilitates team development since many developers working on different components can modify datasets independently.


这些块可以粗略地对应于逻辑单元或组件。
这减少了为每个测试初始化数据库所造成的开销。
这也有利于团队开发，因为在不同组件上工作的许多开发人员可以独立修改数据集。

---

For integrated testing, you can still use the `CompositeDataSet` class to logically combine multiple datasets into a large one at run time.


对于集成测试，您仍然可以使用 `CompositeDataSet` 类在运行时逻辑地将多个数据集组合成一个大数据集。

---

## Perform setup of stale data once for entire test class or test suite _为整个测试类或测试套件执行一次陈旧数据设置_

If several tests are using the same read-only data, this data could be initialized once for an entire test class or test suite. 
You need to be cautious and ensure you never modify this data. 
This can reduce the time required to run your tests but also introduces more risk.


如果多个测试使用相同的只读数据，则可以为整个测试类或测试套件初始化该数据一次。
您需要谨慎并确保永远不会修改这些数据。
这可以减少运行测试所需的时间，但也会带来更多风险。

---

## Connection management strategies _连接管理策略_

Here are the recommended connection management strategies depending whether you test from a remote client or an in-container strategy:


以下是推荐的连接管理策略，具体取决于您是从远程客户端测试还是从容器内策略进行测试：

---

### Remote client with DatabaseTestCase _带有 DatabaseTestCase 的远程客户端_

You should try to reuse the same connection for the entire test suite to reduce the overhead of creating a new connection for each test. 
Since version 1.1, `DatabaseTestCase` is closing every connection in `setUp()` and `tearDown()`. 
Override the `closeConnection()` method with an empty body to modify this behavior.


您应该尝试为整个测试套件重用相同的连接，以减少为每个测试创建新连接的开销。
从 1.1 版开始，`DatabaseTestCase` 关闭了 `setUp()` 和 `tearDown()` 中的每个连接。
使用空主体覆盖 `closeConnection()` 方法以修改此行为。

---

### In-container with Cactus or JUnitEE _带有 Cactus 或 JUnitEE 的容器内_

If you use the in-container strategy you should use the `DatabaseDataSourceConnection` class to access the `DataSource` you configured for your application server. 
JDBC connections are requested on demand from the `DataSource`. 
So you can rely on the built-in connection pooling capability of your application server to achieve good performance. 


如果使用容器内策略，则应使用 `DatabaseDataSourceConnection` 类访问为应用程序服务器配置的 `DataSource` 。
JDBC连接是按需从 `DataSource` 请求的。
因此，您可以依靠应用程序服务器的内置连接池功能来实现良好的性能。

---

`IDatabaseConnection connection = new DatabaseDataSourceConnection(new InitialContext(), "jdbc/myDataSource");`

An alternative since version 2.2 is to subclass `JndiBasedDBTestCase` and specify the JNDI lookup name. 


从 2.2 版开始，另一种方法是将 `JndiBasedDBTestCase` 子类化并指定 JNDI 查找名称。

---

```text
public class MyJNDIDatabaseTest extends JndiBasedDBTestCase {
   protected String getLookupName(){
      return "jdbc/myDatasource";
   }
   ...
}
```

You may also use `JndiDatabaseTester` if you can't subclass `JndiBasedDBTestCase`. 


如果你不能子类化`JndiBasedDBTestCase` ，你也可以使用`JndiDatabaseTester` 。

---
