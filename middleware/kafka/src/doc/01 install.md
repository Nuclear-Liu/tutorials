# Install

1. Zookeeper 集权安装；
2. 下载 Kafka
3. 配置 `server.properties`

    1. `broker.id`
    2. `listeners`
    3. `log.dirs`
    4. `zookeeper.connect`

4. 配置环境变量
5. `scp` 同步配置；
6. 启动 `kafka-server-start.sh ./server.properties`
