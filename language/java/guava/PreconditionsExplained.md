# 预处理 Preconditions

Guava 在 `Preconditions` 类中提供了许多预处理检查工具。
强烈建议导入这些工具。

**每种方法都有三种变体**：
1. 没有额外的参数。抛出的任何异常都没有错误信息。
2. 一个额外的 `Object` 参数。抛出的任何异常都带有错误信息。
3. 一个额外的 `String` 参数，带有任意数量的附加 `Object` 参数。（类似于 `printf` ，但为了 GWT 的兼容性和效率，它只允许 `%s` 标识符）。
	
**注意**： `checkNotNull` `checkArgument` `checkState` 有大量的重载，采用原始参数和 `Object` 参数的组合，而不是 

| 签名（不包括额外的参数）                                         | 描述                                                                                                                  | 失败时抛出的异常                    |
|------------------------------------------------------|---------------------------------------------------------------------------------------------------------------------|-----------------------------|
| `checkArgument(boolean)`                             | 检查 `boolean` 是否为 `true` ；用于检验方法的参数                                                                                  | `IllegalArgumentException`  |
| `checkNotNull(T)`                                    | 检查是否为 `null` ，直接返回值，所以你可以在内联使用 `checkNotNull(value)`                                                                | `NullPointerException`      |
| `checkState(boolean)`                                | 检查对象的某种状态，不依赖于方法参数（例如： `Iterator` 可以使用 `checkState` 来检查在任何调用 `remove` 之前是否已经调用了 `next`）                             | `IllegalStateException`     |
| `checkElementIndex(int index, int size)`             | 检查 `index` 是否是一个有效的元素索引，进入一个指定大小的列表、字符串或数组（只需要传递它的大小 `size` ）。一个元素索引的范围可以从 `0`(**包括**)到 `size`(**不包括**) ；返回 `index` | `IndexOutOfBoundsException` |
| `checkPositionIndex(int index, int size)`            | 检查 `index` 是否是一个有效**位置**索引，进入指定大小的列表、字符串和数组（只需要传递它的大小 `size` ）。一个位置索引的范围可以从 `0`(**包括**)到 `size`(**不包括**)；返回 `index` | `IndexOutOfBoundsException` |
| `checkPositionIndexes(int start, int end, int size)` | 检查 `start` 和 `end` 是否都在 `[0, size)` 范围内                                                                             | `IndexOutOfBoundsException` |

与 Apache Commons 的类似工具相比，倾向于推荐 Guava 预处理检查工具，原因有以下几点：

1. 在静态导入之后， Guava 的方法是清晰明确的。 `checkNotNull` 清楚地描述正在做什么，以及会抛出什么异常。
2. `checkNotNull` 在验证后返回其参数，允许在构造函数中使用简单的单行代码： `this.field = checkNotNull(field);`
3. 简单 varargs `printf-style` 风格的异常消息。（建议继续使用 `checkNotNull` 而不是 `Objects.requireNonNull` ）
