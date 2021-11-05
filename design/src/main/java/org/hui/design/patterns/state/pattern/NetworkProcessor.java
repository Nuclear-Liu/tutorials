package org.hui.design.patterns.state.pattern;

public class NetworkProcessor {
    private NetworkState state;
    public NetworkProcessor(NetworkState state) {
        this.state = state;
    }
    public void operation1() {
        state.operation1();
        state = state.next;
    }
    public void operation2() {
        state.operation2();
        state = state.next;
    }
    public void operation3() {
        state.operation3();
        state = state.next;
    }
}
