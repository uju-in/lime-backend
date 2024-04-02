package com.programmers.lime.redis.chat.model;

import lombok.Builder;

@Builder
public record ChatInfoWithMemberCache(
	Long chatId,
	Long chatRoomId,
	Long memberId,
	String nickname,
	String profileImage,
	String message,
	String sendAt,
	String chatType,
	String destination
) {
}
