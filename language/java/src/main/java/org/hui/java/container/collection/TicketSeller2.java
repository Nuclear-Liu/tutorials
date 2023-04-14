package org.hui.java.container.collection;

import ch.qos.logback.core.util.TimeUtil;
import lombok.extern.slf4j.Slf4j;
import org.hui.java.concurrencyprogramming.SleepHelper;

import java.util.Vector;
import java.util.concurrent.TimeUnit;

@Slf4j
public class TicketSeller2 {
    static Vector<String> tickets = new Vector<>();

    static {
        for (int i = 0; i < 1000; i++) {
            tickets.add("票编号：" + i);
        }
    }

    public static void main(String[] args) {
        for (int i = 0; i < 10; i++) {
            new Thread(() -> {
                while (tickets.size() > 0) {
                    try {
                        TimeUnit.MILLISECONDS.sleep(10);
                    } catch (InterruptedException e) {
                        log.info("thread interrupted.", e);
                    }
                    log.info("销售了: {}", tickets.remove(0));
                }
            }).start();
        }
    }
}
