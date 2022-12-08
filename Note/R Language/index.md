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

## R 操作

* 赋值： 
  * `<-` 右边结果赋值左边 
  * `=` 
  * `->` 左边结果赋值右边
* 取余： `%%`

## R 数据对象

R 用于存储数据的对象类型： 向量、矩阵、数组、数据框、列表；（在存储数据的格式、创建方式、结构复杂度、定位和访问个别元素的标记等方面均布同。

**对象**是指可以赋值给变量的任何事务（常量、数据结构、函数、图像，一切皆对象）；

数据类型：描述一个变量内元素取值的类型。
对象类型是指 R 语言组织和管理内部元素的不同方式。

| 对象类型 | 数据类型            | 是否允许数据类型                  |
|------|-----------------|---------------------------|
| 向量   | 数值型、复数型、字符型、逻辑型 | 否                         |
| 因子   | 数值型、复数型、字符型、逻辑型 | 否                         |
| 数组   | 数值型、复数型、字符型、逻辑型 | 否                         |
| 矩阵   | 数值型、复数型、字符型、逻辑型 | 否                         |
| 数据框  | 数值型、复数型、字符型、逻辑型 | 列内元素数据类型必须相同；不同列内数据类型可以不同 |
| 列表   | 数值型、复数型、字符型、逻辑型 | 任何元素的是数据类型均可不同            |
| 时间序列 | 数值型、复数型、字符型、逻辑型 | 不允许                       |


* 字符串：使用 `'` 或 `"` 包裹
* 逻辑型： `TRUE`/`T` `FALSE`/`F`

> 类型转换：
> 
> * 数值型 + 字符型 => 字符型【数值型变成字符型】
> * 数值型 + 逻辑型 => 数值型【逻辑型变成 `0`-`FALSE` / `1`-`TRUE` 数值型】
> * 字符型 + 逻辑型 => 字符型

对象属性的判定和转换：

> 判断某个对象的类型或将对象转换成另一种类型；

* `methods(as)` `mehtods` 包中全部转换函数
* `methods(is)` `methods` 包中全部对象类型判别函数

* `class()` 获取变量类型
* `mode()` 
* `typeof()` 获取变量类型

> 获取对象类型信息： `class()` < `mode()` < `typeof`

| 判断                | 转换                |
|-------------------|-------------------|
| `is.numeric()`    | `as.numeric()`    |
| `is.character()`  | `as.character()`  |
| `is.vector()`     | `as.vector()`     |
| `is.matrix()`     | `as.matrix()`     |
| `is.data.frame()` | `as.data.frame()` |
| `is.factor()`     | `as.factor()`     |
| `is.logical()`    | `as.logical()`    |

### 向量

**向量**以一维数组的方法管理数据的一种对象类型；

R 中向量起始位置为： `1`

> **向量**是 R 语言的基本数据类型；很多算法函数都以向量的**形式输入**。

> 运算`-` `+` `*` `/`: 向量各元素相加构成向量；如果长度不同，R 利用循环原则，重复较短的向量，将长度补齐到相等长度，然后运算(长短之间长度不是**整数倍数**时，会产生**警告**)；


* `c()` : 创建向量
* `length()` : 获取向量长度
* `which(expression)` 向量中满足 `expression` 的索引位置
* `which.max(vector)` 向量 `vector` 的最大值索引位置
* `which.min(vector)` 向量 `vector` 的最小值索引位置
* `max(vector)` 向量 `vector` 元素的最大值
* `min(vector)` 向量 `vector` 元素的最小值

> R 语言最强大的方面是**函数向量化**，这些函数可以直接对向量的每个元素进行操作。

* `ifelse(condition, true-result, false-result)`: `condition` 为 `TRUE` 返回 `true-result` 否则 `false-result`

* **序列**
  * `seq()`: 产生等差序列

    `seq(from = 1, to = 1, by = ((to -from)/(length.out - 1)), length.out = NULL, along.with = NULL, ...)`
    * `from` 等差数列的首选数据（默认 `1` ）
    * `to` 等差数列尾项数据（默认 `1` ）
    * `by` 等差的数值
    * `length.out` 产生向量的长度

  * `rep()`: 讲一个向量重复若干次

    `rep(x, times, ...)` # `x` 预重复序列， `times` 重复的次数
    * `x` 预重复序列
    * `times` 序列重复次数
    * `each` 序列每个元素重复次数
    * `length.out` 生辰序列的长度

