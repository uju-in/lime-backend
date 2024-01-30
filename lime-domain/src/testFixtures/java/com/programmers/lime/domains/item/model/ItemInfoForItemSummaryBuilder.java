package com.programmers.lime.domains.item.model;

import java.util.List;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ItemInfoForItemSummaryBuilder {

	public static ItemInfoForItemSummary build(
		final Long id,
		final String name,
		final Integer price,
		final String image
	) {
		return ItemInfoForItemSummary.builder()
			.id(id)
			.name(name)
			.price(price)
			.image(image)
			.build();
	}

	public static List<ItemInfoForItemSummary> buildMany(List<Long> ids) {
		return ids.stream().map(
			id -> build(id, "name" + id, id.intValue(), "image" + id)
		).toList();
	}
}
