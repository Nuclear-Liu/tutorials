package org.hui.design.patterns.chainofresposibility.pattern;

public class Handler2 extends ChainHandler {
    @Override
    protected boolean canHandleRequest(Request request) {
        return request.getRequestType() == RequestType.REQ_HANDLER2;
    }

    @Override
    protected void processRequest(Request request) {
        System.out.println("Handler2 is handle request:" + request.getDescription());
    }
}
