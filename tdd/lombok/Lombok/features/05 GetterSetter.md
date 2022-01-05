# `@Getter` and `@Setter`


* Scope
  * `ElementType.FIELD`
  * `ElementType.TYPE`


Never write `public int getFoo() {return foo;}` again.


永远不要再写 `public int getFoo() {return foo;}` 。


## Overview


You can annotate any field with `@Getter` and/or `@Setter`, to let lombok generate the default `getter`/`setter` automatically.
A default `getter` simply returns the field, and is named `getFoo` if the field is called `foo` (or `isFoo` if the field's type is `boolean`). 
A default `setter` is named `setFoo` if the field is called `foo`, returns `void`, and takes 1 parameter of the same type as the field. 
It simply sets the field to this value.


你可以用 `@Getter` 和或 `@Setter` 注解任何字段，让 lombok 自动生成默认的 `getter`/`setter` 。
默认的 `getter` 简单地返回该字段，如果该字段被称为 `foo`（或`isFoo`，如果该字段的类型是 `boolean` ），则命名为 `getFoo` 。
如果该字段被称为 `foo` ，则默认 `setter` 被命名为 `setFoo`，返回 `void`，并采用与该字段相同类型的 1 个参数。
它只是将字段设置为此值。


The generated `getter`/`setter` method will be public unless you explicitly specify an `AccessLevel`, as shown in the example below. 
Legal access levels are `PUBLIC`, `PROTECTED`, `PACKAGE`, and `PRIVATE`.


除非您明确指定 `AccessLevel`，否则生成的 `getter`/`setter` 方法将是公开的，如下面的示例所示。
合法的访问级别是 `PUBLIC`、 `PROTECTED`、 `PACKAGE` 和 `PRIVATE` 。


You can also put a `@Getter` and/or `@Setter` annotation on a class. 
In that case, it's as if you annotate all the non-`static` fields in that class with the annotation.


您还可以在类上添加 `@Getter` 和或 `@Setter` 注解。
在这种情况下，就好像您使用注释对该类中的所有非 `static` 字段进行了注解。


You can always manually disable `getter`/`setter` generation for any field by using the special `AccessLevel.NONE` access level. 
This lets you override the behaviour of a `@Getter`, `@Setter` or `@Data` annotation on a class.


您始终可以使用特殊的 `AccessLevel.NONE` 访问级别为任何字段手动禁用 `getter`/`setter` 生成。
这使您可以覆盖类上 `@Getter`、 `@Setter` 或 `@Data` 注解的行为。


To put annotations on the generated method, you can use `onMethod=@__({@AnnotationsHere})`; to put annotations on the only parameter of a generated `setter` method, you can use `onParam=@__({@AnnotationsHere})`. 
Be careful though! 
This is an experimental feature. 
For more details see the documentation on the `onX` feature.


要将注解放在生成的方法上，可以使用 `onMethod=@__({@AnnotationsHere})` ;要将注解放在生成的 `setter` 方法的唯一参数上，您可以使用 `onParam=@__({@AnnotationsHere})`。
不过要小心！
这是一个实验性功能。
有关更多详细信息，请参阅有关 `onX` 功能的文档。


NEW in lombok v1.12.0: javadoc on the field will now be copied to generated `getter`s and `setter`s. 
Normally, all text is copied, and `@return` is moved to the `getter`, whilst `@param` lines are moved to the `setter`. 
Moved means: Deleted from the field's javadoc. 
It is also possible to define unique text for each `getter`/`setter`. 
To do that, you create a '`section`' named `GETTER` and/or `SETTER`. 
A `section` is a line in your javadoc containing 2 or more dashes, then the text '`GETTER`' or '`SETTER`', followed by 2 or more dashes, and nothing else on the line. 
If you use `section`s, `@return` and `@param` stripping for that section is no longer done (move the `@return` or `@param` line into the section).


lombok v1.12.0 中的新功能：字段上的 javadoc 现在将被复制到生成的 `getter` 和 `setter`。
通常，所有文本都会被复制， `@return` 会移动到 `getter` ，而 `@param` 行会移动到 `setter`。
移动的意思是：从字段的 javadoc 中删除。
也可以为每个 `getter`/`setter` 定义唯一的文本。
为此，您创建了一个名为 `GETTER` 或 `SETTER` 的 `section`。
`section` 是你的 javadoc 中的一行，包含 2 个或更多的破折号，然后是文本 '`GETTER`' 或 '`SETTER`'，后跟 2 个或更多的破折号，该行没有其他内容。
如果您使用`section` ，则该部分的 `@return` 和 `@param` 剥离不再完成（将 `@return` 或 `@param` 行移到该部分中）。


## Supported configuration keys:


`lombok.accessors.chain =` [`true` | `false`] (default: `false`)


`lombok.accessors.chain =` [`true` | `false`] (默认: `false`)


If set to `true`, generated `setter`s will return `this` (instead of `void`). 
An explicitly configured chain parameter of an `@Accessors` annotation takes precedence over this setting.


如果设置为 `true` ，生成的 `setter` 将返回 `this` （而不是 `void` ）。
`@Accessors` 注解的显式配置链参数优先级大于此设置。


`lombok.accessors.fluent =` [`true` | `false`] (default: `false`)


`lombok.accessors.fluent =` [`true` | `false`] (默认: `false`)


If set to `true`, generated `getter`s and `setter`s will not be prefixed with the bean-standard `get`, `is` or `set`; instead, the methods will use the same name as the field (minus prefixes). 
An explicitly configured `chain` parameter of an `@Accessors` annotation takes precedence over this setting.


如果设置为 `true` ，生成的 `getter` 和 `setter` 将不会以bean 标准的 `get`、 `is` 或`set` 为前缀；相反，这些方法将使用与字段相同的名称（减去前缀）。
`@Accessors` 注解的显式配置的 `chain` 参数优先级大于此设置。


`lombok.accessors.prefix +=` **a field prefix** (default: empty list)


`lombok.accessors.prefix +=` **字段前缀** (默认值：空列表)


This is a list property; entries can be added with the `+=` operator. 
Inherited prefixes from parent config files can be removed with the `-=` operator. 
Lombok will strip any matching field prefix from the name of a field in order to determine the name of the `getter`/`setter` to generate. 
For example, if `m` is one of the prefixes listed in this setting, then a field named `mFoobar` will result in a getter named `getFoobar()`, not `getMFoobar()`. 
An explicitly configured `prefix` parameter of an `@Accessors` annotation takes precedence over this setting.


这是一个列表属性；可以使用 `+=` 运算符添加条目。
可以使用 `-=` 运算符删除从**父配置文件继承的前缀**。
Lombok 将从字段名称中去除任何匹配的字段前缀，以确定要生成的 `getter` / `setter` 的名称。
例如，如果 `m` 是此设置中列出的前缀之一，那么名为 `mFoobar` 的字段将生成名为 `getFoobar()` 的 getter，而不是 `getMFoobar()`。
`@Accessors` 注解的显式配置的 `prefix` 参数优先级大于此设置。


`lombok.getter.noIsPrefix =` [`true` | `false`] (default: `false`)


`lombok.getter.noIsPrefix =` [`true` | `false`] (默认: `false`)


If set to `true`, `getter`s generated for `boolean` fields will use the `get` prefix instead of the default `is` prefix, and any generated code that calls `getter`s, such as `@ToString`, will also use `get` instead of `is`


如果设置为 `true` ，则为 `boolean` 字段生成的 `getter` 将使用`get` 前缀而不是默认 `is` 前缀，并且任何调用 `getter` 的生成代码，例如 `@ToString` ，也将使用 `get ` 而不是 `is`


`lombok.setter.flagUsage =` [`warning` | `error`] (default: not set)


`lombok.setter.flagUsage =` [`warning` | `error`] (默认：未设置)


Lombok will flag any usage of `@Setter` as a warning or error if configured.


如果已配置，Lombok 会将任何使用 `@Setter` 的情况标记为警告或错误。


`lombok.getter.flagUsage =` [`warning` | `error`] (default: not set)


`lombok.getter.flagUsage =` [`warning` | `error`] (默认：未设置)


Lombok will flag any usage of `@Getter` as a warning or error if configured.


如果已配置，Lombok 会将任何使用 `@Getter` 的情况标记为警告或错误。


`lombok.copyableAnnotations =` [A list of fully qualified types] (default: empty list)


`lombok.copyableAnnotations =` [完全限定类型的列表] (默认值：空列表)


Lombok will copy any of these annotations from the field to the `setter` parameter, and to the `getter` method. 
Note that lombok ships with a bunch of annotations 'out of the box' which are known to be copyable: 
All popular `nullable`/`nonnull` annotations.


Lombok 会将这些注解中的任何一个从字段复制到 `setter` 参数，以及 `getter` 方法。
请注意，lombok 附带了一堆“开箱即用”的注解，这些注解是可复制的：
所有流行的 `nullable`/`nonnull` 注解。


## Small print


For generating the method names, the first character of the field, if it is a lowercase character, is title-cased, otherwise, it is left unmodified. 
Then, `get`/`set`/`is` is prefixed.


为了生成方法名，字段的第一个字符（如果是小写字符）将以标题大小写显示，否则将保持不变。
然后， `get`/`set`/`is` 是前缀。


**No method is generated if any method already exists with the same name (case insensitive) and same parameter count.** 
For example, `getFoo()` will not be generated if there's already a method `getFoo(String... x)` even though it is technically possible to make the method. 
This caveat exists to prevent confusion. 
If the generation of a method is skipped for this reason, a warning is emitted instead. 
Varargs count as `0` to `N` parameters. 
You can mark any method with `@lombok.experimental.Tolerate` to hide them from lombok.


**如果已存在具有相同名称（不区分大小写）和相同参数计数的任何方法，则不会生成任何方法。**
例如，如果已经有一个方法 `getFoo(String... x)` ，即使在技术上可以创建该方法，也不会生成 `getFoo()` 。
存在此警告是为了防止混淆。
如果因此跳过方法的生成，则会发出警告。
可变参数计为“0”到“N”个参数。
您可以使用 `@lombok.experimental.Tolerate` 标记任何方法以将它们从 lombok 中隐藏。


For `boolean` fields that start with `is` immediately followed by a title-case letter, nothing is prefixed to generate the `getter` name.


对于以 `is` 开头的 `boolean` 字段，后面紧跟一个标题大小写字母，没有任何前缀来生成 `getter` 名称。


Any variation on `boolean` will not result in using the `is` prefix instead of the `get` prefix; for example, returning `java.lang.Boolean` results in a `get` prefix, not an `is` prefix.


`boolean` 的任何变体都不会导致使用 `is` 前缀而不是 `get` 前缀；例如，返回 `java.lang.Boolean` 会产生 `get` 前缀，而不是 `is` 前缀。


A number of annotations from popular libraries that indicate non-nullness, such as `javax.annotation.Nonnull`, if present on the field, result in an explicit null check in the generated setter.


来自流行库的许多指示非空性的注释，例如 `javax.annotation.Nonnull` ，如果出现在字段中，会导致在生成的 `setter` 中进行显式空值检查。


Various well-known annotations about nullability, such as `org.eclipse.jdt.annotation.NonNull`, are automatically copied over to the right place (method for `getter`s, parameter for `setter`s). 
You can specify additional annotations that should always be copied via lombok [configuration key](./../configuration.md) `lombok.copyableAnnotations`.


各种众所周知的关于可空性的注释，例如 `org.eclipse.jdt.annotation.NonNull` ，会自动复制到正确的位置（ `getter` 的方法， `setter` 的参数）。
您可以指定应始终通过 lombok [configuration key](./../configuration.md) `lombok.copyableAnnotations`复制的附加注解。


You can annotate a class with a `@Getter` or `@Setter` annotation. 
Doing so is equivalent to annotating all non-static fields in that class with that annotation. 
`@Getter`/`@Setter` annotations on fields take precedence over the ones on classes.


您可以使用 `@Getter` 或 `@Setter` 注解来注解一个类。
这样做等效于使用该批注对该类中的所有非静态字段进行批注。
`@Getter``@Setter` 字段上的注解优先级大于类上的注解。


Using the `AccessLevel.NONE` access level simply generates nothing. 
It's useful only in combination with `@Data `or a class-wide `@Getter` or `@Setter`.


使用 `AccessLevel.NONE` 访问级别不会产生任何结果。
它仅在与 `@Data` 或类范围内的 `@Getter` 或 `@Setter` 结合使用时才有用。


`@Getter` can also be used on enums. 
`@Setter` can't, not for a technical reason, but for a pragmatic one: `Setter`s on enums are an extremely bad idea.


`@Getter` 也可以用于枚举。
`@Setter` 不能，不是出于技术原因，而是出于务实的原因：枚举上的 `Setter` 是一个非常糟糕的主意。
