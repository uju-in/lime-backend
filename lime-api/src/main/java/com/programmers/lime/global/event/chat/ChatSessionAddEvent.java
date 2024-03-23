package com.programmers.lime.global.event.chat;

public record ChatSessionAddEvent(
	String sessionId,
	String memberId,
	String roomId
) {
}
