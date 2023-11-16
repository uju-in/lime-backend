package com.programmers.bucketback.domains.item.application.dto;

import java.util.List;

public record ItemGetNamesServiceResponse(
	List<ItemNameGetResult> itemNameGetResults
) {
}
