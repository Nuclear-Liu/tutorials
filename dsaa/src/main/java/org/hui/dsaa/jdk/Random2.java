package org.hui.dsaa.jdk;

/**
 * @author Hui.Liu
 * @since 2022-04-02 15:31
 */
public class Random2 {

    public static int to17() {
        return to06() + 1;
    }

    public static int to06() {
        int ans = 0;
        do {
            ans = to07();
        } while (ans == 7);
        return ans;
    }

    public static int to07() {
        return (to01() << 2) + (to01() << 1) + to01();
    }

    public static int to01() {
        int ans = 0;
        do {
            ans = f();
        } while (ans == 3);
        return ans < 3 ? 0 : 1;
    }

    /**
     * @return []
     */
    public static int f() {
        return (int) (Math.random() * 5) + 1;
    }
}
