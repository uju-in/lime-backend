package com.programmers.bucketback.domains.review.api.dto.response;

import java.util.List;

import com.programmers.bucketback.common.cursor.CursorSummary;
import com.programmers.bucketback.domains.review.application.dto.ReviewGetByCursorServiceResponse;
import com.programmers.bucketback.domains.review.model.ReviewCursorSummary;

public record ReviewGetByCursorResponse(
	Long reviewCount,
	String nextCursorId,
	List<ReviewCursorSummary> reviews
) {
	public static ReviewGetByCursorResponse from(final ReviewGetByCursorServiceResponse response) {
		CursorSummary<ReviewCursorSummary> cursorSummary = response.cursorSummary();

		return new ReviewGetByCursorResponse(
			response.reviewCount(),
			cursorSummary.nextCursorId(),
			cursorSummary.summaries()
		);
	}
}
