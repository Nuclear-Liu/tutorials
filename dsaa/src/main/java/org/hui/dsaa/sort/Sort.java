package org.hui.dsaa.sort;

import java.util.Arrays;

/**
 * 选择排序；
 * @author Hui.Liu
 * @since 2022-03-28 14:18
 */
public class Sort {
    public static void main(String[] args) {
        int[] nums = {3, 1, 2, 7, 9, 6, 5};
        System.out.println(Arrays.toString(nums));
//        selectionSort(nums);
//        bubbleSort(nums);
//        insertSort1(nums);
        insertSort2(nums);
        System.out.println(Arrays.toString(nums));
    }

    public static void insertSort2(int[] arr) {
        if (null == arr || arr.length < 2) {
            return;
        }
        for (int end = 1; end < arr.length; end++) {
            // pre 新数的前一个位置
            for (int pre = end - 1; pre >= 0 && arr[pre] > arr[pre + 1] ; pre--) {
                swap(arr, pre, pre + 1);
            }
        }
    }

    public static void insertSort1(int[] arr) {
        if (null == arr || arr.length < 2) {
            return;
        }
        for (int end = 1; end < arr.length; end++) {
            int insertNumIndex = end;
            while (insertNumIndex - 1 >= 0 && arr[insertNumIndex - 1] > arr[insertNumIndex]) {
                swap(arr, insertNumIndex - 1, insertNumIndex);
                insertNumIndex--;
            }
        }
    }

    /**
     * 冒泡排序
     * @param arr 排序数组
     */
    public static void bubbleSort(int[] arr) {
        if (null == arr || arr.length < 2) {
            return;
        }
        for (int i = arr.length - 1; i > 0; i--) {
            for (int j = 0; j < i; j++) {
                if (arr[j] > arr[j+1]) {
                    swap(arr, j, j+1);
                }
            }
        }
    }

    /**
     * 选择排序
     * @param arr 排序数组
     */
    public static void selectionSort(int[] arr) {
        // 处理边界条件
        if (null == arr || arr.length < 2) {
            return;
        }
        for (int i = 0; i < arr.length; i++) {
            int minValueIndex = i;
            for (int j = i + 1; j < arr.length; j++) {
                minValueIndex = arr[j] < arr[minValueIndex] ? j : minValueIndex;
            }
            swap(arr, i, minValueIndex);
        }
    }

    private static void swap(int[] arr, int i, int j) {
        if (j != i) {
            int temp = arr[i];
            arr[i] = arr[j];
            arr[j] = temp;
        }
    }
}
