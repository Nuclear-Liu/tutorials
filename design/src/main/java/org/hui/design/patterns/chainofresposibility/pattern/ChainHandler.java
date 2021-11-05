package org.hui.design.patterns.chainofresposibility.pattern;

public abstract class ChainHandler {
    private ChainHandler nextChain; // 多态指针 形成多态链表
    private void sendRequestToNextHandler(Request request) {
        if (nextChain != null) {
            nextChain.handle(request);
        }
    }

    /**
     * 判断请求是否可以处理.
     * @param request 请求
     * @return
     */
    protected abstract boolean canHandleRequest(Request request);

    /**
     * 处理请求.
     * @param request
     */
    protected abstract void processRequest(Request request);
    public ChainHandler() {
        nextChain = null;
    }
    public void setNextChain(ChainHandler nextChain) {
        this.nextChain = nextChain;
    }

    /**
     * 职责链的处理逻辑.
     * @param request
     */
    public void handle(Request request) {
        if (canHandleRequest(request)) {
            processRequest(request);
        } else {
            sendRequestToNextHandler(request);
        }
    }
}
