package org.hui.design.patterns.visitor.pattern;

public interface Element {
    void accept(Visitor visitor); // 第一次多态辨析
}
