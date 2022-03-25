package org.hui.middleware.lettuce;

import io.lettuce.core.RedisClient;
import io.lettuce.core.api.StatefulRedisConnection;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

/**
 * @author BadMan
 */
@Slf4j
public class ConnectToRedisTests {

    /**
     * Standalone Syntax redis://[[username:]password@]host[:port][/database][?[timeout=timeout[d|h|m|s|ms|us|ns]]
     */
    final String standaloneUri = "redis://127.0.0.1:6379/0";

    @Test
    public void testStandaloneConnect() {
        RedisClient redisClient = RedisClient.create(standaloneUri);
        StatefulRedisConnection<String, String> connection = redisClient.connect();

        log.info("Connected to Redis");

        connection.close();
        redisClient.shutdown();
    }
}
