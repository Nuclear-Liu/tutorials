# `@Builder`

## Overview


The `@Builder` annotation produces complex builder APIs for your classes.


`@Builder` 注释为您的类生成复杂的构建器 API。


`@Builder` lets you automatically produce the code required to have your class be instantiable with code such as:


`@Builder` 允许您自动生成使类可以使用以下代码实例化所需的代码：


```text
Person.builder()
    .name("Adam Savage")
    .city("San Francisco")
    .job("Mythbusters")
    .job("Unchained Reaction")
    .build();
```


`@Builder` can be placed on a class, or on a constructor, or on a method. 
While the "on a class" and "on a constructor" mode are the most common use-case, `@Builder` is most easily explained with the "method" use-case.


`@Builder`可以放在类、构造函数或方法上。
虽然 "on a class" 和 "on a constructor" 模式是最常见的用例，“method”用例最容易解释 `@Builder` 。


A method annotated with `@Builder` (from now on called the target) causes the following 7 things to be generated:


用`@Builder` 注释的方法（从现在起称为目标）会导致生成以下 7 件事：


* An inner static class named `FooBuilder`, with the same type arguments as the static method (called the builder).
* In the builder: One `private` non-`static` non-`final` field for each parameter of the target.
* In the builder: A package private no-`args` empty constructor.
* In the builder: A 'setter'-like method for each parameter of the target: 
  It has the same type as that parameter and the same name. 
  It returns the builder itself, so that the setter calls can be chained, as in the above example.
* In the builder: A `build()` method which calls the method, passing in each field. 
  It returns the same type that the target returns.
* In the builder: A sensible `toString()` implementation.
* In the class containing the _target_: A `builder()` method, which creates a new instance of the builder.


* 一个名为 `FooBuilder` 的内部静态类，具有与静态方法（称为构建器）相同的类型参数。
* 在构建器中：目标的每个参数都有一个 `private` 非 `static` 非 `final` 字段。
* 在构建器中：一个包私有的 no-`args` 空构造函数。
* 在构建器中：针对目标的每个参数的类似“setter”的方法：
  它与该参数具有相同的类型和相同的名称。
  它返回构建器本身，以便可以链接 setter 调用，如上例所示。
* 在构建器中：一个`build()` 方法调用方法，传入每个字段。
  它返回与目标返回的相同类型。
* 在构建器中：一个合理的 `toString()` 实现。
* 在包含目标的类中：一个 `builder()` 方法，它创建一个新的构建器实例。


Each listed generated element will be silently skipped if that element already exists (disregarding parameter counts and looking only at names). 
This includes the builder itself: 
If that class already exists, lombok will simply start injecting fields and methods inside this already existing class, unless of course the fields / methods to be injected already exist. 
You may not put any other method (or constructor) generating lombok annotation on a builder class though; for example, you can not put `@EqualsAndHashCode` on the builder class.


如果该元素已经存在（不考虑参数计数并只查看名称），则每个列出的生成元素将被静默跳过。
这包括构建器本身：
如果该类已经存在，lombok 将简单地开始在这个已经存在的类中注入字段和方法，当然除非要注入的字段方法已经存在。
但是，您不能在构建器类上放置任何其他方法（或构造函数）来生成 lombok 注释；例如，您不能将 `@EqualsAndHashCode` 放在构建器类上。


`@Builder` can generate so-called '`singular`' methods for collection parameters/fields. 
These take 1 element instead of an entire list, and add the element to the list. 
For example:


`@Builder` 可以为集合参数字段生成所谓的 “`singular`” 方法。
这些需要 1 个元素而不是整个列表，并将元素添加到列表中。
例如：


```text
Person.builder()
    .job("Mythbusters")
    .job("Unchained Reaction")
    .build();
```


would result in the `List<String> jobs` field to have 2 strings in it. 
To get this behavior, the field/parameter needs to be annotated with `@Singular`. 
The feature has [its own documentation]().


将导致 `List<String> jobs` 字段中有 2 个字符串。
要获得此行为，需要使用 `@Singular` 对 field/parameter 进行注解。
该功能有 [自己的文档]() 。


Now that the "method" mode is clear, putting a `@Builder` annotation on a constructor functions similarly; effectively, constructors are just static methods that have a special syntax to invoke them: 
Their 'return type' is the class they construct, and their type parameters are the same as the type parameters of the class itself.


现在 "method" 模式已经明确，类似地在构造函数上放置一个 `@Builder` 注解；实际上，构造函数只是具有特殊语法来调用它们的静态方法：
它们的 'return type' 是它们构造的类，它们的类型参数与类本身的类型参数相同。


