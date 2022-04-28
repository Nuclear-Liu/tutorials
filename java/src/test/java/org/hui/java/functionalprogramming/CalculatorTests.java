package org.hui.java.functionalprogramming;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.hui.java.functionalprogramming.Calculator.IntegerMath;

@Slf4j
public class CalculatorTests {
    @Test
    public void testOperateBinary() {
        Calculator myApp = new Calculator();
        IntegerMath addition = (a, b) -> a + b;
        IntegerMath subtraction = (a, b) ->  a - b;
        log.info("40 + 2 = {}", myApp.operateBinary(40, 2, addition));
        log.info("20 - 10 = {}", myApp.operateBinary(20, 10, subtraction));
    }
}
