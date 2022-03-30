package org.hui.dsaa;

/**
 * @author Hui.Liu
 * @since 2022-03-30 10:57
 */
public class Random {
    /**
     *
     * @return 1 - (1 -x) * (1 -x)
     */
    public static double xTo() {
        return Math.min(Math.random(), Math.random());
    }

    /**
     * 返回 [0, 1) 随机数，返回概率符号和 x 的平方。
     * @return 双精度浮点点随机值
     */
    public static double xToXPower2() {
        return Math.max(Math.random(), Math.random());
    }

    public static double xToXPower3() {
        return Math.max(Math.random(), Math.max(Math.random(), Math.random()));
    }



    public static void main(String[] args) {
        int testTimes = 10000000;
        int count = 0;
        double x = 0.7;

        for (int i = 0; i < testTimes; i++) {
            if (xToXPower2() < x) {
                count++;
            }
        }
        System.out.println((double) count / (double) testTimes);
        System.out.println(Math.pow(x, 2));

        count = 0;
        for (int i = 0; i < testTimes; i++) {
            if (xToXPower3() < x) {
                count++;
            }
        }
        System.out.println((double) count / (double) testTimes);
        System.out.println(Math.pow(x, 3));

        count = 0;
        for (int i = 0; i < testTimes; i++) {
            if (xTo() < x) {
                count++;
            }
        }
        System.out.println((double) count / (double) testTimes);
        System.out.println(1 - Math.pow(1- x, 2));

    }
}
