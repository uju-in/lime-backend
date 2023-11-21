package com.programmers.bucketback.domains.item.api.dto.response;

import java.util.List;

import com.programmers.bucketback.common.cursor.CursorSummary;
import com.programmers.bucketback.domains.item.model.ItemCursorSummary;

public record ItemGetByCursorResponse(
	String nextCursorId,
	int totalCount,
	List<ItemCursorSummary> items
) {
	public static ItemGetByCursorResponse from(final CursorSummary<ItemCursorSummary> cursorSummary) {
		return new ItemGetByCursorResponse(
			cursorSummary.nextCursorId(),
			cursorSummary.summaryCount(),
			cursorSummary.summaries()
		);
	}
}
