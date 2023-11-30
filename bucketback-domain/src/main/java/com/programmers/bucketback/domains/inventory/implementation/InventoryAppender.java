package com.programmers.bucketback.domains.inventory.implementation;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.programmers.bucketback.common.model.Hobby;
import com.programmers.bucketback.common.model.ItemIdRegistry;
import com.programmers.bucketback.domains.inventory.domain.Inventory;
import com.programmers.bucketback.domains.inventory.domain.InventoryItem;
import com.programmers.bucketback.domains.inventory.repository.InventoryRepository;
import com.programmers.bucketback.domains.item.domain.Item;
import com.programmers.bucketback.domains.item.implementation.ItemReader;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class InventoryAppender {

	private final InventoryRepository inventoryRepository;
	private final ItemReader itemReader;

	/** 인벤토리 생성 */
	@Transactional
	public Long append(
		final Long memberId,
		final Hobby hobby,
		final ItemIdRegistry registry
	) {
		List<InventoryItem> inventoryItems = createInventoryItem(registry);
		Inventory inventory = new Inventory(memberId, hobby);
		inventoryItems.forEach(inventory::addInventoryItem);

		return inventoryRepository.save(inventory).getId();
	}

	/** 인벤토리 아이템 생성 */
	public List<InventoryItem> createInventoryItem(final ItemIdRegistry registry) {
		return registry.itemIds().stream()
			.distinct()
			.map(id -> {
				Item item = itemReader.read(id);
				InventoryItem inventoryItem = new InventoryItem(item);

				return inventoryItem;
			})
			.collect(Collectors.toList());
	}
}
