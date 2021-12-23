# `@EqualsAndHashCode`


Equality made easy: Generates `hashCode` and `equals` implementations from the fields of your object.


相等变得容易：从对象的字段生成`hashCode` 和`equals` 实现。


## Overview


Any class definition may be annotated with `@EqualsAndHashCode` to let lombok generate implementations of the `equals(Object other)` and `hashCode()` methods. 
By default, it'll use all non-`static`, non-transient fields, but you can modify which fields are used (and even specify that the output of various methods is to be used) by marking type members with `@EqualsAndHashCode.Include` or `@EqualsAndHashCode.Exclude`. 
Alternatively, you can specify exactly which fields or methods you wish to be used by marking them with `@EqualsAndHashCode.Include` and using `@EqualsAndHashCode(onlyExplicitlyIncluded = true)`.


任何类定义都可以用 `@EqualsAndHashCode` 进行注释，让 lombok 生成 `equals(Object other)` 和 `hashCode()` 方法的实现。
默认情况下，它将使用所有非 `static` 、非瞬态字段，但您可以通过使用 `@EqualsAndHashCode.Include` 或 `@EqualsAndHashCode.Exclude` 标记类型成员来修改使用哪些字段（甚至指定要使用各种方法的输出） 。
或者，您可以通过使用 `@EqualsAndHashCode.Include` 和使用 `@EqualsAndHashCode(onlyExplicitlyIncluded = true)` 来标记它们来准确指定要使用的字段或方法。


If applying `@EqualsAndHashCode` to a class that extends another, this feature gets a bit trickier. 
Normally, auto-generating an `equals` and `hashCode` method for such classes is a bad idea, as the superclass also defines fields, which also need `equals`/`hashCode` code but this code will not be generated. 
By setting `callSuper` to `true`, you can include the `equals` and `hashCode` methods of your superclass in the generated methods. 
For `hashCode`, the result of `super.hashCode()` is included in the hash algorithm, and forequals, the generated method will return false if the `super` implementation thinks it is not equal to the passed in object. 
Be aware that not all equals implementations handle this situation properly. 
However, lombok-generated `equals` implementations do handle this situation properly, so you can safely call your superclass equals if it, too, has a lombok-generated `equals` method. 
If you have an explicit superclass you are forced to supply some value for `callSuper` to acknowledge that you've considered it; failure to do so results in a warning.


如果将 `@EqualsAndHashCode` 应用于扩展另一个类的类，则此功能会变得有点棘手。
通常，为此类类自动生成 `equals` 和 `hashCode` 方法是一个坏主意，因为超类也定义了字段，这些字段也需要 `equals`/`hashCode` 代码，但不会生成此代码。
通过将 `callSuper` 设置为 `true` ，您可以在生成的方法中包含超类的 `equals` 和 `hashCode` 方法。
对于`hashCode`，`super.hashCode()`的结果包含在hash算法中，而 forequals ，如果 `super` 实现认为它不等于传入的对象，则生成的方法将返回false。
请注意，并非所有 equals 实现都能正确处理这种情况。
但是，lombok 生成的 `equals` 实现确实可以正确处理这种情况，因此如果超类也有 lombok 生成的 `equals` 方法，您可以安全地调用它。
如果你有一个显式的超类，你必须为 `callSuper` 提供一些值来确认你已经考虑过它；不这样做会导致警告。


Setting `callSuper` to true when you don't extend anything (you extend `java.lang.Object`) is a compile-time error, because it would turn the generated `equals()` and `hashCode()` implementations into having the same behaviour as simply inheriting these methods from `java.lang.Object`: 
only the same object will be equal to each other and will have the same `hashCode`. 
Not setting `callSuper` to true when you extend another class generates a warning, because unless the superclass has no (equality-important) fields, lombok cannot generate an implementation for you that takes into account the fields declared by your superclasses. 
You'll need to write your own implementations, or rely on the `callSuper` chaining facility. 
You can also use the `lombok.equalsAndHashCode.callSuper` config key.


当你不扩展任何东西（你扩展了 `java.lang.Object`）时将 `callSuper` 设置为 true 是一个编译时错误，因为它会将生成的 `equals()` 和 `hashCode()` 实现变成与简单地从 `java.lang.Object` 继承这些方法具有相同的行为：
只有相同的对象才会彼此相等，并且具有相同的 `hashCode`。
当您扩展另一个类时，不将 `callSuper` 设置为 true 会生成警告，因为除非超类没有（相等重要的）字段，否则 lombok 无法为您生成考虑到您的超类声明的字段的实现。
您需要编写自己的实现，或者依赖 `callSuper` 链接工具。
您还可以使用 `lombok.equalsAndHashCode.callSuper` 配置键。


NEW in Lombok 0.10: Unless your class is `final` and extends `java.lang.Object`, lombok generates a `canEqual` method which means JPA proxies can still be equal to their base class, but subclasses that add new state don't break the equals contract. 
The complicated reasons for why such a method is necessary are explained in this paper: [How to Write an Equality Method in Java](). 
If all classes in a hierarchy are a mix of scala case classes and classes with lombok-generated `equals` methods, all equality will 'just work'. 
If you need to write your own `equals` methods, you should always override `canEqual` if you change `equals` and `hashCode`.


Lombok 0.10 中的新内容：除非您的类是 `final` 并扩展了 `java.lang.Object`，lombok 会生成一个 `canEqual` 方法，这意味着 JPA 代理仍然可以等于它们的基类，但添加新状态的子类不会打破平等契约。
本文解释了为什么需要这种方法的复杂原因： [How to Write an Equality Method in Java](./../How%20to%20Write%20an%20Equality%20Method%20in%20Java.md) 。
如果层次结构中的所有类都是 scala 案例类和具有 lombok 生成的 `equals` 方法的类的混合，则所有相等都将“正常工作”。
如果您需要编写自己的 `equals` 方法，并且更改 `equals` 和 `hashCode`，则应始终覆盖 `canEqual`。


NEW in Lombok 1.14.0: To put annotations on the `other` parameter of the `equals` (and, if relevant, `canEqual`) method, you can use `onParam=@__({@AnnotationsHere})`. 
Be careful though! This is an experimental feature. 
For more details see the documentation on the [onX]() feature.


Lombok 1.14.0 中的新功能：要将注释放在 `equals`（以及，如果相关，`canEqual`）方法的 `other` 参数上，您可以使用 `onParam=@__({@AnnotationsHere})` 。
不过要小心！这是一个实验性功能。
有关更多详细信息，请参阅有关 [onX]() 功能的文档。


NEW in Lombok 1.18.16: The result of the generated `hashCode()` can be cached by setting `cacheStrategy` to a value other than `CacheStrategy.NEVER`. 
Do not use this if objects of the annotated class can be modified in any way that would lead to the result of `hashCode()` changing.


Lombok 1.18.16 中的新功能：可以通过将“cacheStrategy”设置为“CacheStrategy.NEVER”以外的值来缓存生成的 `hashCode()` 的结果。
如果注解类的对象可以以任何会导致 `hashCode()` 更改的结果的方式修改，请不要使用它。
