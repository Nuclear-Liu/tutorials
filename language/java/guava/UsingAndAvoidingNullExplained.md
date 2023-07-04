# 使用和避免 `null` Using and avoiding `null`

`null` 是一个令人不快的歧义。
`null` 的返回值是什么意思几乎不明显。
（例如， `Map.get(key)` 可以返回 `null` ，因为 `Map` 中的值是空的，或者该值不在 `Map` 中。） 
`null` 可以意味着失败，也可以意味着成功。

`null` 在一些情形下是正确的，应该使用。
就内存和速度而言， `null` 和廉价，而且在对象数组中是不可避免的。

在应用进程代码中，与库相反，它是混乱、困难和奇怪的 Bug 以及令人不快的歧义的主要来源。
最关键是 `null` 没有给出 `null` 值的含义。

Guava 工具被设计程出现 `null` 时快速失效，而不是允许使用 `null` ，只要有一个对 `null` 友好的解决方法可用。
Guava 提供了在必须使用 `null` 时更容易使用的工具，并尽量帮助你避免使用 `null` 。

## 具体案例

* 在 `Set` 中不要使用 `null` 作为值；在 `Map` 中不要使用 `null` 作为 key 。
* 在 `Set` / `Map` 查找操作中可以明确清晰的对 `null` 进行特殊处理。
* 对于稀疏列表使用 `Map<Integer, E>` 代替 `List` 中使用 `null` 将会更加准确地满足应用需求。
* 适当的应用场景考虑是否存在自然的 `null object` 可以使用。

	例如使用添加了一个常数来表示期望的 `null` 值的 `enum` 。
* 在必须使用 `null` 值是，使用不同的具体实现。

	例如：使用 `Collections.unmodifiableList(Lists.newArrayList())` 替代 `ImmutableList`

## `Optional`

`Optional<T>` 是一种用非空值替代可空的 `T` 引用的方法。
一个 `Optional` 可以包含一个非空的 `T` 引用（这种情况下，这个引用是**存在**的），或者不包含任何东西（这种情况下，这个引用是**不存在**的）。

```jshelllanguage
Optional<Integer> possible = Optional.of(5);
possible.isPresent(); // returns true
possible.get(); // returns 5
```

### 创建 `Optional`

| Method                                                          | Description                                                            |
|-----------------------------------------------------------------|------------------------------------------------------------------------|
| `Optional.of(T)`                                                | 创建一个包含给定**非空值**的 `Optional` ；当 `T` 为 `null` 时抛出 `NullPointerException` |
| `Optional.absent()`                                             | 创建一个缺省的某种类型的 `Optional`                                                |
| `Optional.fromNullable(T)`                                      | 创建一个可能为空的 `Optional`                                                   |
| `Optional.fromJavaUtil(java.util.Optional<T> javaUtilOptional)` | 从 `java.util.Optional` 对象创建 `Optional`                                 |
| `java.util.Optional<T> toJavaUtil(Optional<T> googleOptional)`  |                                                                        |

### 查询方法

| Method                                                   | Description                                       |
|----------------------------------------------------------|---------------------------------------------------|
| `boolean isPresent()`                                    | 如果 `Optional` 包含的非空实例，返回 `true`                   |
| `T get()`                                                | 返回所包含的非空 `T` 类型实例；如果为空则抛出 `IllegalStateException` |
| `T or(T defaultValue)`                                   |                                                   |
| `Optional<T> or(Optional<? extends T> secondChoice)`     | 返回当前 `Optional` 对象，如果为空实例，则返回 `secondChoice` 对象   |
| `T or(Supplier<? extends T> supplier)`                   |                                                   |
| `T orNull()`                                             |                                                   |
| `Set<T> asSet()`                                         |                                                   |
| `java.util.Optional<T> toJavaUtil()`                     |                                                   |
| `Optional<V> transform(Function<? super T, V> function)` |                                                   |
| `int hashCode()`                                         |                                                   |
| `boolean equals(Object object)`                          |                                                   |
