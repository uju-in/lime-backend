package com.programmers.lime.redis.chat.listener;

import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import com.programmers.lime.redis.chat.model.ChatInfoWithMemberCache;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class RedisChatListener implements MessageListener {

	private final RedisTemplate<String, Object> redisTemplate;

	private final ChatListenerProxy chatMessageSubscriberProxy;

	@Override
	public void onMessage(final Message message, final byte[] pattern) {

		try {
			ChatInfoWithMemberCache chatInfoWithMemberCache = (ChatInfoWithMemberCache)redisTemplate.getValueSerializer()
				.deserialize(message.getBody());

			if (chatInfoWithMemberCache == null) {
				throw new IllegalStateException("Deserialized message is null. Message deserialization failed.");
			}

			String destination = chatInfoWithMemberCache.destination();

			chatMessageSubscriberProxy.sendMessage(destination, chatInfoWithMemberCache);
		} catch (Exception e) {
			throw new RuntimeException("Failed to process redis message", e);
		}
	}
}