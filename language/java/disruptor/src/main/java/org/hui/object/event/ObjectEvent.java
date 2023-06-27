package org.hui.object.event;

public class ObjectEvent<T> {
    private T value;

    public void setValue(T value) {
        this.value = value;
    }
    public void clear() {
        value = null;
    }

    @Override
    public String toString() {
        return "ObjectEvent{" +
                "value=" + value +
                '}';
    }
}
