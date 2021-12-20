package org.hui.tdd.junit4;

import org.junit.AssumptionViolatedException;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestRule;
import org.junit.rules.TestWatcher;
import org.junit.runner.Description;
import org.junit.runners.model.Statement;

import static org.junit.Assert.fail;

/**
 * @author Hui.Liu
 * @since 2021-11-18 23:27
 */
public class WatchmanTest {
    private static String watchedLog;

    @Rule
    public final TestRule  watchman = new TestWatcher() {
        @Override
        public Statement apply(Statement base, Description description) {
            return super.apply(base, description);
        }

        @Override
        protected void succeeded(Description description) {
            watchedLog += description.getDisplayName() + " " + "success!\n";
        }

        @Override
        protected void failed(Throwable e, Description description) {
            watchedLog += description.getDisplayName() + " " + e.getClass().getSimpleName() + "\n";
        }

        @Override
        protected void skipped(AssumptionViolatedException e, Description description) {
            watchedLog += description.getDisplayName() + " " + e.getClass().getSimpleName() + "\n";
        }

        @Override
        protected void finished(Description description) {
            super.finished(description);
        }
    };

    @Test
    @Ignore
    public void fails() {
        fail();
    }

    @Test
    public void succeeds() {
    }
}
