# Functional Programming

> **面向对象编程**是对**数据**的抽象；
> **函数式编程**是对**行为**的抽象；
>
> 实际程序中**数据**与**行为**并存；

函数式编程的核心是：在思考问题时，使用**不可变值**和**函数**，函数对一个值进行处理，**映射**成另一个值；

函数式编程优势：
* 提高可读性：更多的表达业务逻辑的意图，而不是实现机制；
* 易于编写回调函数和事件处理程序：在写回调函数和事件处理函数式，不必使用匿名内部类，函数式编程让事件处理系统变简单；
* 更好的支持惰性求值：能将函数方便地传递让编写惰性代码变得容易；


使用 `@FunctionalInterface`  进行注释。
此注释不是编译器将接口识别为功能接口的要求，而只是帮助捕获设计意图并寻求编译器的帮助来识别意外违反设计意图的行为。


中间操作和终端操作


## JDK 函数编程

JDK 中的函数功能接口遵循可扩展的命名约定：

* 有几种基本的函数形状，包括 `Function` (unary function from `T` to `R`), `Consumer` (unary function from `T` to `void`), `Predicate` (unary function from `T` to `boolean`), and `Supplier` (nullary function to R).
* 函数形状根据其最常用的方式具有自然的吸引力。基本形状可以通过 arity 前缀进行修改，以指示不同的 arity，例如 `BiFunction` (binary function from T and U to R)。
* 还有其他派生函数形状可以扩展基本函数形状，including `UnaryOperator` (extends `Function`) and `BinaryOperator` (extends `BiFunction`).
* 函数接口的类型参数可以专用于具有附加类型前缀的基元。
* 如果所有参数都有专用前缀，则可以省略 `arity` 前缀（如在 `ObjIntConsumer` 中）。

### `Stream` 操作

> Java 中对**整型(`IntStream`)**、**长整型(`LongStream`)**和**双浮点型(`DoubleStream`)**做了对基本类型和装箱类型的处理；提升系统性能：
> 
> 方法命名规范：
> 如果返回类型为基本类型，则在基本类型前加 `to`；(`ToLongFunction`)
> 如果参数是基本类型，则不加前缀只需要类型名即可；(`LongFunction`)

#### 中间操作(惰性求值)


* `map()` (高阶函数)将一种类型的值映射为另一种类型
* `filter()` 过滤器
* `flatMap()` 将多个流平展为一个流


#### 终端操作（及早求值）

* `collect()`
    * `toList()` 由 `Stream` 生成 `List` 集合
    * `toSet()` 由 `Stream` 生成 `Set` 集合
* `max()` 获取最大值
* `min()` 获取最小值
* `count()` 
* `reduce()` 从一组值中生成一个值
* `forEach()` 遍历所有元素

> `IntStream` `LongStream` `DoubleStream`:
> 
> * `summaryStatistics()` 返回流元素的各种摘要数据的描述

### 函数接口

* `Predicate<T>` 断言
* `Consumer<T>` 消费者
* `Supplier<T>` 生产者
* `Function<T, R>` 
  * `UnaryOperator<T>`
