package org.hui.tdd.junit4;

import org.junit.rules.TestRule;
import org.junit.runner.Description;
import org.junit.runners.model.Statement;

import java.util.logging.Logger;

/**
 * @author Hui.Liu
 * @since 2021-11-19 13:13
 */
public class TestLogger implements TestRule {
    private Logger logger;

    public Logger getLogger() {
        return this.logger;
    }

    @Override
    public Statement apply(Statement base, Description description) {
        return new Statement() {
            @Override
            public void evaluate() throws Throwable {
                logger = Logger.getLogger(description.getTestClass().getName() + '.' + description.getDisplayName());
                base.evaluate();
            }
        };
    }
}
