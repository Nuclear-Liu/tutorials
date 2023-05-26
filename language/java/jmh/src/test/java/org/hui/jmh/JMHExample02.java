package org.hui.jmh;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.spi.LoggerContextFactory;
import org.junit.jupiter.api.Test;
import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * This class not contains any method that be annotated by @Benchmark
 */
@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.MICROSECONDS)
@State(Scope.Thread)
public class JMHExample02 {
    private static final Logger LOGGER = LogManager.getLogger();
    /**
     * normal instance method.
     */
    public void normalMethod() {
    }

    @Test
    public void test() throws RunnerException {
        RunnerException exception = assertThrows(RunnerException.class,
                () -> {
                    final Options opts = new OptionsBuilder()
                            .include(JMHExample02.class.getSimpleName())
                            .forks(1)
                            .measurementIterations(10)
                            .warmupIterations(10)
                            .build();
                    new Runner(opts).run();
                });
        LOGGER.error(exception);
    }
}
