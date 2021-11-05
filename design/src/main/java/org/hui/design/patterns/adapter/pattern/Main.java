package org.hui.design.patterns.adapter.pattern;

public class Main {
    public static void main(String[] args) {
        Adaptee adaptee = new OldClass();
        Target target = new Adapter(adaptee);
        target.process();
    }
}
