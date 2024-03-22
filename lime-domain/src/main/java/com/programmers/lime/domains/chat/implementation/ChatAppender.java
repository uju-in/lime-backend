package com.programmers.lime.domains.chat.implementation;

import org.springframework.stereotype.Component;

import com.programmers.lime.domains.chat.domain.Chat;
import com.programmers.lime.domains.chat.model.ChatInfo;
import com.programmers.lime.domains.chat.repository.ChatRepository;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class ChatAppender {

	private final ChatRepository chatRepository;

	public void appendChat(
		final ChatInfo chatInfo
	) {

		Chat chat = Chat.builder()
				.chatRoomId(chatInfo.chatRoomId())
				.memberId(chatInfo.memberId())
				.message(chatInfo.message())
				.sendAt(chatInfo.sendAt())
				.chatType(chatInfo.chatType())
				.build();

		chatRepository.save(chat);
	}
}
