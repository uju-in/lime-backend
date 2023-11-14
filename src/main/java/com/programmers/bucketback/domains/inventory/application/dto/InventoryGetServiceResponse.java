package com.programmers.bucketback.domains.inventory.application.dto;

import java.util.List;

import com.programmers.bucketback.domains.common.Hobby;
import com.programmers.bucketback.domains.inventory.api.dto.response.InventoryItemGetResponse;
import com.programmers.bucketback.domains.inventory.domain.Inventory;

import lombok.Builder;

@Builder
public record InventoryGetServiceResponse(
	Long memberId,
	Hobby hobby,
	Integer itemCount,
	List<InventoryItemGetResponse> inventoryItemInfos
) {
	public static InventoryGetServiceResponse of(
		final Inventory inventory,
		final List<InventoryItemGetResponse> inventoryItemGetResponses
	) {
		return InventoryGetServiceResponse.builder()
			.memberId(inventory.getMemberId())
			.hobby(inventory.getHobby())
			.itemCount(inventoryItemGetResponses.size())
			.inventoryItemInfos(inventoryItemGetResponses)
			.build();
	}
}
