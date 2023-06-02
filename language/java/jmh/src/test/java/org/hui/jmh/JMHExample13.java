package org.hui.jmh;

import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.results.format.ResultFormatType;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import javax.swing.*;
import java.util.concurrent.TimeUnit;

import static java.lang.Math.PI;

@BenchmarkMode(Mode.AverageTime)
@Fork(1)
@Warmup(iterations = 5)
@Measurement(iterations = 5)
@OutputTimeUnit(TimeUnit.MICROSECONDS)
@State(Scope.Thread)
public class JMHExample13 {
    @Benchmark
    @CompilerControl(CompilerControl.Mode.EXCLUDE)
    public void baseline() {
        // do nothing.
    }
    @Benchmark
    @CompilerControl(CompilerControl.Mode.EXCLUDE)
    public void measureLog1() {
        // 进行数学运算，但是在局部方法内
        Math.log(PI);
    }
    @Benchmark
    @CompilerControl(CompilerControl.Mode.EXCLUDE)
    public void measureLog2() {
        // result 是通过数学运算所得并且在下一行代码中得到了使用
        double result = Math.log(PI);
        // 对 result 进行数学运算，但是结果既不保存也不返回，更不会进行二次运算
        Math.log(result);
    }
    @Benchmark
    @CompilerControl(CompilerControl.Mode.EXCLUDE)
    public double measureLog3() {
        // 返回数学运算结果
        return Math.log(PI);
    }
    public static void main(String[] args) throws RunnerException {
        final Options opt = new OptionsBuilder()
                .include(JMHExample13.class.getSimpleName())
                .result("JMHExample13.json")
                .resultFormat(ResultFormatType.JSON)
                .build();
        new Runner(opt).run();
    }
}
