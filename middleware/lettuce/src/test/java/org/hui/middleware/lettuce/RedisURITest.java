package org.hui.middleware.lettuce;

import io.lettuce.core.RedisClient;
import io.lettuce.core.RedisURI;
import io.lettuce.core.api.StatefulRedisConnection;
import io.lettuce.core.api.sync.RedisCommands;
import org.junit.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * @author Hui.Liu
 * @since 2022-02-18 10:53
 */
public class RedisURITest {
    @Test
    public void test() {
        RedisURI redisURI = RedisURI.Builder.redis("localhost", 6379).withDatabase(0).build();
        RedisClient redisClient = RedisClient.create(redisURI);
//        RedisClient redisClient = RedisClient.create("redis://127.0.0.1:6379/");

        StatefulRedisConnection<String, String> connection = redisClient.connect();
        LocalDateTime before = LocalDateTime.now();
        RedisCommands<String, String> syncCommands = connection.sync();

        for (int i = 0; i < 10000; i++ ) {
            syncCommands.set("key" + i, "Hello, Redis!" + " index:" + i);
        }

        connection.close();
        redisClient.shutdown();
        LocalDateTime after = LocalDateTime.now();
        System.out.println(before);
        System.out.println(after);
    }
}
