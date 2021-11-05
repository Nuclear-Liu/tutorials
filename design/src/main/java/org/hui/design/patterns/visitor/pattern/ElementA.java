package org.hui.design.patterns.visitor.pattern;

public class ElementA implements Element {
    @Override
    public void accept(Visitor visitor) {
        visitor.visitElementA(this);
    }
}
