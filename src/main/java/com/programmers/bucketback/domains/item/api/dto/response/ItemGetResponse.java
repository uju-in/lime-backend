package com.programmers.bucketback.domains.item.api.dto.response;

import lombok.Builder;

@Builder
public record ItemGetResponse(
	Long itemId,
	String itemImage,
	String itemName,
	Integer itemPrice,
	String itemUrl,
	Double itemAvgRate,
	boolean isMemberItem
) {
}
