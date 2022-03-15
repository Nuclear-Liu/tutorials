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
                    int mainP = prices[i][0];
                    int mainW = weights[i][0];
                    int sub1P = prices[i][1];
                    int sub1W = weights[i][1];
                    int sub2P = prices[i][2];
                    int sub2W = weights[i][2];

                    dp[i][j] = j - mainP >= 0 ? Math.max(dp[i - 1][j], dp[i - 1][j - mainP] + mainW) : dp[i - 1][j];

                    dp[i][j] = j - mainP - sub1P >= 0 ? Math.max(dp[i][j], dp[i - 1][j - mainP - sub1P] + mainW + sub1W):dp[i][j];

                    dp[i][j] = j - mainP - sub2P >= 0 ? Math.max(dp[i][j], dp[i - 1][j - mainP - sub2P] + mainW + sub2W):dp[i][j];

                    dp[i][j] = j - mainP - sub1P - sub2P >= 0 ? Math.max(dp[i][j], dp[i - 1][j - mainP - sub1P - sub2P] + mainW +sub1W + sub2W):dp[i][j];
                }
            }
            System.out.println(dp[m][money] * 10);
        }
    }
}
