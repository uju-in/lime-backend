package com.programmers.bucketback.domains.review.application.dto;

import com.programmers.bucketback.common.cursor.CursorSummary;
import com.programmers.bucketback.domains.review.model.ReviewCursorSummary;

public record ReviewGetByCursorServiceResponse(
	int reviewCount,
	CursorSummary<ReviewCursorSummary> cursorSummary
) {
}
