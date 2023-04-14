
kibana:
`docker run -d -e ELASTICSEARCH_HOSTS=http://<host_ip>:<elasticsearch-port> -p 5601:5601 --name kibana docker.elastic.co/kibana/kibana:7.10.0`

elasticsearch:
`docker run -d -p 9200:9200 -p 9300:9300 -e "discovery.type=single-node" --name elasticsearch docker.elastic.co/elasticsearch/elasticsearch:7.10.0`
