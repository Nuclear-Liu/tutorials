# [JMH _Java Microbenchmark Harness_](https://openjdk.java.net/projects/code-tools/jmh/)

## JMH 依赖

```text
<dependency>
    <groupId>org.openjdk.jmh</groupId>
    <artifactId>jmh-core</artifactId>
    <version>1.35</version>
</dependency>
<dependency>
    <groupId>org.openjdk.jmh</groupId>
    <artifactId>jmh-generator-annprocess</artifactId>
    <version>1.35</version>
    <scope>test</scope>
</dependency>
```

## 运行配置


* `@Benchmark` 定义一个基准测试
* `@Warmup` 预热
    * `iterations` 预热次数
    * `time` 每次预热等地时间
* `@Fork` 使用多少个线程运行测试
* `@BenchmarkMode` 基准测试的模式
    * `Mode.Throughput` 吞吐量
* `@Measurement` 基准测试的次数
    * `iterations` 调用次数
    * `time` 每次调用的间隔时间

## 官方代码案例

[JMH Samples](https://github.com/openjdk/jmh/tree/master/jmh-samples/src/main/java/org/openjdk/jmh/samples)
