package org.hui.java.jmh;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class StreamIsPrime {

    static List<Integer> nums = new ArrayList<>();

    static {
        Random r = new Random();
        for (int i = 0; i < 1_000; i++)
            nums.add(1_000_000 + r.nextInt(1_000_000));
    }

    static void foreach() {
        nums.forEach(v -> isPrime(v));
    }

    static void parallel() {
        nums.parallelStream().forEach(StreamIsPrime::isPrime);
    }

    static boolean isPrime(int num) {
        for (int i = 2; i <= num / 2; i++)
            if (num % i == 0)
                return false;

        return true;
    }
}
