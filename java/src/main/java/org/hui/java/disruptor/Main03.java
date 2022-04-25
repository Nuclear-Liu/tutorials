package org.hui.java.disruptor;

import com.lmax.disruptor.RingBuffer;
import com.lmax.disruptor.dsl.Disruptor;
import com.lmax.disruptor.util.DaemonThreadFactory;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

@Slf4j
public class Main03 {
    public static void main(String[] args) throws IOException {
        // Specify the size of the ring buffer, must be power of 2.
        int bufferSize = 1024;

        // Construct the Disruptor.
        Disruptor<LongEvent> disruptor = new Disruptor<>(LongEvent::new, bufferSize, DaemonThreadFactory.INSTANCE);

        // Connect the handler.
        disruptor.handleEventsWith((event, sequence, endOfBatch) -> log.info("event: {} sqeuence: {} endOfBatch: {}", event.toString(), sequence, endOfBatch));

        // Start the Disruptor, starts all threads running.
        disruptor.start();

        // Get the ring buffer from the Disruptor to be used for publishing.
        RingBuffer<LongEvent> ringBuffer = disruptor.getRingBuffer();

        ringBuffer.publishEvent((event, sequence) -> event.set(10_000L));

        ringBuffer.publishEvent((event, sequence, l) -> event.set(l), 10_000L);
        ringBuffer.publishEvent((event, sequence, l1, l2) -> event.set(l1 + l2), 10_000L, 10_000L);

        System.in.read();
    }
}
