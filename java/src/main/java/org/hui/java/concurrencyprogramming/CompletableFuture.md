# `java.util.concurrent.CompletableFuture`

`public class CompletableFuture<T> implements Future<T>, CompletionStage<T>`

可以显式完成的 `Future` （设置其值和状态），并且可以用作CompletionStage ，支持在完成时触发的依赖函数和操作。

## `public interface CompletionStage<T>`

可能是异步计算的一个阶段，当另一个 `CompletionStage` 完成时执行一个操作或计算一个值。
一个阶段在其计算结束时完成，但这可能反过来触发其他相关阶段。
此界面中定义的功能仅采用几个基本形式，这些形式扩展到更大的方法集，已捕获一系列使用样式：

`CompletionStage` 接口的方法遵循：

* 当 `CompletableFuture` 任务完成后，同步使用任务执行线程来执行依赖任务结果的函数或者行为。
* 所有异步的方法在没有显式指定 `Executor` 参数时，都是复用 `ForkJoinPool.commonPool()` 线程池来执行。
* 所有 `CompletionStage` 方法的实现都是互相独立的，以便一个方法的行为不会因为重载了其他方法而受影响。

接口方法：

* `thenApply`: 返回一个新阶段('U')，当前一个阶段任务正常完成时，将执行新阶段设定的操作返回执行结果（可以获取上一个阶段的任务执行结果，并且可以返回新的结果）。
  * `CompletionStage<U> thenApply(Function<? super T,? extends U> fn)`
  * `CompletionStage<U> thenApplyAsync(Function<? super T,? extends U> fn)`
  * `CompletionStage<U> thenApplyAsync(Function<? super T,? extends U> fn, Executor executor)`

* `thenAccept`: 返回一个新阶段('Void')，当前一个阶段任务正常完成时，将执行新阶段设定的操作（可以获取到上一个阶段的任务执行结果）。
  * `CompletionStage<Void> thenAccept(Consumer<? super T> action)`
  * `CompletionStage<Void> thenAcceptAsync(Consumer<? super T> action)`
  * `CompletionStage<Void> thenAcceptAsync(Consumer<? super T> action, Executor executor)`

* `thenRun`: 返回一个新阶段(`Void`)，当前一个阶段任务正常完成时，将执行新阶段设定的操作（获取不到上一个阶段的任务执行结果）。
  * `CompletionStage<Void> thenRun(Runnable action)`
  * `CompletionStage<Void> thenRunAsync(Runnable action)`
  * `CompletionStage<Void> thenRunAsync(Runnable action, Executor executor)`

* `thenCombine`: 返回一个新阶段(`Void`)，当前一个阶段执行完成后执行 `other` 阶段任务，最后执行 `fn` （前两个阶段的返回值作为参数）。
  * `CompletionStage<V> thenCombine(CompletionStage<? extends U> other, BiFunction<? super T,? super U,? extends V> fn)`
  * `CompletionStage<V> thenCombineAsync(CompletionStage<? extends U> other, BiFunction<? super T,? super U,? extends V> fn)`
  * `CompletionStage<V> thenCombineAsync(CompletionStage<? extends U> other, BiFunction<? super T,? super U,? extends V> fn, Executor executor)`

* `thenAcceptBoth`:
  * `CompletionStage<Void> thenAcceptBoth(CompletionStage<? extends U> other, BiConsumer<? super T, ? super U> action)`
  * `CompletionStage<Void> thenAcceptBothAsync(CompletionStage<? extends U> other, BiConsumer<? super T, ? super U> action)`
  * `CompletionStage<Void> thenAcceptBothAsync(CompletionStage<? extends U> other, BiConsumer<? super T, ? super U> action, Executor executor)`

* `runAfterBoth`:
  * `CompletionStage<Void> runAfterBoth(CompletionStage<?> other, Runnable action)`
  * `CompletionStage<Void> runAfterBothAsync(CompletionStage<?> other, Runnable action)`
  * `CompletionStage<Void> runAfterBothAsync(CompletionStage<?> other, Runnable action, Executor executor)`

* `applyToEither`:
  * `CompletionStage<U> applyToEither(CompletionStage<? extends T> other, Function<? super T, U> fn)`
  * `CompletionStage<U> applyToEitherAsync(CompletionStage<? extends T> other, Function<? super T, U> fn)`
  * `CompletionStage<U> applyToEitherAsync(CompletionStage<? extends T> other, Function<? super T, U> fn, Executor executor)`

