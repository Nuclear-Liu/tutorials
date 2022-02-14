package org.hui.java.tutorials;

import org.junit.Test;

/**
 * Hui.Liu
 */
public class ReflectTest {

    @Test
    public void testForClass() throws ClassNotFoundException {
        Class<?> aClass = Class.forName("org.hui.java.tutorials.ReflectTest");
        Class cDoubleArray = Class.forName("[D");

        Class cStringArray = Class.forName("[[Ljava.lang.String;");

        System.out.println(aClass);
        String doubleClassName = double.class.getName();
        String aDoubleClassName = double[].class.getName();
        System.out.println(doubleClassName);
    }
}
