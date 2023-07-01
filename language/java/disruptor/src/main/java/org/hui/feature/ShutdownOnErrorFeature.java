package org.hui.feature;

import com.lmax.disruptor.EventHandler;
import com.lmax.disruptor.ExceptionHandler;
import com.lmax.disruptor.RingBuffer;
import com.lmax.disruptor.dsl.Disruptor;
import com.lmax.disruptor.util.DaemonThreadFactory;
import jdk.jfr.Event;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hui.feature.event.StubEvent;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

public class ShutdownOnErrorFeature {
    private static final Logger LOGGER = LogManager.getLogger();
    public static void main(String[] args) throws InterruptedException {
        Disruptor<StubEvent> disruptor = new Disruptor<>(StubEvent.EVENT_FACTORY, 1024, DaemonThreadFactory.INSTANCE);

        AtomicBoolean running = new AtomicBoolean(true);

        ErrorHandler errorHandler = new ErrorHandler(running);

        final Handler handler = new Handler();
        disruptor.handleEventsWith(handler);
        disruptor.handleExceptionsFor(handler).with(errorHandler);
        disruptor.start();

        smarterPublish(disruptor, running);
    }
    private static void simplePublish(final Disruptor<StubEvent> disruptor, final AtomicBoolean running) throws InterruptedException {
        while (running.get()) {
            disruptor.publishEvent(((event, sequence) -> event.setValue((int) sequence)));
        }
    }
    private static void smarterPublish(final Disruptor<StubEvent> disruptor, final AtomicBoolean running) throws InterruptedException {
        final RingBuffer<StubEvent> ringBuffer = disruptor.getRingBuffer();

        boolean publishOk;
        do {
            publishOk = ringBuffer.tryPublishEvent((event, sequence) -> event.setValue((int)sequence));
            TimeUnit.MILLISECONDS.sleep(100);
        }while (publishOk && running.get());
    }
}
class Handler implements EventHandler<StubEvent> {
    private static final Logger LOGGER = LogManager.getLogger();
    @Override
    public void onEvent(StubEvent event, long sequence, boolean endOfBatch) throws Exception {
        // do work, if a failure occurs throw exception.
        LOGGER.info("handler:{}",event);
        if (event.getValue() == 400)
            throw new IllegalStateException("error");
    }
}
final class ErrorHandler implements ExceptionHandler<StubEvent> {
    private static final Logger LOGGER = LogManager.getLogger();
    private final AtomicBoolean running;

    ErrorHandler(AtomicBoolean running) {
        this.running = running;
    }

    @Override
    public void handleEventException(Throwable ex, long sequence, StubEvent event) {
        LOGGER.error("event exception",ex);
        running.set(false);
    }
    @Override
    public void handleOnStartException(Throwable ex) {
        LOGGER.error("on start exception",ex);
        running.set(false);
    }
    @Override
    public void handleOnShutdownException(Throwable ex) {
        LOGGER.error("on shutdown exception",ex);
        running.set(false);
    }
}
