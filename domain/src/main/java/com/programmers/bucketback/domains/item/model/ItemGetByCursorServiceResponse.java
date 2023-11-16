package com.programmers.bucketback.domains.item.model;

import java.util.List;

public record ItemGetByCursorServiceResponse(
	String nextCursorId,
	List<ItemCursorSummary> items
) {
}
