package com.programmers.lime.domains.friendships.repository;

import static com.programmers.lime.domains.friendships.domain.QFriendship.*;

import java.util.List;

import com.programmers.lime.domains.friendships.model.FriendshipSummary;
import com.programmers.lime.domains.member.domain.QMember;
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
		return findAllByCursor(nickname, nextCursorId, pageSize, friendship.fromMember, friendship.toMember);
	}

	@Override
	public List<FriendshipSummary> findFollowingByCursor(
		final String nickname,
		final String nextCursorId,
		final int pageSize
	) {
		return findAllByCursor(nickname, nextCursorId, pageSize, friendship.toMember, friendship.fromMember);
	}

	private List<FriendshipSummary> findAllByCursor(
		final String nickname,
		final String nextCursorId,
		final int pageSize,
		final QMember memberToShow,
		final QMember owner
	) {
		return jpaQueryFactory
			.select(Projections.constructor(FriendshipSummary.class,
				Projections.constructor(MemberInfo.class,
					memberToShow.id,
					memberToShow.nickname.nickname,
					memberToShow.socialInfo.profileImage,
					memberToShow.levelPoint
				),
				generateCursorId()
			))
			.from(friendship)
			.where(
				owner.nickname.nickname.eq(nickname),
				lessThanNextCursorId(nextCursorId)
			)
			.orderBy(friendship.id.desc())
			.limit(pageSize)
			.fetch();
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
