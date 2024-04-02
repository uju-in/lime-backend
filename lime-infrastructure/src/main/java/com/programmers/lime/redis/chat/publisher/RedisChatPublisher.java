package com.programmers.lime.redis.chat.publisher;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import com.programmers.lime.redis.chat.model.ChatInfoWithMemberCache;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class RedisChatPublisher implements IChatPublisher {

	private final RedisTemplate<String, Object> redisTemplate;

	@Override
	public void sendMessage(final String channel, final ChatInfoWithMemberCache chatInfoWithMemberCache) {
		redisTemplate.convertAndSend(channel, chatInfoWithMemberCache);
	}
}