Finally, applying `@Builder` to a class is as if you added `@AllArgsConstructor(access = AccessLevel.PACKAGE)` to the class and applied the `@Builder` annotation to this `all-args-constructor`. 
This only works if you haven't written any explicit constructors yourself. 
If you do have an explicit constructor, put the `@Builder` annotation on the constructor instead of on the class. 
Note that if you put both `@Value` and `@Builder` on a class, the package-private constructor that `@Builder` wants to generate 'wins' and suppresses the constructor that `@Value` wants to make.


最后，将 `@Builder` 应用到一个类就好像你将 `@AllArgsConstructor(access = AccessLevel.PACKAGE)` 添加到该类并将`@Builder` 注解应用到这个 `all-args-constructor` 。
这仅在您自己没有编写任何显式构造函数时才有效。
如果您确实有一个显式构造函数，请将 `@Builder` 注释放在构造函数而不是类上。
请注意，如果您将 `@Value` 和 `@Builder` 都放在一个类上，`@Builder` 想要生成的包私有构造函数将获胜并抑制 `@Value` 想要创建的构造函数。


If using `@Builder` to generate builders to produce instances of your own class (this is always the case unless adding `@Builder` to a method that doesn't return your own type), you can use `@Builder(toBuilder = true)` to also generate an instance method in your class called `toBuilder()`; it creates a new builder that starts out with all the values of this instance. 
You can put the `@Builder.ObtainVia` annotation on the parameters (in case of a constructor or method) or fields (in case of `@Builder` on a type) to indicate alternative means by which the value for that field/parameter is obtained from this instance. 
For example, you can specify a method to be invoked: `@Builder.ObtainVia(method = "calculateFoo")`.


如果使用`@Builder` 生成构建器来生成您自己的类的实例（除非将`@Builder` 添加到不返回您自己的类型的方法中，情况总是如此），您可以使用`@Builder(toBuilder = true)` 也在你的类中生成一个名为 `toBuilder()` 的实例方法；它创建一个新的构建器，该构建器以该实例的所有值开始。
您可以将 `@Builder.ObtainVia` 注释放在参数（在构造函数或方法的情况下）或字段（在类型上的 `@Builder` 的情况下）上，以指示获取该字段参数值的替代方法从这个例子。
例如，您可以指定要调用的方法：`@Builder.ObtainVia(method = "calculateFoo")`。


The name of the builder class is `FoobarBuilder`, where Foobar is the simplified, title-cased form of the return type of the target - that is, the name of your type for `@Builder` on constructors and types, and the name of the return type for `@Builder` on methods. 
For example, if `@Builder` is applied to a class named `com.yoyodyne.FancyList<T>`, then the builder name will be `FancyListBuilder<T>`. 
If `@Builder` is applied to a method that returns `void`, the builder will be named `VoidBuilder`.


构建器类的名称是 `FoobarBuilder` ，其中 Foobar 是目标返回类型的简化的标题大小写形式 - 即构造函数和类型上 `@Builder` 的类型名称，以及名称方法上 `@Builder` 的返回类型。
例如，如果将 `@Builder` 应用于名为 `com.yoyodyne.FancyList<T>` 的类，则构建器名称将为 `FancyListBuilder<T>` 。
如果将 `@Builder` 应用于返回 `void` 的方法，则构建器将被命名为 `VoidBuilder`。


The configurable aspects of `builder` are:


`builder` 的可配置方面是：


* The `builder`'s class name (default: return type + 'Builder')
* The `build()` method's name (default: "`build`")
* The `builder()` method's name (default: "`builder`")
* If you want `toBuilder()` (default: no)
* The access level of all generated elements (default: `public`).
* (discouraged) If you want your `builder`'s 'set' methods to have a prefix, i.e. `Person.builder().setName("Jane").build()` instead of `Person.builder().name("Jane").build()` and what it should be.


* `builder` 的类名（默认：返回类型 + 'Builder'）
* `build()` 方法的名称（默认值：“`build`”）
* `builder()` 方法的名称（默认值：“`builder`”）
* 如果你想要`toBuilder()`（默认值：否）
* 所有生成元素的访问级别（默认：`public`）。
* （不鼓励）如果你希望你的`builder` 的'set' 方法有一个前缀，即`Person.builder().setName("Jane").build()` 而不是`Person.builder().name ("Jane").build()` 以及它应该是什么。


Example usage where all options are changed from their defaults:
`@Builder(builderClassName = "HelloWorldBuilder", buildMethodName = "execute", builderMethodName = "helloWorld", toBuilder = true, access = AccessLevel.PRIVATE, setterPrefix = "set")`
Looking to use your builder with [Jackson](https://github.com/FasterXML/jackson), the JSON/XML tool? We have you covered: Check out the [`@Jacksonized`](https://projectlombok.org/features/experimental/Jacksonized) feature.


所有选项都从其默认值更改的示例用法：
`@Builder(builderClassName = "HelloWorldBuilder", buildMethodName = "execute", builderMethodName = "helloWorld", toBuilder = true, access = AccessLevel.PRIVATE, setterPrefix = "set")`
希望将您的构建器与 JSON/XML 工具 [Jackson]() 一起使用？我们为您提供：查看 [`@Jacksonized`]() 功能。


## `@Builder.Default`


If a certain field/parameter is never set during a build session, then it always gets `0` / `null` / `false`. 
If you've put `@Builder` on a class (and not a method or constructor) you can instead specify the default directly on the field, and annotate the field with `@Builder.Default`:
`@Builder.Default private final long created = System.currentTimeMillis();`


如果在构建会话期间从未设置某个字段参数，那么它总是会得到 `0` / `null` / `false` 。
如果您将 `@Builder` 放在一个类（而不是方法或构造函数）上，您可以直接在字段上指定默认值，并使用 `@Builder.Default` 注释该字段：
`@Builder.Default private final long created = System.currentTimeMillis();`


## `@Singular`


By annotating one of the parameters (if annotating a method or constructor with `@Builder`) or fields (if annotating a class with `@Builder`) with the `@Singular` annotation, lombok will treat that builder node as a collection, and it generates 2 'adder' methods instead of a 'setter' method. 
One which adds a single element to the collection, and one which adds all elements of another collection to the collection. 
No setter to just set the collection (replacing whatever was already added) will be generated. 
A '`clear`' method is also generated. 
These 'singular' builders are very complicated in order to guarantee the following properties:


通过使用 `@Singular` 注释来注释一个参数（如果用 `@Builder` 注释一个方法或构造函数）或字段（如果用 `@Builder` 注释一个类），lombok 会将该构建器节点视为一个集合，它生成 2 个 'adder' 方法而不是 'setter' 方法。
一个将单个元素添加到集合中，另一个将另一个集合的所有元素添加到集合中。
不会生成仅设置集合（替换已添加的任何内容）的设置器。
还会生成 '`clear`' 方法。
这些“单一”构建器非常复杂，以保证以下属性：


* When invoking `build()`, the produced collection will be immutable.
* Calling one of the '`adder`' methods, or the '`clear`' method, after invoking `build()` does not modify any already generated objects, and, if `build()` is later called again, another collection with all the elements added since the creation of the builder is generated.
* The produced collection will be compacted to the smallest feasible format while remaining efficient.


* 调用 `build()` 时，生成的集合将是不可变的。
* 调用 '`adder`' 方法之一或 '`clear`' 方法，在调用 `build()` 后不会修改任何已经生成的对象，并且，如果稍后再次调用 `build()` ，则另一个包含所有元素的集合添加自生成构建器的创建。
* 生成的集合将被压缩为最小的可行格式，同时保持高效。


`@Singular` can only be applied to collection types known to lombok. 
Currently, the supported types are:


`@Singular` 只能应用于 lombok 已知的集合类型。
目前，支持的类型有：


* `java.util`:
  * `Iterable`, `Collection`, and `List` (backed by a compacted unmodifiable `ArrayList` in the general case).
  * `Set`, `SortedSet`, and `NavigableSet` (backed by a smartly sized unmodifiable `HashSet` or `TreeSet` in the general case).
  * `Map`, `SortedMap`, and `NavigableMap` (backed by a smartly sized unmodifiable `HashMap` or `TreeMap` in the general case).
* [Guava]()'s `com.google.common.collect`:
  * `ImmutableCollection` and `ImmutableList` (backed by the builder feature of `ImmutableList`).
  * `ImmutableSet` and `ImmutableSortedSet` (backed by the builder feature of those types).
  * `ImmutableMap`, `ImmutableBiMap`, and `ImmutableSortedMap` (backed by the builder feature of those types).
  * `ImmutableTable` (backed by the builder feature of `ImmutableTable`).


* `java.util`:
  * `Iterable`, `Collection`, and `List` ( 在一般情况下由压缩的不可修改的 `ArrayList` 支持 ).
  * `Set`, `SortedSet`, and `NavigableSet` (在一般情况下，由智能大小的不可修改的 `HashSet` 或 `TreeSet` 支持).
  * `Map`, `SortedMap`, and `NavigableMap` (在一般情况下由智能大小的不可修改的 `HashMap` 或 `TreeMap` 支持).
* [Guava]()'s `com.google.common.collect`:
  * `ImmutableCollection` and `ImmutableList` (由 `ImmutableList` 的构建器功能支持).
  * `ImmutableSet` and `ImmutableSortedSet` (由这些类型的构建器功能支持).
  * `ImmutableMap`, `ImmutableBiMap`, and `ImmutableSortedMap` (由这些类型的构建器功能支持).
  * `ImmutableTable` (由 `ImmutableTable` 的构建器功能支持).


If your identifiers are written in common english, lombok assumes that the name of any collection with `@Singular` on it is an english plural and will attempt to automatically singularize that name. 
If this is possible, the `add-one` method will use this name. 
For example, if your collection is called `statuses`, then the `add-one` method will automatically be called `status`. 
You can also specify the singular form of your identifier explicitly by passing the singular form as argument to the annotation like so: `@Singular("axis") List<Line> axes;`.
If lombok cannot singularize your identifier, or it is ambiguous, lombok will generate an error and force you to explicitly specify the singular name.


如果您的标识符是用通用英语编写的，lombok 会假定任何带有 `@Singular` 的集合的名称都是英文复数，并会尝试自动将该名称单数化。
如果可能， `add-one` 方法将使用此名称。
例如，如果您的集合名为`statuses`，那么`add-one` 方法将自动称为`status`。
您还可以通过将单数形式作为参数传递给注解来显式指定标识符的单数形式，如下所示： `@Singular("axis") List<Line> axes;` 。
如果 lombok 无法将您的标识符单数化，或者它不明确，lombok 将生成错误并强制您明确指定单数名称。


The snippet below does not show what lombok generates for a `@Singular` field/parameter because it is rather complicated. 
You can view a snippet [here](https://projectlombok.org/features/builderSingular).


下面的代码片段没有显示 lombok 为 `@Singular` 字段参数生成的内容，因为它相当复杂。
您可以在 [此处](https://projectlombok.org/features/builderSingular) 查看片段。


If also using `setterPrefix = "with"`, the generated names are, for example, `withName` (add 1 name), `withNames` (add many names), and `clearNames` (reset all names).


如果还使用`setterPrefix = "with"`，则生成的名称例如为`withName`（添加1 个名称）、`withNames`（添加多个名称）和`clearNames`（重置所有名称）。


Ordinarily, the generated 'plural form' method (which takes in a collection, and adds each element in this collection) will check if a `null` is passed the same way `@NonNull` does (by default, throws a `NullPointerException` with an appropriate message). 
However, you can also tell lombok to ignore such collection (so, add nothing, return immediately): `@Singular(ignoreNullCollections = true`.


通常，生成的“复数形式”方法（接受一个集合，并添加该集合中的每个元素）将检查是否以与 `@NonNull` 相同的方式传递了一个 `null` （默认情况下，抛出一个 `NullPointerException` 带有适当的消息）。
但是，您也可以告诉 lombok 忽略此类集合（因此，不添加任何内容，立即返回）：`@Singular(ignoreNullCollections = true`。


## With Jackson


You can customize parts of your builder, for example adding another method to the builder class, or annotating a method in the builder class, by making the builder class yourself. 
Lombok will generate everything that you do not manually add, and put it into this builder class. 
For example, if you are trying to configure [jackson](https://github.com/FasterXML/jackson) to use a specific subtype for a collection, you can write something like:


您可以自定义构建器的各个部分，例如通过自己制作构建器类向构建器类添加另一个方法，或在构建器类中注释一个方法。
Lombok 会生成所有你没有手动添加的东西，并把它放到这个构建器类中。
例如，如果您尝试将 [jackson](https://github.com/FasterXML/jackson)  配置为对集合使用特定子类型，则可以编写如下内容：


```java
@Value @Builder
@JsonDeserialize(builder = JacksonExample.JacksonExampleBuilder.class)
public class JacksonExample {
	@Singular(nullBehavior = NullCollectionBehavior.IGNORE) private List<Foo> foos;
	
	@JsonPOJOBuilder(withPrefix = "")
	public static class JacksonExampleBuilder implements JacksonExampleBuilderMeta {
	}
	
	private interface JacksonExampleBuilderMeta {
		@JsonDeserialize(contentAs = FooImpl.class) JacksonExampleBuilder foos(List<? extends Foo> foos);
	}
}
```
