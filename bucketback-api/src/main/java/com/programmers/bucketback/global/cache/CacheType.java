package com.programmers.bucketback.global.cache;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum CacheType {

	REFRESH_TOKEN("refreshToken", 12, 1)
	;

	private final String cacheName;
	private final int expireAfterWrite;
	private final int entryMaxSize;
}
