package org.hui.tdd.hamcrest.tutorial;

import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.hui.tdd.hamcrest.tutorial.IsNotANumber.notANumber;

/**
 * @author BadMan
 * @since 2021-11-02 20:40
 */
public class IsNotANumberTest {

    @Test
    public void testSquareRootOfMinusOneIsNotANumber() {
        assertThat(Math.sqrt(-1), is(notANumber()));
    }

    @Test(expected = AssertionError.class)
    public void testNotANumber() {
        assertThat(1.0, is(notANumber()));
    }
}