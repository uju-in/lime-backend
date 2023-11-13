package com.programmers.bucketback.domains.item.application.dto;

import com.programmers.bucketback.domains.common.Hobby;
import com.programmers.bucketback.domains.item.application.crawling.ItemInfo;

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
		final ItemInfo itemInfo
	) {
		return ItemCreateServiceRequest.builder().
			hobby(hobby).
			imageUrl(itemInfo.imageUrl()).
			url(itemInfo.url()).
			price(itemInfo.price()).
			itemName(itemInfo.itemName())
			.build();
	}
}
