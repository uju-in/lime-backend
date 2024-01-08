package com.programmers.lime.domains.item.application.dto;

import java.util.List;

import com.programmers.lime.domains.item.model.ItemInfo;

public record ItemGetNamesServiceResponse(
	List<ItemInfo> itemNameGetResults
) {
}
