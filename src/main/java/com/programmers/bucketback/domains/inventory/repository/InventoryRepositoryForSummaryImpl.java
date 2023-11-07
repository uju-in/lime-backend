package com.programmers.bucketback.domains.inventory.repository;

import static com.programmers.bucketback.domains.inventory.domain.QInventory.*;
import static com.programmers.bucketback.domains.inventory.domain.QInventoryItem.*;
import static com.programmers.bucketback.domains.item.domain.QItem.*;
import static com.querydsl.core.group.GroupBy.*;

import java.util.List;

import com.programmers.bucketback.domains.bucket.application.vo.ItemImage;
import com.programmers.bucketback.domains.inventory.api.dto.response.InventoryInfoSummary;
import com.programmers.bucketback.domains.inventory.application.InventoryReviewedItem;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.CaseBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class InventoryRepositoryForSummaryImpl implements InventoryRepositoryForSummary {

	private final JPAQueryFactory jpaQueryFactory;

	@Override
	public List<InventoryInfoSummary> findInfoSummaries(final Long memberId) {

		List<Long> inventoryIds = jpaQueryFactory
			.select(inventory.id)
			.from(inventory)
			.where(inventory.memberId.eq(memberId))
			.fetch();

		List<InventoryInfoSummary> transform = jpaQueryFactory
			.selectFrom(inventoryItem)
			.join(item).on(inventoryItem.item.id.eq(item.id))
			.where(inventoryItem.inventory.id.in(inventoryIds))
			.orderBy(new OrderSpecifier<>(Order.DESC, inventory.createdAt))
			.transform(groupBy(inventory.id)
				.list(Projections.constructor(InventoryInfoSummary.class,
						inventory.hobby,
						inventory.id,
						sum(item.price), //refactor
						list(Projections.constructor(ItemImage.class,
							item.id,
							item.image
						))
					)
				));

		return transform;
	}

	@Override
	public List<InventoryReviewedItem> findReviewedItems(
		final List<Long> reviewedItemIds,
		final List<Long> itemIdsInInventory,
		final String cursorId,
		final Integer pageSize
	) {
		BooleanExpression isSelected = new CaseBuilder()
			.when(item.id.in(itemIdsInInventory))
			.then(true)
			.otherwise(false);

		return jpaQueryFactory
			.selectFrom(item)
			.where(item.id.in(reviewedItemIds))
			.orderBy(new OrderSpecifier<>(Order.DESC, item.createdAt))
			.transform(groupBy(item.id)
				.list(Projections.constructor(InventoryReviewedItem.class,
					item.id,
					item.name,
					item.price,
					item.image,
					isSelected,
					item.createdAt))
			);
	}
}
