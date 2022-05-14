# LVS


> LVS 基于四层实现负载均衡，Nginx 基于七层的反向代理实现负载均衡；


## NAT ：基于**三层到四层**的解决方案


> NAT 网络地址转换
>
> 一般位于路由器中，由一张表记录请求的请求的端口和请求IP与NAT生成的随机端口的表，修改数据包的源地址和源端口  属于 **S-NAT** 源地址转换


加一个基于四层的**负载均衡器**，但是负载均衡器不会和请求进行握手建立链接，速度快，进行数据包级别的转发。


通过传修改输层数据包的目标端口号和目标 IP 到达负载目标。 属于 **D-NAT** 目标地址转换协议


请求数据包 通过 D-NAT 转换转发请求数据包


### 缺点

* 被负载服务器的默认网关必须是负载均衡器。
* 要求被负载的对象彼此之间是镜像的。
* 带宽称为瓶颈。在通信的时候是非对称的，下行带宽是瓶颈。
* 消耗算力。


## DR (直接路由模式) ：基于**二层**的 Mac 地址欺骗  _企业中心应用最多_

被负载对象服务器对真是IP进行隐藏，对外隐层对内可见。
请求经过负载均衡器进入被负载对象，处理后直接返回给客户端，响应不在经过负载均衡器。链路形成环状。

* 速度快
* 成本低

### 缺点

* 负载均衡器必须与被负载的机器位于同一个网络内。

## TUN (隧道模式)


TUN 隧道技术： IP 的数据包背着 IP 的数据包的技术


## LVS (Linux Virtual Server) Linux 虚拟服务器：四层技术


不会增加单一吞吐量，而是将并发分治。


### 隐藏 VIP (虚拟IP)


通过修改 kernel 内核的网络协议实现。


> Kernel parameter
> 
> 目标 mac 地址为全 `F` ，交换机触发广播 `/proc/sys/net/ipv4/conf/*IF*/arp_ignore` 定义接收到 ARP 请求时的响应级别：
> `0` : 只要本地配置的有相应地址，就给予响应； 
> `1` : 仅在请求的目标 (MAC) 地址配置请求到达的接口上的时候，才给予响应；
> 
> `/proc/sys/net/ipv4/conf/*IF*/arp_announce` 定义将自己地址向外通知时的通告级别：
> `0` : 将本地任何接口上的任何地址向外通告；
> `1` : 试图仅向目标网络通告与其网络匹配的地址；
> `2` : 仅向与本地接口上地址匹配的网络进行通告；


> 一个网卡可以有多个IP地址

1. 修改内核 ARP 配置参数；
2. 把 VIP 配置到内核虚拟网卡上（回环接口 LOOPBACK ）；

### 调度算法

#### 静态调度算法

1. `rr` 轮询
2. `wrr`
3. `dh`
4. `sh`

#### 动态调度算法

1. `lc` 最少链接数

    链接数的获取：负载均衡器具备偷窥能力；
    记录客户端握手请求中的 ip + port （三次握手确定链接确认） 链接数 +1 
    记录经过的数据包的断开链接的请求 确定断开 链接数 -1

2. `wlc` 加权最小连接
3. `sed` 最短期望延迟
4. `np` never queue
5. `LBLC` 基于本地的最少连接
6. `DH`
7. `LBLCR` 基于本地的带复制功能的最小连接


### 实现 LVS

LVS 包裹在 Linux Kernel 中 模块的名称为 `ipvs` 默认带有。

`ipvsadm` 为与内核 `ipvs` 交互的包，实现与 `ipvs` 交互。

`yum install ipvsadm -y`

* 管理集群服务 入

  * 添加 `-A` `-A -t|u|f service-address [-s scheduler]`

    * `t` TCP 协议集群
    * `u` UDP 协议集群
    * 'f' FWM 防火墙标记
    * `service-address` ip + port
    * `s` 调度算法

  * 修改 `-E`

  * 删除 `-D`

  `ipvsadm -A -t 192.168.9.100:80 -s rr`

