# Linux 基础配置

## 文件分发

```shell
scp -r path hostname:`pwd`
```

## 固定IP配置

### 修改配置文件 

```shell
vi /etc/sysconfig/network-scripts/ifcfg-enp0s3
```

```shell
TYPE=Ethernet
PROXY_METHOD=none
BROWSER_ONLY=no
BOOTPROTO=static
DEFROUTE=yes
IPV4_FAILURE_FATAL=no
IPV6INIT=yes
IPV6_AUTOCONF=yes
IPV6_DEFROUTE=yes
IPV6_FAILURE_FATAL=no
IPV6_ADDR_GEN_MODE=stable-privacy
NAME=ens33
DEVICE=ens33
ONBOOT=yes
IPADDR=10.0.0.100
NETMASK=255.255.255.0
GATEWAY=10.0.0.2
DNS1=114.114.114.114
```

### 重启网络服务

```shell
service network restart
```

## 修改 hostname 与 host 文件

### 修改 hostname

> 不同发行版本存放主机名称的文件位置不一样：Cent OS 7 `/etc/hostname`

```shell
hostnamectl set-hostname <host-name> # host-name 要设置的主机名称
```

### 修改 host

配置主机名与IP映射。

```shell
vi /etc/hosts
```

```shell
<ip-address> <host-name> # ip-address 主机ip地址 host-name 主机名
```

## 关闭防火墙

```shell
# 停止防火墙
systemctl stop firewalld
# 禁止开机启动
systemctl disable firewalld
```

## 关闭 SELinux

```shell
# 查看状态
getenforce # Enforcing
# 关闭 SELinux 
setenforce 0
# 查看状态
getenforce # Permissive
# 设置开机禁用
sed -i 's/SELINUX=enforcing/SELINUX=disabled/' /etc/sysconfig/selinux 
# 查看配置是否启动禁用
cat /etc/sysconfig/selinux # SELINUX=disabled
```


## 配置服务器时间同步

### 安装 ntp
```shell
yum install ntp -y
```

### 修改 `/etc/ntp.conf`

```shell
vi /etc/ntp.conf
```

22 行(`# Please consider joining the pool (http://www.pool.ntp.org/join.html).`)下新增：
```shell
server ntp1.aliyun.com prefer
server ntp2.aliyun.com
```

### 设置时区

```shell
timedatectl set-timezone Asia/Shanghai
```

### 手动同步

```shell
ntpdate ntp1.aliyun.com
```

### 启动服务

```shell
service ntpd start
```

### 开机启动

```shell
systemctl enable ntpd
```

重启后，检查是否开启启动：

```shell
systemctl list-unit-files | grep ntpd
```

## 安装 JDK

### rpm 方式

> 会自动创建 `/usr/java/default` 链接，一些应用依赖于此路径下的链接

#### 上传文件组件：
```shell
yum install lrzsz -y
```

#### 上传文件 `jdk-8u341-linux-x64.rpm` ：
```shell
rz -E
```

#### 安装：
```shell
rpm -i jdk-8u341-linux-x64.rpm
```

#### 添加环境变量：
```shell
vi /etc/profile
```

在文件的最后添加：
```shell
export JAVA_HOME=/usr/java/default
export PATH=$PATH:$JAVA_HOME/bin
```

加载环境变量文件：
```shell
source /etc/profile 
```
或者：
```shell
. /etc/profile
```

## SSH 免密登陆

> 公钥给谁，就可以免密登录谁。

验证是否需要密码（如果需要密码说明并没有实现 ssh 免密登录，同时会自动生成用户目录下的 `.ssh` 目录）：
```shell
ssh localhost
```

被登录主机生成 ssh 密钥对：
```shell
ssh-keygen -t rsa -P '' -f ~/.ssh/id_rsa
```

分发公钥到免密主机：`scp`

免密主机将公钥放置到 `~/.ssh/authorized_keys` 文件中
```ssh
cat ~/.ssh/id_rsa.pub >> ~/.ssh/authorized_keys
```

```shell
chmod 0600 ~/.ssh/authorized_keys
```

## Install PlayOnLinux

```shell
sudo apt install -y winbind
```

