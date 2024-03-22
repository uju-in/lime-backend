package com.programmers.lime.global.config.chat.handler;

import org.springframework.messaging.Message;
import org.springframework.messaging.simp.SimpMessageType;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.messaging.support.MessageHeaderAccessor;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class TimeSeqPreHandler implements ChannelInterceptor {

	@Override
	public Message<?> preSend(
		final Message<?> message,
		final org.springframework.messaging.MessageChannel channel
	) {
		StompHeaderAccessor accessor = MessageHeaderAccessor.getAccessor(message, StompHeaderAccessor.class);
		SimpMessageType messageType = accessor.getMessageType();

		if (messageType == SimpMessageType.HEARTBEAT) {
			return message;
		}

		StompCommand command = accessor.getCommand();

		if(command != StompCommand.SEND) {
			return message;
		}

		accessor.addNativeHeader("time-seq", String.valueOf(System.currentTimeMillis()));

		return message;
	}
}
