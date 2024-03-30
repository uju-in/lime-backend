package com.programmers.lime.global.event.chat;

import java.util.List;

import com.programmers.lime.domains.chat.model.ChatSummary;

import lombok.Builder;

@Builder
public record ChatAppendCacheEvent(
	Long chatRoomId,
	String startCursorId,
	List<ChatSummary> summaries,
	int requestSize
) {
}
