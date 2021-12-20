package org.hui.tdd.junit4.categories;

import org.junit.Test;
import org.junit.experimental.categories.Category;

/**
 * @author Hui.Liu
 * @since 2021-11-21 16:54
 */
@Category({SlowTests.class, FastTests.class})
public class B {
    @Test
    public void c() {}
}
