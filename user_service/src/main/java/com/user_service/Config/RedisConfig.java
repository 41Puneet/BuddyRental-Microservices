package com.user_service.Config;

import java.time.Duration;

import org.slf4j.*;
import org.springframework.cache.Cache;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.interceptor.CacheErrorHandler;
import org.springframework.cache.interceptor.SimpleCacheErrorHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;

@Configuration
@EnableCaching
public class RedisConfig implements CacheErrorHandler {

    private static final Logger logger = LoggerFactory.getLogger(RedisConfig.class);

     @Bean
    public RedisCacheConfiguration cacheConfiguration() {

        return RedisCacheConfiguration.defaultCacheConfig()
                .entryTtl(Duration.ofMinutes(10))
                .disableCachingNullValues()
                .serializeValuesWith(
                        RedisSerializationContext.SerializationPair
                                .fromSerializer(
                                        new GenericJackson2JsonRedisSerializer()
                                )
                );
    }

    @Override
public void handleCacheGetError(RuntimeException exception, Cache cache, Object key) {
    logger.error("Redis GET failed - cache={}, key={}", cache.getName(), key, exception);
}

@Override
public void handleCachePutError(RuntimeException exception, Cache cache, Object key, Object value) {
    logger.error("Redis PUT failed - cache={}, key={}", cache.getName(), key, exception);
}

@Override
public void handleCacheEvictError(RuntimeException exception, Cache cache, Object key) {
    logger.error("Redis EVICT failed - cache={}, key={}", cache.getName(), key, exception);
}

@Override
public void handleCacheClearError(RuntimeException exception, Cache cache) {
    logger.error("Redis CLEAR failed - cache={}", cache.getName(), exception);
}
}