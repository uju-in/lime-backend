package com.programmers.bucketback.domains.inventory.api.dto.response;

import com.programmers.bucketback.domains.item.application.vo.ItemInfo;

public record InventoryItemGetResponse(
	/** 사용할만한 개념객체가 더 있으면 사용 */
	ItemInfo itemInfo,
	String itemUrl
) {

}
