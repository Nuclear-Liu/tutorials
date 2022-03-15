package org.hui.zookeeper.config;


import lombok.extern.slf4j.Slf4j;
import org.apache.zookeeper.ZooKeeper;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.concurrent.TimeUnit;

@Slf4j
public class ConfigTest {

    private ZooKeeper zk;

    @Before
    public void conn () {
        zk = ZKUtils.getZK();
    }

    @After
    public void close () {
        try {
            zk.close();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void getConfig() {

        MyConf myConf = new MyConf();

        WatcherCallBack watcherCallBack = new WatcherCallBack(zk, myConf);

        watcherCallBack.await();

        // 1. 节点不存在；        // 2. 节点存在

        while (true) {
            if (myConf.getConf().equals("")) {
                log.error("conf is delete");
                watcherCallBack.await();
            } else {
                log.info(myConf.getConf());
            }
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

}
