package com.programmers.bucketback.domains.chat.api.dto.response;

import java.time.LocalDateTime;

public record ChatGetResponse(
	String message,
	String sendUserName,
	LocalDateTime createdAt
) {
}
