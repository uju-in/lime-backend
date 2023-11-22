package com.programmers.bucketback.domains.inventory.api.dto.response;

import java.util.List;

import com.programmers.bucketback.domains.inventory.model.InventoryGetServiceResponse;
import com.programmers.bucketback.domains.inventory.model.InventoryItemGetResponse;

import lombok.Builder;

@Builder
public record InventoryGetResponse(
	Long memberId,
	String hobby,
	int itemCount,
	List<InventoryItemGetResponse> inventoryItemInfos
) {
	public static InventoryGetResponse from(final InventoryGetServiceResponse serviceResponse) {
		return InventoryGetResponse.builder()
			.memberId(serviceResponse.memberId())
			.hobby(serviceResponse.hobby().getHobbyValue())
			.itemCount(serviceResponse.itemCount())
			.inventoryItemInfos(serviceResponse.inventoryItemInfos())
			.build();
	}
}
