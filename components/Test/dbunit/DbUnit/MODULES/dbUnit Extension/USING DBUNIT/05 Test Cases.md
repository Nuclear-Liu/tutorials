## Overview _概述_

dbUnit has multiple base test case classes - use these classes as the basis for your tests. 
All extend `DBTestCase` and the top level `DatabaseTestCase`.


dbUnit 有多个基本测试用例类 - 使用这些类作为测试的基础。
所有扩展 `DBTestCase` 和顶级 `DatabaseTestCase` 。

---

Use the submenus to learn about each of them.


使用子菜单了解每一个。

---

## General Notes _一般注意事项_

1. For additional examples, refer to the ITs for the specific test case.
2. To change the setup or teardown operation (e.g. change the teardown to `org.dbunit.operation.DatabaseOperation.DELETE_ALL`), set the setUpOperation or tearDownOperation property on the databaseTester.
3. To set `DatabaseConfig` features/properties, one way is to extend the test case class and override the `setUpDatabaseConfig(DatabaseConfig config)` method from `DatabaseTestCase`.


1. 有关其他示例，请参阅特定测试用例的 IT。
2. 要更改设置或拆卸操作（例如，将拆卸更改为 `org.dbunit.operation.DatabaseOperation.DELETE_ALL` ），请在 databaseTester 上设置 setUpOperation 或 tearDownOperation 属性。
3. 要设置 `DatabaseConfig` 特性属性，一种方法是扩展测试用例类并覆盖 `DatabaseTestCase` 中的 `setUpDatabaseConfig(DatabaseConfig config)` 方法。

---
