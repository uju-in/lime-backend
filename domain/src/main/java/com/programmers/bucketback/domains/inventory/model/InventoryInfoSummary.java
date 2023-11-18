package com.programmers.bucketback.domains.inventory.model;

import java.util.List;

import com.programmers.bucketback.Hobby;
import com.programmers.bucketback.domains.bucket.model.ItemImage;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class InventoryInfoSummary {
	private Hobby hobby;
	private Long inventoryId;
	private int inventoryTotalPrice;
	private List<ItemImage> itemImages;

	public void setItemImages(final List<ItemImage> subList) {
		this.itemImages = subList;
	}
}
