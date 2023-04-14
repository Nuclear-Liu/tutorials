# `java.util.concurrent.FutureTask`

表示一个可被取消的异步计算任务。

`Future` 接口的基本实现（对应方法的实现）。
只有在计算完成后才可以检索结果，如果计算尚未完成，调用 `get()` 的线程阻塞等待结果。
一旦任务开始执行，将不能重新开始或取消，除非调用 `runAndReset()` 。

`FutureTask` 可用于包装 `Callable` 或 `Runnable` 对象。
因为 `FutureTask` 实现了 `Runnable` 所以一个 `FutureTask` 可以提交给一个 `Executor` （线程池）执行。

> `FutureTask` 当使用 `ThreadPoolExecutor` 的 `submit()` 时的返回值：
> 执行成功返回 `null` 。
> 
> Submits a Runnable task for execution and returns a Future representing that task.
> The Future's get method will return `null` upon _successful_ completion.

* `public FutureTask(Callable<V> callable)`
* `public FutureTask(Runnable runnable, V result)`
