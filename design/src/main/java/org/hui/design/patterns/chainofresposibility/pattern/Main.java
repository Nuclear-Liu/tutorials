package org.hui.design.patterns.chainofresposibility.pattern;

public class Main {
    public static void main(String[] args) {
        ChainHandler handler1 = new Handler1();
        ChainHandler handler2 = new Handler2();
        ChainHandler handler3 = new Handler3();
        handler1.setNextChain(handler2);
        handler2.setNextChain(handler3);

        Request request = new Request("process task ...", RequestType.REQ_HANDLER3);
        handler1.handle(request);
    }
}
