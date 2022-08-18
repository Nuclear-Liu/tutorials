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

MySQL:
```shell
docker run -p 3306:3306 --name mysql -v /mydata/mysql/log:/var/log/mysql -v /mydata/mysql/data:/var/lib/mysql -v/mydata/mysql/conf/mysql.cnf:/etc/mysql/mysql.cnf -e MYSQL_ROOT_PASSWORD=root -d mysql:5.7
```
