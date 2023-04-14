package org.hui.zookeeper.watcher;

import lombok.extern.slf4j.Slf4j;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Random;

import static org.apache.zookeeper.ZooDefs.Ids.OPEN_ACL_UNSAFE;

@Slf4j
public class Master implements Watcher {
    private Random random = new Random(this.hashCode());
    private ZooKeeper zk;
    private String hostPort;
    private String serverId = Integer.toHexString(random.nextInt());

    public Master(String hostPort) {
        this.hostPort = hostPort;
    }

    public void startZK() throws IOException {
        zk = new ZooKeeper(hostPort, 15000, this);
    }

    public void runForMaster() throws InterruptedException, KeeperException {
        zk.create("/master",
                serverId.getBytes(StandardCharsets.UTF_8),
                OPEN_ACL_UNSAFE,
                CreateMode.EPHEMERAL);
    }

    @Override
    public void process(WatchedEvent event) {
        log.info(event.toString());
    }

}
