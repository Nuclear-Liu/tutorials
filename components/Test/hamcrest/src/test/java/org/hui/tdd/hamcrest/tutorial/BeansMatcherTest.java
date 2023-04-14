package org.hui.tdd.hamcrest.tutorial;

import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasProperty;

/**
 * @author BadMan
 * @since 2021-11-07 12:49
 */
public class BeansMatcherTest {
    @Test
    public void testHasProperty() {
        Biscuit biscuit = new Biscuit("Red"); // must have `get`/`set` method
        // assertThat("", Biscuit.class, hasProperty("color"));
        assertThat("", biscuit, hasProperty("color"));
        assertThat("", biscuit, hasProperty("color", equalTo("Red")));
    }
    @Test(expected = AssertionError.class)
    public void testFailHasProperty() {
        Biscuit biscuit = new Biscuit("Red");
        assertThat("", biscuit, hasProperty("color2"));
    }
}
