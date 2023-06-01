# Java Microbenchmark Harness(JMH)

[JMH Demo](https://hg.openjdk.org/code-tools/jmh/file/tip/jmh-samples/src/main/java/org/openjdk/jmh/samples/)

[JMH 结果可视化工具](https://jmh.morethan.io/)

## Benchmark

* `@Benchmark`: 标记基准测试方法 [`ElementType.METHOD`]

JMH 对基准测试的方法使用 `@Benchmark` ([JMHExample01](./src/test/java/org/hui/jmh/JMHExample01.java))注解进行标记，否则方法将被视作普通方法，并不会对其执行基准测试。

对一个类如果没有任何基准测试方法(被 `@Benchmark` 标记的方法)，进行基准测试会出现异常([JMHExample02](./src/test/java/org/hui/jmh/JMHExample02.java))。

通常每个 `@Benchmark` 注解的方法都运行在独立的进程中，互不干涉。

## Warmup and Measurement

1. 全局 `Options` 接口配置，拥有最高优先级: `ChainedOptionsBuilder warmupIterations(int value)` `ChainedOptionsBuilder measurementIterations(int count)` 

2. 注解方式
   * `@Warmup`: 代码预热: [`ElementType.METHOD,ElementType.TYPE`]
    
     在基准测试代码正式度量之前进行预热，使得代码经理类的早期优化、JVM 运行期编译、JIT 优化之后的状态，从而能够获得代码真实的性能数据。

     * `iterations` 预热的次数
     * `time` 每次预热的时间
     * `timeUnit` 时间单位(默认`s`)
     * `batchSize` 批处理大小，每次操作调用几次方法

   * `@Measurement`: 代码度量: [`ElementType.METHOD,ElementType.TYPE`]

     与 `ChainedOptionsBuilder measurementIterations(int count)` 功能相同

     * `iterations` 预热的次数
     * `time` 每次预热的时间
     * `timeUnit` 时间单位(默认`s`)
     * `batchSize` 批处理大小，每次操作调用几次方法

     预热数据不会在纳入统计之中，只有度量数据纳入统计之中。

> 优先级： `Options` 配置 > 方法注解 > 类注解

## 测试模式

### 注解模式 `@BenchmarkMode`

`@BenchmarkMode`(`ElementType.METHOD, ElementType.TYPE`) 声明使用的测试模式：

* `Mode.AverageTime` 平均响应时间 主要用于输出基准测试方法调用灭一次所耗费的时间 elapsed time/operation
* `Mode.Throughput` 方法吞吐量 主要用于输出基准测试方法在单位时间内可以对该方法调用多少次
* `Mode.SampleTime` 时间采样 主要用于输出基准测试方法执行时间区间分布
* `Mode.SingleShotTime` 单次操作时间 主要用来进行冷测试，不论是 `Warmup` 还是 `Measurement` ，在每一个批次中基准测试方法只会被执行一次，一般情况下将 `Warmup` 设置为 `0`
* `Mode.All` 对如上所有模式进行测试

### 全局配置

`Options` 接口配置方式，拥有最高优先级： `ChainedOptionsBuilder mode(Mode mode)`

## 输出单位

* `@OutputTimeUnit()` 注解方式
* `ChainedOptionsBuilder timeUnit(TimeUnit tu)` 使用 `Options` 配置

## `State`

定义对象在工作线程之间共享的程度

* `Scope.Thread`: 线程独享

    每一个运行基准方法的线程都会持有一个独立的对象实例，该实例既可能是作为基准测试方法参数传入的，也可能是运行基准测试方法所在的宿主 class 。

    `State` 设置为 `Scope.Thread` 主要是针对非线程安全类。

* `Scope.Thread`<**默认**> 每个线程分配一个独立的对象实例：主要针对非线程安全的类 
* `Scope.Benchmark`  所有测试线程共享一个实例，测试有状态实例在多线程共享下的性能，测试在多线程的情况下，某个类被不同线程操作时的性能
* `Scope.Group` 作用域为 Group ，同一个线程在同一个 Group 里共享实例

## Param

`@Param` 注解使的参数可配置，即每一次的基准测试时都有不同的值与之对应。

## JMH 测试套件 Fixture

* `@Setup` 基准测试之前调用
* `@TearDown` 基准测试之后调用
