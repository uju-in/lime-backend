package com.programmers.lime.global.config.chat.handler;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.messaging.support.MessageHeaderAccessor;
import org.springframework.stereotype.Component;

import com.programmers.lime.global.event.chat.ChatSessionDeleteEvent;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class DisconnectPreHandler implements ChannelInterceptor {

	private final StompHandlerManager stompHandlerManager;

	private final ApplicationEventPublisher eventPublisher;

	@Override
	public Message<?> preSend(final Message<?> message, final MessageChannel channel) {
		StompHeaderAccessor accessor = MessageHeaderAccessor.getAccessor(message, StompHeaderAccessor.class);

		StompCommand command = stompHandlerManager.getCommand(accessor);

		if (StompCommand.DISCONNECT.equals(command)) {
			String sessionId = accessor.getSessionId();
			eventPublisher.publishEvent(new ChatSessionDeleteEvent(sessionId));
		}

		return message;
	}
}
