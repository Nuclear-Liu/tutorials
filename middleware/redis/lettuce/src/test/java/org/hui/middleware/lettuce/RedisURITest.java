package org.hui.middleware.lettuce;

import io.lettuce.core.RedisClient;
import io.lettuce.core.api.StatefulRedisConnection;
import io.lettuce.core.api.sync.RedisCommands;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

/**
 * @author BadMan
 */
@Slf4j
public class RedisURITest {
    private static final String uri = "redis://127.0.0.1:6379/0";
    private static RedisClient redisClient;
    private static StatefulRedisConnection<String, String> connection;

    @BeforeAll
    public static void init() {
        redisClient = RedisClient.create(uri);
        connection = redisClient.connect();
    }

    @Test
    public void test() {
        RedisCommands<String, String> syncCommands = connection.sync();

        syncCommands.set("key", "Hello, Redis!");

        log.info("add <key, \"Hello, Redis!\">");

        syncCommands.del("key");

        log.info("del key");

    }

    @AfterAll
    public static void destory() {
        connection.close();
        redisClient.shutdown();
    }
}
