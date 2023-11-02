package com.programmers.bucketback.domains.vote.application;

import com.programmers.bucketback.domains.item.domain.Item;

import lombok.Builder;

@Builder
public record OptionItem(
	Long id,
	String name,
	int price,
	String image
) {
	public static OptionItem from(final Item item) {
		return OptionItem.builder()
			.id(item.getId())
			.name(item.getName())
			.price(item.getPrice())
			.image(item.getImage())
			.build();
	}
}
