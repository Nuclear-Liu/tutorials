package org.hui.middleware.lettuce;

import io.lettuce.core.RedisClient;
import io.lettuce.core.api.StatefulRedisConnection;
import io.lettuce.core.api.sync.RedisCommands;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

/**
 * @author BadMan
 */
@Slf4j
public class RedisURITest {
    final String uri = "redis://127.0.0.1:6379/0";

    @Test
    public void test() {
        RedisClient redisClient = RedisClient.create(uri);
        StatefulRedisConnection<String, String> connection = redisClient.connect();
        RedisCommands<String, String> syncCommands = connection.sync();

        syncCommands.set("key", "Hello, Redis!");

        log.info("add <key, \"Hello, Redis!\">");

        syncCommands.del("key");

        log.info("del key");

        connection.close();
        redisClient.shutdown();
    }
}
