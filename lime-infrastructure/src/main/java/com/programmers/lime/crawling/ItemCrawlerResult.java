package com.programmers.lime.crawling;

import lombok.Builder;

@Builder
public record ItemCrawlerResult(
	String itemName,
	Integer price,
	String imageUrl,
	String url
) {
}