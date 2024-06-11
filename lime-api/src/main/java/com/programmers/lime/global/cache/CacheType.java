package com.programmers.lime.global.cache;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum CacheType {

	REFRESH_TOKEN("refreshToken", 1209600, 10000),
	CHAT_CACHE("chat", 3600, 500000),
	MEMBER_CACHE("member", 300, 10000);

	private final String cacheName;
	private final int expireAfterWrite;
	private final int entryMaxSize;
}
