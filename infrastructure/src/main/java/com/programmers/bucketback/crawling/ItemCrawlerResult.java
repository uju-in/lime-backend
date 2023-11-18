package com.programmers.bucketback.crawling;

import lombok.Builder;

@Builder
public record ItemCrawlerResult(
	String itemName,
	Integer price,
	String imageUrl,
	String url
) {
}