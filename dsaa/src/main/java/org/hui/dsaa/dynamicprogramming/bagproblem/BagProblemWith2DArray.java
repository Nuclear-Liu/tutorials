package org.hui.dsaa.dynamicprogramming.bagproblem;

import java.util.Arrays;
import java.util.Scanner;

import static java.lang.Math.max;

public class BagProblemWith2DArray {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNextLine()) {
            int pW = scanner.nextInt();
            int wpC = scanner.nextInt();
            int[] weights = new int[wpC];
            int[] values = new int[wpC];
            for (int i = 0; i < wpC; i++) {
                weights[i] = scanner.nextInt();
                values[i] = scanner.nextInt();
            }

            int result = calculateMaxValue(weights, values, pW);
            System.out.println(result);

        }
//        int packageWeight = 4;
//        int[] weights = {1, 3, 4};
//        int[] values = {15, 20, 30};
//        int result = calculateMaxValue(weights, values, packageWeight);
//        System.out.println(result);
    }

    private static int calculateMaxValue(int[] weights, int[] values, int packageWeight) {
        int[][] dp = new int[weights.length][packageWeight + 1];
        // init dp[0~wights.length-1][0] default 0
        // init dp[0][0 ~ packageWeight] when j < weight[0] default 0
        for (int j = 0; j <= packageWeight; j++) {
            if (j >= weights[0])
                dp[0][j] = values[0];
        }

        for (int i = 1; i < weights.length; i++) {
            for (int j = 1; j <= packageWeight; j++) {
                if (j < weights[i])
                    dp[i][j] = dp[i - 1][j];
                else
                    dp[i][j] = max(dp[i - 1][j], dp[i - 1][j - weights[i]] + values[i]);
            }
        }

        for (int i = 0; i < dp.length; i++) {
            System.out.println(Arrays.toString(dp[i]));
        }

        return dp[weights.length - 1][packageWeight];
    }
}
