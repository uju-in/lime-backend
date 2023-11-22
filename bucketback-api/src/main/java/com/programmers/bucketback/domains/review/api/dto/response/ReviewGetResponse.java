package com.programmers.bucketback.domains.review.api.dto.response;

import java.time.LocalDateTime;

import com.programmers.bucketback.domains.member.model.MemberInfo;
import com.programmers.bucketback.domains.review.application.dto.ReviewGetServiceResponse;

public record ReviewGetResponse(
	Long reviewId,
	int rate,
	String content,
	LocalDateTime createdAt,
	LocalDateTime updatedAt,
	MemberInfo memberInfo
) {
	public static ReviewGetResponse from(final ReviewGetServiceResponse serviceResponse) {
		return new ReviewGetResponse(
			serviceResponse.reviewId(),
			serviceResponse.rate(),
			serviceResponse.content(),
			serviceResponse.createdAt(),
			serviceResponse.updatedAt(),
			serviceResponse.memberInfo()
		);
	}
}
