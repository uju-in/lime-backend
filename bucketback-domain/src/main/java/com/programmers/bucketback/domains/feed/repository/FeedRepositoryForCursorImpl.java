package com.programmers.bucketback.domains.feed.repository;

import static com.programmers.bucketback.domains.feed.domain.QFeed.*;
import static com.programmers.bucketback.domains.feed.domain.QFeedItem.*;
import static com.programmers.bucketback.domains.feed.domain.QFeedLike.*;
import static com.programmers.bucketback.domains.member.domain.QMember.*;
import static com.querydsl.core.group.GroupBy.*;

import java.util.List;
import java.util.Objects;

import com.programmers.bucketback.common.model.Hobby;
import com.programmers.bucketback.domains.feed.model.FeedCursorItem;
import com.programmers.bucketback.domains.feed.model.FeedCursorSummary;
import com.programmers.bucketback.domains.feed.model.FeedSortCondition;
import com.programmers.bucketback.domains.member.model.MemberInfo;
import com.querydsl.core.types.ConstantImpl;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
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

	public List<FeedCursorSummary> findAllByCursor(
		final Long nicknameMemberId,
		final boolean onlyNicknameLikeFeeds,
		final Hobby hobby,
		final FeedSortCondition feedSortCondition,
		final String cursorId,
		final int pageSize
	) {
		List<Long> feedIds = jpaQueryFactory.select(feed.id)
			.from(feed)
			.where(
				feed.bucketInfo.hobby.eq(hobby),
				eqMemberId(nicknameMemberId, onlyNicknameLikeFeeds),
				lessThanNextCursorId(feedSortCondition, cursorId)
			).orderBy(feedSort(feedSortCondition), feed.id.desc())
			.fetch();

		if (onlyNicknameLikeFeeds) {
			feedIds = jpaQueryFactory.select(feedLike.feed.id)
				.from(feedLike)
				.where(
					eqLikeMemberId(nicknameMemberId),
					feedLike.feed.id.in(feedIds)
				).fetch();
		}

		List<FeedCursorSummary> transform = jpaQueryFactory
			.selectFrom(feedItem)
			.where(
				feedItem.feed.id.in(feedIds)
			)
			.join(member).on(eqMemberIdToJoin(nicknameMemberId))
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
								member.profileImage,
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

		return transform.stream()
			.limit(pageSize)
			.toList();
	}

	private BooleanExpression eqLikeMemberId(final Long myPageMemberId) {
		if (myPageMemberId == null) {
			return null;
		}

		return feedLike.memberId.eq(myPageMemberId);
	}

	private BooleanExpression eqMemberIdToJoin(final Long myPageMemberId) {
		// 마이페이지 피드 조회
		if (myPageMemberId != null) {
			return member.id.eq(myPageMemberId);
		}

		// 일반 피드 조회
		return member.id.eq(feedItem.feed.memberId);
	}

	private BooleanExpression eqMemberId(
		final Long nicknameMemberId,
		final boolean onlyNicknameLikeFeeds
	) {
		if (onlyNicknameLikeFeeds) {
			return null;
		}

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
