package com.programmers.lime.domains.chat.repository;

import static com.programmers.lime.domains.chat.domain.QChat.*;
import static com.programmers.lime.domains.member.domain.QMember.*;

import java.util.List;

import org.springframework.stereotype.Component;

import com.programmers.lime.domains.chat.model.ChatSummary;
import com.querydsl.core.types.ConstantImpl;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.core.types.dsl.StringExpression;
import com.querydsl.core.types.dsl.StringExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class ChatRepositoryForCursorImpl implements ChatRepositoryForCursor {

	private final JPAQueryFactory queryFactory;

	@Override
	public List<ChatSummary> findAllByCursor(
		final Long chatRoomId,
		final String cursorId,
		final int pageSize
	) {
		return queryFactory
			.select(Projections.constructor(ChatSummary.class,
				generateChatCursorId(),
				chat.id.as("chatId"),
				chat.chatRoomId,
				chat.memberId,
				member.nickname.nickname,
				member.socialInfo.profileImage,
				chat.message,
				chat.sendAt,
				chat.chatType)
			)
			.from(chat)
			.join(member).on(chat.memberId.eq(member.id))
			.where(
				cursorIdCondition(cursorId),
				chat.chatRoomId.eq(chatRoomId)
			)
			.limit(pageSize)
			.orderBy(chat.sendAt.desc())
			.fetch();
	}

	@Override
	public ChatSummary findFirstByCursor(final Long chatRoomId) {
		return queryFactory
			.select(Projections.constructor(ChatSummary.class,
				generateChatCursorId(),
				chat.id.as("chatId"),
				chat.chatRoomId,
				chat.memberId,
				member.nickname.nickname,
				member.socialInfo.profileImage,
				chat.message,
				chat.sendAt,
				chat.chatType)
			)
			.from(chat)
			.join(member).on(chat.memberId.eq(member.id))
			.where(chat.chatRoomId.eq(chatRoomId))
			.orderBy(chat.sendAt.desc())
			.fetchFirst();
	}

	@Override
	public ChatSummary findLastByCursor(final Long chatRoomId) {
		return queryFactory
			.select(Projections.constructor(ChatSummary.class,
				generateChatCursorId(),
				chat.id.as("chatId"),
				chat.chatRoomId,
				chat.memberId,
				member.nickname.nickname,
				member.socialInfo.profileImage,
				chat.message,
				chat.sendAt,
				chat.chatType)
			)
			.from(chat)
			.join(member).on(chat.memberId.eq(member.id))
			.where(chat.chatRoomId.eq(chatRoomId))
			.orderBy(chat.sendAt.asc())
			.fetchFirst();
	}

	private BooleanExpression cursorIdCondition(final String cursorId) {
		if (cursorId == null) {
			return null;
		}

		return generateChatCursorId().lt(cursorId);
	}

	private StringExpression generateChatCursorId() {
		return Expressions.stringTemplate(
			"DATE_FORMAT({0}, {1})",
			chat.sendAt,
			ConstantImpl.create("%y%m%d%H%i%s%f")
		).concat(StringExpressions.lpad(
			chat.id.stringValue(), 8, '0'
		));
	}
}
