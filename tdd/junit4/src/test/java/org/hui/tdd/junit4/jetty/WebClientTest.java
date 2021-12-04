package org.hui.tdd.junit4.jetty;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mortbay.jetty.HttpHeaders;
import org.mortbay.jetty.Server;
import org.mortbay.jetty.handler.AbstractHandler;
import org.mortbay.jetty.servlet.Context;
import org.mortbay.util.ByteArrayISO8859Writer;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URL;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

/**
 * @author Hui.Liu
 * @since 2021-12-04 1:14
 */
public class WebClientTest {

    private WebClient client = new WebClient();

    @BeforeClass
    public static void setUp() throws Exception {
        Server server = new Server(8080);

        WebClientTest t = new WebClientTest();

        Context contextOkContent = new Context(server, "/testGetContentOk");
        contextOkContent.setHandler(t.new GetContentOkHandlerTest());

        Context contextNotFoundContext = new Context(server, "/testGetContentNotFound");
        contextNotFoundContext.setHandler(t.new GetContentNotFoundHandlerTest());

        Context contextErrorContext = new Context(server, "/testGetContentError");
        contextErrorContext.setHandler(t.new GetContentServerErrorHandlerTest());

        server.setStopAtShutdown(true);
        server.start();
    }

    @Test
    public void testGetContentOk() throws Exception {
        String result = client.getContent(new URL("http://localhost:8080/testGetContentOk"));
        assertEquals("It works", result);
    }

    @Test
    public void testGetContentNotFound() throws Exception {
        String result = client.getContent(new URL("http://localhost:8080/testGetContentNotFound"));
        assertNull(result);
    }

    @Test
    public void testGetContentError() throws Exception {
        String result = client.getContent(new URL("http://localhost:8080/testGetContentError"));
        assertNull(result);
    }

    @AfterClass
    public static void tearDown() {
        // empty
    }

    private class GetContentOkHandlerTest extends AbstractHandler {
        @Override
        public void handle(String s, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, int i) throws IOException, ServletException {
            OutputStream out = httpServletResponse.getOutputStream();
            ByteArrayISO8859Writer writer = new ByteArrayISO8859Writer();
            writer.write("It works");
            writer.flush();
            httpServletResponse.setIntHeader( HttpHeaders.CONTENT_LENGTH, writer.size() );
            writer.writeTo(out);
            out.flush();
        }
    }

    private class GetContentNotFoundHandlerTest extends AbstractHandler {

        @Override
        public void handle(String s, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, int i) throws IOException, ServletException {
            httpServletResponse.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }

    private class GetContentServerErrorHandlerTest extends AbstractHandler {

        @Override
        public void handle(String s, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, int i) throws IOException, ServletException {
            httpServletResponse.sendError(HttpServletResponse.SC_SERVICE_UNAVAILABLE);
        }
    }
}
