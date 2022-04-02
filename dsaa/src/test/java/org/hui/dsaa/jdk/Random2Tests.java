package org.hui.dsaa.jdk;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.hui.dsaa.jdk.Random2.to01;
import static org.hui.dsaa.jdk.Random2.to06;
import static org.hui.dsaa.jdk.Random2.to07;
import static org.hui.dsaa.jdk.Random2.to17;

/**
 * @author Hui.Liu
 * @since 2022-04-02 15:46
 */
@Slf4j
public class Random2Tests {
    @Test
    public void testTo01() {
        int count = 0;
        int testTimes = 10000000;
        for (int i = 0; i < testTimes; i++) {
            if (to01() == 0) {
                count++;
            }
        }
        log.info("p = {}", count / (double) testTimes);
    }

    @Test
    public void testTo07() {
        int testTimes = 10000000;
        int[] counts = new int[8];
        for (int i = 0; i < testTimes; i++) {
            counts[to07()]++;
        }
        log.info(Arrays.toString(counts));
    }

    @Test
    public void testTo06() {
        int testTimes = 10000000;
        int[] counts = new int[8];
        for (int i = 0; i < testTimes; i++) {
            counts[to06()]++;
        }
        log.info(Arrays.toString(counts));
    }

    @Test
    public void testTo17() {
        int testTimes = 10000000;
        int[] counts = new int[8];
        for (int i = 0; i < testTimes; i++) {
            counts[to17()]++;
        }
        log.info(Arrays.toString(counts));
    }
}
