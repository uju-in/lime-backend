package com.programmers.lime.domains.chat.model;

import java.time.LocalDateTime;

import lombok.Builder;

@Builder
public record ChatInfo(
	Long chatId,
	Long chatRoomId,
	Long memberId,
	String message,
	LocalDateTime sendAt,
	ChatType chatType
) {
}
