package com.programmers.lime.global.config.chat.subscription.destination;

import org.springframework.messaging.simp.stomp.StompHeaderAccessor;

public interface DestinationProcessor {
	void process(StompHeaderAccessor accessor);
}
