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

    @Before
    public void instantiate() throws Exception {
        controller = new DefaultController();
    }
    @Test
    public void testAddHandler() {
        Request request = new SampleRequest();
        RequestHandler handler = new SampleHandler();
        controller.addHandler(request, handler);
        RequestHandler handler2 = controller.getHandler(request);
        assertSame("Handler we set in controller should be the same handler we get", handler2, handler);
    }
    @Test
    public void testProcessRequest() {
        Request request = new SampleRequest();
        RequestHandler handler = new SampleHandler();
        controller.addHandler(request, handler);
        Response response = controller.processRequest(request);
        assertNotNull("Must not return a null response", response);
        assertEquals("Response should be of type SampleResponse", SampleResponse.class, response.getClass());
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
    }

    private class SampleHandler implements RequestHandler{
        @Override
        public Response process(Request request) throws Exception {
            return new SampleResponse();
        }
    }
}