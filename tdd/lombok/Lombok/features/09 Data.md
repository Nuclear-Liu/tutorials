# `@Data`


## Overview


`@Data` is a convenient shortcut annotation that bundles the features of `@ToString`, `@EqualsAndHashCode`, `@Getter` / `@Setter` and `@RequiredArgsConstructor` together: 
In other words, `@Data` generates all the boilerplate that is normally associated with simple POJOs (Plain Old Java Objects) and beans: 
getters for all fields, setters for all non-final fields, and appropriate `toString`, `equals` and `hashCode` implementations that involve the fields of the class, and a constructor that initializes all final fields, as well as all non-`final` fields with no initializer that have been marked with `@NonNull`, in order to ensure the field is never null.


`@Data` 是一个方便的快捷注解，它捆绑了 `@ToString` 、 `@EqualsAndHashCode` 、 `@Getter` 、 `@Setter` 和 `@RequiredArgsConstructor` 的特性：
换句话说，`@Data` 生成通常与简单 POJO（Plain Old Java Objects）和 bean 相关联的所有样板：
所有字段的 getter，所有非 final 字段的 setter，以及涉及类字段的适当 `toString`、`equals` 和 `hashCode` 实现，以及初始化所有 `final` 字段以及所有非最终字段的构造函数没有初始化器的 `final` 字段被标记为 `@NonNull`，以确保该字段永远不会为空。


`@Data` is like having implicit `@Getter`, `@Setter`, `@ToString`, `@EqualsAndHashCode` and `@RequiredArgsConstructor` annotations on the class (except that no constructor will be generated if any explicitly written constructors already exist). 
However, the parameters of these annotations (such as `callSuper`, `includeFieldNames` and `exclude`) cannot be set with `@Data`. 
If you need to set non-default values for any of these parameters, just add those annotations explicitly; `@Data` is smart enough to defer to those annotations.


`@Data` 就像在类上有隐式的 `@Getter` 、 `@Setter` 、 `@ToString` 、 `@EqualsAndHashCode` 和 `@RequiredArgsConstructor` 注释（除了如果已经有任何显式编写的构造函数，则不会生成构造函数存在）。
但是，这些注解的参数（如 `callSuper` 、 `includeFieldNames` 和 `exclude` ）不能用 `@Data` 设置。
如果您需要为这些参数中的任何一个设置非默认值，只需显式添加这些注释即可； `@Data` 足够聪明，可以遵循这些注释。


All generated getters and setters will be `public`. 
To override the access level, annotate the field or class with an explicit `@Setter` and/or `@Getter` annotation. 
You can also use this annotation (by combining it with `AccessLevel.NONE`) to suppress generating a getter and/or setter altogether.


所有生成的 getter 和 setter 都将是 `public`。
要覆盖访问级别，请使用显式的 `@Setter` 和或 `@Getter` 注释对字段或类进行注释。
您还可以使用此注释（通过将其与 `AccessLevel.NONE` 结合使用）来完全抑制生成 getter 和/或 setter。


All fields marked as `transient` will not be considered for `hashCode` and `equals`. 
All static fields will be skipped entirely (not considered for any of the generated methods, and no setter/getter will be made for them).


`hashCode` 和 `equals` 不会考虑所有标记为 `transient` 的字段。
所有静态字段都将被完全跳过（不考虑任何生成的方法，并且不会为它们制作 setter/getter ）。


If the class already contains a method with the same name and parameter count as any method that would normally be generated, that method is not generated, and no warning or error is emitted. 
For example, if you already have a method with signature `equals(AnyType param)`, no `equals` method will be generated, even though technically it might be an entirely different method due to having different parameter types. 
The same rule applies to the constructor (any explicit constructor will prevent `@Data` from generating one), as well as `toString`, `equals`, and all `getter`s and `setter`s. 
You can mark any constructor or method with `@lombok.experimental.Tolerate` to hide them from lombok.


如果该类已包含与通常生成的任何方法具有相同名称和参数计数的方法，则不会生成该方法，并且不会发出警告或错误。
例如，如果您已经有一个带有“equals(AnyType param)”签名的方法，则不会生成“equals”方法，即使从技术上讲，由于具有不同的参数类型，它可能是一种完全不同的方法。
相同的规则适用于构造函数（任何显式构造函数都会阻止`@Data` 生成），以及`toString`、`equals` 和所有`getter` 和`setter`。
您可以使用 `@lombok.experimental.Tolerate` 标记任何构造函数或方法以将它们从 lombok 中隐藏。


`@Data` can handle generics parameters for fields just fine. 
In order to reduce the boilerplate when constructing objects for classes with generics, you can use the `staticConstructor` parameter to generate a private constructor, as well as a static method that returns a new instance. 
This way, javac will infer the variable name. 
Thus, by declaring like so: `@Data(staticConstructor="of") class Foo<T> { private T x;}` you can create new instances of `Foo` by writing: `Foo.of(5);` instead of having to write: `new Foo<Integer>(5);`.


`@Data` 可以很好地处理字段的泛型参数。
为了在为具有泛型的类构造对象时减少样板，您可以使用 `staticConstructor` 参数来生成私有构造函数，以及返回新实例的静态方法。
这样，javac 将推断变量名称。
因此，通过像这样声明： `@Data(staticConstructor="of") class Foo<T> { private T x;}` 你可以通过写来创建 `Foo` 的新实例： `Foo.of(5);` 而不必写： `new Foo<Integer>(5);` 。

