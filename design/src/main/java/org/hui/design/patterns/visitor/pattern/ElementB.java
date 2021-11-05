package org.hui.design.patterns.visitor.pattern;

public class ElementB implements Element {
    @Override
    public void accept(Visitor visitor) {
        visitor.visitElementB(this);
    }
}
