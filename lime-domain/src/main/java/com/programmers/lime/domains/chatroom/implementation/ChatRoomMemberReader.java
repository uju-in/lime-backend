package com.programmers.lime.domains.chatroom.implementation;


import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.programmers.lime.domains.chatroom.repository.ChatRoomMemberRepository;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ChatRoomMemberReader {

	private final ChatRoomMemberRepository chatRoomMemberRepository;

	public int countChatRoomMembersByChatRoomId(final Long chatRoomId) {
		return chatRoomMemberRepository.countChatRoomMembersByChatRoomId(chatRoomId);
	}

	public boolean existMemberByMemberIdAndRoomId(
		final Long chatRoomId,
		final Long memberId
	) {
		return chatRoomMemberRepository.existsAllByChatRoomIdAndMemberId(chatRoomId, memberId);
	}
}
