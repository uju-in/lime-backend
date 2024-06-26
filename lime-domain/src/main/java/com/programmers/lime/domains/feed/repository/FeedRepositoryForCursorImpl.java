package com.programmers.lime.domains.feed.repository;

import static com.programmers.lime.domains.feed.domain.QFeed.*;
import static com.programmers.lime.domains.feed.domain.QFeedItem.*;
import static com.programmers.lime.domains.feed.domain.QFeedLike.*;
import static com.programmers.lime.domains.member.domain.QMember.*;
import static com.querydsl.core.group.GroupBy.*;

import java.util.List;
import java.util.Objects;

import com.programmers.lime.common.model.Hobby;
import com.programmers.lime.domains.feed.model.FeedCursorItem;
import com.programmers.lime.domains.feed.model.FeedCursorSummary;
import com.programmers.lime.domains.feed.model.FeedCursorSummaryLike;
import com.programmers.lime.domains.feed.model.FeedLikeInfo;
import com.programmers.lime.domains.feed.model.FeedSortCondition;
import com.programmers.lime.domains.member.model.MemberInfo;
import com.querydsl.core.types.ConstantImpl;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.core.types.dsl.NumberExpression;
import com.querydsl.core.types.dsl.StringExpression;
import com.querydsl.core.types.dsl.StringExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class FeedRepositoryForCursorImpl implements FeedRepositoryForCursor {

	private final JPAQueryFactory jpaQueryFactory;

	private static BooleanExpression eqHobby(final Hobby hobby) {

		if (hobby == null) {
			return null;
		}

		return feed.hobby.eq(hobby);
	}

	public List<FeedCursorSummary> findAllByCursor(
		final Long nicknameMemberId,
		final Hobby hobby,
		final FeedSortCondition feedSortCondition,
		final String cursorId,
		final int pageSize
	) {
		List<Long> feedIds = jpaQueryFactory.select(feed.id)
			.from(feed)
			.where(
				eqHobby(hobby),
				eqMemberId(nicknameMemberId),
				lessThanNextCursorId(feedSortCondition, cursorId)
			).orderBy(feedSort(feedSortCondition), feed.id.desc())
			.limit(pageSize)
			.fetch();

		return jpaQueryFactory
			.selectFrom(feedItem)
			.where(
				feedItem.feed.id.in(feedIds)
			)
			.join(member).on(member.id.eq(feedItem.feed.memberId))
			.orderBy(feedSort(feedSortCondition), feed.id.desc())
			.transform(
				groupBy(feed.id)
					.list(Projections.constructor(
						FeedCursorSummary.class,
						generateCursorId(feedSortCondition),
						Projections.constructor(
							MemberInfo.class,
							member.id,
							member.nickname.nickname,
							member.socialInfo.profileImage,
							member.levelPoint
						),
						feedItem.feed.id,
						feedItem.feed.content.content,
							feedItem.feed.likes.size(),
							feedItem.feed.comments.size(),
							feedItem.feed.createdAt,
							list(
								Projections.constructor(
									FeedCursorItem.class,
									feedItem.item.id,
									feedItem.item.image,
									feedItem.item.url
								)
							)
						)
					)
			);
	}

	@Override
	public List<FeedLikeInfo> getFeedLikeInfos(
		final List<Long> feedIds,
		final Long loginMemberId
	) {
		return jpaQueryFactory
			.select(
				Projections.constructor(
					FeedLikeInfo.class,
					feedLike.feed.id,
					feedLike.memberId
				)
			)
			.from(feedLike)
			.where(feedLike.feed.id.in(feedIds), eqLoginMemberIdWithFeedLikes(loginMemberId))
			.fetch();
	}

	private Predicate eqLoginMemberIdWithFeedLikes(final Long loginMemberId) {
		if (loginMemberId == null) {
			return Expressions.FALSE;
		}

		return feedLike.memberId.eq(loginMemberId);
	}

	private BooleanExpression eqMemberId(
		final Long nicknameMemberId
	) {
		if (nicknameMemberId == null) {
			return null;
		}

		return feed.memberId.eq(nicknameMemberId);
	}

	private BooleanExpression lessThanNextCursorId(
		final FeedSortCondition feedSortCondition,
		final String nextCursorId
	) {
		if (nextCursorId == null) {
			return null;
		}

		return generateCursorId(feedSortCondition).lt(nextCursorId);
	}

	private OrderSpecifier<?> feedSort(final FeedSortCondition feedSortCondition) {
		if (Objects.requireNonNull(feedSortCondition) == FeedSortCondition.POPULARITY) {
			return new OrderSpecifier<>(Order.DESC, feed.likes.size());
		}

		return new OrderSpecifier<>(Order.DESC, feed.createdAt);
	}

	private StringExpression generateCursorId(final FeedSortCondition feedSortCondition) {

		if (Objects.requireNonNull(feedSortCondition) == FeedSortCondition.POPULARITY) {
			final NumberExpression<Integer> popularity = feed.likes.size();

			return StringExpressions.lpad(
				popularity.stringValue(), 8, '0'
			).concat(StringExpressions.lpad(
				feed.id.stringValue(), 8, '0'
			));
		}

		return Expressions.stringTemplate(
			"DATE_FORMAT({0}, {1})", feed.createdAt, ConstantImpl.create("%Y%m%d%H%i%s")
		).concat(StringExpressions.lpad(
			feed.id.stringValue(), 8, '0'
		));
	}
}
