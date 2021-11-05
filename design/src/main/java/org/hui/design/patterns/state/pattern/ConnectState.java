package org.hui.design.patterns.state.pattern;

public class ConnectState extends NetworkState {
    private static NetworkState instance;
    public static NetworkState getInstance() {
        if (instance == null) {
            instance = new ConnectState();
        }
        return instance;
    }
    @Override
    public void operation1() {

    }

    @Override
    public void operation2() {

    }

    @Override
    public void operation3() {

    }
}
