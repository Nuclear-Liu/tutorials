# Install In CentOS

删除已有安装：

```shell
sudo yum remove docker docker-client docker-client-latest docker-common docker-latest docker-latest-logrotate docker-logrotate docker-engine
```

安装 `yum-utils` 工具：
```shell
sudo yum install -y yum-utils
```

设置 Docker 存储库：
```shell
sudo yum-config-manager --add-repo https://download.docker.com/linux/centos/docker-ce.repo
```

替换存储库为清华源：
```shell
sed -i 's+download.docker.com+mirrors.tuna.tsinghua.edu.cn/docker-ce+' /etc/yum.repos.d/docker-ce.repo
# 刷新缓存
sudo yum makecache fast
```

安装 Docker ：
```shell
sudo yum install -y docker-ce docker-ce-cli containerd.io docker-compose-plugin
```

启动服务：
```shell
sudo systemctl start docker
```

设置开机启动：
```shell
sudo systemctl enable docker
```

配置国内镜像加速：
```shell
tee /etc/docker/daemon.json <<-'EOF'
{
  "registry-mirrors": ["https://registry.docker-cn.com"]
}
EOF
# 配置生效
systemctl daemon-reload
```

## 常用中间件

设置容器的自启动：
```shell
docker update --restart=always container_name
```

安装文档工具：
```shell
yum install -y lrzsz
```

登入容器：
```shell
docker exec -it container_name /bin/bash
```

MySQL:5.7
```shell
# set mysql.cnf to /mydata/mysql/conf/mysql.cnf
cd /mydata/mysql/conf/
rz -E
docker run -p 3306:3306 --name mysql -v /mydata/mysql/log:/var/log/mysql -v /mydata/mysql/data:/var/lib/mysql -v /mydata/mysql/conf/mysql.cnf:/etc/mysql/mysql.cnf -e MYSQL_ROOT_PASSWORD=root -d mysql:5.7
```

Redis:7.0.4
```shell
# set redis.conf to /mydata/redis/conf/redis.conf
cd /mydata/redis/conf
rz -E
docker run -d -p 6379:6379 --name redis -v /mydata/redis/data:/data -v /mydata/redis/conf:/etc/redis  redis:7.0.4 redis-server /etc/redis/redis.conf
```

Nacos:1.4.2
```shell
docker pull nacos/nacos-server:1.4.2
cd /mydata/nacos/conf
rz -E
docker run -d -e MODE=standalone -v /mydata/nacos/conf:/home/nacos/conf --name nacos -p 8848:8848 nacos/nacos-server:1.4.2
```