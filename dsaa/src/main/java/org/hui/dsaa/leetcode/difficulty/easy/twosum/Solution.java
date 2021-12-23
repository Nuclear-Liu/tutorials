package org.hui.dsaa.leetcode.difficulty.easy.twosum;

import java.util.HashMap;
import java.util.Map;

class Solution {
    // o(n!) double loop
    public int[] twoSum01(int[] nums, int target) {
        int[] result = new int[2];
        for (int i = 0; i < nums.length; i++) {
            for (int j = i +1; j < nums.length; j++) {
                if (nums[j] == (target - nums[i])) {
                    result[0] = i;
                    result[1] = j;
                    break;
                }
            }
        }
        return result;
    }

    // o(n) <key: target - nums[index] value: index>
    public int[] twoSum02(int[] nums, int target) {
        int [] result  = new int[2];
        Map<Integer, Integer> map = new HashMap<>();
        for (int i = 0; i < nums.length; i++) {
            if (map.containsKey(nums[i])) {
                result[0] = map.get(nums[i]);
                result[1] = i;
            }
            map.put(target - nums[i], i);
        }
        return result;
    }
}