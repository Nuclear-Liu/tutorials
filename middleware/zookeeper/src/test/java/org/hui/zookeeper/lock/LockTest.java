package org.hui.zookeeper.lock;

import lombok.extern.slf4j.Slf4j;
import org.apache.zookeeper.ZooKeeper;
import org.hui.zookeeper.config.ZKUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.concurrent.TimeUnit;

@Slf4j
public class LockTest {

    private ZooKeeper zk;

    @Before
    public void conn() {
        zk = ZKUtils.getZK();
    }

    @After
    public void close() {
        try {
            zk.close();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testLock() {

        for (int i = 0; i < 10; i++) {
            new Thread(() -> {

                WatcherCallBack watcherCallBack = new WatcherCallBack(zk, Thread.currentThread().getName());

                // 每一个线程：
                // 1. 抢锁
                watcherCallBack.tryLock();
                // 2. 干活
                log.info(Thread.currentThread().getName() + " is working");
//                try {
//                    TimeUnit.SECONDS.sleep(1);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
                // 3. 释放锁
                watcherCallBack.unLock();
            }).start();
        }

        try {
            TimeUnit.SECONDS.sleep(20);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
