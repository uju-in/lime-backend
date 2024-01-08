package com.programmers.lime.domains.review.application.dto;

public record ReviewGetServiceResponse(
	Long reviewId,
	int rate,
	String content
) {
}
