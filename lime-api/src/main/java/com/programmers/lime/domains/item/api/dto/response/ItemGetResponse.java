package com.programmers.lime.domains.item.api.dto.response;

import com.programmers.lime.domains.item.application.dto.ItemGetServiceResponse;
import com.programmers.lime.domains.item.model.ItemInfo;

import lombok.Builder;

@Builder
public record ItemGetResponse(
	ItemInfo itemInfo,
	String itemUrl,
	Double itemAvgRate,
	boolean isMemberItem,
	boolean isReviewed
) {
	public static ItemGetResponse from(final ItemGetServiceResponse response) {
		return ItemGetResponse.builder()
			.itemInfo(response.itemInfo())
			.itemUrl(response.itemUrl())
			.itemAvgRate(response.itemAvgRate())
			.isMemberItem(response.isMemberItem())
			.isReviewed(response.isReviewed())
			.build();
	}
}
