# Lombok

## Lombok features _Lombok 特性_

The Lombok javadoc is available, but we advise these pages.

* [`val`](https://projectlombok.org/features/val) [`val`](features/01%20val.md)

  > Finally! Hassle-free `final` local variables.

  > 最后！轻松的 `final` 局部变量。

* [`var`](https://projectlombok.org/features/var) [`var`](features/02%20var.md)

  > Mutably! Hassle-free local variables.

  > 可变！轻松的局部变量。

* [`@NonNull`](https://projectlombok.org/features/NonNull) [`@NonNull`](features/03%20NonNull.md)

  > or: How I learned to stop worrying and love the `NullPointerException`.

  > 或者：我是如何学会停止担心并喜欢 `NullPointerException` 的。

* [`@Cleanup`](https://projectlombok.org/features/Cleanup) [`@Cleanup`](features/04%20Cleanup.md)

  > Automatic resource management: Call your `close()` methods safely with no hassle.

  > 自动资源管理：安全地调用你的 `close()` 方法，没有麻烦。

* [`@Getter` / `@Setter`](https://projectlombok.org/features/GetterSetter) [`@Getter` / `@Setter`](features/05%20GetterSetter.md)

  > Never write `public int getFoo() {return foo;}` again.

  > 永远不要再写 `public int getFoo() {return foo;}` 。

* [`@ToString`](https://projectlombok.org/features/ToString) [`@ToString`](features/06%20ToString.md)

  > No need to start a debugger to see your fields: Just let lombok generate a `toString` for you!

  > 无需启动调试器即可查看您的字段：只需让 lombok 为您生成一个 `toString` ！

* [`@EqualsAndHashCode`](https://projectlombok.org/features/EqualsAndHashCode) [`@EqualsAndHashCode`](features/07%20EqualsAndHashCode.md)

  > Equality made easy: Generates `hashCode` and `equals` implementations from the fields of your object.

  > 相等变得容易：从对象的字段生成 `hashCode` 和 `equals` 实现。

* [`@NoArgsConstructor` / `@RequireArgsConstructor` / `@AllArgsConstructor`](https://projectlombok.org/features/constructor) [`@NoArgsConstructor` / `@RequireArgsConstructor` / `@AllArgsConstructor`](features/08%20Constructor.md)

  > Constructors made to order: Generates constructors that take no arguments, one argument per final / non-nullfield, or one argument for every field.

  > 构造构造函数：生成不带参数、每个最终非空字段一个参数或每个字段一个参数的构造函数。

* [`@Data`](https://projectlombok.org/features/Data) [`@Data`](features/09%20Data.md)

  > All together now: A shortcut for `@ToString`, `@EqualsAndHashCode`, `@Getter` on all fields, and `@Setter` on all non-`final` fields, and `@RequiredArgsConstructor`!

  > 现在都在一起了： `@ToString` 、 `@EqualsAndHashCode` 、 `@Getter` 在所有字段上、`@Setter` 在所有非 `final` 字段上以及 `@RequiredArgsConstructor` 的快捷方式！

* [`@Value`](https://projectlombok.org/features/Value) [`@Value`](features/10%20Value.md)

  > Immutable classes made very easy.

  > 轻松构造不可变类。

* [`@Builder`](https://projectlombok.org/features/Builder) [`@Builder`](features/11%20Builder.md)

  > ... and Bob's your uncle: No-hassle fancy-pants APIs for object creation!

  > ... Bob 是你的叔叔：用于创建对象的无障碍花哨 API ！

* [`@SneakyThrows`](https://projectlombok.org/features/SneakyThrows) [`@SneakyThrows`](features/12%20SneakyThrows.md)

  > To boldly throw checked exceptions where no one has thrown them before!

  > 大胆地抛出之前没有人抛出过的已检查异常！

* [`@Synchronized`](https://projectlombok.org/features/Synchronized) [`@Synchronized`](features/13%20Synchronized.md)

  > `synchronized` done right: Don't expose your locks.

  > `synchronized` 做得对：不要暴露你的锁。

* [`@With`](https://projectlombok.org/features/With) [`@With`](features/14%20With.md)

  > Immutable 'setters' - methods that create a clone but with one changed field.

  > 不可变的“setter” - 创建一个克隆但具有一个更改字段的方法。

* [`@Getter(lazy=true)`](https://projectlombok.org/features/GetterLazy) [`@Getter(lazy=true)`](features/15%20GetterLazy.md)

  > Laziness is a virtue!

  > 懒惰是一种美德！

* [`@Log`](https://projectlombok.org/features/log) [`@Log`](features/16%20log.md)

  > Captain's Log, stardate 24435.7: "What was that line again?"

  > 船长日志，星历 24435.7：“那条线又是什么？”

* [`experimental`](https://projectlombok.org/features/experimental/all) [`experimental`](experimental/experimental%20features.md)

  > Head to the lab: The new stuff we're working on.

  > 前往实验室：我们正在研究的新事物。


## Configuration system _配置系统_


Lombok, made to order: [Configure lombok features](https://projectlombok.org/features/configuration) in one place for your entire project or even your workspace.


Lombok，定制：[配置 lombok 功能](configuration.md) 在一个地方为您的整个项目甚至您的工作区。


## Running delombok _运行 delombok_


Delombok copies your source files to another directory, replacing all lombok annotations with their desugared form. 
So, it'll turn `@Getter` back into the actual `getter`. 
It then removes the annotation. 
This is useful for all sorts of reasons; you can check out what's happening under the hood, if the unthinkable happens and you want to stop using lombok, you can easily remove all traces of it in your source, and you can use delombok to preprocess your source files for source-level tools such as javadoc and GWT. 
More information about how to run delombok, including instructions for build tools can be found at the [delombok page](https://projectlombok.org/features/delombok).


Delombok 将您的源文件复制到另一个目录，用它们的脱糖形式替换所有 lombok 注解。
因此，它会将 `@Getter` 转回实际的 `getter` 。
然后删除注解。
出于各种原因，这很有用；您可以查看幕后发生的事情，如果发生了不可思议的事情并且您想停止使用 lombok，您可以轻松删除源代码中的所有痕迹，并且您可以使用 delombok 为源代码级工具预处理源文件，例如作为 javadoc 和 GWT。
有关如何运行 delombok 的更多信息，包括构建工具的说明，请访问 [delombok 页面](Delombok.md) 。

