package org.hui.design.patterns.iterator.pattern;

public class CollectionIterator<T> implements Iterator<T> {
    MyCollection<T> myCollection;

    public CollectionIterator(MyCollection<T> myCollection) {
        this.myCollection = myCollection;
    }

    @Override
    public void first() {

    }

    @Override
    public void next() {

    }

    @Override
    public boolean isDone() {
        return false;
    }

    @Override
    public T current() {
        return null;
    }
}
