package com.programmers.lime.domains.chatroom.implementation;

import java.util.List;

import org.springframework.stereotype.Component;

import com.programmers.lime.domains.chatroom.model.ChatRoomInfo;
import com.programmers.lime.domains.chatroom.repository.ChatRoomRepository;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class ChatRoomReader {

	private final ChatRoomRepository chatRoomRepository;

	public List<ChatRoomInfo> readOpenChatRoomsByMemberId(Long memberId) {
		return chatRoomRepository.findOpenChatRoomsIncludingWithoutMembers(memberId);
	}

	public List<ChatRoomInfo> readOpenChatRooms() {
		return chatRoomRepository.findOpenChatRooms();
	}
}
