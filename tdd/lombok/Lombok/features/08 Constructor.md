# `@NoArgsConstructor`, `@RequiredArgsConstructor`, `@AllArgsConstructor`

## Overview


This set of 3 annotations generate a constructor that will accept 1 parameter for certain fields, and simply assigns this parameter to the field.


这组 3 个注释生成一个构造函数，该构造函数将接受某些字段的 1 个参数，并简单地将此参数分配给该字段。


`@NoArgsConstructor` will generate a constructor with no parameters. 
If this is not possible (because of `final` fields), a compiler error will result instead, unless `@NoArgsConstructor(force = true)` is used, then all `final` fields are initialized with `0` / `false` / `null`. 
For fields with constraints, such as `@NonNull` fields, no check is generated,so be aware that these constraints will generally not be fulfilled until those fields are properly initialized later. 
Certain java constructs, such as hibernate and the Service Provider Interface require a no-args constructor. 
This annotation is useful primarily in combination with either `@Data` or one of the other constructor generating annotations.


`@NoArgsConstructor` 将生成一个没有参数的构造函数。
如果这是不可能的（因为 `final` 字段），则会导致编译器错误，除非使用 `@NoArgsConstructor(force = true)` ，然后所有 `final` 字段都初始化为 `0` / `false` / `null` 。
对于具有约束的字段，例如 `@NonNull` 字段，不会生成检查，因此请注意，在这些字段稍后正确初始化之前，通常不会满足这些约束。
某些 java 构造，例如 hibernate 和服务提供者接口，需要无参数构造函数。
此注释主要与 `@Data` 或其他生成注释的构造函数之一结合使用。


`@RequiredArgsConstructor` generates a constructor with 1 parameter for each field that requires special handling. 
All non-initialized `final` fields get a parameter, as well as any fields that are marked as `@NonNull` that aren't initialized where they are declared. 
For those fields marked with `@NonNull`, an explicit null check is also generated. 
The constructor will throw a `NullPointerException` if any of the parameters intended for the fields marked with `@NonNull` contain `null`. 
The order of the parameters match the order in which the fields appear in your class.


`@RequiredArgsConstructor` 为每个需要特殊处理的字段生成一个带有 1 个参数的构造函数。
所有未初始化的 `final` 字段都会获得一个参数，以及任何标记为 `@NonNull` 且未在声明位置初始化的字段。
对于那些标有“@NonNull”的字段，还会生成一个显式的空检查。
如果用于标记为 `@NonNull` 的字段的任何参数包含 `null` ，构造函数将抛出 `NullPointerException` 。
参数的顺序与字段在您的类中出现的顺序相匹配。


`@AllArgsConstructor` generates a constructor with 1 parameter for each field in your class. 
Fields marked with `@NonNull` result in null checks on those parameters.


`@AllArgsConstructor` 为类中的每个字段生成一个带有 1 个参数的构造函数。
标有 `@NonNull` 的字段会导致对这些参数进行空检查。


Each of these annotations allows an alternate form, where the generated constructor is always private, and an additional static factory method that wraps around the private constructor is generated. 
This mode is enabled by supplying the `staticName` value for the annotation, like so: `@RequiredArgsConstructor(staticName="of")`. 
Such a static factory method will infer generics, unlike a normal constructor. 
This means your API users get write `MapEntry.of("foo", 5)` instead of the much longer `new MapEntry<String, Integer>("foo", 5)`.


这些注释中的每一个都允许一种替代形式，其中生成的构造函数始终是私有的，并且生成了一个附加的静态工厂方法，它环绕着私有构造函数。
这种模式是通过为注解提供 `staticName` 值来启用的，就像这样：`@RequiredArgsConstructor(staticName="of")`。
与普通构造函数不同，这样的静态工厂方法将推断泛型。
这意味着您的 API 用户可以写入 `MapEntry.of("foo", 5)` 而不是更长的 `new MapEntry<String, Integer>("foo", 5)`。


To put annotations on the generated constructor, you can use `onConstructor=@__({@AnnotationsHere})`, but be careful; this is an experimental feature. 
For more details see the documentation on the [onX](https://projectlombok.org/features/experimental/onX) feature.


要将注解放在生成的构造函数上，可以使用`onConstructor=@__({@AnnotationsHere})`，但要小心；这是一个实验性功能。
有关更多详细信息，请参阅有关 [onX]() 功能的文档。


Static fields are skipped by these annotations.


这些注释会跳过静态字段。


Unlike most other lombok annotations, the existence of an explicit constructor does not stop these annotations from generating their own constructor. 
This means you can write your own specialized constructor, and let lombok generate the boilerplate ones as well. 
If a conflict arises (one of your constructors ends up with the same signature as one that lombok generates), a compiler error will occur.


与大多数其他 lombok 注释不同，显式构造函数的存在不会阻止这些注释生成自己的构造函数。
这意味着您可以编写自己的专用构造函数，并让 lombok 生成样板文件。
如果出现冲突（您的构造函数之一的签名与 lombok 生成的签名相同），则会发生编译器错误。

