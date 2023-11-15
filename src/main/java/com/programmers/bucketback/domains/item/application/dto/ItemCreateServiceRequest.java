package com.programmers.bucketback.domains.item.application.dto;

import com.programmers.bucketback.domains.common.Hobby;
import com.programmers.bucketback.domains.item.application.crawling.ItemCrawlerInfo;

import lombok.Builder;

@Builder
public record ItemCreateServiceRequest(
	Hobby hobby,
	String itemName,
	Integer price,
	String imageUrl,
	String url
) {

	public static ItemCreateServiceRequest of(
		final Hobby hobby,
		final ItemCrawlerInfo itemCrawlerInfo
	) {
		return ItemCreateServiceRequest.builder().
			hobby(hobby).
			imageUrl(itemCrawlerInfo.imageUrl()).
			url(itemCrawlerInfo.url()).
			price(itemCrawlerInfo.price()).
			itemName(itemCrawlerInfo.itemName())
			.build();
	}
}
