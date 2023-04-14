# `@EqualsAndHashCode`


Equality made easy: Generates `hashCode` and `equals` implementations from the fields of your object.


相等变得容易：从对象的字段生成 `hashCode` 和 `equals` 实现。


## Overview


Any class definition may be annotated with `@EqualsAndHashCode` to let lombok generate implementations of the `equals(Object other)` and `hashCode()` methods. 
By default, it'll use all non-`static`, non-`transient` fields, but you can modify which fields are used (and even specify that the output of various methods is to be used) by marking type members with `@EqualsAndHashCode.Include` or `@EqualsAndHashCode.Exclude`. 
Alternatively, you can specify exactly which fields or methods you wish to be used by marking them with `@EqualsAndHashCode.Include` and using `@EqualsAndHashCode(onlyExplicitlyIncluded = true)`.


任何类定义都可以用 `@EqualsAndHashCode` 进行注解，让 lombok 生成 `equals(Object other)` 和 `hashCode()` 方法的实现。
默认情况下，它将使用所有非 `static` 、非 `transient` 字段，但您可以通过使用 `@EqualsAndHashCode.Include` 或 `@EqualsAndHashCode.Exclude` 标记类型成员来修改使用哪些字段（甚至指定要使用各种方法的输出） 。
或者，您可以通过使用 `@EqualsAndHashCode.Include` 和使用 `@EqualsAndHashCode(onlyExplicitlyIncluded = true)` 来标记它们来准确指定要使用的字段或方法。


If applying `@EqualsAndHashCode` to a class that extends another, this feature gets a bit trickier. 
Normally, auto-generating an `equals` and `hashCode` method for such classes is a bad idea, as the superclass also defines fields, which also need `equals`/`hashCode` code but this code will not be generated. 
By setting `callSuper` to `true`, you can include the `equals` and `hashCode` methods of your superclass in the generated methods. 
For `hashCode`, the result of `super.hashCode()` is included in the hash algorithm, and forequals, the generated method will return `false` if the `super` implementation thinks it is not equal to the passed in object. 
Be aware that not all equals implementations handle this situation properly. 
However, lombok-generated `equals` implementations do handle this situation properly, so you can safely call your superclass equals if it, too, has a lombok-generated `equals` method. 
If you have an explicit superclass you are forced to supply some value for `callSuper` to acknowledge that you've considered it; failure to do so results in a warning.


如果将 `@EqualsAndHashCode` 应用于扩展另一个类的类，则此功能会变得有点棘手。
通常，为此类类自动生成 `equals` 和 `hashCode` 方法是一个坏主意，因为超类也定义了字段，这些字段也需要 `equals`/`hashCode` 代码，但不会生成此代码。
通过将 `callSuper` 设置为 `true` ，您可以在生成的方法中包含超类的 `equals` 和 `hashCode` 方法。
对于 `hashCode` ， `super.hashCode()` 的结果包含在 hash 算法中，而 forequals ，如果 `super` 实现认为它不等于传入的对象，则生成的方法将返回 `false` 。
请注意，并非所有 equals 实现都能正确处理这种情况。
但是，lombok 生成的 `equals` 实现确实可以正确处理这种情况，因此如果超类也有 lombok 生成的 `equals` 方法，您可以安全地调用它。
如果你有一个显式的超类，你必须为 `callSuper` 提供一些值来确认你已经考虑过它；不这样做会导致警告。


Setting `callSuper` to `true` when you don't extend anything (you extend `java.lang.Object`) is a **compile-time error**, because it would turn the generated `equals()` and `hashCode()` implementations into having the same behaviour as simply inheriting these methods from `java.lang.Object`: 
only the same object will be equal to each other and will have the same `hashCode`. 
Not setting `callSuper` to `true` when you extend another class generates a warning, because unless the superclass has no (equality-important) fields, lombok cannot generate an implementation for you that takes into account the fields declared by your superclasses. 
You'll need to write your own implementations, or rely on the `callSuper` chaining facility. 
You can also use the `lombok.equalsAndHashCode.callSuper` config key.


当你不扩展任何东西（你扩展了 `java.lang.Object`）时将 `callSuper` 设置为 `true` 是一个**编译时错误**，因为它会将生成的 `equals()` 和 `hashCode()` 实现变成与简单地从 `java.lang.Object` 继承这些方法具有相同的行为：
只有相同的对象才会彼此相等，并且具有相同的 `hashCode` 。
当您扩展另一个类时，不将 `callSuper` 设置为 `true` 会生成警告，因为除非超类没有（相等重要的）字段，否则 lombok 无法为您生成考虑到您的超类声明的字段的实现。
您需要编写自己的实现，或者依赖 `callSuper` 链接工具。
您还可以使用 `lombok.equalsAndHashCode.callSuper` 配置键。


