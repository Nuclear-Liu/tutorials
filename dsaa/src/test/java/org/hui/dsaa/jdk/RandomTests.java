package org.hui.dsaa.jdk;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.hui.dsaa.jdk.Random.xToXPower2;
import static org.hui.dsaa.jdk.Random.xToXPower3;

/**
 * @author Hui.Liu
 * @since 2022-04-02 14:16
 */
@Slf4j
public class RandomTests {

    @Test
    public void testP() {
        log.info("test start.");
        int testTimes = 10000000;
        int count = 0;
        for (int i = 0; i < testTimes; i++) {
            if (Math.random() < 0.75) {
                count++;
            }
        }
        log.info("p = {}", (double) count / (double) testTimes);
    }

    @Test
    public void test1() {
        log.info("test start.");
        int testTimes = 10000000;
        int count = 0;
        for (int i = 0; i < testTimes; i++) {
            if (Math.random() * 8 < 5) {
                count++;
            }
        }
        log.info("p = {}", (double) count / (double) testTimes);
        log.info("p = {}", 5 / (double) 8);
    }

    @Test
    public void test2() {
        int N = 9;
        int[] counts = new int[N];
        int testTimes = 10000000;
        for (int i = 0; i < testTimes; i++) {
            int ans = (int) (Math.random() * N);
            counts[ans]++;
        }
        log.info("counts: {}", Arrays.toString(counts));
    }

    /**
     * 对于任意 `x` ∈ `[0, 1)` 在 `[0, x]` 概率 `p = x^2`
     */
    @Test
    public void test3() {
        int count = 0;
        double x = 0.63;
        int testTimes = 10000000;
        for (int i = 0; i < testTimes; i++) {
            if (xToXPower2() < x) {
                count++;
            }
        }
        log.info("p = {}", count / (double) testTimes);
        log.info("p = {}", Math.pow(x, 2));
    }

    @Test
    public void test4() {
        int count = 0;
        double x = 0.64;
        int testTimes = 10000000;
        for (int i = 0; i < testTimes; i++) {
            if (xToXPower3() < x) {
                count++;
            }
        }
        log.info("p = {}", count / (double) testTimes);
        log.info("p = {}", Math.pow(x, 3));
    }
}
