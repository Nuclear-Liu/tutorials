## Database File Layout *数据库文件布局*

The following files are created for persistent databases when the default MVStore engine is used:


使用默认 MVStore 引擎时，将为持久性数据库创建以下文件：

---

| File Name | Description | Number of Files |
| ---- | ---- | ---- |
| test.mv.db  | Database file. Contains the transaction log, indexes, and data for all tables. Format: `<database>.mv.db` | 1 per database |
| test.newFile | Temporary file for database compaction. Contains the new MVStore file. Format: `<database>.newFile` | 0 or 1 per database |
| test.tempFile | Temporary file for database compaction. Contains the temporary MVStore file. Format: `<database>.tempFile` | 0 or 1 per database |


| File Name | Description | Number of Files |
| ---- | ---- | ---- |
| test.mv.db  | 数据库文件。包含所有表的事务日志、索引和数据。格式： `<database>.mv.db` | 1 per database |
| test.newFile | 用于数据库压缩的临时文件。包含新的 MVStore 文件。格式： `<database>.newFile` | 0 or 1 per database |
| test.tempFile | 用于数据库压缩的临时文件。包含临时 MVStore 文件。格式： `<database>.tempFile` | 0 or 1 per database |

---

The following file is created for persistent databases when PageStore engine is used:


使用 PageStore 引擎时为持久数据库创建以下文件：

---

| File Name | Description | Number of Files |
| ---- | ---- | ---- |
| test.h2.db | Database file. Contains the transaction log, indexes, and data for all tables. Format: `<database>.h2.db` | 1 per database |


| File Name | Description | Number of Files |
| ---- | ---- | ---- |
| test.h2.db | 数据库文件。包含所有表的事务日志、索引和数据。格式： `<database>.h2.db` | 1 per database |

---

The following files are created for persistent databases by both MVStore and PageStore engines:


MVStore 和 PageStore 引擎为持久数据库创建了以下文件：

---

| File Name | Description | Number of Files |
| ---- | ---- | ---- |
| test.lock.db | Database lock file. Automatically (re-)created while the database is in use. Format: `<database>.lock.db` | 1 per database (only if in use)  |
| test.trace.db  | Trace file (if the trace option is enabled). Contains trace information. Format: `<database>.trace.db` Renamed to `<database>.trace.db.old` if too big.  | 0 or 1 per database |
| test.123.temp.db | Temporary file. Contains a temporary blob or a large result set. Format: `<database>.<id>.temp.db` | 1 per object |


| File Name | Description | Number of Files |
| ---- | ---- | ---- |
| test.lock.db | 数据库锁定文件。在使用数据库时自动（重新）创建。格式： `<database>.lock.db` | 1 per database (only if in use)  |
| test.trace.db  | 跟踪文件（如果启用了跟踪选项）。包含跟踪信息。格式： `<database>.trace.db` 如果太大，重命名为 `<database>.trace.db.old`。  | 0 or 1 per database |
| test.123.temp.db | 临时文件。包含临时 blob 或大型结果集。格式： `<database>.<id>.temp.db` | 1 per object |

---

Legacy PageStore databases from old versions of H2 can have the following additional files:


旧版 H2 中的旧版 PageStore 数据库可以包含以下附加文件：

---

| File Name | Description | Number of Files |
| ---- | ---- | ---- |
| test.lobs.db/* | Directory containing one file for each BLOB or CLOB value larger than a certain size. Format: `<id>.t<tableId>.lob.db` | 1 per large object |


| File Name | Description | Number of Files |
| ---- | ---- | ---- |
| test.lobs.db/* | 对于大于特定大小的每个 BLOB 或 CLOB 值，包含一个文件的目录。格式： `<id>.t<tableId>.lob.db` | 1 per large object |

---

### Moving and Renaming Database Files *移动和重命名数据库文件*

Database name and location are not stored inside the database files.


数据库名称和位置不存储在数据库文件中。

---

While a database is closed, the files can be moved to another directory, and they can be renamed as well (as long as all files of the same database start with the same name and the respective extensions are unchanged).


当数据库关闭时，文件可以移动到另一个目录，也可以重命名（只要同一个数据库的所有文件都以相同的名称开头并且各自的扩展名不变）。

---

As there is no platform specific data in the files, they can be moved to other operating systems without problems.


由于文件中没有特定于平台的数据，因此可以毫无问题地将它们移动到其他操作系统。

---

### Backup *备份*

When the database is closed, it is possible to backup the database files.


当数据库关闭时，可以备份数据库文件。

---

To backup data while the database is running, the SQL commands `SCRIPT` and `BACKUP` can be used. 


要在数据库运行时备份数据，可以使用 SQL 命令 `SCRIPT` 和 `BACKUP` 。

---
