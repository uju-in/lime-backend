package com.programmers.bucketback.domains.inventory.api.dto.response;

import com.programmers.bucketback.domains.item.application.vo.ItemInfo;

public record InventoryItemGetResponse(
	ItemInfo itemInfo,
	String itemUrl
) {

}
