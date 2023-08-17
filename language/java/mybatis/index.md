# MyBatis 源码

* **体系结构**
    * **接口层**
        * `SqlSession`
    * **核心处理层**
        * `配置解析`
        * `参数映射`
        * `SQL 解析`
        * `SQL 执行`
        * `结果集映射`
        * `插件`
    * **基础模块**
        * `反射模块`
        * `类型转换模块`
        * `日志模块`
        * `资源加载`
        * `解析器模块`
        * `Binding 模块`
        * `缓存模块`
        * `事务管理模块`
        * `数据源模块`

## 基本使用

[https://blog.mybatis.org/p/products.html](https://blog.mybatis.org/p/products.html)

[](https://mybatis.org/mybatis-3/)

[](http://mybatis.org/generator/)

[](http://www.mybatis.org/mybatis-dynamic-sql/docs/introduction.html)

### 全局配置文件 `mybatis-config.xml`

```xml
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE configuration PUBLIC "-//mybatis.org//DTD Config 3.0//EN"
        "http://mybatis.org/dtd/mybatis-3-config.dtd">
<configuration>
    
</configuration>
```

properties?, settings?, typeAliases?, typeHandlers?, objectFactory?, objectWrapperFactory?, reflectorFactory?, plugins?, environments?, databaseIdProvider?, mappers

## 源码

* 父工程仓库: [mybatis-parent](https://github.com/mybatis/parent)
* MyBatis仓库: [mybatis-3](https://github.com/mybatis/mybatis-3)

### 源码结构

* 接口层
    * SqlSession
* 核心处理层
    * 配置解析
    * 参数映射
    * SQL解析
    * SQL执行
    * 结果集映射
    * 插件
* 基础支持层
    * 数据源模块
    * 事务模块
    * 缓存模块
    * Binding模块
    * 反射模块
    * 类型转换模块
    * 日志模块
    * 资源加载
    * 解析器模块

1. 加载全局配置文件
2. 加载映射文件
3. 加载内容存储到哪个Java对象中: Configuration
