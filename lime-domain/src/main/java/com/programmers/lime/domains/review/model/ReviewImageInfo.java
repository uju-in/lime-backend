package com.programmers.lime.domains.review.model;

import java.util.List;

import lombok.Builder;

@Builder
public record ReviewImageInfo(
	Long reviewId,
	List<String> imageUrls
) {

}
