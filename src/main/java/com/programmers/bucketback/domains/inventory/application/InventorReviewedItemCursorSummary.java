package com.programmers.bucketback.domains.inventory.application;

import java.util.List;

public record InventorReviewedItemCursorSummary(
	String nextCursorId,
	int summaryCount,
	List<InventoryReviewItemSummary> summaries
) {
}
