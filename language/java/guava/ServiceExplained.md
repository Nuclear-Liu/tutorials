# Service

Guava `Service` 接口表示一个具有运行状态的对象，并提供启动和停止的方法。
管理服务状态（需要适当的启动和关闭管理）并非易事，尤其是在涉及多个线程或调度的情况下。
Guava 提供了一些骨架为管理状态逻辑和同步细节。

## 使用服务

`Service` 的正常生命周期：

* `Service.State.NEW`
* `Service.State.STARTING`
* `Service.State.RUNNING`
* `Service.State.STOPPING`
* `Service.State.TERMINATED`
* `Service.State.FAILED`

已停止的服务可能无法重新启动。
如果服务在启动、运行或停止时失败，它将进入状态： `Service.State.FAILED` 。

如果服务状态为 `NEW` ，则可以使用 `startAsync()` 异步启动服务。
因此应该为应用程序中的每个服务设置一个唯一的**启动位置**。

停止服务的方法与此类似，使用**异步** `stopAsync()` 方法。
与 `startAsync()` 方法不同，多次调用该方法时安全的。
这是为了处理关闭服务时可能出现的竞争。

* **异步**使用 `addListener()` ：

    `addListener()` 允许添加一个 `Service.Listener` ，该 `Service.Listener` 将在服务的每次状态转换时调用。
    **注意：如果在添加监听器时服务状态不是 `NEW` ，则任何已经发生的状态转换都不会在监听器上重播**
* **同步**使用 `awaitRunning()` ：

    这是不可中断的，不会引发任何已检查的异常，并在服务完成启动后返回。
    如果服务无法启动，则会抛出一个 `IllegalStateException` 异常。
    类似地， `awaitTerminated()` 等待服务达到结束状态(`TERMINATED` 或 `FAILED`)。
    这两种方法都有允许指定超时的重载。

`Service` 接口微妙且复杂。
**不建议直接实现它。**
请使用 Guava 中的一个抽象基类作为实现的基础。
每个基类都支持特定的**线程模型**。

## 实现类

### `AbstractIdleService`

`AbstractIdleService` 框架实现了一个 `Service` ，它在 `running` 状态下不需要执行任何操作，因此在运行时不需要线程，但需要执行**启动**和**关闭**操作。
实现这样的服务非常简单，只需要扩展 `AbstractIdleService` 并实现 `startUp` 和 `shutDown()` 方法即可。

### `AbstractExecutionThreadService`

`AbstractExecutionThreadService` 在单个线程中执行启动、运行和关闭操作。
必须重载 `run` 方法，而且必须相应停止请求。
例如，可以在工作玄幻中执行操作：

```jshelllanguage
@Override
public void run() {
    while(isRunning()) {
        // perform a unit of work
    }
}
```

或者，可以以任何方式覆盖，使 `run()` 返回。

重载 `startUp()` 和 `shutDown()` 是可选的，但服务状态会为您管理。

**注意： `start()` 会调用 `startUp()` 方法，为您创建一个线程，并在该线程中调用 `run()` 。
`stop()` 调用 `triggerShutdown()` 并等待线程死亡。**

### `AbstractScheduledService`

`AbstractScheduledService` 在运行是执行一些定期任务。
子类实现 `runOneIteration()` 来指定任务的一个迭代。
以及熟悉的 `startUp` 和 `shutDown` 方法。

要描述执行计划，必须实现 `scheduler()` 方法。
通常，使用 `AbstractScheduledService.Scheduler` 中提供的调取之一，即 `newFixedRateSchedule(initialDelay, delay, TimeUnit)` 或 `newFixedDelaySchedule(initialDelay, delay, TimeUnit)` ，对应于 `ScheduledExecutorService` 中熟悉的方法。

### `AbstractService`

当需要自己手动进行线程管理时，请直接继承 `AbstractService` 。

要继承 `AbstractService` 必须实现 2 个方法：

* `doStart()` 由第一次调用 `startAsync()` 直接调用， `doStart` 方法应该执行所有初始化，如果启动成功，最终调用 `notifyStarted()` ，如果启动失败，调用 `notifyFailed()` 。
* `doStop()` 由第一次调用 `stopAsync()` 直接调用， `doStop` 方法应该关闭服务，如果关闭成功，最终调用 `notifyStopped()` ，如果关闭失败，调用 `notifyFailed()` 。

`doStart` 和 `doStop` 方法应该很快。
如果需要执行昂贵的初始化，则应考虑将工作移至另一个线程。

## 使用 `ServiceManager`

`ServiceManager` 使涉及多个 `Service` 实现的某些操作变得更容易。
创建一个新的 `ServiceManager` 类，其中包含一个 `Service` 集合。

* `startAsync()` 启动所管理的所有服务。与 `Service.startAsync()` 一样，如果所有服务都是 `NEW` ，则只能调用该方法一次。
* `stopAsync()` 将停止管理下的所有服务。
* `addListener()` 将添加一个 `ServiceManager.Listener` ，在主要状态转换时调用。
* `awaitHealthy()` 将等待所有服务达到 `RUNNING` 状态
* `awaitStopped()` 将等待所有服务达到 `TERMINATED` 状态

* `isHealthy()` 如果服务都处于 `RUNNING` 返回 `true`
* `servicesByState()` 返回以状态为索引的所有服务的一致快照
* `startupTimes()` 返回一个映射，从所管理的 `Service` 到该服务启动所需的时间（以毫秒为单位）。返回的映射保证按启动时间排序。

`ServiceManager` 执行的唯一要求是，在构建 `ServiceManager` 时，所有 `Service` 都必须是 `NEW` 状态。
