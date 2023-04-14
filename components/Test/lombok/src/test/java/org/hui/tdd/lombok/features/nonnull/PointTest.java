package org.hui.tdd.lombok.features.nonnull;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * @author Hui.Liu
 * @since 2021-12-19 17:22
 */
public class PointTest {

    @Test(expected = IllegalArgumentException.class)
    public void testNonNull() {
        Point point = new Point();
        point.setX(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testParameter() {
        Point.testParameter(null);
    }

    @Test
    public void testMethod() {
        Point.methodAnon();
    }
    @Test
    public void testMethod1() {
        Point.method1();
    }
    @Test(expected = IllegalArgumentException.class)
    public void testMethodxx() {
        Point.method2(null, null);
    }
    @Test
    public void testMethodox() {
        Point.method2(new Object(), null);
    }
    @Test(expected = IllegalArgumentException.class)
    public void testMethodxo() {
        Point.method2(null, "Hello");
    }
    @Test
    public void testMethodoo() {
        Point.method2(new Object(), "Hello");
    }

    @Test
    public void testConstructorXX() {
        Point point = new Point(null, null);
    }
    @Test
    public void testConstructorXO() {
        Point point = new Point(null, 1);
    }
    @Test
    public void testConstructorox() {
        Point point = new Point(1, null);
    }
    @Test
    public void testConstructoroo() {
        Point point = new Point(1, 1);
    }

    @Test
    public void testLocalVariableIsNull() {
        Point.localVariableIsNull();
    }

}