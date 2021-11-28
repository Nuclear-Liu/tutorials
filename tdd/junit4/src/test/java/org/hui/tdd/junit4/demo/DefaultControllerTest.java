package org.hui.tdd.junit4.demo;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * @author Hui.Liu
 * @since 2021-11-28 1:09
 */
public class DefaultControllerTest {
    private DefaultController controller;
    private Request request;
    private RequestHandler handler;

    @Before
    public void initialize() {
        controller = new DefaultController();
        request = new SampleRequest();
        handler = new SampleHandler();
    }
    @Test
    public void testAddHandler() {
        controller.addHandler(request, handler);
        RequestHandler handler2 = controller.getHandler(request);
        assertSame("Handler we set in controller should be the same handler we get", handler2, handler);
    }
    @Test
    public void testProcessRequest() {
        controller.addHandler(request, handler);
        Response response = controller.processRequest(request);
        assertNotNull("Must not return a null response", response);
        assertEquals("Response should be of type SampleResponse", new SampleResponse(), response);
    }
    @Test
    public void testMethod() {
        throw new RuntimeException("implement me");
    }

    private class SampleRequest implements Request {
        @Override
        public String getName() {
            return "Test";
        }
    }

    private class SampleResponse implements Response {
        private static final String NAME = "Test";
        public String getName() {
            return NAME;
        }

        @Override
        public boolean equals(Object obj) {
            boolean result = false;
            if (obj instanceof SampleResponse) {
                result = ((SampleResponse) obj).getName().equals(getName());
            }
            return result;
        }

        @Override
        public int hashCode() {
            return NAME.hashCode();
        }
    }

    private class SampleHandler implements RequestHandler{
        @Override
        public Response process(Request request) throws Exception {
            return new SampleResponse();
        }
    }
}