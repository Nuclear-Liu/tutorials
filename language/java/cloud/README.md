# Spring Cloud

微服务名称与实例是一对多关系：即一个微服务名称可以有多个实例对应。

## 典型组件

| 组件项   | Netflix              | Spring Cloud | Alibaba  |
|-------|----------------------|--------------|----------|
| 服务注册  | Eureka               | Consul       | Nacos    |
| 服务间调用 | Feign/Hystrix/Ribbon |              | Sentinel |
| 配置中心  | Config               |              | Nacos    |
| 网关    | Zuul                 | Gateway      |          |

## 组件版本(Spring Boot / Spring Cloud / Spring Cloud Alibaba)

https://start.spring.io/actuator/info

`SNAPSHOT`(快照版本)>>`PRE`(预览版)>>`RC`(发行候选版本)>>`SR`(修正版本)>>`GA`(正式发布版本)

#### Spring Boot / Spring Cloud

| 英文 | 中文 | 终结版本 | Spring Boot 大版本 | Spring Boot 代表版本 | 说明 |
| ---- | ---- | ---- | ---- | ---- | ---- |
|    |    |      |                 |                  |    |

#### Spring Cloud Alibaba / Spring Cloud



**服务注册与发现**

| 对比项     | apollo                          | nacos                       | config               |
|---------|---------------------------------|-----------------------------|----------------------|
| 配置实时推送  | 支持(HTTP 长轮询 < 1s)               | 支持(HTTP 长轮询 < 1s)           | 支持(Spring Cloud Bus) |
| 版本管理    | 自动管理                            | 自定管理                        | 支持(Git)              |
| 配置回滚    | 支持                              | 支持                          | 支持(Git)              |
| 权限管理    | 支持                              | 待支持                         | 支持                   |
| 多集群/多环境 | 支持                              | 支持                          | 支持                   |
| 多语言     | Go/C++/Python/Java/.NET/OpenAPI | Python/Java/Node.js/OpenAPI | Java                 |
| 分布式部署   |                                 |                             |                      |

