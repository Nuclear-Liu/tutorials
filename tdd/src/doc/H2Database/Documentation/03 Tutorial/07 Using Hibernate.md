## Using Hibernate *使用 Hibernate*

This database supports Hibernate version 3.1 and newer.
You can use the HSQLDB Dialect, or the native H2 Dialect.


该数据库支持 Hibernate 3.1 及更新版本。
您可以使用 HSQLDB 方言或本机 H2 方言。

----

When using Hibernate, try to use the `H2Dialect` if possible.
When using the `H2Dialect`, compatibility modes such as `MODE=MySQL` are not supported.
When using such a compatibility mode, use the Hibernate dialect for the corresponding database instead of the `H2Dialect`;
but please note H2 does not support all features of all databases.


使用 Hibernate 时，如果可能，请尝试使用“H2Dialect”。
使用`H2Dialect`时，不支持`MODE=MySQL`等兼容模式。
使用这种兼容模式时，对应数据库使用 Hibernate 方言而不是 `H2Dialect` ；
但请注意 H2 并不支持所有数据库的所有功能。

----
