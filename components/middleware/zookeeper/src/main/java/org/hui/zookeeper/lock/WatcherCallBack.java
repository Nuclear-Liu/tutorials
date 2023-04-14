package org.hui.zookeeper.lock;

import lombok.extern.slf4j.Slf4j;
import org.apache.zookeeper.AsyncCallback;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooDefs;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.Stat;

import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CountDownLatch;

@Slf4j
public class WatcherCallBack implements Watcher, AsyncCallback.StringCallback, AsyncCallback.Children2Callback, AsyncCallback.StatCallback {

    private final ZooKeeper zk;

    private final String threadName;

    private CountDownLatch latch = new CountDownLatch(1);

    private String pathName;

    public String getThreadName() {
        return threadName;
    }

    public WatcherCallBack(ZooKeeper zk, String threadName) {
        this.zk = zk;
        this.threadName = threadName;
    }

    public void tryLock() {
//        zk.getData("/", false, )
        try {
            // 实现锁
            zk.create("/lock", threadName.getBytes(StandardCharsets.UTF_8), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL_SEQUENTIAL, this, "lock");

            latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void unLock() {
        try {
            log.info(threadName + "pathName: " + pathName + " is unlock");
            zk.delete(pathName, -1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (KeeperException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void process(WatchedEvent event) {
        switch (event.getType()) {
            case NodeDeleted:
                zk.getChildren("/", false, this, "lock");
                break;
        }
    }

    @Override
    public void processResult(int rc, String path, Object ctx, String name) {
        if (name != null) {
            // 节点创建成功
            log.info(threadName + " ephemeral sequential node " + name);
            pathName = name;
            zk.getChildren("/", false, this, "lock");
        }
    }

    // getChildren call back
    @Override
    public void processResult(int rc, String path, Object ctx, List<String> children, Stat stat) {
        // 一定能看到自己前边的节点
//        for (String child : children) {
//            log.info(threadName + " get child " + child);
//        }
        Collections.sort(children);
        int index = children.indexOf(pathName.substring(1));
        // 判断自己是不是第一个
        if (index == 0) {
            log.info(threadName + " get lock");
            try {
                zk.setData("/", pathName.getBytes(StandardCharsets.UTF_8), -1);
            } catch (KeeperException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            latch.countDown();
        } else {
            zk.exists("/" + children.get(index - 1), this, this, "lock");
        }
    }

    @Override
    public void processResult(int rc, String path, Object ctx, Stat stat) {
        //
    }
}
