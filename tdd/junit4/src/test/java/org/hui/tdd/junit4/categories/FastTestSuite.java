package org.hui.tdd.junit4.categories;

import org.junit.experimental.categories.Categories;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 * @author Hui.Liu
 * @since 2021-11-21 16:59
 */
@RunWith(Categories.class)
@Categories.IncludeCategory(SlowTests.class)
@Categories.ExcludeCategory(FastTests.class) // Note that Categories is a kind of Suite
@Suite.SuiteClasses({A.class, B.class})
public class FastTestSuite {
    // Will run A.b, but not A.a or B.c
}
