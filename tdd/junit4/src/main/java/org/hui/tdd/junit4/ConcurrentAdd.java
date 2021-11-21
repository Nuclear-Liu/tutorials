package org.hui.tdd.junit4;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * @author Hui.Liu
 * @since 2021-11-21 18:27
 */
public class ConcurrentAdd {
    private int  sum = 0;

    public void addUnSafe(int a) {
        sum += a;
    }

    public void addSafe(int a) {
        synchronized (ConcurrentAdd.class) {
            sum += a;
        }
    }

    public int getSum() {
        return sum;
    }
}
