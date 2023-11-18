package com.programmers.bucketback.domains.item.application.dto;

import java.util.List;

import com.programmers.bucketback.domains.item.model.ItemInfo;

public record ItemGetNamesServiceResponse(
	List<ItemInfo> itemNameGetResults
) {
}
