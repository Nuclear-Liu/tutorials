package org.hui.tdd.junit4.jetty;

import org.junit.BeforeClass;
import org.junit.Test;

import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLStreamHandler;
import java.net.URLStreamHandlerFactory;

import static org.junit.Assert.assertEquals;

/**
 * @author Hui.Liu
 * @since 2021-12-04 16:29
 */
public class WebClientTest1 {

    @BeforeClass
    public static void setUp() {
        WebClientTest1 t = new WebClientTest1();

        URL.setURLStreamHandlerFactory(t.new StubSteamHandlerFactor());
    }

    @Test
    public void testGetContentOk() throws Exception {
        WebClient client = new WebClient();
        String result = client.getContent(new URL("http://localhost/"));
        assertEquals("It works", result);
    }

    private class StubSteamHandlerFactor implements URLStreamHandlerFactory {
        @Override
        public URLStreamHandler createURLStreamHandler(String protocol) {
            return new StubHttpURLStreamHandler();
        }
    }

    private class StubHttpURLStreamHandler extends URLStreamHandler {
        @Override
        protected URLConnection openConnection(URL u) throws IOException {
            return new StubHttpURLConnection(u);
        }
    }
}
