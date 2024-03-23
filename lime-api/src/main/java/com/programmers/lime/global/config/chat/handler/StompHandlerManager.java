package com.programmers.lime.global.config.chat.handler;

import org.springframework.messaging.simp.SimpMessageType;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Component;

@Component
public class StompHandlerManager {

	public StompCommand getCommand(final StompHeaderAccessor accessor) {

		if (accessor == null) {
			throw new RuntimeException("Invalid Accessor");
		}

		SimpMessageType messageType = accessor.getMessageType();
		if (messageType == SimpMessageType.HEARTBEAT) {
			return null;
		}

		return accessor.getCommand();
	}
}
