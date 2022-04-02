package org.hui.dsaa.simplesort;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.hui.dsaa.simplesort.Sort.insertSort1;
import static org.hui.dsaa.simplesort.Sort.insertSort2;

/**
 * @author Hui.Liu
 * @since 2022-04-02 11:19
 */
@Slf4j
public class InsertSortTests {
    @Test public void testSort1() {
        int[] nums = {3, 1, 2, 7, 9, 6, 5};
        log.info("unordered array: {}", Arrays.toString(nums));
        insertSort1(nums);
        log.info("ordered   array: {}", Arrays.toString(nums));
    }
    @Test public void testSort2() {
        int[] nums = {3, 1, 2, 7, 9, 6, 5};
        log.info("unordered array: {}", Arrays.toString(nums));
        insertSort2(nums);
        log.info("ordered   array: {}", Arrays.toString(nums));
    }
}
