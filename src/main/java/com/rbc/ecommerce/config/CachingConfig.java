package com.rbc.ecommerce.config;

import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.concurrent.ConcurrentMapCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableCaching
public class CachingConfig {
	public static final String CACHE_SALE_ITEMS = "sale_items";

    @Bean
    public CacheManager cacheManager() {
        return new ConcurrentMapCacheManager(CACHE_SALE_ITEMS);
    }
 }
