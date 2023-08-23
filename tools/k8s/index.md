# Kubernetes

## 概述

Kubernetes 功能：
* **服务发现**和**负载均衡**

  Kubernetes 可以使用 DNS 名称或自己的 IP 地址暴露容器。
  如果进入容器的流量很大，Kubernetes 可以负载均衡并分配网络流量，从而使部署稳定。

* **存储编排**

  Kubernetes 允许你自动挂载选择的存储系统（例如本地存储、公有云提供商等）。

* **自动部署**和**回滚**

  可以使用 Kubernetes 描述已部署容器的所需状态，它可以以受控的速率将实际状态更改为期望状态（例如可以自动化 Kubernetes 来部署创建新容器，删除现有容器并将它们的所有资源用于新容器）。

* **自动完成装箱计算**

  为 Kubernetes 提供许多节点组成的集群，在这个集群上运行容器化的任务（可以高速 Kubernetes 每个容器需要的 CPU 与 RAM ， Kubernetes 可以将容器按实际情况调度到你的节点上，以最佳方式利用你的资源）。

* **自我修复**

  Kubernetes 将重启启动失败的容器、替换容器、杀死不响应用户定义的运行状况检查的容器，并且在准备好服务之前不将其通告给客户端。

* **密钥与配置管理**

  Kubernetes 允许你存储和管理敏感信息（例如密码、 OAuth 令牌和 SSH 密钥）。

### Kubernetes 对象

> * 在 Kubernetes API 中对象是如何表示 Kubernetes 对象的
> * 如何使用 `.yaml` 格式的文件表示 Kubernetes 对象

#### 理解 Kubernetes 对象

在 Kubernetes 系统中， **Kubernetes 对象**是**持久化的实体**。
Kubernetes 使用这些实体去表示整个集群的状态。
Kubernetes 对象具体描述了如下信息：

* 哪些容器化应用正在运行（以及在哪些节点上运行）
* 可以被应用使用的资源
* 关于应用运行时行为的策略（比如重启策略、升级策略以及容错策略）

> Kubernetes 对象是一个种**意向表达**：
> 
> 一旦创建该对象， Kubernetes 系统将不断工作以确保该对象存在。
> 本质上是在告知 Kubernetes 系统想要的集群工作负载状态看起来是什么样子，即 Kubernetes 集群所谓的**期望状态**。

操作 Kubernetes 对象(创建、修改、删除)——通过使用 Kubernetes API 实现。

##### 对象*规约(`Spec`)*与*状态(`Status`)*

几乎每个 Kubernetes 对象包含两个嵌套的对象字段：对象 **`spec`(规约)** 和对象 **`status`(状态)** 负责管理对象的配置。

* `spec` 规约： 在**创建对象时设置**其内容，描述希望对象具有的特征(**期望状态(Desired State)**)
* `status` 状态：描述对象的**当前状态(Current State)**，由 Kubernetes 系统和组件设置并更新

在任何时刻， Kubernetes **控制平面**都一直在积极地管理对象的实际状态(`status`)，以使之达成期望状态(`spec`)。

> 例如：Kubernetes 中的 Deployment 对象能够表示运行在集群中的应用。
> 
> 当创建 Deployment 时，你可能设置的 `spec` ：3个副本运行。
> Kubernetes 系统读取 Deployment 的 `spec` 并启动期望的 3 个实例的应用 —— 更新状态以与规约相匹配。
> 如果实例中有失败， Kubernetes 系统会通过执行修正操作来响应 `spec` 和 `status` 间的不一致 —— 会启动一个新的实例来替换。

##### 描述 Kubernetes 对象

创建 Kubernetes 对象时，必须提供对象的 `spec` ，用来描述对象的期望状态，以及关于对象的一些基本信息。
当使用 Kubernetes API 创建对象时（直接创建或经由 `kubectl` 创建）， API 请求必须在请求主体中包含 JSON 格式的信息。
**大多数情况下需要提供 `.yaml` 文件为 `kubectl` 提供这些信息**（`kubectl` 将这些信息转换成 JSON 格式）。

