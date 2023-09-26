# Cache 缓存

> 主要目的：**降低数据源访问压力**
> 
> 对于单机缓存在分布式场景下会出现部分节点未更新缓存而导致的读写不一致问题(二级缓存)。

## MyBatis 缓存

### 缓存类型：

* 一级缓存(会话级别)
* 二级缓存(进程级别)
* 三级缓存(分布式缓存)

缓存设计：

* 一级缓存(内存存储)
* 二级缓存(内存存储)
* 三级缓存(NOSQL等第三方独立容器)

MyBatis Cache 设计：

对于缓存的使用流程：
1. 查询缓存：首先**获取锁**获取数据库查询权
   1. 如果缓存未命中：**不释放锁**，返回 `null` 表示缓存未命中，执行第 2 步：查询数据库；
   2. 如果缓存命中：**释放锁**，返回 缓存中的对象，执行完成；
2. 查询数据库
3. 填充缓存，释放锁

> 问题： 
> 1. 如果查询数据库结果未空

所以，当 putObject 时，不需要获取锁，而只需要释放锁；


* `org.apache.ibatis.cache`: 模块路径
  * `Cache`[Interface]:
  * `impl.*`: MyBatis 对 `Cache` 接口的默认实现
    * `PerpetualCache`: 基于 `HashMap` 的默认二级缓存简单实现
      * 问题：
        1. 没有对使用空间最大内存空间做约束
        2. 没有合适的淘汰策略
        3. 没有排序策略
        4. 线程安全
      * 解决一：使用**代理模式**，对缓存实现做**增强**
        1. 缓存数据淘汰机制
        2. 缓存数据存放策略
        3. 线程安全问题
        4. 容量监控
        
        可能存在多种特性组合，不能用统一的代理实现所有的特性组合。
      
      * 解决二：使用装饰者模式，对缓存实现做*选择性***增强**
  * `decorators.*`: MyBatis 为默认缓存实现提供的**装饰器**
    * [`BlockingCache`]()

### 自定义缓存

参考： `org.mybatis.caches.redis.RedisCache`

1. 实现 `Cache` 接口
2. 在 Mapper xml 映射文件中的 `<cache>` 标签中配置 type 属性，设置自定义实现缓存的全类路径。

每个映射文件中对缓存单独设置，可以设置不同的配置方式.

#### `BlockingCache`