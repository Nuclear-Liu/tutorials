package org.hui.tdd.junit4.categories;

import org.junit.experimental.categories.Categories;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 * @author Hui.Liu
 * @since 2021-11-21 16:56
 */
@RunWith(Categories.class)
@Categories.IncludeCategory(SlowTests.class)
@Suite.SuiteClasses({A.class, B.class})
public class SlowTestSuite {
    // Will run A.b and B.c, but not A.a
}
