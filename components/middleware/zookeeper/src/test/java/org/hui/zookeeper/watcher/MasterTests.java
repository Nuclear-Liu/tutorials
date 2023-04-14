package org.hui.zookeeper.watcher;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.concurrent.TimeUnit;

@Slf4j
public class MasterTests {
    @Test
    public void testWatcher() throws IOException, InterruptedException {
        log.info("start zk client: {}", LocalDateTime.now());
        Master m = new Master("127.0.0.1:2181");

        m.startZK();

        // warit for a bit
        TimeUnit.SECONDS.sleep(6);
        log.info("closed zk client: {}", LocalDateTime.now());
    }
}
