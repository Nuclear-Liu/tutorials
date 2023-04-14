package org.hui.disruptor;

import com.lmax.disruptor.EventHandler;
import com.lmax.disruptor.RewindableException;
import com.lmax.disruptor.Sequence;
import org.hui.disruptor.longevent.LongEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EarlyReleaseHandler implements EventHandler<LongEvent> {
    private static final Logger LOGGER = LoggerFactory.getLogger(EarlyReleaseHandler.class);

    private Sequence sequenceCallBack;
    private int batchRemaining = 20;

    private static boolean state = true;

    @Override
    public void setSequenceCallback(final Sequence sequenceCallback) {
        this.sequenceCallBack = sequenceCallback;
    }

    @Override
    public void onEvent(LongEvent event, long sequence, boolean endOfBatch) throws Exception {
        processEvent(event);

        if (75 == sequence && state) {

            state = false;
            throw new RewindableException(new Throwable("eeee"));
        }

        boolean logicalChunkOfWorkComplete = isLogicalChunkOfWorkComplete();

        if (logicalChunkOfWorkComplete) {
            sequenceCallBack.set(sequence);
        }

        batchRemaining = logicalChunkOfWorkComplete || endOfBatch ? 20 : batchRemaining;
    }

    private boolean isLogicalChunkOfWorkComplete() {
        // Ret true or false based on whatever criteria is required for the smaller chunk.
        // If this is doing I/O, it may be after flushing/syncing to disk or at the end of DB batch+commit.
        // Or it could simply be working off a smaller batch size.
        return --batchRemaining == -1;
    }

    private void processEvent(final LongEvent event) {
        // Do processing.
        LOGGER.info("subscribe: {}", event);

        try {
            Thread.sleep(200);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
