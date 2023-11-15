package com.programmers.bucketback.domains.vote.repository;

import com.programmers.bucketback.domains.common.Hobby;
import com.programmers.bucketback.domains.vote.application.dto.request.VoteSortCondition;
import com.programmers.bucketback.domains.vote.application.dto.request.VoteStatusCondition;
import com.programmers.bucketback.domains.vote.application.dto.response.VoteInfo;
import com.programmers.bucketback.domains.vote.application.dto.response.VoteSummary;
import com.querydsl.core.types.ConstantImpl;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.*;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

import static com.programmers.bucketback.domains.vote.domain.QVote.vote;

@RequiredArgsConstructor
public class VoteRepositoryForCursorImpl implements VoteRepositoryForCursor {

	private final JPAQueryFactory jpaQueryFactory;

	@Override
	public List<VoteSummary> findAllByCursor(
		final Hobby hobby,
		final VoteStatusCondition statusCondition,
		final VoteSortCondition sortCondition,
		final Long memberId,
		final String nextCursorId,
		final int pageSize
	) {
		return jpaQueryFactory
			.select(Projections.constructor(VoteSummary.class,
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
				vote.hobby.eq(hobby),
				getExpressionBy(statusCondition, memberId),
				lessThanNextCursorId(sortCondition, nextCursorId)
			)
			.orderBy(getOrderSpecifierBy(sortCondition),
				vote.id.desc())
			.limit(pageSize)
			.fetch();
	}

	private BooleanExpression getExpressionBy(
		final VoteStatusCondition statusCondition,
		final Long memberId
	) {
		switch (statusCondition) {
			case INPROGRESS -> {
				return isInProgress();
			}
			case COMPLETED -> {
				return isCompleted();
			}
			case POSTED -> {
				return isPosted(memberId);
			}
			case PARTICIPATED -> {
				return isParticipatedIn(memberId);
			}
		}

		return null;
	}

	private BooleanExpression isInProgress() {
		return vote.endTime.after(LocalDateTime.now());
	}

	private BooleanExpression isCompleted() {
		return vote.endTime.before(LocalDateTime.now());
	}

	private BooleanExpression isPosted(final Long memberId) {
		return vote.memberId.eq(memberId);
	}

	private BooleanExpression isParticipatedIn(final Long memberId) {
		return vote.voters.any()
			.memberId.eq(memberId);
	}

	private OrderSpecifier<?> getOrderSpecifierBy(final VoteSortCondition sortCondition) {
		if (sortCondition == VoteSortCondition.POPULARITY) {
			return new OrderSpecifier<>(Order.DESC, vote.voters.size());
		}

		return new OrderSpecifier<>(Order.DESC, vote.createdAt);
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
