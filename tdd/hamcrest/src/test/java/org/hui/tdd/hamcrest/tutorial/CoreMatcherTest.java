package org.hui.tdd.hamcrest.tutorial;

import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.anything;
import static org.hamcrest.Matchers.describedAs;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

/**
 * @author BadMan
 * @since 2021-11-06 17:12
 */
public class CoreMatcherTest {
    @Test
    public void testAnything() {
        assertThat("", 1, anything("3"));
    }
    @Test
    public void testDescribedAs() {
        assertThat("", 1, describedAs("3", equalTo(1)));
    }
    @Test
    public void testIs() {
        assertThat("", 1, is(new Integer(1)));
    }
}
