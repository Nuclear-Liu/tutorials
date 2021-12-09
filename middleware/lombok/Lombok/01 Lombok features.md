# Lombok features _Lombok 特性_

The Lombok javadoc is available, but we advise these pages.

* [`val`](https://projectlombok.org/features/val) [`val`](./features/01%20val.md)

  > Finally! Hassle-free `final` local variables.

  > 最后！轻松的 `final` 局部变量。

* [`var`](https://projectlombok.org/features/var) [`var`](./features/02%20var.md)

  > Mutably! Hassle-free local variables.

  > 可变！轻松的局部变量。

* [`@NonNull`](https://projectlombok.org/features/NonNull) [`@NonNull`](./features/03%20NonNull.md)

  > or: How I learned to stop worrying and love the `NullPointerException`.

  > 或者：我是如何学会停止担心并喜欢 `NullPointerException` 的。

* [`@Cleanup`](https://projectlombok.org/features/Cleanup) [`@Cleanup`](./features/04%20Cleanup.md)

  > Automatic resource management: Call your `close()` methods safely with no hassle.

  > 自动资源管理：安全地调用你的 `close()` 方法，没有麻烦。

* [`@Getter` / `@Setter`](https://projectlombok.org/features/GetterSetter) [`@Getter` / `@Setter`](./features/04%20Cleanup.md)

  > Never write `public int getFoo() {return foo;}` again.

  > 永远不要再写 `public int getFoo() {return foo;}` 。

* [`@ToString`]()

  > No need to start a debugger to see your fields: Just let lombok generate a `toString` for you!

  > 无需启动调试器即可查看您的字段：只需让 lombok 为您生成一个 `toString` ！

* [`@EqualsAndHashCode`]()

  > Equality made easy: Generates hashCode and equals implementations from the fields of your object.

  > 平等变得容易：从对象的字段生成 hashCode 和 equals 实现。

* [`@NoArgsConstructor` / `@RequireArgsConstructor` / `@AllArgsConstructor`]()

  > Constructors made to order: Generates constructors that take no arguments, one argument per final / non-nullfield, or one argument for every field.

  > 构造构造函数：生成不带参数、每个最终非空字段一个参数或每个字段一个参数的构造函数。

* [`@Data`]()

  > All together now: A shortcut for `@ToString`, `@EqualsAndHashCode`, `@Getter` on all fields, and `@Setter` on all non-`final` fields, and `@RequiredArgsConstructor`!

  > 现在都在一起了： `@ToString` 、 `@EqualsAndHashCode` 、 `@Getter` 在所有字段上、`@Setter` 在所有非 `final` 字段上以及 `@RequiredArgsConstructor` 的快捷方式！

* [`@Value`]()

  > Immutable classes made very easy.

  > 轻松构造不可变类。

* [`@Builder`]()

  > ... and Bob's your uncle: No-hassle fancy-pants APIs for object creation!

  > ... Bob 是你的叔叔：用于创建对象的无障碍花哨 API ！

* [`@SneakyThrows`]()

  > To boldly throw checked exceptions where no one has thrown them before!

  > 大胆地抛出之前没有人抛出过的已检查异常！

* [`@Synchronized`]()

  > `synchronized` done right: Don't expose your locks.

  > `synchronized` 做得对：不要暴露你的锁。

* [`@With`]()

  > Immutable 'setters' - methods that create a clone but with one changed field.

  > 不可变的“setter” - 创建一个克隆但具有一个更改字段的方法。

* [`@Getter(lazy=true)`]()

  > Laziness is a virtue!

  > 懒惰是一种美德！

* [`@Log`]()

  > Captain's Log, stardate 24435.7: "What was that line again?"

  > 船长日志，星历 24435.7：“那条线又是什么？”

* [`experimental`]()

  > Head to the lab: The new stuff we're working on.

  > 前往实验室：我们正在研究的新事物。
