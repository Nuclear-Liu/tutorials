## Pluggable or User-Defined Tables *可插拔或用户定义的表*

For situations where you need to expose other data-sources to the SQL engine as a table, there are "pluggable tables".
For some examples, have a look at the code in `org.h2.test.db.TestTableEngines`.


对于需要将其他数据源作为表公开给 SQL 引擎的情况，有“可插入表”。
对于一些示例，请查看 `org.h2.test.db.TestTableEngines` 中的代码。

---

In order to create your own TableEngine, you need to implement the `org.h2.api.TableEngine` interface e.g. something like this:


为了创建你自己的 TableEngine，你需要实现 `org.h2.api.TableEngine` 接口，例如像这样：

---

```
package acme;
public static class MyTableEngine implements org.h2.api.TableEngine {

    private static class MyTable extends org.h2.table.TableBase {
        .. rather a lot of code here...
    }

    public EndlessTable createTable(CreateTableData data) {
        return new EndlessTable(data);
    }
}
```

and then create the table from SQL like this:


然后像这样从 SQL 创建表：

---

```sql
CREATE TABLE TEST(ID INT, NAME VARCHAR)
    ENGINE "acme.MyTableEngine";
```

It is also possible to pass in parameters to the table engine, like so:


也可以将参数传递给表引擎，如下所示：

---

```sql
CREATE TABLE TEST(ID INT, NAME VARCHAR) ENGINE "acme.MyTableEngine" WITH "param1", "param2";
```

In which case the parameters are passed down in the tableEngineParams field of the CreateTableData object.


在这种情况下，参数在 CreateTableData 对象的 tableEngineParams 字段中传递。

---

It is also possible to specify default table engine params on schema creation:


也可以在模式创建时指定默认表引擎参数：

---

```sql
CREATE SCHEMA TEST_SCHEMA WITH "param1", "param2";
```

Params from the schema are used when CREATE TABLE issued on this schema does not have its own engine params specified. 


当在此模式上发出的 CREATE TABLE 没有指定自己的引擎参数时，将使用模式中的参数。

---
