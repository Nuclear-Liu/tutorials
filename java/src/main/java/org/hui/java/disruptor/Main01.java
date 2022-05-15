package org.hui.java.disruptor;

import com.lmax.disruptor.RingBuffer;
import com.lmax.disruptor.dsl.Disruptor;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.Executors;

@Slf4j
public class Main01 {
    public static void main(String[] args) {
        // The factory for the event.
        LongEventFactory factory = new LongEventFactory();

        // Specify the size of the ring buffer, must be power of 2.
        int bufferSize=1024;

        // Construct the Disruptor.
        Disruptor<LongEvent> disruptor = new Disruptor<>(factory,bufferSize, Executors.defaultThreadFactory());

        // Connect the handler.
        disruptor.handleEventsWith(new LongEventHandler());

        // Start the Disruptor, starts all threads running.
        disruptor.start();

        // Get the ring buffer from the Disruptor to be used for publishing.
        RingBuffer<LongEvent> ringBuffer = disruptor.getRingBuffer();

        // demo.
        long sequence = ringBuffer.next(); // Grab the next sequence.
        try {
            LongEvent event = ringBuffer.get(sequence); // Get the entry in the Disruptor for the sequence.
            event.set(8_888L);
        }
        finally {
            ringBuffer.publish(sequence);
        }
    }
}