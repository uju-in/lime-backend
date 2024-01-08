package com.programmers.lime.domains.item.application.dto;

import com.programmers.lime.domains.item.model.ItemInfo;

public record ItemNameGetResult(
	Long itemId,

	String itemName
) {
	public static ItemNameGetResult from(final ItemInfo itemInfo) {
		return new ItemNameGetResult(
			itemInfo.id(),
			itemInfo.name()
		);
	}
}
