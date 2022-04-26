
`docker network create app-tier --driver bridge`

```shell
docker run --name zookeeper \
  --network app-tier \
  -e ALLOW_ANONYMOUS_LOGIN=yes \
  -p 2181:2181 \
  bitnami/zookeeper:3.8
```

`docker run -d --name zookeeper --network app-tier -e ALLOW_ANONYMOUS_LOGIN=yes -p 2181:2181 bitnami/zookeeper:3.8`

```shell
docker run --name kafka1 \
  --network app-tier \
  -e KAFKA_CFG_ZOOKEEPER_CONNECT=zookeeper:2181 \
  -e ALLOW_PLAINTEXT_LISTENER=yes \
  -p 9093:9092 \
  bitnami/kafka:3.1
```
`docker run -d --name kafka1 --network app-tier -e KAFKA_CFG_ZOOKEEPER_CONNECT=zookeeper:2181 -e ALLOW_PLAINTEXT_LISTENER=yes -p 9093:9092 bitnami/kafka:3.1`