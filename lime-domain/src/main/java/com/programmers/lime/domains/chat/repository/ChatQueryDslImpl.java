package com.programmers.lime.domains.chat.repository;

import static com.programmers.lime.domains.member.domain.QMember.*;
import static com.programmers.lime.domains.chat.domain.QChat.*;

import java.util.List;

import org.springframework.stereotype.Component;

import com.programmers.lime.domains.chat.model.ChatInfoWithMember;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class ChatQueryDslImpl implements ChatQueryDsl{


	private final JPAQueryFactory queryFactory;

	@Override
	public List<ChatInfoWithMember> getChatInfoWithMembers(final Long chatRoomId) {
		return queryFactory
			.select(Projections.constructor(ChatInfoWithMember.class,
				chat.id.as("chatId"),
				chat.chatRoomId,
				chat.memberId,
				member.nickname.nickname,
				member.socialInfo.profileImage,
				chat.message,
				chat.chatType)
			)
			.from(chat)
			.join(member).on(chat.memberId.eq(member.id))
			.where(chat.chatRoomId.eq(chatRoomId))
			.fetch();
	}
}
