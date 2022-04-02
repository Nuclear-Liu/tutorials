package org.hui.dsaa.simplesort;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.hui.dsaa.simplesort.Sort.selectionSort;

/**
 * @author Hui.Liu
 * @since 2022-04-02 11:11
 */
@Slf4j
public class SelectionSortTests {
    @Test public void testSort() {
        int[] nums = {3, 1, 2, 7, 9, 6, 5};
        log.info("unordered array: {}", Arrays.toString(nums));
        selectionSort(nums);
        log.info("ordered   array: {}", Arrays.toString(nums));
    }
}
