package com.programmers.lime.redis.chat.publisher;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import com.programmers.lime.redis.chat.model.ChatRoomRemoveAllSessionInfo;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class ChatRoomRemoveSessionPublisher implements IChatRoomRemoveSessionPublisher {

	private final RedisTemplate<String, Object> redisTemplate;

	public void removeAllSession(final String channel,
		final ChatRoomRemoveAllSessionInfo chatRoomRemoveAllSessionInfo) {
		redisTemplate.convertAndSend(channel, chatRoomRemoveAllSessionInfo);
	}
}
