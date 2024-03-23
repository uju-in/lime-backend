package com.programmers.lime.domains.chatroom.implementation;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.programmers.lime.domains.chatroom.repository.ChatRoomMemberRepository;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class ChatRoomMemberRemover {

	private final ChatRoomMemberRepository chatRoomMemberRepository;

	@Transactional
	public void removeChatRoomMember(
		final Long chatRoomId,
		final Long memberId
	) {
		chatRoomMemberRepository.deleteByChatRoomIdAndMemberId(chatRoomId, memberId);
	}
}
