package com.programmers.lime.domains.item.model;

import lombok.Builder;

@Builder
public record ItemInfoForItemSummary(
	Long id,

	String name,

	Integer price,

	String image
)  {
}
