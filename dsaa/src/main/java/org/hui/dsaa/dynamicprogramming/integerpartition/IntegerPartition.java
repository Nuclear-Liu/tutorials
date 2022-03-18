package org.hui.dsaa.dynamicprogramming.integerpartition;

import java.util.Arrays;
import java.util.Scanner;

import static java.lang.Math.max;


/**
 * Integer Partition.
 */
public class IntegerPartition {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNextLine()) {
            int n = scanner.nextInt();

            int[] dp = new int[n + 1];

            dp[2] = 1;

            for (int i = 3; i <= n; i++) {
                for (int j = 2; j < i; j++) {
                    dp[i] = max(dp[i], max((i - j) * j, dp[i - j] * j));
                }
            }
            System.out.println(Arrays.toString(dp));
        }
    }
}
