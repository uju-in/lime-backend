package com.programmers.bucketback.domains.item.api.dto.response;

import java.util.List;

import com.programmers.bucketback.domains.item.application.vo.ItemCursorSummary;

public record ItemGetByCursorResponse(
	String nextCursorId,
	List<ItemCursorSummary> items
) {
}
