package com.programmers.lime.domains.review.api.dto.request;

import java.util.List;

import com.programmers.lime.domains.review.model.ReviewContent;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record ReviewUpdateRequest(

	@Schema(description = "리뷰 평점", example = "3")
	@NotNull(message = "리뷰 평점은 필수 값입니다.")
	@Min(value = 1, message = "리뷰 평점은 최소 1점 입니다.")
	@Max(value = 5, message = "리뷰 평점은 최대 5점 입니다.")
	Integer rating,

	@Schema(description = "리뷰 내용", example = "사실 제가 원하는 않은 스타일이에요")
	@Size(min = 10, max = 1000, message = "리뷰 내용은 최소 10자, 최대 1000자 입니다.")
	String content,

	@Schema(description = "삭제할 아이템 url 목록", example = "[https://lime-bucket.kr.object.ncloudstorage.com/review-images/bc17405d-f035-443c-a03c-805146e1f1f1png, https://lime-bucket.kr.object.ncloudstorage.com/review-images/sfewff33-f035-443c-a03c-805146e1f1f1png]")
	List<String> reviewItemUrlsToRemove
) {

	public ReviewContent toReviewContent() {
		return new ReviewContent(
			this.rating,
			this.content
		);
	}
}
