package org.hui.design.patterns.composite.pattern;

public class Leaf implements Component {
    private String name;

    public Leaf(String name) {
        this.name = name;
    }

    @Override
    public void process() {
        // process current node
    }
}
