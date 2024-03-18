package com.programmers.lime.domains.chat.api.dto.response;

import java.time.LocalDateTime;

import com.programmers.lime.domains.chat.model.ChatType;

import lombok.Builder;

@Builder
public record ChatCreateResponse(
	String message,
	String nickname,
	String profileImage,
	Long memberId,
	LocalDateTime createdAt,
	ChatType chatType
) {
}
