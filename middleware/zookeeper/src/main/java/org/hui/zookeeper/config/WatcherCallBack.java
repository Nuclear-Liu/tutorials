package org.hui.zookeeper.config;

import lombok.extern.slf4j.Slf4j;
import org.apache.zookeeper.AsyncCallback;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.Stat;

import java.nio.charset.StandardCharsets;
import java.util.concurrent.CountDownLatch;

@Slf4j
public class WatcherCallBack implements Watcher, AsyncCallback.StatCallback, AsyncCallback.DataCallback {

    private CountDownLatch latch = new CountDownLatch(1);

    private final ZooKeeper zk;

    private final MyConf myConf;

    public WatcherCallBack(ZooKeeper zk, MyConf myConf) {
        this.zk = zk;
        this.myConf = myConf;
    }

    @Override
    public void process(WatchedEvent event) {

        switch (event.getType()) {
            case None:
                break;
            case NodeCreated:
                zk.getData("/AppConf",this,this,"sdfs");
                break;
            case NodeDeleted:
                // 容忍性
                myConf.setConf("");
                latch = new CountDownLatch(1);
                break;
            case NodeDataChanged:
                // 获取更新后的数据
                zk.getData("/AppConf", this, this, "sdfs");
                break;
            case NodeChildrenChanged :
                break;
        }

    }

    @Override
    public void processResult(int rc, String path, Object ctx, byte[] data, Stat stat) {

        if (data != null) {
            String conf = new String(data, StandardCharsets.UTF_8);
            myConf.setConf(conf);
            latch.countDown();
        }

    }

    @Override
    public void processResult(int rc, String path, Object ctx, Stat stat) {
        log.info("exec call back");
        if (stat != null) {
            zk.getData("/AppConf", this, this, "sdfs");
        }
    }

    public void await() {
        // 检查目录是否存在
        zk.exists("/AppConf", this, this, "ABC");
        try {
            latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
