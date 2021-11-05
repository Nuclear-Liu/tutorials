## Settings Read from System Properties *从系统属性读取的设置*

Some settings of the database can be set on the command line using `-DpropertyName=value`.
It is usually not required to change those settings manually.
The settings are case sensitive. 
Example:


可以使用`-DpropertyName=value`在命令行上设置数据库的某些设置。
通常不需要手动更改这些设置。
设置区分大小写。
例子：

---

```shell
java -Dh2.serverCachedObjects=256 org.h2.tools.Server
```

The current value of the settings can be read in the table `INFORMATION_SCHEMA.SETTINGS`.


设置的当前值可以在 `INFORMATION_SCHEMA.SETTINGS` 表中读取。

---

For a complete list of settings, see [SysProperties]().


有关设置的完整列表，请参阅 [SysProperties]()。

---
