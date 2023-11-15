package com.programmers.bucketback.domains.review.application.dto;

import java.util.List;

import com.programmers.bucketback.domains.review.application.vo.ReviewCursorSummary;

public record ReviewGetByCursorServiceResponse(
	Long reviewCount,
	String nextCursorId,
	List<ReviewCursorSummary> reviews
) {
}
