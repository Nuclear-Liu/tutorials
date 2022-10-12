# HDFS 伪分布式模式

## 基础设施 - GNU/Linux

> 所需软件：
>    * Java：1.8
>    * ssh(免密远程执行 Hadoop 脚本)
> 
> **远程登陆时不会加载 `/etc/profile` 环境变量文件**

1. 设置 IP 及主机名
2. 关闭防火墙 SELinux
3. 设置 `hosts` 映射
4. 时间同步
5. 安装 JDK
6. 设置 SSH 免密

## Hadoop 的配置 - 应用搭建

> 伪分布式（单一节点）

1. 部署路径 `/opt/bigdata/`

    * 解压安装波： `tar xf hadoop-2.6.5.tar.gz`
    * 移动文件到安装目录： `mv hadoop-2.6.5.tar.gz /opt/bigdata/`
    * 配置环境变量： `vi /etc/profile`
   
      * 文件已配置 `JAVA_HOME` 下添加： `exprot HADOOP_HOME=/opt/bigdata/hadoop-2.6.5`
      * `PATH` 添加： `$HADOOP_HOME/bin` `$HADOOP_HOME/sbin`
   
2. 配置文件 `$HADOOP_HOME/etc/hadoop/`

    * `hadoop-env.sh` 环境配置文件 - 配置 `JAVA_HOME` (支持 ssh 登录时 java 可以)

      `exprot JAVA_HOME=/usr/java/default`
   
    * `core-site.xml` 核心配置文件 - NameNode 信息

    ```xml
    <configuration>
        <property>
            <name>fs.defaultFS</name>
            <value>hdfs://host_name:9000</value> <!-- <value>hdfs://node01:9000</value> -->
        </property>
    </configuration>
    ```

    * `hdfs-site.xml` 

      * `replication` 副本数  
      ```xml
      <configuration>
          <property>
              <name>dfs.replication</name>
              <value>/</value>
          </property>
      </configuration>
      ```
   
      * NameNode 元数据存放位置（默认临时目录，不安全）
      ```xml
      <configuration>
          <property>
              <name>dfs.namenode.name.dir</name>
              <value>/var/bigdata/hadoop/local/dfs/name</value>
          </property>
      </configuration>
      ```

      * DataNode 元数据存放位置（默认临时目录，不安全）
      ```xml
      <configuration>
          <property>
              <name>dfs.datanode.data.dir</name>
              <value>/var/bigdata/hadoop/local/dfs/data</value>
          </property>
      </configuration>
      ```
   
      * SecondaryNode 启动位置
      ```xml
      <configuration>
          <property>
              <name>dfs.namenode.secondary.http-address</name>
              <value>host_name:5090</value> <!-- <value>node01:5090</value> -->
          </property>
      </configuration>
      ```
   
      * SecondaryNode 数据目录配置
      ```xml
      <configuration>
          <property>
              <name>dfs.namenode.checkpoint.dir</name>
              <value>/opt/bigdata/hadoop/local/dfs/secondary</value>
          </property>
      </configuration>
      ```

   * `slaves` 配置 DataNode 位置（主机名）

3. 初始化启动

   1. 格式化(初始化，格式化 NameNode )
   
        `hdfs namenode -format`

      > 创建目录、初始化一个空的 `FsImage` 文件、 生成集群ID `VERSION:clusterID`

   2. 启动角色进程

      `start-dfs.sh`

      > 启动 `NameNode` 和 `DataNode` 角色，并初始化自己的数据目录

      `stop-dfs.sh` 停止 HDFS

4. 验证安装

    * `EditLog` 的 ID 是不是比 `FsImage` 的ID 大 ： `/opt/bigdata/hadoop/local/dfs/name/current`
    * `SecondaryNameNode` 只需要从 `NameNode` 拷贝最后时点的 `FsImage` 和增量 `EditLog` ： `/opt/bigdata/hadoop/local/dfs/secondary/current`
    * 上传文件可以看到成对的文件（**块** + **块校验和**） ： `/opt/bigdata/hadoop/local/dfs/data/current/.../current/finalized/subdir0/subdir0`
    * 上传文件被按字节切割
