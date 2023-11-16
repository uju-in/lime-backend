package com.programmers.bucketback.domains.review.application.dto;

import java.util.List;

import com.programmers.bucketback.domains.review.model.ReviewCursorSummary;

public record ReviewGetByCursorServiceResponse(
	Long reviewCount,
	String nextCursorId,
	List<ReviewCursorSummary> reviews
) {
}
