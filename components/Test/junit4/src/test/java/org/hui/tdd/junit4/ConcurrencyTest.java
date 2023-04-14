package org.hui.tdd.junit4;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static org.hui.tdd.junit4.Assert.assertConcurrent;

/**
 * @author Hui.Liu
 * @since 2021-11-21 18:26
 */
public class ConcurrencyTest {

    private List<Runnable> runnables = new ArrayList<>();
    private ConcurrentAdd add = null;

    @Before
    public void init() {
        add = new ConcurrentAdd();
    }
    @Test
    public void testAssertConcurrent() throws InterruptedException {
        for (int i = 0; i < 100; i++) {
            runnables.add(new Runnable() {
                @Override
                public void run() {
                    add.addUnSafe(10);
                    try {
                        Thread.sleep(10);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            });
        }
        assertConcurrent("safe", runnables, 10);
        System.out.println(add.getSum());
    }
}
