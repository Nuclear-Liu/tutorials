package org.hui.java.functionalprogramming;

import lombok.extern.slf4j.Slf4j;
import org.hui.java.functionalprogramming.Calculator.IntegerMath;
import org.junit.jupiter.api.Test;

@Slf4j
public class CalculatorTests {
    @Test
    public void testOperateBinary() {
        Calculator myApp = new Calculator();
        IntegerMath addition = (a, b) -> a + b;
        IntegerMath subtraction = (a, b) -> a - b;
        log.info("40 + 2 = {}", myApp.operateBinary(40, 2, addition));
        log.info("20 - 10 = {}", myApp.operateBinary(20, 10, subtraction));
    }

    @Test
    public void test() throws InterruptedException {
        String context = "context";
        // context = "Hello";
        Runnable runner = new Runnable() {
            @Override
            public void run() {
                System.out.println(context);
            }
        };
        Thread t = new Thread(runner);
        t.start();
        t.join();
    }

    @Test
    public void testFuncationInterface() {
        C c = () -> "GetC";

        System.out.println(c.getC());
        System.out.println(c.getB());

        D d = () -> "GetD";

        System.out.println(d.getB());
    }

    @Test
    public void testFin() {
        FF f = () -> {
            return () -> "return A";
            // return new Object();
        };
    }


}
