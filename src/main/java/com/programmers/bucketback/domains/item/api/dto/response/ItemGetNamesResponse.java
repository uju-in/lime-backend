package com.programmers.bucketback.domains.item.api.dto.response;

import java.util.List;

import com.programmers.bucketback.domains.item.application.dto.ItemNameGetResult;

public record ItemGetNamesResponse(
	List<ItemNameGetResult> itemNameGetResults
) {
}
