package org.hui.design.patterns.memento.pattern;

public class Originator {
    private String state;
    // ...

    public Originator() {
    }

    public Memento createMemento() {
        Memento memento = new Memento(state);
        return memento;
    }

    void setMomento(Memento memento) {
        state = memento.getState();
    }
}
