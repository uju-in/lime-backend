package com.programmers.lime.redis.chat.model;

import java.util.List;

import lombok.Builder;

@Builder
public record ChatCursorCacheResult(
	List<ChatCursorCache> chatCursorCacheList,
	ChatCursorCacheStatus chatCursorCacheStatus,
	String nextCursorId
) {
}