* 管理集群服务中的 RS 出

  * 添加 `-a` `-a -t|u|f service-address -r server-address [-g|i|m] [-w weight]`
  
    * `-t|u|f service-address` 事先定义好的某集群服务
    * `-r server-address` 某 RS 的地址，在 NAT 模型中，可使用 ip + port 实现端口映射
    * `[-g|i|m]` LVS 类型
      * `-g` DR
      * `-i` TUN
      * `-m` NAT
    * `[-w weight]` 定义服务器权重
  * 修改 `-e`
  * 删除 `-d` `-d -t|u|f service-address -r server-address`
  
    `ipvsadm -a -t 172.16.100.1:80 -r 192.168.10.8 -g`
  
    `ipvsadm -a -t 172.16.100.1:80 -r 192.168.10.9 -g`

  * 查看 `-L|l` `L` 查看集群服务； `l` 查看集群中 RS
    * `-n` 数字格式显示主机地址和端口
    * `--status` 统计数据
    * `--rate` 速率
    * `--timeout` 显示 TCP TCPFIN UDP 的会话超时时长
    * `-c` 显示当前的 `ipvs` 连接状况
  * 删除所有集群服务 `-C` 清空 `ipvs` 规则
  * 保存规则 `-S` 
  
    `ipvsadm -S > /path/to/somefile`
  
  * 载入此前的规则 `-R`
  
    `ipvsadm -R < /path/form/somefile`


#### 实验手册

VM 提供虚拟网络

VM 提供虚拟主机

VIP: `192.168.150.100`
DIP(LVS): `192.168.150.1`
RIP:

**LVS 实验手册**
```shell
# node01:LVS config
ifconfig eth0:8 192.168.150.100/24

# node02~node03: config
# 1. edit ARP 
echo 1 > /proc/sys/net/ipv4/conf/eth0/arp_ignore
echo 1 > /proc/sys/net/ipv4/conf/all/arp_ignore

echo 2 > /proc/sys/net/ipv4/conf/eth0/arp_announce
echo 2 > /proc/sys/net/ipv4/conf/all/arp_announce
# 2. set VIP
ifconfig lo:3 192.168.150.100 netmask 255.255.255.255

# RS(node02~node03) 中配置对应服务
yum install httpd -y
service httpd start
vi /var/www/html/index.html # insert text `from 192.168.150.1x`

# node01 LVS config
yum install ipvsadm
ipvsadm -A -t 192.168.150.100:80 -s rr
ipvsadm -ln
ipvsadm -a -t 192.168.150.100:80 -r 192.168.150.12 -g -w 1
ipvsadm -ln
ipvsadm -a -t 192.168.150.100:80 -r 192.168.150.13 -g -w 1

# node01 lvs 
netstat -natp # can not find socket connection
# node02 RS
netstat -natp # can find socket connection
# node03 RS
netstat -natp # can find socket connection

# node01 lvs
ipvsadm -lnc
#    FIN_WAIT 连接过，偷窥了所有包
#    SYN_RECV 基本上 LVS 都记录了，证明 LVS 正常，一定是与负载间的网络层出问题了
```

## 高可用

### 问题

* 单点 LVS 可能**挂掉**（单点故障）

    整个对外服务不可用；

* RS 可能**挂掉**
    
    负载均衡器还记录了挂掉了 RS 的负载记录，导致部分客户端请求异常；


### 解决方案

#### 单点 LVS 可能**挂掉**（单点故障）

> 单点故障解决两个思路，多点集群逻辑的两种形式：
> 
> 1. **主备**模型
> 2. **主主**模型

一边多，用多台 LVS ，但是 VIP 需要全局唯一；

> 主备 vs 主从
> 
> 主备，只有主机对外提供服务；备机不对外提供服务。只有当主机故障，从机才会启用变为新的主机。
> 
> 主从，有主机、从机，写作完成对外的提供的服务（例如：读写分离），主机或者从机挂掉都会有影响。
> 
> **一般情况下，主从模式中主角色会利用主备做主角色的高可用（HA）**，从角色一般为多节点。

