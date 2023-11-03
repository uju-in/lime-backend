package com.programmers.bucketback.domains.item.application.vo;

import lombok.Builder;

@Builder
public record ItemInfo(
	Long id,

	String name,

	Integer price,

	String image
) {
}
