package org.hui.design.patterns.state.pattern;

public abstract class NetworkState {
    public NetworkState next;
    public abstract void operation1();
    public abstract void operation2();
    public abstract void operation3();
}
