package com.programmers.bucketback.domains.item.application.dto;

import com.programmers.bucketback.domains.item.domain.Item;

public record GetItemNameResult(
	Long itemId,

	String itemName
) {
	public static GetItemNameResult from(final Item item) {
		return new GetItemNameResult(
			item.getId(),
			item.getName()
		);
	}
}
