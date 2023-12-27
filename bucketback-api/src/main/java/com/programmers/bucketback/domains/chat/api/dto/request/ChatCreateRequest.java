package com.programmers.bucketback.domains.chat.api.dto.request;

public record ChatCreateRequest(
	String userNickname,
	Long chatRoomId,
	String message
) {
}
