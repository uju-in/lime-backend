package com.programmers.bucketback.domains.inventory.application.dto;

import java.util.List;

import com.programmers.bucketback.domains.common.Hobby;
import com.programmers.bucketback.domains.inventory.api.dto.response.InventoryItemGetResponse;
import com.programmers.bucketback.domains.inventory.domain.Inventory;

import lombok.Builder;

@Builder
public record GetInventoryServiceResponse(
	Long memberId,
	Hobby hobby,
	Integer itemCount,
	List<InventoryItemGetResponse> inventoryItemGetResponses
) {
	public static GetInventoryServiceResponse of(
		final Inventory inventory,
		final List<InventoryItemGetResponse> inventoryItemGetResponses
	) {
		return GetInventoryServiceResponse.builder()
			.memberId(inventory.getMemberId())
			.hobby(inventory.getHobby())
			.itemCount(inventoryItemGetResponses.size())
			.inventoryItemGetResponses(inventoryItemGetResponses)
			.build();
	}
}
