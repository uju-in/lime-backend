package com.programmers.bucketback.domains.inventory.application.vo;

import java.util.List;

public record InventoryUpdateContent(
	List<Long> itemIds
) {
}
