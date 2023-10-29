package com.programmers.bucketback.domains.item.application.dto;

import com.programmers.bucketback.domains.common.Hobby;
import com.programmers.bucketback.domains.item.application.crawling.ItemInfo;

import lombok.Builder;

@Builder
public record CreateItemServiceRequest(
	Hobby hobby,
	String itemName,
	Integer price,
	String imageUrl,
	String url
) {

	public static CreateItemServiceRequest of(
		final Hobby hobby,
		final ItemInfo itemInfo
	) {
		return CreateItemServiceRequest.builder().
			hobby(hobby).
			imageUrl(itemInfo.imageUrl()).
			url(itemInfo.url()).
			price(itemInfo.price()).
			itemName(itemInfo.itemName())
			.build();
	}
}
