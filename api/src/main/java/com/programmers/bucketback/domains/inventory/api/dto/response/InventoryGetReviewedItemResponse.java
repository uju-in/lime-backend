package com.programmers.bucketback.domains.inventory.api.dto.response;

import com.programmers.bucketback.domains.inventory.model.InventorReviewedItemCursorSummary;

public record InventoryGetReviewedItemResponse(
	InventorReviewedItemCursorSummary inventorReviewedItemCursorSummary
) {
}
