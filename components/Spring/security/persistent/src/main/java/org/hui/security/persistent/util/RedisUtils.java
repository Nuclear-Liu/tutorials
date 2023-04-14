package org.hui.security.persistent.util;

import org.springframework.data.redis.core.RedisTemplate;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public final class RedisUtils {
    private RedisUtils() {
    }

    private static RedisTemplate<String, Object> redisTemplate = SpringUtils.getBean("redisTemplate", RedisTemplate.class);

    public static boolean expire(final String key, final long timeout) {
        return expire(key, timeout, TimeUnit.SECONDS);
    }

    public static boolean expire(final String key, final long timeout, final TimeUnit unit) {
        Boolean ret = redisTemplate.expire(key, timeout, unit);
        return ret != null && ret;
    }

    public static boolean del(final String key) {
        return Boolean.TRUE.equals(redisTemplate.delete(key));
    }

    public static long del(final Collection<String> keys) {
        Long ret = redisTemplate.delete(keys);
        return ret == null ? 0 : ret;
    }

    public static void set(final String key, final Object value) {
        redisTemplate.opsForValue().set(key, value);
    }

    public static void set(final String key, final Object value, final long timeout) {
        redisTemplate.opsForValue().set(key, value, timeout, TimeUnit.SECONDS);
    }

    public static Object get(final String key) {
        return redisTemplate.opsForValue().get(key);
    }

    public static void hashPut(final String key, final String filed, final Object value) {
        redisTemplate.opsForHash().put(key, filed, value);
    }

    public static void hashPutAll(final String key, final Map<String, Object> filedMap) {
        redisTemplate.opsForHash().putAll(key, filedMap);
    }

    public static Object hashGet(final String key, final String filed) {
        return redisTemplate.opsForHash().get(key, filed);
    }

    public static List<Object> hashMultiGet(final String key, final Collection<Object> fileds) {
        return redisTemplate.opsForHash().multiGet(key, fileds);
    }


    public static long setSet(final String key, final Object... values) {
        Long count = redisTemplate.opsForSet().add(key, values);
        return count == null ? 0 : count;
    }

    public static long setDel(final String key, final Object... values) {
        Long count = redisTemplate.opsForSet().add(key, values);
        return count == null ? 0 : count;
    }


    public static long listLPush(final String key, final Object value) {
        Long count = redisTemplate.opsForList().leftPush(key, value);
        return count == null ? 0 : count;
    }

    public static long listRPush(final String key, final Object value) {
        Long count = redisTemplate.opsForList().rightPush(key, value);
        return count == null ? 0 : count;
    }

    public static long listLPushAll(final String key, final Collection<Object> values) {
        Long count = redisTemplate.opsForList().leftPushAll(key, values);
        return count == null ? 0 : count;
    }

    public static long listRPushAll(final String key, final Collection<Object> values) {
        Long count = redisTemplate.opsForList().rightPushAll(key, values);
        return count == null ? 0 : count;
    }

    public static List<Object> getRanges(final String key, final int start, final int end) {
        return redisTemplate.opsForList().range(key, start, end);
    }

    public static Object listGetL(final String key) {
        return redisTemplate.opsForList().leftPop(key);
    }

    public static Object listGetR(final String key) {
        return redisTemplate.opsForList().rightPop(key);
    }
}
