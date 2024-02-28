package com.programmers.lime.domains.item.api.dto.response;

import java.util.List;

import com.programmers.lime.common.cursor.CursorSummary;
import com.programmers.lime.domains.item.application.dto.ItemGetByCursorServiceResponse;
import com.programmers.lime.domains.item.model.ItemCursorSummary;

public record ItemGetByCursorResponse(
	String nextCursorId,
	Long itemTotalCount,
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
