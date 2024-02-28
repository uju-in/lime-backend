package com.programmers.lime.domains.review.api.dto.request;

import com.programmers.lime.domains.review.model.ReviewContent;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record ReviewCreateRequest(

	@Schema(description = "아이템 id", example = "1")
	@NotNull(message = "아이템 id는 필수 값입니다.")
	Long itemId,

	@Schema(description = "리뷰 평점", example = "3")
	@NotNull(message = "리뷰 평점은 필수 값입니다.")
	@Min(value = 1, message = "리뷰 평점은 최소 1점 입니다.")
	@Max(value = 5, message = "리뷰 평점은 최대 5점 입니다.")
	Integer rating,

	@Schema(description = "리뷰 내용", example = "제가 원하는 스타일이에요")
	@Size(max = 1000, message = "리뷰 내용은 최대 1000자 입니다.")
	String content
) {
	public ReviewContent toReviewContent() {
		return new ReviewContent(
			this.rating,
			this.content
		);
	}
}
