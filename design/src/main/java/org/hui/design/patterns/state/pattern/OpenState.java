package org.hui.design.patterns.state.pattern;

public class OpenState extends NetworkState {
    private static NetworkState instance;
    public static NetworkState getInstance() {
        if (instance == null) {
            instance = new OpenState();
        }
        return instance;
    }
    @Override
    public void operation1() {
        // ...
        next = CloseState.getInstance();
    }

    @Override
    public void operation2() {
        // ...
        next = ConnectState.getInstance();
    }

    @Override
    public void operation3() {
        // ...
        next = OpenState.getInstance();
    }
}
