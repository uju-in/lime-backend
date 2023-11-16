package com.programmers.bucketback.domains.item.application.dto;

import com.programmers.bucketback.domains.item.application.vo.ItemInfo;

import lombok.Builder;

@Builder
public record ItemGetServiceResponse(
	ItemInfo itemInfo,
	String itemUrl,
	Double itemAvgRate,
	boolean isMemberItem
) {
}
