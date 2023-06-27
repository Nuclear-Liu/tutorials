package org.hui.object;

import com.lmax.disruptor.EventTranslatorOneArg;
import com.lmax.disruptor.EventTranslatorVararg;
import com.lmax.disruptor.YieldingWaitStrategy;
import com.lmax.disruptor.dsl.Disruptor;
import com.lmax.disruptor.dsl.ProducerType;
import com.lmax.disruptor.util.DaemonThreadFactory;
import org.hui.basic.event.LongEvent;
import org.hui.object.consumer.ClearingEventHandler;
import org.hui.object.consumer.ObjectEventHandler;
import org.hui.object.event.ObjectEvent;

import java.nio.ByteBuffer;

public class ObjectEventProcessor {
    private Disruptor<ObjectEvent<String>> disruptor;

    public ObjectEventProcessor(int bufferSize) {
        disruptor = new Disruptor<>(ObjectEvent::new, bufferSize, DaemonThreadFactory.INSTANCE, ProducerType.SINGLE,new YieldingWaitStrategy());
        disruptor.handleEventsWith(new ObjectEventHandler<>())
                .then(new ClearingEventHandler<>());
        disruptor.start();
    }
    public void produce(EventTranslatorVararg<ObjectEvent<String>> eventTranslatorVararg) {
        disruptor.getRingBuffer().publishEvent(eventTranslatorVararg);
    }

    public void produce(EventTranslatorOneArg<ObjectEvent<String>, String> translator, String buffer) {
        disruptor.getRingBuffer().publishEvent(translator, buffer);
    }
}
