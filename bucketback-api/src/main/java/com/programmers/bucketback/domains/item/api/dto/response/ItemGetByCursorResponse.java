package com.programmers.bucketback.domains.item.api.dto.response;

import java.util.List;

import com.programmers.bucketback.common.cursor.CursorSummary;
import com.programmers.bucketback.domains.item.application.dto.ItemGetByCursorServiceResponse;
import com.programmers.bucketback.domains.item.model.ItemCursorSummary;

public record ItemGetByCursorResponse(
	String nextCursorId,
	int itemTotalCount,
	int totalCount,
	List<ItemCursorSummary> items
) {
	public static ItemGetByCursorResponse from(final ItemGetByCursorServiceResponse serviceResponse) {
		CursorSummary<ItemCursorSummary> cursorSummary = serviceResponse.cursorSummary();

		return new ItemGetByCursorResponse(
			cursorSummary.nextCursorId(),
			serviceResponse.itemTotalCount(),
			cursorSummary.summaryCount(),
			cursorSummary.summaries()
		);
	}
}
