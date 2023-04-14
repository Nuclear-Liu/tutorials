package org.hui.java.concurrencyprogramming.example.completablefuture;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

public class StreamSync {
    public static final Logger LOGGER = LoggerFactory.getLogger(StreamSync.class);
    public static String rpcCall(String ip, String param) {
        LOGGER.info("{} rpcCall:{}", ip, param);
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return param;
    }
    public static void main(String[] args) {
        // 1. 生成 ip 列表
        List<String> ips = new ArrayList<>();

        for (int i = 0; i < 10; i++) {
            ips.add("192.168.0." + i);
        }

        // 2. 发起广播调用
        long start = System.currentTimeMillis();
        List<String> results = new ArrayList<>();
        for (String ip : ips) {
            results.add(rpcCall(ip, ip));
        }

        // 3. 输出
        results.stream().forEach(LOGGER::info);
        LOGGER.info("cost: {}", (System.currentTimeMillis() - start));
    }
}
