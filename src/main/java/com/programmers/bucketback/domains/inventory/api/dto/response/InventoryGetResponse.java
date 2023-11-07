package com.programmers.bucketback.domains.inventory.api.dto.response;

import java.util.List;

import com.programmers.bucketback.domains.common.Hobby;
import com.programmers.bucketback.domains.inventory.domain.Inventory;

import lombok.Builder;

@Builder
public record InventoryGetResponse(
	Long memberId,
	Hobby hobby,
	int itemCount,
	List<InventoryItemGetResponse> inventoryItemGetResponses
) {

	public static InventoryGetResponse of(
		final Inventory inventory,
		final List<InventoryItemGetResponse> inventoryItemGetResponses
	){
		return InventoryGetResponse.builder()
			.memberId(inventory.getMemberId())
			.hobby(inventory.getHobby())
			.itemCount(inventoryItemGetResponses.size())
			.inventoryItemGetResponses(inventoryItemGetResponses)
			.build();
	}
}
