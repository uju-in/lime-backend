package com.programmers.bucketback.domains.inventory.repository;

import java.util.List;

import com.programmers.bucketback.domains.inventory.api.dto.response.InventoryInfoSummary;
import com.programmers.bucketback.domains.inventory.application.InventoryReviewedItem;

public interface InventoryRepositoryForSummary {
	List<InventoryInfoSummary> findInfoSummaries(final Long memberId);

	List<InventoryReviewedItem> findReviewedItems(
		final List<Long> reviewedItemIds,
		final List<Long> itemIdsInInventory,
		final String cursorId,
		final Integer pageSize
	);
}
