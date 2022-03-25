package org.hui.middleware.lettuce;

import io.lettuce.core.LettuceFutures;
import io.lettuce.core.RedisClient;
import io.lettuce.core.RedisFuture;
import io.lettuce.core.api.StatefulRedisConnection;
import io.lettuce.core.api.async.RedisAsyncCommands;
import io.lettuce.core.api.sync.RedisCommands;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.function.Consumer;
import java.util.function.Function;

/**
 * @author Hui.Liu
 */
@Slf4j
public class RedisURITest {
    private static final String uri = "redis://127.0.0.1:6379/0";
    private static RedisClient redisClient;
    private static StatefulRedisConnection<String, String> connection;

    @BeforeAll public static void init() {
        redisClient = RedisClient.create(uri);
        connection = redisClient.connect();
    }

    @Test public void test() {
        RedisCommands<String, String> syncCommands = connection.sync();

        syncCommands.set("key", "Hello, Redis!");

        log.info("add <key, \"Hello, Redis!\">");

        syncCommands.del("key");

        log.info("del key");

    }

    @Test public void test2() throws ExecutionException, InterruptedException {
        RedisAsyncCommands<String, String> commands = connection.async();
        RedisFuture<String> future = commands.get("key");
        String value = future.get();
        log.info("key: {}", value);
    }

    @Test public void test3() throws ExecutionException, InterruptedException, TimeoutException {
        RedisAsyncCommands<String, String> commands = connection.async();
        RedisFuture<String> future = commands.get("key");
        String value = future.get(1, TimeUnit.MINUTES);
        log.info("key: {}", value);
    }

    @Test public void test4() {
        RedisAsyncCommands<String, String> commands = connection.async();
        RedisFuture<String> future = commands.get("key");

        future.thenAccept(new Consumer<String>() {
            @Override
            public void accept(String s) {
                log.info("key: {}", s);
            }
        });
    }

    @Test public void test4Lambda() {
        RedisAsyncCommands<String, String> commands = connection.async();
        RedisFuture<String> future = commands.get("key");
        future.thenAccept((var)->{
            log.info("key: {}", var);
        });
    }

    @Test public void test5() {
        Executor sharedExecutor = ForkJoinPool.commonPool();

        RedisAsyncCommands<String, String> commands = connection.async();
        RedisFuture<String> future = commands.get("key");

        future.thenAcceptAsync((var)-> {
            log.info("key: {}", var);
        }, sharedExecutor);
        log.info("main thread exec finnish.");
    }

    @Test public void test6() {
        RedisAsyncCommands<String, String> commands = connection.async();
        List<RedisFuture<String>> futures = new ArrayList<>();

        for (int i = 0; i < 10; i++) {
            futures.add(commands.set("key-" + i, "value-" + i));
        }
        LettuceFutures.awaitAll(1, TimeUnit.MINUTES, futures.toArray(new RedisFuture[futures.size()]));
    }

    @Test public void test7() {
        RedisAsyncCommands<String, String> commands = connection.async();
        RedisFuture<String> future = commands.get("key");
        while (!future.isDone()) {
            log.info("do something.");
        }
    }

    @Test public void test8() {
        RedisAsyncCommands<String, String> commands = connection.async();
        RedisFuture<String> future = commands.get("key");
        future.thenApply(new Function<String, Integer>() {
            @Override
            public Integer apply(String s) {
                log.info("call aooly. arg: {}", s);
                return s.length();
            }
        }).thenAccept(new Consumer<Integer>() {
            @Override
            public void accept(Integer integer) {
                log.info("Got value: {}", integer);
            }
        });
    }

    @Test public void async() throws ExecutionException, InterruptedException {
        CompletableFuture<String> future = new CompletableFuture<>();

        log.info("Current state: {}", future.isDone());

        future.complete("my value");

        log.info("Current state: {}", future.isDone());
        log.info("Got value: {}", future.get());
    }

    @Test public void async2() {
        final CompletableFuture<String> future = new CompletableFuture<>();

        future.thenRun(new Runnable() {
            @Override
            public void run() {
                try {
                    log.info("Got value: {}", future.get());
                } catch (InterruptedException | ExecutionException e) {
                    log.error("future then run exception.", e);
                }
            }
        });

        log.info("Current state: {}", future.isDone());
        future.complete("my value");
        log.info("Current state: {}", future.isDone());
    }

    @Test public void async3() {
        CompletableFuture<String> future = new CompletableFuture<>();

        future.thenAccept(new Consumer<String>() {
            @Override
            public void accept(String s) {
                log.info("Got value: {}", s);
            }
        });

        log.info("Current state: {}", future.isDone());
        future.complete("my value");
        log.info("Current state: {}", future.isDone());
    }

    @AfterAll public static void destory() {
        connection.close();
        redisClient.shutdown();
    }
}
