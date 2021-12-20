package org.hui.tdd.junit4;

import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.Timeout;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * @author Hui.Liu
 * @since 2021-11-16 10:11
 */
public class TimeoutTest {
    public static String log;
    private final CountDownLatch latch = new CountDownLatch(1);

    @Test(timeout = 1000)
    public void testTimeout() throws InterruptedException {
        TimeUnit.MILLISECONDS.sleep(100);
    }

    @Rule
    public Timeout globalTimeout = Timeout.seconds(10);

    @Ignore
    @Test
    public void testSleepForTooLong() throws Exception {
        log += "ran1";
        TimeUnit.SECONDS.sleep(100);
    }

    @Ignore
    @Test
    public void testBlockForver() throws Exception {
        log += "ran2";
        latch.await();
    }
}
