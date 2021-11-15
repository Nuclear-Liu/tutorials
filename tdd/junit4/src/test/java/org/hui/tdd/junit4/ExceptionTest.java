package org.hui.tdd.junit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

/**
 * @author BadMan
 * @since 2021-11-15 12:50
 */
public class ExceptionTest {
    List<Object> list = new ArrayList<>();

    @Test(expected = IndexOutOfBoundsException.class)
    public void test01() {
        new ArrayList<>().get(0);
    }
    @Test
    public void testAssertThrows() {
        IndexOutOfBoundsException thrown = assertThrows(IndexOutOfBoundsException.class, () -> list.add(1, new Object()));
        assertEquals("Index: 1, Size: 0", thrown.getMessage());
        assertTrue(list.isEmpty());
    }
    @Test
    public void testExceptionMessage() {
        try {
            list.get(0);
            fail("Expected an IndexOutOfBoundsException to be thrown");
        } catch (IndexOutOfBoundsException anIndexOutOfBoundsException) {
            assertThat(anIndexOutOfBoundsException.getMessage(), is("Index 0 out of bounds for length 0"));
        }
    }
    @Rule
    public ExpectedException exception = ExpectedException.none();
    @Test
    public void shouldTestExceptionMessage() throws IndexOutOfBoundsException {
        List<Object> list = new ArrayList<>();

        exception.expect(IndexOutOfBoundsException.class);
        exception.expectMessage("Index 0 out of bounds for length 0");
        list.get(0); // execution will never get post this line
    }
}
