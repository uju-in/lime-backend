package com.programmers.lime.domains.review.repository;

import static com.programmers.lime.domains.member.domain.QMember.*;
import static com.programmers.lime.domains.review.domain.QReview.*;
import static com.programmers.lime.domains.review.domain.QReviewImage.*;
import static com.programmers.lime.domains.review.domain.QReviewLike.*;
import static com.querydsl.core.group.GroupBy.*;

import java.util.List;

import com.programmers.lime.domains.member.model.MemberInfo;
import com.programmers.lime.domains.review.model.MemberInfoWithReviewId;
import com.programmers.lime.domains.review.model.ReviewCursorIdInfo;
import com.programmers.lime.domains.review.model.ReviewImageInfo;
import com.programmers.lime.domains.review.model.ReviewInfo;
import com.programmers.lime.domains.review.model.ReviewSortCondition;
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
		final int pageSize,
		ReviewSortCondition reviewSortCondition
	) {
		if(reviewSortCondition == null) {
			reviewSortCondition = ReviewSortCondition.NEWEST;
		}

		return jpaQueryFactory
			.select(
				Projections.constructor(
					ReviewCursorIdInfo.class,
					review.id,
					generateCursorId(reviewSortCondition)
				)
			)
			.from(review)
			.where(
				review.itemId.eq(itemId),
				whereCursorIdCondition(cursorId, reviewSortCondition)
			)
			.limit(pageSize)
			.leftJoin(reviewLike).on(review.eq(reviewLike.review))
			.groupBy(review.id)
			.having(havingCursorIdCondition(cursorId, reviewSortCondition))
			.orderBy(orderBySortCondition(reviewSortCondition), orderByIdSortCondition(reviewSortCondition))
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
	public List<ReviewImageInfo> getReviewImageInfos(final List<Long> reviewIds) {
		return jpaQueryFactory
			.selectFrom(review)
			.where(review.id.in(reviewIds))
			.leftJoin(reviewImage).on(reviewImage.review.id.eq(review.id))
			.transform(
				groupBy(review.id)
				.list(Projections.constructor(
					ReviewImageInfo.class,
					review.id,
					list(reviewImage.imageUrl)
				)
			));
	}

	@Override
	public List<ReviewInfo> getReviewInfo(final List<Long> reviewIds) {
		return jpaQueryFactory
			.selectFrom(review)
			.where(review.id.in(reviewIds))
			.leftJoin(reviewLike).on(review.eq(reviewLike.review))
			.groupBy(review.id, review.rating, review.content, review.createdAt, review.modifiedAt)
			.transform(groupBy(review.id, review.rating, review.content, review.createdAt, review.modifiedAt, reviewLike.id.count())
				.list(Projections.constructor(ReviewInfo.class,
					review.id,
					review.rating,
					review.content,
					reviewLike.id.count(),
					review.createdAt,
					review.modifiedAt
				))
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

	private OrderSpecifier<?> orderBySortCondition(ReviewSortCondition reviewSortCondition) {
		return switch (reviewSortCondition) {
			case LOWEST_RATE -> new OrderSpecifier<>(Order.ASC, review.rating);
			case HIGHEST_RATE -> new OrderSpecifier<>(Order.DESC, review.rating);
			case LIKE_COUNT_DESC -> new OrderSpecifier<>(Order.DESC, reviewLike.id.count());
			default -> new OrderSpecifier<>(Order.DESC, review.createdAt);
		};
	}

	private OrderSpecifier<?> orderByIdSortCondition(ReviewSortCondition reviewSortCondition) {
		return switch (reviewSortCondition) {
			case LOWEST_RATE -> new OrderSpecifier<>(Order.ASC, review.id);
			case HIGHEST_RATE -> new OrderSpecifier<>(Order.DESC, review.id);
			case LIKE_COUNT_DESC -> new OrderSpecifier<>(Order.DESC, review.id);
			default -> new OrderSpecifier<>(Order.DESC, review.id);
		};
	}

	private BooleanExpression whereCursorIdCondition(final String cursorId, final ReviewSortCondition reviewSortCondition) {
		if (cursorId == null) {
			return null;
		}

		return switch (reviewSortCondition) {
			case LOWEST_RATE -> generateCursorId(reviewSortCondition).gt(cursorId);
			case HIGHEST_RATE -> generateCursorId(reviewSortCondition).lt(cursorId);
			case LIKE_COUNT_DESC -> null;
			default -> generateCursorId(reviewSortCondition).lt(cursorId);
		};
	}

	private BooleanExpression havingCursorIdCondition(final String cursorId, final ReviewSortCondition reviewSortCondition) {
		if (cursorId == null) {
			return null;
		}

		return switch (reviewSortCondition) {
			case LOWEST_RATE -> null;
			case HIGHEST_RATE -> null;
			case LIKE_COUNT_DESC -> generateCursorId(reviewSortCondition).lt(cursorId);
			default -> null;
		};
	}

	public StringExpression generateCursorId(ReviewSortCondition reviewSortCondition) {
		return switch (reviewSortCondition) {
			case LOWEST_RATE -> concatWithLpadZero(review.rating.stringValue(), review.id.stringValue());
			case HIGHEST_RATE -> concatWithLpadZero(review.rating.stringValue(), review.id.stringValue());
			case LIKE_COUNT_DESC -> concatWithLpadZero(reviewLike.id.count().stringValue(), review.id.stringValue());
			default -> {
				StringTemplate dateStr = Expressions.stringTemplate(
					"DATE_FORMAT({0}, {1})",
					review.createdAt,
					ConstantImpl.create("%Y%m%d%H%i%s")
				);
				yield dateStr.concat(lpadWithZero(review.id.stringValue()));
			}
		};
	}



	// str1 문자열을 8자리로 만들고, 앞에 0으로 채움
	// str2 문자열을 8자리로 만들고, 앞에 0으로 채움
	// str1 + str2 반환 (총 16자리)
	public StringExpression concatWithLpadZero(final StringExpression str1, final StringExpression str2) {
		return lpadWithZero(str1).concat(lpadWithZero(str2));
	}

	// str 문자열을 8자리로 만들고, 앞에 0으로 채움
	public StringExpression lpadWithZero(final StringExpression str) {
		return StringExpressions.lpad(
			str, 8, '0'
		);
	}
}
