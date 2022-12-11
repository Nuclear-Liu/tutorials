# LoadBalance 负载均衡

> 不同的客户链接负载均衡连接到不同的实例
> * 不同实例的客户连接之间如何实现相互的消息推送
> * 其他业务数据库推送所有链接，如何保证各个实例可以保证推送消息

* **方案一**： Nginx 负载均衡 + 基于 Simple Broker 内存实现消息推送
    
  1. 实例间依靠缓存(`Redis`)共享客户端连接信息
  2. 实例间依靠服务间调用，传递推送业务需求

* **方案二**： Nginx 负载均衡 + 基于 STOMP Broker 实习消息推送 + 消息队列集群(`RabbitMQ`)

  1. 实例间共享使用同一个 STOMP Broker 集群

* **方案三**： Nginx 一级负载均衡 + Spring Cloud Gateway 集群二级负载 + 服务注册中心(`Nacos`/`Eurake`) + 基于 STOMP Broker 实习消息推送 + 消息队列集群(`RabbitMQ`)

  1. 实例间共享使用同一个 STOMP Broker 集群(RabbitMQ)
  2. 实例与网关统一通过注册中心实现服务注册发现


**Nginx 配置：** 
```text
upstream stomp-cluster
{
    server localhost:8071 weight=1;
    server localhost:8072 weight=1;
}

server
{
    listen 8070;
    server_name localhost;
    location /{
        proxy_pass http://stomp-cluster/;
    }
}
```
