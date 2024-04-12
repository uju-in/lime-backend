package com.programmers.lime.redis.chat.listener;

import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import com.programmers.lime.redis.chat.model.ChatRoomRemoveAllSessionInfo;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class ChatRoomRemoveAllSessionListenerImpl implements MessageListener {

	private final RedisTemplate<String, Object> redisTemplate;

	private final IChatRoomRemoveSessionListener chatRoomRemoveSessionListener;

	@Override
	public void onMessage(final Message message, final byte[] pattern) {
		try {
			ChatRoomRemoveAllSessionInfo chatRoomRemoveAllSessionInfo = (ChatRoomRemoveAllSessionInfo)redisTemplate.getValueSerializer()
				.deserialize(message.getBody());

			if (chatRoomRemoveAllSessionInfo == null) {
				throw new IllegalStateException("Deserialized message is null. Message deserialization failed.");
			}

			chatRoomRemoveSessionListener.removeAllSession(chatRoomRemoveAllSessionInfo);
		} catch (Exception e) {
			throw new RuntimeException("Failed to process redis message", e);
		}
	}
}
