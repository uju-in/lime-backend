package com.programmers.lime.domains.item.model;

import java.time.LocalDateTime;

import lombok.Builder;

@Builder
public record ItemSummary(
	Long id,

	String name,

	Integer price,

	String image,

	LocalDateTime createdAt,

	Long reviewCount,

	Long favoriteCount
) {
}
