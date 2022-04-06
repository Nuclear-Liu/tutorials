package org.hui.dsaa.tools;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.hui.dsaa.simplesort.Sort.insertSort2;
import static org.hui.dsaa.simplesort.Sort.selectionSort;
import static org.hui.dsaa.tools.NumericalComparator.copyArray;
import static org.hui.dsaa.tools.NumericalComparator.isSorted;
import static org.hui.dsaa.tools.NumericalComparator.lenRandomValueRandom;

/**
 * 对数器
 *
 * @author Hui.Liu
 * @since 2022-04-06 10:36
 */
@Slf4j
public class NumericalComparatorTests {
    @Test
    public void test() {
        int maxLen = 50;
        int maxValue = 1000;
        int testTime = 10000;

        for (int i = 0; i < testTime; i++) {
            int[] arr1 = lenRandomValueRandom(maxLen, maxValue);
            int[] temp = copyArray(arr1);
            selectionSort(arr1);
            if (!isSorted(arr1)) {
                log.info(Arrays.toString(temp));
                log.info(Arrays.toString(arr1));
            }
        }
    }



    private static boolean equalValues(int[] arr1, int[] arr2) {
        for (int i = 0; i < arr1.length; i++) {
            if (arr1[i] != arr2[i]) {
                return false;
            }
        }
        return true;
    }
}
