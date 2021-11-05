package org.hui.design.patterns.chainofresposibility.pattern;

public class Handler1 extends ChainHandler {
    @Override
    protected boolean canHandleRequest(Request request) {
        return request.getRequestType() == RequestType.REQ_HANDLER1;
    }

    @Override
    protected void processRequest(Request request) {
        System.out.println("Handler1 is handle request:" + request.getDescription());
    }
}
