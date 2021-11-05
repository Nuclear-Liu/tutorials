package org.hui.design.patterns.adapter.pattern;

public class Adapter implements Target {
    private Adaptee adaptee;

    public Adapter(Adaptee adaptee) {
        this.adaptee = adaptee;
    }

    @Override
    public void process() {
        int data = adaptee.bar();
        // ...
        adaptee.foo(data);
    }
}
