package org.hui.tdd.lombok.features.tostring;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * @author Hui.Liu
 * @since 2021-12-23 21:40
 */
public class ToStringExampleTest {
    /*
    * ToStringExample(INCLUDE_FIELD=10, name=null,
    *   square1=ToStringExample.Square1(width=5, height=10),
    *   square2=ToStringExample.Square2(5, 10),
    *   square3=ToStringExample.Square3(width=5, height=10),
    *   square4=ToStringExample.Square4(
    *       super=ToStringExample.Square1(width=5, height=10), width=5, height=10),
    * tags=null)
    *
     * */
    @Test
    public void testToString() {
        ToStringExample example = new ToStringExample();
        System.out.println(example);
        assertFalse(example.toString().contains("STATIC_VAR"));
        assertTrue(example.toString().contains("INCLUDE_FIELD"));
    }
    @Test
    public void testCallSuper() {
        Square3 square3 = new Square3(5, 6);
        Square4 square4 = new Square4(5, 6);
        assertFalse(square3.toString().contains("super="));
        assertTrue(square4.toString().contains("super="));
    }
    @Test public void testIncludeFieldNames() {
        Square1 square1 = new Square1(5, 5);
        Square2 square2 = new Square2(5, 5);
        assertTrue(square1.toString().contains("width"));
        assertFalse(square2.toString().contains("width"));
    }
}