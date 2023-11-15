package com.programmers.bucketback.domains.review.api.dto.response;

import com.programmers.bucketback.domains.review.application.dto.ReviewCreateServiceResponse;

public record ReviewCreateResponse(Long reviewId) {
	public static ReviewCreateResponse from(final ReviewCreateServiceResponse response) {
		return new ReviewCreateResponse(response.reviewId());
	}
}
