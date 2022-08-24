package org.hui.java.concurrencyprogramming.example.completablefuture;

import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

@Slf4j
public class StreamAsync {
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
            ips.add("192.168.0."+i);
        }

        // 2. 发起广播调用
        long start = System.currentTimeMillis();
        List<CompletableFuture<String>> futures = ips.stream().map(ip -> CompletableFuture.supplyAsync(() -> rpcCall(ip, ip))).collect(Collectors.toList());
        List<String> results = futures.stream().map(future -> future.join()).collect(Collectors.toList());
        results.forEach(System.out::println);

        log.info("cost:{}",(System.currentTimeMillis()-start));
    }
}
