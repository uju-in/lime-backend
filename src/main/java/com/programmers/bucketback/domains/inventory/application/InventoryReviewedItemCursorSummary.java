package com.programmers.bucketback.domains.inventory.application;

import java.time.LocalDateTime;

import com.programmers.bucketback.domains.item.application.vo.ItemInfo;

import lombok.Builder;

@Builder
public record InventoryReviewedItemCursorSummary(
	String cursorId,
	ItemInfo itemInfo,
	boolean isSelected,
	LocalDateTime createdAt
) {
	public static InventoryReviewedItemCursorSummary of(
		final String cursorId,
		final InventoryReviewedItem inventoryReviewedItem
	) {
		return InventoryReviewedItemCursorSummary.builder()
			.cursorId(cursorId)
			.itemInfo(ItemInfo.builder()
				.id(inventoryReviewedItem.getItemId())
				.name(inventoryReviewedItem.getName())
				.price(inventoryReviewedItem.getPrice())
				.image(inventoryReviewedItem.getImage())
				.build())
			.isSelected(inventoryReviewedItem.isSelected())
			.createdAt(inventoryReviewedItem.getCreatedAt())
			.build();
	}
}
