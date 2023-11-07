package com.programmers.bucketback.domains.inventory.application;

import java.util.List;

import org.springframework.stereotype.Component;

import com.programmers.bucketback.domains.inventory.application.vo.InventoryUpdateContent;
import com.programmers.bucketback.domains.inventory.domain.Inventory;
import com.programmers.bucketback.domains.inventory.domain.InventoryItem;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class InventoryModifier {

	private final InventoryRemover inventoryRemover;
	private final InventoryAppender inventoryAppender;

	/** 인벤토리 수정 */
	public void modify(
		final Inventory inventory,
		final InventoryUpdateContent content
	) {
		inventory.removeInventoryItems();
		inventoryRemover.removeInventoryItems(inventory.getId());

		List<InventoryItem> inventoryItems = inventoryAppender.createInventoryItem(content.itemIds());
		inventoryItems.forEach(inventory::addInventoryItem);
	}
}
