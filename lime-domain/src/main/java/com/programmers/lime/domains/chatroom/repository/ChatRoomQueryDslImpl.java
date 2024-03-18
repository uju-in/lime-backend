package com.programmers.lime.domains.chatroom.repository;

import java.util.List;

import static com.programmers.lime.domains.chatroom.domain.QChatRoom.*;
import static com.programmers.lime.domains.chatroom.domain.QChatRoomMember.*;
import static com.querydsl.core.types.ExpressionUtils.*;

import com.programmers.lime.domains.chatroom.model.ChatRoomInfo;
import com.programmers.lime.domains.chatroom.model.ChatRoomStatus;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ChatRoomQueryDslImpl implements ChatRoomQueryDsl {

	private final JPAQueryFactory jpaQueryFactory;

	@Override
	public List<ChatRoomInfo> findOpenChatRoomsIncludingWithoutMembers(final Long memberId) {
		return jpaQueryFactory
			.select(Projections.constructor(ChatRoomInfo.class,
				chatRoom.id.as("chatRoomId"),
				chatRoom.roomName.as("chatRoomName"),
				chatRoom.chatRoomType.as("chatRoomType"),
				chatRoom.chatRoomStatus.as("chatRoomStatus"),
				chatRoom.roomMaxMemberCount.as("roomMaxMemberCount"),
				chatRoomMember.memberId.coalesce(0L).goe(1L).as("isJoined"),
				JPAExpressions
					.select(count(chatRoomMember.chatRoomId))
					.from(chatRoomMember)
					.where(
						chatRoom.id.eq(chatRoomMember.chatRoomId)
					)
			))
			.from(chatRoom)
			.leftJoin(chatRoomMember).on(chatRoom.id.eq(chatRoomMember.chatRoomId)
				.and(chatRoomMember.memberId.eq(memberId)))
			.where(chatRoom.chatRoomStatus.eq(ChatRoomStatus.OPEN))
			.groupBy(chatRoom.id)
			.fetch();
	}

	@Override
	public List<ChatRoomInfo> findOpenChatRooms() {
		return jpaQueryFactory
			.select(Projections.constructor(ChatRoomInfo.class,
				chatRoom.id.as("chatRoomId"),
				chatRoom.roomName.as("chatRoomName"),
				chatRoom.chatRoomType.as("chatRoomType"),
				chatRoom.chatRoomStatus.as("chatRoomStatus"),
				chatRoom.roomMaxMemberCount.as("roomMaxMemberCount"),
				Expressions.constant(false),
				JPAExpressions
					.select(count(chatRoomMember.chatRoomId))
					.from(chatRoomMember)
					.where(
						chatRoom.id.eq(chatRoomMember.chatRoomId)
					)
			))
			.from(chatRoom)
			.where(chatRoom.chatRoomStatus.eq(ChatRoomStatus.OPEN))
			.fetch();
	}


}
