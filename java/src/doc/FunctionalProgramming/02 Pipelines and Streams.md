# Streams and Pipelines

## Streams _流_

Streams 用于支持对集合元素的函数是操作

* 普通对象元素集合流对象： `Stream`
* `int` 元素集合流对象： `IntStream`
* `long` 元素集合流对象： `LongStream`
* `double` 元素集合流对象： `DoubleStream`

Streams vs Collections:

* No storage _不存储_

    **流**不是存储元素的结构；相反，流通过计算操作的管道从数据结构、数组、生成器函数或 I/O channel 等源传送元素。

* Functional in nature _本质是函数_

    对**流**的操作产生新的结果，不会修改源。

* Laziness-seeking _懒计算_

    **流**操作分为**中间操作**（生产 `Stream` ）和**终端操作**（产生值或副作用）。
    **中间操作**总是懒计算。

* Possibly unbounded _（元素）可能无界_

    集合的元素可以是无限的。**短路操作**允许针对无限流的计算在有限时间内完成；

* Consumable _可消费_

    流的元素在一个流对象生命周期中只被访问一次。（所以在中间操作需要产生新的流供下一步操作消费）

JDK 提供的获取**流**的方式：

1. 从 `Collection` 使用 `stream()` / `parallelStream()` 方法；
2. 从数组使用 `Arrays.stream(Object[])` ；
3. 从**流**类上的静态工程方法（ `Stream.of(Object[])` `IntStream.range(int, int)` `Stream.iterate(Object, UnaryOperator)` ）；
4. 文件行可以从 `BufferedReader.lines()` 获取；
5. 文件路径刘可以从 `Files` 中的方法获取；
6. 从 `Random.ints()` 获得随机数流；
7. 其他流承载方法： `BitSet.stream()` `Pattern.splitAsStream(java.lang.CharSequence)` `JarFile.stream()`

## Pipelines _管道_

