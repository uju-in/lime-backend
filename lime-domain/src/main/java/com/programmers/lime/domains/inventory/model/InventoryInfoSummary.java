package com.programmers.lime.domains.inventory.model;

import java.util.List;

import com.programmers.lime.common.model.Hobby;
import com.programmers.lime.domains.bucket.model.ItemImage;

import lombok.Getter;

@Getter
public class InventoryInfoSummary {
	private String hobby;
	private Long inventoryId;
	private int inventoryTotalPrice;
	private List<ItemImage> itemImages;

	public InventoryInfoSummary(
		Hobby hobby,
		Long inventoryId,
		int inventoryTotalPrice,
		List<ItemImage> itemImages
	) {
		this.hobby = hobby.getName();
		this.inventoryId = inventoryId;
		this.inventoryTotalPrice = inventoryTotalPrice;
		this.itemImages = itemImages;
	}

	public void setItemImages(final List<ItemImage> subList) {
		this.itemImages = subList;
	}
}
