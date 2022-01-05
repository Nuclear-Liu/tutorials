# `@With`


Immutable '`setter`s' - methods that create a clone but with one changed field.


不可变的 “`setter`” - 创建一个克隆但具有一个更改字段的方法。


`@Wither` was introduced as experimental feature in lombok v0.11.4.


`@Wither` 在 lombok v0.11.4 中作为实验性功能引入。


`@Wither` was renamed to `@With`, and moved out of experimental and into the core package, in lombok v1.18.10.


`@Wither` 在 lombok v1.18.10 中被重命名为 `@With`，并移出实验性并进入核心包。


## Overview


The next best alternative to a `setter` for an immutable property is to construct a clone of the object, but with a new value for this one field. 
A method to generate this clone is precisely what `@With` generates: 
a `withFieldName(newValue)` method which produces a clone except for the new value for the associated field.


不可变属性的 `setter` 的下一个最佳替代方法是构造对象的克隆，但为该字段使用新值。
生成这个 `clone` 的方法正是 `@With` 生成的：
一个 `withFieldName(newValue)` 方法，它产生一个克隆，除了关联字段的新值。


For example, if you create `public class Point { private final int x, y; }`, `setter`s make no sense because the fields are `final`. 
`@With` can generate a `withX(int newXValue)` method for you which will return a new point with the supplied value for `x` and the same value for `y`.


例如，如果您创建 `public class Point { private final int x, y; }` ，`setter` 没有意义，因为这些字段是 `final` 。
`@With` 可以为您生成一个 `withX(int newXValue)` 方法，该方法将返回一个新的点，其中包含为 `x` 提供的值和为 `y` 提供的相同值。


The `@With` relies on **a constructor for all fields** in order to do its work. 
If this constructor does not exist, your `@With` annotation will result in a compile time error message. 
You can use Lombok's own `@AllArgsConstructor`, or as `Value` will automatically produce an all args constructor as well, you can use that too. 
It's of course also acceptable if you manually write this constructor. 
It must contain all non-static fields, in the same lexical order.


`@With` 依赖于**所有字段的构造函数**来完成其工作。
如果此构造函数不存在，您的 `@With` 注解将导致编译时错误消息。
您可以使用 Lombok 自己的 `@AllArgsConstructor` ，或者因为 `Value` 也会自动生成一个 all args 构造函数，您也可以使用它。
如果你手动编写这个构造函数当然也是可以接受的。
它必须以相同的词法顺序包含所有非静态字段。


Like `@Setter`, you can specify an access level in case you want the generated `with` method to be something other than public:
`@With(level = AccessLevel.PROTECTED)`. 
Also like `@Setter`, you can also put a `@With` annotation on a type, which means a `with` method is generated for each field (even non-`final` fields).


像 `@Setter` 一样，你可以指定一个访问级别，以防你希望生成的 `with` 方法不是公共的：
`@With(level = AccessLevel.PROTECTED)`.
同样像 `@Setter` ，你也可以在一个类型上加上 `@With` 注解，这意味着为每个字段（甚至非 `final` 字段）生成一个 `with` 方法。


To put annotations on the generated method, you can use `onMethod=@__({@AnnotationsHere})`. 
Be careful though! 
This is an experimental feature. 
For more details see the documentation on the [onX](https://projectlombok.org/features/experimental/onX) feature.


要将注解放在生成的方法上，您可以使用 `onMethod=@__({@AnnotationsHere})`。
不过要小心！
这是一个实验性功能。
有关更多详细信息，请参阅有关 [onX]() 功能的文档。


javadoc on the field will be copied to generated `with` methods. 
Normally, all text is copied, and `@param` is moved to the with method, whilst `@return` lines are stripped from the `with` method's javadoc. 
Moved means: Deleted from the field's javadoc. 
It is also possible to define unique text for the `with` method's javadoc. 
To do that, you create a 'section' named `WITH`. 
A section is a line in your javadoc containing 2 or more dashes, then the text 'WITH', followed by 2 or more dashes, and nothing else on the line. 
If you use sections, `@return` and `@param` stripping / copying for that section is no longer done (move the `@param` line into the section).


字段上的 javadoc 将被复制到生成的 `with` 方法中。
通常，所有文本都被复制，并且 `@param` 被移动到 with 方法，而 `@return` 行从 `with` 方法的 javadoc 中删除。
移动的意思是：从字段的 javadoc 中删除。
也可以为 `with` 方法的javadoc 定义唯一的文本。
为此，您需要创建一个名为 `WITH` 的“部分”。
节是 javadoc 中包含 2 个或更多破折号的一行，然后是文本 '`WITH`'，然后是 2 个或更多破折号，该行没有其他任何内容。
如果您使用部分，则该部分的 `@return` 和 `@param` 剥离复制不再完成（将 `@param` 行移到该部分中）。


## Supported configuration keys:

`lombok.with.flagUsage =` [`warning` | `error`] (default: not set)


Lombok will flag any usage of `@With` as a warning or error if configured.


如果已配置，Lombok 会将 `@With` 的任何用法标记为警告或错误。


## Small print


With methods cannot be generated for static fields because that makes no sense.


不能为静态字段生成方法，因为这没有意义。


With methods can be generated for abstract classes, but this generates an abstract method with the appropriate signature.


可以为抽象类生成 with 方法，但这会生成具有适当签名的抽象方法。


When applying `@With` to a type, static fields and fields whose name start with a `$` are skipped.


将 `@With` 应用于类型时，将跳过静态字段和名称以 `$` 开头的字段。


For generating the method names, the first character of the field, if it is a lowercase character, is title-cased, otherwise, it is left unmodified. 
Then, `with` is prefixed.


对于生成方法名称，字段的第一个字符，如果是小写字符，则是标题大小写，否则不做修改。
然后， `with` 是前缀。


No method is generated if any method already exists with the same name (case insensitive) and same parameter count. 
For example, `withX(int x)` will not be generated if there's already a method `withX(String... x)` even though it is technically possible to make the method. 
This caveat exists to prevent confusion. 
If the generation of a method is skipped for this reason, a warning is emitted instead. Varargs count as 0 to N parameters.


如果已存在具有相同名称（不区分大小写）和相同参数计数的任何方法，则不会生成任何方法。
例如，如果已经有一个方法 `withX(String... x)` ，则不会生成 `withX(int x)` ，即使该方法在技术上是可行的。
存在此警告是为了防止混淆。
如果由于这个原因跳过方法的生成，则会发出警告。可变参数计为 0 到 N 个参数。


Various well known annotations about nullity cause null checks to be inserted and will be copied to the parameter. 
See `Getter`/`Setter` documentation's small print for more information.


关于空性的各种众所周知的注解会导致插入空检查并将其复制到参数中。
有关更多信息，请参阅 `Getter`/`Setter` 文档的小字。


If you have configured a nullity annotation flavour via `lombok.config` key `lombok.addNullAnnotations`, the method or return type (as appropriate for the chosen flavour) is annotated with a non-null annotation.


如果您通过 `lombok.config` 键 `lombok.addNullAnnotations` 配置了空注解风格，则方法或返回类型（根据所选风格）将使用非空注解进行注解。
