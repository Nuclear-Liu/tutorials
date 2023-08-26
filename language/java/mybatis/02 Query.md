# 执行过程

> **二级缓存** => **一级缓存** => **数据库查询**
> 
> * 二级缓存作用域(`CachingExecutor`)： 进程级别
> * 一级缓存作用域(`BaseExecutor`#`LocalCache`)： 会话(session)级别

使用接口映射代理的前提：
1. 接口名称必须与映射文件名称一致
2. 映射文件 namespace 必须是接口的全类路径
3. 接口声明的方法名称必须与映射文件中的 select/insert/update/delete 标签 id 属性一致
