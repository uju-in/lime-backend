package com.programmers.lime.domains.inventory.implementation;

import java.util.List;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.programmers.lime.common.model.ItemIdRegistry;
import com.programmers.lime.domains.inventory.domain.Inventory;
import com.programmers.lime.domains.inventory.domain.InventoryItem;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class InventoryModifier {

	private final InventoryRemover inventoryRemover;
	private final InventoryAppender inventoryAppender;
	private final InventoryReader inventoryReader;

	/** 인벤토리 수정 */
	@Transactional
	public void modify(
		final Long memberId,
		final Long inventoryId,
		final ItemIdRegistry registry
	) {
		Inventory inventory = inventoryReader.read(inventoryId, memberId);

		inventory.removeInventoryItems();
		inventoryRemover.removeInventoryItems(inventory.getId());

		List<InventoryItem> inventoryItems = inventoryAppender.createInventoryItem(registry);
		inventoryItems.forEach(inventory::addInventoryItem);
	}
}
