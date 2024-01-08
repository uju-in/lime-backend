package com.programmers.lime.domains.review.api.dto.response;

import java.util.List;

import com.programmers.lime.common.cursor.CursorSummary;
import com.programmers.lime.domains.review.application.dto.ReviewGetByCursorServiceResponse;
import com.programmers.lime.domains.review.model.ReviewCursorSummary;

public record ReviewGetByCursorResponse(
	String nextCursorId,
	int itemReviewTotalCount,
	int totalCount,
	List<ReviewCursorSummary> reviews
) {
	public static ReviewGetByCursorResponse from(final ReviewGetByCursorServiceResponse response) {
		CursorSummary<ReviewCursorSummary> cursorSummary = response.cursorSummary();

		return new ReviewGetByCursorResponse(
			cursorSummary.nextCursorId(),
			response.reviewCount(),
			cursorSummary.summaryCount(),
			cursorSummary.summaries()
		);
	}
}
