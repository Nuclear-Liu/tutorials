package org.hui.tdd.junit4;

import org.junit.experimental.theories.DataPoint;
import org.junit.experimental.theories.DataPoints;
import org.junit.experimental.theories.Theories;
import org.junit.experimental.theories.Theory;
import org.junit.runner.RunWith;

/**
 * @author Hui.Liu
 * @since 2021-11-21 14:15
 */
@RunWith(Theories.class)
public class TheoryTest {
    @DataPoints
    public static String[] args = {"args1", "args2", "args3"};
    @DataPoint
    public static String arg1 = "arg1";
    @DataPoint
    public static String arg2 = "arg2";
    @DataPoint
    public static String arg3 = "arg3";
    @Theory
    public void test(String args) {
        System.out.println(args);
    }
}
