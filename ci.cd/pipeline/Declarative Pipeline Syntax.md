# Declarative Pipeline Syntax: 声明式管道语法

所有有效的声明式流水线必须包含在一个 `pipeline` 块中。

> 声明式流水线中有效的基本语法和表达式遵循与 Groovy 语法同样的规则：
>
> * 流水线顶层必须是一个 _block_ (`pipeline { }`)
> * 不使用分号作为语句分隔符，每条语句必须都在自己的行上
> * 块只能由 `Sections` `Directives` `Steps` 或**赋值语句**构成
> * 属性引用语句被视为无参方法调用

## Sections: 节段

节段通常包含一个或多个 `Directives` `Steps` 。

### `agent`: 代理

`agent` 部分指定了整个流水线或特定 `stage` 在 Jenkins 环境中执行的位置，这取决于代理部分的放置位置。

`agent` 必须在 `pipeline` 块内的顶层定义， `stage` 级的使用是可选的。

> 顶级 `agent` 与 `stage` 级的 `agent` 差异：
> 
> todo:

* 参数：
    * `any`: 在任何可用的代理上执行流水线(`pipeline`)或 `stage`
    * `none`: 在 `pepeline` 块的顶部没有全局代理；该参数将会被分配到整个流水线的运行中，每个 `stage` 部分都需要自己的 `agent`
    * `label`: 在提供了标签的 Jenkins 环境中可用的代理上执行 `pipeline` 或 `stage`
    * `node`: 行为与 `label` 相同，但是节点允许其他选项(如 `customWorkspace`)
    * `docker`: 使用给定的容器执行 `pipeline` 或 `stage` ；
  
        该容器将在预置的 node 上，或在匹配可选定义的 `label` 参数上，动态的供应来接受基于 Docker 的流水线。
    * `dockerfile`: 执行 `pipeline` 或 `stage` ，使用从源码库包含的 `Dockerfile` 构建的容器。

### `post`

`post` 部分定义一个或多个 `steps` ，这些阶段()根据 `pipeline` 或 `stage` 的完成情况

* `always`: 无论 `pipeline` 或 `stage` 完成情况如何，都允许在 `post` 中的部分运行
* `changed`: 只有当 `pipeline` 或 `stage` 的完成状态与之前的运行不同时，才允许再 `post` 中的部分运行
* `failure`: 只有当 `pipeline` 或 `stage` 的完成状态是 `failure` ，才允许在 `post` 部分运行该步骤
* `success`: 只有当 `pipeline` 或 `stage` 的完成状态是 `success` ，才允许在 `post` 部分运行该步骤
* `unstable`: 只有当 `pipeline` 或 `stage` 的完成状态是 `unstable` ，才允许在 `post` 部分运行该步骤
* `aborted`: 只有当 `pipeline` 或 `stage` 的完成状态是 `aborted` ，才允许在 `post` 部分运行该步骤

### `stages`

包含一些列一个或多个 `stage` 指令， `stages` 部分是流水线描述的大部分 **work** 的位置。

建议 `stages` 至少包含一个 `stage` 指令用于连续交付过程的每个离散部分（如 构建、测试和部署）。

### `steps`

`steps` 部分在给定的 `stage` 指令中执行定义了一系列的一个或多个 `steps`

## Directives: 


### `environment`: 黄静变量


### `options`

### `parameters`

### `triggers`

### Jenkins cron syntax

### `stage`

### `tools`

### `input`

### `when`

## Parallel: 并行

- todo:

## Matrix: 矩阵

- todo:

## Steps: 

### `script`
