package org.hui.dsaa.dynamicprogramming.fibonaccinumber;

import java.util.Scanner;

/**
 * @author Hui.Liu
 * @since 2022-03-16 11:14
 */
public class FibonacciNumber03 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNextLine()) {
            int n = scanner.nextInt();
            int dpN = fib(n);
            System.out.println(dpN);
        }
    }

    public static int fib(int n) {
        if (n == 0) return 0;
        if (n == 1) return 1;
        return fib(n - 1) + fib(n - 2);
    }
}
