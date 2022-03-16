package org.hui.dsaa.dynamicprogramming.fibonaccinumber;

import java.util.Scanner;

/**
 * @author Hui.Liu
 * @since 2022-03-16 11:07
 */
public class FibonacciNumber02 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNextLine()) {
            int n = scanner.nextInt();
            int[] dp = new int[2];
            dp[0] = 0;
            dp[1] = 1;
            for (int i = 2; i <= n; i++) {
                int sum = dp[0] + dp[1];
                dp[0] = dp[1];
                dp[1] = sum;
            }
            System.out.println(dp[1]);
        }
    }
}
