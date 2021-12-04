package org.hui.tdd.junit4.jetty;

import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import java.net.URL;

import static org.junit.Assert.assertEquals;

/**
 * @author Hui.Liu
 * @since 2021-12-04 0:55
 */
public class WebClientSkeletonTest {

    @Before
    public void setUp() {
        //
    }

    @After
    public void tearDown() {
        // stop jetty
    }

    @Test
    @Ignore(value = "This is just inital skeleton of a test. " + "Therefore is we run it now it will fail.")
    public void testGetContentOk() throws Exception {
        WebClient client = new WebClient();
        String result = client.getContent(new URL("http://localhost:8080/testGetContentOk"));
        assertEquals("It works", result);
    }
}
