package com.programmers.bucketback.domains.inventory.application;

import java.time.LocalDateTime;

import com.programmers.bucketback.domains.item.application.vo.ItemInfo;

public record InventoryReviewItemSummary(
	String cursorId,
	boolean isSelected,
	LocalDateTime createdAt,
	ItemInfo itemInfo
) {
}