##### 主备模型


* 方向性：

  1. 备机主动定时轮询主机状态
    
    增加主机压力

  2. 主机周期性主动向备机广播状态，从机被动接收 (效率高)

    一般会有一个重试机制，到达一定次数的不可用才可以断定为不可用。

效率性：

  1. 推让制 (效率高)

    通过给备机加**权重**选主机；
    退让制，基本上一轮就可以选出主机。

  3. 投票选择


###### Keepalived 实现主备 HA （高可用）


Keepalived 解决单点故障，实现高可用 (HA) ，是一个存在于用户空间的外挂程序； Keepalived 提供的功能：

1. 监控自己身的功能服务 (`LVS`) 服务；

2. Master 主机通告自己存活； Backup 备机监听 Master 主机状态；如果 Master 不可用，多台 Backup 推举出一个新的 Master ；

3. 配置功能：通过配置文件实现配置（设置 `VIP` ，添加 `ipvs` ）；

4. 对被负载对象做健康检查；


> Keepalived 是一个通用工具（不仅仅用于 `LVS` ，包括 Nginx 、 Tomcat 高可用实现），主要作为高可用 (HA) 的实现。


清理已有 LVS 配置 ： `ipvsadm -C`

清理虚拟网卡配置 ： `ifconfig eth:8 down`

**Keepalived 实现 LVS HA （高可用）**
```shell
# noed01 keepalived+lvm noed04 keepalived+lvm node02 
yum install keepalived -y
# edit keepalived config `/etc/keepalived/keepalived.conf`
cd /etc/keepalived/
# backup keepalived.conf to keepalived.conf.bak
cp keepalived.conf keepalived.conf.bak
# edit config
vi keepalived.conf
# start service
service keepalived start
```

配置文件结构： [keepalived.conf](./keepalived.conf)

  * `global_defs` 全局配置
  * `vrrp_instance` 虚拟路由冗余协议
  * `virtual_server` 虚拟服务器
    * `real_server` 真是服务器
    

> **不是所有的主备模型，主正常上线后一定可以抢回主的地位。**
>
> **LVS 上没有太多的数据同步，所以可以抢回主的状态。（HDFS不可以抢回）。而且主一般比从配置强**
> 
> **一个主异常下线恢复后是否抢回主：参考成本复杂度。**
> 
> 主要想抢回原为的主地位，需要同步会不可用时间内的数据。如果时间太长，就不合适。
> 所以 HDFS 主从配置一般一致，为了避免需要同步不可用时间内的数据，不回去抢回主的地位。

* `man` 帮助手册： `yum install man -y`

* 查看帮助文件： `man 5 keepalived.conf`

* 远程文件拷贝： 

    ```shell
    scp ./keepalived.conf root@node04:`pwd`
    ```
* 关闭网卡： `ifconfig eth0 down`
* 开启网卡： `ifconfig eth0 up`

##### 主主模型

主主模式需要在 LVS 之前加一层动态 DNS 。

#### RS (Real Server) 可能**挂掉**

RS 不可用确定机制：

    验证应用层（HTTP，发送请求，判断返回状态码 `200` ）协议。


> `ping` 只能检测 IP 层状态；并不能确定应用状态。


#### Keepalived 优缺点


> Keepalived 后台会有三个进程（一个主进程和若干个主进程的子进程）
> 
> * **主进程**：负责主动广播自身状态信息。
> 
> * **子进程**：每个子进程连接一个 RS 负责健康检查（HTTP连接）。

问题：

* Keepalived 是一个第三方程序，有可能会异常退出

    异常退出会导致 LVS 配置信息没有被正确收回（VIP、内核配置）；备机不知道主机还活着，备机称为主机，出现VIP冲突。
    导致网络异常（连接原子性被破坏、网络稳定性被破坏）。

**通过 Zookeeper 高可靠集群来解决 Keepalived 不可靠问题。**
