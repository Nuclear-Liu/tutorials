# RabbitMQ

rabbitmq下载
https://www.rabbitmq.com/install-windows.html
安装路径：
C:\Program Files\RabbitMQ Server\rabbitmq_server-3.8.3\


## 启动rabbitmq

cd C:\Program Files\RabbitMQ Server\rabbitmq_server-3.8.3\sbin
rabbitmq-plugins enable rabbitmq_management


## 重启

rabbitmq-service stop
rabbitmq-service start

## 安装插件

rabbitmq-plugins enable rabbitmq_stomp
rabbitmq-plugins enable rabbitmq_web_stomp


## 清除所有队列数据：

rabbitmqctl stop_app
rabbitmqctl reset
rabbitmqctl start_app

## 修改端口

打开C:\Program Files\RabbitMQ Server\rabbitmq_server-3.8.2\ebin文件夹
打开ebin文件夹，找到文件“rabbit.app”
修改tcp_listeners里面的端口号5672为5673或你想指定的端口

## RabbitMQ 前缀规则

> RabbitMQ 中合法前缀： 
> * `/exchange`
> * `/temp-queue`
> * `/topic`
> * `/queue`
> * `/amq/queue`
> * `/reply-queue`

* `/exchange/<exchangeName>` 交换器 -- 不会持久化
    
    * SUBSCRIBE frame `destination` 的一般形式: `/exchange/<exchangeName>/[/pattern]` 

      交换机要手动创建，该 `destination` 会创建一个唯一的、自动删除、名称为 `<exchangeName>` 的 queue；
      根据 `pattern` 将该 queue 绑定到所给的 `exchange` ，实现对该队列的消息订阅。
    * SUBSCRIBE frame `destination` 的一般形式: `/exchange/<exchangeName>/[/routingKey]`
      
      消息会被发送到定义的 `exchange` 中，并且指定了 `routingKey`

* `/queue/<queueName>` 队列 -- 可靠的推送，持久化

    自动创建 `<queueName>` 实现对队列消息的共享，在第一次发送消息的时候定义 `<queueName>` ，使用默认的 `exchange` ， `routingKey` 即为 `queueName` 。
    
* `/topic/<topicName>` 队列 -- 非持久化
    
    自动创建、删除，根据 `<topicName>`(`routingKey`) 绑定到 `amq.topic` `exchange` 上，同时实现对该 `queue` 的订阅。
    
* `/amq/queue/<queueName>` 









