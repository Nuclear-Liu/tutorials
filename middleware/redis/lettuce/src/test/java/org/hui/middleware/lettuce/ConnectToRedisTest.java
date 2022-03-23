package org.hui.middleware.lettuce;

import io.lettuce.core.RedisClient;
import io.lettuce.core.api.StatefulRedisConnection;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

/**
 * @author BadMan
 */
@Slf4j
public class ConnectToRedisTest {

    /**
     * Syntax: redis://[password@]host[:port][/databaseNumber]
     */
    final String uri = "redis://127.0.0.1:6379/0";

    @Test
    public void testConnect() {
        RedisClient redisClient = RedisClient.create(uri);
        StatefulRedisConnection<String, String> connection = redisClient.connect();

        log.info("Connected to Redis");

        connection.close();
        redisClient.shutdown();
    }
}
