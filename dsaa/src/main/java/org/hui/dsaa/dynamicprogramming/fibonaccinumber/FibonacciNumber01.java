package org.hui.dsaa.dynamicprogramming.fibonaccinumber;

import java.util.Arrays;
import java.util.Scanner;

/**
 * @author Hui.Liu
 * @since 2022-03-16 10:32
 */
public class FibonacciNumber01 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNextLine()) {
            int N = scanner.nextInt();
            int[] dp = new int[N + 1];
            dp[0] = 0;
            dp[1] = 1;
            for (int i = 2; i <= N; i++) {
                dp[i] = dp[i - 1] + dp[i - 2];
            }
            System.out.println(Arrays.toString(dp));
        }
    }
}
