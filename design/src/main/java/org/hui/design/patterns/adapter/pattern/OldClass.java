package org.hui.design.patterns.adapter.pattern;

public class OldClass implements Adaptee {
    @Override
    public void foo(int data) {
        // ...
    }

    @Override
    public int bar() {
        return 0;
    }
}
