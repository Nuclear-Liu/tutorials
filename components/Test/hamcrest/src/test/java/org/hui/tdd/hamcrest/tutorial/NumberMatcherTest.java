package org.hui.tdd.hamcrest.tutorial;

import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.closeTo;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.greaterThanOrEqualTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.lessThan;
import static org.hamcrest.Matchers.lessThanOrEqualTo;
import static org.hamcrest.Matchers.not;

/**
 * @author BadMan
 * @since 2021-11-08 9:27
 */
public class NumberMatcherTest {
    @Test
    public void testCloseTo() {
        double num1 = 3.1415926528;
        double num2 = 3.1415926537;
        double prec = 0.000000001;
        assertThat("", num1, closeTo(num2, prec));
    }
    @Test(expected = AssertionError.class)
    public void testFailCloseTo() {
        double num1 = 3.1415926528;
        double num2 = 3.1415926538;
        double prec = 0.000000001;
        assertThat("", num1, closeTo(num2, prec));
    }
    @Test
    public void testGreaterThan() {
        assertThat("", 3.14, greaterThan(3.13));
    }
    @Test(expected = AssertionError.class)
    public void testFailGreaterThan() {
        assertThat("", 3.14, greaterThan(3.14));
        assertThat("", 3.14, greaterThan(3.15));
    }
    @Test
    public void testGreaterThanOrEqualTo() {
        assertThat("", 3.14, greaterThanOrEqualTo(3.13));
        assertThat("", 3.14, greaterThanOrEqualTo(3.14));
    }
    @Test(expected = AssertionError.class)
    public void testFailGreaterThanorEqualTo() {
        assertThat("", 3.14, greaterThanOrEqualTo(3.15));
    }
    @Test
    public void testLessThan() {
        assertThat("", 3.14, is(lessThan(3.15)));
        assertThat("", 3.14, not(lessThan(3.14)));
        assertThat("", 3.14, not(lessThan(3.13)));
    }
    @Test
    public void testLessThanOrEqualTo() {
        assertThat("", 3.14, lessThanOrEqualTo(3.15));
        assertThat("", 3.14, lessThanOrEqualTo(3.14));
        assertThat("", 3.14, not(lessThanOrEqualTo(3.13)));
    }
}
