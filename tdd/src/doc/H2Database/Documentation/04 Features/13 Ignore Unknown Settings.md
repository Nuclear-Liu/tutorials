## Ignore Unknown Settings *忽略未知设置*

Some applications (for example OpenOffice.org Base) pass some additional parameters when connecting to the database.
Why those parameters are passed is unknown.
The parameters `PREFERDOSLIKELINEENDS` and `IGNOREDRIVERPRIVILEGES` are such examples; they are simply ignored to improve the compatibility with OpenOffice.org.
If an application passes other parameters when connecting to the database, usually the database throws an exception saying the parameter is not supported.
It is possible to ignored such parameters by adding `;IGNORE_UNKNOWN_SETTINGS=TRUE` to the database URL. 


某些应用程序（例如 OpenOffice.org Base）在连接到数据库时会传递一些附加参数。
为什么传递这些参数是未知的。
参数 `PREFERDOSLIKELINEENDS` 和 `IGNOREDRIVERPRIVILEGES` 就是这样的例子；它们只是被忽略以提高与 OpenOffice.org 的兼容性。
如果应用程序在连接数据库时传递了其他参数，通常数据库会抛出异常，表示该参数不受支持。
通过向数据库 URL 添加 `;IGNORE_UNKNOWN_SETTINGS=TRUE` 可以忽略这些参数。

---
