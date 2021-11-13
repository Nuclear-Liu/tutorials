package org.hui.tdd.junit4;

import junit.framework.JUnit4TestAdapter;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses(SuiteTest.class)
public class SuiteTest {
//    public static Test suite() {
//        return (Test) new JUnit4TestAdapter(SuiteTest.class);
//    }
//    @Test
//    public void test1() {
//        System.out.println("Hello");
//    }
}
