package org.hui.tdd.junit4;

import org.junit.Rule;
import org.junit.Test;

import java.util.logging.Logger;

/**
 * @author Hui.Liu
 * @since 2021-11-19 13:20
 */
public class MyLoggerTest {
    @Rule
    public final TestLogger logger = new TestLogger();

    @Test
    public void checkOutMyLogger() {
        final Logger log = logger.getLogger();
        log.warning("Your test is showing");
    }
}
