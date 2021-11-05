package org.hui.design.patterns.visitor.pattern;

public class Visitor1 implements Visitor {
    @Override
    public void visitElementA(ElementA element) {
        System.out.println("Visitor1 is processing ElementA");
    }

    @Override
    public void visitElementB(ElementB element) {
        System.out.println("Visitor1 is processing ElementB");
    }
}
