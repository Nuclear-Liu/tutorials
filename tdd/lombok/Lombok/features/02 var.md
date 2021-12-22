# `var`

## Overview


`var` works exactly like `val`, except the local variable is not marked as `final`.


`var` 的工作方式与 `val` 完全一样，只是局部变量没有标记为 `final`。


The type is still entirely derived from the mandatory initializer expression, and any further assignments, while now legal (because the variable is no longer final), aren't looked at to determine the appropriate type.


类型仍然完全从强制初始化表达式派生而来，任何进一步的赋值，虽然现在是合法的（因为变量不再是最终的），但不会查看以确定适当的类型。

For example, `var x = "Hello"; x = Color.RED;` does not work; the type of x will be inferred to be `java.lang.String` and thus, the `x = Color.RED` assignment will fail. 
If the type of `x` was inferred to be `java.lang.Object` this code would have compiled, but that's not how `var` works.


例如， `var x = "Hello"; x = Color.RED;` 不起作用； `x` 的类型将被推断为`java.lang.String`，因此`x = Color.RED` 赋值将失败。
如果 `x` 的类型被推断为 `java.lang.Object` ，这段代码就会被编译，但这不是 `var` 的工作方式。


## Supported configuration keys:


`lombok.var.flagUsage =` [`warning` | `error`] (default: not set)


`lombok.var.flagUsage =` [`warning` | `error`] (默认：未设置)


Lombok will flag any usage of `var` as a warning or error if configured.


如果已配置，Lombok 会将任何对 `var` 的使用标记为警告或错误。
