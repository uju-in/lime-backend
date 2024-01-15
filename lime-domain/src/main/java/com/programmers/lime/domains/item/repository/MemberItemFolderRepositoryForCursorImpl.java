package com.programmers.lime.domains.item.repository;

import static com.programmers.lime.domains.item.domain.QMemberItemFolder.*;


import java.util.List;

import com.programmers.lime.common.model.Hobby;
import com.programmers.lime.domains.item.model.MemberItemFolderCursorSummary;
import com.programmers.lime.domains.item.model.MemberItemFolderSummary;
import com.querydsl.core.types.ConstantImpl;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.core.types.dsl.StringExpression;
import com.querydsl.core.types.dsl.StringExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class MemberItemFolderRepositoryForCursorImpl implements MemberItemFolderRepositoryForCursor{

	private final JPAQueryFactory jpaQueryFactory;

	@Override
	public List<MemberItemFolderCursorSummary> findMemberItemFoldersByCursor(
		final Long memberId,
		final Hobby hobby,
		final String cursorId,
		final int pageSize
	) {
		return jpaQueryFactory
			.select(Projections.constructor(
				MemberItemFolderCursorSummary.class,
				generateMemberItemFolderCursorId(),
				Projections.constructor(
					MemberItemFolderSummary.class,
					memberItemFolder.id,
					memberItemFolder.name
				)
			)).from(memberItemFolder)
			.where(
				cursorIdConditionFromMemberItemFolder(cursorId),
				memberItemFolder.memberId.eq(memberId),
				hobbyCondition(hobby)
			).orderBy(new OrderSpecifier<>(Order.DESC, memberItemFolder.id))
			.limit(pageSize)
			.fetch();
	}

	private BooleanExpression hobbyCondition(final Hobby hobby) {
		if (hobby == null) {
			return null;
		}

		return memberItemFolder.hobby.eq(hobby);
	}

	private BooleanExpression cursorIdConditionFromMemberItemFolder(final String cursorId) {
		if (cursorId == null) {
			return null;
		}

		return generateMemberItemFolderCursorId().lt(cursorId);
	}

	private StringExpression generateMemberItemFolderCursorId() {
		return Expressions.stringTemplate(
				"DATE_FORMAT({0}, {1})",
				memberItemFolder.createdAt,
				ConstantImpl.create("%Y%m%d%H%i%s"))
			.concat(StringExpressions.lpad(
				memberItemFolder.id.stringValue(), 8, '0'
			));
	}
}
