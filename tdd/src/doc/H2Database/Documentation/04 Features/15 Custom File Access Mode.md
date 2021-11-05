## Custom File Access Mode *自定义文件访问模式*

Usually, the database opens the database file with the access mode `rw`, meaning read-write (except for read only databases, where the mode r is used).
To open a database in read-only mode if the database file is not read-only, use `ACCESS_MODE_DATA=r`.
Also supported are `rws` and `rwd`.
This setting must be specified in the database URL:


通常，数据库以访问模式`rw`打开数据库文件，意思是读写（只读数据库除外，其中使用模式 r ）。
如果数据库文件不是只读的，要以只读模式打开数据库，请使用 `ACCESS_MODE_DATA=r` 。
还支持 `rws` 和 `rwd` 。
必须在数据库 URL 中指定此设置：

---

```
String url = "jdbc:h2:~/test;ACCESS_MODE_DATA=rws";
```

For more information see [Durability Problems]().
On many operating systems the access mode rws does not guarantee that the data is written to the disk. 


有关更多信息，请参阅 [Durability Problems]() 。
在许多操作系统上，访问模式 rws 不保证将数据写入磁盘。

---
