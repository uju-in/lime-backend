package com.programmers.lime.domains.item.application.dto;

import com.programmers.lime.domains.item.model.ItemInfo;

import lombok.Builder;

@Builder
public record ItemGetServiceResponse(
	ItemInfo itemInfo,
	String itemUrl,
	Double itemAvgRate,
	boolean isMemberItem,
	boolean isReviewed,
	int favoriteCount,
	int reviewCount,
	String hobbyName
	) {
}
