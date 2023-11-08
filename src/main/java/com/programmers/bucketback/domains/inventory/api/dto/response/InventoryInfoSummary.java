package com.programmers.bucketback.domains.inventory.api.dto.response;

import java.util.List;

import com.programmers.bucketback.domains.bucket.application.vo.ItemImage;
import com.programmers.bucketback.domains.common.Hobby;

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
	private List<ItemImage> itemImages; //이미지 개수 3개로 제한

	public void setItemImages(final List<ItemImage> subList) {
		this.itemImages = subList;
	}
}
