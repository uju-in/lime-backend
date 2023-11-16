package com.programmers.bucketback.domains.inventory.model;

import java.util.List;

import com.programmers.bucketback.Hobby;

import lombok.Builder;

@Builder
public record InventoryGetResponse(
	Long memberId,
	Hobby hobby,
	int itemCount,
	List<InventoryItemGetResponse> inventoryItemInfos
) {
	public static InventoryGetResponse from(final InventoryGetServiceResponse serviceResponse) {
		return InventoryGetResponse.builder()
			.memberId(serviceResponse.memberId())
			.hobby(serviceResponse.hobby())
			.itemCount(serviceResponse.itemCount())
			.inventoryItemInfos(serviceResponse.inventoryItemInfos())
			.build();
	}
}
