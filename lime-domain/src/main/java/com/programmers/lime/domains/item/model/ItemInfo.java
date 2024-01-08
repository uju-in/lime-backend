package com.programmers.lime.domains.item.model;

import com.programmers.lime.domains.item.domain.Item;

import lombok.Builder;

@Builder
public record ItemInfo(
	Long id,

	String name,

	Integer price,

	String image
) {
	public static ItemInfo from(final Item item){
		return ItemInfo.builder()
			.id(item.getId())
			.name(item.getName())
			.price(item.getPrice())
			.image(item.getImage())
			.build();
	}
}
