# @NonNull

* Scope
  * `ElementType.FIELD` _字段声明（包括枚举常量）_

    如果放在一个字段上，任何为该字段赋值的生成方法也将产生这些空检查。
  
  * `ElementType.METHOD` _方法声明_ **不建议使用**
  
    如果放在方法上（包括构造函数），仅校验第一个参数。 配置属性对此情况不起作用
  
  * `ElementType.PARAMETER` _形参声明_

    如果放入一个参数，lombok 将在方法构造函数体的开头插入一个空检查，抛出一个 NullPointerException 与参数的名称作为消息。
  
  * `ElementType.LOCAL_VARIABLE` _局部变量声明_ 不起作用
  
  * `ElementType.TYPE_USE` _类型的使用 `Since: 1.8`_


* `@NonNull` was introduced in lombok v0.11.10.


* `@NonNull` 是在 lombok v0.11.10 中引入的。


## Overview


You can use `@NonNull` on a record component, or a parameter of a method or constructor. 
This will cause to lombok generate a null-check statement for you.


您可以在记录组件、方法或构造函数的参数上使用 `@NonNull` 。
这将导致 lombok 为您生成一个空检查语句。


Lombok has always treated various annotations generally named `@NonNull` on a field as a signal to generate a null-check if lombok generates an entire method or constructor for you, via for example `@Data`. 
However, using lombok's own `@lombok.NonNull` on a **parameter** or **record component** results in the insertion of the null-check at the top of that method.


如果 lombok 为您生成整个方法或构造函数，例如通过 `@Data` ，Lombok 一直将`字段`上通常名为 `@NonNull` 的各种注解视为信号，以生成空检查。
但是，在**参数**或**记录组件**上使用 lombok 自己的 `@lombok.NonNull` 会导致在该方法的顶部插入空检查。


The null-check looks like `if (param == null) throw new NullPointerException("param is marked @NonNull but is null");` and will be inserted at the very top of your method. 
For constructors, the null-check will be inserted immediately following any explicit `this()` or `super()` calls. 
For record components, the null-check will be inserted in the 'compact constructor' (the one that has no argument list at all), which will be generated if you have no constructor. 
If you have written out the record constructor in long form (with parameters matching your components exactly), then nothing happens - you'd have to annotate the parameters of this long-form constructor instead.


空检查看起来像 `if (param == null) throw new NullPointerException("param is marked @NonNull but is null");` 并且将被插入到你的方法的最顶部。
对于**构造函数**，将在任何显式的 `this()` 或 `super()` 调用之后立即插入空检查。
对于记录组件，空检查将插入到 'compact constructor' （根本没有参数列表的构造函数）中，如果您没有构造函数，则会生成该构造函数。
如果您已经写出长格式的记录构造函数（参数与您的组件完全匹配），那么什么也不会发生——您必须改为注释这个长格式构造函数的参数。


If a null-check is already present at the top, no additional null-check will be generated.


如果顶部已经存在空检查，则不会生成额外的空检查。


## Supported configuration keys: _支持的配置键：_


`lombok.nonNull.exceptionType =` [`NullPointerException` | `IllegalArgumentException` | `JDK` | `Guava` | `Assertion`] (default: `NullPointerException`).


`lombok.nonNull.exceptionType =` [`NullPointerException` | `IllegalArgumentException` | `JDK` | `Guava` | `Assertion`] (默认: `NullPointerException`).


When lombok generates a null-check `if` statement, by default, a `java.lang.NullPointerException` will be thrown with 'field name is marked non-null but is null' as the exception message. 
However, you can use `IllegalArgumentException` in this configuration key to have lombok throw that exception with this message instead. 
By using `Assertion`, an `assert` statement with the same message will be generated. 
The keys `JDK` or `Guava` result in an invocation to the standard nullcheck method of these two frameworks: 
`java.util.Objects.requireNonNull([field name here], "[field name here] is marked non-null but is null");` or `com.google.common.base.Preconditions.checkNotNull([field name here], "[field name here] is marked non-null but is null");` respectively.


当 lombok 生成 null-check `if` 语句时，默认情况下会抛出 `java.lang.NullPointerException`，并带有 'field name is marked non-null but is null' 作为异常消息。
但是，您可以在此配置键中使用 `IllegalArgumentException` 来让 lombok 使用此消息抛出该异常。
通过使用 `Assertion` ，将生成具有相同消息的 `assert` 语句。
键为 `JDK` 或 `Guava` 导致调用这两个框架的标准 nullcheck 方法：
分别 `java.util.Objects.requireNonNull([field name here], "[field name here] is marked non-null but is null");` or `com.google.common.base.Preconditions.checkNotNull([field name here], "[field name here] is marked non-null but is null");` 。


`lombok.nonNull.flagUsage =` [`warning` | `error`] (default: not set)


`lombok.nonNull.flagUsage =` [`warning` | `error`] (默认值：未设置)


Lombok will flag any usage of `@NonNull` as a warning or error if configured.


如果已配置，Lombok 会将任何使用 `@NonNull` 的情况标记为警告或错误。


## Small print


Lombok's detection scheme for already existing null-checks consists of scanning for `if` statements or `assert` statements that look just like lombok's own. 
Any 'throws' statement as the '`then`' part of the `if` statement, whether in braces or not, counts. 
Any invocation to any method named `requireNonNull` or `checkNotNull` counts. 
The conditional of the `if` statement must look exactly like `PARAMNAME == null`; the `assert` statement must look exactly like `PARAMNAME != null`. 
The invocation to a `requireNonNull`-style method must be on its own (a statement which just invokes that method), or must be the expression of an assignment or variable declaration statement. 
The first statement in your method that is not such a null-check stops the process of inspecting for null-checks.


Lombok 针对现有空值检查的检测方案包括扫描与 lombok 自己的类似的 `if` 语句或 `assert` 语句。
任何作为 `if` 语句的 '`then`' 部分的“throws”语句，无论是否在大括号中，都是重要的。
对任何名为 `requireNonNull` 或 `checkNotNull` 的方法的任何调用都会计数。
`if` 语句的条件必须看起来完全像 `PARAMNAME == null` ； `assert` 语句必须看起来完全像 `PARAMNAME != null` 。
对 `requireNonNull` 风格的方法的调用必须是独立的（一个只调用该方法的语句），或者必须是赋值语句或变量声明语句的表达式。
您的方法中第一个不是空检查的语句会停止检查空检查的过程。


While `@Data` and other method-generating lombok annotations will trigger on various well-known annotations that signify the field must never be `@NonNull`, this feature only triggers on lombok's own `@NonNull` annotation from the `lombok` package.


虽然 `@Data` 和其他方法生成的 lombok 注释将触发各种众所周知的注释，这些注释表明该字段永远不能是 `@NonNull` ，但此功能仅在来自 `lombok` 包的 lombok 自己的 `@NonNull` 注释上触发.


A `@NonNull` on a primitive parameter results in a warning. 
No null-check will be generated.


原始参数上的 `@NonNull` 会导致警告。
不会生成空检查。


A `@NonNull` on a parameter of an abstract method used to generate a warning; starting with version `1.16.8`, this is no longer the case, to acknowledge the notion that `@NonNull` also has a documentary role. 
For the same reason, you can annotate a method as `@NonNull`; this is allowed, generates no warning, and does not generate any code.


用于生成警告的抽象方法的参数上的`@NonNull`；从版本“1.16.8”开始，情况不再如此，以承认“@NonNull”也具有纪录片角色的概念。
出于同样的原因，您可以将方法注释为 `@NonNull` ；这是允许的，不会产生警告，也不会产生任何代码。

