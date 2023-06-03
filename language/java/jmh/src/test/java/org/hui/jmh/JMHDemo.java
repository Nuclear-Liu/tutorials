package org.hui.jmh;

import org.junit.jupiter.api.Test;
import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.results.format.ResultFormatType;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import java.util.concurrent.TimeUnit;

@State(Scope.Thread)
public class JMHDemo {

    @BenchmarkMode(Mode.AverageTime)
    @Benchmark
    @Warmup(iterations = 1)
    @Measurement(iterations = 2)
    public void testAverageTime() throws InterruptedException {
        TimeUnit.MILLISECONDS.sleep(1);
    }

    /*@BenchmarkMode(Mode.Throughput)
    @Benchmark
    @Warmup(iterations = 1)
    @Measurement(iterations = 2)
    public void testThroughput() throws InterruptedException {
        TimeUnit.MILLISECONDS.sleep(1);
    }

    @BenchmarkMode(Mode.SampleTime)
    @Benchmark
    @Warmup(iterations = 1)
    @Measurement(iterations = 2)
    public void testSampleTime() throws InterruptedException {
        TimeUnit.MILLISECONDS.sleep(1);
    }

    @BenchmarkMode(Mode.SingleShotTime)
    @Benchmark
    @Warmup(iterations = 0)
    public void testSingleShotTime() throws InterruptedException {
        TimeUnit.MILLISECONDS.sleep(1);
    }

    @BenchmarkMode(Mode.All)
    @Benchmark
    public void testAll() throws InterruptedException {
        TimeUnit.MILLISECONDS.sleep(1);
    }*/

    @Test
    public void test() throws RunnerException {
        final Options opts = new OptionsBuilder()
                .include(JMHDemo.class.getSimpleName())
                .timeUnit(TimeUnit.MILLISECONDS)
                .result("JHMDemo.json")
                .resultFormat(ResultFormatType.JSON)
                .build();
        new Runner(opts).run();
    }

}
