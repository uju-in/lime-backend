package com.programmers.bucketback.domains.inventory.application;

import java.util.List;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.programmers.bucketback.domains.bucket.application.vo.ItemIdRegistry;
import com.programmers.bucketback.domains.inventory.domain.Inventory;
import com.programmers.bucketback.domains.inventory.domain.InventoryItem;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class InventoryModifier {

	private final InventoryRemover inventoryRemover;
	private final InventoryAppender inventoryAppender;

	/** 인벤토리 수정 */
	@Transactional
	public void modify(
		final Inventory inventory,
		final ItemIdRegistry registry
	) {
		inventory.removeInventoryItems();
		inventoryRemover.removeInventoryItems(inventory.getId());

		List<InventoryItem> inventoryItems = inventoryAppender.createInventoryItem(registry);
		inventoryItems.forEach(inventory::addInventoryItem);
	}
}
