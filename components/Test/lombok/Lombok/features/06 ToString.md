# `@ToString`

* `@Target(ElementType.TYPE)`


No need to start a debugger to see your fields: Just let lombok generate a `toString` for you!


无需启动调试器即可查看您的字段：只需让 lombok 为您生成一个 `toString` ！


## Overview


Any class definition may be annotated with `@ToString` to let lombok generate an implementation of the `toString()` method. 
By default, it'll print your class name, along with each field, in order, separated by commas.


任何类定义都可以用 `@ToString` 注解，让 lombok 生成 `toString()` 方法的实现。
默认情况下，它会按顺序打印您的类名以及每个字段，并以逗号分隔。


By setting the `includeFieldNames` parameter to `true` you can add some clarity (but also quite some length) to the output of the `toString()` method.


通过将 `includeFieldNames` 参数设置为 `true` ，您可以为 `toString()` 方法的输出添加一些清晰度（但也有一定的长度）。


**By default, all non-`static` fields will be printed.** 
If you want to skip some fields, you can annotate these fields with `@ToString.Exclude`. 
Alternatively, you can specify exactly which fields you wish to be used by using `@ToString(onlyExplicitlyIncluded = true)`, then marking each field you want to include with `@ToString.Include`.


**默认情况下，将打印所有非 `static` 字段。**
如果要**跳过某些字段**，可以使用 `@ToString.Exclude` 注解这些字段。
或者，您可以通过使用 `@ToString(onlyExplicitlyIncluded = true)` 来准确**指定您希望使用的字段**，然后用 `@ToString.Include` 标记要包含的每个字段。


By setting `callSuper` to `true`, you can include the output of the superclass implementation of `toString` to the output. 
Be aware that the default implementation of `toString()` in `java.lang.Object` is pretty much meaningless, so you probably don't want to do this unless you are extending another class.


通过将 `callSuper` 设置为 `true`，您可以将 `toString` 的**超类实现的输出包含到输出中**。
请注意， `java.lang.Object` 中 `toString()` 的默认实现几乎毫无意义，因此除非您要扩展另一个类，否则您可能不想这样做。


You can also include the output of a method call in your `toString`. 
Only instance (non-`static`) methods that take no arguments can be included. 
To do so, mark the method with `@ToString.Include`.


您还可以在 `toString` 中包含方法调用的输出。
只能包含不带参数的实例（非 `static` ）方法。
为此，请使用 `@ToString.Include` 标记该方法。


You can change the name used to identify the member with `@ToString.Include(name = "some other name")`, and you can change the order in which the members are printed via `@ToString.Include(rank = -1)`. 
Members without a rank are considered to have rank `0`, members of a higher rank are printed first, and members of the same rank are printed in the same order they appear in the source file.


您可以使用 `@ToString.Include(name = "some other name")` 更改用于标识成员的名称，并且可以通过  `@ToString.Include(rank = -1)` 更改成员的打印顺序。
没有等级的成员被认为具有等级 `0` ，更高等级的成员首先打印，**相同等级的成员按照它们在源文件中出现的相同顺序打印**。


## Supported configuration keys:



`lombok.toString.includeFieldNames =` [`true` | `false`] (default: `true`)



`lombok.toString.includeFieldNames =` [`true` | `false`] (默认: `true`)


Normally lombok generates a fragment of the `toString` response for each field in the form of `fieldName = fieldValue`. 
If this setting is set to `false`, lombok will omit the name of the field and simply deploy a comma-separated list of all the field values. 
The annotation parameter '`includeFieldNames`', if explicitly specified, takes precedence over this setting.


通常 lombok 会以 `fieldName = fieldValue` 的形式为每个字段生成一个 `toString` 响应片段。
如果此设置设置为 `false` ，lombok 将省略字段的名称并简单地部署所有字段值的逗号分隔列表。
注释参数 `includeFieldNames` （如果明确指定）优先级大于设置。


`lombok.toString.doNotUseGetters =` [`true` | `false`] (default: `false`)


`lombok.toString.doNotUseGetters =` [`true` | `false`] (默认: `false`)


If set to `true`, lombok will access fields directly instead of using `getter`s (if available) when generating `toString` methods. 
The annotation parameter `doNotUseGetters`, if explicitly specified, takes precedence over this setting.


如果设置为 `true` ，lombok 将在生成 `toString` 方法时直接访问字段而不是使用 `getter` （如果可用）。
注释参数 `doNotUseGetters` ，如果明确指定，优先级大于该设置。


