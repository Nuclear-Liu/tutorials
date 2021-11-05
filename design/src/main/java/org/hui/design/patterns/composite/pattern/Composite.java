package org.hui.design.patterns.composite.pattern;

import java.util.List;

public class Composite implements Component {

    private String name;
    private List<Component> elements;

    public Composite(String name, List<Component> elements) {
        this.name = name;
        this.elements = elements;
    }

    public void add(Component element) {
        elements.add(element);
    }

    public void remove(Component element) {
        elements.remove(element);
    }

    @Override
    public void process() {
        // 1. process current node
        // 2. process leaf nodes
        for (Component element : elements) {
            element.process(); // 多态调用
        }
    }
}
