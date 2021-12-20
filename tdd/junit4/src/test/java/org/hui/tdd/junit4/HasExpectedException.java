package org.hui.tdd.junit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static org.hamcrest.CoreMatchers.startsWith;

/**
 * @author Hui.Liu
 * @since 2021-11-19 0:12
 */
public class HasExpectedException {
    @Rule
    public final ExpectedException thrown = ExpectedException.none();

    @Test
    public void throwsNoting() {

    }

    @Test
    public void throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        throw new NullPointerException();
    }

    @Test
    public void throwsNullPointerExceptionWithMessage() {
        thrown.expect(NullPointerException.class);
        thrown.expectMessage("happened?");
        thrown.expectMessage(startsWith("What"));
        throw new NullPointerException("What happened?");
    }
}
