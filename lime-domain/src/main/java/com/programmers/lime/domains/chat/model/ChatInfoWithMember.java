package com.programmers.lime.domains.chat.model;

import java.time.LocalDateTime;

import lombok.Builder;

@Builder
public record ChatInfoWithMember(
		Long chatId,
		Long chatRoomId,
		Long memberId,
		String nickname,
		String profileImage,
		String message,
		LocalDateTime sendAt,
		ChatType chatType
) {

	public ChatInfo toChatInfo() {
		return ChatInfo.builder()
			.chatId(this.chatId)
			.chatRoomId(this.chatRoomId)
			.memberId(this.memberId)
			.message(this.message)
			.sendAt(this.sendAt)
			.chatType(this.chatType)
			.build();
	}
}
