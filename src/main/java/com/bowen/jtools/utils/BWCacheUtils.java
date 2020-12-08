package com.bowen.jtools.utils;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;

import java.util.concurrent.TimeUnit;

/**
 * Copyright (c) 2020 XiaoMi Inc. All Rights Reserved.
 * Created by: jiangbowen <jiangbowen@xiaomi.com>.
 * On 2020/12/8
 */
public class BWCacheUtils {

    // example
    protected static Cache<String, Double> similarityCache = CacheBuilder.newBuilder()
            .maximumSize(100000)
            .expireAfterWrite(60 * 24, TimeUnit.MINUTES)
            .build();

    private static void test() {

        Double similarity = 0.0;
        synchronized (similarityCache) {
            similarity = similarityCache.getIfPresent("keyname");
        }
        if (similarity == null) {
            similarity = 2.22;
            synchronized (similarityCache) {
                similarityCache.put("keyname", similarity);
            }
        }

    }

}
