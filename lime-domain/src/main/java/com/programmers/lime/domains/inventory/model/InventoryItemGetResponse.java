package com.programmers.lime.domains.inventory.model;

import com.programmers.lime.domains.item.model.ItemInfo;

import lombok.Builder;

@Builder
public record InventoryItemGetResponse(
	Long id,
	String name,
	Integer price,
	String image,
	String itemUrl
) {
	public static InventoryItemGetResponse of(
		final ItemInfo itemInfo,
		final String itemUrl
	) {
		return InventoryItemGetResponse.builder()
			.id(itemInfo.id())
			.name(itemInfo.name())
			.price(itemInfo.price())
			.image(itemInfo.image())
			.itemUrl(itemUrl)
			.build();
	}
}
