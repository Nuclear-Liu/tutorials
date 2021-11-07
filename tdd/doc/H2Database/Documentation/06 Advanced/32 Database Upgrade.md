## Database Upgrade *数据库升级*

In version 1.2, H2 introduced a new file store implementation which is incompatible to the one used in versions < 1.2.
To automatically convert databases to the new file store, it is necessary to include an additional jar file.
The file can be found at [https://h2database.com/h2mig_pagestore_addon.jar]() .
If this file is in the classpath, every connect to an older database will result in a conversion process.


在 1.2 版本中，H2 引入了一种新的文件存储实现，该实现与版本 < 1.2 中使用的不兼容。
要将数据库自动转换为新的文件存储，需要包含一个额外的 jar 文件。
该文件可以在 [https://h2database.com/h2mig_pagestore_addon.jar]() 找到。
如果此文件在类路径中，则每次连接到旧数据库都会导致转换过程。

---

The conversion itself is done internally via `'script to'` and `'runscript from'`.
After the conversion process, the files will be renamed from


转换本身是通过 `'script to'` 和 `'runscript from'` 在内部完成的。
转换过程后，文件将从

---

* `dbName.data.db` to `dbName.data.db.backup`
* `dbName.index.db` to `dbName.index.db.backup`


* `dbName.data.db` 到 `dbName.data.db.backup`
* `dbName.index.db` 到 `dbName.index.db.backup`

---

by default. 
Also, the temporary script will be written to the database directory instead of a temporary directory.
Both defaults can be customized via


默认情况下。
此外，临时脚本将写入数据库目录而不是临时目录。
两个默认值都可以通过自定义

---

* `org.h2.upgrade.DbUpgrade.setDeleteOldDb(boolean)`
* `org.h2.upgrade.DbUpgrade.setScriptInTmpDir(boolean)`

prior opening a database connection.


在打开数据库连接之前。

---

Since version 1.2.140 it is possible to let the old h2 classes (v 1.2.128) connect to the database.
The automatic upgrade .jar file must be present, and the URL must start with `jdbc:h2v1_1`: (the JDBC driver class is `org.h2.upgrade.v1_1.Driver`).
If the database should automatically connect using the old version if a database with the old format exists (without upgrade), and use the new version otherwise, then append `;NO_UPGRADE=TRUE` to the database URL.
Please note the old driver did not process the system property `"h2.baseDir"` correctly, so that using this setting is not supported when upgrading.


从 1.2.140 版本开始，可以让旧的 h2 类（v 1.2.128）连接到数据库。
必须存在自动升级 .jar 文件，并且 URL 必须以 `jdbc:h2v1_1` 开头：（JDBC 驱动程序类是 `org.h2.upgrade.v1_1.Driver` ）。
如果存在旧格式的数据库（无需升级），则数据库应自动使用旧版本连接，否则使用新版本，然后将 `;NO_UPGRADE=TRUE` 附加到数据库 URL。
请注意，旧驱动程序没有正确处理系统属性 `"h2.baseDir"` ，因此升级时不支持使用此设置。

---
