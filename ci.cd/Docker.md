# Docker

## Dockerfile

### 命令

| Command         | Description                                           |
|-----------------|-------------------------------------------------------|
| `FROM`          | 设置镜像使用的基础镜像                                           |
| ~~`MAINTANER`~~ | 设置镜像的作者                                               |
| `RUN`           | 编译镜像时运行的脚本(Shell)                                     |
| `CMD`           | 设置容量的启动命令（如果有多个，只有最后一个生效）                             |
| `LABEL`         | 设置镜像的标签                                               |
| `EXPOESE`       | 设置镜像暴露的端口                                             |
| `ENV`           | 设置容器的环境变量                                             |
| `ADD`           | 编译镜像时复制文件到镜像中                                         |
| `COPY`          | 编译镜像时复制文件到镜像中                                         |
| `ENTRYPOINT`    | 设置容器的入口程序(运行容器时通过 `--entrypoint=<script-shell>` 重新指定) |
| `VOLUME`        | 设置容器的挂载卷                                              |
| `USER`          | 设置运行 `RUN` `CMD` `ENTRYPOINT` 的用户名                    |
| `WORKDIR`       | 设置 `RUN` `CMD` `ENTRYPOINT` `COPY` `ADD` 指令的工作目录      |
| `ARG`           | 设置编译镜像时加入的参数                                          |
| `ONBUILD`       | 设置镜像的 `ONBUILD` 指令                                    |
| `STOPSIGNAL`    | 设置容器的退出信号量                                            |

> **`ENTRYPOINT`**
> 
> 设置容器入口程序( 类似于 `CMD` )；
> 不会被 `docker run` 命令行参数覆盖，命令行参数被当作 `ENTRYPOINT` 的参数。
> 
> **用法：**
> * `ENTRYPOINT ["<executable>", "<parame1>", "<param2>"]` : 可执行文件（可以带有参数）
> * `ENTRYPOINT <command> <param1> <param2>` : shell 命令（可以带有参数）
> 
> 使用 `--entrypoint=<script-shell>` 覆盖镜像中的 `ENTRYPOINT` 。

> **`RUN`**
> 
> 指定 `docker build` 过程中要运行的命令，每次运行一次镜像层数将会多一层。

> **`ADD`**
> 
> 将文件复制到镜像中（支持网络文件）。
> 
> 用法：
> * `ADD <src> ... <dest>`
> * `ADD ["<src>", ... "<dest>"]`

> **`COPY`**
> 
> 将文件复制到镜像中（仅本地文件）。

> **`ARG`**
> 
> `ARG <参数名>[=<默认值>]`
> 
> 定义镜像构建过程中使用的变量。

* `docker history <image>` 查看镜像分层信息
    * `--no-trunc` 查看完成信息