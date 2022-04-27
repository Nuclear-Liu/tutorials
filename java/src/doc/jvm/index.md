# [JVM Java Virtual Machine](https://docs.oracle.com/javase/specs/index.html)

JVM 跨语言平台；与语言无关；

* `.java` 通过 `javac` 编译成 `.class` 文件（字节码文件）；
* `.class` 文件提交到 JVM 解释执行；
    * 通过 `ClassLoader` 加载字节码文件；
    * 通过**字节码解释器**（解释过程）或 **`JIT`** （**即时编译器**，编译过程）解释；
    * 执行引擎通过调用 OS 硬件执行；

JVM 是一种规范；虚构出来的一台计算机（独立的**字节码指令集**、**内存管理**）；

## JVM 的实现：

* Hotspot

    Oracle 官方；

* Jrockit

    BEA 公司，后来被 Oracle 收购，合并于 Hotspot 。

* J9

    IBM 实现；

* Microsoft VM

    微软实现；

* TaobaoVM

    淘宝基于 Hotspot 深度定制版；

* LiquidVM

    直接面向硬件的实现；

* azul zing

    最先的垃圾回收行业标杆； [www.azul.com](https://www.azul.com/) ；


## JDK JRE JVM

JDK = JRE + DevelopmentKit;

JRE = JVM + CoreLib;
