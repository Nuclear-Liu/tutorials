package org.hui.dsaa.jdk;

/**
 * @author Hui.Liu
 * @since 2022-04-02 14:42
 */
public class Random {
    /**
     * 对于任意 `x` ∈ `[0, 1)` 在 `[0, x]` 概率 `p = x^2`
     *
     * @return [0, 1)
     */
    public static double xToXPower2() {
        return Math.max(Math.random(), Math.random());
    }

    /**
     * 对于任意 `x` ∈ `[0, 1)` 在 `[0, x]` 概率 `p = x^3`
     *
     * @return [0, 1)
     */
    public static double xToXPower3() {
        return Math.max(Math.random(), Math.max(Math.random(), Math.random()));
    }
}
