package com.programmers.lime.domains.chatroom.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.programmers.lime.domains.chatroom.domain.ChatRoomMember;

public interface ChatRoomMemberRepository extends JpaRepository<ChatRoomMember, Long> {

	int countChatRoomMembersByChatRoomId(final Long chatRoomId);

	boolean existsAllByChatRoomIdAndMemberId(
		final Long chatRoomId,
		final Long memberId
	);

	void deleteByChatRoomIdAndMemberId(
		final Long chatRoomId,
		final Long memberId
	);
}
