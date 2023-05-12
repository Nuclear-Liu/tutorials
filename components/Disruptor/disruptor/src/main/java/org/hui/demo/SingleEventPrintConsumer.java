package org.hui.demo;

import com.lmax.disruptor.EventHandler;


public class SingleEventPrintConsumer {

    public EventHandler<ValueEvent>[] getEventHandler() {
        EventHandler<ValueEvent> eventHandler = (event, sequence, endOfBatch) -> System.out.println("Id is " + event.getValue() + " sequence id that was used is " + sequence);
        return new EventHandler[] {eventHandler};
    }

}
