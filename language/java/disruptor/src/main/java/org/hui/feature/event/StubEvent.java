package org.hui.feature.event;

import com.lmax.disruptor.EventFactory;
import com.lmax.disruptor.EventTranslatorTwoArg;

import java.util.Objects;

public final class StubEvent {
    private int value;
    private String testString;
    public static final EventTranslatorTwoArg<StubEvent, Integer, String> TRANSLATOR = ((event, sequence, arg0, arg1) -> {
        event.setValue(arg0);
        event.setTestString(arg1);
    });
    public static final EventFactory<StubEvent> EVENT_FACTORY = () -> new StubEvent(-1);

    public StubEvent(final int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public void setValue(final int value) {
        this.value = value;
    }

    public String getTestString() {
        return testString;
    }

    public void setTestString(final String testString) {
        this.testString = testString;
    }

    public void clear() {
        value = -1;
        testString = null;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        StubEvent stubEvent = (StubEvent) o;
        return value == stubEvent.value && Objects.equals(testString, stubEvent.testString);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value, testString);
    }

    @Override
    public String toString() {
        return "StubEvent{" +
                "value=" + value +
                ", testString='" + testString + '\'' +
                '}';
    }
}
