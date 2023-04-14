package org.hui.study.before;

import java.util.Scanner;

public class ReadingIntegerNumbers {
    /**
     * input: 101    102 103  104
     * @param args
     */
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNextInt()) {
            System.out.println(scanner.nextInt());
        }
    }
}
