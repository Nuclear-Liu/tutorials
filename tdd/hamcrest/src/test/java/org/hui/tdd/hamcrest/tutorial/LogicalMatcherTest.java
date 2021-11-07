package org.hui.tdd.hamcrest.tutorial;

import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.anyOf;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.not;


/**
 * @author BadMan
 * @since 2021-11-06 17:54
 */
public class LogicalMatcherTest {
    @Test
    public void testAllOf() {
        assertThat("",4, allOf(equalTo(4)));
    }
    @Test(expected = AssertionError.class)
    public void testFailAllOf() {
        assertThat("", 4, allOf(equalTo(4), equalTo(5)));
    }
    @Test
    public void testAnyOf() {
        assertThat("", 6, anyOf(equalTo(5), equalTo(6)));
    }
    @Test
    public void testNot() {
        assertThat("", 6, not(7));
    }
}
