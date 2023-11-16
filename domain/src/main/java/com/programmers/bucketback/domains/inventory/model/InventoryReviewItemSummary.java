package com.programmers.bucketback.domains.inventory.model;

import java.time.LocalDateTime;

import com.programmers.bucketback.domains.item.model.ItemInfo;

public record InventoryReviewItemSummary(
	String cursorId,
	boolean isSelected,
	LocalDateTime createdAt,
	ItemInfo itemInfo
) {
}
