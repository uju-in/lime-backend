package com.programmers.lime.redis.chat.model;

public record CacheKeyAndStartIdxPair(
	String cacheKey,
	Double startIdx
) {
}
