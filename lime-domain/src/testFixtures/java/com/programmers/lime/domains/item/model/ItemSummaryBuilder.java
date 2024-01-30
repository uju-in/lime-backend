package com.programmers.lime.domains.item.model;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ItemSummaryBuilder {

	public static ItemSummary build(final Long id) {
		return ItemSummary.builder()
			.id(id)
			.name("name" + id)
			.price(id.intValue())
			.image("image" + id)
			.favoriteCount(id)
			.reviewCount(id)
			.build();
	}
}
