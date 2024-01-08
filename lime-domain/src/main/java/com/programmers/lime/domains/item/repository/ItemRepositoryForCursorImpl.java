package com.programmers.lime.domains.item.repository;

import static com.programmers.lime.domains.item.domain.QItem.*;
import static com.querydsl.core.group.GroupBy.*;

import java.time.LocalDateTime;
import java.util.List;

import com.programmers.lime.common.model.Hobby;
import com.programmers.lime.domains.inventory.model.InventoryReviewItemSummary;
import com.programmers.lime.domains.item.model.ItemCursorSummary;
import com.programmers.lime.domains.item.model.ItemInfo;
import com.programmers.lime.domains.item.model.ItemSummary;
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
public class ItemRepositoryForCursorImpl implements ItemRepositoryForCursor {

	private final JPAQueryFactory jpaQueryFactory;

	@Override
	public List<ItemCursorSummary> findAllByCursor(
		final String keyword,
		final String cursorId,
		final int pageSize
	) {
		return jpaQueryFactory
			.select(
				Projections.constructor(
					ItemCursorSummary.class,
					generateItemCursorId(),
					Projections.constructor(
						ItemSummary.class,
						item.id,
						item.name,
						item.price,
						item.image,
						item.createdAt
					)
				)
			).from(item)
			.where(
				cursorIdCondition(cursorId),
				item.name.contains(keyword)
			).orderBy(decrease(), item.id.desc())
			.limit(pageSize)
			.fetch();
	}

	@Override
	public List<InventoryReviewItemSummary> findReviewedItemByCursor(
		final List<Long> itemIdsFromReview,
		final List<Long> itemIdsFromInventory,
		final Hobby hobby,
		final String cursorId,
		final int pageSize
	) {
		return jpaQueryFactory
			.selectFrom(item)
			.where(
				cursorIdCondition(cursorId),
				item.id.in(itemIdsFromReview),
				hobbyCondition(hobby)
			)
			.orderBy(decrease(), item.id.desc())
			.limit(pageSize)
			.transform(groupBy(item.id)
				.list(Projections.constructor(InventoryReviewItemSummary.class,
					generateItemCursorId(),
					isSelected(itemIdsFromInventory),
					item.createdAt,
					Projections.constructor(ItemInfo.class,
						item.id,
						item.name,
						item.price,
						item.image
					))
				)
			);
	}

	private BooleanExpression isSelected(final List<Long> itemIdsFromInventory) {
		if (itemIdsFromInventory == null) {
			return Expressions.asBoolean(false).isTrue();
		}

		return new CaseBuilder()
			.when(item.id.in(itemIdsFromInventory))
			.then(true)
			.otherwise(false);
	}

	private OrderSpecifier<LocalDateTime> decrease() {
		return new OrderSpecifier<>(Order.DESC, item.createdAt);
	}

	private BooleanExpression hobbyCondition(final Hobby hobby) {
		if (hobby == null) {
			return null;
		}

		return item.hobby.eq(hobby);
	}

	private BooleanExpression cursorIdCondition(final String cursorId) {
		if (cursorId == null) {
			return null;
		}

		return generateItemCursorId()
			.lt(cursorId);
	}

	private StringExpression generateItemCursorId() {
		return Expressions.stringTemplate(
				"DATE_FORMAT({0}, {1})",
				item.createdAt,
				ConstantImpl.create("%Y%m%d%H%i%s"))
			.concat(StringExpressions.lpad(
				item.id.stringValue(), 8, '0'
			));
	}
}
