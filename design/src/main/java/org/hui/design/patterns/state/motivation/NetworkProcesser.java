package org.hui.design.patterns.state.motivation;

public class NetworkProcesser {
    private NetworkState state;
    public void operation1() {
        if (state == NetworkState.NETWORK_OPEN) {
            // ...
            state = NetworkState.NETWORK_CLOSE;
        } else if (state == NetworkState.NETWORK_CLOSE) {
            // ...
            state = NetworkState.NETWORK_CONNECT;
        } else if (state == NetworkState.NETWORK_CONNECT) {
            // ...
            state = NetworkState.NETWORK_OPEN;
        }
    }
    public void operation2() {
        if (state == NetworkState.NETWORK_OPEN) {
            // ...
            state = NetworkState.NETWORK_CLOSE;
        } else if (state == NetworkState.NETWORK_CLOSE) {
            // ...
            state = NetworkState.NETWORK_CONNECT;
        } else if (state == NetworkState.NETWORK_CONNECT) {
            // ...
            state = NetworkState.NETWORK_OPEN;
        }
    }
}
