package org.hui.dsaa.leetcode.difficulty.easy.palindromenumber;

import java.util.HashMap;
import java.util.Map;

class Solution {
    // to String Process
    public boolean isPalindrome1(int x) {
        String s = String.valueOf(x);
        for (int i = 0, j = s.length() -1; i < s.length() / 2; i ++, j--) {
            if (s.charAt(i) != s.charAt(j)) {
                return false;
            }
        }
        return true;
    }

    public static boolean isPalindrome2(int x) {
        if (x < 0) return false;
        int length = (int) Math.log10(x);
        for (int l = 0 , r = length; l < r; l ++, r --) {
            if (getRightDigits(x, r) != getLeftDigits(x, l)) {
                return false;
            }
        }
        return true;
    }

    private static int getLeftDigits(int x, int l) {
        return 0;
    }

    private static int getRightDigits(int x, int r) {
        return 0;
    }

    public static void main(String[] args) {
        Map<Integer, Integer> map = new HashMap<>();
        map.put(2, 2);
        map.put(3, 3);
        map.put(2, 3);
        System.out.println(map);
    }
}