package org.hui.java.functionalprogramming;

@FunctionalInterface
public interface C extends B ,A {
    String getC();

    @Override
    default String getB() {
        return "test";
    }
}
