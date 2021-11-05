package org.hui.design.patterns.chainofresposibility.pattern;

public class Handler3 extends ChainHandler {
    @Override
    protected boolean canHandleRequest(Request request) {
        return request.getRequestType() == RequestType.REQ_HANDLER3;
    }

    @Override
    protected void processRequest(Request request) {
        System.out.println("Handler3 is handle request:" + request.getDescription());
    }
}
