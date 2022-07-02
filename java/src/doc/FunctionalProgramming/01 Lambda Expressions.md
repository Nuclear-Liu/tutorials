# Lambda Expressions

Java 8 引入 Lambda 表达式：一种紧凑的、传递行为的方式；

> 事件驱动模型中，事件处理代码传递了一个代表某种**行为**的对象，即**代码即数据**；

## Syntax of Lambda Expression

lambda 表达式由以下内容组成： `(parameter list) -> { Statement blocks }`
* 用逗号分隔的正式参数列表，括在括号中；

    > **注意**：
    > 
    > * 在 lambda 表达式中可以**省略参数数据类型**，参数类型不省略时，括号不可以省略；
    > * 如果只有一个参数，则可以**省略括号**；

    `CheckPerson.test` 方法包含一个参数 `p` ，它表示 `Person` 类的一个实例；

    `(Person p) -> p.getGender() == Person.Sex.MALE && p.getAge() >= 18 && p.getAge() <= 25`

    简约：

    `p -> p.getGender() == Person.Sex.MALE && p.getAge() >= 18 && p.getAge() <= 25`

* 箭头标记 `->` ；

* **单个表达式**或**语句块**组成的主体；

    * **单个表达式**主体
     
        * 如果指定单个表达式，那么 Java 运行时将计算该表达式，然后返回其值；
     
            `p -> p.getGender() == Person.Sex.MALE && p.getAge() >= 18 && p.getAge() <= 25`
     
        * 或者，如果需要返回值，使用 `return` 语句：
     
            `p -> { return p.getGender() == Person.Sex.MALE && p.getAge() >= 18 && p.getAge() <= 25; }`
            `email -> { System.out.println(email); }`

    * **语句块**主体，必须将所有语句括在大括号 (`{}`)；如果需要返回值使用 `return` 作为语句块的最后一个语句返回；

    > 与内部类特性相同，当语句块内引用所定义的方法里的变量（可以非 `final` 修饰）必须是**即成事实上**的 `final` (JDK 17 依然受限)。
    > 
    > 即 Lambda 表达式引用的是**值**，而不是**变量**(`final` 所修饰变量编译后，常量替换)；
    > 即 Lambda 也被成为**闭包**：未赋值的变量与周边环境隔离起来，进而被绑定到一个特定的值。

> **注意**：
> Lambda 表达式看上去像方法声明；可以将 Lambda 表达式视为匿名方法 —— 没有名称的方法；

> **类型推断**：
> 
> 即可以省略 Lambda 表达式中的所有**参数**，并且可以检查**返回值**类型时候匹配；实际上是 Java 7 引入的目标类型推断的扩展(即 `<>` 运算符)； 
> `javac` 根据 Lambda 表达式上下文信息推断出参数和返回值的正确类型。

##  Type Of `Lambda` : `FunctionInterface`

Lambda 表达式本身的类型： **函数接口**

> **函数接口**：只有**一个**抽象方法的接口；可以使用 `@FunctionalInterface` 修饰（非必需）。
> 无论接口声明中是否存在FunctionalInterface注释，编译器都会将满足功能接口定义的任何接口视为功能接口。
> 
> * 函数接口支持接口继承，继承规则与普通接口一致；如果一个函数接口继承另一个函数接口，则子函数接口必须使用 `default` 修饰重写实现父函数接口中的方法。
> * 函数接口中的单一方法的命名并不重要，只要**方法签名**和**返回值兼容**即可。

### Java 内置函数接口 [`java.util.function`](https://docs.oracle.com/en/java/javase/17/docs/api/java.base/java/util/function/package-summary.html)

> 泛型函数接口的在定义时不指定**类型**编译不通过；

#### `Function<T, R>` : `R apply(T t)`



* `Predicate<T>` : `boolean test(T t)` 
* `Consumer<T>` : `void accept(T t)`
* `Supplier<T>` : `T get()`
* `UnaryOperator<T> extends Function<T, T>` : 
* `BinaryOperator<T> extends BiFunction<T,T,T> ` : 

