package com.programmers.bucketback.domains.inventory.application;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.programmers.bucketback.domains.inventory.application.vo.InventoryContent;
import com.programmers.bucketback.domains.inventory.domain.Inventory;
import com.programmers.bucketback.domains.inventory.domain.InventoryItem;
import com.programmers.bucketback.domains.inventory.repository.InventoryRepository;
import com.programmers.bucketback.domains.item.application.ItemReader;
import com.programmers.bucketback.domains.item.domain.Item;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class InventoryAppender {

	private final InventoryRepository inventoryRepository;
	private final ItemReader itemReader;

	/** 인벤토리 생성 */
	@Transactional
	public void append(
		final Long memberId,
		final InventoryContent content
	) {
		List<InventoryItem> inventoryItems = createInventoryItem(content.itemIds());
		Inventory inventory = new Inventory(memberId, content.hobby());
		inventoryItems.forEach(inventory::addInventoryItem);

		inventoryRepository.save(inventory);
	}

	public List<InventoryItem> createInventoryItem(final List<Long> itemIds) {
		return itemIds.stream()
			.map(id -> {
				Item item = itemReader.read(id);
				InventoryItem inventoryItem = new InventoryItem(item);

				return inventoryItem;
			}).distinct()
			.collect(Collectors.toList());
	}
}
