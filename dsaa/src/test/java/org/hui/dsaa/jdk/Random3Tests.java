package org.hui.dsaa.jdk;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import static org.hui.dsaa.jdk.Random3.to01;

/**
 * @author Hui.Liu
 * @since 2022-04-02 16:21
 */
@Slf4j
public class Random3Tests {

    @Test
    public void testTo01() {
        int count = 0;
        int testTimes = 10000000;
        for (int i = 0; i < testTimes; i++) {
            if (to01() == 0) {
                count ++;
            }
        }
        log.info("p = {}", count / (double) testTimes);
    }
}
