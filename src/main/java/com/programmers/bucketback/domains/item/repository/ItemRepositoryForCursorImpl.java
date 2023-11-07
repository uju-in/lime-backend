package com.programmers.bucketback.domains.item.repository;

import static com.programmers.bucketback.domains.item.domain.QItem.*;

import java.time.LocalDateTime;
import java.util.List;

import com.programmers.bucketback.domains.item.application.vo.ItemSummary;
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
public class ItemRepositoryForCursorImpl implements ItemRepositoryForCursor {

	private final JPAQueryFactory jpaQueryFactory;

	@Override
	public List<ItemSummary> findAllByCursor(
		final String keyword,
		final String cursorId,
		final int pageSize
	) {
		return jpaQueryFactory
			.select(
				Projections.constructor(
					ItemSummary.class,
					item.id,
					item.name,
					item.price,
					item.image,
					item.createdAt
				)
			).from(item)
			.where(
				cursorIdCondition(cursorId),
				item.name.contains(keyword)
			).orderBy(decrease())
			.fetch();
	}

	private OrderSpecifier<LocalDateTime> decrease() {
		return new OrderSpecifier<>(Order.DESC, item.createdAt);
	}

	private BooleanExpression cursorIdCondition(final String cursorId) {
		if (cursorId == null) {
			return null;
		}

		StringTemplate dateCursor = Expressions.stringTemplate(
			"DATE_FORMAT({0}, {1})",
			item.createdAt,
			ConstantImpl.create("%Y%m%d%H%i%s")
		);

		return dateCursor.concat(StringExpressions.lpad(
				item.id.stringValue(), 8, '0'
			))
			.lt(cursorId);
	}
}
