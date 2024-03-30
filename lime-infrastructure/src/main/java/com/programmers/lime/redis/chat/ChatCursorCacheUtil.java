package com.programmers.lime.redis.chat;

import java.util.List;

import com.programmers.lime.redis.chat.model.ChatCursorCacheResult;
import com.programmers.lime.redis.chat.model.ChatCursorCacheStatus;

public final class ChatCursorCacheUtil {

	public static final String HEAD_CURSOR_ID = "headCursorId";
	public static final String TAIL_CURSOR_ID = "tailCursorId";

	public static String getRoomKey(final Long roomId) {
		return "cacheKey roomId:%s".formatted(roomId);
	}

	public static ChatCursorCacheResult getFailResult() {
		return ChatCursorCacheResult.builder()
			.chatCursorCacheList(List.of())
			.chatCursorCacheStatus(ChatCursorCacheStatus.FAIL)
			.build();
	}

	public static ChatCursorCacheResult getSuccessResult() {
		return ChatCursorCacheResult.builder()
			.chatCursorCacheList(List.of())
			.chatCursorCacheStatus(ChatCursorCacheStatus.SUCCESS)
			.build();
	}

	public static String getCursorKey(final String cursorId) {
		return "cursorId: %s".formatted(cursorId);
	}
}
