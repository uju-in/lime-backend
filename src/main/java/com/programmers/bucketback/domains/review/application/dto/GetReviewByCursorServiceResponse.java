package com.programmers.bucketback.domains.review.application.dto;

import java.util.List;

import com.programmers.bucketback.domains.review.api.dto.response.ReviewGetByCursorResponse;
import com.programmers.bucketback.domains.review.application.vo.ReviewCursorSummary;

public record GetReviewByCursorServiceResponse(
	Long reviewCount,
	String nextCursorId,
	List<ReviewCursorSummary> reviews
) {
	public ReviewGetByCursorResponse toReviewGetByCursorResponse() {
		return new ReviewGetByCursorResponse(
			reviewCount,
			nextCursorId,
			reviews
		);
	}
}
