package org.hui.patterns.strategy;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;

@Component("STRATEGY_TWO")
public class StrategyTwo implements IStrategy {
    private static final Logger LOGGER = LogManager.getLogger();
    @Override
    public void execute() {
        LOGGER.info("execute: {}", this.getClass().getSimpleName());
    }
}
