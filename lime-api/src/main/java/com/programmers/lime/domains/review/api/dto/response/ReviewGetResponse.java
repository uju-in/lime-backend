package com.programmers.lime.domains.review.api.dto.response;

import com.programmers.lime.domains.review.application.dto.ReviewGetServiceResponse;

public record ReviewGetResponse(
	Long reviewId,
	int rate,
	String content
) {
	public static ReviewGetResponse from(final ReviewGetServiceResponse serviceResponse) {
		return new ReviewGetResponse(
			serviceResponse.reviewId(),
			serviceResponse.rate(),
			serviceResponse.content()
		);
	}
}
