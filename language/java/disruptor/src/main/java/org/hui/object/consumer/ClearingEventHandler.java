package org.hui.object.consumer;

import com.lmax.disruptor.EventHandler;
import org.hui.object.event.ObjectEvent;

public class ClearingEventHandler<T> implements EventHandler<ObjectEvent<T>> {
    @Override
    public void onEvent(ObjectEvent<T> event, long sequence, boolean endOfBatch) throws Exception {
        event.clear();
    }
}
