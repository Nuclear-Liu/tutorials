package org.hui.tdd.junit4;

/**
 * @author Hui.Liu
 * @since 2021-11-16 12:53
 */
public class Fibonacci {
    public static int compute(int n) {
        int result = 0;

        if (n <= 1) {
            result = n;
        } else {
            result = compute(n - 1) + compute(n - 2);
        }

        return result;
    }
}
