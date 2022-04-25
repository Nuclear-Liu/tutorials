package org.hui.java.disruptor;

import com.lmax.disruptor.EventTranslator;
import com.lmax.disruptor.EventTranslatorOneArg;
import com.lmax.disruptor.EventTranslatorTwoArg;
import com.lmax.disruptor.EventTranslatorVararg;
import com.lmax.disruptor.RingBuffer;
import com.lmax.disruptor.dsl.Disruptor;
import com.lmax.disruptor.util.DaemonThreadFactory;

import java.io.IOException;

/**
 * 把事件翻译成消息，用于支持 Lambda 表达式.
 */
public class Main02 {
    public static void main(String[] args) throws IOException {
        // The factory for the event.
        LongEventFactory factory = new LongEventFactory();

        // Specify the size of the ring buffer, must be power of 2.
        int bufferSize = 1024;

        // Construct the Disruptor.
        Disruptor<LongEvent> disruptor = new Disruptor<>(factory, bufferSize, DaemonThreadFactory.INSTANCE);

        // Connect the handler.
        disruptor.handleEventsWith(new LongEventHandler());

        // Start the Disruptor, starts all threads running.
        disruptor.start();

        // Get the ring buffer from the Disruptor to be used for publishing.
        RingBuffer<LongEvent> ringBuffer = disruptor.getRingBuffer();

        // For Lambda expression.
        EventTranslator<LongEvent> translator1 = new EventTranslator<>() {
            @Override
            public void translateTo(LongEvent event, long sequence) {
                event.set(8_888L);
            }
        };
        ringBuffer.publishEvent(translator1);

        EventTranslatorOneArg<LongEvent, Long> translator2 = new EventTranslatorOneArg<LongEvent, Long>() {
            @Override
            public void translateTo(LongEvent event, long sequence, Long arg0) {
                event.set(arg0);
            }
        };
        ringBuffer.publishEvent(translator2, 7_777L);

        EventTranslatorTwoArg<LongEvent, Long, Long> translator3 = new EventTranslatorTwoArg<LongEvent, Long, Long>() {
            @Override
            public void translateTo(LongEvent event, long sequence, Long arg0, Long arg1) {
                event.set(arg0 + arg1);
            }
        };
        ringBuffer.publishEvent(translator3, 10_000L, 10_000L);

        EventTranslatorVararg<LongEvent> translator4 = new EventTranslatorVararg<LongEvent>() {
            @Override
            public void translateTo(LongEvent event, long sequence, Object... args) {
                long result = 0;
                for (Object arg : args) {
                    result += (long) arg;
                }
                event.set(result);
            }
        };
        ringBuffer.publishEvent(translator4, 10_000L, 10_000L, 10_000L, 10_000L);

        System.in.read();
    }
}
