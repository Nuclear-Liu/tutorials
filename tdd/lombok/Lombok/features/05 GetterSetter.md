# `@Getter` and `@Setter`

## Overview


You can annotate any field with `@Getter` and/or `@Setter`, to let lombok generate the default `getter`/`setter` automatically.
A default `getter` simply returns the field, and is named `getFoo` if the field is called `foo` (or `isFoo` if the field's type is `boolean`). 
A default `setter` is named `setFoo` if the field is called `foo`, returns `void`, and takes 1 parameter of the same type as the field. 
It simply sets the field to this value.


你可以用`@Getter` 和或`@Setter` 注释任何字段，让lombok 自动生成默认的 `getter`/`setter` 。
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

