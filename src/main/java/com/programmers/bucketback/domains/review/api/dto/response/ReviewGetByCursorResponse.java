package com.programmers.bucketback.domains.review.api.dto.response;

import java.util.List;

import com.programmers.bucketback.domains.review.application.vo.ReviewCursorSummary;

public record ReviewGetByCursorResponse(
	Long reviewCount,
	String nextCursorId,
	List<ReviewCursorSummary> reviews
) {
}
