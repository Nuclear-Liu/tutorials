package org.hui.dsaa.tools;

/**
 * 对数器.
 *
 * @author Hui.Liu
 * @since 2022-04-06 10:47
 */
public class NumericalComparator {
    /**
     * 生成长度 [0, maxnLen) 随机，值 [0, maxValue) 随机的数组.
     *
     * @param maxLen   最大长度
     * @param maxValue 最大值
     * @return 随机数组
     */
    public static int[] lenRandomValueRandom(int maxLen, int maxValue) {
        int len = (int) (Math.random() * maxLen);
        int[] ans = new int[len];

        for (int i = 0; i < len; i++) {
            ans[i] = (int) (Math.random() * maxValue);
        }
        return ans;
    }

    /**
     * is sorted.
     *
     * @param arr 被判断有序的数组
     * @return 有序 true 无需 false
     */
    public static boolean isSorted(int[] arr) {
        if (arr == null || arr.length < 2) {
            return true;
        }
        for (int i = 1; i < arr.length; i++) {
            if (arr[i - 1] > arr[i]) {
                return false;
            }
        }
        return true;
    }

    /**
     * Copy Array.
     *
     * @param arr 原数组
     * @return copyArray
     */
    public static int[] copyArray(int[] arr) {
        int[] ans = new int[arr.length];

        for (int i = 0; i < arr.length; i++) {
            ans[i] = arr[i];
        }
        return ans;
    }
}
