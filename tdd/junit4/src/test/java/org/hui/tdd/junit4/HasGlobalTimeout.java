package org.hui.tdd.junit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestRule;
import org.junit.rules.Timeout;

/**
 * @author Hui.Liu
 * @since 2021-11-19 0:07
 */
public class HasGlobalTimeout {
    public static String log;

    @Rule
    public final TestRule globalTimeout = Timeout.seconds(3);

    @Test
    public void testInfiniteLoop1() {
        log += "ran1";
        for (; ; ) {
        }
    }

    @Test
    public void testInfiniteLoop2() {
        log += "ran2";
        for (; ; ) {
        }
    }
}
