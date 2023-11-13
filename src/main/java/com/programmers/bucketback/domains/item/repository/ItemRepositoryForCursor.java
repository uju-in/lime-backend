package com.programmers.bucketback.domains.item.repository;

import java.util.List;

import com.programmers.bucketback.domains.inventory.application.InventoryReviewItemSummary;
import com.programmers.bucketback.domains.item.application.vo.ItemSummary;

public interface ItemRepositoryForCursor {
	List<ItemSummary> findAllByCursor(
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