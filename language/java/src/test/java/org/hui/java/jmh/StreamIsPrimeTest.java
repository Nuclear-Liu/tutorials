package org.hui.java.jmh;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Measurement;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.Warmup;

public class StreamIsPrimeTest {
    @Benchmark
    @Warmup(iterations = 1, time = 3)
    @BenchmarkMode(Mode.Throughput)
    @Measurement(iterations = 1, time = 3)
    public void testForEach() {
        StreamIsPrime.foreach();
    }
}