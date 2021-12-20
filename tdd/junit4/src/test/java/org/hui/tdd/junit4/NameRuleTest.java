package org.hui.tdd.junit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestName;

import static org.junit.Assert.assertEquals;

/**
 * @author Hui.Liu
 * @since 2021-11-19 0:00
 */
public class NameRuleTest {
    @Rule
    public final TestName name = new TestName();

    @Test
    public void testA() {
        assertEquals("testA", name.getMethodName());
    }

    @Test
    public void testB() {
        assertEquals("testB", name.getMethodName());
    }
}
