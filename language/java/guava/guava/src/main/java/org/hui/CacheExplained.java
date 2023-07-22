package org.hui;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;

import java.util.concurrent.TimeUnit;

public class CacheExplained {
    public static final void main(String[] args) {
        // LoadingCache<String, String> cache = CacheBuilder.<String, String>newBuilder()
        //         .maximumSize(1000)
        //         .expireAfterWrite(10, TimeUnit.MINUTES)
        //         // .removalListener()
        //         .build(
        //                 new CacheLoader<String, String>() {
        //                     @Override
        //                     public String load(String key) throws Exception {
        //                         return null;
        //                     }
        //                 }
        //         );
    }
}
