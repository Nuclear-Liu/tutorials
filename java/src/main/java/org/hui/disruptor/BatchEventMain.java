package org.hui.disruptor;

import com.lmax.disruptor.BatchEventProcessor;
import com.lmax.disruptor.BlockingWaitStrategy;
import com.lmax.disruptor.NanosecondPauseBatchRewindStrategy;
import com.lmax.disruptor.RingBuffer;
import com.lmax.disruptor.dsl.Disruptor;
import com.lmax.disruptor.dsl.ProducerType;
import com.lmax.disruptor.util.DaemonThreadFactory;
import org.hui.disruptor.longevent.LongEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.ByteBuffer;

public class BatchEventMain {
    private static final Logger LOGGER = LoggerFactory.getLogger(BatchEventMain.class);

    public static void main(String[] args) throws InterruptedException {
        int bufferSize = 1024;

        Disruptor<LongEvent> disruptor = new Disruptor<>(LongEvent::new, bufferSize, DaemonThreadFactory.INSTANCE, ProducerType.SINGLE, new BlockingWaitStrategy());

        EarlyReleaseHandler earlyReleaseHandler = new EarlyReleaseHandler();
        earlyReleaseHandler.onBatchStart(25L);

        disruptor.handleEventsWith(earlyReleaseHandler);

        // disruptor.handleExceptionsFor(earlyReleaseHandler).with(new GenericExceptionHandler());

        disruptor.start();

        RingBuffer<LongEvent> ringBuffer = disruptor.getRingBuffer();

        BatchEventProcessor<LongEvent> batchEventProcessor = new BatchEventProcessor<>(ringBuffer, ringBuffer.newBarrier(), earlyReleaseHandler);
        batchEventProcessor.setRewindStrategy(new NanosecondPauseBatchRewindStrategy(2));

        ByteBuffer buffer = ByteBuffer.allocate(8);
        for (long l = 0; true; l++) {
            buffer.putLong(0, l);

            ringBuffer.publishEvent((event, sequence, arg0) -> event.set(arg0.getLong(0)), buffer);

            LOGGER.info("publish: {}", l);

            // if (l%30 == 0) {
            //     Thread.sleep(1000);
            // }

        }
    }
}
