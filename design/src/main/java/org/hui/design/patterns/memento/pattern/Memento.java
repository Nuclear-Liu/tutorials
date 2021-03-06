package org.hui.design.patterns.memento.pattern;

public class Memento {
    private String state;
    // ...

    public Memento(String state) {
        this.state = state;
    }

    public String getState() {
        return state;
    }

    void setState(String state) {
        this.state = state;
    }
}
