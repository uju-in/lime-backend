package com.programmers.lime.domains.chat.implementation;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import com.programmers.lime.domains.chat.domain.Chat;
import com.programmers.lime.domains.chat.model.ChatInfo;
import com.programmers.lime.domains.chat.repository.ChatJdbcTemplateRepository;
import com.programmers.lime.domains.chat.repository.ChatRepository;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class ChatAppender {

	private final ChatRepository chatRepository;

	private final ChatJdbcTemplateRepository chatJdbcTemplateRepository;

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

	public void appendChats(
		final List<ChatInfo> chatInfos
	) {
		List<Chat> chats = chatInfos.stream().map(chatInfo ->
			Chat.builder()
				.chatRoomId(chatInfo.chatRoomId())
				.memberId(chatInfo.memberId())
				.message(chatInfo.message())
				.sendAt(chatInfo.sendAt())
				.chatType(chatInfo.chatType())
				.build()
		).collect(Collectors.toList());

		chatJdbcTemplateRepository.bulkInsertChats(chats);
	}
}
