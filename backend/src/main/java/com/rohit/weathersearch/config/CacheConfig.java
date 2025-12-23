package com.rohit.weathersearch.config;

import com.github.benmanes.caffeine.cache.Caffeine;
import com.github.benmanes.caffeine.cache.stats.CacheStats;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.caffeine.CaffeineCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.TimeUnit;

/**
 * Configuration class for cache management using Caffeine.
 * Implements caching strategy with maximum size and time-based expiry.
 */
@Configuration
@EnableCaching
public class CacheConfig {
    
    public static final String WEATHER_CACHE = "weatherCache";
    
    @Value("${cache.max-size}")
    private int maxSize;
    
    @Value("${cache.expire-after-write-minutes}")
    private int expireAfterWriteMinutes;
    
    /**
     * Configures Caffeine cache with size limit and expiry time.
     * recordStats() enables cache statistics monitoring.
     */
    @Bean
    public Caffeine<Object, Object> caffeineConfig() {
        return Caffeine.newBuilder()
                .maximumSize(maxSize)
                .expireAfterWrite(expireAfterWriteMinutes, TimeUnit.MINUTES)
                .recordStats(); // Enable statistics for monitoring cache performance
    }
    
    /**
     * Creates CacheManager bean with Caffeine configuration.
     */
    @Bean
    public CacheManager cacheManager(Caffeine<Object, Object> caffeine) {
        CaffeineCacheManager cacheManager = new CaffeineCacheManager(WEATHER_CACHE);
        cacheManager.setCaffeine(caffeine);
        return cacheManager;
    }
}
