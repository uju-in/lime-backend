package com.programmers.bucketback.domains.item.repository;

import static com.programmers.bucketback.domains.item.domain.QItem.*;
import static com.programmers.bucketback.domains.item.domain.QMemberItem.*;
import static com.querydsl.core.group.GroupBy.*;

import java.util.List;

import com.programmers.bucketback.domains.bucket.application.vo.BucketMemberItemSummary;
import com.programmers.bucketback.domains.item.application.vo.ItemInfo;
import com.querydsl.core.types.ConstantImpl;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.CaseBuilder;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.core.types.dsl.StringExpression;
import com.querydsl.core.types.dsl.StringExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class MemberItemRepositoryForCursorImpl implements MemberItemRepositoryForCursor {

	private final JPAQueryFactory jpaQueryFactory;

	@Override
	public List<BucketMemberItemSummary> findBucketMemberItemsByCursor(
		final List<Long> itemIdsFromBucketItem,
		final List<Long> itemIdsFromMemberItem,
		final Long memberId,
		final String cursorId,
		final int pageSize
	) {

		return jpaQueryFactory
			.selectFrom(item)
			.join(memberItem).on(item.id.eq(memberItem.item.id))
			.where(
				cursorIdConditionFromMemberItem(cursorId),
				item.id.in(itemIdsFromMemberItem)
			)
			.orderBy(new OrderSpecifier<>(Order.DESC, item.createdAt))
			.limit(pageSize)
			.transform(groupBy(item.id)
				.list(Projections.constructor(BucketMemberItemSummary.class,
					generateMemberItemCursorId(),
					isSelected(itemIdsFromBucketItem),
					item.createdAt,
					Projections.constructor(ItemInfo.class,
						item.id,
						item.name,
						item.price,
						item.image
					)
				))
			);
	}

	private BooleanExpression isSelected(final List<Long> itemIdsFromBucketItem) {
		return new CaseBuilder()
			.when(memberItem.item.id.in(itemIdsFromBucketItem))
			.then(true)
			.otherwise(false);
	}

	private BooleanExpression cursorIdConditionFromMemberItem(final String cursorId) {
		if (cursorId == null) {
			return null;
		}

		return generateMemberItemCursorId().lt(cursorId);
	}

	private StringExpression generateMemberItemCursorId() {
		return Expressions.stringTemplate(
				"DATE_FORMAT({0}, {1})",
				item.createdAt,
				ConstantImpl.create("%Y%m%d%H%i%s"))
			.concat(StringExpressions.lpad(
				item.id.stringValue(), 8, '0'
			));
	}
}
