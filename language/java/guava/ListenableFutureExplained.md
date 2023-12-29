# `ListenableFuture`

Guava 用 `ListenableFuture` 扩展 JDK 的 `Future` 接口。

强烈建议在所有代码中始终使用 `ListenableFuture` 而不是 `Future` ：

* 大多数 `Futures` 方法都需要它
* 这比以后更改为 `ListenableFuture` 更容易
* 工具方法的提供者不需要提供其方法的 `Future` 和 `ListenableFuture` 变体

## 接口

传统的 `Future` 表示异步执行的结果：
一个 `Future` 可以是一个正在进行的计算的句柄，也可以是一个服务向我们提供结果的承诺。

通过 `ListenableFuture` 可以注册回调，以便在计算机完成后立即执行，如果计算机已经完成，则立即执行。
通过这一简单的补充，可以有效地支持许多基本的 `Future` 接口无法支持的操作。

## 添加回调

大多数用户更喜欢用 `Futures.addCallback(ListenableFuture<V>, FutureCallback<V>, Executor)` 。
一个 `FutureCallback<V>` 实现两个方法：

* `onSuccess(V)` 如果未来成功，根据其结果执行的操作
* `onFailure(Throwable)` 如果未来失败，根据失败结果执行的操作

## 创建

与 JDK `ExecutorService.submit(Callable)` 启动异步计算的方法相对应， Guava 提供了 `ListenableExecutorService` 接口，该接口返回 `ListenableFuture` ，而 `ExecutorService` 返回正常的 `Future` 。
要将 `ExecutorService` 转换为 `ListeningExecutorService` 只需使用 `MoreExecutors.listeningDecorator(ExecutorService)` 。

```jshelllanguage
ListeningExecutorService service = MoreExecutors.listeningDecorator(Executors.newFixedThreadPool(10));
ListenableFuture<Explosion> explosion = service.submit( new Callable<Explosion>() {
        @Override 
        public Explosion call() {
            return pushBigRedButton();
        }
    });
Futures.addCallback(explosion, new FutureCallback<Explosion>() {
        @Override // we want this handler to run immediately after we push the big red button!
        public void onSuccess(Explosion explosion) {
            walkAwayFrom(explosion);
        }
        @Override
        public void onFailure(Throwable thrown) {
            battleArchNemesis(); // escaped the explosion!
        }
    }, service);
```

另外，如果是基于 `FutureTask` 的 API 进行转换， Guava 提供了 `ListenableFutureTask.create(Callable<V>)` 和 `ListenableFutureTask.create(Runnable, V)` 。
与 JDK 不同， `ListenableFutureTask` 并不打算直接扩展。

如果更喜欢设置未来值的抽象概念，而不是实现计算未来值的方法，请考虑扩展 `AbstractFuture<V>` 或直接使用 `SettableFuture` 。

如果必须将其他 API 提供的 `Future` 转换为 `ListenableFuture` ，可能别无选择，只能使用重量级的 `JdkFutureAdapters.listenInPoolThread(Future)` ，将 `Future` 转换为 `ListenableFuture` 。
如果可以最好修改原始代码以返回 `listenableFuture` 。

## 应用

使用 `ListenableFuture` 的最重要的原因是，可以使用复杂的异步操作链。

```jshelllanguage
ListenableFuture<RowKey> rowKeyFuture = indexService.lookUp(query);
AsyncFunction<RowKey, QueryResult> queryFunction = new AsyncFunction<RowKey, QueryResult>() {
        public ListenableFuture<QueryResult> apply(RowKey rowKey) {
            return dataService.read(rowKey);
        }
    };
ListenableFuture<QueryResult> queryFuture = Futures.transformAsync(rowKeyFuture, queryFunction, queryExecutor);
```

使用 `ListenableFuture` 可以高效地支持许多其他操作，而仅使用 `Future` 则无法支持这些操作。
不同的操作可能由不同的执行器执行，一个 `ListenableFuture` 可能由多个操作在等待。

当多个操作应在另一个操作开始后立即开始时（即**扇出**）， `ListenableFuture` 就能正常工作：
它会触发所有请求的回调。
只要稍加努力，就能实现**扇入**，或在其他几个未来操作全部完成后立即触发一个 `ListenableFuture` 进行计算：
参阅 `Futures.allAsList` 的实现示例。

| 方法                                                                   | 描述                                                                                                | 参见                                                         |
|----------------------------------------------------------------------|---------------------------------------------------------------------------------------------------|------------------------------------------------------------|
| `transformAsync(ListenableFuture<A>, AsyncFunction<A, B>, Executor)` | 返回一个新的 `ListenableFuture` ，其结果是将给定的 `AsyncFuntion` 应用于给定的 `ListenableFuture` 的结果的乘积               | `transformAsync(ListenableFuture<A>, AsyncFunction<A, B>)` |
| `transform(ListenableFuture<A>, Function<A, B>, Executor)`           | 返回一个新的 `ListenableFuture` ，其结果是将给定的 `Function` 应用于给定的 `ListenableFuture` 的结果的乘积                   | `transform(ListenableFuture<A>, Function<A, B>)`           |
| `allAsList(Iterable<ListenableFuture<V>>)`                           | 返回一个新的 `ListenableFuture` ，其值是一个列表其中包含每个输入 `Future` 按顺序排列。如果任何输入 `Future` 失败或被取消则该 `Future` 失败或取消 | `allAsList(ListenableFuture<V>...)`                        |
| `successfulAsList(Iterable<ListenableFuture<V>>)`                    | 返回一个 `ListenableFuture` ，其值是一个列表，其中按顺序包含每个成功输入 future 的值。与失败或取消的 future 相对应的值将替换为 `null` 。        | `successfulAsList(ListenableFuture<V>...)`                 |

`AsyncFunction` 提供了一种方法 `ListenableFuture<B> apply(A input)` 它可用于异步转换值。

```jshelllanguage
List<ListenableFuture<QeryResult>> queries;
// The queries go to all different data centers, but we want to wait until they're all done or failed.
ListenableFuture<List<QueryResult>> successfulQueries = Futures.successfulAsList(queries);

Futures.addCallback(successfulQueries, callbackOnSuccessfulQueries);
```

## 避免 `Futures` 嵌套

在代码调用泛型接口返回 `Future` 的情况下，可能会以嵌套 `Future` 结束。

```jshelllanguage
executorService.submit(new Callable<ListenableFuture<Foo>() {
    @Override
    public ListenableFuture<Foo> call() {
        return otherExecutorService.submit(otherCallable);
    }
});
```

将返回 `ListenableFuture<ListenableFuture<Foo>>` 。
这段代码是不正确的，因为如果外在 future 的 `cancel` 与外在的 future 的完成相冲突，那么这种取消将不会传播到内在的 future 。
使用 `get()` 或监听器检查其他 future 的失败也是一个常见的错误，但除非特别小心，否则从 `otherCallable` 抛出的异常将被抑制。
为了避免这种情况， Guava 的所有 future 处理方法（以及 JDK 中的一些方法）都有 `*Async` 版本，可以安全地解压缩这个嵌套—— 
`transform(ListenableFuture<A>, Function<A, B>, Executor)` 
`transformAsync(ListenableFuture<A>, AsyncFunction<A, B>, Executor)`
`ExecutorService.submit(Callable)` `submitAsync(AsyncCallable<A>, Executor)` 。
