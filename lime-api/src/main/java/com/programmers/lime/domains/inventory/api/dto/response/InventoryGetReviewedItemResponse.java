package com.programmers.lime.domains.inventory.api.dto.response;

import java.util.List;

import com.programmers.lime.common.cursor.CursorSummary;
import com.programmers.lime.domains.inventory.model.InventoryReviewItemSummary;

public record InventoryGetReviewedItemResponse(
	String nextCursorId,
	int totalCount,
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
