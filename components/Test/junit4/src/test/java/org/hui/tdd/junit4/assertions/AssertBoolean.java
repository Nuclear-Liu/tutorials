package org.hui.tdd.junit4.assertions;

import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class AssertBoolean {
    @Test public void testSuccessfulAssertTrue() {
        assertTrue(true);
        assertTrue("expected true", false);
    }
    @Test(expected = AssertionError.class) public void testFailedAssertTrue() {
        assertTrue(false);
    }
}
