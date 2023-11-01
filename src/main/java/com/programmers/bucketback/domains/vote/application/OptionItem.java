package com.programmers.bucketback.domains.vote.application;

import com.programmers.bucketback.domains.item.domain.Item;

public record OptionItem(
	Long id,
	String name,
	int price,
	String image
) {
	public static OptionItem from(final Item item) {
		return new OptionItem(
			item.getId(),
			item.getName(),
			item.getPrice(),
			item.getImage()
		);
	}
}
