package org.hui.tdd.junit4;

import org.junit.Ignore;
import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

/**
 * @author BadMan
 * @since 2021-11-16 9:56
 */
public class IgnoreTest {
    @Ignore("Test is ignored as a demonstration")
    @Test
    public void testIgnore() {
        assertThat(1, is(1));
    }
}
