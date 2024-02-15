# 服务注册与服务发现

## 解决的问题

* 同意的注册中心
* 容易实现负载均衡

## 主流组件

|                 | Eureka                             | Nacos         | Consul                    | Zookeeper    |
|-----------------|------------------------------------|---------------|---------------------------|--------------|
| 一致性协议           | `CP`/`AP`                          | `AP`          | `CP`                      | `CP`         |
| 健康检查            | `TCP`/`HTTP`/`MYSQL`/`Client Beat` | `Client Beat` | `TCP`/`HTTP`/`gRPC`/`Cmd` | `Keep Alive` |
| 负载均衡策略          | 权重/Metadata/Selector               | Ribbon        | Fabio                     | --           |
| 雪崩保护            | 支持                                 | 支持            | 不支持                       | 不支持          |
| 跨注册中心同步         | 支持                                 | 不支持           | 支持                        | 不支持          |
| Spring Cloud 集成 | 支持                                 | 支持            | 支持                        | 不支持          |
| Dubbo 集成        | 支持                                 | 不支持           | 不支持                       | 支持           |
| k8s 集成          | 支持                                 | 不支持           | 支持                        | 不支持          |

##### Eureka 心跳机制

* Eureka Client 请求 Eureka Server ，将自己的 ip、port 发送到 server ， server 会将客户端加入服务清单；
* Eureka Client 每 30s 会向 Eureka Server 发送一次心跳，当超过 90s 未发送心跳，且不在保护模式时，将会把节点删除；
* Eureka Consumer 会首先从 Eureka Server 端获取服务列表，然后向服务实例发送调用请求；

> 单例配置：
> 
> * 指示实例是否向 Eureka Server 注册(某些情况不希望自己的实例被发现，而像发现其他实例)： `eureka.client.register-with-eureka=false`
> * 指示客户端是否从 Eureka Server 获取注册信息： `eureka.client.fetch-registry=false`

#### Nacos 服务发现

启动模式：
* 单体模式 `start -m standalone`

## 
