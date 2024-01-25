package com.programmers.lime.domains.review.repository;

import static com.programmers.lime.domains.member.domain.QMember.*;
import static com.programmers.lime.domains.review.domain.QReview.*;
import static com.programmers.lime.domains.review.domain.QReviewImage.*;
import static com.querydsl.core.group.GroupBy.*;

import java.time.LocalDateTime;
import java.util.List;

import com.programmers.lime.domains.member.model.MemberInfo;
import com.programmers.lime.domains.review.model.MemberInfoWithReviewId;
import com.programmers.lime.domains.review.model.ReviewCursorIdInfo;
import com.programmers.lime.domains.review.model.ReviewSummary;
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
	public List<ReviewCursorIdInfo> findAllByCursor(
		final Long itemId,
		final String cursorId,
		final int pageSize
	) {
		return jpaQueryFactory
			.select(
				Projections.constructor(
					ReviewCursorIdInfo.class,
					review.id,
					generateCursorId()
				)
			)
			.from(review)
			.where(
				cursorIdCondition(cursorId),
				review.itemId.eq(itemId)
			)
			.limit(pageSize)
			.orderBy(decrease(), review.id.desc())
			.fetch();
	}

	@Override
	public List<MemberInfoWithReviewId> getMemberInfos(final List<Long> reviewIds) {
		return jpaQueryFactory
			.select(
				Projections.constructor(
					MemberInfoWithReviewId.class,
					review.id,
					Projections.constructor(
						MemberInfo.class,
						member.id,
						member.nickname.nickname,
						member.socialInfo.profileImage,
						member.levelPoint
					)
				)
			)
			.from(review)
			.where(review.id.in(reviewIds))
			.leftJoin(member).on(review.memberId.eq(member.id))
			.fetch();
	}

	@Override
	public List<ReviewSummary> getReviewSummaries(final List<Long> reviewIds) {
		return jpaQueryFactory
			.select(
				review
			)
			.from(review)
			.where(review.id.in(reviewIds))
			.leftJoin(reviewImage).on(review.id.eq(reviewImage.reviewId))
			.transform(
				groupBy(review.id)
					.list(
						Projections.constructor(
							ReviewSummary.class,
							review.id,
							review.rating,
							review.content,
							list(
								reviewImage.imageUrl
							),
							review.createdAt,
							review.modifiedAt
						)
					)
			);
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
