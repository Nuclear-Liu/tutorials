package org.hui.dsaa.simplesort;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.hui.dsaa.simplesort.Sort.bubbleSort;
import static org.hui.dsaa.simplesort.Sort.insertSort1;
import static org.hui.dsaa.simplesort.Sort.insertSort2;
import static org.hui.dsaa.tools.NumericalComparator.copyArray;
import static org.hui.dsaa.tools.NumericalComparator.isSorted;
import static org.hui.dsaa.tools.NumericalComparator.lenRandomValueRandom;

/**
 * @author Hui.Liu
 * @since 2022-04-02 11:19
 */
@Slf4j
public class InsertSortTests {
    @Test public void testSort1() {
        int maxLen = 500;
        int maxValue = 1000;
        int testTime = 100000;

        for (int i = 0; i < testTime; i++) {
            int[] nums = lenRandomValueRandom(maxLen, maxValue);
            int[] temp = copyArray(nums);
            insertSort1(nums);
            if (!isSorted(nums)) {
                log.info("unordered array: {}", Arrays.toString(temp));
                log.info("ordered   array: {}", Arrays.toString(nums));
            }
        }
    }
    @Test public void testSort2() {
        int maxLen = 500;
        int maxValue = 1000;
        int testTime = 100000;

        for (int i = 0; i < testTime; i++) {
            int[] nums = lenRandomValueRandom(maxLen, maxValue);
            int[] temp = copyArray(nums);
            insertSort2(nums);
            if (!isSorted(nums)) {
                log.info("unordered array: {}", Arrays.toString(temp));
                log.info("ordered   array: {}", Arrays.toString(nums));
            }
        }
    }
}
