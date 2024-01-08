package com.programmers.lime.domains.item.model;

import lombok.Builder;

@Builder
public record ItemCrawlerInfo(
	String itemName,
	Integer price,
	String imageUrl,
	String url
) {
}
