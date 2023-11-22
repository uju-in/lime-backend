package com.programmers.bucketback.domains.review.repository;

import static com.programmers.bucketback.domains.member.domain.QMember.*;
import static com.programmers.bucketback.domains.review.domain.QReview.*;

import java.time.LocalDateTime;
import java.util.List;

import com.programmers.bucketback.domains.member.model.MemberInfo;
import com.programmers.bucketback.domains.review.model.ReviewCursorSummary;
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
public class ReviewRepositoryForCursorImpl implements ReviewRepositoryForCursor {

	private final JPAQueryFactory jpaQueryFactory;

	@Override
	public List<ReviewCursorSummary> findAllByCursor(
		final Long itemId,
		final Long memberId,
		final String cursorId,
		final int pageSize
	) {
		return jpaQueryFactory
			.select(
				Projections.constructor(
					ReviewCursorSummary.class,
					generateCursorId(),
					Projections.constructor(
						MemberInfo.class,
						member.id,
						member.nickname.nickname,
						member.profileImage,
						member.levelPoint
					),
					review.id,
					review.rating,
					review.content,
					memberIdCondition(memberId),
					review.createdAt,
					review.modifiedAt
				)
			)
			.from(review)
			.join(member).on(review.memberId.eq(member.id))
			.where(
				cursorIdCondition(cursorId),
				review.itemId.eq(itemId)
			)
			.limit(pageSize)
			.orderBy(decrease(), review.id.desc())
			.fetch();
	}

	@Override
	public int getReviewCount(final Long itemId) {
		return Math.toIntExact(jpaQueryFactory
			.select(review.count())
			.from(review)
			.where(review.itemId.eq(itemId))
			.join(review).on(review.memberId.eq(member.id))
			.fetchFirst());
	}

	private OrderSpecifier<LocalDateTime> decrease() {
		return new OrderSpecifier<>(Order.DESC, review.createdAt);
	}

	private BooleanExpression memberIdCondition(final Long memberId) {
		if (memberId == null) {
			// memberId가 null인 경우(사용자가 로그인 하지 않은 경우) false 반환
			return Expressions.asBoolean(false).isTrue();
		}

		return review.memberId.eq(memberId);
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
			review.createdAt,
			ConstantImpl.create("%Y%m%d%H%i%s")
		);

		return dateCursor.concat(StringExpressions.lpad(
			review.id.stringValue(), 8, '0'
		));
	}
}
