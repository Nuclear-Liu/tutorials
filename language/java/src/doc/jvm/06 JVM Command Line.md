# JVM 命令行

[JVM 命令行参数参考](https://docs.oracle.com/javase/8/docs/technotes/tools/unix/java.html)

## Hotspot 参数分类

* `-` : 标准参数开头；所有的 Hotspot 都支持；
* `-X` : 非标准参数开头；特定版本 Hotspot 支持；
* `-XX` : 不稳定参数开头；后期版本可能取消；

`-XX:+PrintGC`
`-XX:+PrintGCDetails`
`-XX:+PrintGCStamps`
`-XX:+PrintGCCauses`
