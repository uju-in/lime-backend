package com.programmers.lime.domains.sse;

import java.util.Map;

import lombok.Builder;

@Builder
public record SsePayload(
	String alarmType,
	Long receiverId,
	Map<String, Object> data
) {
}
