package org.hui.jmh;

import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.results.format.ResultFormatType;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import java.util.concurrent.TimeUnit;

import static java.lang.Math.log;

@BenchmarkMode(Mode.AverageTime)
@Fork(1)
@Warmup(iterations = 5)
@Measurement(iterations = 5)
@OutputTimeUnit(TimeUnit.NANOSECONDS)
@State(Scope.Thread)
public class JMHExample15 {
    // x1 和 x2 是使用 final 修饰的常量
    private final double x1 = 123.456;
    private final double x2 = 342.456;
    // y1 和 y2 则是普通的成员变量
    private double y1 = 123.456;
    private double y2 = 342.456;

    // 直接返回 123.456*342.456 的计算结果，主要用它来作基准
    @Benchmark
    public double returnDirect() {
        return 123.456 * 342.456;
    }

    // 两个常量相乘，
    @Benchmark
    public double returnCaculate_1() {
        return x1 * x2;
    }

    // 两个变量相乘
    @Benchmark
    public double returnCaculate_2() {
        return y1 * y2;
    }
    @Benchmark
    public double returnCalculate_3() {
        return log(x1)*log(x2);
    }

    public static void main(String[] args) throws RunnerException {
        final Options opts = new OptionsBuilder()
                .include(JMHExample15.class.getSimpleName())
                .result("JMHExample15.json")
                .resultFormat(ResultFormatType.JSON)
                .build();
        new Runner(opts).run();
    }
}
