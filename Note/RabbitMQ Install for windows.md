# RabbitMQ Install



## Erlang



1. install `otp_win64_23.0.exe`

2. Config `ENV`

   1. 新建系统变量: `ERLANG_HOME`

      `ERLANG_HOME` : `<install-path>\erl-23.0`

   2. 添加: `%ERLANG_HOME%\bin;` 到 `path`  变量头部



## RabbitMQ



1. install `rabbitmq-server-3.8.3.exe`

2. Config `ENV` 

   1. 新建系统变量: `RABBITMQ_SERVER`

      `RABBITMQ_SERVER` : `<install-path>\RabbitMQ Server\rabbitmq_server-3.8.3`

   2. 添加: `%RABBITMQ_SERVER%\sbin;` 到 `path` 变量头部

   3. 启用 `rabbitmq_management` 插件: `rabbitmq-plugins.bat enable rabbitmq_management`

   4. 启用 STOMP 插件: `rabbitmq-plugins.bat enable rabbitmq_stomp`

   5. 启用 STOMP web 插件: `rabbitmq-plugins.bat enable rabbitmq_web_stomp`