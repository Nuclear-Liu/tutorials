package org.hui.middleware.lettuce.errorhanding;

import io.lettuce.core.RedisClient;
import io.lettuce.core.RedisFuture;
import io.lettuce.core.api.StatefulRedisConnection;
import io.lettuce.core.api.async.RedisAsyncCommands;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.function.BiFunction;
import java.util.function.Consumer;

/**
 * @author Hui.Liu
 * @since 2022-03-25 17:02
 */
@Slf4j
public class ErrorHandingTests {
    private static final String uri = "redis://127.0.0.1:6379";
    private static RedisClient redisClient;
    private static StatefulRedisConnection<String, String> connection;

    @BeforeAll public static void init() {
        redisClient = RedisClient.create(uri);
        connection = redisClient.connect();
    }

    @Test public void test() {
        RedisAsyncCommands<String, String> commands = connection.async();
        RedisFuture<String> future = commands.get("key");

        future.handle(new BiFunction<String, Throwable, String>() {
            @Override
            public String apply(String value, Throwable throwable) {
                log.info("exec handle, arg1 {}, arg2 {}", value, throwable);
                if (throwable != null) {
                    return "default value";
                }
                return value;
            }
        }).thenAccept(new Consumer<String>() {
            @Override
            public void accept(String s) {
                log.info("Got value: {}", s);
            }
        });
    }

    @AfterAll public static void destory() {
        connection.close();
        redisClient.shutdown();
    }
}
