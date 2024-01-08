package com.programmers.lime.domains.inventory.api.dto.response;

import java.util.List;

import com.programmers.lime.domains.inventory.model.InventoryInfoSummary;

public record InventoriesGetResponse(
	int totalCount,
	List<InventoryInfoSummary> inventoryInfos
) {
	public static InventoriesGetResponse from(final List<InventoryInfoSummary> inventoryInfoSummaries) {
		return new InventoriesGetResponse(inventoryInfoSummaries.size(), inventoryInfoSummaries);
	}
}
