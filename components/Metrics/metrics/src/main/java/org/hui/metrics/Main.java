package org.hui.metrics;

import com.codahale.metrics.ConsoleReporter;
import com.codahale.metrics.Meter;
import com.codahale.metrics.MetricRegistry;

import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

import static com.codahale.metrics.MetricRegistry.name;

public class Main {
    private static final MetricRegistry REGISTRY=  new MetricRegistry();
    private static final Meter REQUEST_METER =REGISTRY.meter("tps");
    private static final Meter SIZE_METER = REGISTRY.meter("volums");
    public static void main(String[] args) {
        ConsoleReporter reporter = ConsoleReporter.forRegistry(REGISTRY)
                .convertRatesTo(TimeUnit.MINUTES)
                .convertDurationsTo(TimeUnit.MINUTES)
                .build();
        reporter.start(10,TimeUnit.SECONDS);


        for (;;) {
            handleRequest(new byte[ThreadLocalRandom.current().nextInt(1_000)]);
            randomSleep();
        }
    }

    private static void handleRequest(byte[] request) {
        REQUEST_METER.mark();
        SIZE_METER.mark(request.length);
        randomSleep();
    }
    private static void randomSleep() {
        try {
            TimeUnit.SECONDS.sleep(ThreadLocalRandom.current().nextInt(10));
        } catch (InterruptedException e) {
            // no do anything.
        }
    }
}