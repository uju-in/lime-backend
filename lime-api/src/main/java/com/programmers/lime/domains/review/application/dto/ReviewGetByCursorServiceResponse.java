package com.programmers.lime.domains.review.application.dto;

import com.programmers.lime.common.cursor.CursorSummary;
import com.programmers.lime.domains.review.model.ReviewCursorSummary;

public record ReviewGetByCursorServiceResponse(
	int reviewCount,
	CursorSummary<ReviewCursorSummary> cursorSummary
) {
}
