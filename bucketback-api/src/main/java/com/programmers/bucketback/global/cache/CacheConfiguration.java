package com.programmers.bucketback.global.cache;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.caffeine.CaffeineCache;
import org.springframework.cache.support.SimpleCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.github.benmanes.caffeine.cache.Caffeine;

@EnableCaching
@Configuration
public class CacheConfiguration {
	@Bean
	public CacheManager cacheManager() {
		final List<CaffeineCache> caffeineCaches = Arrays.stream(CacheType.values())
			.map(cache -> new CaffeineCache(cache.getCacheName(), Caffeine.newBuilder().recordStats()
				.expireAfterWrite(cache.getExpireAfterWrite(), TimeUnit.SECONDS)
				.maximumSize(cache.getEntryMaxSize())
				.build()))
			.toList();

		final SimpleCacheManager cacheManager = new SimpleCacheManager();
		cacheManager.setCaches(caffeineCaches);

		return cacheManager;
	}
}
