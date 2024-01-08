package com.programmers.lime.domains.inventory.model;

import java.util.List;

import com.programmers.lime.domains.inventory.domain.Inventory;

public record InventoryProfile(
	Long id,
	String hobby,
	List<String> images
) {
	public static InventoryProfile of(
		final Inventory inventory,
		final List<String> itemImages
	) {
		return new InventoryProfile(
			inventory.getId(),
			inventory.getHobby().getHobbyValue(),
			itemImages
		);
	}
}
