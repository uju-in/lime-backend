package com.programmers.lime.redis.chat.model;

import lombok.Builder;

@Builder
public record ChatRoomRemoveAllSessionInfo(
	Long roomId,
	Long memberId
) {
}
