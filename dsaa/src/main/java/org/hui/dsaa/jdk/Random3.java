package org.hui.dsaa.jdk;

/**
 * @author Hui.Liu
 * @since 2022-04-02 16:20
 */
public class Random3 {

    public static int to01() {
        /*int ans = 0;
        do {
            ans = (f() << 1) + f();
        } while (ans == 0 || ans == 3);
        return ans == 1 ? 0 : 1;*/
        int ans = 0;
        do {
            ans = f();
        } while (ans == f());
        return ans;
    }

    public static int f() {
        return Math.random() < 0.84 ? 0 : 1;
    }
}
