# Pipeline

Jenkins 流水线（Jenkins 的头等公民）是一套插件支持实现和集成 `continuous delivery pipelines`(持续交付管道) 到 Jenkins 。

`continuous delivery(CD) pipelines` 用于版本控制向用户\客户交付软件。

流水线提供一组可扩展的工具基于 [`Pipeline domain-specific language(DSL) syntax`]() 对交付流水线进行建模。

对 Jenkins 流水线的定义支持写在一个文本文件中( 名称为: `Jenkinsfile` ，文件的方式便于使用版本控制)，即**流水线即代码**。

> `Jenkinsfile` 版本控制的优势：
> 
> * 为所有分支和拉取请求自动创建流水线构建过程
> * 对流水线进行代码审查/迭代（以及剩余的源代码）
> * 对流水线进行审计跟踪
> * 流水线的真正源代码可以被项目的多个成员查看和编辑

使用基于版本控制的 `Jenkinsfile` 定义流水线是最佳实践。

`Jenkinsfile` 支持两种语法进行编写：**声明式**和**脚本化**；声明式流水线与 Jenkins 流水线特性更相似。

流水线特征：
* **Code**: 流水线用代码实现，基于版本控制使团队能够编辑、审查和迭代它们的交付流水线
* **Durable**: 流水线可以承受 Jenkins 控制器计划内和计划外的重启
* **Pausable**: 流水线可以有选择地停止或等待输入（批准）后继续运行流水线
* **Versatile**: 流水线支持复杂的实际 CD 需求，包括 fork/join 、循环和并行执行工作的能力
* **Extensible**: 流水线插件支持对 DSL 的自定义扩展、其他插件集成

## Pipeline concepts: 流水线相关概念

以下概念是 Jenkins 流水线的关键方面，与流水线语法紧密相连。

### Pipeline: 流水线

流水线是用户定义的一个 CD 流水线模型。

流水线代码定义了整个构建的过程（通常包括：构建、测试和交付应用程序）。

`pipeline` 块是[声明式流水线语法](./Declarative%20Pipeline%20Syntax.md)的关键部分。

### Node: 节点

节点是一个机器。

节点是 Jenkins 环境的一部分，能够执行一个流水线。

`node` 块是[脚本化流水线语法](./Scripted%20Pipeline%20Syntax.md)的关键部分。

### Sage: 阶段

`stage` 块定义在整个流水线执行任务（例如 "Build" "Test" "Deploy" 阶段）的概念。

### Step: 步骤

定义一个流水线中的具体的每项任务。


