package com.programmers.bucketback.domains.item.application.dto;

import com.programmers.bucketback.common.cursor.CursorSummary;
import com.programmers.bucketback.domains.item.model.ItemCursorSummary;

public record ItemGetByCursorServiceResponse(

	int itemTotalCount,
	CursorSummary<ItemCursorSummary> cursorSummary
) {
}