* 子集： `[]` (向量的部分元**索引向量**素（也称为**子集**、**下标**、**切片**）) 填写索引向量：
  * `-index` < 0 表示子集不包含 `index` 位置
  * `index` > 0 表示子集包含该位置索引
  * 索引向量为逻辑向量时，返回索引位置对应为 `TRUE` 的元素构成的向量 -- **可以使用逻辑表达式**
  * 对已命名的向量，给向量传入命名的字符向量，将会返回向量中包含这些名字的元素切片

  > 提取子集时，索引向量不可以整数与负数同时使用。


### 因子

简单、紧凑的形式来处理分类（名义）数据；
**水平**：表示因子所有可能的取值；

因子分为：**有序因子**、**无序因子**

> 如果数据集有取值固定的名义变量，因子特别有用。

* `factor(x = character(), levels, labels = levels, exclude = NA, ordered = is.ordered(x), nmax = NA)` 创建因子
  * `x` 表示需要创建为因子的向量数据
  * `levels` 表示所创建的因子数据的水平；如果不指定，则为向量中所有不重复值
  * `labels` 用于表示水平的名称（方便用户识别），与水平一一对应
  * `exclude` 表示需要排除的水平
  * `ordered` (**逻辑变量**) `TRUE` 表示有序因子， `FALSE` 表示无序因子(**默认**)
  * `nmax` 表示水平个数的上限
* `levels(x)` 查看因子 `x` 的水平
* `gl(n, k, length = n*k, labels = seq_len(n), ordered = FALSE)` 用于生成带有因子的序列
  * `n` 因子水平的个数
  * `k` 表示每个水平的重复次数
  * `length` 表示生成的因子长度
  * `labels` 是一个 `n` 维向量，表示因子水平
  * `ordered` **逻辑值** `TRUE` 表示有序因子， `FALSE` 表示无序因子(**默认**)

### 矩阵

矩阵 `matrix()` 描述二维数据；矩阵使用两个下标来访问元素。

* `matrix(data = NA, nrow = 1, ncol = 1, byrow = FALSE, dimnames = NULL)` 创建矩阵
  * ``

> `mtx[rIndex, cIndex]`: 
>   * `rIndex`: 矩阵 `mtx` 的 `rIndex` 行
>   * `cIndex`: 矩阵 `mtx` 的 `cIndex` 列

* `dim(mtx)` 
* `nrow(mtx)`
* `ncol(mtx)`
* `colSums(mtx)`
* `colMeans(mtx)`
* `rowSums(mtx)`
* `rowMeans(mtx)`
* `sum(mtx)`
* `mean(mtx)`
* `as.factor(mtx)` 矩阵转变为因子
* `as.vector(mtx)`
* `paste0()` 

**矩阵**是具有两个维度的特殊向量。

### 数组

数组 `array()` 描述多维数据；数组的特征属性：维数向量(`dim` 属性)，描述多维数组的维数， `dim` 内元素对应维度的长度。

* `array()` 创建数组

### 列表

列表 `list()` 是二维数据，存储的元素的数据类型可以不同，列表内的长度也可以不同。

* `list(...)` 创建列表对象
  * `listName$fileName` 提取子属性
* `unlist(listName)`: 将 `list` 对象 `listName` 转换为 `vector` 
* `summary(listname)` 用于生成各种模型拟合函数的结果摘要
* `names(listname)` 查看列表内容

### 数据框

数据框 `data.frame()` 是二维数据，存储的元素的数据类型可以不同，列长度必须相同。

`data.frame()` 把多个向量建立为一个数据框，并为列设置名称


* `data.frame(..., row.names = NULL, check.rows = FALSE, check.names = TRUE, fix.empty.names = TRUE, stringsAsFactors = FALSE)` 创建数据框
  * ``
  * ``
* `is.data.frame(df)` 判断 `df` 是否是数据框
* `as.data.frame(mtx)` 将矩阵转换为数据框
* `dim(dtf)` 查看数据框 `dtf` 的维度信息
* `nrow(dtf)` 查看数据框 `dtf` 的行数
* `nclo(dtf)` 查看数据框 `dft` 的列数
* `names(dtf)` 查看数据框 `dtf` 的列名称
* `colnames(dtf)` 查看数据框 `dtf` 列名称
* `rownames(dtf)` 查看数据框 `dtf` 行名称
