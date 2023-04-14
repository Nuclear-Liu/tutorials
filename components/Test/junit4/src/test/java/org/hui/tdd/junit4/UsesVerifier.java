package org.hui.tdd.junit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.Verifier;

import static junit.framework.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.junit.experimental.results.ResultMatchers.isSuccessful;

/**
 * @author Hui.Liu
 * @since 2021-11-18 13:09
 */
public class UsesVerifier {
    private static String sequence;

    @Rule
    public final Verifier collector = new Verifier() {
        @Override
        protected void verify() throws Throwable {
            sequence += "verify";
        }
    };

    @Test
    public void example() {
        sequence += "test ";
    }

    @Test
    public void verifierRunsAfterTest() {
        sequence = "";
//        assertThat(example(), isSuccessful());
//        assertEquals("test verify ", sequence);
    }
}
