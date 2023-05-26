# Java Microbenchmark Harness(JMH)


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

   * `@Measurement`: 代码度量: [`ElementType.METHOD,ElementType.TYPE`]

     与 `ChainedOptionsBuilder measurementIterations(int count)` 功能相同
      
     预热数据不会在纳入统计之中，只有度量数据纳入统计之中。

> 优先级： `Options` 配置 > 方法注解 > 类注解

## BenchmarkMode

`@BenchmarkMode` 声明使用的测试模式：

* `Mode.AverageTime` 平均响应时间

