package com.programmers.bucketback.domains.inventory.application;

import java.util.List;

import com.programmers.bucketback.domains.common.Hobby;

public record InventoryContent(
	Hobby hobby,
	List<Long> itemIds
) {
}
