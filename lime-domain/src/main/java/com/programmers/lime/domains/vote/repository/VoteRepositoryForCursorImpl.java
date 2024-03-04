package com.programmers.lime.domains.vote.repository;

import static com.programmers.lime.domains.item.domain.QItem.*;
import static com.programmers.lime.domains.vote.domain.QVote.*;

import java.util.List;

import com.programmers.lime.common.model.Hobby;
import com.programmers.lime.domains.vote.model.VoteCursorSummary;
import com.programmers.lime.domains.vote.model.VoteInfo;
import com.programmers.lime.domains.vote.model.VoteSortCondition;
import com.programmers.lime.domains.vote.model.VoteStatusCondition;
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
public class VoteRepositoryForCursorImpl implements VoteRepositoryForCursor {

	private final JPAQueryFactory jpaQueryFactory;

	@Override
	public List<VoteCursorSummary> findAllByCursor(
		final Hobby hobby,
		final VoteStatusCondition statusCondition,
		final VoteSortCondition sortCondition,
		final String keyword,
		final Long memberId,
		final String nextCursorId,
		final int pageSize
	) {
		return jpaQueryFactory
			.select(Projections.constructor(VoteCursorSummary.class,
				Projections.constructor(VoteInfo.class,
					vote.id,
					vote.content.content,
					vote.startTime,
					vote.endTime,
					vote.voters.size()
				),
				vote.item1Id,
				vote.item2Id,
				generateCursorId(sortCondition)
			))
			.from(vote)
			.where(
				eqHobby(hobby),
				getExpressionBy(statusCondition, memberId),
				containsKeyword(keyword),
				lessThanNextCursorId(sortCondition, nextCursorId)
			)
			.orderBy(getOrderSpecifierBy(sortCondition),
				vote.id.desc())
			.limit(pageSize)
			.fetch();
	}

	public Long countByKeyword(final String keyword) {
		return jpaQueryFactory
			.select(vote.count())
			.from(vote)
			.where(containsKeyword(keyword))
			.fetchOne();
	}

	private BooleanExpression eqHobby(final Hobby hobby) {
		if (hobby == null) {
			return null;
		}

		return vote.hobby.eq(hobby);
	}

	private BooleanExpression getExpressionBy(
		final VoteStatusCondition statusCondition,
		final Long memberId
	) {
		if (statusCondition == null) {
			return null;
		}

		switch (statusCondition) {
			case POSTED -> {
				return isPosted(memberId);
			}
			case PARTICIPATED -> {
				return isParticipatedIn(memberId);
			}
			default -> {
				return null;
			}
		}
	}

	private BooleanExpression isPosted(final Long memberId) {
		return vote.memberId.eq(memberId);
	}

	private BooleanExpression isParticipatedIn(final Long memberId) {
		return vote.voters.any()
			.memberId.eq(memberId);
	}

	private OrderSpecifier<?> getOrderSpecifierBy(final VoteSortCondition sortCondition) {
		switch (sortCondition) {
			case POPULARITY -> {
				return new OrderSpecifier<>(Order.DESC, vote.voters.size());
			}
			case RECENT -> {
				return new OrderSpecifier<>(Order.DESC, vote.createdAt);
			}
			case CLOSED -> {
				return new OrderSpecifier<>(Order.ASC, vote.endTime);
			}
			default -> {
				return null;
			}
		}
	}

	private BooleanExpression containsKeyword(final String keyword) {
		if (keyword == null) {
			return null;
		}

		final List<Long> itemIds = getItemIds(keyword);

		return vote.item1Id.in(itemIds).or(vote.item2Id.in(itemIds)).or(vote.content.content.contains(keyword));
	}

	private List<Long> getItemIds(final String keyword) {
		return jpaQueryFactory
			.select(item.id)
			.from(item)
			.where(item.name.contains(keyword))
			.fetch();
	}

	private BooleanExpression lessThanNextCursorId(
		final VoteSortCondition sortCondition,
		final String nextCursorId
	) {
		if (nextCursorId == null) {
			return null;
		}

		return generateCursorId(sortCondition).lt(nextCursorId);
	}

	private StringExpression generateCursorId(final VoteSortCondition sortCondition) {
		if (sortCondition == VoteSortCondition.POPULARITY) {
			final NumberExpression<Integer> popularity = vote.voters.size();

			return StringExpressions.lpad(
				popularity.stringValue(), 8, '0'
			).concat(StringExpressions.lpad(
				vote.id.stringValue(), 8, '0'
			));
		}

		return Expressions.stringTemplate(
			"DATE_FORMAT({0}, {1})", vote.createdAt, ConstantImpl.create("%Y%m%d%H%i%s")
		).concat(StringExpressions.lpad(
			vote.id.stringValue(), 8, '0'
		));
	}
}
