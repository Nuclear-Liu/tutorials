package org.hui.jmh;

import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.results.format.ResultFormatType;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import java.util.concurrent.TimeUnit;

@BenchmarkMode(Mode.AverageTime)
@Fork(2)
@Warmup(iterations = 0)
@Measurement(iterations = 1)
@OutputTimeUnit(TimeUnit.MICROSECONDS)
@State(Scope.Thread)
public class JMHExample17 {
    interface Inc {
        int inc();
    }
    public static class Inc1 implements Inc {
        private int i =0;
        @Override
        public int inc() {
            return ++i;
        }
    }
    public static class Inc2 implements Inc {
        private int i =0;
        @Override
        public int inc() {
            return ++i;
        }
    }
    private Inc inc1 = new Inc1();
    private Inc inc2 = new Inc2();
    private int measure(Inc inc) {
        int result = 0;
        for (int i = 0; i < 10; i++) {
            result += inc.inc();
        }
        return result;
    }
    @Benchmark
    public int measure_inc_1() {

        System.out.println("1:"+Thread.currentThread().getName());
        return measure(inc1);
    }
    @Benchmark
    public int measure_inc_2() {
        System.out.println("2"+Thread.currentThread().getName());
        return measure(inc2);
    }
    @Benchmark
    public int measure_inc_3() {
        System.out.println("3"+Thread.currentThread().getName());
        return measure(inc1);
    }

    public static void main(String[] args) throws RunnerException {
        final Options opts = new OptionsBuilder()
                .include(JMHExample17.class.getSimpleName())
                .result("JMHExample17.json")
                .resultFormat(ResultFormatType.JSON)
                .build();

        System.out.println("Main:" + Thread.currentThread().getName());
        new Runner(opts).run();
    }
}
