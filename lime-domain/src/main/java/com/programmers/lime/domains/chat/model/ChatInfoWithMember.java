package com.programmers.lime.domains.chat.model;

import lombok.Builder;

@Builder
public record ChatInfoWithMember(
		Long chatId,
		Long chatRoomId,
		Long memberId,
		String nickname,
		String profileImage,
		String message,
		ChatType chatType
) {
}
