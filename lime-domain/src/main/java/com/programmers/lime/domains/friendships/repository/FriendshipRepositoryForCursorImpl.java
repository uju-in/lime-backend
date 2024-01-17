package com.programmers.lime.domains.friendships.repository;

import static com.programmers.lime.domains.friendships.domain.QFriendship.*;
import static com.programmers.lime.domains.member.domain.QMember.*;

import java.util.List;

import com.programmers.lime.domains.friendships.model.FriendshipSummary;
import com.programmers.lime.domains.member.model.MemberInfo;
import com.querydsl.core.types.ConstantImpl;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.core.types.dsl.StringExpression;
import com.querydsl.core.types.dsl.StringExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class FriendshipRepositoryForCursorImpl implements FriendshipRepositoryForCursor {

	private final JPAQueryFactory jpaQueryFactory;

	@Override
	public List<FriendshipSummary> findFollowerByCursor(
		final String nickname,
		final String nextCursorId,
		final int pageSize
	) {
		return jpaQueryFactory
			.select(Projections.constructor(FriendshipSummary.class,
				Projections.constructor(MemberInfo.class,
					member.id,
					member.nickname.nickname,
					member.profileImage,
					member.levelPoint
				),
				generateCursorId()
			))
			.from(friendship)
			.join(member).on(friendship.fromMemberId.eq(member.id))
			.where(
				friendship.toMemberId.eq(getMemberId(nickname)),
				lessThanNextCursorId(nextCursorId)
			)
			.orderBy(friendship.id.desc())
			.limit(pageSize)
			.fetch();
	}

	@Override
	public List<FriendshipSummary> findFollowingByCursor(
		final String nickname,
		final String nextCursorId,
		final int pageSize
	) {
		return jpaQueryFactory
			.select(Projections.constructor(FriendshipSummary.class,
				Projections.constructor(MemberInfo.class,
					member.id,
					member.nickname.nickname,
					member.profileImage,
					member.levelPoint
				),
				generateCursorId()
			))
			.from(friendship)
			.join(member).on(friendship.toMemberId.eq(member.id))
			.where(
				friendship.fromMemberId.eq(getMemberId(nickname)),
				lessThanNextCursorId(nextCursorId)
			)
			.orderBy(friendship.id.desc())
			.limit(pageSize)
			.fetch();
	}

	private Long getMemberId(final String nickname) {
		return jpaQueryFactory
			.select(member.id)
			.from(member)
			.where(member.nickname.nickname.eq(nickname))
			.fetchOne();
	}

	private BooleanExpression lessThanNextCursorId(final String nextCursorId) {
		if (nextCursorId == null) {
			return null;
		}

		return generateCursorId().lt(nextCursorId);
	}

	private StringExpression generateCursorId() {
		return Expressions.stringTemplate(
			"DATE_FORMAT({0}, {1})", friendship.createdAt, ConstantImpl.create("%Y%m%d%H%i%s")
		).concat(StringExpressions.lpad(
			friendship.id.stringValue(), 8, '0'
		));
	}
}
