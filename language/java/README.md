# Java 

* [Java Language Updates](https://docs.oracle.com/en/java/javase/20/language/java-language-changes.html#GUID-6459681C-6881-45D8-B0DB-395D1BD6DB9B)

## Remote JVM Debug

Host: 远程主机IP
Port: 远程端口默认 5005

jdk9+ : `-agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=*:5005`
jdk5~8: `-agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=5005`
jdk1.4: `-Xdebug -Xrunjdwp:transport=dt_socket,server=y,suspend=n,address=5005`


如果代码与程序不一致，断点将以行号为准；