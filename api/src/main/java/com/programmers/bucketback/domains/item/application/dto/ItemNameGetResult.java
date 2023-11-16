package com.programmers.bucketback.domains.item.application.dto;

import com.programmers.bucketback.domains.item.domain.Item;

public record ItemNameGetResult(
	Long itemId,

	String itemName
) {
	public static ItemNameGetResult from(final Item item) {
		return new ItemNameGetResult(
			item.getId(),
			item.getName()
		);
	}
}
