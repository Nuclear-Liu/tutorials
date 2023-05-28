package org.hui.jmh;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.Test;
import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.TimeUnit;

@BenchmarkMode(Mode.Throughput)
//@OutputTimeUnit(TimeUnit.MICROSECONDS)
@State(Scope.Thread)
@Warmup(iterations = 2)
@Measurement(iterations = 3)
public class JMHExample03 {

    private static final Logger LOGGER = LogManager.getLogger();

    private static final String DATA = "DUMMY DATA";
    private List<String> arrayList;
    private List<String> linkedList;

    @Setup(Level.Iteration)
    public void setUp() {
        arrayList = new ArrayList<>();
        linkedList = new LinkedList<>();
    }

    @Benchmark
    public List<String> arrayListAdd() {
        arrayList.add(DATA);
        return arrayList;
    }

    @Benchmark
    public List<String> linkedListAdd() {
        linkedList.add(DATA);
        return linkedList;
    }

    @Test
    public void test() throws RunnerException {
        final Options opts = new OptionsBuilder()
                .include(JMHExample01.class.getSimpleName())
                .forks(1)
                // .measurementIterations(10)
                // .warmupIterations(10)
                .build();
        new Runner(opts).run();
    }
}
