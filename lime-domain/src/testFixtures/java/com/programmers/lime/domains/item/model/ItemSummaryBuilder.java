package com.programmers.lime.domains.item.model;

import java.time.LocalDateTime;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ItemSummaryBuilder {

	public static ItemSummary build(final Long id) {
		return ItemSummary.builder()
			.id(id)
			.name("아이템")
			.price(10000)
			.image("https://www.naver.com//image")
			.createdAt(LocalDateTime.of(2023, 1, 1, 0, 0, 0))
			.build();
	}
}
