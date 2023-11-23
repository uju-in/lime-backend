package com.programmers.bucketback.domains.item.repository;

import static com.programmers.bucketback.domains.item.domain.QItem.*;
import static com.programmers.bucketback.domains.item.domain.QMemberItem.*;
import static com.querydsl.core.group.GroupBy.*;

import java.util.List;

import com.programmers.bucketback.common.model.Hobby;
import com.programmers.bucketback.domains.bucket.model.BucketMemberItemSummary;
import com.programmers.bucketback.domains.item.model.ItemInfo;
import com.programmers.bucketback.domains.item.model.MemberItemSummary;
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
		final Hobby hobby,
		final Long memberId,
		final String cursorId,
		final int pageSize
	) {
		return jpaQueryFactory
			.selectFrom(item)
			.join(memberItem).on(item.id.eq(memberItem.item.id))
			.where(
				cursorIdConditionFromMemberItem(cursorId),
				item.id.in(itemIdsFromMemberItem),
				hobbyCondition(hobby)
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

	@Override
	public List<MemberItemSummary> findMemberItemsByCursor(
		final Hobby hobby,
		final Long memberId,
		final String cursorId,
		final int pageSize
	) {
		List<Long> itemIdsFromMemberItem = jpaQueryFactory.select(memberItem.item.id)
			.from(memberItem)
			.where(memberItem.memberId.eq(memberId))
			.stream()
			.toList();

		return jpaQueryFactory
			.select(
				Projections.constructor(MemberItemSummary.class,
					generateMemberItemCursorId(),
					item.createdAt,
					Projections.constructor(ItemInfo.class,
						item.id,
						item.name,
						item.price,
						item.image
					)
				)
			).from(item)
			.where(
				cursorIdConditionFromMemberItem(cursorId),
				hobbyCondition(hobby),
				item.id.in(itemIdsFromMemberItem)
			).orderBy(new OrderSpecifier<>(Order.DESC, item.createdAt))
			.limit(pageSize)
			.fetch();
	}

	private BooleanExpression isSelected(final List<Long> itemIdsFromBucketItem) {
		if (itemIdsFromBucketItem == null) {
			return Expressions.asBoolean(false).isTrue();
		}

		return new CaseBuilder()
			.when(memberItem.item.id.in(itemIdsFromBucketItem))
			.then(true)
			.otherwise(false);
	}

	private BooleanExpression hobbyCondition(final Hobby hobby) {
		if (hobby == null) {
			return null;
		}

		return item.hobby.eq(hobby);
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
