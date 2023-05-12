package org.hui.demo;

import com.lmax.disruptor.EventFactory;

public class ValueEvent {
    private int value;

    public final static EventFactory<ValueEvent> EVENT_FACTORY = () -> new ValueEvent();

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }
}
