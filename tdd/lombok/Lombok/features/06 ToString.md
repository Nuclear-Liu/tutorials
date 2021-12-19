# `@ToString`

## Overview


Any class definition may be annotated with `@ToString` to let lombok generate an implementation of the `toString()` method. 
By default, it'll print your class name, along with each field, in order, separated by commas.


任何类定义都可以用 `@ToString` 注释，让lombok 生成 `toString()` 方法的实现。
默认情况下，它会按顺序打印您的类名以及每个字段，并以逗号分隔。


By setting the `includeFieldNames` parameter to `true` you can add some clarity (but also quite some length) to the output of the `toString()` method.


通过将 `includeFieldNames` 参数设置为 `true` ，您可以为 `toString()` 方法的输出添加一些清晰度（但也有一定的长度）。


By default, all non-`static` fields will be printed. 
If you want to skip some fields, you can annotate these fields with `@ToString.Exclude`. 
Alternatively, you can specify exactly which fields you wish to be used by using `@ToString(onlyExplicitlyIncluded = true)`, then marking each field you want to include with `@ToString.Include`.


默认情况下，将打印所有非 `static` 字段。
如果要跳过某些字段，可以使用`@ToString.Exclude` 注释这些字段。
或者，您可以通过使用 `@ToString(onlyExplicitlyIncluded = true)` 来准确指定您希望使用的字段，然后用 `@ToString.Include` 标记要包含的每个字段。


By setting `callSuper` to `true`, you can include the output of the superclass implementation of `toString` to the output. 
Be aware that the default implementation of `toString()` in `java.lang.Object` is pretty much meaningless, so you probably don't want to do this unless you are extending another class.


通过将 `callSuper` 设置为 `true`，您可以将 `toString` 的超类实现的输出包含到输出中。
请注意， `java.lang.Object` 中 `toString()` 的默认实现几乎毫无意义，因此除非您要扩展另一个类，否则您可能不想这样做。


You can also include the output of a method call in your `toString`. 
Only instance (non-`static`) methods that take no arguments can be included. 
To do so, mark the method with `@ToString.Include`.


您还可以在 `toString` 中包含方法调用的输出。
只能包含不带参数的实例（非 `static` ）方法。
为此，请使用 `@ToString.Include` 标记该方法。


You can change the name used to identify the member with `@ToString.Include(name = "some other name")`, and you can change the order in which the members are printed via `@ToString.Include(rank = -1)`. 
Members without a rank are considered to have rank 0, members of a higher rank are printed first, and members of the same rank are printed in the same order they appear in the source file.


您可以使用 `@ToString.Include(name = "some other name")` 更改用于标识成员的名称，并且可以通过  `@ToString.Include(rank = -1)` 更改成员的打印顺序。
没有等级的成员被认为具有等级 0，更高等级的成员首先打印，相同等级的成员按照它们在源文件中出现的相同顺序打印。
