package org.hui.jmh;

import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.results.format.ResultFormatType;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import java.util.Collections;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentSkipListMap;
import java.util.concurrent.TimeUnit;

@BenchmarkMode(Mode.AverageTime)
@Fork(1)
@Warmup(iterations = 2)
@Measurement(iterations = 2)
@OutputTimeUnit(TimeUnit.MICROSECONDS)
@Threads(5)
@State(Scope.Benchmark)
public class JMHExample10 {
    @Param({"1", "2", "3", "4"})
    private int type;
    private Map<Long, Long> map;
    @Setup
    public void setUp() {
        switch (type) {
            case 1 -> map = new ConcurrentHashMap<>();
            case 2 -> map = new ConcurrentSkipListMap<>();
            case 3 -> map = new Hashtable<>();
            case 4 -> map = Collections.synchronizedMap(new HashMap<>());
            default -> throw new IllegalArgumentException("Illegal map type.");
        }
    }
    @Benchmark
    public void test() {
        map.put(System.nanoTime(), System.nanoTime());
    }
    public static void main(String[] args) throws RunnerException {
        final Options opts = new OptionsBuilder()
                .include(JMHExample10.class.getSimpleName())
                .result("JMHExample10.json")
                .resultFormat(ResultFormatType.JSON)
                .build();
        new Runner(opts).run();
    }
}
