package org.hui.dsaa.leetcode.difficulty.easy.palindromenumber;

import java.util.HashMap;
import java.util.Map;

class Solution {
    //
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
        int l = (int) Math.log10(x);
        for (int i = 0 , j = l; i < j; i ++, j --) {
            int nl = (x % (int) (Math.pow(10, i + 1))) / (int) Math.pow(10, i);
            int nr = (x / (int) Math.pow(10, j)) % (int) Math.pow(10, i - 1);
            System.out.println(nl);
            System.out.println(nr);
        }

        return true;
    }

    public static void main(String[] args) {
        Map<Integer, Integer> map = new HashMap<>();
        map.put(2, 2);
        map.put(3, 3);
        map.put(2, 3);
        System.out.println(map);
    }
}