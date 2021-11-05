package org.hui.design.patterns.visitor.pattern;

public interface Visitor {
    void visitElementA(ElementA element); // 第二次动态辨析
    void visitElementB(ElementB element); // 第二次动态辨析
}
