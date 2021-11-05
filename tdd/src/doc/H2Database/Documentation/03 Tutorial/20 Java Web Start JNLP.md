## Java Web Start / JNLP *Java Web Start / JNLP*

When using Java Web Start / JNLP (Java Network Launch Protocol), permissions tags must be set in the .jnlp file, and the application .jar file must be signed.
Otherwise, when trying to write to the file system, the following exception will occur: `java.security.AccessControlException`: access denied (`java.io.FilePermission ... read`).
Example permission tags:


使用 Java Web Start / JNLP（Java 网络启动协议）时，必须在 .jnlp 文件中设置权限标签，并且必须对应用程序 .jar 文件进行签名。
否则，在尝试写入文件系统时，会出现以下异常： `java.security.AccessControlException` : access denied ( `java.io.FilePermission ... read` )。
示例权限标签：

----

```xml
<security>
    <all-permissions/>
</security>
```
