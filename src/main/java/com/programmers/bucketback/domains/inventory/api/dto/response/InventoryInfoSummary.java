package com.programmers.bucketback.domains.inventory.api.dto.response;

import java.util.List;

import com.programmers.bucketback.domains.bucket.application.vo.ItemImage;
import com.programmers.bucketback.domains.common.Hobby;

import lombok.Builder;
import lombok.Getter;

@Getter
public class InventoryInfoSummary{
	private final Hobby hobby;
	private final Long inventoryId;
	private final int inventoryTotalPrice; //인벤토리 가격의 총합
	private List<ItemImage> itemImages; //이미지 개수 3개로 제한해야함

	@Builder
	public InventoryInfoSummary(
		final Hobby hobby,
		final Long inventoryId,
		final int inventoryTotalPrice,
		final List<ItemImage> itemImages
	) {
		this.hobby = hobby;
		this.inventoryId = inventoryId;
		this.inventoryTotalPrice = inventoryTotalPrice;
		this.itemImages = itemImages;
	}

	public void setItemImages(final List<ItemImage> subList) {
		this.itemImages = subList;
	}
}
