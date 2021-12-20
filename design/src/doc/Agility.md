# Agility

* 敏捷(Agility)是指以微小增量的方式构建软件；

  * 如何去设计软件？
  * 如何去确保软件具有灵活性、可维护性以及可重用性的良好结构？
  * 如果以微小增量的方式构建软件，难道不是假借**重构**的旗号，实际上导致许多代码碎片和返工？
  * 难道不会忽略全局视图吗？

* 在敏捷团队中，全局视图和软件一起演化；

    在每次迭代中，团队改进系统设计，使设计尽可能适合于当前系统。

    > 关注当前的系统结构，并使它尽可能地好。

    * 不花费许多时间去预测未来的需求和需要。
    * 不会试图在今天构建一些基础结构去支撑认为明天才会需要的特性。

## 拙劣设计的症状

拙劣设计的症状如下：

1. 僵化性(Rigidity): 设计难以改变。
2. 脆弱性(Fragility): 设计易于遭到破坏。
3. 牢固性(Immobility): 设计难以重用。
4. 粘滞性(Viscosity): 难以做正确的事情。
5. 不必要的复杂性(Needless Complexity): 过分设计。
6. 不必要的重复(Needless Repetition): 滥用鼠标（指滥用鼠标进行拷贝、粘贴）。
7. 晦涩性(Opacity): 混乱的表达。

这些症状在本质上和代码的“臭味”(small)相似，但是它们所处的层次稍高一些。
他们是遍及整个软件结构的臭味，而不仅仅是一小段代码。

## 原则

面向对象的这些原则有助于开发人员消除设计中的**臭味**，并为当前的特性集构建出最好的设计。

* 单一职责原则(The Single Responsibility Principle, 简称 SRP )
* 开放 —— 封闭原则(The Open-Close Principle, 简称 OCP )
* Liskov 替换原则(The Liskov Substitution Principle, 简称 LSP )
* 依赖倒置原则(The Dependency Inversion Principle, 简称 DIP )
* 接口隔离原则(The Interface Segregation Interface, 简称 ISP )


