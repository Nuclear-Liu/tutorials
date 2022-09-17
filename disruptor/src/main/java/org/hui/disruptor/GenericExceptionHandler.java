package org.hui.disruptor;

import com.lmax.disruptor.BatchRewindStrategy;
import com.lmax.disruptor.ExceptionHandler;
import com.lmax.disruptor.SimpleBatchRewindStrategy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GenericExceptionHandler implements ExceptionHandler<Object> {

    private static final Logger LOGGER = LoggerFactory.getLogger(GenericExceptionHandler.class);

    @Override
    public void handleEventException(Throwable ex, long sequence, Object event) {
        LOGGER.error("handleEventException", ex);

    }

    @Override
    public void handleOnStartException(Throwable ex) {
        LOGGER.error("handleOnStartException", ex);
    }

    @Override
    public void handleOnShutdownException(Throwable ex) {
        LOGGER.error("handleOnStartException", ex);
    }
}
