# MySQL


#### 连接 MySQL


* `-h` : 主机名，表示要连接的主机名或 IP
* `-u` : 用户名，表示连接数据库的用户名
* `-P` : 端口，表示连接数据库的端口号
* `-p` : 密码，要连接的数据库的密码， `-p` 后面可以直接输入密码（没有空格），但是这样密码是明文不安全，建议输入 `-p` 回车，换行输入密码


```shell
mysql -h ip -u user -P port -ppassword
```

```shell
mysql -h 127.0.0.1 -u root -P 3306 -proot
```

#### 查看版本信息

`status;`

### 清屏

`system clear;`


#### 切换数据库


`use database_name;`

#### 查看当前所有的数据库

`show databases;`

#### 查看当前数据库下的所有表

`show tables;`

### 查看表索引

`show index from table_name;`


### 查看表结构

`desc table_name;`


`show create table table_name;`

### 创建索引

* 普通索引

`create index index_name on table_name(colums);`

