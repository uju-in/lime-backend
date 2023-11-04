package com.programmers.bucketback.domains.inventory.api.dto.response;

import java.util.List;

public record InventoriesGetResponse(
	int inventoryCount,
	List<InventoryInfoSummary> inventoryInfoSummaries
) {
	public static InventoriesGetResponse from(final List<InventoryInfoSummary> inventoryInfoSummaries){
		return new InventoriesGetResponse(inventoryInfoSummaries.size(), inventoryInfoSummaries);
	}
}
