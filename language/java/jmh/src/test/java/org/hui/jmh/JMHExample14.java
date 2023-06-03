package org.hui.jmh;

import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.infra.Blackhole;
import org.openjdk.jmh.results.format.ResultFormatType;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import java.util.concurrent.TimeUnit;

@BenchmarkMode(Mode.AverageTime)
@Fork(1)
@Warmup(iterations = 5)
@Measurement(iterations = 5)
@OutputTimeUnit(TimeUnit.NANOSECONDS)
@State(Scope.Thread)
public class JMHExample14 {
    double x1 = Math.PI;
    double x2 = Math.PI * 2;

    @Benchmark
    public double baseline() {
        // 不是 Dead Code ，因为对结果进行了返回
        return Math.pow(x1, 2);
    }

    @Benchmark
    public double powButReturnOne() {
        // Dead Code 会被擦除
        Math.pow(x1, 2);
        // 不会被擦除，因为对结果进行了返回
        return Math.pow(x2, 2);
    }

    @Benchmark
    public double powThenAdd() {
        // 通过加法运算对两个结果进行了合并，因此两次的计算都会生效
        return Math.pow(x1, 2) + Math.pow(x2, 2);
    }

    @Benchmark
    public void useBlackhole(Blackhole blackhole) {
        // 将结果存放至 block hole 中，因此两次 pow 操作都会生效
        blackhole.consume(Math.pow(x1, 2));
        blackhole.consume(Math.pow(x2, 2));
    }

    public static void main(String[] args) throws RunnerException {
        final Options opts = new OptionsBuilder()
                .include(JMHExample14.class.getSimpleName())
                .result("JMHExample14.json")
                .resultFormat(ResultFormatType.JSON)
                .build();
        new Runner(opts).run();
    }
}
