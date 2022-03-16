package org.hui.dsaa.dynamicprogramming.stairs;

import java.util.Arrays;
import java.util.Scanner;

/**
 * @author Hui.Liu
 * @since 2022-03-16 15:32
 */
public class Stairs {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNextLine()) {
            int n = scanner.nextInt();
            int[] dp = new int[n + 1];
            dp[1] = 1;
            dp[2] = 2;
            for (int i = 3; i <= n; i++) {
                dp[i] = dp[i - 1] + dp[i - 2];
            }
            System.out.println(Arrays.toString(dp));
        }
    }
}
