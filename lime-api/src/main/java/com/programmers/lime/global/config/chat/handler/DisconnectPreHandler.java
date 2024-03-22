package com.programmers.lime.global.config.chat.handler;

import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.messaging.support.MessageHeaderAccessor;
import org.springframework.stereotype.Component;

import com.programmers.lime.redis.chat.ChatSessionRedisManager;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class DisconnectPreHandler implements ChannelInterceptor {

	private final ChatSessionRedisManager chatSessionRedisManager;

	private final StompHandlerManager stompHandlerManager;

	@Override
	public Message<?> preSend(Message<?> message, MessageChannel channel) {
		StompHeaderAccessor accessor = MessageHeaderAccessor.getAccessor(message, StompHeaderAccessor.class);

		StompCommand command = stompHandlerManager.getCommand(accessor);

		if (StompCommand.DISCONNECT.equals(command)) {
			String sessionId = accessor.getSessionId();
			chatSessionRedisManager.deleteSession(sessionId);
		}

		return message;
	}
}
