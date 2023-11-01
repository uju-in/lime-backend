package com.programmers.bucketback.domains.review.api.dto.request;

import com.programmers.bucketback.domains.review.application.dto.ReviewContent;

import jakarta.validation.constraints.NotNull;

public record ReviewCreateRequest(

	@NotNull
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
