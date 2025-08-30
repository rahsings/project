package com.fastfashion.config;

import com.github.benmanes.caffeine.cache.Caffeine;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.cache.CacheManager;
import org.springframework.cache.caffeine.CaffeineCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;

@Configuration
public class CacheConfig {
    @Bean
    @ConditionalOnMissingBean(CacheManager.class)
    public CacheManager cacheManager(@Value("${app.cache.ttl-seconds:300}") long ttlSeconds) {
        CaffeineCacheManager mgr = new CaffeineCacheManager("shops", "productsByShop");
        mgr.setCaffeine(Caffeine.newBuilder()
                .expireAfterWrite(Duration.ofSeconds(ttlSeconds))
                .maximumSize(10_000));
        return mgr;
    }
}
