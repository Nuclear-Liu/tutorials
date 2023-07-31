package org.hui;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

public class CacheExplained {
    public static final void main(String[] args) throws ExecutionException {
        LoadingCache<String, String> cache = CacheBuilder.newBuilder()
                .maximumSize(1000)
                .build(new CacheLoader<String, String>() {
                    @Override
                    public String load(String key) throws Exception {
                        System.out.println("call load");
                        String result = "hui" + key;
                        return result;
                    }
                });

        System.out.println("get cache");
        System.out.println(cache.get("2"));
        System.out.println(cache.get("2"));
    }
}
