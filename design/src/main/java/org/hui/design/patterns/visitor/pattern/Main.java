package org.hui.design.patterns.visitor.pattern;

public class Main {
    public static void main(String[] args) {
        Visitor visitor = new Visitor2();
        Element element = new ElementB();
        element.accept(visitor); // double dispatch 二次多态辨析
    }
}
