# Linux 命令

## 网络配置

`ip addr` 查看网卡信息

`service network restart` 重启网络服务

配置文件位置： `/etc/sysconf/network-scripts/ifcfg-ens33`

> 默认名称为：`ifcfg-ens33`

* `ONBOOT`：
  * `no` 关闭
  * `yes` 开启
* `BOOTPROTO=dhcp`
  * `dhcp` DHCP 自动获取
  * `static` 静态IP
* `IPADDR`
* `NETMASK`
* `GATEWAY`