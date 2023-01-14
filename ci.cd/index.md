# CI/CD

* Jenkins
* Docker

https://github.com/jenkinsci/docker

## 1. 安装 Jenkins

### 1.1 yum 方式安装

> 安装 wget 工具: `yum install wget -y`

* 导入 yum repo 源: `sudo wget -O /etc/yum.repos.d/jenkins.repo https://pkg.jenkins.io/redhat-stable/jenkins.repo`
* 导入 Jenkins 软件包校验 key : `sudo rpm --import https://pkg.jenkins.io/redhat-stable/jenkins.io.key`
* 更新 yum : `sudo yum upgrade`
* yum 安装 Jenkins : `sudo install jenkins`
* 设置开机自启动: `sudo systemctl enable jenkins`
* 启动 Jenkins: `sudo systemctl start jenkins`

### 1.2 Docker 运行

```shell
docker run \ 
  -d \
  -p 8080:8080 \
  -p 50000:50000 \
  --restart=always \
  -v /mnt/d/volumes/jenkins:/var/jenkins_home \
  -v /var/run/docker.sock:/var/run/docker.sock \
  --name jenkins \
  jenkins/jenkins:lts-jdk11
```

```shell
docker pull jenkinsci/blueocean:1.25.7
```

```shell
docker run \
  --rm \
  -u root \
  -p 8080:8080 \
  -v jenkins-data:/var/jenkins_home \
  -v /var/run/docker.sock:/var/run/docker.sock \
  -v "%HOMEPATH%":/home \
  --name blueocean
  jenkinsci/blueocean:1.25.7
```

### 1.3 war 包方式

## 2. 配置

> 查找 Jenkins 相关文件: `find / -name jenkins`

* `/etc/sysconfig/jenkins`:
    * `JENKINS_PORT` Jenkins 端口
    * `JENKINS_USER` Jenkins 用户 默认 `jenkins` 修改用户时对应修改 `/var/lib/jenkins` `/var/cache/jenkins` `/var/log/jenkins` 目录所属用户为修改用户
    * ``
* `/var/jenkins_home/hudson.model.UpdateCenter.xml` 插件源
    ```xml
    <?xml version='1.1' encoding='UTF-8'?>
    <sites>
      <site>
        <id>default</id>
        <url>https://mirrors.tuna.tsinghua.edu.cn/jenkins/updates/update-center.json</url>
      </site>
    </sites>
    ```

> **问题：找不到 java 环境**
> 
> `ln -s <java-home>/bin/java /usr/bin/java` 创建软连接
