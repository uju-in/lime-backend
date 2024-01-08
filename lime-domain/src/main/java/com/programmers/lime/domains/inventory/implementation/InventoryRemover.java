package com.programmers.lime.domains.inventory.implementation;

import java.util.List;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.programmers.lime.domains.inventory.domain.Inventory;
import com.programmers.lime.domains.inventory.domain.InventoryItem;
import com.programmers.lime.domains.inventory.repository.InventoryItemRepository;
import com.programmers.lime.domains.inventory.repository.InventoryRepository;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class InventoryRemover {

	private final InventoryRepository inventoryRepository;
	private final InventoryItemRepository inventoryItemRepository;
	private final InventoryReader inventoryReader;

	/** 인벤토리 삭제 */
	@Transactional
	public void remove(
		final Long inventoryId,
		final Long memberId
	) {
		Inventory inventory = inventoryReader.read(inventoryId, memberId);

		inventoryRepository.delete(inventory);
	}

	/** 인벤토리 아이템 삭제 */
	@Transactional
	public void removeInventoryItems(final Long inventoryId) {
		List<InventoryItem> inventoryItems = inventoryReader.inventoryItemRead(inventoryId);

		inventoryItemRepository.deleteAllInBatch(inventoryItems);
	}
}
