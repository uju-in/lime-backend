package com.programmers.lime.redis.chat.model;

public enum ChatCursorCacheStatus {
	SUCCESS("조회에 성공했습니다."),
	FAIL("캐시가 비어있습니다.");
	private final String status;

	ChatCursorCacheStatus(final String status) {
		this.status = status;
	}
}
