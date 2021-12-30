package org.hui.java.languageupdate;

import org.junit.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertTrue;

/**
 * @author Hui.Liu
 * @since 2021-12-29 14:28
 */
public class LocalTimeTest {
    @Test
    public void testTime() {
        LocalDateTime time1 = LocalDateTime.parse("2021-12-28 16:04:10", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        LocalDateTime time2 = LocalDateTime.parse("2021-12-28 16:04:17", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        assertTrue(time1.isBefore(time2));
    }
}

class Solution {
    public String[] findRelativeRanks(int[] score) {
        Map<Integer, Integer> map = new HashMap<>();

        for (int i=0; i<score.length; ++i)
            map.put(score[i], i);

        Arrays.sort(score);
        String[] ans = new String[score.length];
        for (int i=0; i<score.length; ++i){
            int s = map.get(score[i]);
            if (i == score.length-1) {
                ans[s] = "Gold Medal";
            } else if (i == score.length-2) {
                ans[s] = "Silver Medal";
            } else if (i == score.length-3) {
                ans[s] = "Bronze Medal";
            } else {
                ans[s] = String.valueOf(score.length-i);
            }
        }
        return ans;
    }
}
