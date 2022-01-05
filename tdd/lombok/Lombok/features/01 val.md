# `val`

## Overview


You can use `val` as the type of a local variable declaration instead of actually writing the type. 
When you do this, the type will be inferred from the initializer expression. 
The local variable will also be made `final`. 
This feature works on **local variables** and on **foreach loops** only, **not on fields**. 
The **initializer** expression is required.


您可以使用 `val` 作为局部变量声明的类型，而不是实际编写类型。
执行此操作时，将从初始值设定项表达式推断类型。
局部变量也将成为 `final` 。
此功能仅适用于**局部变量**和 **foreach 循环**，**不适用于字段**。
**初始化**表达式是必需的。


`val` is actually a 'type' of sorts, and exists as a real class in the `lombok` package. 
You must import it for `val` to work (or use `lombok.val` as the type). 
The existence of this type on a local variable declaration triggers both the adding of the `final` keyword as well as copying the type of the initializing expression which overwrites the 'fake' `val` type.


`val` 实际上是一种“类型”，并且作为一个真正的类存在于 `lombok` 包中。
您必须导入它才能让 `val` 工作（或使用 `lombok.val` 作为类型）。
局部变量声明中存在此类型会触发添加 `final` 关键字以及复制覆盖 “假” `val` 类型的初始化表达式的类型。


_WARNING: This feature does not currently work in NetBeans._


_警告：此功能当前在 NetBeans 中不起作用。_


## Supported configuration keys:


`lombok.val.flagUsage =` [`warning` | `error`] (default: not set)


`lombok.val.flagUsage =` [`warning` | `error`] (默认值：未设置)


Lombok will flag any usage of `val` as a warning or error if configured.


如果已配置，Lombok 会将任何使用 `val` 的情况标记为警告或错误。


## Small print


For compound types, the most common superclass is inferred, not any shared interfaces. 
For example, `bool ? new HashSet() : new ArrayList()` is an expression with a compound type: 
The result is both `AbstractCollection` as well as `Serializable`. 
The type inferred will be `AbstractCollection`, as that is a class, whereas `Serializable` is an interface.


对于复合类型，最常见的超类是推断的，而不是任何共享接口。
例如， `bool ? new HashSet() : new ArrayList()` 是一个复合类型的表达式：
结果是 `AbstractCollection` 和 `Serializable` 。
推断的类型将是 `AbstractCollection` ，因为它是一个类，而 `Serializable` 是一个接口。


In ambiguous cases, such as when the initializer expression is null, `java.lang.Object` is inferred.


在不明确的情况下，例如初始化表达式为 null 时，会推断出 `java.lang.Object`。
