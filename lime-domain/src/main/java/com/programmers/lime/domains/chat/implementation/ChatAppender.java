package com.programmers.lime.domains.chat.implementation;

import org.springframework.stereotype.Component;

import com.programmers.lime.domains.chat.domain.Chat;
import com.programmers.lime.domains.chat.model.ChatType;
import com.programmers.lime.domains.chat.repository.ChatRepository;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class ChatAppender {

	private final ChatRepository chatRepository;

	public void appendChat(
		final String message,
		final Long memberId,
		final Long chatRoomId,
		final ChatType chatType
	) {

		Chat chat = Chat.builder()
			.message(message)
			.memberId(memberId)
			.chatRoomId(chatRoomId)
			.chatType(chatType)
			.build();

		chatRepository.save(chat);
	}
}
