package com.programmers.lime.redis.chat.model;

import java.io.Serializable;

import lombok.Builder;

@Builder
public record ChatCursorCache(
	String cursorId,
	String nextCursorId,
	Long chatId,
	Long chatRoomId,
	Long memberId,
	String nickname,
	String profileImage,
	String message,
	String sendAt,
	String chatType
) implements Serializable {

	public static ChatCursorCache newNextCursorId(
		final ChatCursorCache chatCursorCache,
		final String nextCursorId
	) {
		return ChatCursorCache.builder()
			.cursorId(chatCursorCache.cursorId())
			.nextCursorId(nextCursorId)
			.chatId(chatCursorCache.chatId())
			.chatRoomId(chatCursorCache.chatRoomId())
			.memberId(chatCursorCache.memberId())
			.nickname(chatCursorCache.nickname())
			.profileImage(chatCursorCache.profileImage())
			.message(chatCursorCache.message())
			.sendAt(chatCursorCache.sendAt())
			.chatType(chatCursorCache.chatType())
			.build();
	}
}
