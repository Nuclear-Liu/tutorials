package org.hui.tdd.junit4;

import org.junit.Test;

import java.io.File;

import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assume.assumeThat;
import static org.junit.Assume.assumeTrue;

/**
 * @author Hui.Liu
 * @since 2021-11-17 12:49
 */
public class AssumptionsTest {
    @Test
    public void testAssume() {
        assumeThat(File.separatorChar, is('/'));
    }
}
