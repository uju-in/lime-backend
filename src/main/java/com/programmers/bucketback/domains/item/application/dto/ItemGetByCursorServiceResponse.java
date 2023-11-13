package com.programmers.bucketback.domains.item.application.dto;

import java.util.List;

import com.programmers.bucketback.domains.item.application.vo.ItemCursorSummary;

public record ItemGetByCursorServiceResponse(
	String nextCursorId,
	List<ItemCursorSummary> items
) {
}
