# `java.util.concurrent.Future<V>`

表示异步计算的结果。

提供检查计算是否完成、等待其完成以及检索计算结果的方法。
结果只能在计算完成后使用 `get()` 检索，必要时阻塞，直到它准备好。
取消通过 `cancel()` 取消执行。


## Method

* `boolean cancel(boolean mayInterruptIfRunning)`

  尝试取消任务的执行。

* `boolean isCancelled()`

  `isCancelled()` 确定任务是正常完成还是被取消。

* `boolean isDone()`

  任务是否完成（完成可能是正常终止、异常或取消）。

* `V get() throws InterruptedException, ExecutionException`

  阻塞获取计算结果。

* `V get(long timeout, TimeUnit unit) throws InterruptedException, ExecutionException, TimeoutException`

  指定超时时间 `timeout` 内获取计算结果。
