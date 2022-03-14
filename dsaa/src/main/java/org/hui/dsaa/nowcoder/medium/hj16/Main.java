package org.hui.dsaa.nowcoder.medium.hj16;

import java.util.*;

/**
 * @author Hui.Liu
 */
public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        while (sc.hasNextLine()) {
            int money = sc.nextInt();
            int m = sc.nextInt();
            sc.nextLine();
            money /= 10;
            int[][] prices = new int[m + 1][3];
            int[][] weights = new int[m + 1][3];
            for (int i = 1; i <= m; i++) {
                int v = sc.nextInt(); // price
                int p = sc.nextInt(); // weight
                int q = sc.nextInt(); // q = 0 # 主件 q > 1 # 附件 所属 主件编号
                v /= 10; // price
                p = p * v; // weight
                if (q == 0) {
                    // 主件
                    prices[i][0] = v;
                    weights[i][0] = p;
                } else if (prices[q][1] == 0) {
                    // 附件1
                    prices[q][1] = v;
                    weights[q][1] = p;
                } else {
                    // 附件2
                    prices[q][2] = v;
                    weights[q][2] = p;
                }
                sc.nextLine();
            }
            int[][] dp = new int[m + 1][money + 1];
            for (int i = 1; i <= m; i++) {
                for(int j = 1; j <= money; j++) {
                    int a = prices[i][0];
                    int b = weights[i][0];
                    int c = prices[i][1];
                    int d = weights[i][1];
                    int e = prices[i][2];
                    int f = weights[i][2];

                    dp[i][j] = j - a >= 0 ? Math.max(dp[i - 1][j], dp[i - 1][j - a] + b) : dp[i - 1][j];
                    dp[i][j] = j - a - c >= 0 ? Math.max(dp[i][j], dp[i - 1][j - a - c] + b + d):dp[i][j];
                    dp[i][j] = j - a - e >= 0 ? Math.max(dp[i][j], dp[i - 1][j - a - e] + b + f):dp[i][j];
                    dp[i][j] = j - a - c - e >= 0 ? Math.max(dp[i][j], dp[i - 1][j - a - c - e] + b +d + f):dp[i][j];
                }
            }
            System.out.println(dp[m][money] * 10);
        }
    }
}