示例：
```yaml
apiVersion: apps/v1
kind: Deployment
metadata:
  name: nginx-deployment
spec:
  selector:
    matchLabels:
      app: nginx
  replicas: 2 # 告知 Deployment 运行 2 个与该模板匹配的 Pod
  template:
    metadata:
      labels:
        app: nginx
    spec:
      containers:
      - name: nginx
        image: nginx:1.14.2
        ports:
        - containerPort: 80
```

运行示例对象：
```shell
kubectl apply -f https://k8s.io/examples/application/deployment.yaml
```
输出结果：
```text
deployment.apps/nginx-deployment created
```

##### 必须字段

Kubernetes 对象对应的 `.yaml` 文件中，需要配置的字段：

* `apiVersion`: 创建该对象所使用的 Kubernetes **API 版本**
* `kind`: 创建对象的类别
* `metadata`: 帮助唯一标识对象的一些数据(`name` `UUID` `namespace`)
* `spec`: 期望对象状态

对每个 Kubernetes 对象而言，其 `spec` 的精确格式是不同的，包含特定于该对象的嵌套字段。
对象具体的规约 `spec` 格式参考 [Kubernetes API]() 。

#### 服务器端字段验证

从 Kubernetes v1.25 开始， API 服务器提供服务器端**字段验证**，可以检测对象中**未被识别**或**重复的字段**。

`kubectl` 工具使用 `--validate` 标志来设置字段的验证级别。
它接受值 `ignore`(等同于 `false`)、`warn`和`strict`(等同于 `true`)。
`kubectl` 的默认验证设置为 `--validate=true` :

* `strict` : 严格的字段验证，验证失败时会报错
* `warn` : 执行字段验证，但错误会以警告的形式提供而不是拒绝请求
* `ignore` : 不执行服务器端字段验证

> 当 `kubectl` 无法连接到支持字段验证的 API 服务器时，它将回退为使用客户端验证。
> Kubernetes 1.27 及更高版本始终提供字段验证；较早的 Kubernetes 版本可能没有此功能。

### Kubernetes 组件

Kubernetes 中工作机器称为**节点**，会运行容器化应用程序，每个集群至少有一个工作节点。

工作节点会**托管** Pod ，而 Pod 就是**作为应用负载的组件**。
**控制平面**管理集群中的工作节点和 Pod 。

> 在生产环境中，**控制平面**通常跨多台计算机运行，一个集群通常运行多个节点，提供容错性和高可用性。

![Kubernetes 集群的组件](./components-of-kubernetes.svg)

#### 控制平面组件(Control Plane Components)

控制平面组件为集群做出全局决策（如资源的调度）；以及检测和响应集群事件。

> 控制平面组件可以在集群中的任何节点上运行，为了简单期间，设置脚本通常会在同一个计算机上启动所有控制平面组件，并且不会在此计算机上运行用户容器。
> 关于跨多机器控制平面设置参阅：使用 kubeadm 构建高可用集群。

##### Kubernetes API server:`kube-apiserver`

**API 服务器**是控制平面组件，负责公开 Kubernetes API 。
AIP 服务器是 Kubernetes 控制平面的**前端**。

Kubernetes API 服务器的主要实现是 [`kube-apiserver`]() 。
`kube-apiserver` 设计上考虑了水平扩展，即可以通过部署多个示例来进行扩缩，并在这些实例之间平衡流量。

##### `etcd`

一致且高可用的键值存储，用作 Kubernetes 所有集群数据的后台数据库。

如果 Kubernetes 集群使用 etcd 作为后台数据库，请确保针对这些数据有一份[备份]()计划。

##### `kube-scheduler`

`kube-scheduler` 是控制平面的组件，负责监视新创建的、未指定运行节点(node)的 Pod(表示集群上一组正在运行的容器) ，并选择节点来让 Pod 在上面运行。

