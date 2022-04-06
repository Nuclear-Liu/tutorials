package org.hui.dsaa.simplesort;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.hui.dsaa.simplesort.Sort.bubbleSort;
import static org.hui.dsaa.simplesort.Sort.selectionSort;
import static org.hui.dsaa.tools.NumericalComparator.copyArray;
import static org.hui.dsaa.tools.NumericalComparator.isSorted;
import static org.hui.dsaa.tools.NumericalComparator.lenRandomValueRandom;

/**
 * @author Hui.Liu
 * @since 2022-04-02 11:18
 */
@Slf4j
public class BubbleSortTests {
    @Test
    public void testSort() {
        int maxLen = 500;
        int maxValue = 1000;
        int testTime = 100000;

        for (int i = 0; i < testTime; i++) {
            int[] arr = lenRandomValueRandom(maxLen, maxValue);
            int[] temp = copyArray(arr);
            bubbleSort(arr);
            if (!isSorted(arr)) {
                log.info("unordered array: {}", Arrays.toString(temp));
                log.info("ordered   array: {}", Arrays.toString(arr));
            }
        }
    }
}
