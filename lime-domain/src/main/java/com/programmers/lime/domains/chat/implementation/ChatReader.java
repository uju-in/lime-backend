package com.programmers.lime.domains.chat.implementation;

import java.util.List;

import org.springframework.stereotype.Component;

import com.programmers.lime.domains.chat.model.ChatInfoWithMember;
import com.programmers.lime.domains.chat.repository.ChatRepository;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class ChatReader {

	private final ChatRepository chatRepository;

	public List<ChatInfoWithMember> readChatInfoLists(final Long chatRoomId) {
		return chatRepository.getChatInfoWithMembers(chatRoomId);
	}
}
