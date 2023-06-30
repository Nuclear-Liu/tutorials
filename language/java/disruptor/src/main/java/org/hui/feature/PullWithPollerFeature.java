package org.hui.feature;

import com.lmax.disruptor.EventPoller;
import com.lmax.disruptor.RingBuffer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hui.feature.event.StubEvent;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

public class PullWithPollerFeature {
    private static final Logger LOGGER = LogManager.getLogger();

    public static void main(String[] args) throws Exception {
        /**
         * 使用默认等待策略 {@link com.lmax.disruptor.BlockingWaitStrategy} 创建新的多生产者 RingBuffer
         */
        RingBuffer<StubEvent> ringBuffer = RingBuffer.createMultiProducer(StubEvent.EVENT_FACTORY, 1024);

        // 模拟生产
        CompletableFuture.runAsync(() -> {
            for (int i = 0; i < Integer.MAX_VALUE; i++) {
                ringBuffer.publishEvent((event, sequence, arg0) -> event.setValue(arg0), i);
                try {
                    TimeUnit.MILLISECONDS.sleep(100);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        });

        final EventPoller<StubEvent> poller = ringBuffer.newPoller();

        // 模拟消费
        while (true) {

            Object value = getNextValue(poller);

            // Value could be null if no events are available.
            if (null != value) {
                // process value.
                LOGGER.info("{}", value);
            }
            TimeUnit.SECONDS.sleep(2);
        }
    }

    private static Object getNextValue(final EventPoller<StubEvent> poller) throws Exception {
        final Object[] out = new Object[1024];

        /**
         * lambda 表达式返回 false 表示一次只处理一个事件；
         * 返回 true lambda 将会被调用多次，处理一个批次事件
         */
        poller.poll((event, sequence, endOfBatch) -> {
            out[0] = event.getValue();
            LOGGER.info("------:{}",out[0]);
            // Return false so that only one event is processed at a time.
            return false;
        });

        return out[0];
    }
}
