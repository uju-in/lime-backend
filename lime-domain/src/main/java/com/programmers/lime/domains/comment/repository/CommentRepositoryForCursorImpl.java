package com.programmers.lime.domains.comment.repository;

import static com.programmers.lime.domains.comment.domain.QComment.*;
import static com.programmers.lime.domains.member.domain.QMember.*;

import java.time.LocalDateTime;
import java.util.List;

import com.programmers.lime.domains.member.model.MemberInfo;
import com.querydsl.core.types.ConstantImpl;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.core.types.dsl.StringExpression;
import com.querydsl.core.types.dsl.StringExpressions;
import com.querydsl.core.types.dsl.StringTemplate;
import com.querydsl.jpa.impl.JPAQueryFactory;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class CommentRepositoryForCursorImpl implements CommentRepositoryForCursor {

	private final JPAQueryFactory jpaQueryFactory;

	@Override
	public List<CommentSummary> findByCursor(
		final Long feedId,
		final Long memberId,
		final String cursorId,
		final int pageSize
	) {
		return jpaQueryFactory
			.select(
				Projections.constructor(
					CommentSummary.class,
					generateCursorId(),
					Projections.constructor(
						MemberInfo.class,
						member.id,
						member.nickname.nickname,
						member.profileImage,
						member.levelPoint
					),
					comment.id,
					comment.content.content,
					comment.adoption,
					comment.createdAt
				)
			)
			.from(comment)
			.join(member).on(comment.memberId.eq(member.id))
			.where(
				cursorIdCondition(cursorId),
				comment.feed.id.eq(feedId)
			)
			.limit(pageSize)
			.orderBy(decrease(), comment.id.desc())
			.fetch();
	}

	private OrderSpecifier<LocalDateTime> decrease() {
		return new OrderSpecifier<>(Order.DESC, comment.createdAt);
	}

	private BooleanExpression cursorIdCondition(final String cursorId) {
		if (cursorId == null) {
			return null;
		}

		return generateCursorId().lt(cursorId);
	}

	public StringExpression generateCursorId() {
		StringTemplate dateCursor = Expressions.stringTemplate(
			"DATE_FORMAT({0}, {1})",
			comment.createdAt,
			ConstantImpl.create("%Y%m%d%H%i%s")
		);

		return dateCursor.concat(StringExpressions.lpad(
			comment.id.stringValue(), 8, '0'
		));
	}
}
