package com.programmers.lime.global.config.chat.handler;

import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.SimpMessageType;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.messaging.support.MessageHeaderAccessor;
import org.springframework.stereotype.Component;

import com.programmers.lime.error.ErrorCode;
import com.programmers.lime.redis.chat.ChatSessionRedisManager;

import io.jsonwebtoken.MalformedJwtException;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class DisconnectPreHandler implements ChannelInterceptor {

	private final ChatSessionRedisManager chatSessionRedisManager;

	@Override
	public Message<?> preSend(Message<?> message, MessageChannel channel) {
		StompHeaderAccessor accessor = MessageHeaderAccessor.getAccessor(message, StompHeaderAccessor.class);

		if (accessor == null) {
			throw new MalformedJwtException(ErrorCode.MEMBER_NOT_LOGIN.getMessage());
		}

		SimpMessageType messageType = accessor.getMessageType();
		if(messageType == SimpMessageType.HEARTBEAT) {
			return message;
		}

		if (StompCommand.DISCONNECT.equals(accessor.getCommand())) {
			String sessionId = accessor.getSessionId();
			chatSessionRedisManager.deleteSession(sessionId);
		}

		return message;
	}
}
