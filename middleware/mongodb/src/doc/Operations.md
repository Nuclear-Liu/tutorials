#### 启动

* `mongod`
    * `-f config_file_path` 

#### 连接

* `mongo`
    * `--port port_number`

* `show databases` / `show dbs` : 显示所有数据库
* `use database_name` : 创建 `database_name` 数据库
* `db.dropDatabase()` : 删除数据库

* `dp.<collection>.insertOne()` 插入到 `collection` 一条记录返回 `ObjectId`
* `dp.<collection>.insertMany()` 批量插入 `collection` 返回 `ObjectId`

#### 查询

* `db.<collection>.find(<query>,<projection>)`\
    * `query` : 可选，查询筛选器， JSON 对象
    * `projection` : 可选，结果字段， JSON 对象

#### 导出数据

`mongodump -h ip:port -d database_name -o dump_file_path`

### 导入数据

`mongorestore -h ip:port -d database_name --dir restore_file_path`

#### 删除数据库

* 修复数据（存在丢数据可能，用于开发；）： `mongod --repair --dbpath=mongobd_data_path`




