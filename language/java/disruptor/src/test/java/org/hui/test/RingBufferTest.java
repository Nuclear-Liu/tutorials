package org.hui.test;

import com.lmax.disruptor.RingBuffer;
import com.lmax.disruptor.dsl.Disruptor;
import com.lmax.disruptor.util.DaemonThreadFactory;
import org.junit.jupiter.api.Test;

class RingBufferTest {
    @Test
    void test() {
        Disruptor<String> disruptor = new Disruptor<>(String::new,1024, DaemonThreadFactory.INSTANCE);
        RingBuffer<String> ringBuffer = disruptor.start();
        System.out.println(ringBuffer.getMinimumGatingSequence());
    }
}
