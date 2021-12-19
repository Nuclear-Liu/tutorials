# `@Value`

## Overview


`@Value` is the immutable variant of `@Data`; all fields are made `private` and `final` by default, and setters are not generated. 
The class itself is also made `final` by default, because immutability is not something that can be forced onto a subclass. 
Like `@Data`, useful `toString()`, `equals()` and `hashCode()` methods are also generated, each field gets a getter method, and a constructor that covers every argument (except `final` fields that are initialized in the field declaration) is also generated.


`@Value` 是 `@Data` 的不可变变体；默认情况下，所有字段都设置为 `private` 和 `final` ，并且不生成 setter。
默认情况下，类本身也被设为 `final` ，因为不可变性不是可以强加给子类的东西。
像 `@Data` 一样，也生成了有用的 `toString()` 、 `equals()` 和 `hashCode()` 方法，每个字段都有一个 getter 方法，以及一个覆盖每个参数的构造函数（除了 `final` 字段，在字段声明中初始化）也会生成。


In practice, `@Value` is shorthand for: 
`final @ToString @EqualsAndHashCode @AllArgsConstructor @FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE) @Getter`, except that explicitly including an implementation of any of the relevant methods simply means that part won't be generated and no warning will be emitted. 
For example, if you write your own `toString`, no error occurs, and lombok will not generate a `toString`. 
Also, any explicit constructor, no matter the arguments list, implies lombok will not generate a constructor. 
If you do want lombok to generate the all-args constructor, add `@AllArgsConstructor` to the class. 
Note that if both `@Builder` and `@Value` are on a class, the package private allargs constructor that `@Builder` wants to make 'wins' over the public one that `@Value` wants to make. 
You can mark any constructor or method with `@lombok.experimental.Tolerate` to hide them from lombok.


在实践中，`@Value` 是以下的简写：
`final @ToString @EqualsAndHashCode @AllArgsConstructor @FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE) @Getter` ， 除了明确包含任何相关方法的实现仅意味着不会生成该部分并且不会发出警告。
比如你自己写的 `toString` ，就不会出错，lombok也不会生成 `toString` 。
此外，任何显式构造函数，无论参数列表如何，都意味着 lombok 不会生成构造函数。
如果您确实希望 lombok 生成全参数构造函数，请将 `@AllArgsConstructor` 添加到类中。
请注意，如果 `@Builder` 和 `@Value` 都在一个类上， `@Builder` 想要创建的包私有 allargs 构造函数'胜过' `@Value` 想要创建的公共构造函数。
您可以使用 `@lombok.experimental.Tolerate` 标记任何构造函数或方法以将它们从 lombok 中隐藏。


It is possible to override the final-by-default and private-by-default behavior using either an explicit access level on a field, or by using the `@NonFinal` or `@PackagePrivate` annotations. 
`@NonFinal` can also be used on a class to remove the final keyword.


可以使用字段上的显式访问级别或使用 `@NonFinal` 或 `@PackagePrivate` 注释来覆盖默认 final 和默认私有行为。
`@NonFinal` 也可用于类以删除 final 关键字。


It is possible to override any default behavior for any of the 'parts' that make up `@Value` by explicitly using that annotation.


通过显式使用该注释，可以覆盖构成 `@Value` 的任何“部分”的任何默认行为。

