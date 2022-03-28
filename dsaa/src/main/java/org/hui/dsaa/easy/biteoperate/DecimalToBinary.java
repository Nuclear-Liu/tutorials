package org.hui.dsaa.easy.biteoperate;

/**
 * 负数表示 = (除符号位)存储值 取反 + 1
 *
 * @author Hui.Liu
 * @since 2022-03-28 9:47
 */
public class DecimalToBinary {

    public static void print(int num) {
        for (int i = 31; i >= 0; i--) {
            System.out.print((num & (1 << i)) == 0? '0' : '1');
        }
        System.out.println();
    }
    public static void main(String[] args) {
        int num = -23;
        print(num);

        int max = Integer.MAX_VALUE;
        print(max);
        int min = Integer.MIN_VALUE;
        print(-1);
        print(~min);
    }
}
