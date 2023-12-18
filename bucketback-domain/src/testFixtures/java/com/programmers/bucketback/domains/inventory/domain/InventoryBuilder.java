package com.programmers.bucketback.domains.inventory.domain;

import java.util.List;

import org.springframework.test.util.ReflectionTestUtils;

import com.programmers.bucketback.common.model.Hobby;
import com.programmers.bucketback.common.model.ItemIdRegistry;
import com.programmers.bucketback.common.model.ItemIdRegistryBuilder;
import com.programmers.bucketback.domains.item.domain.ItemBuilder;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class InventoryBuilder {

	public static Inventory build(
		final Hobby hobby,
		final ItemIdRegistry itemIdRegistry
	) {
		Long memberId = 1L;
		Inventory inventory = new Inventory(memberId, hobby);
		List<InventoryItem> inventoryItems = buildInventoryItems(itemIdRegistry);
		inventoryItems.forEach(inventory::addInventoryItem);

		setInventoryId(inventory);

		return inventory;
	}

	public static Inventory build() {
		Long memberId = 1L;
		Inventory inventory = new Inventory(memberId, Hobby.BASKETBALL);

		ItemIdRegistry itemIdRegistry = ItemIdRegistryBuilder.build();
		List<InventoryItem> inventoryItems = buildInventoryItems(itemIdRegistry);
		inventoryItems.forEach(inventory::addInventoryItem);

		setInventoryId(inventory);

		return inventory;
	}

	public static List<InventoryItem> buildInventoryItems(ItemIdRegistry itemIdRegistry) {
		return itemIdRegistry.itemIds().stream()
			.map(itemId -> new InventoryItem(ItemBuilder.build(itemId)))
			.toList();
	}

	private static void setInventoryId(final Inventory inventory) {
		ReflectionTestUtils.setField(
			inventory,
			"id",
			1L
		);
	}
}
