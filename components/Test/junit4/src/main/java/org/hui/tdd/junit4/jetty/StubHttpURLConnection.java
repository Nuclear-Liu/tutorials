package org.hui.tdd.junit4.jetty;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.ProtocolException;
import java.net.URL;

/**
 * @author Hui.Liu
 * @since 2021-12-04 16:36
 */
public class StubHttpURLConnection extends HttpURLConnection {
    private boolean isInput = true;

    /**
     * Constructor for the HttpURLConnection.
     *
     * @param u the URL
     */
    protected StubHttpURLConnection(URL u) {
        super(u);
    }

    @Override
    public InputStream getInputStream() throws IOException {
        if (!isInput) {
            throw new ProtocolException("Cannot read from URLConnection" + " if doInput=false (call setInput(true))");
        }
        ByteArrayInputStream bais = new ByteArrayInputStream(new String("It works").getBytes());
        return bais;
    }

    @Override
    public void disconnect() {

    }

    @Override
    public boolean usingProxy() {
        return false;
    }

    @Override
    public void connect() throws IOException {

    }
}
