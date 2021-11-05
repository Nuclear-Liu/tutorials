package org.hui.design.patterns.iterator.pattern;

public interface Iterator<T> {
    void first();
    void next();
    boolean isDone();
    T current();
}
