package org.hui.java.functionalprogramming;

public class Calculator {

    @FunctionalInterface
    interface IntegerMath {
        int operation(int a, int b);
    }

    public int operateBinary(int a, int b, IntegerMath op) {
        return op.operation(a, b);
    }

}
