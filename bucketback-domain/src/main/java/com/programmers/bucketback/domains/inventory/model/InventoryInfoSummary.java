package com.programmers.bucketback.domains.inventory.model;

import java.util.List;

import com.programmers.bucketback.common.model.Hobby;
import com.programmers.bucketback.domains.bucket.model.ItemImage;

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
		this.hobby = hobby.getHobbyValue();
		this.inventoryId = inventoryId;
		this.inventoryTotalPrice = inventoryTotalPrice;
		this.itemImages = itemImages;
	}

	public void setItemImages(final List<ItemImage> subList) {
		this.itemImages = subList;
	}
}
