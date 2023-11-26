package com.programmers.bucketback.domains.item.model;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ItemCrawlerInfoBuilder {

	public static ItemCrawlerInfo build() {
		return ItemCrawlerInfo.builder()
			.itemName("아이템")
			.price(10000)
			.url("https://www.naver.com")
			.imageUrl("https://www.naver.com//image")
			.build();
	}
}
