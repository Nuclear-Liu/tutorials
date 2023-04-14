package org.hui.tdd.hamcrest.tutorial;

import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasToString;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.Matchers.nullValue;
import static org.hamcrest.Matchers.sameInstance;
import static org.hamcrest.Matchers.typeCompatibleWith;

/**
 * @author BadMan
 * @since 2021-11-07 11:42
 */
public class ObjectMatcherTest {
    @Test
    public void testEqualTo() {
        Biscuit redBiscuit = new Biscuit("Red");
        assertThat("", redBiscuit, equalTo(new Biscuit("Red")));
    }
    @Test(expected = AssertionError.class)
    public void testFailEqualTo() {
        Biscuit red = new Biscuit("Red");
        assertThat("color", red, equalTo(new Biscuit("red")));
    }
    @Test
    public void testHasToString() {
        Biscuit red = new Biscuit("Red");
        assertThat("toString", red, hasToString("Biscuit{color='Red'}"));
    }
    @Test
    public void testInstanceOf() {
        Biscuit red = new Biscuit("Red");
        assertThat("", red, instanceOf(Biscuit.class));
    }
    @Test
    public void testIsCompatibleType() {
        Biscuit red = new Biscuit("Red");
        assertThat("", red.getClass(), typeCompatibleWith(Biscuit.class));
        assertThat("", Biscuit.class, typeCompatibleWith(Biscuit.class));
    }
    @Test
    public void testNotNullValue() {
        Biscuit red = new Biscuit("Red");
        assertThat("", red, notNullValue());
        assertThat("", red, notNullValue(Biscuit.class));
    }
    @Test(expected = AssertionError.class)
    public void testFailNotNullValue() {
        Biscuit biscuit = null;
        assertThat("", biscuit, notNullValue());
        assertThat("", biscuit, notNullValue(Biscuit.class));
    }
    @Test
    public void testNullValue() {
        Biscuit biscuit = null;
        assertThat("", biscuit, nullValue());
        assertThat("", biscuit, nullValue(Biscuit.class));
    }
    @Test(expected = AssertionError.class)
    public void testFailNullValue() {
        Biscuit biscuit = new Biscuit("Blue");
        assertThat("", biscuit, nullValue());
        assertThat("", biscuit, nullValue(Biscuit.class));
    }
    @Test
    public void testSameInstance() {
        Biscuit red = new Biscuit("Red");
        Biscuit biscuit = red;
        assertThat("", red, sameInstance(biscuit));
    }
    @Test(expected = AssertionError.class)
    public void testFailSameInstance() {
        Biscuit red = new Biscuit("Red");
        Biscuit blue = new Biscuit("Blue");
        assertThat("", red, sameInstance(blue));
    }
}
