package com.programmers.lime.domains.item.repository;

import java.util.List;

import com.programmers.lime.common.model.Hobby;
import com.programmers.lime.domains.inventory.model.InventoryReviewItemSummary;
import com.programmers.lime.domains.item.model.ItemCursorSummary;
import com.programmers.lime.domains.item.model.ItemSortCondition;

public interface ItemRepositoryForCursor {
	List<ItemCursorSummary> findAllByCursor(
		final String keyword,
		final String cursorId,
		final int pageSize,
		final ItemSortCondition itemSortCondition,
		final Hobby hobby
	);

	List<InventoryReviewItemSummary> findReviewedItemByCursor(
		final List<Long> itemIdsFromReview,
		final List<Long> itemIdsFromInventory,
		final Hobby hobby,
		final String cursorId,
		final int pageSize
	);
}
