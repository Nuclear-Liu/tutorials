# R Language

* R 是一种**区分大小写**的**解释性语言**；
* 在命令提示符 `>` 后每次输入并执行一条命令，或者一次性执行写在脚本文件中的一组命令；
* R 中数据类型：向量、矩阵、数据框（与数据集相似）、列表（各种对象的集合）；
* R 中功能由内置函数和用户自定义函数提供，一次会话期间的所有数据对象都在内存中；

## R 帮助

| 函数                                 | 功能                            |
|------------------------------------|-------------------------------|
| `help.start()`                     | 打开帮助文档首页                      |
| `help("foo")` 或 `?foo`             | 查看函数 `foo` 的帮助（引号 `"` 可以省略）   |
| `help.search("foo")` 或 `??foo`     | 以 `foo` 为关键词搜索本地帮助文档          |
| `example("foo")`                   | 函数 `foo` 的使用示例（引号 `"` 可以省略）   |
| `RSiteSearch("foo")`               | 以 `foo` 为关键字搜索在线文档和邮件列表存档     |
| `apropos.("foo", mode="function")` | 列出名称中含有 `foo` 的所有可用函数         |
| `data()`                           | 列出当前已加载包中所有可用示例数据集            |
| `vignette()`                       | 列出当前已安装包中所有可用的 `vignette` 文档  |
| `vignette("foo")`                  | 为主题 `foo` 显示指定的 `vignette` 文档 |

## R 工作空间

**工作空间**： R 当前的工作环境，存储用户自定义的对象；
在会话结束时，将工作空间保存到一个镜像，下次启动 R 时自动载入；
当前工作目录(working directory)是 R 用来读取文件和保存结果的默认目录。

管理工作空间的函数：

| 函数                                      | 功能                                     |
|-----------------------------------------|----------------------------------------|
| `getwd()`                               | 查看当前的工作目录                              |
| `setwd(dir)`                            | 设定当前的工作目录为 `dir`                       |
| `ls()`                                  | 列出当前工作空间中的对象                           |
| `rm(objectlist)`                        | 移除（删除）一个或多个对象                          |
| `help(options)`                         | 显示可用选项的说明                              |
| `options()`                             | 显示或设置当前选项                              |
| `history(#)`                            | 显示最近用过的 `#` 个命令（默认值为 `25` ）            |
| `savehistory("cmdhis")`                 | 保存命令历史到文件 `cmdhis` 中（默认值为 `Rhistory` ） |
| `loadhistory("cmdhis")`                 | 载入一个命令历史文件 `cmdhis` （默认值为 `Rhistory` ） |
| `save.image("dataimg")`                 | 保存工作空间到文件 `dataimg` （默认值为 `Rdata` ）    |
| `load("dataimg")`                       | 读取一个工作空间到当前会话中（默认值为 `Rata` ）           |
| `save(objectlist, file = "objectdata")` | 保存指定对象 `objectlist` 到一个文件 `objectdata` |
| `q()`                                   | 退出 R，将会提醒是否保存工作空间                      |

## R 包

**包**是 R 函数、数据、预编译代码以一种定义完善的格式组成的集合；
存储包的目录为**库**(library)；


`.libPaths()` 显示库所在的位置
`library()` 显示库中包含的包
`library(libname)`/`require(libname)` 加载 `libname` 到内存

`detach("package:libname", unload = TRUE)` 从内存释放 `libname`

`install.package("libname")` 安装 `libname` 包
`update.packege()` 更新包