调度决策考虑的因素包括单个 Pod 及 Pod 集合的资源需求、软硬件及策略约束、亲和性及反亲和性规范、数据位置、工作负载的干扰及最后时限。

##### `kube-controller-manager`

`kube-controller-manager` 是控制平面的组件，负责运行**控制器**进程。

从逻辑上讲，每个控制器都是一个单独的进程，但是为了降低复杂性，它们都被编译到同一个可执行文件，并在同一个进程中运行。

控制器类型：
* 节点控制器 (Node Controller): 负责在节点出现故障时运行通知和响应
* 任务控制器 (Job Controller): 监测代表一次性任务的 Job 对象，然后创建 Pod 来运行这些任务直到完成
* 端点分片控制器 (EndpointSlice Controller): 填充端点分片 (EndpointSlice) 对象（以提供 Service 和 Pod 之间的链接）
* 服务账号控制器 (ServiceAccount Controller): 为新的命名空间创建默认的服务账号(ServiceAccount)

##### `cloud-controller-manager`

`cloud-controller-manager` (云控制器)是 Kubernetes 控制平面组件，潜入了特定于云平台的控制逻辑。
云控制器管理器允许你将你的集群连接到云提供商的 API 之上，并将于该云平台交互的组件同与你的集群交互的组件分离开来。

> `cloud-controller-manager` 仅运行特定于云平台的控制器，如果在自己的环境中运行 Kubernetes ，或者在本地计算机中运行，所有部署的集群都不需要有**云控制器管理器**。
> 
> `cloud-controller-manager` 与 `kube-controller-manager` 类似，将若干逻辑上独立的控制回路组合到同一个可执行文件中，供你以同一进程的方式运行。
> 可以执行水平扩容，以提升性能或者增强容错能力。

控制器类型：
* 节点控制器 (Node Controller): 用于在节点终止响应后检查云提供商以确定节点是否已被删除
* 路由控制器 (Route Controller): 用于在底层云基础架构中设置路由
* 服务控制器 (Service Controller): 用于创建、更新和删除云提供商负载均衡器

#### `Node` 组件

节点组件会在每个节点上运行，负责维护运行的 Pod 并提供 Kubernetes 运行环境。

##### `kubelet`

`kubelet` 会在集群中每个节点(node)上运行。
它保证**容器(containers)**都运行在 Pod 中。

`kubelet` 接收一组通过各类机制提供给它的 **PodSpecs** ，**确保这些 PodSpecs 中描述的容器处于运行状态且健康**。
`kubelet` **不会管理**不是由 Kubernetes 创建的容器。

##### `kube-proxy`

`kube-proxy` 是集群中每个节点(node)上所运行的网络代理，实现 Kubernetes 服务(Service)概念的一部分。

`kube-proxy` 维护节点上的一些网络规则，这些网络规则会允许从集群内部或外部的网络会话与 Pod 进行网络通信。

如果操作系统提供了可用的数据包过滤，则 `kube-proxy` 会通过它来实现网络规则。
否则， `kube-proxy` 仅作流量转发。

##### 容器运行时 `Container Runtime`

容器运行环境是负责运行容器的软件(`containerd` `CRI-O` 等任何符合 Kubernetes CRI(容器运行环境接口) 的实现)。

### Kubernetes API

Kubernetes 控制平面的核心是 API 服务器(`apiserver`)。
API 服务器负责提供 **HTTP API** ，以供用户、集群中的不同部分和集群外部组件相互**通信**。

Kubernetes API 使你可以在 Kubernetes 中查询和操纵 API 对象(例如 Pod、Namespace、ConfigMap 和 Event)的状态。

大部分操作都可以通过 `kubectl` 命令行接口或类似 `kubeadm` 类命令行工具执行。

## 架构

### 节点

### 节点与控制面之间的通信

### 控制器

### 租约(Lease)

### 云控制器管理器

### 关于 cgroup v2

### 容器运行时接口 CRI

### 垃圾回收

### 混合版本代理


https://kubernetes.io/zh-cn/docs/tutorials/