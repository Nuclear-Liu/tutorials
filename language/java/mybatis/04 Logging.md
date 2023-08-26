# Logging 日志

> 日志模块的目的：屏蔽各类日志框架带来的不一致。

通过适配器模式适配不同的日志模块：每一个具体日志框架提供一个 MyBatis 自定义日志接口(`org.apache.ibatis.logging.Log`)的适配类。

## 日志配置加载过程


* SQL 查询语句日志来自于哪里？

`InvocationHandler`: 代理，织入日志逻辑。

