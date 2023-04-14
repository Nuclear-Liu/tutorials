package org.hui.tdd.junit4.categories;

import org.junit.Test;
import org.junit.experimental.categories.Category;

import static org.junit.Assert.fail;

/**
 * @author Hui.Liu
 * @since 2021-11-21 16:48
 */
public class A {
    @Test
    public void a() {
        fail();
    }

    @Category(SlowTests.class)
    @Test
    public void b() {}
}