* `acceptEither`:
  * `CompletionStage<Void> acceptEither(CompletionStage<? extends T> other, Consumer<? super T> action)`
  * `CompletionStage<Void> acceptEitherAsync(CompletionStage<? extends T> other, Consumer<? super T> action)`
  * `CompletionStage<Void> acceptEitherAsync(CompletionStage<? extends T> other, Consumer<? super T> action, Executor executor)`

* `runAfterEither`:
  * `CompletionStage<Void> runAfterEither(CompletionStage<?> other, Runnable action)`
  * `CompletionStage<Void> runAfterEitherAsync(CompletionStage<?> other, Runnable action)`
  * `CompletionStage<Void> runAfterEitherAsync(CompletionStage<?> other, Runnable action, Executor executor)`

* `thenCompose`: 返回一个新的阶段(`U`)，当前一个阶段完成时，执行新阶段设定的操作（可以获取前一个阶段的接口，返回值与上一个阶段类型一致）。
  * `CompletionStage<U> thenCompose(Function<? super T, ? extends CompletionStage<U>> fn)`
  * `CompletionStage<U> thenComposeAsync(Function<? super T, ? extends CompletionStage<U>> fn)`
  * `CompletionStage<U> thenComposeAsync(Function<? super T, ? extends CompletionStage<U>> fn, Executor executor)`

* `handle`:
  * `CompletionStage<U> handle(BiFunction<? super T, Throwable, ? extends U> fn)`
  * `CompletionStage<U> handleAsync(BiFunction<? super T, Throwable, ? extends U> fn)`
  * `CompletionStage<U> handleAsync(BiFunction<? super T, Throwable, ? extends U> fn, Executor executor)`

* `whenComplete`:
  * `CompletionStage<T> whenComplete(BiConsumer<? super T, ? super Throwable> action)`
  * `CompletionStage<T> whenCompleteAsync(BiConsumer<? super T, ? super Throwable> action)`
  * `CompletionStage<T> whenCompleteAsync(BiConsumer<? super T, ? super Throwable> action, Executor executor)`

* `exceptionally`:
  * `CompletionStage<T> exceptionally(Function<Throwable, ? extends T> fn)`
  * `CompletionStage<T> exceptionallyAsync(Function<Throwable, ? extends T> fn)`
  * `CompletionStage<T> exceptionallyAsync(Function<Throwable, ? extends T> fn, Executor executor)`

* `exceptionallyCompose`:
  * `CompletionStage<T> exceptionallyCompose(Function<Throwable, ? extends CompletionStage<T>> fn)`
  * `CompletionStage<T> exceptionallyComposeAsync(Function<Throwable, ? extends CompletionStage<T>> fn)`
  * `CompletionStage<T> exceptionallyComposeAsync(Function<Throwable, ? extends CompletionStage<T>> fn, Executor executor)`

* `CompletableFuture<T> toCompletableFuture()`

## `public class CompletableFuture<T> implements Future<T>, CompletionStage<T>`

`CompletableFuture` 是一个可以通过编程方式显式的设置计算结果和状态以便让任务结束的 `Future` ，并且其可以作为一个 `CompletionStage` （计算阶段），当它的计算完成时可以触发一个函数或行为；
当多个线程企图调用同一个 `CompletableFuture` 的 `complete` `cancel` 方式时只有一个线程会成功。

`CompletableFuture` 除了包含有直接操作任务状态和结果的方法外，实现了 `CompletionStage` 接口。

### 方法：
* `runAsync()`

  实现无返回值的异步计算（异步日志打印、异步消息通知等）。
  * `public static CompletableFuture<Void> runAsync(Runnable runnable)`

    默认使用 `ForkJoinPool.commonPool()` 线程池执行。

  * `public static CompletableFuture<Void> runAsync(Runnable runnable, Executor executor)`

* `supplyAsync()`

  实现有返回值的异步计算（对原始数据进行加工，并需要获取到被加工后的结果）。
  * `public static <U> CompletableFuture<U> supplyAsync(Supplier<U> supplier)`

    默认使用 `ForkJoinPool.commonPool()` 线程池执行。

  * `public static <U> CompletableFuture<U> supplyAsync(Supplier<U> supplier, Executor executor)`

* `allOf()`

  等待所有的多个 `CompletableFuture` 并行任务执行完毕（返回值 `void` ）。
  * `public static CompletableFuture<Void> allOf(CompletableFuture<?>... cfs)`

* `anyOf()`

  等待多个任务中的任意一个执行完成返回，返回值为 `CompletableFuture<Object>`（为执行成功 `cfs` 的结果）。
  * `public static CompletableFuture<Object> anyOf(CompletableFuture<?>... cfs)`
