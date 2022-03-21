package org.hui.dsaa.dynamicprogramming.bagproblem;

import java.util.Arrays;
import java.util.Scanner;

import static java.lang.Math.max;

public class BagProblemWithArray {
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
    }

    private static int calculateMaxValue(int[] weights, int[] values, int packageWeight) {
        int[] dp = new int[packageWeight + 1];
        // init dp[]
        dp[0] = 0;
        for (int i = 0; i < weights.length; i++) {
            for (int j = packageWeight; j >= weights[i]; j--) {
                dp[j] = max(dp[j],dp[j - weights[i]] + values[i]);
            }
        }
        System.out.println(Arrays.toString(dp));
        return dp[packageWeight];
    }
}
