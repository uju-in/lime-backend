package com.programmers.bucketback.domains.review.application.dto;

public record ReviewGetServiceResponse(
	Long reviewId,
	int rate,
	String content
) {
}
