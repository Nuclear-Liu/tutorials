# Throwables 

Guava 的 `Throwables` 工具可以简化对异常的处理。

## 异常 - 传播

当捕获到一个异常时，想把它扔给下一个一个 `try`/`catch` 块处理。
这种情况发生在 `RuntimeException` 或 `Error` 实例上，它们不需要 `try`/`catch` 块，但无意间可能被 `try`/`catch` 块所捕获。

Guava 提供了一些工具简化异常的传播：

| 签名                                                                         | 解释                                                                    |
|----------------------------------------------------------------------------|-----------------------------------------------------------------------|
| `void propagateIfPossible(Throwable, Class<X extends Throwable>) throws X` | 仅当 ` throwable` 是一个 `RuntimeException`/`Error`/`X` 时才原样抛出 `throwable` |
| `void throwIfInstanceOf(Throwable, Class<X extends Exception>) throws X`   | 仅当 `throwable` 是一个实例时原样抛出 `throwable`                                 |
| `void throwIfUnchecked(Throwable)`                                         | 仅当 `throwable` 时一个 `Runtime`                                          |

## 因果链

Guava 让研究因果链变得更加简单，它提供了三个有用的方法，其签名时不言自明的：

| 签名                                            | 解释                                                         |
|-----------------------------------------------|------------------------------------------------------------|
| `Throwable getRootCause(Throwable throwable)` | 返回 `throwable` 的最内部原因。                                     |
| `List<Throwable> getCausalChain(Throwable)`   | 获取 `throwable` 原因链的列表。列表中的第一个条目时 `throwable` ，后面是它的原因层次结构。 |
| `String getStackTraceAsString(Throwable)`     | 返回一个字符串，其中包含 `toString()` 的结果，后跟可抛出的完整递归堆栈跟踪。              |
