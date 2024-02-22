package com.programmers.lime.domains.item.application.dto;

import com.programmers.lime.common.cursor.CursorSummary;
import com.programmers.lime.domains.item.model.ItemCursorSummary;

public record ItemGetByCursorServiceResponse(

	Long itemTotalCount,
	CursorSummary<ItemCursorSummary> cursorSummary
) {
}
