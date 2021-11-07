package org.hui.tdd.hamcrest.tutorial;

import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.array;

/**
 * @author BadMan
 * @since 2021-11-07 13:24
 */
public class CollectionMatcherTest {
    private final String[] arrayS = {"1", "2", "3", "4"};
    @Test
    public void testArray() {
        assertThat("", arrayS, array());
    }
}
