package org.hui.tdd.lombok.features.nonnull;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * @author Hui.Liu
 * @since 2021-12-19 17:22
 */
public class PointTest {

    @Test(expected = NullPointerException.class)
    public void testNonNull() {
        Point point = new Point();
    }

}