package com.programmers.lime.domains.inventory.domain;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.test.util.ReflectionTestUtils;

import com.programmers.lime.common.model.Hobby;
import com.programmers.lime.common.model.ItemIdRegistry;
import com.programmers.lime.common.model.ItemIdRegistryBuilder;
import com.programmers.lime.domains.inventory.model.InventoryReviewItemSummary;
import com.programmers.lime.domains.item.domain.ItemBuilder;
import com.programmers.lime.domains.item.model.ItemInfo;

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

	public static void setModifiedDate(
		final Inventory inventory,
		final LocalDateTime modifiedDate
	) {
		ReflectionTestUtils.setField(
			inventory,
			"modifiedAt",
			modifiedDate
		);
	}

	public static void setModifiedDate(
		final List<InventoryItem> inventoryItems,
		final LocalDateTime modifiedDate
	) {
		for (InventoryItem inventoryItem : inventoryItems) {
			ReflectionTestUtils.setField(
				inventoryItem,
				"modifiedAt",
				modifiedDate
			);
		}
	}

	public static List<InventoryReviewItemSummary> buildInventoryReviewItemSummaries(final List<Long> itemIds) {
		return itemIds.stream()
			.map(itemId -> {
				return new InventoryReviewItemSummary(
					"202301010000000000000" + itemId,
					true,
					LocalDateTime.now(),
					ItemInfo.from(ItemBuilder.build(itemId))
				);
			})
			.toList();
	}
}
