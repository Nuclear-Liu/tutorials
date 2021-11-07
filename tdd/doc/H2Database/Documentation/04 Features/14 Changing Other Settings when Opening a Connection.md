## Changing Other Settings when Opening a Connection *打开连接时更改其他设置*

In addition to the settings already described, other database settings can be passed in the database URL.
Adding `;setting=value` at the end of a database URL is the same as executing the statement `SET setting value` just after connecting.
For a list of supported settings, see [SQL Grammar]() or the [DbSettings]() javadoc. 


除了已经描述的设置之外，还可以在数据库 URL 中传递其他数据库设置。
在数据库 URL 末尾添加 `;setting=value` 与在连接后立即执行语句 `SET setting value` 相同。
有关受支持设置的列表，请参阅 [SQL Grammar]() 或 [DbSettings]() javadoc。
