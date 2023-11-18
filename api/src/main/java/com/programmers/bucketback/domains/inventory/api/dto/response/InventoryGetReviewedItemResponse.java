package com.programmers.bucketback.domains.inventory.api.dto.response;

import java.util.List;

import com.programmers.bucketback.common.cursor.CursorSummary;
import com.programmers.bucketback.domains.inventory.model.InventoryReviewItemSummary;

public record InventoryGetReviewedItemResponse(
	String nextCursorId,
	int summaryCount,
	List<InventoryReviewItemSummary> reviewedItems
) {
	public static InventoryGetReviewedItemResponse from(
		final CursorSummary<InventoryReviewItemSummary> summary
	) {
		return new InventoryGetReviewedItemResponse(
			summary.nextCursorId(),
			summary.summaryCount(),
			summary.summaries()
		);
	}
}
