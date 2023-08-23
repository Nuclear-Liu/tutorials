# Kubernetes 客户端工具: kubectl


## 1. kubectl 命令帮助

获取 kubectl 命令的方式：
* `kubectl`
* `kubectl -h`
* `kubectl --help`

## 2. kubectl 命令说明


### 基本命令 Basic Commands


| 命令                               | 描述                                                          |
|----------------------------------|-------------------------------------------------------------|
| `create`                         | 通过**文件名**或**标准输入**创建资源(`namespace` \ `pod` \ `service` ...) |
| `expose`                         | 将一个资源公开为一个新的 Kubernetes `service`                           |
| `run`                            | 在集群中运行一个特定的镜像                                               |
| `set`                            | 在对象让设置特定的功能                                                 |
| `get`                            | 显示一个或多个资源                                                   |
| <font color=red>`explain`</font> | 文档参考资料                                                      |
| `edit`                           | 编辑服务器上的资源                                                   |
| `delete`                         | 通过文件名、标准输入、资源名或标签选择器来删除资源                                   |


### 部署命令 Deploy Commands


| 命令               | 描述                                        |
|------------------|-------------------------------------------|
| `rollout`        | 管理资源的发布                                   |
| `rolling-update` | 对给定的复制控制器滚动更新                             |
| `scale`          | 扩容或缩容 Pod 数量，Deployment、ReplicaSet、RC或Job |
| `autoscale`      | 创建一个自动选择扩容或缩容并设置 Pod 数量                   |


### 集群管理命令 Cluster Management Commands


| 命令             | 描述                     |
|----------------|------------------------|
| `certificate`  | 修改证书资源                 |
| `cluster-info` | 显示集群信息                 |
| `top`          | 显示资源(`CPU`/`memory`)使用 |
| `cordon`       | 标记节点不可调度               |
| `uncordon`     | 标记节点可调度                |
| `drain`        | 驱逐节点上的应用，准备下线维护        |
| `taint`        | 修改节点 taint 标记          |


### 故障诊断与调试命令 Troubleshooting and Debugging Commands


| 命令                                | 描述                                       |
|-----------------------------------|------------------------------------------|
| <font color=red>`describe`</font> | 显示特定资源或资源组的详细信息                          |
| <font color=red>`logs`</font>     | 在一个 Pod 中打印一个容器日志；如果 Pod 只有一个容器，容器名称是可选择 |
| `attach`                          | 附加到一个运行的容器                               |
| <font color=red>`exec`</font>     | 在一个容器中执行命令                               |
| `port-forward`                    | 转发一个或多个本地端口到一个 Pod                       |
| `proxy`                           | 运行一个 proxy 到 Kubernetes API Server       |
| `cp`                              | 拷贝文件或目录到容器中                              |
| `auth`                            | 检查授权                                     |


### 高级命令 Advanced Commands


| 命令        | 描述                        |
|-----------|---------------------------|
| `apply`   | 通过文件名称或标准输入对资源应用配置        |
| `patch`   | 对正在运行的资源对象，使用补丁修改、更新资源的字段 |
| `replace` | 通过文件名或标准输入替换一个资源          |
| `convert` | 不同的 API 版本之间转换配置文件        |


### 设置命令 Setting Commands


| 命令           | 描述                  |
|--------------|---------------------|
| `label`      | 更新资源上的标签            |
| `annotate`   | 更新资源上的注释            |
| `completion` | 用于实现 kubectl 工具自动补全 |


### 其他命令 Other Commands


| 命令             | 描述                                  |
|----------------|-------------------------------------|
| `api-versions` | 打印受支持的 API 版本                       |
| `config`       | 修改 kubeconfig 文件(用于访问 API，比如配置认证信息) |
| `help`         | 所有命令帮助                              |
| `plugin`       | 运行一个命令行插件                           |
| `version`      | 打印客户端和服务版本信息                        |


## 3. kubectl 命令补全


> 只需要在 Kubernetes Master 节点配置。



1. 安装 `bash-completion` 终端命令增强:  `yum install -y bash-completion`

2. 加载环境变量:

   `source /usr/share/bash-completion/bash-completion`

   `source <(kubectl completion bash)`

3. (避免重启失效)将 kubectl 相关命令写入文件: `kubectl completion bash > ~/.kube/completion.bash.inc`

4. (避免重启失效)将命令文件添加到 profile : `vi ~/.bash_profile`

   追加: `source ~/.kube/completion.bash.inc`


## 4. kubectl 最佳实践

