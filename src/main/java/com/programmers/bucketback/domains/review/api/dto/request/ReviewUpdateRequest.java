package com.programmers.bucketback.domains.review.api.dto.request;

import com.programmers.bucketback.domains.review.application.dto.ReviewContent;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record ReviewUpdateRequest(

	@NotNull
	@Min(1)
	@Max(5)
	Integer rating,

	String content
) {

	public ReviewContent toReviewContent() {
		return new ReviewContent(
			this.rating,
			this.content
		);
	}
}
