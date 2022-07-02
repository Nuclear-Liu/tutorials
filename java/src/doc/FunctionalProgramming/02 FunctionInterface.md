# `FunctionInterface` _函数接口_

`FunctionInterface` 为 lambda 表达式和方法引用提供目标类型。
每个函数接口都有**一个抽象方法**，称为给函数接口的**功能方法**， lambda 表达式的**参数**和**返回类型**与之匹配或适应。

函数接口通常表示抽象概念，如**函数**、**动作**或**谓词**。

> 泛型函数接口的在定义时不指定**类型**编译不通过；

> **函数接口**：只有**一个**抽象方法的接口；可以使用 `@FunctionalInterface` 修饰（非必需）。
> 无论接口声明中是否存在FunctionalInterface注释，编译器都会将满足功能接口定义的任何接口视为功能接口。
>
> * 函数接口支持接口继承，继承规则与普通接口一致；如果一个函数接口继承另一个函数接口，则子函数接口必须使用 `default` 修饰重写实现父函数接口中的方法。
> * 函数接口中的单一方法的命名并不重要，只要**方法签名**和**返回值兼容**即可。

## `@FunctionalInterface`

接口使用 `@FunctionInterface` 注解修饰接口函数，不是编译器将接口识别未函数接口的要求，只是帮助捕获设计意图并在识别意外违反设计意图时获得编译器的帮助。

## [`java.util.function`](https://docs.oracle.com/en/java/javase/17/docs/api/java.base/java/util/function/package-summary.html)

JDK 通用功能接口。

`java.util.function` 包中的函数接口遵循可扩展命名约定：
* 基本函数接口； 
    * `Function<T, R>` (从 `T` 到 `R` 的一元函数)
    * `Consumer<T>` (从 `T` 到 `void` 的一元函数)
    * `Predicate<T>` (从 `T` 到 `boolean` 的一元函数)
    * `Supplier<T>` ( `R` 的零函数)
* Function shapes have a natural arity based on how they are most commonly used.
    * `BiFunction<T, U, R>` (从 `T` 与 `U` 到 `R` 的二元函数)
* There are additional derived function shapes which extend the basic function shapes, including UnaryOperator (extends Function) and BinaryOperator (extends BiFunction).
    * `UnaryOperator<T> extends Function<T, T>` (继承自 `Function` )
    * `BinaryOperator<T> extends BiFunction<T,T,T>` (继承自 `BiFunction`)
* Type parameters of functional interfaces can be specialized to primitives with additional type prefixes. To specialize the return type for a type that has both generic return type and generic arguments, we prefix ToXxx, as in ToIntFunction. Otherwise, type arguments are specialized left-to-right, as in DoubleConsumer or ObjIntConsumer. (The type prefix Obj is used to indicate that we don't want to specialize this parameter, but want to move on to the next parameter, as in ObjIntConsumer.) These schemes can be combined, as in IntToDoubleFunction.
    * `ToIntFunction<T>`
    * `DoubleConsumer`
    * `ObjIntConsumer<T>`
    * `IntToDoubleFunction`
* If there are specialization prefixes for all arguments, the arity prefix may be left out (as in ObjIntConsumer).
    * `ObjIntConsumer<T>`

#### `Function<T, R>` : `R apply(T t)`



* `Predicate<T>` : `boolean test(T t)`
* `Consumer<T>` : `void accept(T t)`
* `Supplier<T>` : `T get()`
* `UnaryOperator<T> extends Function<T, T>` :
* `BinaryOperator<T> extends BiFunction<T,T,T> ` : 

