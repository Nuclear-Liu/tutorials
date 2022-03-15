package org.hui.zookeeper.config;

import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;

import java.util.concurrent.CountDownLatch;

public class DefaultWatcher implements Watcher {

    private final CountDownLatch latch;

    public DefaultWatcher(CountDownLatch latch) {
        this.latch = latch;
    }

    @Override
    public void process(WatchedEvent event) {

        System.out.println(event.toString());

        switch (event.getState()) {
            case Disconnected:
                break;
            case SyncConnected:
                latch.countDown();
                break;
            case AuthFailed:
                break;
            case ConnectedReadOnly:
                break;
            case SaslAuthenticated:
                break;
            case Expired:
                break;
            case Closed:
                break;
        }

    }
}
