package com.programmers.bucketback.domains.inventory.domain;

import org.springframework.test.util.ReflectionTestUtils;

import com.programmers.bucketback.common.model.Hobby;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class InventoryBuilder {

	public static Inventory build() {
		Long memberId = 1L;
		Inventory inventory = new Inventory(memberId, Hobby.BASKETBALL);

		setInventoryId(inventory);

		return inventory;
	}

	/** 차후 사용 예정 */
	// public static InventoryItem build(final Inventory inventory) {
	// 	InventoryItem inventoryItem = InventoryItem.builder()
	// 		.inventory(inventory)
	// 		.itemId(1L)
	// 		.build();
	//
	// 	setInventoryItemId(inventoryItem);
	//
	// 	return inventoryItem;
	// }
	//
	// private static void setInventoryItemId(final InventoryItem inventoryItem) {
	// 	ReflectionTestUtils.setField(
	// 		inventoryItem,
	// 		"id",
	// 		1L
	// 	);
	// }
	private static void setInventoryId(final Inventory inventory) {
		ReflectionTestUtils.setField(
			inventory,
			"id",
			1L
		);
	}
}
