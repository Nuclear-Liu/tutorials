package org.hui.tdd.junit4.demo;

import org.junit.Before;
import org.junit.Ignore;
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
        controller.addHandler(request, handler);
    }
    @Test
    public void testAddHandler() {
        RequestHandler handler2 = controller.getHandler(request);
        assertSame("Handler we set in controller should be the same handler we get", handler2, handler);
    }
    @Test
    public void testProcessRequest() {
        Response response = controller.processRequest(request);
        assertNotNull("Must not return a null response", response);
        assertEquals("Response should be of type SampleResponse", new SampleResponse(), response);
    }
    @Test
    public void testProcessRequestAnswersResponse() {
        SampleRequest request = new SampleRequest("testError");
        SampleExceptionHandler handler = new SampleExceptionHandler();
        controller.addHandler(request, handler);
        Response response = controller.processRequest(request);

        assertNotNull("Must not return a nul response", response);
        assertEquals(ErrorResponse.class, response.getClass());
    }
    @Test(expected = RuntimeException.class)
    public void testGetHandlerNotDefined() {
        Request request = new SampleRequest("testNotDefined");

        // The following line is supposed to throw a RuntimeException
        controller.getHandler(request);
    }
    @Test(expected = RuntimeException.class)
    public void testAddRequestDuplicateName() {
        Request request = new SampleRequest();
        RequestHandler handler = new SampleHandler();

        // The following line is supposed to throw a RuntimeException
        controller.addHandler(request, handler);
    }
    @Test(timeout = 130)
    @Ignore(value = "Ignore for now until we decide a decent time-limit")
    public void testProcessMultipleRequestTimeout() {
        Request request;
        Response response = new SampleResponse();
        RequestHandler handler = new SampleHandler();

        for (int i = 0; i < 99999; i++) {
            request = new SampleRequest(String.valueOf(i));
            controller.addHandler(request, handler);
            response = controller.processRequest(request);
            assertNotNull(response);
            assertNotSame(ErrorResponse.class, response.getClass());
        }
    }

    private class SampleRequest implements Request {
        private static final String DEFAULT_NAME = "Test";
        private String name;

        public SampleRequest(String name) {
            this.name = name;
        }
        public SampleRequest() {
            this.name = DEFAULT_NAME;
        }
        @Override
        public String getName() {
            return name;
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

    private class SampleExceptionHandler implements RequestHandler {
        @Override
        public Response process(Request request) throws Exception {
            throw  new Exception("error processing request");
        }
    }
}