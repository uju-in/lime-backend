package com.programmers.bucketback.domains.item.application.crawling;

import lombok.Builder;

@Builder
public record ItemCrawlerInfo(
	String itemName,
	Integer price,
	String imageUrl,
	String url
) {
}
