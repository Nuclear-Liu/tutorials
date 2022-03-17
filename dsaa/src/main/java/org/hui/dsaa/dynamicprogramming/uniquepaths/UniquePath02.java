package org.hui.dsaa.dynamicprogramming.uniquepaths;

import java.util.Arrays;
import java.util.Scanner;

/**
 * @author Hui.Liu
 * @since 2022-03-17 14:23
 */
public class UniquePath02 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNextLine()) {
            int m = scanner.nextInt();
            int n = scanner.nextInt();
            int[][] dp = new int[m][n];

            // init
            for (int i = 0; i < n; i++) {
                dp[0][i] = 1;
            }
            for (int i = 0; i < m; i++) {
                dp[i][0] = 1;
            }
            for (int i = 1; i < m; i++) {
                for (int j = 1; j < n; j++) {
                    dp[i][j] = dp[i - 1][j] + dp[i][j - 1];
                }
            }
            for (int i = 0; i < m; i++) {
                System.out.println(Arrays.toString(dp[i]));
            }
        }
    }
}
