package org.hui.jmh;

import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.results.format.ResultFormatType;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

@BenchmarkMode(Mode.AverageTime)
@Fork(1)
@Warmup(iterations = 5)
@Measurement(iterations = 5)
@OutputTimeUnit(TimeUnit.MICROSECONDS)
@State(Scope.Thread)
public class JMHExample11 {
    private List<String> list;
    // @Setup(Level.Trial)
    @Setup(Level.Iteration)
    // @Setup(Level.Invocation)
    public void setUp() {
        list = new ArrayList<>();
        System.out.println("init.");
    }
    @Benchmark
    public void measureRight() {
        list.add("Test");
    }

    @Benchmark
    public void measureWrong() {
        // do nothing.
    }
    // @TearDown(Level.Trial)
    @TearDown(Level.Iteration)
    // @TearDown(Level.Invocation)
    public void tearDown() {
        assert list.size() > 0 : "The list elements must greater than zero";
        System.out.println("destroy.");
    }
    public static void main(String[] args) throws RunnerException {
        final Options opts = new OptionsBuilder()
                .include(JMHExample11.class.getSimpleName())
                .result("JMHExample11.json")
                .resultFormat(ResultFormatType.JSON)
                .build();
        new Runner(opts).run();
    }
}
