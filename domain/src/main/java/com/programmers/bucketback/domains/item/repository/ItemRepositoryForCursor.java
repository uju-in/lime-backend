package com.programmers.bucketback.domains.item.repository;

import java.util.List;

import com.programmers.bucketback.domains.inventory.model.InventoryReviewItemSummary;
import com.programmers.bucketback.domains.item.model.ItemCursorSummary;

public interface ItemRepositoryForCursor {
	List<ItemCursorSummary> findAllByCursor(
		String keyword,
		String cursorId,
		int pageSize
	);

	List<InventoryReviewItemSummary> findReviewedItemByCursor(
		final List<Long> itemIdsFromReview,
		final List<Long> itemIdsFromInventory,
		final String cursorId,
		final int pageSize
	);
}
