package org.hui.tdd.hamcrest.tutorial;

import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;

/**
 * @author BadMan
 * @since 2021-11-02 18:38
 */
public class BiscuitTest {
    @Test
    public void testEquals() {
        Biscuit theBiscuit = new Biscuit("Ginger");
        Biscuit myBiscuit = new Biscuit("Ginger");
        assertThat("color", theBiscuit, equalTo(myBiscuit));
    }
    @Test(expected = AssertionError.class)
    public void testNotEquals() {
        Biscuit theBiscuit = new Biscuit("Ginger");
        Biscuit myBiscuit = new Biscuit("Pink");
        assertThat("color", theBiscuit, equalTo(myBiscuit));
    }
}
