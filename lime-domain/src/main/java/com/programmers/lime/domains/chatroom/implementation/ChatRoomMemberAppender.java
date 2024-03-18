package com.programmers.lime.domains.chatroom.implementation;

import org.springframework.stereotype.Component;

import com.programmers.lime.domains.chatroom.domain.ChatRoomMember;
import com.programmers.lime.domains.chatroom.repository.ChatRoomMemberRepository;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class ChatRoomMemberAppender {

	private final ChatRoomMemberRepository chatRoomMemberRepository;

	public void appendChatRoomMember(
		final Long chatRoomId,
		final Long memberId
	) {
		ChatRoomMember chatRoomMember = new ChatRoomMember(chatRoomId, memberId);
		chatRoomMemberRepository.save(chatRoomMember);
	}
}
