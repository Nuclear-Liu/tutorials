## Updatable Views *可更新的视图*

By default, views are not updatable.
To make a view updatable, use an "instead of" trigger as follows:


默认情况下，视图不可更新。
要使视图可更新，请使用 "instead of" 触发器，如下所示：

---

```sql
CREATE TRIGGER TRIGGER_NAME
INSTEAD OF INSERT, UPDATE, DELETE
ON VIEW_NAME
FOR EACH ROW CALL "com.acme.TriggerClassName";
```

Update the base table(s) within the trigger as required.
For details, see the sample application `org.h2.samples.UpdatableView`.


根据需要更新触发器内的基表。
有关详细信息，请参阅示例应用程序 `org.h2.samples.UpdatableView` 。

---
