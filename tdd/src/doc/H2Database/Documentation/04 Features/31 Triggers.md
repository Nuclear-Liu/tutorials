## Triggers *触发器*

This database supports Java triggers that are called before or after a row is updated, inserted or deleted.
Triggers can be used for complex consistency checks, or to update related data in the database.
It is also possible to use triggers to simulate materialized views.
For a complete sample application, see `src/test/org/h2/samples/TriggerSample.java`.
A Java trigger must implement the interface `org.h2.api.Trigger`.
The trigger class must be available in the classpath of the database engine (when using the server mode, it must be in the classpath of the server).


此数据库支持在更新、插入或删除行之前或之后调用的 Java 触发器。
触发器可用于复杂的一致性检查，或更新数据库中的相关数据。
也可以使用触发器来模拟物化视图。
有关完整的示例应用程序，请参阅 `srctestorgh2samplesTriggerSample.java` 。
Java 触发器必须实现接口 `org.h2.api.Trigger`。
触发器类必须在数据库引擎的类路径中（使用服务器模式时，必须在服务器的类路径中）。

---

```
import org.h2.api.Trigger;
...
public class TriggerSample implements Trigger {

    public void init(Connection conn, String schemaName, String triggerName,
            String tableName, boolean before, int type) {
        // initialize the trigger object is necessary
    }

    public void fire(Connection conn,
            Object[] oldRow, Object[] newRow)
            throws SQLException {
        // the trigger is fired
    }

    public void close() {
        // the database is closed
    }

    public void remove() {
        // the trigger was dropped
    }

}
```

The connection can be used to query or update data in other tables.
The trigger then needs to be defined in the database:


该连接可用于查询或更新其他表中的数据。
然后需要在数据库中定义触发器：

---

```sql
CREATE TRIGGER INV_INS AFTER INSERT ON INVOICE
    FOR EACH ROW CALL "org.h2.samples.TriggerSample"
```

The trigger can be used to veto a change by throwing a SQLException.


触发器可用于通过抛出 SQLException 来否决更改。

---

As an alternative to implementing the Trigger interface, an application can extend the abstract class `org.h2.tools.TriggerAdapter`.
This will allows to use the `ResultSet` interface within trigger implementations.
In this case, only the `fire` method needs to be implemented:


作为实现 Trigger 接口的替代方案，应用程序可以扩展抽象类 `org.h2.tools.TriggerAdapter`。
这将允许在触发器实现中使用 `ResultSet` 接口。
在这种情况下，只需要实现 `fire` 方法：

---

```
import org.h2.tools.TriggerAdapter;
...
public class TriggerSample extends TriggerAdapter {

    public void fire(Connection conn, ResultSet oldRow, ResultSet newRow)
            throws SQLException {
        // the trigger is fired
    }

}
```
