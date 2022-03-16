package org.hui.dsaa.dynamicprogramming.mincostclimbingstairs;

import java.util.Arrays;
import java.util.Scanner;

import static java.lang.Math.min;

public class MinCostClimbingStairs {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNextLine()) {
            int[] cost = getCost(scanner.nextLine());
            int[] dp = new int[cost.length];

            dp[0] = cost[0];
            dp[1] = cost[1];

            for (int i = 2; i < cost.length; i++) {
                dp[i] = min(dp[i -1], dp[i - 2]) + cost[i];
            }

            System.out.println(Arrays.toString(dp));
            System.out.println(min(dp[cost.length - 1], dp[cost.length - 2]));
        }
    }

    private static int[] getCost(String costStr) {
        String[] costArr = costStr.substring(1, costStr.length() - 1).split(",");
        int[] cost = new int[costArr.length];
        for (int i = 0; i < costArr.length; i++) {
            cost[i] = Integer.valueOf(costArr[i].replace(" ", ""));
        }
        return cost;
    }
}
