package org.hui.tdd.junit4;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;

import static org.junit.Assert.*;

/**
 * @author Hui.Liu
 * @since 2021-11-16 13:04
 */
@RunWith(Parameterized.class)
public class AddTest {
    private int a1;
    private int a2;
    private int a3;
    private int r;

    public AddTest(int a1, int a2, int a3, int r) {
        this.a1 = a1;
        this.a2 = a2;
        this.a3 = a3;
        this.r = r;
    }

    @Parameterized.Parameters(name = "{index}:add({0}, {1}, {2})={3}")
    public static Collection<Integer[]> data() {
        return Arrays.asList(new Integer[][] {
                {0, 0, 0, 0},
                {1, 0, 0, 1},
                {1, 1, 0, 2},
                {1, 1, 1, 3},
                {2, 1, 1, 4}
        });
    }

    @Test
    public void add() {
        assertEquals(r, Add.add(a1, a2, a3));
    }
}