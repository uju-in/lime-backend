package com.programmers.bucketback.domains.bucket.repository;

import static com.programmers.bucketback.domains.bucket.domain.QBucket.*;
import static com.programmers.bucketback.domains.bucket.domain.QBucketItem.*;
import static com.programmers.bucketback.domains.item.domain.QItem.*;
import static com.querydsl.core.group.GroupBy.*;

import java.time.LocalDateTime;
import java.util.List;

import com.programmers.bucketback.domains.bucket.application.vo.BucketSummary;
import com.programmers.bucketback.domains.bucket.application.vo.ItemImage;
import com.programmers.bucketback.domains.common.Hobby;
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
				bucket.bucketInfo.hobby.eq(hobby)
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
						generateBucketCursorId(),
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

	private OrderSpecifier<LocalDateTime> decrease() {
		return new OrderSpecifier<>(Order.DESC, bucket.createdAt);
	}

	private BooleanExpression cursorIdCondition(final String cursorId) {
		if (cursorId == null) {
			return null;
		}

		return generateBucketCursorId().lt(cursorId);
	}

	private StringExpression generateBucketCursorId() {
		return Expressions.stringTemplate(
			"DATE_FORMAT({0}, {1})",
			bucket.createdAt,
			ConstantImpl.create("%Y%m%d%H%i%s")
		).concat(StringExpressions.lpad(
			bucket.id.stringValue(), 8, '0'
		));
	}

}
