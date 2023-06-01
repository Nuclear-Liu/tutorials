package org.hui.jmh;

import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.results.format.ResultFormatType;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

@BenchmarkMode(Mode.AverageTime)
@Fork(1)
@Warmup(iterations = 2)
@Measurement(iterations = 2)
@OutputTimeUnit(TimeUnit.MICROSECONDS)
@Threads(5)
@State(Scope.Benchmark)
public class JMHExample09 {
    private Map<Long, Long> concurrentMap;
    private Map<Long, Long> synchronizedMap;

    @Setup
    public void setUp() {
        concurrentMap = new ConcurrentHashMap<>();
        synchronizedMap = Collections.synchronizedMap(new HashMap<>());
    }

    @Benchmark
    public void testConcurrencyMap() {
        concurrentMap.put(System.nanoTime(), System.nanoTime());
    }
    @Benchmark
    public void testSynchronizedMap() {
        synchronizedMap.put(System.nanoTime(), System.nanoTime());
    }

    public static void main(String[] args) throws RunnerException {
        final Options opts = new OptionsBuilder()
                .include(JMHExample09.class.getSimpleName())
                .result("JMHExample09.json")
                .resultFormat(ResultFormatType.JSON)
                .build();
        new Runner(opts).run();
    }
}
