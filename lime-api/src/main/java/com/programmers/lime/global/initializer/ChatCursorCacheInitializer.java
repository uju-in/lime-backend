package com.programmers.lime.global.initializer;

import java.util.List;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

import com.programmers.lime.domains.chatroom.implementation.ChatRoomReader;
import com.programmers.lime.domains.chatroom.model.ChatRoomInfo;
import com.programmers.lime.global.event.chat.ChatInitCacheEvent;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class ChatCursorCacheInitializer implements ApplicationRunner {

	private final ChatRoomReader chatRoomReader;

	private final ApplicationEventPublisher eventPublisher;

	@Override
	public void run(final ApplicationArguments args) {
		List<ChatRoomInfo> chatRoomInfos = chatRoomReader.readOpenChatRoomsByMemberId(null);

		ChatInitCacheEvent chatInitCacheEvent = new ChatInitCacheEvent(chatRoomInfos);
		eventPublisher.publishEvent(chatInitCacheEvent);
	}
}