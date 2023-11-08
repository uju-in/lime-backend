package com.programmers.bucketback.domains.bucket.repository;

import static com.programmers.bucketback.domains.bucket.domain.QBucket.*;
import static com.programmers.bucketback.domains.bucket.domain.QBucketItem.*;
import static com.programmers.bucketback.domains.item.domain.QItem.*;
import static com.programmers.bucketback.domains.item.domain.QMemberItem.*;
import static com.querydsl.core.group.GroupBy.*;

import java.time.LocalDateTime;
import java.util.List;

import com.programmers.bucketback.domains.bucket.application.vo.BucketMemberItemSummary;
import com.programmers.bucketback.domains.bucket.application.vo.BucketSummary;
import com.programmers.bucketback.domains.bucket.application.vo.ItemImage;
import com.programmers.bucketback.domains.common.Hobby;
import com.querydsl.core.types.ConstantImpl;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.CaseBuilder;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.core.types.dsl.StringExpression;
import com.querydsl.core.types.dsl.StringExpressions;
import com.querydsl.core.types.dsl.StringTemplate;
import com.querydsl.jpa.impl.JPAQueryFactory;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class BucketRepositoryForCursorImpl implements BucketRepositoryForCursor {

	private final JPAQueryFactory jpaQueryFactory;

	@Override
	public List<BucketSummary> findAllByCursor(
		final Long memberId,
		final Hobby hobby,
		final String cursorId,
		final int pageSize
	) {
		List<Long> bucketIds = jpaQueryFactory
			.select(bucket.id)
			.from(bucket)
			.where(
				cursorIdCondition(cursorId),
				bucket.memberId.eq(memberId),
				bucket.hobby.eq(hobby)
			)
			.orderBy(decrease())
			.limit(pageSize)
			.fetch();

		return jpaQueryFactory
			.selectFrom(bucketItem)
			.join(item).on(bucketItem.item.id.eq(item.id))
			.where(bucketItem.bucket.id.in(bucketIds))
			.orderBy(decrease())
			.transform(groupBy(bucket.id)
				.list(Projections.constructor(BucketSummary.class,
						bucket.id,
						bucket.bucketInfo.name,
						bucket.bucketInfo.budget,
						bucket.createdAt,
						list(Projections.constructor(ItemImage.class,
							item.id,
							item.image
						))
					)
				));
	}

	@Override
	public List<BucketMemberItemSummary> findBucketMemberItemsByCursor(
		final List<Long> itemIdsFromBucketItem,
		final Long memberId,
		final String cursorId,
		final int pageSize
	) {
		List<Long> itemIdsFromMemberItem = jpaQueryFactory
			.select(memberItem.item.id)
			.from(memberItem)
			.where(
				cursorIdConditionFromMemberItem(cursorId),
				memberItem.memberId.eq(memberId)
			)
			.orderBy(new OrderSpecifier<>(Order.DESC, memberItem.createdAt))
			.limit(pageSize)
			.fetch();

		BooleanExpression isSelected = new CaseBuilder()
			.when(item.id.in(itemIdsFromBucketItem))
			.then(true)
			.otherwise(false);

		return jpaQueryFactory
			.selectFrom(item)
			.where(item.id.in(itemIdsFromMemberItem))
			.orderBy(new OrderSpecifier<>(Order.DESC, item.createdAt))
			.transform(groupBy(item.id)
				.list(Projections.constructor(BucketMemberItemSummary.class,
					generateMemberItemCursorId(),
					item.id,
					item.name,
					item.price,
					item.image,
					isSelected,
					item.createdAt
				))
			);
	}

	private OrderSpecifier<LocalDateTime> decrease() {
		return new OrderSpecifier<>(Order.DESC, bucket.createdAt);
	}

	private BooleanExpression cursorIdCondition(final String cursorId) {
		if (cursorId == null) {
			return null;
		}

		StringTemplate dateCursor = Expressions.stringTemplate(
			"DATE_FORMAT({0}, {1})",
			bucket.createdAt,
			ConstantImpl.create("%Y%m%d%H%i%s")
		);

		return dateCursor.concat(StringExpressions.lpad(
				bucket.id.stringValue(), 8, '0'
			))
			.lt(cursorId);
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