NEW in Lombok 0.10: Unless your class is `final` and extends `java.lang.Object`, lombok generates a `canEqual` method which means JPA proxies can still be equal to their base class, but subclasses that add new state don't break the equals contract. 
The complicated reasons for why such a method is necessary are explained in this paper: [How to Write an Equality Method in Java](https://www.artima.com/lejava/articles/equality.html). 
If all classes in a hierarchy are a mix of scala case classes and classes with lombok-generated `equals` methods, all equality will 'just work'. 
If you need to write your own `equals` methods, you should always override `canEqual` if you change `equals` and `hashCode`.


Lombok 0.10 中的新内容：除非您的类是 `final` 并扩展了 `java.lang.Object` ，lombok 会生成一个 `canEqual` 方法，这意味着 JPA 代理仍然可以等于它们的基类，但添加新状态的子类不会打破平等契约。
本文解释了为什么需要这种方法的复杂原因： [How to Write an Equality Method in Java](./../How%20to%20Write%20an%20Equality%20Method%20in%20Java.md) 。
如果层次结构中的所有类都是 scala 案例类和具有 lombok 生成的 `equals` 方法的类的混合，则所有相等都将“正常工作”。
如果您需要编写自己的 `equals` 方法，并且更改 `equals` 和 `hashCode`，则应始终覆盖 `canEqual`。


NEW in Lombok 1.14.0: To put annotations on the `other` parameter of the `equals` (and, if relevant, `canEqual`) method, you can use `onParam=@__({@AnnotationsHere})`. 
Be careful though! This is an experimental feature. 
For more details see the documentation on the [onX]() feature.


Lombok 1.14.0 中的新功能：要将注解放在 `equals`（以及，如果相关，`canEqual`）方法的 `other` 参数上，您可以使用 `onParam=@__({@AnnotationsHere})` 。
不过要小心！这是一个实验性功能。
有关更多详细信息，请参阅有关 [onX]() 功能的文档。


NEW in Lombok 1.18.16: The result of the generated `hashCode()` can be cached by setting `cacheStrategy` to a value other than `CacheStrategy.NEVER`. 
Do not use this if objects of the annotated class can be modified in any way that would lead to the result of `hashCode()` changing.


Lombok 1.18.16 中的新功能：可以通过将 `cacheStrategy` 设置为 `CacheStrategy.NEVER` 以外的值来缓存生成的 `hashCode()` 的结果。
如果注解类的对象可以以任何会导致 `hashCode()` 更改的结果的方式修改，请不要使用它。


## Supported configuration keys:


`lombok.equalsAndHashCode.doNotUseGetters =` [`true` | `false`] (default: `false`)


`lombok.equalsAndHashCode.doNotUseGetters =` [`true` | `false`] (默认: `false`)


If set to `true`, lombok will access fields directly instead of using `getter`s (if available) when generating `equals` and `hashCode` methods. 
The annotation parameter '`doNotUseGetters`', if explicitly specified, takes precedence over this setting.


如果设置为 `true` ，lombok 将在生成 `equals` 和 `hashCode` 方法时直接访问字段而不是使用 `getter`（如果可用）。
注解参数 '`doNotUseGetters`'，如果明确指定，优先级大于该设置。


`lombok.equalsAndHashCode.callSuper =` [`call` | `skip` | `warn`] (default: `warn`)


`lombok.equalsAndHashCode.callSuper =` [`call` | `skip` | `warn`] (默认: `warn`)


If set to `call`, lombok will generate calls to the superclass implementation of `hashCode` and `equals` if your class extends something. 
If set to `skip` no such calls are generated. 
The default behaviour is like `skip`, with an additional warning.


如果设置为 `call` ，lombok 将生成对 `hashCode` 和 `equals` 的超类实现的调用，如果你的类扩展了某些东西。
如果设置为 `skip` ，则不会生成此类调用。
默认行为类似于 `skip` ，带有额外的警告。


`lombok.equalsAndHashCode.flagUsage =` [`warning` | `error`] (default: not set)


`lombok.equalsAndHashCode.flagUsage =` [`warning` | `error`] (默认：未设置)


Lombok will flag any usage of `@EqualsAndHashCode` as a warning or error if configured.


如果已配置，Lombok 会将 `@EqualsAndHashCode` 的任何使用标记为警告或错误。


## Small print


Arrays are 'deep' compared/hashCoded, which means that arrays that contain themselves will result in `StackOverflowErrors`. 
However, this behaviour is no different from e.g. `ArrayList`.


数组是 'deep' 比较哈希编码的，这意味着包含自身的数组将导致 `StackOverflowErrors` 。
但是，这种行为与例如 `ArrayList` 没有什么不同。


You may safely presume that the `hashCode` implementation used will not change between versions of lombok, however this guarantee is not set in stone; if there's a significant performance improvement to be gained from using an alternate hash algorithm, that will be substituted in a future version.


您可以安全地假设所使用的 `hashCode` 实现不会在 lombok 的版本之间改变，但是这种保证不是一成不变的；如果使用替代哈希算法可以获得显着的性能改进，则将在未来版本中替换。


For the purposes of equality, 2 `NaN` (not a number) values for floats and doubles are considered equal, eventhough '`NaN == NaN`' would return `false`. 
This is analogous to `java.lang.Double`'s `equals` method, and is in fact required to ensure that comparing an object to an exact copy of itself returns `true` for equality.


出于相等的目的，浮点数和双精度数的 2 个 `NaN`（不是数字）值被认为是相等的，即使 '`NaN == NaN`' 将返回 `false` 。
这类似于 `java.lang.Double` 的 `equals` 方法，并且实际上需要确保将对象与自身的精确副本进行比较返回 `true` 以表示相等。


If there is any method named either `hashCode` or `equals`, regardless of return type, no methods will be generated, and a warning is emitted instead. 
These 2 methods need to be in sync with each other, which lombok cannot guarantee unless it generates all the methods, hence you always get a warning if one or both of the methods already exist. 
You can mark any method with `@lombok.experimental.Tolerate` to hide them from lombok.


如果有任何名为 `hashCode` 或 `equals` 的方法，无论返回类型如何，都不会生成任何方法，而是发出警告。
这两种方法需要相互同步，lombok 无法保证除非它生成所有方法，因此如果其中一种或两种方法已经存在，您总是会收到警告。
您可以使用 `@lombok.experimental.Tolerate` 标记任何方法以将它们从 lombok 中隐藏。


Attempting to exclude fields that don't exist or would have been excluded anyway (because they are `static` or `transient`) results in warnings on the named fields.


尝试排除不存在或本来会被排除的字段（因为它们是 `static` 或 `transient` ）会导致命名字段出现警告。


If a method is marked for inclusion and it has the same name as a field, it replaces the field (the method is included, the field is excluded).


如果一个方法被标记为包含并且它与一个字段同名，它将替换该字段（该方法被包含，该字段被排除）。


Prior to lombok 1.16.22, `inclusion`/`exclusion` could be done with the of and exclude parameters of the `@EqualsAndHashCode` annotation. 
This old-style inclusion mechanism is still supported but will be deprecated in the future.


在 lombok 1.16.22 之前，可以使用 `@EqualsAndHashCode` 注解的 `inclusion`/`exclusion` 参数完成包含排除。
这种旧式的包含机制仍受支持，但将来会被弃用。


By default, any variables that start with a $ symbol are excluded automatically. 
You can only include them by marking them with `@EqualsAndHashCode.Include`.


默认情况下，任何以符号开头的变量都会被自动排除。
您只能通过用 `@EqualsAndHashCode.Include` 标记它们来包含它们。


If a `getter` exists for a field to be included, it is called instead of using a direct field reference. 
This behaviour can be suppressed:


如果要包含的字段存在 `getter`，则调用它而不是使用直接字段引用。
这种行为可以被抑制：


`@EqualsAndHashCode(doNotUseGetters = true)`


If you have configured a nullity annotation flavour via `lombok.config` key `lombok.addNullAnnotations`, the parameter of both the generated `equals` method as well as any `canEqual` method is annotated with a nullable annotation. 
This is required if you use a `@NonNullByDefault` style annotation in combination with strict nullity checking.


如果您通过 `lombok.config` 键 `lombok.addNullAnnotations` 配置了空注解风格，则生成的 `equals` 方法以及任何 `canEqual` 方法的参数都使用可为空的注解进行注解。
如果您将 `@NonNullByDefault` 样式注解与严格的空值检查结合使用，则这是必需的。
