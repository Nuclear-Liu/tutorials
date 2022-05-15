# Lambda Expressions

Java 8 引入 Lambda 表达式：一种紧凑的、传递行为的方式；

> 事件驱动模型中，事件处理代码传递了一个代表某种**行为**的对象，即**代码即数据**；

## Syntax of Lambda Expression

lambda 表达式由以下内容组成：
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

> **注意**：
> Lambda 表达式看上去像方法声明；可以将 Lambda 表达式视为匿名方法 —— 没有名称的方法；


> * Lambda 表达式**参数类型**可以由编译器推断得出；不过最好根据**实际情况**决定是否明确写明参数类型；
> * Lambda **表达式类型**（也称为**目标类型**）依赖于表达式上下文环境，由编译器推断出来；