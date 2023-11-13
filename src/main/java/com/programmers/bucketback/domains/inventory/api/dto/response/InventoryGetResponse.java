package com.programmers.bucketback.domains.inventory.api.dto.response;

import java.util.List;

import com.programmers.bucketback.domains.common.Hobby;
import com.programmers.bucketback.domains.inventory.application.dto.GetInventoryServiceResponse;

import lombok.Builder;

@Builder
public record InventoryGetResponse(
	Long memberId,
	Hobby hobby,
	int itemCount,
	List<InventoryItemGetResponse> inventoryItemGetResponses
) {
	public static InventoryGetResponse from(final GetInventoryServiceResponse serviceResponse) {
		return InventoryGetResponse.builder()
			.memberId(serviceResponse.memberId())
			.hobby(serviceResponse.hobby())
			.itemCount(serviceResponse.itemCount())
			.inventoryItemGetResponses(serviceResponse.inventoryItemGetResponses())
			.build();
	}
}
