package com.url.shortener.ratelimit;

import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Service;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Service
public class RateLimiterService {

    private final StringRedisTemplate redisTemplate;

    private static final int LIMIT = 100;
    private static final int WINDOW = 60;

    public RateLimiterService(StringRedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public boolean allowRequest(String key) {

        long now = System.currentTimeMillis();
        long windowStart = now - WINDOW * 1000;

        String redisKey = "rate_limit:" + key;

        ZSetOperations<String, String> zset = redisTemplate.opsForZSet();

        zset.removeRangeByScore(redisKey, 0, windowStart);

        Long count = zset.zCard(redisKey);

        if (count != null && count >= LIMIT) {
            return false;
        }

        zset.add(redisKey, UUID.randomUUID().toString(), now);

        redisTemplate.expire(redisKey, WINDOW, TimeUnit.SECONDS);

        return true;
    }
}
