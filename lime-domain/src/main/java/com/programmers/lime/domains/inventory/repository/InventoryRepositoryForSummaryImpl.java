package com.programmers.lime.domains.inventory.repository;

import static com.programmers.lime.domains.inventory.domain.QInventory.*;
import static com.programmers.lime.domains.inventory.domain.QInventoryItem.*;
import static com.programmers.lime.domains.item.domain.QItem.*;
import static com.querydsl.core.group.GroupBy.*;

import java.util.List;

import com.programmers.lime.domains.bucket.model.ItemImage;
import com.programmers.lime.domains.inventory.model.InventoryInfoSummary;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Projections;
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

		return jpaQueryFactory
			.selectFrom(inventoryItem)
			.join(item).on(inventoryItem.item.id.eq(item.id))
			.where(inventoryItem.inventory.id.in(inventoryIds))
			.orderBy(new OrderSpecifier<>(Order.DESC, inventory.createdAt))
			.transform(groupBy(inventory.id)
				.list(Projections.constructor(InventoryInfoSummary.class,
						inventory.hobby,
						inventory.id,
						sum(item.price),
						list(Projections.constructor(ItemImage.class,
							item.id,
							item.image
						))
					)
				));
	}
}
