package org.hui.feature;

import com.lmax.disruptor.EventFactory;
import com.lmax.disruptor.EventPoller;
import com.lmax.disruptor.RingBuffer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Arrays;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

public class PullWithBatchedPollerFeature {
    private static final Logger LOGGER = LogManager.getLogger();

    public static void main(final String[] args) throws Exception {
        int batchSize = 20;
        RingBuffer<DataEvent<Object>> ringBuffer =
                RingBuffer.createMultiProducer(DataEvent.factory(), 1024);

        // 模拟生产
        CompletableFuture.runAsync(() -> {
            for (int i = 0; i < Integer.MAX_VALUE; ) {
                if (ringBuffer.tryPublishEvent((event, sequence, arg0) -> {
                    event.set(arg0);
                }, String.valueOf(i))) {
                    i++;
                }
            }
        });

        BatchedPoller<Object> poller = new BatchedPoller<>(ringBuffer, batchSize);

        // 模拟消费
        while (true) {
            Object[] value = poller.poll();

            // value could be null if no events are available.
            if (null != value) {
                // process value.
                LOGGER.info("consume size:{} context: {}", value.length, value);
            }
            TimeUnit.SECONDS.sleep(1);
        }
    }
}

class BatchedPoller<T> {
    private final EventPoller<DataEvent<T>> poller;
    private final BatchedData<T> polledData;

    BatchedPoller(final RingBuffer<DataEvent<T>> ringBuffer, final int batchSize) {
        this.poller = ringBuffer.newPoller();
        ringBuffer.addGatingSequences(poller.getSequence());
        this.polledData = new BatchedData<>(batchSize);
    }

    public T[] poll() throws Exception {
        if (polledData.getMsgCount() > 0) {
            return polledData.pollMessage(); // 我们只是从本地获取了
        }

        EventPoller.PollState pollState = loadNextValues(poller, polledData);// 我们尝试从环缓冲中加载

        return polledData.getMsgCount() > 0 ? polledData.pollMessage() : null;
    }

    private EventPoller.PollState loadNextValues(final EventPoller<DataEvent<T>> poller, final BatchedData<T> batch)
            throws Exception {
        return poller.poll((event, sequence, endOfBatch) ->
        {
            T item = event.copyOfData();
            return item != null ? batch.addDataItem(item) : false;
        });
    }
}

class BatchedData<T> {
    private int msgHighBound;
    private final int capacity;
    private final T[] data;
    private int cursor;

    @SuppressWarnings("unchecked")
    BatchedData(final int size) {
        this.capacity = size;
        data = (T[]) new Object[this.capacity];
    }

    private void clearCount() {
        msgHighBound = 0;
        cursor = 0;
    }

    public int getMsgCount() {
        return msgHighBound - cursor;
    }

    public boolean addDataItem(final T item) throws IndexOutOfBoundsException {
        if (msgHighBound >= capacity) {
            throw new IndexOutOfBoundsException("Attempting to add item to full batch");
        }

        data[msgHighBound++] = item;
        return msgHighBound < capacity;
    }

    public T[] pollMessage() {
        T[] rtVal = null;
        if (cursor < msgHighBound) {
            rtVal = Arrays.copyOfRange(data, cursor, msgHighBound);
            clearCount();
        }
//        if (cursor > 0 && cursor >= msgHighBound) {
//            clearCount();
//        }
        return rtVal;
    }
}

class DataEvent<T> {
    T data;

    public static <T> EventFactory<DataEvent<T>> factory() {
        return DataEvent::new;
    }

    public T copyOfData() {
        // Copy the data out here. In this case we have a single reference
        // object, so the pass by
        // reference is sufficient. But if we were reusing a byte array,
        // then we
        // would need to copy
        // the actual contents.
        return data;
    }

    void set(final T d) {
        data = d;
    }
}