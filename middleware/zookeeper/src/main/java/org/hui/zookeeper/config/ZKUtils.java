package org.hui.zookeeper.config;

import lombok.extern.slf4j.Slf4j;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;

@Slf4j
public class ZKUtils {

    private static String address = "127.0.0.1:2181,127.0.0.1:2182,127.0.0.1:2183,127.0.0.1:2184/testConf";

    private static ZooKeeper zk;

    private static CountDownLatch latch =  new CountDownLatch(1);

    private static Watcher watcher = new DefaultWatcher(latch);



    public static ZooKeeper getZK() {

        try {
            zk = new ZooKeeper(address, 1000, watcher);
            latch.await();
        } catch (IOException | InterruptedException e) {
            log.error(e.getMessage());
            e.printStackTrace();
        }

        return zk;
    }

}
