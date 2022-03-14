package org.hui.zookeeper;

import org.apache.zookeeper.*;
import org.apache.zookeeper.data.Stat;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.CountDownLatch;

public class App {
    public static void main(String[] args) throws IOException, InterruptedException, KeeperException {

        CountDownLatch cd = new CountDownLatch(1);

        // Zookeeper 是由 Session 概念的，没有线程池的概念；
        String cluster = "127.0.0.1:2181,127.0.0.1:2182,127.0.0.1:2183,127.0.0.1:2184";
        int sessionTimeout = 3000;
        // watch 分为两类
        // 第一类：在 new Zookeeper() 的时候传入的 watch ，这个 watch 是 Session 级别的；与 path、node 没有关系
        // 第二类：在
        //
        // watch 的注册只发生在 读类型的调用： get exits ...
        ZooKeeper zk = new ZooKeeper(cluster, sessionTimeout, new Watcher() {
            @Override
            public void process(WatchedEvent event) {
                Event.KeeperState state = event.getState();
                Event.EventType type = event.getType();
                String path = event.getPath();
                System.out.println(event.toString());

                switch (state) {
                    case Unknown:
                        break;
                    case Disconnected:
                        break;
                    case NoSyncConnected:
                        break;
                    case SyncConnected:
                        System.out.println("connected");
                        cd.countDown();
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

                switch (type) {
                    case None:
                        break;
                    case NodeCreated:
                        break;
                    case NodeDeleted:
                        break;
                    case NodeDataChanged:
                        break;
                    case NodeChildrenChanged:
                        break;
                }

            }
        });

        cd.await();

        ZooKeeper.States state = zk.getState();
        switch (state) {
            case CONNECTING:
                System.out.println("ing...");
                break;
            case ASSOCIATING:
                break;
            case CONNECTED:
                System.out.println("ed....");
                break;
            case CONNECTEDREADONLY:
                break;
            case CLOSED:
                break;
            case AUTH_FAILED:
                break;
            case NOT_CONNECTED:
                break;
        }

        String pathName = zk.create("/ooxx", "olddata".getBytes(StandardCharsets.UTF_8), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL);

        Stat stat = new Stat();
        byte[] node = zk.getData("/ooxx", new Watcher() {
            @Override
            public void process(WatchedEvent event) {
                System.out.println("getData watch:" + event.toString());
            }
        }, stat);

    }
}
