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
import com.querydsl.core.types.dsl.StringExpressions;
import com.querydsl.core.types.dsl.StringTemplate;
import com.querydsl.jpa.impl.JPAQueryFactory;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class BucketRepositoryForCursorImpl implements BucketRepositoryForCursor{

	private final JPAQueryFactory jpaQueryFactory;

	@Override
	public List<BucketSummary> findAllByCursor(
		Long memberId,
		Hobby hobby,
		String cursorId,
		int pageSize
	) {
		return jpaQueryFactory.selectFrom(bucket)
			.join(bucketItem).on(bucket.id.eq(bucketItem.bucket.id))
			.join(item).on(bucketItem.item.id.eq(item.id))
			.where(
					cursorIdCondition(cursorId),
					isDeclined(),
					bucket.memberId.eq(memberId),
					bucket.hobby.eq(hobby)
			)
			// .limit(pageSize)
			.orderBy(decrease())
			.transform(
				groupBy(bucket.id).list(
					Projections.constructor(BucketSummary.class,
						bucket.id, bucket.bucketInfo.name, bucket.bucketInfo.budget, bucket.createdAt,
						list(Projections.constructor(ItemImage.class, item.id, item.url)
					))
				)
			);
	}

	private OrderSpecifier decrease() {
		return new OrderSpecifier(Order.DESC, bucket.createdAt);
	}

	private BooleanExpression isDeclined() {
		return bucket.createdAt.lt(LocalDateTime.now());
	}

	private BooleanExpression cursorIdCondition(String cursorId) {
		if (cursorId == null) {

			return null;
		}

		StringTemplate stringTemplate = Expressions.stringTemplate(
			"DATE_FORMAT({0}, {1})",
			bucket.createdAt
			,ConstantImpl.create("%Y%m%d%H%i%s")
		);

		return stringTemplate.concat(StringExpressions.lpad(
				bucket.id.stringValue(), 8,
				'0'
			))
			.lt(cursorId);
	}

}