`lombok.toString.callSuper =` [`call` | `skip` | `warn`] (default: `skip`)


`lombok.toString.callSuper =` [`call` | `skip` | `warn`] (默认: `skip`)


If set to `call`, lombok will generate calls to the superclass implementation of `toString` if your class extends something. 
If set to `skip` no such call is generated. 
If set to `warn` no such call is generated either, but lombok does generate a warning to tell you about it.


如果设置为 `call` ，如果你的类扩展了某些东西，lombok 将生成对 `toString` 超类实现的调用。
如果设置为“skip”，则不会生成此类调用。
If set to `warn` no such call is generated either, but lombok does generate a warning to tell you about it.


`lombok.toString.flagUsage` = [`warning` | `error`] (default: not set)


`lombok.toString.flagUsage` = [`warning` | `error`] (默认：未设置)


Lombok will flag any usage of `@ToString` as a warning or error if configured. 


如果已配置，Lombok 会将 `@ToString` 的任何使用标记为警告或错误。


## Small print


If there is any method named `toString` with no arguments, regardless of return type, no method will be generated, and instead a warning is emitted explaining that your `@ToString` annotation is doing nothing. 
You can mark any method with `@lombok.experimental.Tolerate` to hide them from lombok.


如果有任何名为 `toString` 的方法没有参数，无论返回类型如何，都不会生成任何方法，而是发出警告，说明您的 `@ToString` 注释没有任何作用。
您可以使用 `@lombok.experimental.Tolerate` 标记任何方法以将它们从 lombok 中隐藏。


Arrays are printed via `Arrays.deepToString`, which means that arrays that contain themselves will result in `StackOverflowError`s. 
However, this behaviour is no different from e.g. `ArrayList`.


数组通过 `Arrays.deepToString` 打印，这意味着包含自身的数组将导致 `StackOverflowError`。
但是，这种行为与例如没有什么不同。例如 `ArrayList` 。


If a method is marked for inclusion and it has the same name as a field, it replaces the `toString` output for that field (the method is included, the field is excluded, and the method's output is printed in the place the field would be printed).


如果一个方法被标记为包含并且它与一个字段具有相同的名称，它将替换该字段的 `toString` 输出（该方法被包含，该字段被排除，并且该方法的输出被打印在该字段将被打印的位置）。


Prior to lombok `1.16.22`, inclusion/exclusion could be done with the `of` and `exclude` parameters `of` the `@ToString` annotation. 
This old-style inclusion mechanism is still supported but will be deprecated in the future.


在 lombok `1.16.22` 之前，可以使用 `@ToString` 注解的 `of` 和 `exclude` 参数 `of` 来完成包含排除。
这种旧式的包含机制仍受支持，但将来会被弃用。


Having both `@ToString.Exclude` and `@ToString.Include` on a member generates a warning; the member will be excluded in this case.


在成员上同时使用 `@ToString.Exclude` 和 `@ToString.Include` 会产生警告；在这种情况下，该成员将被排除在外。


We don't promise to keep the output of the generated `toString()` methods the same between lombok versions. 
You should never design your API so that other code is forced to parse your `toString()` output anyway!


我们不承诺在 lombok 版本之间保持生成的 `toString()` 方法的输出相同。
您永远不应该设计您的 API，以便其他代码无论如何都必须解析您的 `toString()` 输出！


By default, any variables that start with a `$` symbol are excluded automatically. 
You can only include them by using the `@ToString.Include` annotation.


默认情况下，任何以 `$` 符号开头的变量都会被自动排除。
您只能通过使用 `@ToString.Include` 注释来包含它们。


If a `getter` exists for a field to be included, it is called instead of using a direct field reference. 
This behaviour can be suppressed:


如果要包含的字段存在 `getter` ，则调用它而不是使用直接字段引用。
这种行为可以被抑制：


`@ToString(doNotUseGetters = true)`


`@ToString` can also be used on an enum definition.


`@ToString` 也可以用于枚举定义。


If you have configured a nullity annotation flavour via `lombok.config` key `lombok.addNullAnnotations`, the method or return type (as appropriate for the chosen flavour) is annotated with a non-null annotation.


如果您通过 `lombok.config` 键 `lombok.addNullAnnotations` 配置了空注解风格，则方法或返回类型（根据所选风格）将使用非空注解进行注解。
