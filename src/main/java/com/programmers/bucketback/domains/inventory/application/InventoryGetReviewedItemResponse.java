package com.programmers.bucketback.domains.inventory.application;

import java.util.List;

public record InventoryGetReviewedItemResponse(
	String nextCursorId,
	int itemCount,
	List<InventoryReviewedItemCursorSummary> inventoryReviewedItemCursorSummaries
) {
}
