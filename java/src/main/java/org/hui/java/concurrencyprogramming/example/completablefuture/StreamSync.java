package org.hui.java.concurrencyprogramming.example.completablefuture;

import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

@Slf4j
public class StreamSync {
    public static String rpcCall(String ip, String param) {
        log.info("{} rpcCall:{}", ip, param);
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
        results.stream().forEach(log::info);
        log.info("cost: {}", (System.currentTimeMillis() - start));
    }
}
