package com.programmers.lime.global.event.chat;

import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import com.programmers.lime.redis.chat.ChatSessionRedisManager;
import com.programmers.lime.redis.chat.model.ChatSessionInfo;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class ChatSessionEventListener {

	private final ChatSessionRedisManager chatSessionRedisManager;

	@Async
	@EventListener
	public void addChatSession(final ChatSessionAddEvent event) {
		ChatSessionInfo chatSessionInfo = new ChatSessionInfo(
			Long.valueOf(event.memberId()), Long.valueOf(event.roomId())
		);

		chatSessionRedisManager.saveSession(event.sessionId(), chatSessionInfo);
	}
}
