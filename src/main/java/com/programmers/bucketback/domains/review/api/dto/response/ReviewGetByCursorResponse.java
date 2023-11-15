package com.programmers.bucketback.domains.review.api.dto.response;

import java.util.List;

import com.programmers.bucketback.domains.review.application.dto.ReviewGetByCursorServiceResponse;
import com.programmers.bucketback.domains.review.application.vo.ReviewCursorSummary;

public record ReviewGetByCursorResponse(
	Long reviewCount,
	String nextCursorId,
	List<ReviewCursorSummary> reviews
) {
	public static ReviewGetByCursorResponse from(final ReviewGetByCursorServiceResponse response) {
		return new ReviewGetByCursorResponse(
			response.reviewCount(),
			response.nextCursorId(),
			response.reviews()
		);
	}
}
