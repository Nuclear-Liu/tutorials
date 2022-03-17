package org.hui.dsaa.dynamicprogramming.uniquepaths;

import java.util.Scanner;

/**
 * @author Hui.Liu
 * @since 2022-03-17 10:02
 */
public class UniquePaths01 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNextLine()) {
            int m = scanner.nextInt();
            int n = scanner.nextInt();
            int ways = dfs(1, 1, m, n);
            System.out.println(ways);
        }
    }

    public static int dfs(int i, int j ,int m, int n) {
        if (i > m || j > n) return 0;
        if (i == m && j == n) return 1;
        return dfs(i + 1, j, m, n) + dfs(i, j + 1, m, n);
    }
}
