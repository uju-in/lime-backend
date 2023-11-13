package com.programmers.bucketback.domains.item.application.dto;

import java.util.List;

import com.programmers.bucketback.domains.item.api.dto.response.ItemGetNamesResponse;

public record ItemGetNamesServiceResponse(
	List<ItemNameGetResult> itemNameGetResults
) {
	public ItemGetNamesResponse toItemGetNamesResponse() {
		return new ItemGetNamesResponse(itemNameGetResults);
	}
}
