package com.programmers.lime.domains.inventory.model;

import java.util.List;

import com.programmers.lime.common.model.Hobby;
import com.programmers.lime.domains.inventory.domain.Inventory;

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
