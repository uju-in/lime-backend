package com.programmers.bucketback.domains.review.api.dto.request;

import com.programmers.bucketback.domains.review.application.vo.ReviewContent;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record ReviewUpdateRequest(

	@NotNull(message = "리뷰 평점은 필수 값입니다.")
	@Min(value = 1, message = "리뷰 평점은 최소 1점 입니다.")
	@Max(value = 5, message = "리뷰 평점은 최대 5점 입니다.")
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
