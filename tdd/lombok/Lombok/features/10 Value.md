# `@Value`


Immutable classes made very easy.


不可变类变得非常容易。


* `@Value` was introduced as experimental feature in lombok v0.11.4.

* `@Value` no longer implies `@With` since lombok v0.11.8.

* `@Value` promoted to the main `lombok` package since lombok v0.12.0.


* `@Value` 在 lombok v0.11.4 中作为实验性功能引入。

* 从 lombok v0.11.8 开始， `@Value` 不再暗示 `@With` 。

* 从 lombok v0.12.0 开始， `@Value` 被提升为主要的 `lombok` 包。


## Overview


`@Value` is the immutable variant of `@Data`; all fields are made `private` and `final` by default, and `setter`s are not generated. 
The class itself is also made `final` by default, because immutability is not something that can be forced onto a subclass. 
Like `@Data`, useful `toString()`, `equals()` and `hashCode()` methods are also generated, each field gets a `getter` method, and a constructor that covers every argument (except `final` fields that are initialized in the field declaration) is also generated.


`@Value` 是 `@Data` 的不可变变体；默认情况下，所有字段都设置为 `private` 和 `final` ，并且不生成 `setter`。
默认情况下，类本身也被设为 `final` ，因为不可变性不是可以强加给子类的东西。
像 `@Data` 一样，也生成了有用的 `toString()` 、 `equals()` 和 `hashCode()` 方法，每个字段都有一个 `getter` 方法，以及一个覆盖每个参数的构造函数（除了 `final` 字段，在字段声明中初始化）也会生成。


In practice, `@Value` is shorthand for: 
`final @ToString @EqualsAndHashCode @AllArgsConstructor @FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE) @Getter`, except that explicitly including an implementation of any of the relevant methods simply means that part won't be generated and no warning will be emitted. 
For example, if you write your own `toString`, no error occurs, and lombok will not generate a `toString`. 
Also, any explicit constructor, no matter the arguments list, implies lombok will not generate a constructor. 
If you do want lombok to generate the all-args constructor, add `@AllArgsConstructor` to the class. 
**Note that if both `@Builder` and `@Value` are on a class, the package private `allargs` constructor that `@Builder` wants to make 'wins' over the public one that `@Value` wants to make.** 
You can mark any constructor or method with `@lombok.experimental.Tolerate` to hide them from lombok.


在实践中， `@Value` 是以下的简写：
`final @ToString @EqualsAndHashCode @AllArgsConstructor @FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE) @Getter` ， 除了明确包含任何相关方法的实现仅意味着不会生成该部分并且不会发出警告。
比如你自己写的 `toString` ，就不会出错，lombok也不会生成 `toString` 。
此外，任何显式构造函数，无论参数列表如何，都意味着 lombok 不会生成构造函数。
如果您确实希望 lombok 生成全参数构造函数，请将 `@AllArgsConstructor` 添加到类中。
**请注意，如果 `@Builder` 和 `@Value` 都在一个类上， `@Builder` 想要创建的包私有 `allargs` 构造函数'胜过' `@Value` 想要创建的公共构造函数。**
您可以使用 `@lombok.experimental.Tolerate` 标记任何构造函数或方法以将它们从 lombok 中隐藏。


It is possible to override the final-by-default and private-by-default behavior using either an explicit access level on a field, or by using the `@NonFinal` or `@PackagePrivate` annotations. 
`@NonFinal` can also be used on a class to remove the `final` keyword.


可以使用字段上的显式访问级别或使用 `@NonFinal` 或 `@PackagePrivate` 注释来覆盖默认 final 和默认私有行为。
`@NonFinal` 也可用于类以删除 `final` 关键字。


It is possible to override any default behavior for any of the 'parts' that make up `@Value` by explicitly using that annotation.


通过显式使用该注解，可以覆盖构成 `@Value` 的任何“部分”的任何默认行为。


## Supported configuration keys:


`lombok.value.flagUsage =` [`warning` | `error`] (default: not set)


Lombok will flag any usage of `@Value` as a warning or error if configured.


如果已配置，Lombok 会将 `@Value` 的任何使用标记为警告或错误。


`lombok.noArgsConstructor.extraPrivate =` [`true` | `false`] (default: `false`)


If `true`, lombok will generate a private no-args constructor for any `@Value` annotated class, which sets all fields to default values (`null` / `0` / `false`).


如果为 `true` ，lombok 将为任何“@Value”注释类生成一个私有的无参数构造函数，它将所有字段设置为默认值（`null` / `0` / `false`）。


## Small print


Look for the documentation on the 'parts' of `@Value`: `@ToString`, `@EqualsAndHashCode`, `@AllArgsConstructor`, `@FieldDefaults`, and `@Getter`.


查找有关 `@Value` 的“部分”的文档： `@ToString`、 `@EqualsAndHashCode`、 `@AllArgsConstructor`、 `@FieldDefaults` 和 `@Getter` 。


For classes with generics, it's useful to have a static method which serves as a constructor, because inference of generic parameters via static methods works in java6 and avoids having to use the diamond operator. 
While you can force this by applying an explicit `@AllArgsConstructor(staticConstructor="of")` annotation, there's also the `@Value(staticConstructor="of")` feature, which will make the generated all-arguments constructor private, and generates a public static method named `of` which is a wrapper around this private constructor.


对于具有泛型的类，将静态方法用作构造函数是很有用的，因为通过静态方法推断泛型参数在 java6 中有效并且避免了必须使用菱形运算符。
虽然您可以通过应用显式的 `@AllArgsConstructor(staticConstructor="of")` 注解来强制执行此操作，但还有 `@Value(staticConstructor="of")` 功能，它将使生成的所有参数构造函数成为私有的，并且生成一个名为 `of` 的公共静态方法，它是这个私有构造函数的包装器。


Various well known annotations about nullity cause null checks to be inserted and will be copied to the relevant places (such as the method for getters, and the parameter for the constructor and `setter`s). 
See [Getter/Setter](https://projectlombok.org/features/experimental/FieldDefaults) documentation's small print for more information.


各种众所周知的关于空性的注解会导致插入空检查并将其复制到相关位置（例如 getter 的方法，以及构造函数和 `setter` 的参数）。
有关更多信息，请参阅 [Getter/Setter](./05%20GetterSetter.md) 文档的小字体。


`@Value` was an experimental feature from v0.11.4 to v0.11.9 (as `@lombok.experimental.Value`). 
It has since been moved into the core package. 
The old annotation is still around (and is an alias). 
It will eventually be removed in a future version, though.


`@Value` 是一个从 v0.11.4 到 v0.11.9 的实验性特性（如 `@lombok.experimental.Value` ）。
它已被移至核心包中。
旧注释仍然存在（并且是别名）。
不过，它最终会在未来的版本中被删除。


It is not possible to use `@FieldDefaults` to 'undo' the private-by-default and final-by-default aspect of fields in the annotated class. 
Use `@NonFinal` and `@PackagePrivate` on the fields in the class to override this behaviour.


不可能使用 `@FieldDefaults` 来“撤消”注解类中字段的默认私有和默认最终方面。
在类中的字段上使用 `@NonFinal` 和 `@PackagePrivate` 来覆盖此行为。
